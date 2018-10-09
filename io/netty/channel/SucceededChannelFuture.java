/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
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
/*    */ final class SucceededChannelFuture
/*    */   extends CompleteChannelFuture
/*    */ {
/*    */   SucceededChannelFuture(Channel paramChannel, EventExecutor paramEventExecutor)
/*    */   {
/* 33 */     super(paramChannel, paramEventExecutor);
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
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\SucceededChannelFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */