/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.handler.ssl.SslHandler;
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
/*     */ class WebSocketServerProtocolHandshakeHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final String websocketPath;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadSize;
/*     */   
/*     */   WebSocketServerProtocolHandshakeHandler(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  47 */     this.websocketPath = paramString1;
/*  48 */     this.subprotocols = paramString2;
/*  49 */     this.allowExtensions = paramBoolean;
/*  50 */     this.maxFramePayloadSize = paramInt;
/*     */   }
/*     */   
/*     */   public void channelRead(final ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/*  55 */     FullHttpRequest localFullHttpRequest = (FullHttpRequest)paramObject;
/*     */     try {
/*  57 */       if (localFullHttpRequest.getMethod() != HttpMethod.GET) {
/*  58 */         sendHttpResponse(paramChannelHandlerContext, localFullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
/*     */       }
/*     */       else
/*     */       {
/*  62 */         WebSocketServerHandshakerFactory localWebSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(paramChannelHandlerContext.pipeline(), localFullHttpRequest, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize);
/*     */         
/*     */ 
/*  65 */         WebSocketServerHandshaker localWebSocketServerHandshaker = localWebSocketServerHandshakerFactory.newHandshaker(localFullHttpRequest);
/*  66 */         if (localWebSocketServerHandshaker == null) {
/*  67 */           WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(paramChannelHandlerContext.channel());
/*     */         } else {
/*  69 */           ChannelFuture localChannelFuture = localWebSocketServerHandshaker.handshake(paramChannelHandlerContext.channel(), localFullHttpRequest);
/*  70 */           localChannelFuture.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/*  73 */               if (!paramAnonymousChannelFuture.isSuccess()) {
/*  74 */                 paramChannelHandlerContext.fireExceptionCaught(paramAnonymousChannelFuture.cause());
/*     */               } else {
/*  76 */                 paramChannelHandlerContext.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*     */               }
/*     */               
/*     */             }
/*  80 */           });
/*  81 */           WebSocketServerProtocolHandler.setHandshaker(paramChannelHandlerContext, localWebSocketServerHandshaker);
/*  82 */           paramChannelHandlerContext.pipeline().replace(this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
/*     */         }
/*     */       }
/*     */     } finally {
/*  86 */       localFullHttpRequest.release();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void sendHttpResponse(ChannelHandlerContext paramChannelHandlerContext, HttpRequest paramHttpRequest, HttpResponse paramHttpResponse) {
/*  91 */     ChannelFuture localChannelFuture = paramChannelHandlerContext.channel().writeAndFlush(paramHttpResponse);
/*  92 */     if ((!HttpHeaders.isKeepAlive(paramHttpRequest)) || (paramHttpResponse.getStatus().code() != 200)) {
/*  93 */       localChannelFuture.addListener(ChannelFutureListener.CLOSE);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String getWebSocketLocation(ChannelPipeline paramChannelPipeline, HttpRequest paramHttpRequest, String paramString) {
/*  98 */     String str = "ws";
/*  99 */     if (paramChannelPipeline.get(SslHandler.class) != null)
/*     */     {
/* 101 */       str = "wss";
/*     */     }
/* 103 */     return str + "://" + paramHttpRequest.headers().get("Host") + paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandshakeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */