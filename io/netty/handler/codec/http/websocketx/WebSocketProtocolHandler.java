/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.List;
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
/*    */ abstract class WebSocketProtocolHandler
/*    */   extends MessageToMessageDecoder<WebSocketFrame>
/*    */ {
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList)
/*    */     throws Exception
/*    */   {
/* 27 */     if ((paramWebSocketFrame instanceof PingWebSocketFrame)) {
/* 28 */       paramWebSocketFrame.content().retain();
/* 29 */       paramChannelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(paramWebSocketFrame.content()));
/* 30 */       return;
/*    */     }
/* 32 */     if ((paramWebSocketFrame instanceof PongWebSocketFrame))
/*    */     {
/* 34 */       return;
/*    */     }
/*    */     
/* 37 */     paramList.add(paramWebSocketFrame.retain());
/*    */   }
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*    */   {
/* 42 */     paramChannelHandlerContext.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */