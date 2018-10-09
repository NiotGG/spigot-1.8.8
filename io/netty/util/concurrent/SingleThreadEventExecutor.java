/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SingleThreadEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*     */   private static final InternalLogger logger;
/*     */   private static final int ST_NOT_STARTED = 1;
/*     */   private static final int ST_STARTED = 2;
/*     */   private static final int ST_SHUTTING_DOWN = 3;
/*     */   private static final int ST_SHUTDOWN = 4;
/*     */   private static final int ST_TERMINATED = 5;
/*     */   private static final Runnable WAKEUP_TASK;
/*     */   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
/*     */   private final EventExecutorGroup parent;
/*     */   private final Queue<Runnable> taskQueue;
/*     */   
/*     */   static
/*     */   {
/*  45 */     logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */     WAKEUP_TASK = new Runnable()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       public void run() {}
/*     */ 
/*     */ 
/*     */ 
/*  63 */     };
/*  64 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, "state");
/*     */     
/*  66 */     if (localAtomicIntegerFieldUpdater == null) {
/*  67 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
/*     */     }
/*  69 */     STATE_UPDATER = localAtomicIntegerFieldUpdater;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  74 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue();
/*     */   
/*     */   private final Thread thread;
/*  77 */   private final Semaphore threadLock = new Semaphore(0);
/*  78 */   private final Set<Runnable> shutdownHooks = new LinkedHashSet();
/*     */   
/*     */   private final boolean addTaskWakesUp;
/*     */   
/*     */   private long lastExecutionTime;
/*  83 */   private volatile int state = 1;
/*     */   
/*     */   private volatile long gracefulShutdownQuietPeriod;
/*     */   
/*     */   private volatile long gracefulShutdownTimeout;
/*     */   
/*     */   private long gracefulShutdownStartTime;
/*  90 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
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
/*     */   protected SingleThreadEventExecutor(EventExecutorGroup paramEventExecutorGroup, ThreadFactory paramThreadFactory, boolean paramBoolean)
/*     */   {
/* 103 */     if (paramThreadFactory == null) {
/* 104 */       throw new NullPointerException("threadFactory");
/*     */     }
/*     */     
/* 107 */     this.parent = paramEventExecutorGroup;
/* 108 */     this.addTaskWakesUp = paramBoolean;
/*     */     
/* 110 */     this.thread = paramThreadFactory.newThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 113 */         int i = 0;
/* 114 */         SingleThreadEventExecutor.this.updateLastExecutionTime();
/*     */         try {
/* 116 */           SingleThreadEventExecutor.this.run();
/* 117 */           i = 1;
/*     */         } catch (Throwable localThrowable) { int j;
/* 119 */           SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", localThrowable);
/*     */         } finally {
/*     */           for (;;) { int k;
/* 122 */             int m = SingleThreadEventExecutor.STATE_UPDATER.get(SingleThreadEventExecutor.this);
/* 123 */             if ((m >= 3) || (SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, m, 3))) {
/*     */               break;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 129 */           if ((i != 0) && (SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L)) {
/* 130 */             SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/*     */             for (;;)
/*     */             {
/* 139 */               if (SingleThreadEventExecutor.this.confirmShutdown()) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           } finally {
/*     */             try {
/* 145 */               SingleThreadEventExecutor.this.cleanup();
/*     */             } finally {
/* 147 */               SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
/* 148 */               SingleThreadEventExecutor.this.threadLock.release();
/* 149 */               if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
/* 150 */                 SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
/*     */               }
/*     */               
/*     */ 
/*     */ 
/* 155 */               SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/* 161 */     });
/* 162 */     this.taskQueue = newTaskQueue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Queue<Runnable> newTaskQueue()
/*     */   {
/* 172 */     return new LinkedBlockingQueue();
/*     */   }
/*     */   
/*     */   public EventExecutorGroup parent()
/*     */   {
/* 177 */     return this.parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void interruptThread()
/*     */   {
/* 184 */     this.thread.interrupt();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Runnable pollTask()
/*     */   {
/* 191 */     assert (inEventLoop());
/*     */     Runnable localRunnable;
/* 193 */     do { localRunnable = (Runnable)this.taskQueue.poll();
/* 194 */     } while (localRunnable == WAKEUP_TASK);
/*     */     
/*     */ 
/* 197 */     return localRunnable;
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
/*     */   protected Runnable takeTask()
/*     */   {
/* 211 */     assert (inEventLoop());
/* 212 */     if (!(this.taskQueue instanceof BlockingQueue)) {
/* 213 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/* 216 */     BlockingQueue localBlockingQueue = (BlockingQueue)this.taskQueue;
/*     */     for (;;) {
/* 218 */       ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/* 219 */       if (localScheduledFutureTask == null) {
/* 220 */         Runnable localRunnable1 = null;
/*     */         try {
/* 222 */           localRunnable1 = (Runnable)localBlockingQueue.take();
/* 223 */           if (localRunnable1 == WAKEUP_TASK) {
/* 224 */             localRunnable1 = null;
/*     */           }
/*     */         }
/*     */         catch (InterruptedException localInterruptedException1) {}
/*     */         
/* 229 */         return localRunnable1;
/*     */       }
/* 231 */       long l = localScheduledFutureTask.delayNanos();
/* 232 */       Runnable localRunnable2 = null;
/* 233 */       if (l > 0L) {
/*     */         try {
/* 235 */           localRunnable2 = (Runnable)localBlockingQueue.poll(l, TimeUnit.NANOSECONDS);
/*     */         } catch (InterruptedException localInterruptedException2) {
/* 237 */           return null;
/*     */         }
/*     */       }
/* 240 */       if (localRunnable2 == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 245 */         fetchFromDelayedQueue();
/* 246 */         localRunnable2 = (Runnable)localBlockingQueue.poll();
/*     */       }
/*     */       
/* 249 */       if (localRunnable2 != null) {
/* 250 */         return localRunnable2;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fetchFromDelayedQueue()
/*     */   {
/* 257 */     long l = 0L;
/*     */     for (;;) {
/* 259 */       ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/* 260 */       if (localScheduledFutureTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 264 */       if (l == 0L) {
/* 265 */         l = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 268 */       if (localScheduledFutureTask.deadlineNanos() > l) break;
/* 269 */       this.delayedTaskQueue.remove();
/* 270 */       this.taskQueue.add(localScheduledFutureTask);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Runnable peekTask()
/*     */   {
/* 281 */     assert (inEventLoop());
/* 282 */     return (Runnable)this.taskQueue.peek();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean hasTasks()
/*     */   {
/* 289 */     assert (inEventLoop());
/* 290 */     return !this.taskQueue.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean hasScheduledTasks()
/*     */   {
/* 298 */     assert (inEventLoop());
/* 299 */     ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/* 300 */     return (localScheduledFutureTask != null) && (localScheduledFutureTask.deadlineNanos() <= ScheduledFutureTask.nanoTime());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int pendingTasks()
/*     */   {
/* 310 */     return this.taskQueue.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addTask(Runnable paramRunnable)
/*     */   {
/* 318 */     if (paramRunnable == null) {
/* 319 */       throw new NullPointerException("task");
/*     */     }
/* 321 */     if (isShutdown()) {
/* 322 */       reject();
/*     */     }
/* 324 */     this.taskQueue.add(paramRunnable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean removeTask(Runnable paramRunnable)
/*     */   {
/* 331 */     if (paramRunnable == null) {
/* 332 */       throw new NullPointerException("task");
/*     */     }
/* 334 */     return this.taskQueue.remove(paramRunnable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean runAllTasks()
/*     */   {
/* 343 */     fetchFromDelayedQueue();
/* 344 */     Runnable localRunnable = pollTask();
/* 345 */     if (localRunnable == null) {
/* 346 */       return false;
/*     */     }
/*     */     do
/*     */     {
/*     */       try {
/* 351 */         localRunnable.run();
/*     */       } catch (Throwable localThrowable) {
/* 353 */         logger.warn("A task raised an exception.", localThrowable);
/*     */       }
/*     */       
/* 356 */       localRunnable = pollTask();
/* 357 */     } while (localRunnable != null);
/* 358 */     this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/* 359 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean runAllTasks(long paramLong)
/*     */   {
/* 369 */     fetchFromDelayedQueue();
/* 370 */     Runnable localRunnable = pollTask();
/* 371 */     if (localRunnable == null) {
/* 372 */       return false;
/*     */     }
/*     */     
/* 375 */     long l1 = ScheduledFutureTask.nanoTime() + paramLong;
/* 376 */     long l2 = 0L;
/*     */     do
/*     */     {
/*     */       try {
/* 380 */         localRunnable.run();
/*     */       } catch (Throwable localThrowable) {
/* 382 */         logger.warn("A task raised an exception.", localThrowable);
/*     */       }
/*     */       
/* 385 */       l2 += 1L;
/*     */       
/*     */ 
/*     */ 
/* 389 */       if ((l2 & 0x3F) == 0L) {
/* 390 */         l3 = ScheduledFutureTask.nanoTime();
/* 391 */         if (l3 >= l1) {
/*     */           break;
/*     */         }
/*     */       }
/*     */       
/* 396 */       localRunnable = pollTask();
/* 397 */     } while (localRunnable != null);
/* 398 */     long l3 = ScheduledFutureTask.nanoTime();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 403 */     this.lastExecutionTime = l3;
/* 404 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected long delayNanos(long paramLong)
/*     */   {
/* 411 */     ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)this.delayedTaskQueue.peek();
/* 412 */     if (localScheduledFutureTask == null) {
/* 413 */       return SCHEDULE_PURGE_INTERVAL;
/*     */     }
/*     */     
/* 416 */     return localScheduledFutureTask.delayNanos(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateLastExecutionTime()
/*     */   {
/* 427 */     this.lastExecutionTime = ScheduledFutureTask.nanoTime();
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
/*     */   protected void wakeup(boolean paramBoolean)
/*     */   {
/* 443 */     if ((!paramBoolean) || (STATE_UPDATER.get(this) == 3)) {
/* 444 */       this.taskQueue.add(WAKEUP_TASK);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean inEventLoop(Thread paramThread)
/*     */   {
/* 450 */     return paramThread == this.thread;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addShutdownHook(final Runnable paramRunnable)
/*     */   {
/* 457 */     if (inEventLoop()) {
/* 458 */       this.shutdownHooks.add(paramRunnable);
/*     */     } else {
/* 460 */       execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 463 */           SingleThreadEventExecutor.this.shutdownHooks.add(paramRunnable);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeShutdownHook(final Runnable paramRunnable)
/*     */   {
/* 473 */     if (inEventLoop()) {
/* 474 */       this.shutdownHooks.remove(paramRunnable);
/*     */     } else {
/* 476 */       execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 479 */           SingleThreadEventExecutor.this.shutdownHooks.remove(paramRunnable);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean runShutdownHooks() {
/* 486 */     boolean bool = false;
/*     */     
/* 488 */     while (!this.shutdownHooks.isEmpty()) {
/* 489 */       ArrayList localArrayList = new ArrayList(this.shutdownHooks);
/* 490 */       this.shutdownHooks.clear();
/* 491 */       for (Runnable localRunnable : localArrayList) {
/*     */         try {
/* 493 */           localRunnable.run();
/*     */         } catch (Throwable localThrowable) {
/* 495 */           logger.warn("Shutdown hook raised an exception.", localThrowable);
/*     */         } finally {
/* 497 */           bool = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 502 */     if (bool) {
/* 503 */       this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 506 */     return bool;
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 511 */     if (paramLong1 < 0L) {
/* 512 */       throw new IllegalArgumentException("quietPeriod: " + paramLong1 + " (expected >= 0)");
/*     */     }
/* 514 */     if (paramLong2 < paramLong1) {
/* 515 */       throw new IllegalArgumentException("timeout: " + paramLong2 + " (expected >= quietPeriod (" + paramLong1 + "))");
/*     */     }
/*     */     
/* 518 */     if (paramTimeUnit == null) {
/* 519 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 522 */     if (isShuttingDown()) {
/* 523 */       return terminationFuture();
/*     */     }
/*     */     
/* 526 */     boolean bool = inEventLoop();
/*     */     int i;
/*     */     int j;
/*     */     for (;;) {
/* 530 */       if (isShuttingDown()) {
/* 531 */         return terminationFuture();
/*     */       }
/*     */       
/* 534 */       i = 1;
/* 535 */       j = STATE_UPDATER.get(this);
/* 536 */       int k; if (bool) {
/* 537 */         k = 3;
/*     */       } else {
/* 539 */         switch (j) {
/*     */         case 1: 
/*     */         case 2: 
/* 542 */           k = 3;
/* 543 */           break;
/*     */         default: 
/* 545 */           k = j;
/* 546 */           i = 0;
/*     */         }
/*     */       }
/* 549 */       if (STATE_UPDATER.compareAndSet(this, j, k)) {
/*     */         break;
/*     */       }
/*     */     }
/* 553 */     this.gracefulShutdownQuietPeriod = paramTimeUnit.toNanos(paramLong1);
/* 554 */     this.gracefulShutdownTimeout = paramTimeUnit.toNanos(paramLong2);
/*     */     
/* 556 */     if (j == 1) {
/* 557 */       this.thread.start();
/*     */     }
/*     */     
/* 560 */     if (i != 0) {
/* 561 */       wakeup(bool);
/*     */     }
/*     */     
/* 564 */     return terminationFuture();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/* 569 */     return this.terminationFuture;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown()
/*     */   {
/* 575 */     if (isShutdown()) {
/* 576 */       return;
/*     */     }
/*     */     
/* 579 */     boolean bool = inEventLoop();
/*     */     int i;
/*     */     int j;
/*     */     for (;;) {
/* 583 */       if (isShuttingDown()) {
/* 584 */         return;
/*     */       }
/*     */       
/* 587 */       i = 1;
/* 588 */       j = STATE_UPDATER.get(this);
/* 589 */       int k; if (bool) {
/* 590 */         k = 4;
/*     */       } else {
/* 592 */         switch (j) {
/*     */         case 1: 
/*     */         case 2: 
/*     */         case 3: 
/* 596 */           k = 4;
/* 597 */           break;
/*     */         default: 
/* 599 */           k = j;
/* 600 */           i = 0;
/*     */         }
/*     */       }
/* 603 */       if (STATE_UPDATER.compareAndSet(this, j, k)) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 608 */     if (j == 1) {
/* 609 */       this.thread.start();
/*     */     }
/*     */     
/* 612 */     if (i != 0) {
/* 613 */       wakeup(bool);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/* 619 */     return STATE_UPDATER.get(this) >= 3;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/* 624 */     return STATE_UPDATER.get(this) >= 4;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/* 629 */     return STATE_UPDATER.get(this) == 5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean confirmShutdown()
/*     */   {
/* 636 */     if (!isShuttingDown()) {
/* 637 */       return false;
/*     */     }
/*     */     
/* 640 */     if (!inEventLoop()) {
/* 641 */       throw new IllegalStateException("must be invoked from an event loop");
/*     */     }
/*     */     
/* 644 */     cancelDelayedTasks();
/*     */     
/* 646 */     if (this.gracefulShutdownStartTime == 0L) {
/* 647 */       this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 650 */     if ((runAllTasks()) || (runShutdownHooks())) {
/* 651 */       if (isShutdown())
/*     */       {
/* 653 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 657 */       wakeup(true);
/* 658 */       return false;
/*     */     }
/*     */     
/* 661 */     long l = ScheduledFutureTask.nanoTime();
/*     */     
/* 663 */     if ((isShutdown()) || (l - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout)) {
/* 664 */       return true;
/*     */     }
/*     */     
/* 667 */     if (l - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod)
/*     */     {
/*     */ 
/* 670 */       wakeup(true);
/*     */       try {
/* 672 */         Thread.sleep(100L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */       
/*     */ 
/* 677 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 682 */     return true;
/*     */   }
/*     */   
/*     */   private void cancelDelayedTasks() {
/* 686 */     if (this.delayedTaskQueue.isEmpty()) {
/* 687 */       return;
/*     */     }
/*     */     
/* 690 */     ScheduledFutureTask[] arrayOfScheduledFutureTask1 = (ScheduledFutureTask[])this.delayedTaskQueue.toArray(new ScheduledFutureTask[this.delayedTaskQueue.size()]);
/*     */     
/*     */ 
/* 693 */     for (ScheduledFutureTask localScheduledFutureTask : arrayOfScheduledFutureTask1) {
/* 694 */       localScheduledFutureTask.cancel(false);
/*     */     }
/*     */     
/* 697 */     this.delayedTaskQueue.clear();
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
/*     */   {
/* 702 */     if (paramTimeUnit == null) {
/* 703 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 706 */     if (inEventLoop()) {
/* 707 */       throw new IllegalStateException("cannot await termination of the current thread");
/*     */     }
/*     */     
/* 710 */     if (this.threadLock.tryAcquire(paramLong, paramTimeUnit)) {
/* 711 */       this.threadLock.release();
/*     */     }
/*     */     
/* 714 */     return isTerminated();
/*     */   }
/*     */   
/*     */   public void execute(Runnable paramRunnable)
/*     */   {
/* 719 */     if (paramRunnable == null) {
/* 720 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 723 */     boolean bool = inEventLoop();
/* 724 */     if (bool) {
/* 725 */       addTask(paramRunnable);
/*     */     } else {
/* 727 */       startThread();
/* 728 */       addTask(paramRunnable);
/* 729 */       if ((isShutdown()) && (removeTask(paramRunnable))) {
/* 730 */         reject();
/*     */       }
/*     */     }
/*     */     
/* 734 */     if ((!this.addTaskWakesUp) && (wakesUpForTask(paramRunnable))) {
/* 735 */       wakeup(bool);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean wakesUpForTask(Runnable paramRunnable)
/*     */   {
/* 741 */     return true;
/*     */   }
/*     */   
/*     */   protected static void reject() {
/* 745 */     throw new RejectedExecutionException("event executor terminated");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 750 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 754 */     if (paramRunnable == null) {
/* 755 */       throw new NullPointerException("command");
/*     */     }
/* 757 */     if (paramTimeUnit == null) {
/* 758 */       throw new NullPointerException("unit");
/*     */     }
/* 760 */     if (paramLong < 0L) {
/* 761 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong) }));
/*     */     }
/*     */     
/* 764 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, paramRunnable, null, ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong))));
/*     */   }
/*     */   
/*     */ 
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 770 */     if (paramCallable == null) {
/* 771 */       throw new NullPointerException("callable");
/*     */     }
/* 773 */     if (paramTimeUnit == null) {
/* 774 */       throw new NullPointerException("unit");
/*     */     }
/* 776 */     if (paramLong < 0L) {
/* 777 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong) }));
/*     */     }
/*     */     
/* 780 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, paramCallable, ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong))));
/*     */   }
/*     */   
/*     */ 
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 786 */     if (paramRunnable == null) {
/* 787 */       throw new NullPointerException("command");
/*     */     }
/* 789 */     if (paramTimeUnit == null) {
/* 790 */       throw new NullPointerException("unit");
/*     */     }
/* 792 */     if (paramLong1 < 0L) {
/* 793 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong1) }));
/*     */     }
/*     */     
/* 796 */     if (paramLong2 <= 0L) {
/* 797 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(paramLong2) }));
/*     */     }
/*     */     
/*     */ 
/* 801 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(paramRunnable, null), ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong1)), paramTimeUnit.toNanos(paramLong2)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 808 */     if (paramRunnable == null) {
/* 809 */       throw new NullPointerException("command");
/*     */     }
/* 811 */     if (paramTimeUnit == null) {
/* 812 */       throw new NullPointerException("unit");
/*     */     }
/* 814 */     if (paramLong1 < 0L) {
/* 815 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(paramLong1) }));
/*     */     }
/*     */     
/* 818 */     if (paramLong2 <= 0L) {
/* 819 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(paramLong2) }));
/*     */     }
/*     */     
/*     */ 
/* 823 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(paramRunnable, null), ScheduledFutureTask.deadlineNanos(paramTimeUnit.toNanos(paramLong1)), -paramTimeUnit.toNanos(paramLong2)));
/*     */   }
/*     */   
/*     */ 
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> paramScheduledFutureTask)
/*     */   {
/* 829 */     if (paramScheduledFutureTask == null) {
/* 830 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 833 */     if (inEventLoop()) {
/* 834 */       this.delayedTaskQueue.add(paramScheduledFutureTask);
/*     */     } else {
/* 836 */       execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 839 */           SingleThreadEventExecutor.this.delayedTaskQueue.add(paramScheduledFutureTask);
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 844 */     return paramScheduledFutureTask;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 848 */     if ((STATE_UPDATER.get(this) == 1) && 
/* 849 */       (STATE_UPDATER.compareAndSet(this, 1, 2))) {
/* 850 */       this.delayedTaskQueue.add(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(null), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL));
/*     */       
/*     */ 
/* 853 */       this.thread.start(); } }
/*     */   
/*     */   protected abstract void run();
/*     */   
/*     */   protected void cleanup() {}
/*     */   
/*     */   private final class PurgeTask implements Runnable { private PurgeTask() {}
/*     */     
/* 861 */     public void run() { Iterator localIterator = SingleThreadEventExecutor.this.delayedTaskQueue.iterator();
/* 862 */       while (localIterator.hasNext()) {
/* 863 */         ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)localIterator.next();
/* 864 */         if (localScheduledFutureTask.isCancelled()) {
/* 865 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\SingleThreadEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */