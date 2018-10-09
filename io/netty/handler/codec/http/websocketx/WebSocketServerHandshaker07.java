/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerHandshaker07
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker07(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  58 */     super(WebSocketVersion.V07, paramString1, paramString2, paramInt);
/*  59 */     this.allowExtensions = paramBoolean;
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
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders)
/*     */   {
/*  98 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
/*     */     
/*     */ 
/* 101 */     if (paramHttpHeaders != null) {
/* 102 */       localDefaultFullHttpResponse.headers().add(paramHttpHeaders);
/*     */     }
/*     */     
/* 105 */     String str1 = paramFullHttpRequest.headers().get("Sec-WebSocket-Key");
/* 106 */     if (str1 == null) {
/* 107 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 109 */     String str2 = str1 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 110 */     byte[] arrayOfByte = WebSocketUtil.sha1(str2.getBytes(CharsetUtil.US_ASCII));
/* 111 */     String str3 = WebSocketUtil.base64(arrayOfByte);
/*     */     
/* 113 */     if (logger.isDebugEnabled()) {
/* 114 */       logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", str1, str3);
/*     */     }
/*     */     
/* 117 */     localDefaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 118 */     localDefaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 119 */     localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", str3);
/* 120 */     String str4 = paramFullHttpRequest.headers().get("Sec-WebSocket-Protocol");
/* 121 */     if (str4 != null) {
/* 122 */       String str5 = selectSubprotocol(str4);
/* 123 */       if (str5 == null) {
/* 124 */         if (logger.isDebugEnabled()) {
/* 125 */           logger.debug("Requested subprotocol(s) not supported: {}", str4);
/*     */         }
/*     */       } else {
/* 128 */         localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", str5);
/*     */       }
/*     */     }
/* 131 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 136 */     return new WebSocket07FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 141 */     return new WebSocket07FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker07.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */