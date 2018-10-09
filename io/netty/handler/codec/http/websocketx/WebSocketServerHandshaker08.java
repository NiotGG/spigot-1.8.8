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
/*     */ public class WebSocketServerHandshaker08
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_08_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker08(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  58 */     super(WebSocketVersion.V08, paramString1, paramString2, paramInt);
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
/* 100 */     if (paramHttpHeaders != null) {
/* 101 */       localDefaultFullHttpResponse.headers().add(paramHttpHeaders);
/*     */     }
/*     */     
/* 104 */     String str1 = paramFullHttpRequest.headers().get("Sec-WebSocket-Key");
/* 105 */     if (str1 == null) {
/* 106 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 108 */     String str2 = str1 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 109 */     byte[] arrayOfByte = WebSocketUtil.sha1(str2.getBytes(CharsetUtil.US_ASCII));
/* 110 */     String str3 = WebSocketUtil.base64(arrayOfByte);
/*     */     
/* 112 */     if (logger.isDebugEnabled()) {
/* 113 */       logger.debug("WebSocket version 08 server handshake key: {}, response: {}", str1, str3);
/*     */     }
/*     */     
/* 116 */     localDefaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 117 */     localDefaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 118 */     localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", str3);
/* 119 */     String str4 = paramFullHttpRequest.headers().get("Sec-WebSocket-Protocol");
/* 120 */     if (str4 != null) {
/* 121 */       String str5 = selectSubprotocol(str4);
/* 122 */       if (str5 == null) {
/* 123 */         if (logger.isDebugEnabled()) {
/* 124 */           logger.debug("Requested subprotocol(s) not supported: {}", str4);
/*     */         }
/*     */       } else {
/* 127 */         localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", str5);
/*     */       }
/*     */     }
/* 130 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 135 */     return new WebSocket08FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 140 */     return new WebSocket08FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker08.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */