/*     */ package io.netty.util.concurrent;
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
/*     */ public class DefaultProgressivePromise<V>
/*     */   extends DefaultPromise<V>
/*     */   implements ProgressivePromise<V>
/*     */ {
/*     */   public DefaultProgressivePromise(EventExecutor paramEventExecutor)
/*     */   {
/*  30 */     super(paramEventExecutor);
/*     */   }
/*     */   
/*     */   protected DefaultProgressivePromise() {}
/*     */   
/*     */   public ProgressivePromise<V> setProgress(long paramLong1, long paramLong2)
/*     */   {
/*  37 */     if (paramLong2 < 0L)
/*     */     {
/*  39 */       paramLong2 = -1L;
/*  40 */       if (paramLong1 < 0L) {
/*  41 */         throw new IllegalArgumentException("progress: " + paramLong1 + " (expected: >= 0)");
/*     */       }
/*  43 */     } else if ((paramLong1 < 0L) || (paramLong1 > paramLong2)) {
/*  44 */       throw new IllegalArgumentException("progress: " + paramLong1 + " (expected: 0 <= progress <= total (" + paramLong2 + "))");
/*     */     }
/*     */     
/*     */ 
/*  48 */     if (isDone()) {
/*  49 */       throw new IllegalStateException("complete already");
/*     */     }
/*     */     
/*  52 */     notifyProgressiveListeners(paramLong1, paramLong2);
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public boolean tryProgress(long paramLong1, long paramLong2)
/*     */   {
/*  58 */     if (paramLong2 < 0L) {
/*  59 */       paramLong2 = -1L;
/*  60 */       if ((paramLong1 < 0L) || (isDone())) {
/*  61 */         return false;
/*     */       }
/*  63 */     } else if ((paramLong1 < 0L) || (paramLong1 > paramLong2) || (isDone())) {
/*  64 */       return false;
/*     */     }
/*     */     
/*  67 */     notifyProgressiveListeners(paramLong1, paramLong2);
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/*  73 */     super.addListener(paramGenericFutureListener);
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/*  79 */     super.addListeners(paramVarArgs);
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> paramGenericFutureListener)
/*     */   {
/*  85 */     super.removeListener(paramGenericFutureListener);
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... paramVarArgs)
/*     */   {
/*  91 */     super.removeListeners(paramVarArgs);
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> sync() throws InterruptedException
/*     */   {
/*  97 */     super.sync();
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> syncUninterruptibly()
/*     */   {
/* 103 */     super.syncUninterruptibly();
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> await() throws InterruptedException
/*     */   {
/* 109 */     super.await();
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> awaitUninterruptibly()
/*     */   {
/* 115 */     super.awaitUninterruptibly();
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> setSuccess(V paramV)
/*     */   {
/* 121 */     super.setSuccess(paramV);
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public ProgressivePromise<V> setFailure(Throwable paramThrowable)
/*     */   {
/* 127 */     super.setFailure(paramThrowable);
/* 128 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultProgressivePromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */