/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.MpscLinkedQueueNode;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.Executors;
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
/*     */ public class HashedWheelTimer
/*     */   implements Timer
/*     */ {
/*  77 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
/*     */   
/*     */ 
/*  80 */   private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = new ResourceLeakDetector(HashedWheelTimer.class, 1, Runtime.getRuntime().availableProcessors() * 4);
/*     */   private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
/*     */   private final ResourceLeak leak;
/*     */   
/*     */   static
/*     */   {
/*  86 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.class, "workerState");
/*     */     
/*  88 */     if (localAtomicIntegerFieldUpdater == null) {
/*  89 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
/*     */     }
/*  91 */     WORKER_STATE_UPDATER = localAtomicIntegerFieldUpdater;
/*     */   }
/*     */   
/*     */ 
/*  95 */   private final Worker worker = new Worker(null);
/*     */   
/*     */   private final Thread workerThread;
/*     */   public static final int WORKER_STATE_INIT = 0;
/*     */   public static final int WORKER_STATE_STARTED = 1;
/*     */   public static final int WORKER_STATE_SHUTDOWN = 2;
/* 101 */   private volatile int workerState = 0;
/*     */   
/*     */   private final long tickDuration;
/*     */   
/*     */   private final HashedWheelBucket[] wheel;
/*     */   private final int mask;
/* 107 */   private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
/* 108 */   private final Queue<HashedWheelTimeout> timeouts = PlatformDependent.newMpscQueue();
/* 109 */   private final Queue<Runnable> cancelledTimeouts = PlatformDependent.newMpscQueue();
/*     */   
/*     */ 
/*     */ 
/*     */   private volatile long startTime;
/*     */   
/*     */ 
/*     */ 
/*     */   public HashedWheelTimer()
/*     */   {
/* 119 */     this(Executors.defaultThreadFactory());
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
/*     */   public HashedWheelTimer(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 133 */     this(Executors.defaultThreadFactory(), paramLong, paramTimeUnit);
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
/*     */   public HashedWheelTimer(long paramLong, TimeUnit paramTimeUnit, int paramInt)
/*     */   {
/* 147 */     this(Executors.defaultThreadFactory(), paramLong, paramTimeUnit, paramInt);
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
/*     */   public HashedWheelTimer(ThreadFactory paramThreadFactory)
/*     */   {
/* 160 */     this(paramThreadFactory, 100L, TimeUnit.MILLISECONDS);
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
/*     */   public HashedWheelTimer(ThreadFactory paramThreadFactory, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 176 */     this(paramThreadFactory, paramLong, paramTimeUnit, 512);
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
/*     */   public HashedWheelTimer(ThreadFactory paramThreadFactory, long paramLong, TimeUnit paramTimeUnit, int paramInt)
/*     */   {
/* 195 */     if (paramThreadFactory == null) {
/* 196 */       throw new NullPointerException("threadFactory");
/*     */     }
/* 198 */     if (paramTimeUnit == null) {
/* 199 */       throw new NullPointerException("unit");
/*     */     }
/* 201 */     if (paramLong <= 0L) {
/* 202 */       throw new IllegalArgumentException("tickDuration must be greater than 0: " + paramLong);
/*     */     }
/* 204 */     if (paramInt <= 0) {
/* 205 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + paramInt);
/*     */     }
/*     */     
/*     */ 
/* 209 */     this.wheel = createWheel(paramInt);
/* 210 */     this.mask = (this.wheel.length - 1);
/*     */     
/*     */ 
/* 213 */     this.tickDuration = paramTimeUnit.toNanos(paramLong);
/*     */     
/*     */ 
/* 216 */     if (this.tickDuration >= Long.MAX_VALUE / this.wheel.length) {
/* 217 */       throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[] { Long.valueOf(paramLong), Long.valueOf(Long.MAX_VALUE / this.wheel.length) }));
/*     */     }
/*     */     
/*     */ 
/* 221 */     this.workerThread = paramThreadFactory.newThread(this.worker);
/*     */     
/* 223 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */   
/*     */   private static HashedWheelBucket[] createWheel(int paramInt) {
/* 227 */     if (paramInt <= 0) {
/* 228 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + paramInt);
/*     */     }
/*     */     
/* 231 */     if (paramInt > 1073741824) {
/* 232 */       throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + paramInt);
/*     */     }
/*     */     
/*     */ 
/* 236 */     paramInt = normalizeTicksPerWheel(paramInt);
/* 237 */     HashedWheelBucket[] arrayOfHashedWheelBucket = new HashedWheelBucket[paramInt];
/* 238 */     for (int i = 0; i < arrayOfHashedWheelBucket.length; i++) {
/* 239 */       arrayOfHashedWheelBucket[i] = new HashedWheelBucket(null);
/*     */     }
/* 241 */     return arrayOfHashedWheelBucket;
/*     */   }
/*     */   
/*     */   private static int normalizeTicksPerWheel(int paramInt) {
/* 245 */     int i = 1;
/* 246 */     while (i < paramInt) {
/* 247 */       i <<= 1;
/*     */     }
/* 249 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 260 */     switch (WORKER_STATE_UPDATER.get(this)) {
/*     */     case 0: 
/* 262 */       if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
/* 263 */         this.workerThread.start();
/*     */       }
/*     */       break;
/*     */     case 1: 
/*     */       break;
/*     */     case 2: 
/* 269 */       throw new IllegalStateException("cannot be started once stopped");
/*     */     default: 
/* 271 */       throw new Error("Invalid WorkerState");
/*     */     }
/*     */     
/*     */     
/* 275 */     while (this.startTime == 0L) {
/*     */       try {
/* 277 */         this.startTimeInitialized.await();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Set<Timeout> stop()
/*     */   {
/* 286 */     if (Thread.currentThread() == this.workerThread) {
/* 287 */       throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 293 */     if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2))
/*     */     {
/* 295 */       WORKER_STATE_UPDATER.set(this, 2);
/*     */       
/* 297 */       if (this.leak != null) {
/* 298 */         this.leak.close();
/*     */       }
/*     */       
/* 301 */       return Collections.emptySet();
/*     */     }
/*     */     
/* 304 */     int i = 0;
/* 305 */     while (this.workerThread.isAlive()) {
/* 306 */       this.workerThread.interrupt();
/*     */       try {
/* 308 */         this.workerThread.join(100L);
/*     */       } catch (InterruptedException localInterruptedException) {
/* 310 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/* 314 */     if (i != 0) {
/* 315 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 318 */     if (this.leak != null) {
/* 319 */       this.leak.close();
/*     */     }
/* 321 */     return this.worker.unprocessedTimeouts();
/*     */   }
/*     */   
/*     */   public Timeout newTimeout(TimerTask paramTimerTask, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 326 */     if (paramTimerTask == null) {
/* 327 */       throw new NullPointerException("task");
/*     */     }
/* 329 */     if (paramTimeUnit == null) {
/* 330 */       throw new NullPointerException("unit");
/*     */     }
/* 332 */     start();
/*     */     
/*     */ 
/*     */ 
/* 336 */     long l = System.nanoTime() + paramTimeUnit.toNanos(paramLong) - this.startTime;
/* 337 */     HashedWheelTimeout localHashedWheelTimeout = new HashedWheelTimeout(this, paramTimerTask, l);
/* 338 */     this.timeouts.add(localHashedWheelTimeout);
/* 339 */     return localHashedWheelTimeout;
/*     */   }
/*     */   
/*     */   private final class Worker implements Runnable {
/* 343 */     private final Set<Timeout> unprocessedTimeouts = new HashSet();
/*     */     private long tick;
/*     */     
/*     */     private Worker() {}
/*     */     
/*     */     public void run()
/*     */     {
/* 350 */       HashedWheelTimer.this.startTime = System.nanoTime();
/* 351 */       if (HashedWheelTimer.this.startTime == 0L)
/*     */       {
/* 353 */         HashedWheelTimer.this.startTime = 1L;
/*     */       }
/*     */       
/*     */ 
/* 357 */       HashedWheelTimer.this.startTimeInitialized.countDown();
/*     */       int i;
/*     */       HashedWheelTimer.HashedWheelBucket localHashedWheelBucket;
/* 360 */       do { long l = waitForNextTick();
/* 361 */         if (l > 0L) {
/* 362 */           i = (int)(this.tick & HashedWheelTimer.this.mask);
/* 363 */           processCancelledTasks();
/* 364 */           localHashedWheelBucket = HashedWheelTimer.this.wheel[i];
/*     */           
/* 366 */           transferTimeoutsToBuckets();
/* 367 */           localHashedWheelBucket.expireTimeouts(l);
/* 368 */           this.tick += 1L;
/*     */         }
/* 370 */       } while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
/*     */       
/*     */ 
/* 373 */       for (localHashedWheelBucket : HashedWheelTimer.this.wheel) {
/* 374 */         localHashedWheelBucket.clearTimeouts(this.unprocessedTimeouts);
/*     */       }
/*     */       for (;;) {
/* 377 */         ??? = (HashedWheelTimer.HashedWheelTimeout)HashedWheelTimer.this.timeouts.poll();
/* 378 */         if (??? == null) {
/*     */           break;
/*     */         }
/* 381 */         if (!((HashedWheelTimer.HashedWheelTimeout)???).isCancelled()) {
/* 382 */           this.unprocessedTimeouts.add(???);
/*     */         }
/*     */       }
/* 385 */       processCancelledTasks();
/*     */     }
/*     */     
/*     */ 
/*     */     private void transferTimeoutsToBuckets()
/*     */     {
/* 391 */       for (int i = 0; i < 100000; i++) {
/* 392 */         HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout = (HashedWheelTimer.HashedWheelTimeout)HashedWheelTimer.this.timeouts.poll();
/* 393 */         if (localHashedWheelTimeout == null) {
/*     */           break;
/*     */         }
/*     */         
/* 397 */         if (localHashedWheelTimeout.state() != 1)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 402 */           long l1 = localHashedWheelTimeout.deadline / HashedWheelTimer.this.tickDuration;
/* 403 */           localHashedWheelTimeout.remainingRounds = ((l1 - this.tick) / HashedWheelTimer.this.wheel.length);
/*     */           
/* 405 */           long l2 = Math.max(l1, this.tick);
/* 406 */           int j = (int)(l2 & HashedWheelTimer.this.mask);
/*     */           
/* 408 */           HashedWheelTimer.HashedWheelBucket localHashedWheelBucket = HashedWheelTimer.this.wheel[j];
/* 409 */           localHashedWheelBucket.addTimeout(localHashedWheelTimeout);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void processCancelledTasks() {
/* 415 */       for (;;) { Runnable localRunnable = (Runnable)HashedWheelTimer.this.cancelledTimeouts.poll();
/* 416 */         if (localRunnable == null) {
/*     */           break;
/*     */         }
/*     */         try
/*     */         {
/* 421 */           localRunnable.run();
/*     */         } catch (Throwable localThrowable) {
/* 423 */           if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 424 */             HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", localThrowable);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private long waitForNextTick()
/*     */     {
/* 437 */       long l1 = HashedWheelTimer.this.tickDuration * (this.tick + 1L);
/*     */       for (;;)
/*     */       {
/* 440 */         long l2 = System.nanoTime() - HashedWheelTimer.this.startTime;
/* 441 */         long l3 = (l1 - l2 + 999999L) / 1000000L;
/*     */         
/* 443 */         if (l3 <= 0L) {
/* 444 */           if (l2 == Long.MIN_VALUE) {
/* 445 */             return -9223372036854775807L;
/*     */           }
/* 447 */           return l2;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 456 */         if (PlatformDependent.isWindows()) {
/* 457 */           l3 = l3 / 10L * 10L;
/*     */         }
/*     */         try
/*     */         {
/* 461 */           Thread.sleep(l3);
/*     */         } catch (InterruptedException localInterruptedException) {
/* 463 */           if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
/* 464 */             return Long.MIN_VALUE;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 471 */     public Set<Timeout> unprocessedTimeouts() { return Collections.unmodifiableSet(this.unprocessedTimeouts); }
/*     */   }
/*     */   
/*     */   private static final class HashedWheelTimeout extends MpscLinkedQueueNode<Timeout> implements Timeout {
/*     */     private static final int ST_INIT = 0;
/*     */     private static final int ST_CANCELLED = 1;
/*     */     private static final int ST_EXPIRED = 2;
/*     */     private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER;
/*     */     private final HashedWheelTimer timer;
/*     */     private final TimerTask task;
/*     */     private final long deadline;
/*     */     
/*     */     static {
/* 484 */       AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimeout.class, "state");
/*     */       
/* 486 */       if (localAtomicIntegerFieldUpdater == null) {
/* 487 */         localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
/*     */       }
/* 489 */       STATE_UPDATER = localAtomicIntegerFieldUpdater;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 496 */     private volatile int state = 0;
/*     */     
/*     */ 
/*     */     long remainingRounds;
/*     */     
/*     */ 
/*     */     HashedWheelTimeout next;
/*     */     
/*     */ 
/*     */     HashedWheelTimeout prev;
/*     */     
/*     */     HashedWheelTimer.HashedWheelBucket bucket;
/*     */     
/*     */ 
/*     */     HashedWheelTimeout(HashedWheelTimer paramHashedWheelTimer, TimerTask paramTimerTask, long paramLong)
/*     */     {
/* 512 */       this.timer = paramHashedWheelTimer;
/* 513 */       this.task = paramTimerTask;
/* 514 */       this.deadline = paramLong;
/*     */     }
/*     */     
/*     */     public Timer timer()
/*     */     {
/* 519 */       return this.timer;
/*     */     }
/*     */     
/*     */     public TimerTask task()
/*     */     {
/* 524 */       return this.task;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean cancel()
/*     */     {
/* 530 */       if (!compareAndSetState(0, 1)) {
/* 531 */         return false;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 540 */       this.timer.cancelledTimeouts.add(new Runnable()
/*     */       {
/*     */         public void run() {
/* 543 */           HashedWheelTimer.HashedWheelBucket localHashedWheelBucket = HashedWheelTimer.HashedWheelTimeout.this.bucket;
/* 544 */           if (localHashedWheelBucket != null) {
/* 545 */             localHashedWheelBucket.remove(HashedWheelTimer.HashedWheelTimeout.this);
/*     */           }
/*     */         }
/* 548 */       });
/* 549 */       return true;
/*     */     }
/*     */     
/*     */     public boolean compareAndSetState(int paramInt1, int paramInt2) {
/* 553 */       return STATE_UPDATER.compareAndSet(this, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */     public int state() {
/* 557 */       return this.state;
/*     */     }
/*     */     
/*     */     public boolean isCancelled()
/*     */     {
/* 562 */       return state() == 1;
/*     */     }
/*     */     
/*     */     public boolean isExpired()
/*     */     {
/* 567 */       return state() == 2;
/*     */     }
/*     */     
/*     */     public HashedWheelTimeout value()
/*     */     {
/* 572 */       return this;
/*     */     }
/*     */     
/*     */     public void expire() {
/* 576 */       if (!compareAndSetState(0, 2)) {
/* 577 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 581 */         this.task.run(this);
/*     */       } catch (Throwable localThrowable) {
/* 583 */         if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 584 */           HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', localThrowable);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 591 */       long l1 = System.nanoTime();
/* 592 */       long l2 = this.deadline - l1 + this.timer.startTime;
/*     */       
/* 594 */       StringBuilder localStringBuilder = new StringBuilder(192);
/* 595 */       localStringBuilder.append(StringUtil.simpleClassName(this));
/* 596 */       localStringBuilder.append('(');
/*     */       
/* 598 */       localStringBuilder.append("deadline: ");
/* 599 */       if (l2 > 0L) {
/* 600 */         localStringBuilder.append(l2);
/* 601 */         localStringBuilder.append(" ns later");
/* 602 */       } else if (l2 < 0L) {
/* 603 */         localStringBuilder.append(-l2);
/* 604 */         localStringBuilder.append(" ns ago");
/*     */       } else {
/* 606 */         localStringBuilder.append("now");
/*     */       }
/*     */       
/* 609 */       if (isCancelled()) {
/* 610 */         localStringBuilder.append(", cancelled");
/*     */       }
/*     */       
/* 613 */       localStringBuilder.append(", task: ");
/* 614 */       localStringBuilder.append(task());
/*     */       
/* 616 */       return ')';
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class HashedWheelBucket
/*     */   {
/*     */     private HashedWheelTimer.HashedWheelTimeout head;
/*     */     
/*     */ 
/*     */     private HashedWheelTimer.HashedWheelTimeout tail;
/*     */     
/*     */ 
/*     */ 
/*     */     public void addTimeout(HashedWheelTimer.HashedWheelTimeout paramHashedWheelTimeout)
/*     */     {
/* 634 */       assert (paramHashedWheelTimeout.bucket == null);
/* 635 */       paramHashedWheelTimeout.bucket = this;
/* 636 */       if (this.head == null) {
/* 637 */         this.head = (this.tail = paramHashedWheelTimeout);
/*     */       } else {
/* 639 */         this.tail.next = paramHashedWheelTimeout;
/* 640 */         paramHashedWheelTimeout.prev = this.tail;
/* 641 */         this.tail = paramHashedWheelTimeout;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void expireTimeouts(long paramLong)
/*     */     {
/* 649 */       Object localObject = this.head;
/*     */       
/*     */ 
/* 652 */       while (localObject != null) {
/* 653 */         int i = 0;
/* 654 */         if (((HashedWheelTimer.HashedWheelTimeout)localObject).remainingRounds <= 0L) {
/* 655 */           if (HashedWheelTimer.HashedWheelTimeout.access$800((HashedWheelTimer.HashedWheelTimeout)localObject) <= paramLong) {
/* 656 */             ((HashedWheelTimer.HashedWheelTimeout)localObject).expire();
/*     */           }
/*     */           else {
/* 659 */             throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", new Object[] { Long.valueOf(HashedWheelTimer.HashedWheelTimeout.access$800((HashedWheelTimer.HashedWheelTimeout)localObject)), Long.valueOf(paramLong) }));
/*     */           }
/*     */           
/* 662 */           i = 1;
/* 663 */         } else if (((HashedWheelTimer.HashedWheelTimeout)localObject).isCancelled()) {
/* 664 */           i = 1;
/*     */         } else {
/* 666 */           localObject.remainingRounds -= 1L;
/*     */         }
/*     */         
/* 669 */         HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout = ((HashedWheelTimer.HashedWheelTimeout)localObject).next;
/* 670 */         if (i != 0) {
/* 671 */           remove((HashedWheelTimer.HashedWheelTimeout)localObject);
/*     */         }
/* 673 */         localObject = localHashedWheelTimeout;
/*     */       }
/*     */     }
/*     */     
/*     */     public void remove(HashedWheelTimer.HashedWheelTimeout paramHashedWheelTimeout) {
/* 678 */       HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout = paramHashedWheelTimeout.next;
/*     */       
/* 680 */       if (paramHashedWheelTimeout.prev != null) {
/* 681 */         paramHashedWheelTimeout.prev.next = localHashedWheelTimeout;
/*     */       }
/* 683 */       if (paramHashedWheelTimeout.next != null) {
/* 684 */         paramHashedWheelTimeout.next.prev = paramHashedWheelTimeout.prev;
/*     */       }
/*     */       
/* 687 */       if (paramHashedWheelTimeout == this.head)
/*     */       {
/* 689 */         if (paramHashedWheelTimeout == this.tail) {
/* 690 */           this.tail = null;
/* 691 */           this.head = null;
/*     */         } else {
/* 693 */           this.head = localHashedWheelTimeout;
/*     */         }
/* 695 */       } else if (paramHashedWheelTimeout == this.tail)
/*     */       {
/* 697 */         this.tail = paramHashedWheelTimeout.prev;
/*     */       }
/*     */       
/* 700 */       paramHashedWheelTimeout.prev = null;
/* 701 */       paramHashedWheelTimeout.next = null;
/* 702 */       paramHashedWheelTimeout.bucket = null;
/*     */     }
/*     */     
/*     */ 
/*     */     public void clearTimeouts(Set<Timeout> paramSet)
/*     */     {
/*     */       for (;;)
/*     */       {
/* 710 */         HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout = pollTimeout();
/* 711 */         if (localHashedWheelTimeout == null) {
/* 712 */           return;
/*     */         }
/* 714 */         if ((!localHashedWheelTimeout.isExpired()) && (!localHashedWheelTimeout.isCancelled()))
/*     */         {
/*     */ 
/* 717 */           paramSet.add(localHashedWheelTimeout); }
/*     */       }
/*     */     }
/*     */     
/*     */     private HashedWheelTimer.HashedWheelTimeout pollTimeout() {
/* 722 */       HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout1 = this.head;
/* 723 */       if (localHashedWheelTimeout1 == null) {
/* 724 */         return null;
/*     */       }
/* 726 */       HashedWheelTimer.HashedWheelTimeout localHashedWheelTimeout2 = localHashedWheelTimeout1.next;
/* 727 */       if (localHashedWheelTimeout2 == null) {
/* 728 */         this.tail = (this.head = null);
/*     */       } else {
/* 730 */         this.head = localHashedWheelTimeout2;
/* 731 */         localHashedWheelTimeout2.prev = null;
/*     */       }
/*     */       
/*     */ 
/* 735 */       localHashedWheelTimeout1.next = null;
/* 736 */       localHashedWheelTimeout1.prev = null;
/* 737 */       localHashedWheelTimeout1.bucket = null;
/* 738 */       return localHashedWheelTimeout1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\HashedWheelTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */