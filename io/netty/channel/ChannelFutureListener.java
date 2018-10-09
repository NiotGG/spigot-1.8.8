/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.GenericFutureListener;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface ChannelFutureListener
/*    */   extends GenericFutureListener<ChannelFuture>
/*    */ {
/* 41 */   public static final ChannelFutureListener CLOSE = new ChannelFutureListener()
/*    */   {
/*    */     public void operationComplete(ChannelFuture paramAnonymousChannelFuture) {
/* 44 */       paramAnonymousChannelFuture.channel().close();
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 52 */   public static final ChannelFutureListener CLOSE_ON_FAILURE = new ChannelFutureListener()
/*    */   {
/*    */     public void operationComplete(ChannelFuture paramAnonymousChannelFuture) {
/* 55 */       if (!paramAnonymousChannelFuture.isSuccess()) {
/* 56 */         paramAnonymousChannelFuture.channel().close();
/*    */       }
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 65 */   public static final ChannelFutureListener FIRE_EXCEPTION_ON_FAILURE = new ChannelFutureListener()
/*    */   {
/*    */     public void operationComplete(ChannelFuture paramAnonymousChannelFuture) {
/* 68 */       if (!paramAnonymousChannelFuture.isSuccess()) {
/* 69 */         paramAnonymousChannelFuture.channel().pipeline().fireExceptionCaught(paramAnonymousChannelFuture.cause());
/*    */       }
/*    */     }
/*    */   };
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelFutureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */