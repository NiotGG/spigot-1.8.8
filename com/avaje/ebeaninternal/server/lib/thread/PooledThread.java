/*     */ package com.avaje.ebeaninternal.server.lib.thread;
/*     */ 
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
/*     */ public class PooledThread
/*     */   implements Runnable
/*     */ {
/*  29 */   private static final Logger logger = Logger.getLogger(PooledThread.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected PooledThread(ThreadPool threadPool, String name, boolean isDaemon, Integer threadPriority)
/*     */   {
/*  37 */     this.name = name;
/*  38 */     this.threadPool = threadPool;
/*  39 */     this.lastUsedTime = System.currentTimeMillis();
/*     */     
/*  41 */     this.thread = new Thread(this, name);
/*  42 */     this.thread.setDaemon(isDaemon);
/*     */     
/*  44 */     if (threadPriority != null) {
/*  45 */       this.thread.setPriority(threadPriority.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void start()
/*     */   {
/*  51 */     this.thread.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean assignWork(Work work)
/*     */   {
/*  59 */     synchronized (this.workMonitor) {
/*  60 */       this.work = work;
/*  61 */       this.workMonitor.notifyAll();
/*     */     }
/*  63 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*  71 */     synchronized (this.workMonitor) {
/*  72 */       while (!this.isStopping) {
/*     */         try {
/*  74 */           if (this.work == null) {
/*  75 */             this.workMonitor.wait();
/*     */           }
/*     */         }
/*     */         catch (InterruptedException e) {}
/*  79 */         doTheWork();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  84 */     synchronized (this.threadMonitor) {
/*  85 */       this.threadMonitor.notifyAll();
/*     */     }
/*     */     
/*  88 */     this.isStopped = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void doTheWork()
/*     */   {
/*  95 */     if (this.isStopping) {
/*  96 */       return;
/*     */     }
/*     */     
/*  99 */     long startTime = System.currentTimeMillis();
/* 100 */     if (this.work != null)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 105 */         this.work.setStartTime(startTime);
/* 106 */         this.work.getRunnable().run();
/*     */       }
/*     */       catch (Throwable ex) {
/* 109 */         logger.log(Level.SEVERE, null, ex);
/*     */         
/* 111 */         if (this.wasInterrupted) {
/* 112 */           this.isStopping = true;
/* 113 */           this.threadPool.removeThread(this);
/* 114 */           logger.info("PooledThread [" + this.name + "] removed due to interrupt");
/*     */           try {
/* 116 */             this.thread.interrupt();
/*     */           } catch (Exception e) {
/* 118 */             String msg = "Error interrupting PooledThead[" + this.name + "]";
/* 119 */             logger.log(Level.SEVERE, msg, e);
/*     */           }
/* 121 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 125 */     this.lastUsedTime = System.currentTimeMillis();
/* 126 */     this.totalWorkCount += 1;
/* 127 */     this.totalWorkExecutionTime = (this.totalWorkExecutionTime + this.lastUsedTime - startTime);
/* 128 */     this.work = null;
/* 129 */     this.threadPool.returnThread(this);
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
/*     */   public void interrupt()
/*     */   {
/* 143 */     this.wasInterrupted = true;
/*     */     try {
/* 145 */       this.thread.interrupt();
/*     */     }
/*     */     catch (SecurityException ex) {
/* 148 */       this.wasInterrupted = false;
/* 149 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isStopped()
/*     */   {
/* 157 */     return this.isStopped;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void stop()
/*     */   {
/* 165 */     this.isStopping = true;
/*     */     
/* 167 */     synchronized (this.threadMonitor)
/*     */     {
/* 169 */       assignWork(null);
/*     */       try
/*     */       {
/* 172 */         this.threadMonitor.wait(10000L);
/*     */       }
/*     */       catch (InterruptedException e) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 179 */     this.thread = null;
/* 180 */     this.threadPool.removeThread(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 187 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */   public Work getWork()
/*     */   {
/* 193 */     return this.work;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalWorkCount()
/*     */   {
/* 200 */     return this.totalWorkCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getTotalWorkExecutionTime()
/*     */   {
/* 207 */     return this.totalWorkExecutionTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getLastUsedTime()
/*     */   {
/* 214 */     return this.lastUsedTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 220 */   private boolean wasInterrupted = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastUsedTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 230 */   private Work work = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 235 */   private boolean isStopping = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 240 */   private boolean isStopped = false;
/*     */   
/*     */ 
/*     */ 
/* 244 */   private Thread thread = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ThreadPool threadPool;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 254 */   private String name = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 259 */   private Object threadMonitor = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 264 */   private Object workMonitor = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 269 */   private int totalWorkCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 274 */   private long totalWorkExecutionTime = 0L;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\thread\PooledThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */