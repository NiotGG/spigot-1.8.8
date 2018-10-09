/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Iterator;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public final class GlobalEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
/*     */   
/*  42 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */   
/*  44 */   public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
/*     */   
/*  46 */   final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue();
/*  47 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue();
/*  48 */   final ScheduledFutureTask<Void> purgeTask = new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(null), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL);
/*     */   
/*     */ 
/*     */ 
/*  52 */   private final ThreadFactory threadFactory = new DefaultThreadFactory(getClass());
/*  53 */   private final TaskRunner taskRunner = new TaskRunner();
/*  54 */   private final AtomicBoolean started = new AtomicBoolean();
/*     */   
/*     */   volatile Thread thread;
/*  57 */   private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
/*     */   
/*     */   private GlobalEventExecutor() {
/*  60 */     this.delayedTaskQueue.add(this.purgeTask);
/*     */   }
/*     */   
/*     */   public EventExecutorGroup parent()
/*     */   {
/*  65 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Runnable takeTask()
/*     */   {
/*  74 */     BlockingQueue localBlockingQueue = this.taskQueue;
/*     */     for (;;) {
/*  76 */       ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/*  77 */       if (localScheduledFutureTask == null) {
/*  78 */         Runnable localRunnable1 = null;
/*     */         try {
/*  80 */           localRunnable1 = (Runnable)localBlockingQueue.take();
/*     */         }
/*     */         catch (InterruptedException localInterruptedException1) {}
/*     */         
/*  84 */         return localRunnable1;
/*     */       }
/*  86 */       long l = localScheduledFutureTask.delayNanos();
/*     */       Runnable localRunnable2;
/*  88 */       if (l > 0L) {
/*     */         try {
/*  90 */           localRunnable2 = (Runnable)localBlockingQueue.poll(l, TimeUnit.NANOSECONDS);
/*     */         } catch (InterruptedException localInterruptedException2) {
/*  92 */           return null;
/*     */         }
/*     */       } else {
/*  95 */         localRunnable2 = (Runnable)localBlockingQueue.poll();
/*     */       }
/*     */       
/*  98 */       if (localRunnable2 == null) {
/*  99 */         fetchFromDelayedQueue();
/* 100 */         localRunnable2 = (Runnable)localBlockingQueue.poll();
/*     */       }
/*     */       
/* 103 */       if (localRunnable2 != null) {
/* 104 */         return localRunnable2;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fetchFromDelayedQueue()
/*     */   {
/* 111 */     long l = 0L;
/*     */     for (;;) {
/* 113 */       ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/* 114 */       if (localScheduledFutureTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 118 */       if (l == 0L) {
/* 119 */         l = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 122 */       if (localScheduledFutureTask.deadlineNanos() > l) break;
/* 123 */       this.delayedTaskQueue.remove();
/* 124 */       this.taskQueue.add(localScheduledFutureTask);
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
/*     */   public int pendingTasks()
/*     */   {
/* 138 */     return this.taskQueue.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addTask(Runnable paramRunnable)
/*     */   {
/* 146 */     if (paramRunnable == null) {
/* 147 */       throw new NullPointerException("task");
/*     */     }
/* 149 */     this.taskQueue.add(paramRunnable);
/*     */   }
/*     */   
/*     */   public boolean inEventLoop(Thread paramThread)
/*     */   {
/* 154 */     return paramThread == this.thread;
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 159 */     return terminationFuture();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/* 164 */     return this.terminationFuture;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown()
/*     */   {
/* 170 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/* 175 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean awaitInactivity(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 202 */     if (paramTimeUnit == null) {
/* 203 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 206 */     Thread localThread = this.thread;
/* 207 */     if (localThread == null) {
/* 208 */       throw new IllegalStateException("thread was not started");
/*     */     }
/* 210 */     localThread.join(paramTimeUnit.toMillis(paramLong));
/* 211 */     return !localThread.isAlive();
/*     */   }
/*     */   
/*     */   public void execute(Runnable paramRunnable)
/*     */   {
/* 216 */     if (paramRunnable == null) {
/* 217 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 220 */     addTask(paramRunnable);
/* 221 */     if (!inEventLoop()) {
/* 222 */       startThread();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 230 */     if (paramRunnable == null) {
/* 231 */       throw new NullPointerException("command");
/*     */     }
/* 233 */     if (paramTimeUnit == null) {
/* 234 */       throw new NullPointerException("unit");
/*     */     }
/* 236 */     if (paramLong < 0L) {
/* 237 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong) }));
/*     */     }
/*     */     
/* 240 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, paramRunnable, null, ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong))));
/*     */   }
/*     */   
/*     */ 
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 246 */     if (paramCallable == null) {
/* 247 */       throw new NullPointerException("callable");
/*     */     }
/* 249 */     if (paramTimeUnit == null) {
/* 250 */       throw new NullPointerException("unit");
/*     */     }
/* 252 */     if (paramLong < 0L) {
/* 253 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong) }));
/*     */     }
/*     */     
/* 256 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, paramCallable, ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong))));
/*     */   }
/*     */   
/*     */ 
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 262 */     if (paramRunnable == null) {
/* 263 */       throw new NullPointerException("command");
/*     */     }
/* 265 */     if (paramTimeUnit == null) {
/* 266 */       throw new NullPointerException("unit");
/*     */     }
/* 268 */     if (paramLong1 < 0L) {
/* 269 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong1) }));
/*     */     }
/*     */     
/* 272 */     if (paramLong2 <= 0L) {
/* 273 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(paramLong2) }));
/*     */     }
/*     */     
/*     */ 
/* 277 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(paramRunnable, null), ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong1)), paramTimeUnit.toNanos(paramLong2)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 284 */     if (paramRunnable == null) {
/* 285 */       throw new NullPointerException("command");
/*     */     }
/* 287 */     if (paramTimeUnit == null) {
/* 288 */       throw new NullPointerException("unit");
/*     */     }
/* 290 */     if (paramLong1 < 0L) {
/* 291 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong1) }));
/*     */     }
/*     */     
/* 294 */     if (paramLong2 <= 0L) {
/* 295 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(paramLong2) }));
/*     */     }
/*     */     
/*     */ 
/* 299 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(paramRunnable, null), ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong1)), -paramTimeUnit.toNanos(paramLong2)));
/*     */   }
/*     */   
/*     */ 
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> paramScheduledFutureTask)
/*     */   {
/* 305 */     if (paramScheduledFutureTask == null) {
/* 306 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 309 */     if (inEventLoop()) {
/* 310 */       this.delayedTaskQueue.add(paramScheduledFutureTask);
/*     */     } else {
/* 312 */       execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 315 */           GlobalEventExecutor.this.delayedTaskQueue.add(paramScheduledFutureTask);
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 320 */     return paramScheduledFutureTask;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 324 */     if (this.started.compareAndSet(false, true)) {
/* 325 */       Thread localThread = this.threadFactory.newThread(this.taskRunner);
/* 326 */       localThread.start();
/* 327 */       this.thread = localThread;
/*     */     }
/*     */   }
/*     */   
/*     */   final class TaskRunner implements Runnable {
/*     */     TaskRunner() {}
/*     */     
/*     */     public void run() {
/* 335 */       for (;;) { Runnable localRunnable = GlobalEventExecutor.this.takeTask();
/* 336 */         if (localRunnable != null) {
/*     */           try {
/* 338 */             localRunnable.run();
/*     */           } catch (Throwable localThrowable) {
/* 340 */             GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", localThrowable);
/*     */           }
/*     */           
/* 343 */           if (localRunnable != GlobalEventExecutor.this.purgeTask) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 349 */         else if ((GlobalEventExecutor.this.taskQueue.isEmpty()) && (GlobalEventExecutor.this.delayedTaskQueue.size() == 1))
/*     */         {
/*     */ 
/*     */ 
/* 353 */           boolean bool = GlobalEventExecutor.this.started.compareAndSet(true, false);
/* 354 */           assert (bool);
/*     */           
/*     */ 
/* 357 */           if ((GlobalEventExecutor.this.taskQueue.isEmpty()) && (GlobalEventExecutor.this.delayedTaskQueue.size() == 1)) {
/*     */             break;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */           if (!GlobalEventExecutor.this.started.compareAndSet(false, true)) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final class PurgeTask
/*     */     implements Runnable
/*     */   {
/*     */     private PurgeTask() {}
/*     */     
/*     */ 
/*     */     public void run()
/*     */     {
/* 383 */       Iterator localIterator = GlobalEventExecutor.this.delayedTaskQueue.iterator();
/* 384 */       while (localIterator.hasNext()) {
/* 385 */         ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)localIterator.next();
/* 386 */         if (localScheduledFutureTask.isCancelled()) {
/* 387 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\GlobalEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */