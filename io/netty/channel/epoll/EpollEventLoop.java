/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.collection.IntObjectMap;
/*     */ import io.netty.util.collection.IntObjectMap.Entry;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ final class EpollEventLoop
/*     */   extends SingleThreadEventLoop
/*     */ {
/*     */   private static final InternalLogger logger;
/*     */   private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER;
/*     */   private final int epollFd;
/*     */   private final int eventFd;
/*     */   
/*     */   static
/*     */   {
/*  39 */     logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
/*     */     
/*     */ 
/*     */ 
/*  43 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(EpollEventLoop.class, "wakenUp");
/*     */     
/*  45 */     if (localAtomicIntegerFieldUpdater == null) {
/*  46 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
/*     */     }
/*  48 */     WAKEN_UP_UPDATER = localAtomicIntegerFieldUpdater;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  53 */   private final IntObjectMap<AbstractEpollChannel> ids = new IntObjectHashMap();
/*     */   
/*     */   private final long[] events;
/*     */   
/*     */   private int id;
/*     */   
/*     */   private boolean overflown;
/*     */   private volatile int wakenUp;
/*  61 */   private volatile int ioRatio = 50;
/*     */   
/*     */   EpollEventLoop(EventLoopGroup paramEventLoopGroup, ThreadFactory paramThreadFactory, int paramInt) {
/*  64 */     super(paramEventLoopGroup, paramThreadFactory, false);
/*  65 */     this.events = new long[paramInt];
/*  66 */     int i = 0;
/*  67 */     int j = -1;
/*  68 */     int k = -1;
/*     */     try {
/*  70 */       this.epollFd = (j = Native.epollCreate());
/*  71 */       this.eventFd = (k = Native.eventFd());
/*  72 */       Native.epollCtlAdd(j, k, 1, 0);
/*  73 */       i = 1; return;
/*     */     } finally {
/*  75 */       if (i == 0) {
/*  76 */         if (j != -1) {
/*     */           try {
/*  78 */             Native.close(j);
/*     */           }
/*     */           catch (Exception localException3) {}
/*     */         }
/*     */         
/*  83 */         if (k != -1) {
/*     */           try {
/*  85 */             Native.close(k);
/*     */           }
/*     */           catch (Exception localException4) {}
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int nextId()
/*     */   {
/*  95 */     int i = this.id;
/*  96 */     if (i == Integer.MAX_VALUE) {
/*  97 */       this.overflown = true;
/*  98 */       i = 0;
/*     */     }
/* 100 */     if (this.overflown)
/*     */     {
/*     */ 
/*     */ 
/* 104 */       while (this.ids.containsKey(++i)) {}
/* 105 */       this.id = i;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 110 */       this.id = (++i);
/*     */     }
/* 112 */     return i;
/*     */   }
/*     */   
/*     */   protected void wakeup(boolean paramBoolean)
/*     */   {
/* 117 */     if ((!paramBoolean) && (WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)))
/*     */     {
/* 119 */       Native.eventFdWrite(this.eventFd, 1L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void add(AbstractEpollChannel paramAbstractEpollChannel)
/*     */   {
/* 127 */     assert (inEventLoop());
/* 128 */     int i = nextId();
/* 129 */     Native.epollCtlAdd(this.epollFd, paramAbstractEpollChannel.fd, paramAbstractEpollChannel.flags, i);
/* 130 */     paramAbstractEpollChannel.id = i;
/* 131 */     this.ids.put(i, paramAbstractEpollChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void modify(AbstractEpollChannel paramAbstractEpollChannel)
/*     */   {
/* 138 */     assert (inEventLoop());
/* 139 */     Native.epollCtlMod(this.epollFd, paramAbstractEpollChannel.fd, paramAbstractEpollChannel.flags, paramAbstractEpollChannel.id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void remove(AbstractEpollChannel paramAbstractEpollChannel)
/*     */   {
/* 146 */     assert (inEventLoop());
/* 147 */     if ((this.ids.remove(paramAbstractEpollChannel.id) != null) && (paramAbstractEpollChannel.isOpen()))
/*     */     {
/*     */ 
/* 150 */       Native.epollCtlDel(this.epollFd, paramAbstractEpollChannel.fd);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected Queue<Runnable> newTaskQueue()
/*     */   {
/* 157 */     return PlatformDependent.newMpscQueue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getIoRatio()
/*     */   {
/* 164 */     return this.ioRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIoRatio(int paramInt)
/*     */   {
/* 172 */     if ((paramInt <= 0) || (paramInt > 100)) {
/* 173 */       throw new IllegalArgumentException("ioRatio: " + paramInt + " (expected: 0 < ioRatio <= 100)");
/*     */     }
/* 175 */     this.ioRatio = paramInt;
/*     */   }
/*     */   
/*     */   private int epollWait(boolean paramBoolean) {
/* 179 */     int i = 0;
/* 180 */     long l1 = System.nanoTime();
/* 181 */     long l2 = l1 + delayNanos(l1);
/*     */     for (;;) {
/* 183 */       long l3 = (l2 - l1 + 500000L) / 1000000L;
/* 184 */       if (l3 <= 0L) {
/* 185 */         if (i != 0) break;
/* 186 */         j = Native.epollWait(this.epollFd, this.events, 0);
/* 187 */         if (j > 0) {
/* 188 */           return j;
/*     */         }
/* 190 */         break;
/*     */       }
/*     */       
/*     */ 
/* 194 */       int j = Native.epollWait(this.epollFd, this.events, (int)l3);
/* 195 */       i++;
/*     */       
/* 197 */       if ((j != 0) || (paramBoolean) || (this.wakenUp == 1) || (hasTasks()) || (hasScheduledTasks()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 202 */         return j;
/*     */       }
/* 204 */       l1 = System.nanoTime();
/*     */     }
/* 206 */     return 0;
/*     */   }
/*     */   
/*     */   protected void run()
/*     */   {
/*     */     for (;;) {
/* 212 */       boolean bool = WAKEN_UP_UPDATER.getAndSet(this, 0) == 1;
/*     */       try {
/*     */         int i;
/* 215 */         if (hasTasks())
/*     */         {
/* 217 */           i = Native.epollWait(this.epollFd, this.events, 0);
/*     */         } else {
/* 219 */           i = epollWait(bool);
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
/*     */ 
/*     */ 
/*     */ 
/* 249 */           if (this.wakenUp == 1) {
/* 250 */             Native.eventFdWrite(this.eventFd, 1L);
/*     */           }
/*     */         }
/*     */         
/* 254 */         int j = this.ioRatio;
/* 255 */         if (j == 100) {
/* 256 */           if (i > 0) {
/* 257 */             processReady(this.events, i);
/*     */           }
/* 259 */           runAllTasks();
/*     */         } else {
/* 261 */           long l1 = System.nanoTime();
/*     */           
/* 263 */           if (i > 0) {
/* 264 */             processReady(this.events, i);
/*     */           }
/*     */           
/* 267 */           long l2 = System.nanoTime() - l1;
/* 268 */           runAllTasks(l2 * (100 - j) / j);
/*     */         }
/*     */         
/* 271 */         if (isShuttingDown()) {
/* 272 */           closeAll();
/* 273 */           if (confirmShutdown()) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 278 */         logger.warn("Unexpected exception in the selector loop.", localThrowable);
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 283 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeAll()
/*     */   {
/* 292 */     Native.epollWait(this.epollFd, this.events, 0);
/* 293 */     ArrayList localArrayList = new ArrayList(this.ids.size());
/*     */     
/* 295 */     for (Iterator localIterator = this.ids.entries().iterator(); localIterator.hasNext();) { localObject = (IntObjectMap.Entry)localIterator.next();
/* 296 */       localArrayList.add(((IntObjectMap.Entry)localObject).value());
/*     */     }
/*     */     Object localObject;
/* 299 */     for (localIterator = localArrayList.iterator(); localIterator.hasNext();) { localObject = (AbstractEpollChannel)localIterator.next();
/* 300 */       ((AbstractEpollChannel)localObject).unsafe().close(((AbstractEpollChannel)localObject).unsafe().voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private void processReady(long[] paramArrayOfLong, int paramInt) {
/* 305 */     for (int i = 0; i < paramInt; i++) {
/* 306 */       long l = paramArrayOfLong[i];
/*     */       
/* 308 */       int j = (int)(l >> 32);
/* 309 */       if (j == 0)
/*     */       {
/* 311 */         Native.eventFdRead(this.eventFd);
/*     */       } else {
/* 313 */         int k = (l & 1L) != 0L ? 1 : 0;
/* 314 */         int m = (l & 0x2) != 0L ? 1 : 0;
/* 315 */         int n = (l & 0x8) != 0L ? 1 : 0;
/*     */         
/* 317 */         AbstractEpollChannel localAbstractEpollChannel = (AbstractEpollChannel)this.ids.get(j);
/* 318 */         if (localAbstractEpollChannel != null) {
/* 319 */           AbstractEpollChannel.AbstractEpollUnsafe localAbstractEpollUnsafe = (AbstractEpollChannel.AbstractEpollUnsafe)localAbstractEpollChannel.unsafe();
/* 320 */           if ((m != 0) && (localAbstractEpollChannel.isOpen()))
/*     */           {
/* 322 */             localAbstractEpollUnsafe.epollOutReady();
/*     */           }
/* 324 */           if ((k != 0) && (localAbstractEpollChannel.isOpen()))
/*     */           {
/* 326 */             localAbstractEpollUnsafe.epollInReady();
/*     */           }
/* 328 */           if ((n != 0) && (localAbstractEpollChannel.isOpen())) {
/* 329 */             localAbstractEpollUnsafe.epollRdHupReady();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void cleanup()
/*     */   {
/*     */     try {
/* 339 */       Native.close(this.epollFd);
/*     */     } catch (IOException localIOException1) {
/* 341 */       logger.warn("Failed to close the epoll fd.", localIOException1);
/*     */     }
/*     */     try {
/* 344 */       Native.close(this.eventFd);
/*     */     } catch (IOException localIOException2) {
/* 346 */       logger.warn("Failed to close the event fd.", localIOException2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */