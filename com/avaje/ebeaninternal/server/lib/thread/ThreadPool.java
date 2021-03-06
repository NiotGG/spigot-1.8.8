/*     */ package com.avaje.ebeaninternal.server.lib.thread;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPool
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(ThreadPool.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long maxIdleTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String poolName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int minSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isDaemon;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  61 */   private boolean isStopping = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Integer threadPriority;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int uniqueThreadID;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   private Vector<PooledThread> freeList = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  83 */   private Vector<PooledThread> busyList = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  88 */   private Vector<Work> workOverflowQueue = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  94 */   private int maxSize = 100;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean stopThePool;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThreadPool(String poolName, boolean isDaemon, Integer threadPriority)
/*     */   {
/* 107 */     this.poolName = poolName;
/* 108 */     this.stopThePool = false;
/* 109 */     this.isDaemon = isDaemon;
/* 110 */     this.threadPriority = threadPriority;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isStopping()
/*     */   {
/* 117 */     return this.isStopping;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 124 */     return this.poolName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMinSize(int minSize)
/*     */   {
/* 131 */     if (minSize > 0) {
/* 132 */       if (minSize > this.maxSize) {
/* 133 */         this.maxSize = minSize;
/*     */       }
/* 135 */       this.minSize = minSize;
/* 136 */       maintainPoolSize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMinSize()
/*     */   {
/* 144 */     return this.minSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMaxSize(int maxSize)
/*     */   {
/* 151 */     if (maxSize > 0) {
/* 152 */       if (this.minSize > maxSize) {
/* 153 */         this.minSize = maxSize;
/*     */       }
/* 155 */       this.maxSize = maxSize;
/* 156 */       maintainPoolSize();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMaxSize()
/*     */   {
/* 164 */     return this.maxSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 171 */     return this.busyList.size() + this.freeList.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getBusyCount()
/*     */   {
/* 178 */     return this.busyList.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean assign(Runnable work, boolean addToQueueIfFull)
/*     */   {
/* 198 */     if (this.stopThePool) {
/* 199 */       throw new RuntimeException("Pool is stopping... no more work please.");
/*     */     }
/*     */     
/* 202 */     Work runWork = new Work(work);
/*     */     
/*     */ 
/* 205 */     PooledThread thread = getNextAvailableThread();
/* 206 */     if (thread != null)
/*     */     {
/* 208 */       this.busyList.add(thread);
/* 209 */       thread.assignWork(runWork);
/* 210 */       return true;
/*     */     }
/*     */     
/* 213 */     if (addToQueueIfFull) {
/* 214 */       runWork.setEnterQueueTime(System.currentTimeMillis());
/* 215 */       this.workOverflowQueue.add(runWork);
/*     */     }
/* 217 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeThread(PooledThread thread)
/*     */   {
/* 226 */     synchronized (this.freeList) {
/* 227 */       this.busyList.remove(thread);
/* 228 */       this.freeList.remove(thread);
/* 229 */       this.freeList.notify();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void returnThread(PooledThread thread)
/*     */   {
/* 243 */     synchronized (this.freeList)
/*     */     {
/*     */ 
/* 246 */       this.busyList.remove(thread);
/*     */       
/* 248 */       if (!this.workOverflowQueue.isEmpty())
/*     */       {
/* 250 */         Work queuedWork = (Work)this.workOverflowQueue.remove(0);
/*     */         
/*     */ 
/* 253 */         queuedWork.setExitQueueTime(System.currentTimeMillis());
/* 254 */         this.busyList.add(thread);
/* 255 */         thread.assignWork(queuedWork);
/*     */       }
/*     */       else
/*     */       {
/* 259 */         this.freeList.add(thread);
/*     */         
/* 261 */         this.freeList.notify();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private PooledThread getNextAvailableThread()
/*     */   {
/* 274 */     synchronized (this.freeList) {
/* 275 */       if (!this.freeList.isEmpty()) {
/* 276 */         return (PooledThread)this.freeList.remove(0);
/*     */       }
/* 278 */       if (size() < this.maxSize) {
/* 279 */         return growPool(true);
/*     */       }
/* 281 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Iterator<PooledThread> getBusyThreads()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 39	com/avaje/ebeaninternal/server/lib/thread/ThreadPool:freeList	Ljava/util/Vector;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 41	com/avaje/ebeaninternal/server/lib/thread/ThreadPool:busyList	Ljava/util/Vector;
/*     */     //   11: invokevirtual 147	java/util/Vector:iterator	()Ljava/util/Iterator;
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: areturn
/*     */     //   17: astore_2
/*     */     //   18: aload_1
/*     */     //   19: monitorexit
/*     */     //   20: aload_2
/*     */     //   21: athrow
/*     */     // Line number table:
/*     */     //   Java source line #293	-> byte code offset #0
/*     */     //   Java source line #294	-> byte code offset #7
/*     */     //   Java source line #295	-> byte code offset #17
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	22	0	this	ThreadPool
/*     */     //   5	14	1	Ljava/lang/Object;	Object
/*     */     //   17	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	16	17	finally
/*     */     //   17	20	17	finally
/*     */   }
/*     */   
/*     */   protected void shutdown()
/*     */   {
/* 304 */     synchronized (this.freeList) {
/* 305 */       this.isStopping = true;
/*     */       
/* 307 */       int size = size();
/*     */       
/* 309 */       if (size > 0) {
/* 310 */         String msg = null;
/* 311 */         msg = "ThreadPool [" + this.poolName + "] Shutting down; threadCount[" + size() + "] busyCount[" + getBusyCount() + "]";
/*     */         
/*     */ 
/* 314 */         logger.info(msg);
/*     */       }
/*     */       
/* 317 */       this.stopThePool = true;
/*     */       
/* 319 */       while (!this.freeList.isEmpty()) {
/* 320 */         PooledThread thread = (PooledThread)this.freeList.remove(0);
/* 321 */         thread.stop();
/*     */       }
/*     */       try
/*     */       {
/* 325 */         while (getBusyCount() > 0)
/*     */         {
/* 327 */           String msg = "ThreadPool [" + this.poolName + "] has [" + getBusyCount() + "] busy threads, waiting for those to finish.";
/*     */           
/* 329 */           logger.info(msg);
/*     */           
/* 331 */           Iterator<PooledThread> busyThreads = getBusyThreads();
/* 332 */           while (busyThreads.hasNext()) {
/* 333 */             PooledThread busyThread = (PooledThread)busyThreads.next();
/*     */             
/* 335 */             String threadName = busyThread.getName();
/* 336 */             Work work = busyThread.getWork();
/*     */             
/* 338 */             String busymsg = "Busy thread [" + threadName + "] work[" + work + "]";
/* 339 */             logger.info(busymsg);
/*     */           }
/*     */           
/*     */ 
/* 343 */           this.freeList.wait();
/* 344 */           PooledThread thread = (PooledThread)this.freeList.remove(0);
/*     */           
/*     */ 
/* 347 */           if (thread != null) {
/* 348 */             thread.stop();
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/* 354 */         logger.log(Level.SEVERE, null, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void maintainPoolSize()
/*     */   {
/* 363 */     synchronized (this.freeList) {
/* 364 */       if (this.isStopping)
/*     */       {
/* 366 */         return;
/*     */       }
/*     */       
/* 369 */       int numToStop = size() - this.minSize;
/* 370 */       if (numToStop > 0)
/*     */       {
/* 372 */         long usedAfter = System.currentTimeMillis() - this.maxIdleTime;
/* 373 */         ArrayList<PooledThread> stopList = new ArrayList();
/* 374 */         Iterator<PooledThread> it = this.freeList.iterator();
/* 375 */         while ((it.hasNext()) && (numToStop > 0)) {
/* 376 */           PooledThread thread = (PooledThread)it.next();
/* 377 */           if (thread.getLastUsedTime() < usedAfter) {
/* 378 */             stopList.add(thread);
/* 379 */             numToStop--;
/*     */           }
/*     */         }
/* 382 */         Iterator<PooledThread> stopIt = stopList.iterator();
/* 383 */         while (stopIt.hasNext()) {
/* 384 */           PooledThread thread = (PooledThread)stopIt.next();
/* 385 */           thread.stop();
/*     */         }
/*     */       }
/* 388 */       int numToAdd = this.minSize - size();
/* 389 */       if (numToAdd > 0)
/*     */       {
/* 391 */         for (int i = 0; i < numToAdd; i++) {
/* 392 */           growPool(false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PooledThread interrupt(String threadName)
/*     */   {
/* 424 */     PooledThread thread = getBusyThread(threadName);
/* 425 */     if (thread != null) {
/* 426 */       thread.interrupt();
/* 427 */       return thread;
/*     */     }
/* 429 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PooledThread getBusyThread(String threadName)
/*     */   {
/* 437 */     synchronized (this.freeList) {
/* 438 */       Iterator<PooledThread> it = getBusyThreads();
/* 439 */       while (it.hasNext()) {
/* 440 */         PooledThread pt = (PooledThread)it.next();
/* 441 */         if (pt.getName().equals(threadName)) {
/* 442 */           return pt;
/*     */         }
/*     */       }
/* 445 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private PooledThread growPool(boolean andReturn)
/*     */   {
/* 455 */     synchronized (this.freeList)
/*     */     {
/* 457 */       String threadName = this.poolName + "." + this.uniqueThreadID++;
/* 458 */       PooledThread bgw = new PooledThread(this, threadName, this.isDaemon, this.threadPriority);
/* 459 */       bgw.start();
/*     */       
/* 461 */       if (logger.isLoggable(Level.FINE)) {
/* 462 */         logger.fine("ThreadPool grow created [" + threadName + "] size[" + size() + "]");
/*     */       }
/* 464 */       if (andReturn) {
/* 465 */         return bgw;
/*     */       }
/* 467 */       this.freeList.add(bgw);
/* 468 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getMaxIdleTime()
/*     */   {
/* 478 */     return this.maxIdleTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaxIdleTime(long maxIdleTime)
/*     */   {
/* 486 */     this.maxIdleTime = maxIdleTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\thread\ThreadPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */