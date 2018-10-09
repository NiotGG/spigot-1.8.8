/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import java.net.URI;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   private final WebSocketClientHandshaker handshaker;
/*     */   private final boolean handleCloseFrames;
/*     */   
/*     */   public static enum ClientHandshakeStateEvent
/*     */   {
/*  52 */     HANDSHAKE_ISSUED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  57 */     HANDSHAKE_COMPLETE;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private ClientHandshakeStateEvent() {}
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
/*     */   public WebSocketClientProtocolHandler(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean1, HttpHeaders paramHttpHeaders, int paramInt, boolean paramBoolean2)
/*     */   {
/*  80 */     this(WebSocketClientHandshakerFactory.newHandshaker(paramURI, paramWebSocketVersion, paramString, paramBoolean1, paramHttpHeaders, paramInt), paramBoolean2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocketClientProtocolHandler(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean, HttpHeaders paramHttpHeaders, int paramInt)
/*     */   {
/* 102 */     this(paramURI, paramWebSocketVersion, paramString, paramBoolean, paramHttpHeaders, paramInt, true);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker paramWebSocketClientHandshaker, boolean paramBoolean)
/*     */   {
/* 116 */     this.handshaker = paramWebSocketClientHandshaker;
/* 117 */     this.handleCloseFrames = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker paramWebSocketClientHandshaker)
/*     */   {
/* 128 */     this(paramWebSocketClientHandshaker, true);
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList) throws Exception
/*     */   {
/* 133 */     if ((this.handleCloseFrames) && ((paramWebSocketFrame instanceof CloseWebSocketFrame))) {
/* 134 */       paramChannelHandlerContext.close();
/* 135 */       return;
/*     */     }
/* 137 */     super.decode(paramChannelHandlerContext, paramWebSocketFrame, paramList);
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 142 */     ChannelPipeline localChannelPipeline = paramChannelHandlerContext.pipeline();
/* 143 */     if (localChannelPipeline.get(WebSocketClientProtocolHandshakeHandler.class) == null)
/*     */     {
/* 145 */       paramChannelHandlerContext.pipeline().addBefore(paramChannelHandlerContext.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */