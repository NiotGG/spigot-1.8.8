/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Delayed;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ final class ScheduledFutureTask<V>
/*     */   extends PromiseTask<V>
/*     */   implements ScheduledFuture<V>
/*     */ {
/*  27 */   private static final AtomicLong nextTaskId = new AtomicLong();
/*  28 */   private static final long START_TIME = System.nanoTime();
/*     */   
/*     */   static long nanoTime() {
/*  31 */     return System.nanoTime() - START_TIME;
/*     */   }
/*     */   
/*     */   static long deadlineNanos(long paramLong) {
/*  35 */     return nanoTime() + paramLong;
/*     */   }
/*     */   
/*  38 */   private final long id = nextTaskId.getAndIncrement();
/*     */   
/*     */   private final Queue<ScheduledFutureTask<?>> delayedTaskQueue;
/*     */   
/*     */   private long deadlineNanos;
/*     */   
/*     */   private final long periodNanos;
/*     */   
/*     */   ScheduledFutureTask(EventExecutor paramEventExecutor, Queue<ScheduledFutureTask<?>> paramQueue, Runnable paramRunnable, V paramV, long paramLong)
/*     */   {
/*  48 */     this(paramEventExecutor, paramQueue, toCallable(paramRunnable, paramV), paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ScheduledFutureTask(EventExecutor paramEventExecutor, Queue<ScheduledFutureTask<?>> paramQueue, Callable<V> paramCallable, long paramLong1, long paramLong2)
/*     */   {
/*  55 */     super(paramEventExecutor, paramCallable);
/*  56 */     if (paramLong2 == 0L) {
/*  57 */       throw new IllegalArgumentException("period: 0 (expected: != 0)");
/*     */     }
/*  59 */     this.delayedTaskQueue = paramQueue;
/*  60 */     this.deadlineNanos = paramLong1;
/*  61 */     this.periodNanos = paramLong2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ScheduledFutureTask(EventExecutor paramEventExecutor, Queue<ScheduledFutureTask<?>> paramQueue, Callable<V> paramCallable, long paramLong)
/*     */   {
/*  68 */     super(paramEventExecutor, paramCallable);
/*  69 */     this.delayedTaskQueue = paramQueue;
/*  70 */     this.deadlineNanos = paramLong;
/*  71 */     this.periodNanos = 0L;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor()
/*     */   {
/*  76 */     return super.executor();
/*     */   }
/*     */   
/*     */   public long deadlineNanos() {
/*  80 */     return this.deadlineNanos;
/*     */   }
/*     */   
/*     */   public long delayNanos() {
/*  84 */     return Math.max(0L, deadlineNanos() - nanoTime());
/*     */   }
/*     */   
/*     */   public long delayNanos(long paramLong) {
/*  88 */     return Math.max(0L, deadlineNanos() - (paramLong - START_TIME));
/*     */   }
/*     */   
/*     */   public long getDelay(TimeUnit paramTimeUnit)
/*     */   {
/*  93 */     return paramTimeUnit.convert(delayNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */   
/*     */   public int compareTo(Delayed paramDelayed)
/*     */   {
/*  98 */     if (this == paramDelayed) {
/*  99 */       return 0;
/*     */     }
/*     */     
/* 102 */     ScheduledFutureTask localScheduledFutureTask = (ScheduledFutureTask)paramDelayed;
/* 103 */     long l = deadlineNanos() - localScheduledFutureTask.deadlineNanos();
/* 104 */     if (l < 0L)
/* 105 */       return -1;
/* 106 */     if (l > 0L)
/* 107 */       return 1;
/* 108 */     if (this.id < localScheduledFutureTask.id)
/* 109 */       return -1;
/* 110 */     if (this.id == localScheduledFutureTask.id) {
/* 111 */       throw new Error();
/*     */     }
/* 113 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/* 119 */     assert (executor().inEventLoop());
/*     */     try {
/* 121 */       if (this.periodNanos == 0L) {
/* 122 */         if (setUncancellableInternal()) {
/* 123 */           Object localObject = this.task.call();
/* 124 */           setSuccessInternal(localObject);
/*     */         }
/*     */         
/*     */       }
/* 128 */       else if (!isCancelled()) {
/* 129 */         this.task.call();
/* 130 */         if (!executor().isShutdown()) {
/* 131 */           long l = this.periodNanos;
/* 132 */           if (l > 0L) {
/* 133 */             this.deadlineNanos += l;
/*     */           } else {
/* 135 */             this.deadlineNanos = (nanoTime() - l);
/*     */           }
/* 137 */           if (!isCancelled()) {
/* 138 */             this.delayedTaskQueue.add(this);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {
/* 144 */       setFailureInternal(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   protected StringBuilder toStringBuilder()
/*     */   {
/* 150 */     StringBuilder localStringBuilder = super.toStringBuilder();
/* 151 */     localStringBuilder.setCharAt(localStringBuilder.length() - 1, ',');
/* 152 */     localStringBuilder.append(" id: ");
/* 153 */     localStringBuilder.append(this.id);
/* 154 */     localStringBuilder.append(", deadline: ");
/* 155 */     localStringBuilder.append(this.deadlineNanos);
/* 156 */     localStringBuilder.append(", period: ");
/* 157 */     localStringBuilder.append(this.periodNanos);
/* 158 */     localStringBuilder.append(')');
/* 159 */     return localStringBuilder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\ScheduledFutureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */