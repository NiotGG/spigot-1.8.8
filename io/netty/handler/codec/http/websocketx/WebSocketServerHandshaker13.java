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
/*     */ public class WebSocketServerHandshaker13
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_13_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker13(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  57 */     super(WebSocketVersion.V13, paramString1, paramString2, paramInt);
/*  58 */     this.allowExtensions = paramBoolean;
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
/*  97 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
/*  98 */     if (paramHttpHeaders != null) {
/*  99 */       localDefaultFullHttpResponse.headers().add(paramHttpHeaders);
/*     */     }
/*     */     
/* 102 */     String str1 = paramFullHttpRequest.headers().get("Sec-WebSocket-Key");
/* 103 */     if (str1 == null) {
/* 104 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 106 */     String str2 = str1 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 107 */     byte[] arrayOfByte = WebSocketUtil.sha1(str2.getBytes(CharsetUtil.US_ASCII));
/* 108 */     String str3 = WebSocketUtil.base64(arrayOfByte);
/*     */     
/* 110 */     if (logger.isDebugEnabled()) {
/* 111 */       logger.debug("WebSocket version 13 server handshake key: {}, response: {}", str1, str3);
/*     */     }
/*     */     
/* 114 */     localDefaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 115 */     localDefaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 116 */     localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", str3);
/* 117 */     String str4 = paramFullHttpRequest.headers().get("Sec-WebSocket-Protocol");
/* 118 */     if (str4 != null) {
/* 119 */       String str5 = selectSubprotocol(str4);
/* 120 */       if (str5 == null) {
/* 121 */         if (logger.isDebugEnabled()) {
/* 122 */           logger.debug("Requested subprotocol(s) not supported: {}", str4);
/*     */         }
/*     */       } else {
/* 125 */         localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", str5);
/*     */       }
/*     */     }
/* 128 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 133 */     return new WebSocket13FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 138 */     return new WebSocket13FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */