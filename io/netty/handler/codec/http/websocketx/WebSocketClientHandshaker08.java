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
/*     */ public class WebSocketClientHandshaker08
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker08.class);
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
/*     */   public WebSocketClientHandshaker08(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean, HttpHeaders paramHttpHeaders, int paramInt)
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
/* 113 */       logger.debug("WebSocket version 08 client handshake key: {}, expected response: {}", str2, this.expectedChallengeResponseString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 119 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, str1);
/* 120 */     HttpHeaders localHttpHeaders = localDefaultFullHttpRequest.headers();
/*     */     
/* 122 */     localHttpHeaders.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", str2).add("Host", localURI.getHost());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 127 */     int i = localURI.getPort();
/* 128 */     String str4 = "http://" + localURI.getHost();
/* 129 */     if ((i != 80) && (i != 443))
/*     */     {
/*     */ 
/* 132 */       str4 = str4 + ':' + i;
/*     */     }
/* 134 */     localHttpHeaders.add("Sec-WebSocket-Origin", str4);
/*     */     
/* 136 */     String str5 = expectedSubprotocol();
/* 137 */     if ((str5 != null) && (!str5.isEmpty())) {
/* 138 */       localHttpHeaders.add("Sec-WebSocket-Protocol", str5);
/*     */     }
/*     */     
/* 141 */     localHttpHeaders.add("Sec-WebSocket-Version", "8");
/*     */     
/* 143 */     if (this.customHeaders != null) {
/* 144 */       localHttpHeaders.add(this.customHeaders);
/*     */     }
/* 146 */     return localDefaultFullHttpRequest;
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
/* 168 */     HttpResponseStatus localHttpResponseStatus = HttpResponseStatus.SWITCHING_PROTOCOLS;
/* 169 */     HttpHeaders localHttpHeaders = paramFullHttpResponse.headers();
/*     */     
/* 171 */     if (!paramFullHttpResponse.getStatus().equals(localHttpResponseStatus)) {
/* 172 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + paramFullHttpResponse.getStatus());
/*     */     }
/*     */     
/* 175 */     String str1 = localHttpHeaders.get("Upgrade");
/* 176 */     if (!"WebSocket".equalsIgnoreCase(str1)) {
/* 177 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + str1);
/*     */     }
/*     */     
/* 180 */     String str2 = localHttpHeaders.get("Connection");
/* 181 */     if (!"Upgrade".equalsIgnoreCase(str2)) {
/* 182 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + str2);
/*     */     }
/*     */     
/* 185 */     String str3 = localHttpHeaders.get("Sec-WebSocket-Accept");
/* 186 */     if ((str3 == null) || (!str3.equals(this.expectedChallengeResponseString))) {
/* 187 */       throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { str3, this.expectedChallengeResponseString }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 194 */     return new WebSocket08FrameDecoder(false, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 199 */     return new WebSocket08FrameEncoder(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker08.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */