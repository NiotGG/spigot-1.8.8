/*    */ package io.netty.util.concurrent;
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
/*    */ public final class SucceededFuture<V>
/*    */   extends CompleteFuture<V>
/*    */ {
/*    */   private final V result;
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
/*    */   public SucceededFuture(EventExecutor paramEventExecutor, V paramV)
/*    */   {
/* 32 */     super(paramEventExecutor);
/* 33 */     this.result = paramV;
/*    */   }
/*    */   
/*    */   public Throwable cause()
/*    */   {
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isSuccess()
/*    */   {
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   public V getNow()
/*    */   {
/* 48 */     return (V)this.result;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\SucceededFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */