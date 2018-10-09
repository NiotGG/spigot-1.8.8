/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelFutureListener;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.http.FullHttpResponse;
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
/*    */ class WebSocketClientProtocolHandshakeHandler
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/*    */   private final WebSocketClientHandshaker handshaker;
/*    */   
/*    */   WebSocketClientProtocolHandshakeHandler(WebSocketClientHandshaker paramWebSocketClientHandshaker)
/*    */   {
/* 28 */     this.handshaker = paramWebSocketClientHandshaker;
/*    */   }
/*    */   
/*    */   public void channelActive(final ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*    */   {
/* 33 */     super.channelActive(paramChannelHandlerContext);
/* 34 */     this.handshaker.handshake(paramChannelHandlerContext.channel()).addListener(new ChannelFutureListener()
/*    */     {
/*    */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 37 */         if (!paramAnonymousChannelFuture.isSuccess()) {
/* 38 */           paramChannelHandlerContext.fireExceptionCaught(paramAnonymousChannelFuture.cause());
/*    */         } else {
/* 40 */           paramChannelHandlerContext.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*    */     throws Exception
/*    */   {
/* 49 */     if (!(paramObject instanceof FullHttpResponse)) {
/* 50 */       paramChannelHandlerContext.fireChannelRead(paramObject);
/* 51 */       return;
/*    */     }
/*    */     
/* 54 */     if (!this.handshaker.isHandshakeComplete()) {
/* 55 */       this.handshaker.finishHandshake(paramChannelHandlerContext.channel(), (FullHttpResponse)paramObject);
/* 56 */       paramChannelHandlerContext.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*    */       
/* 58 */       paramChannelHandlerContext.pipeline().remove(this);
/* 59 */       return;
/*    */     }
/* 61 */     throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolHandshakeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */