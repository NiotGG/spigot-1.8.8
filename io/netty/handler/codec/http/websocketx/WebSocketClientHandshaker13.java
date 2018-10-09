/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.URI;
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
/*     */ public class WebSocketClientHandshaker13
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String expectedChallengeResponseString;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean allowExtensions;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocketClientHandshaker13(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean, HttpHeaders paramHttpHeaders, int paramInt)
/*     */   {
/*  69 */     super(paramURI, paramWebSocketVersion, paramString, paramHttpHeaders, paramInt);
/*  70 */     this.allowExtensions = paramBoolean;
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
/*     */   protected FullHttpRequest newHandshakeRequest()
/*     */   {
/*  94 */     URI localURI = uri();
/*  95 */     String str1 = localURI.getPath();
/*  96 */     if ((localURI.getQuery() != null) && (!localURI.getQuery().isEmpty())) {
/*  97 */       str1 = localURI.getPath() + '?' + localURI.getQuery();
/*     */     }
/*     */     
/* 100 */     if ((str1 == null) || (str1.isEmpty())) {
/* 101 */       str1 = "/";
/*     */     }
/*     */     
/*     */ 
/* 105 */     byte[] arrayOfByte1 = WebSocketUtil.randomBytes(16);
/* 106 */     String str2 = WebSocketUtil.base64(arrayOfByte1);
/*     */     
/* 108 */     String str3 = str2 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 109 */     byte[] arrayOfByte2 = WebSocketUtil.sha1(str3.getBytes(CharsetUtil.US_ASCII));
/* 110 */     this.expectedChallengeResponseString = WebSocketUtil.base64(arrayOfByte2);
/*     */     
/* 112 */     if (logger.isDebugEnabled()) {
/* 113 */       logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", str2, this.expectedChallengeResponseString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 119 */     int i = localURI.getPort();
/*     */     
/*     */ 
/* 122 */     if (i == -1) {
/* 123 */       if ("wss".equals(localURI.getScheme())) {
/* 124 */         i = 443;
/*     */       } else {
/* 126 */         i = 80;
/*     */       }
/*     */     }
/*     */     
/* 130 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, str1);
/* 131 */     HttpHeaders localHttpHeaders = localDefaultFullHttpRequest.headers();
/*     */     
/* 133 */     localHttpHeaders.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", str2).add("Host", localURI.getHost() + ':' + i);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 138 */     String str4 = "http://" + localURI.getHost();
/* 139 */     if ((i != 80) && (i != 443))
/*     */     {
/*     */ 
/* 142 */       str4 = str4 + ':' + i;
/*     */     }
/* 144 */     localHttpHeaders.add("Sec-WebSocket-Origin", str4);
/*     */     
/* 146 */     String str5 = expectedSubprotocol();
/* 147 */     if ((str5 != null) && (!str5.isEmpty())) {
/* 148 */       localHttpHeaders.add("Sec-WebSocket-Protocol", str5);
/*     */     }
/*     */     
/* 151 */     localHttpHeaders.add("Sec-WebSocket-Version", "13");
/*     */     
/* 153 */     if (this.customHeaders != null) {
/* 154 */       localHttpHeaders.add(this.customHeaders);
/*     */     }
/* 156 */     return localDefaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse paramFullHttpResponse)
/*     */   {
/* 178 */     HttpResponseStatus localHttpResponseStatus = HttpResponseStatus.SWITCHING_PROTOCOLS;
/* 179 */     HttpHeaders localHttpHeaders = paramFullHttpResponse.headers();
/*     */     
/* 181 */     if (!paramFullHttpResponse.getStatus().equals(localHttpResponseStatus)) {
/* 182 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + paramFullHttpResponse.getStatus());
/*     */     }
/*     */     
/* 185 */     String str1 = localHttpHeaders.get("Upgrade");
/* 186 */     if (!"WebSocket".equalsIgnoreCase(str1)) {
/* 187 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + str1);
/*     */     }
/*     */     
/* 190 */     String str2 = localHttpHeaders.get("Connection");
/* 191 */     if (!"Upgrade".equalsIgnoreCase(str2)) {
/* 192 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + str2);
/*     */     }
/*     */     
/* 195 */     String str3 = localHttpHeaders.get("Sec-WebSocket-Accept");
/* 196 */     if ((str3 == null) || (!str3.equals(this.expectedChallengeResponseString))) {
/* 197 */       throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { str3, this.expectedChallengeResponseString }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 204 */     return new WebSocket13FrameDecoder(false, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 209 */     return new WebSocket13FrameEncoder(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */