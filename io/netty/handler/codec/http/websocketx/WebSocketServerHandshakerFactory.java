/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
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
/*     */ public class WebSocketServerHandshakerFactory
/*     */ {
/*     */   private final String webSocketURL;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   public WebSocketServerHandshakerFactory(String paramString1, String paramString2, boolean paramBoolean)
/*     */   {
/*  55 */     this(paramString1, paramString2, paramBoolean, 65536);
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
/*     */   public WebSocketServerHandshakerFactory(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  75 */     this.webSocketURL = paramString1;
/*  76 */     this.subprotocols = paramString2;
/*  77 */     this.allowExtensions = paramBoolean;
/*  78 */     this.maxFramePayloadLength = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocketServerHandshaker newHandshaker(HttpRequest paramHttpRequest)
/*     */   {
/*  89 */     String str = paramHttpRequest.headers().get("Sec-WebSocket-Version");
/*  90 */     if (str != null) {
/*  91 */       if (str.equals(WebSocketVersion.V13.toHttpHeaderValue()))
/*     */       {
/*  93 */         return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  95 */       if (str.equals(WebSocketVersion.V08.toHttpHeaderValue()))
/*     */       {
/*  97 */         return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  99 */       if (str.equals(WebSocketVersion.V07.toHttpHeaderValue()))
/*     */       {
/* 101 */         return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*     */       
/* 104 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 108 */     return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void sendUnsupportedWebSocketVersionResponse(Channel paramChannel)
/*     */   {
/* 117 */     sendUnsupportedVersionResponse(paramChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelFuture sendUnsupportedVersionResponse(Channel paramChannel)
/*     */   {
/* 124 */     return sendUnsupportedVersionResponse(paramChannel, paramChannel.newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelFuture sendUnsupportedVersionResponse(Channel paramChannel, ChannelPromise paramChannelPromise)
/*     */   {
/* 131 */     DefaultHttpResponse localDefaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
/*     */     
/*     */ 
/* 134 */     localDefaultHttpResponse.headers().set("Sec-WebSocket-Version", WebSocketVersion.V13.toHttpHeaderValue());
/* 135 */     return paramChannel.write(localDefaultHttpResponse, paramChannelPromise);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshakerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */