/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutorGroup;
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
/*    */ final class DefaultChannelHandlerContext
/*    */   extends AbstractChannelHandlerContext
/*    */ {
/*    */   private final ChannelHandler handler;
/*    */   
/*    */   DefaultChannelHandlerContext(DefaultChannelPipeline paramDefaultChannelPipeline, EventExecutorGroup paramEventExecutorGroup, String paramString, ChannelHandler paramChannelHandler)
/*    */   {
/* 26 */     super(paramDefaultChannelPipeline, paramEventExecutorGroup, paramString, isInbound(paramChannelHandler), isOutbound(paramChannelHandler));
/* 27 */     if (paramChannelHandler == null) {
/* 28 */       throw new NullPointerException("handler");
/*    */     }
/* 30 */     this.handler = paramChannelHandler;
/*    */   }
/*    */   
/*    */   public ChannelHandler handler()
/*    */   {
/* 35 */     return this.handler;
/*    */   }
/*    */   
/*    */   private static boolean isInbound(ChannelHandler paramChannelHandler) {
/* 39 */     return paramChannelHandler instanceof ChannelInboundHandler;
/*    */   }
/*    */   
/*    */   private static boolean isOutbound(ChannelHandler paramChannelHandler) {
/* 43 */     return paramChannelHandler instanceof ChannelOutboundHandler;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultChannelHandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */