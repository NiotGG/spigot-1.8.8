/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientHandshaker00
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*     */   private ByteBuf expectedChallengeResponseBytes;
/*     */   
/*     */   public WebSocketClientHandshaker00(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, HttpHeaders paramHttpHeaders, int paramInt)
/*     */   {
/*  64 */     super(paramURI, paramWebSocketVersion, paramString, paramHttpHeaders, paramInt);
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
/*  88 */     int i = WebSocketUtil.randomNumber(1, 12);
/*  89 */     int j = WebSocketUtil.randomNumber(1, 12);
/*     */     
/*  91 */     int k = Integer.MAX_VALUE / i;
/*  92 */     int m = Integer.MAX_VALUE / j;
/*     */     
/*  94 */     int n = WebSocketUtil.randomNumber(0, k);
/*  95 */     int i1 = WebSocketUtil.randomNumber(0, m);
/*     */     
/*  97 */     int i2 = n * i;
/*  98 */     int i3 = i1 * j;
/*     */     
/* 100 */     String str1 = Integer.toString(i2);
/* 101 */     String str2 = Integer.toString(i3);
/*     */     
/* 103 */     str1 = insertRandomCharacters(str1);
/* 104 */     str2 = insertRandomCharacters(str2);
/*     */     
/* 106 */     str1 = insertSpaces(str1, i);
/* 107 */     str2 = insertSpaces(str2, j);
/*     */     
/* 109 */     byte[] arrayOfByte1 = WebSocketUtil.randomBytes(8);
/*     */     
/* 111 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
/* 112 */     localByteBuffer.putInt(n);
/* 113 */     byte[] arrayOfByte2 = localByteBuffer.array();
/* 114 */     localByteBuffer = ByteBuffer.allocate(4);
/* 115 */     localByteBuffer.putInt(i1);
/* 116 */     byte[] arrayOfByte3 = localByteBuffer.array();
/*     */     
/* 118 */     byte[] arrayOfByte4 = new byte[16];
/* 119 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, 4);
/* 120 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 4, 4);
/* 121 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte4, 8, 8);
/* 122 */     this.expectedChallengeResponseBytes = Unpooled.wrappedBuffer(WebSocketUtil.md5(arrayOfByte4));
/*     */     
/*     */ 
/* 125 */     URI localURI = uri();
/* 126 */     String str3 = localURI.getPath();
/* 127 */     if ((localURI.getQuery() != null) && (!localURI.getQuery().isEmpty())) {
/* 128 */       str3 = localURI.getPath() + '?' + localURI.getQuery();
/*     */     }
/*     */     
/* 131 */     if ((str3 == null) || (str3.isEmpty())) {
/* 132 */       str3 = "/";
/*     */     }
/*     */     
/*     */ 
/* 136 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, str3);
/* 137 */     HttpHeaders localHttpHeaders = localDefaultFullHttpRequest.headers();
/* 138 */     localHttpHeaders.add("Upgrade", "WebSocket").add("Connection", "Upgrade").add("Host", localURI.getHost());
/*     */     
/*     */ 
/*     */ 
/* 142 */     int i4 = localURI.getPort();
/* 143 */     String str4 = "http://" + localURI.getHost();
/* 144 */     if ((i4 != 80) && (i4 != 443))
/*     */     {
/*     */ 
/* 147 */       str4 = str4 + ':' + i4;
/*     */     }
/*     */     
/* 150 */     localHttpHeaders.add("Origin", str4).add("Sec-WebSocket-Key1", str1).add("Sec-WebSocket-Key2", str2);
/*     */     
/*     */ 
/*     */ 
/* 154 */     String str5 = expectedSubprotocol();
/* 155 */     if ((str5 != null) && (!str5.isEmpty())) {
/* 156 */       localHttpHeaders.add("Sec-WebSocket-Protocol", str5);
/*     */     }
/*     */     
/* 159 */     if (this.customHeaders != null) {
/* 160 */       localHttpHeaders.add(this.customHeaders);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 165 */     localHttpHeaders.set("Content-Length", Integer.valueOf(arrayOfByte1.length));
/* 166 */     localDefaultFullHttpRequest.content().writeBytes(arrayOfByte1);
/* 167 */     return localDefaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse paramFullHttpResponse)
/*     */   {
/* 192 */     HttpResponseStatus localHttpResponseStatus = new HttpResponseStatus(101, "WebSocket Protocol Handshake");
/*     */     
/* 194 */     if (!paramFullHttpResponse.getStatus().equals(localHttpResponseStatus)) {
/* 195 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + paramFullHttpResponse.getStatus());
/*     */     }
/*     */     
/* 198 */     HttpHeaders localHttpHeaders = paramFullHttpResponse.headers();
/*     */     
/* 200 */     String str1 = localHttpHeaders.get("Upgrade");
/* 201 */     if (!"WebSocket".equalsIgnoreCase(str1)) {
/* 202 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + str1);
/*     */     }
/*     */     
/*     */ 
/* 206 */     String str2 = localHttpHeaders.get("Connection");
/* 207 */     if (!"Upgrade".equalsIgnoreCase(str2)) {
/* 208 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + str2);
/*     */     }
/*     */     
/*     */ 
/* 212 */     ByteBuf localByteBuf = paramFullHttpResponse.content();
/* 213 */     if (!localByteBuf.equals(this.expectedChallengeResponseBytes)) {
/* 214 */       throw new WebSocketHandshakeException("Invalid challenge");
/*     */     }
/*     */   }
/*     */   
/*     */   private static String insertRandomCharacters(String paramString) {
/* 219 */     int i = WebSocketUtil.randomNumber(1, 12);
/*     */     
/* 221 */     char[] arrayOfChar = new char[i];
/* 222 */     int j = 0;
/* 223 */     while (j < i) {
/* 224 */       k = (int)(Math.random() * 126.0D + 33.0D);
/* 225 */       if (((33 < k) && (k < 47)) || ((58 < k) && (k < 126))) {
/* 226 */         arrayOfChar[j] = ((char)k);
/* 227 */         j++;
/*     */       }
/*     */     }
/*     */     
/* 231 */     for (int k = 0; k < i; k++) {
/* 232 */       int m = WebSocketUtil.randomNumber(0, paramString.length());
/* 233 */       String str1 = paramString.substring(0, m);
/* 234 */       String str2 = paramString.substring(m);
/* 235 */       paramString = str1 + arrayOfChar[k] + str2;
/*     */     }
/*     */     
/* 238 */     return paramString;
/*     */   }
/*     */   
/*     */   private static String insertSpaces(String paramString, int paramInt) {
/* 242 */     for (int i = 0; i < paramInt; i++) {
/* 243 */       int j = WebSocketUtil.randomNumber(1, paramString.length() - 1);
/* 244 */       String str1 = paramString.substring(0, j);
/* 245 */       String str2 = paramString.substring(j);
/* 246 */       paramString = str1 + ' ' + str2;
/*     */     }
/*     */     
/* 249 */     return paramString;
/*     */   }
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 254 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 259 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker00.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */