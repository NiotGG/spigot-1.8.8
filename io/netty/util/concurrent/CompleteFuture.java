/*     */ package io.netty.util.concurrent;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CompleteFuture<V>
/*     */   extends AbstractFuture<V>
/*     */ {
/*     */   private final EventExecutor executor;
/*     */   
/*     */   protected CompleteFuture(EventExecutor paramEventExecutor)
/*     */   {
/*  34 */     this.executor = paramEventExecutor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected EventExecutor executor()
/*     */   {
/*  41 */     return this.executor;
/*     */   }
/*     */   
/*     */   public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/*  46 */     if (paramGenericFutureListener == null) {
/*  47 */       throw new NullPointerException("listener");
/*     */     }
/*  49 */     DefaultPromise.notifyListener(executor(), this, paramGenericFutureListener);
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/*  55 */     if (paramVarArgs == null) {
/*  56 */       throw new NullPointerException("listeners");
/*     */     }
/*  58 */     for (GenericFutureListener<? extends Future<? super V>> localGenericFutureListener : paramVarArgs) {
/*  59 */       if (localGenericFutureListener == null) {
/*     */         break;
/*     */       }
/*  62 */       DefaultPromise.notifyListener(executor(), this, localGenericFutureListener);
/*     */     }
/*  64 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/*  70 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public Future<V> await() throws InterruptedException
/*     */   {
/*  81 */     if (Thread.interrupted()) {
/*  82 */       throw new InterruptedException();
/*     */     }
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException
/*     */   {
/*  89 */     if (Thread.interrupted()) {
/*  90 */       throw new InterruptedException();
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public Future<V> sync() throws InterruptedException
/*     */   {
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public Future<V> syncUninterruptibly()
/*     */   {
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong) throws InterruptedException
/*     */   {
/* 107 */     if (Thread.interrupted()) {
/* 108 */       throw new InterruptedException();
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public Future<V> awaitUninterruptibly()
/*     */   {
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong)
/*     */   {
/* 125 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDone()
/*     */   {
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCancellable()
/*     */   {
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCancelled()
/*     */   {
/* 140 */     return false;
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean paramBoolean)
/*     */   {
/* 145 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\CompleteFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */