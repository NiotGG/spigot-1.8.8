/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FailedFuture<V>
/*    */   extends CompleteFuture<V>
/*    */ {
/*    */   private final Throwable cause;
/*    */   
/*    */   public FailedFuture(EventExecutor paramEventExecutor, Throwable paramThrowable)
/*    */   {
/* 36 */     super(paramEventExecutor);
/* 37 */     if (paramThrowable == null) {
/* 38 */       throw new NullPointerException("cause");
/*    */     }
/* 40 */     this.cause = paramThrowable;
/*    */   }
/*    */   
/*    */   public Throwable cause()
/*    */   {
/* 45 */     return this.cause;
/*    */   }
/*    */   
/*    */   public boolean isSuccess()
/*    */   {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public Future<V> sync()
/*    */   {
/* 55 */     PlatformDependent.throwException(this.cause);
/* 56 */     return this;
/*    */   }
/*    */   
/*    */   public Future<V> syncUninterruptibly()
/*    */   {
/* 61 */     PlatformDependent.throwException(this.cause);
/* 62 */     return this;
/*    */   }
/*    */   
/*    */   public V getNow()
/*    */   {
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\FailedFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */