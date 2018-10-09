/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.Signal;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class DefaultPromise<V>
/*     */   extends AbstractFuture<V>
/*     */   implements Promise<V>
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
/*  35 */   private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
/*     */   
/*     */   private static final int MAX_LISTENER_STACK_DEPTH = 8;
/*     */   
/*  39 */   private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class.getName() + ".SUCCESS");
/*  40 */   private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class.getName() + ".UNCANCELLABLE");
/*  41 */   private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(new CancellationException());
/*     */   private final EventExecutor executor;
/*     */   
/*  44 */   static { CANCELLATION_CAUSE_HOLDER.cause.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE); }
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
/*     */   public DefaultPromise(EventExecutor paramEventExecutor)
/*     */   {
/*  75 */     if (paramEventExecutor == null) {
/*  76 */       throw new NullPointerException("executor");
/*     */     }
/*  78 */     this.executor = paramEventExecutor;
/*     */   }
/*     */   
/*     */   protected DefaultPromise()
/*     */   {
/*  83 */     this.executor = null;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor() {
/*  87 */     return this.executor;
/*     */   }
/*     */   
/*     */   public boolean isCancelled()
/*     */   {
/*  92 */     return isCancelled0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isCancelled0(Object paramObject) {
/*  96 */     return ((paramObject instanceof CauseHolder)) && ((((CauseHolder)paramObject).cause instanceof CancellationException));
/*     */   }
/*     */   
/*     */   public boolean isCancellable()
/*     */   {
/* 101 */     return this.result == null;
/*     */   }
/*     */   
/*     */   public boolean isDone()
/*     */   {
/* 106 */     return isDone0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isDone0(Object paramObject) {
/* 110 */     return (paramObject != null) && (paramObject != UNCANCELLABLE);
/*     */   }
/*     */   
/*     */   public boolean isSuccess()
/*     */   {
/* 115 */     Object localObject = this.result;
/* 116 */     if ((localObject == null) || (localObject == UNCANCELLABLE)) {
/* 117 */       return false;
/*     */     }
/* 119 */     return !(localObject instanceof CauseHolder);
/*     */   }
/*     */   
/*     */   public Throwable cause()
/*     */   {
/* 124 */     Object localObject = this.result;
/* 125 */     if ((localObject instanceof CauseHolder)) {
/* 126 */       return ((CauseHolder)localObject).cause;
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/* 133 */     if (paramGenericFutureListener == null) {
/* 134 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 137 */     if (isDone()) {
/* 138 */       notifyLateListener(paramGenericFutureListener);
/* 139 */       return this;
/*     */     }
/*     */     
/* 142 */     synchronized (this) {
/* 143 */       if (!isDone()) {
/* 144 */         if (this.listeners == null) {
/* 145 */           this.listeners = paramGenericFutureListener;
/*     */         }
/* 147 */         else if ((this.listeners instanceof DefaultFutureListeners)) {
/* 148 */           ((DefaultFutureListeners)this.listeners).add(paramGenericFutureListener);
/*     */         } else {
/* 150 */           GenericFutureListener localGenericFutureListener = (GenericFutureListener)this.listeners;
/*     */           
/* 152 */           this.listeners = new DefaultFutureListeners(localGenericFutureListener, paramGenericFutureListener);
/*     */         }
/*     */         
/* 155 */         return this;
/*     */       }
/*     */     }
/*     */     
/* 159 */     notifyLateListener(paramGenericFutureListener);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/* 165 */     if (paramVarArgs == null) {
/* 166 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 169 */     for (GenericFutureListener<? extends Future<? super V>> localGenericFutureListener : paramVarArgs) {
/* 170 */       if (localGenericFutureListener == null) {
/*     */         break;
/*     */       }
/* 173 */       addListener(localGenericFutureListener);
/*     */     }
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/* 180 */     if (paramGenericFutureListener == null) {
/* 181 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 184 */     if (isDone()) {
/* 185 */       return this;
/*     */     }
/*     */     
/* 188 */     synchronized (this) {
/* 189 */       if (!isDone()) {
/* 190 */         if ((this.listeners instanceof DefaultFutureListeners)) {
/* 191 */           ((DefaultFutureListeners)this.listeners).remove(paramGenericFutureListener);
/* 192 */         } else if (this.listeners == paramGenericFutureListener) {
/* 193 */           this.listeners = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/* 203 */     if (paramVarArgs == null) {
/* 204 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 207 */     for (GenericFutureListener<? extends Future<? super V>> localGenericFutureListener : paramVarArgs) {
/* 208 */       if (localGenericFutureListener == null) {
/*     */         break;
/*     */       }
/* 211 */       removeListener(localGenericFutureListener);
/*     */     }
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   public Promise<V> sync() throws InterruptedException
/*     */   {
/* 218 */     await();
/* 219 */     rethrowIfFailed();
/* 220 */     return this;
/*     */   }
/*     */   
/*     */   public Promise<V> syncUninterruptibly()
/*     */   {
/* 225 */     awaitUninterruptibly();
/* 226 */     rethrowIfFailed();
/* 227 */     return this;
/*     */   }
/*     */   
/*     */   private void rethrowIfFailed() {
/* 231 */     Throwable localThrowable = cause();
/* 232 */     if (localThrowable == null) {
/* 233 */       return;
/*     */     }
/*     */     
/* 236 */     PlatformDependent.throwException(localThrowable);
/*     */   }
/*     */   
/*     */   public Promise<V> await() throws InterruptedException
/*     */   {
/* 241 */     if (isDone()) {
/* 242 */       return this;
/*     */     }
/*     */     
/* 245 */     if (Thread.interrupted()) {
/* 246 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 249 */     synchronized (this) {
/* 250 */       while (!isDone()) {
/* 251 */         checkDeadLock();
/* 252 */         incWaiters();
/*     */         try {
/* 254 */           wait();
/*     */         } finally {
/* 256 */           decWaiters();
/*     */         }
/*     */       }
/*     */     }
/* 260 */     return this;
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 266 */     return await0(paramTimeUnit.toNanos(paramLong), true);
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong) throws InterruptedException
/*     */   {
/* 271 */     return await0(TimeUnit.MILLISECONDS.toNanos(paramLong), true);
/*     */   }
/*     */   
/*     */   public Promise<V> awaitUninterruptibly()
/*     */   {
/* 276 */     if (isDone()) {
/* 277 */       return this;
/*     */     }
/*     */     
/* 280 */     int i = 0;
/* 281 */     synchronized (this) {
/* 282 */       while (!isDone()) {
/* 283 */         checkDeadLock();
/* 284 */         incWaiters();
/*     */         try {
/* 286 */           wait();
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {
/* 289 */           i = 1;
/*     */         } finally {
/* 291 */           decWaiters();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 296 */     if (i != 0) {
/* 297 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 300 */     return this;
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*     */     try {
/* 306 */       return await0(paramTimeUnit.toNanos(paramLong), false);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 309 */       throw new InternalError();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong)
/*     */   {
/*     */     try {
/* 316 */       return await0(TimeUnit.MILLISECONDS.toNanos(paramLong), false);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 319 */       throw new InternalError();
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
/*     */   private volatile Object result;
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
/*     */   private Object listeners;
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
/*     */   private DefaultPromise<V>.LateListeners lateListeners;
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
/*     */   private short waiters;
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
/*     */   protected void checkDeadLock()
/*     */   {
/* 388 */     EventExecutor localEventExecutor = executor();
/* 389 */     if ((localEventExecutor != null) && (localEventExecutor.inEventLoop())) {
/* 390 */       throw new BlockingOperationException(toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public Promise<V> setSuccess(V paramV)
/*     */   {
/* 396 */     if (setSuccess0(paramV)) {
/* 397 */       notifyListeners();
/* 398 */       return this;
/*     */     }
/* 400 */     throw new IllegalStateException("complete already: " + this);
/*     */   }
/*     */   
/*     */   public boolean trySuccess(V paramV)
/*     */   {
/* 405 */     if (setSuccess0(paramV)) {
/* 406 */       notifyListeners();
/* 407 */       return true;
/*     */     }
/* 409 */     return false;
/*     */   }
/*     */   
/*     */   public Promise<V> setFailure(Throwable paramThrowable)
/*     */   {
/* 414 */     if (setFailure0(paramThrowable)) {
/* 415 */       notifyListeners();
/* 416 */       return this;
/*     */     }
/* 418 */     throw new IllegalStateException("complete already: " + this, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean tryFailure(Throwable paramThrowable)
/*     */   {
/* 423 */     if (setFailure0(paramThrowable)) {
/* 424 */       notifyListeners();
/* 425 */       return true;
/*     */     }
/* 427 */     return false;
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean paramBoolean)
/*     */   {
/* 432 */     Object localObject1 = this.result;
/* 433 */     if ((isDone0(localObject1)) || (localObject1 == UNCANCELLABLE)) {
/* 434 */       return false;
/*     */     }
/*     */     
/* 437 */     synchronized (this)
/*     */     {
/* 439 */       localObject1 = this.result;
/* 440 */       if ((isDone0(localObject1)) || (localObject1 == UNCANCELLABLE)) {
/* 441 */         return false;
/*     */       }
/*     */       
/* 444 */       this.result = CANCELLATION_CAUSE_HOLDER;
/* 445 */       if (hasWaiters()) {
/* 446 */         notifyAll();
/*     */       }
/*     */     }
/*     */     
/* 450 */     notifyListeners();
/* 451 */     return true;
/*     */   }
/*     */   
/*     */   public boolean setUncancellable()
/*     */   {
/* 456 */     Object localObject1 = this.result;
/* 457 */     if (isDone0(localObject1)) {
/* 458 */       return !isCancelled0(localObject1);
/*     */     }
/*     */     
/* 461 */     synchronized (this)
/*     */     {
/* 463 */       localObject1 = this.result;
/* 464 */       if (isDone0(localObject1)) {
/* 465 */         return !isCancelled0(localObject1);
/*     */       }
/*     */       
/* 468 */       this.result = UNCANCELLABLE;
/*     */     }
/* 470 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setFailure0(Throwable paramThrowable) {
/* 474 */     if (paramThrowable == null) {
/* 475 */       throw new NullPointerException("cause");
/*     */     }
/*     */     
/* 478 */     if (isDone()) {
/* 479 */       return false;
/*     */     }
/*     */     
/* 482 */     synchronized (this)
/*     */     {
/* 484 */       if (isDone()) {
/* 485 */         return false;
/*     */       }
/*     */       
/* 488 */       this.result = new CauseHolder(paramThrowable);
/* 489 */       if (hasWaiters()) {
/* 490 */         notifyAll();
/*     */       }
/*     */     }
/* 493 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setSuccess0(V paramV) {
/* 497 */     if (isDone()) {
/* 498 */       return false;
/*     */     }
/*     */     
/* 501 */     synchronized (this)
/*     */     {
/* 503 */       if (isDone()) {
/* 504 */         return false;
/*     */       }
/* 506 */       if (paramV == null) {
/* 507 */         this.result = SUCCESS;
/*     */       } else {
/* 509 */         this.result = paramV;
/*     */       }
/* 511 */       if (hasWaiters()) {
/* 512 */         notifyAll();
/*     */       }
/*     */     }
/* 515 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public V getNow()
/*     */   {
/* 521 */     Object localObject = this.result;
/* 522 */     if (((localObject instanceof CauseHolder)) || (localObject == SUCCESS)) {
/* 523 */       return null;
/*     */     }
/* 525 */     return (V)localObject;
/*     */   }
/*     */   
/*     */   private boolean hasWaiters() {
/* 529 */     return this.waiters > 0;
/*     */   }
/*     */   
/*     */   private void incWaiters() {
/* 533 */     if (this.waiters == Short.MAX_VALUE) {
/* 534 */       throw new IllegalStateException("too many waiters: " + this);
/*     */     }
/* 536 */     this.waiters = ((short)(this.waiters + 1));
/*     */   }
/*     */   
/*     */   private void decWaiters() {
/* 540 */     this.waiters = ((short)(this.waiters - 1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void notifyListeners()
/*     */   {
/* 550 */     Object localObject1 = this.listeners;
/* 551 */     if (localObject1 == null) {
/* 552 */       return;
/*     */     }
/*     */     
/* 555 */     EventExecutor localEventExecutor = executor();
/* 556 */     final Object localObject2; if (localEventExecutor.inEventLoop()) {
/* 557 */       localObject2 = InternalThreadLocalMap.get();
/* 558 */       int i = ((InternalThreadLocalMap)localObject2).futureListenerStackDepth();
/* 559 */       if (i < 8) {
/* 560 */         ((InternalThreadLocalMap)localObject2).setFutureListenerStackDepth(i + 1);
/*     */         try {
/* 562 */           if ((localObject1 instanceof DefaultFutureListeners)) {
/* 563 */             notifyListeners0(this, (DefaultFutureListeners)localObject1);
/*     */           } else {
/* 565 */             GenericFutureListener localGenericFutureListener = (GenericFutureListener)localObject1;
/*     */             
/* 567 */             notifyListener0(this, localGenericFutureListener);
/*     */           }
/*     */         } finally {
/* 570 */           this.listeners = null;
/* 571 */           ((InternalThreadLocalMap)localObject2).setFutureListenerStackDepth(i);
/*     */         }
/* 573 */         return;
/*     */       }
/*     */     }
/*     */     
/* 577 */     if ((localObject1 instanceof DefaultFutureListeners)) {
/* 578 */       localObject2 = (DefaultFutureListeners)localObject1;
/* 579 */       execute(localEventExecutor, new Runnable()
/*     */       {
/*     */         public void run() {
/* 582 */           DefaultPromise.notifyListeners0(DefaultPromise.this, localObject2);
/* 583 */           DefaultPromise.this.listeners = null;
/*     */         }
/*     */       });
/*     */     } else {
/* 587 */       localObject2 = (GenericFutureListener)localObject1;
/*     */       
/* 589 */       execute(localEventExecutor, new Runnable()
/*     */       {
/*     */         public void run() {
/* 592 */           DefaultPromise.notifyListener0(DefaultPromise.this, localObject2);
/* 593 */           DefaultPromise.this.listeners = null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private static void notifyListeners0(Future<?> paramFuture, DefaultFutureListeners paramDefaultFutureListeners) {
/* 600 */     GenericFutureListener[] arrayOfGenericFutureListener = paramDefaultFutureListeners.listeners();
/* 601 */     int i = paramDefaultFutureListeners.size();
/* 602 */     for (int j = 0; j < i; j++) {
/* 603 */       notifyListener0(paramFuture, arrayOfGenericFutureListener[j]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void notifyLateListener(GenericFutureListener<?> paramGenericFutureListener)
/*     */   {
/* 613 */     EventExecutor localEventExecutor = executor();
/* 614 */     if (localEventExecutor.inEventLoop()) { Object localObject1;
/* 615 */       if ((this.listeners == null) && (this.lateListeners == null)) {
/* 616 */         localObject1 = InternalThreadLocalMap.get();
/* 617 */         int i = ((InternalThreadLocalMap)localObject1).futureListenerStackDepth();
/* 618 */         if (i < 8) {
/* 619 */           ((InternalThreadLocalMap)localObject1).setFutureListenerStackDepth(i + 1);
/*     */           try {
/* 621 */             notifyListener0(this, paramGenericFutureListener);
/*     */           } finally {
/* 623 */             ((InternalThreadLocalMap)localObject1).setFutureListenerStackDepth(i);
/*     */           }
/* 625 */           return;
/*     */         }
/*     */       } else {
/* 628 */         localObject1 = this.lateListeners;
/* 629 */         if (localObject1 == null) {
/* 630 */           this.lateListeners = (localObject1 = new LateListeners());
/*     */         }
/* 632 */         ((LateListeners)localObject1).add(paramGenericFutureListener);
/* 633 */         execute(localEventExecutor, (Runnable)localObject1);
/* 634 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 641 */     execute(localEventExecutor, new LateListenerNotifier(paramGenericFutureListener));
/*     */   }
/*     */   
/*     */ 
/*     */   protected static void notifyListener(EventExecutor paramEventExecutor, Future<?> paramFuture, final GenericFutureListener<?> paramGenericFutureListener)
/*     */   {
/* 647 */     if (paramEventExecutor.inEventLoop()) {
/* 648 */       InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.get();
/* 649 */       int i = localInternalThreadLocalMap.futureListenerStackDepth();
/* 650 */       if (i < 8) {
/* 651 */         localInternalThreadLocalMap.setFutureListenerStackDepth(i + 1);
/*     */         try {
/* 653 */           notifyListener0(paramFuture, paramGenericFutureListener);
/*     */         } finally {
/* 655 */           localInternalThreadLocalMap.setFutureListenerStackDepth(i);
/*     */         }
/* 657 */         return;
/*     */       }
/*     */     }
/*     */     
/* 661 */     execute(paramEventExecutor, new Runnable()
/*     */     {
/*     */       public void run() {
/* 664 */         DefaultPromise.notifyListener0(this.val$future, paramGenericFutureListener);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private static void execute(EventExecutor paramEventExecutor, Runnable paramRunnable) {
/*     */     try {
/* 671 */       paramEventExecutor.execute(paramRunnable);
/*     */     } catch (Throwable localThrowable) {
/* 673 */       rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   static void notifyListener0(Future paramFuture, GenericFutureListener paramGenericFutureListener)
/*     */   {
/*     */     try {
/* 680 */       paramGenericFutureListener.operationComplete(paramFuture);
/*     */     } catch (Throwable localThrowable) {
/* 682 */       if (logger.isWarnEnabled()) {
/* 683 */         logger.warn("An exception was thrown by " + paramGenericFutureListener.getClass().getName() + ".operationComplete()", localThrowable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized Object progressiveListeners()
/*     */   {
/* 693 */     Object localObject = this.listeners;
/* 694 */     if (localObject == null)
/*     */     {
/* 696 */       return null;
/*     */     }
/*     */     
/* 699 */     if ((localObject instanceof DefaultFutureListeners))
/*     */     {
/* 701 */       DefaultFutureListeners localDefaultFutureListeners = (DefaultFutureListeners)localObject;
/* 702 */       int i = localDefaultFutureListeners.progressiveSize();
/* 703 */       switch (i) {
/*     */       case 0: 
/* 705 */         return null;
/*     */       case 1: 
/* 707 */         for (GenericFutureListener localGenericFutureListener1 : localDefaultFutureListeners.listeners()) {
/* 708 */           if ((localGenericFutureListener1 instanceof GenericProgressiveFutureListener)) {
/* 709 */             return localGenericFutureListener1;
/*     */           }
/*     */         }
/* 712 */         return null;
/*     */       }
/*     */       
/* 715 */       ??? = localDefaultFutureListeners.listeners();
/* 716 */       GenericProgressiveFutureListener[] arrayOfGenericProgressiveFutureListener = new GenericProgressiveFutureListener[i];
/* 717 */       ??? = 0; for (int m = 0; m < i; ???++) {
/* 718 */         GenericFutureListener localGenericFutureListener2 = ???[???];
/* 719 */         if ((localGenericFutureListener2 instanceof GenericProgressiveFutureListener)) {
/* 720 */           arrayOfGenericProgressiveFutureListener[(m++)] = ((GenericProgressiveFutureListener)localGenericFutureListener2);
/*     */         }
/*     */       }
/*     */       
/* 724 */       return arrayOfGenericProgressiveFutureListener; }
/* 725 */     if ((localObject instanceof GenericProgressiveFutureListener)) {
/* 726 */       return localObject;
/*     */     }
/*     */     
/* 729 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   void notifyProgressiveListeners(final long paramLong1, long paramLong2)
/*     */   {
/* 735 */     Object localObject1 = progressiveListeners();
/* 736 */     if (localObject1 == null) {
/* 737 */       return;
/*     */     }
/*     */     
/* 740 */     final ProgressiveFuture localProgressiveFuture = (ProgressiveFuture)this;
/*     */     
/* 742 */     EventExecutor localEventExecutor = executor();
/* 743 */     if (localEventExecutor.inEventLoop()) {
/* 744 */       if ((localObject1 instanceof GenericProgressiveFutureListener[])) {
/* 745 */         notifyProgressiveListeners0(localProgressiveFuture, (GenericProgressiveFutureListener[])localObject1, paramLong1, paramLong2);
/*     */       }
/*     */       else {
/* 748 */         notifyProgressiveListener0(localProgressiveFuture, (GenericProgressiveFutureListener)localObject1, paramLong1, paramLong2);
/*     */       }
/*     */     } else {
/*     */       final Object localObject2;
/* 752 */       if ((localObject1 instanceof GenericProgressiveFutureListener[])) {
/* 753 */         localObject2 = (GenericProgressiveFutureListener[])localObject1;
/*     */         
/* 755 */         execute(localEventExecutor, new Runnable()
/*     */         {
/*     */           public void run() {
/* 758 */             DefaultPromise.notifyProgressiveListeners0(localProgressiveFuture, localObject2, paramLong1, this.val$total);
/*     */           }
/*     */         });
/*     */       } else {
/* 762 */         localObject2 = (GenericProgressiveFutureListener)localObject1;
/*     */         
/* 764 */         execute(localEventExecutor, new Runnable()
/*     */         {
/*     */           public void run() {
/* 767 */             DefaultPromise.notifyProgressiveListener0(localProgressiveFuture, localObject2, paramLong1, this.val$total);
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void notifyProgressiveListeners0(ProgressiveFuture<?> paramProgressiveFuture, GenericProgressiveFutureListener<?>[] paramArrayOfGenericProgressiveFutureListener, long paramLong1, long paramLong2)
/*     */   {
/* 776 */     for (GenericProgressiveFutureListener<?> localGenericProgressiveFutureListener : paramArrayOfGenericProgressiveFutureListener) {
/* 777 */       if (localGenericProgressiveFutureListener == null) {
/*     */         break;
/*     */       }
/* 780 */       notifyProgressiveListener0(paramProgressiveFuture, localGenericProgressiveFutureListener, paramLong1, paramLong2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void notifyProgressiveListener0(ProgressiveFuture paramProgressiveFuture, GenericProgressiveFutureListener paramGenericProgressiveFutureListener, long paramLong1, long paramLong2)
/*     */   {
/*     */     try
/*     */     {
/* 788 */       paramGenericProgressiveFutureListener.operationProgressed(paramProgressiveFuture, paramLong1, paramLong2);
/*     */     } catch (Throwable localThrowable) {
/* 790 */       if (logger.isWarnEnabled())
/* 791 */         logger.warn("An exception was thrown by " + paramGenericProgressiveFutureListener.getClass().getName() + ".operationProgressed()", localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class CauseHolder {
/*     */     final Throwable cause;
/*     */     
/*     */     CauseHolder(Throwable paramThrowable) {
/* 799 */       this.cause = paramThrowable;
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 805 */     return toStringBuilder().toString();
/*     */   }
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 809 */     StringBuilder localStringBuilder = new StringBuilder(64);
/* 810 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 811 */     localStringBuilder.append('@');
/* 812 */     localStringBuilder.append(Integer.toHexString(hashCode()));
/*     */     
/* 814 */     Object localObject = this.result;
/* 815 */     if (localObject == SUCCESS) {
/* 816 */       localStringBuilder.append("(success)");
/* 817 */     } else if (localObject == UNCANCELLABLE) {
/* 818 */       localStringBuilder.append("(uncancellable)");
/* 819 */     } else if ((localObject instanceof CauseHolder)) {
/* 820 */       localStringBuilder.append("(failure(");
/* 821 */       localStringBuilder.append(((CauseHolder)localObject).cause);
/* 822 */       localStringBuilder.append(')');
/*     */     } else {
/* 824 */       localStringBuilder.append("(incomplete)");
/*     */     }
/* 826 */     return localStringBuilder;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private boolean await0(long paramLong, boolean paramBoolean)
/*     */     throws InterruptedException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   4: ifeq +5 -> 9
/*     */     //   7: iconst_1
/*     */     //   8: ireturn
/*     */     //   9: lload_1
/*     */     //   10: lconst_0
/*     */     //   11: lcmp
/*     */     //   12: ifgt +8 -> 20
/*     */     //   15: aload_0
/*     */     //   16: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   19: ireturn
/*     */     //   20: iload_3
/*     */     //   21: ifeq +21 -> 42
/*     */     //   24: invokestatic 154	java/lang/Thread:interrupted	()Z
/*     */     //   27: ifeq +15 -> 42
/*     */     //   30: new 131	java/lang/InterruptedException
/*     */     //   33: dup
/*     */     //   34: aload_0
/*     */     //   35: invokevirtual 158	io/netty/util/concurrent/DefaultPromise:toString	()Ljava/lang/String;
/*     */     //   38: invokespecial 159	java/lang/InterruptedException:<init>	(Ljava/lang/String;)V
/*     */     //   41: athrow
/*     */     //   42: invokestatic 203	java/lang/System:nanoTime	()J
/*     */     //   45: lstore 4
/*     */     //   47: lload_1
/*     */     //   48: lstore 6
/*     */     //   50: iconst_0
/*     */     //   51: istore 8
/*     */     //   53: aload_0
/*     */     //   54: dup
/*     */     //   55: astore 9
/*     */     //   57: monitorenter
/*     */     //   58: aload_0
/*     */     //   59: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   62: ifeq +23 -> 85
/*     */     //   65: iconst_1
/*     */     //   66: istore 10
/*     */     //   68: aload 9
/*     */     //   70: monitorexit
/*     */     //   71: iload 8
/*     */     //   73: ifeq +9 -> 82
/*     */     //   76: invokestatic 191	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   79: invokevirtual 194	java/lang/Thread:interrupt	()V
/*     */     //   82: iload 10
/*     */     //   84: ireturn
/*     */     //   85: lload 6
/*     */     //   87: lconst_0
/*     */     //   88: lcmp
/*     */     //   89: ifgt +26 -> 115
/*     */     //   92: aload_0
/*     */     //   93: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   96: istore 10
/*     */     //   98: aload 9
/*     */     //   100: monitorexit
/*     */     //   101: iload 8
/*     */     //   103: ifeq +9 -> 112
/*     */     //   106: invokestatic 191	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   109: invokevirtual 194	java/lang/Thread:interrupt	()V
/*     */     //   112: iload 10
/*     */     //   114: ireturn
/*     */     //   115: aload_0
/*     */     //   116: invokevirtual 162	io/netty/util/concurrent/DefaultPromise:checkDeadLock	()V
/*     */     //   119: aload_0
/*     */     //   120: invokespecial 165	io/netty/util/concurrent/DefaultPromise:incWaiters	()V
/*     */     //   123: aload_0
/*     */     //   124: lload 6
/*     */     //   126: ldc2_w 204
/*     */     //   129: ldiv
/*     */     //   130: lload 6
/*     */     //   132: ldc2_w 204
/*     */     //   135: lrem
/*     */     //   136: l2i
/*     */     //   137: invokevirtual 208	java/lang/Object:wait	(JI)V
/*     */     //   140: goto +15 -> 155
/*     */     //   143: astore 10
/*     */     //   145: iload_3
/*     */     //   146: ifeq +6 -> 152
/*     */     //   149: aload 10
/*     */     //   151: athrow
/*     */     //   152: iconst_1
/*     */     //   153: istore 8
/*     */     //   155: aload_0
/*     */     //   156: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   159: ifeq +27 -> 186
/*     */     //   162: iconst_1
/*     */     //   163: istore 10
/*     */     //   165: aload_0
/*     */     //   166: invokespecial 171	io/netty/util/concurrent/DefaultPromise:decWaiters	()V
/*     */     //   169: aload 9
/*     */     //   171: monitorexit
/*     */     //   172: iload 8
/*     */     //   174: ifeq +9 -> 183
/*     */     //   177: invokestatic 191	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   180: invokevirtual 194	java/lang/Thread:interrupt	()V
/*     */     //   183: iload 10
/*     */     //   185: ireturn
/*     */     //   186: lload_1
/*     */     //   187: invokestatic 203	java/lang/System:nanoTime	()J
/*     */     //   190: lload 4
/*     */     //   192: lsub
/*     */     //   193: lsub
/*     */     //   194: lstore 6
/*     */     //   196: lload 6
/*     */     //   198: lconst_0
/*     */     //   199: lcmp
/*     */     //   200: ifgt -77 -> 123
/*     */     //   203: aload_0
/*     */     //   204: invokevirtual 95	io/netty/util/concurrent/DefaultPromise:isDone	()Z
/*     */     //   207: istore 10
/*     */     //   209: aload_0
/*     */     //   210: invokespecial 171	io/netty/util/concurrent/DefaultPromise:decWaiters	()V
/*     */     //   213: aload 9
/*     */     //   215: monitorexit
/*     */     //   216: iload 8
/*     */     //   218: ifeq +9 -> 227
/*     */     //   221: invokestatic 191	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   224: invokevirtual 194	java/lang/Thread:interrupt	()V
/*     */     //   227: iload 10
/*     */     //   229: ireturn
/*     */     //   230: astore 11
/*     */     //   232: aload_0
/*     */     //   233: invokespecial 171	io/netty/util/concurrent/DefaultPromise:decWaiters	()V
/*     */     //   236: aload 11
/*     */     //   238: athrow
/*     */     //   239: astore 12
/*     */     //   241: aload 9
/*     */     //   243: monitorexit
/*     */     //   244: aload 12
/*     */     //   246: athrow
/*     */     //   247: astore 13
/*     */     //   249: iload 8
/*     */     //   251: ifeq +9 -> 260
/*     */     //   254: invokestatic 191	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   257: invokevirtual 194	java/lang/Thread:interrupt	()V
/*     */     //   260: aload 13
/*     */     //   262: athrow
/*     */     // Line number table:
/*     */     //   Java source line #324	-> byte code offset #0
/*     */     //   Java source line #325	-> byte code offset #7
/*     */     //   Java source line #328	-> byte code offset #9
/*     */     //   Java source line #329	-> byte code offset #15
/*     */     //   Java source line #332	-> byte code offset #20
/*     */     //   Java source line #333	-> byte code offset #30
/*     */     //   Java source line #336	-> byte code offset #42
/*     */     //   Java source line #337	-> byte code offset #47
/*     */     //   Java source line #338	-> byte code offset #50
/*     */     //   Java source line #341	-> byte code offset #53
/*     */     //   Java source line #342	-> byte code offset #58
/*     */     //   Java source line #343	-> byte code offset #65
/*     */     //   Java source line #378	-> byte code offset #71
/*     */     //   Java source line #379	-> byte code offset #76
/*     */     //   Java source line #346	-> byte code offset #85
/*     */     //   Java source line #347	-> byte code offset #92
/*     */     //   Java source line #378	-> byte code offset #101
/*     */     //   Java source line #379	-> byte code offset #106
/*     */     //   Java source line #350	-> byte code offset #115
/*     */     //   Java source line #351	-> byte code offset #119
/*     */     //   Java source line #355	-> byte code offset #123
/*     */     //   Java source line #362	-> byte code offset #140
/*     */     //   Java source line #356	-> byte code offset #143
/*     */     //   Java source line #357	-> byte code offset #145
/*     */     //   Java source line #358	-> byte code offset #149
/*     */     //   Java source line #360	-> byte code offset #152
/*     */     //   Java source line #364	-> byte code offset #155
/*     */     //   Java source line #365	-> byte code offset #162
/*     */     //   Java source line #374	-> byte code offset #165
/*     */     //   Java source line #378	-> byte code offset #172
/*     */     //   Java source line #379	-> byte code offset #177
/*     */     //   Java source line #367	-> byte code offset #186
/*     */     //   Java source line #368	-> byte code offset #196
/*     */     //   Java source line #369	-> byte code offset #203
/*     */     //   Java source line #374	-> byte code offset #209
/*     */     //   Java source line #378	-> byte code offset #216
/*     */     //   Java source line #379	-> byte code offset #221
/*     */     //   Java source line #374	-> byte code offset #230
/*     */     //   Java source line #376	-> byte code offset #239
/*     */     //   Java source line #378	-> byte code offset #247
/*     */     //   Java source line #379	-> byte code offset #254
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	263	0	this	DefaultPromise
/*     */     //   0	263	1	paramLong	long
/*     */     //   0	263	3	paramBoolean	boolean
/*     */     //   45	146	4	l1	long
/*     */     //   48	149	6	l2	long
/*     */     //   51	199	8	i	int
/*     */     //   66	47	10	bool1	boolean
/*     */     //   143	7	10	localInterruptedException	InterruptedException
/*     */     //   163	65	10	bool2	boolean
/*     */     //   230	7	11	localObject1	Object
/*     */     //   239	6	12	localObject2	Object
/*     */     //   247	14	13	localObject3	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   123	140	143	java/lang/InterruptedException
/*     */     //   123	165	230	finally
/*     */     //   186	209	230	finally
/*     */     //   230	232	230	finally
/*     */     //   58	71	239	finally
/*     */     //   85	101	239	finally
/*     */     //   115	172	239	finally
/*     */     //   186	216	239	finally
/*     */     //   230	244	239	finally
/*     */     //   53	71	247	finally
/*     */     //   85	101	247	finally
/*     */     //   115	172	247	finally
/*     */     //   186	216	247	finally
/*     */     //   230	249	247	finally
/*     */   }
/*     */   
/*     */   private final class LateListeners
/*     */     extends ArrayDeque<GenericFutureListener<?>>
/*     */     implements Runnable
/*     */   {
/*     */     private static final long serialVersionUID = -687137418080392244L;
/*     */     
/*     */     LateListeners()
/*     */     {
/* 834 */       super();
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 839 */       if (DefaultPromise.this.listeners == null) {
/*     */         for (;;) {
/* 841 */           GenericFutureListener localGenericFutureListener = (GenericFutureListener)poll();
/* 842 */           if (localGenericFutureListener == null) {
/*     */             break;
/*     */           }
/* 845 */           DefaultPromise.notifyListener0(DefaultPromise.this, localGenericFutureListener);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 850 */       DefaultPromise.execute(DefaultPromise.this.executor(), this);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class LateListenerNotifier implements Runnable {
/*     */     private GenericFutureListener<?> l;
/*     */     
/*     */     LateListenerNotifier() {
/*     */       GenericFutureListener localGenericFutureListener;
/* 859 */       this.l = localGenericFutureListener;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 864 */       DefaultPromise.LateListeners localLateListeners = DefaultPromise.this.lateListeners;
/* 865 */       if (this.l != null) {
/* 866 */         if (localLateListeners == null) {
/* 867 */           DefaultPromise.this.lateListeners = (localLateListeners = new DefaultPromise.LateListeners(DefaultPromise.this));
/*     */         }
/* 869 */         localLateListeners.add(this.l);
/* 870 */         this.l = null;
/*     */       }
/*     */       
/* 873 */       localLateListeners.run();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */