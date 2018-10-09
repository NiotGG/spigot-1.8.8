/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerHandshaker00
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*  49 */   private static final Pattern BEGINNING_DIGIT = Pattern.compile("[^0-9]");
/*  50 */   private static final Pattern BEGINNING_SPACE = Pattern.compile("[^ ]");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocketServerHandshaker00(String paramString1, String paramString2, int paramInt)
/*     */   {
/*  65 */     super(WebSocketVersion.V00, paramString1, paramString2, paramInt);
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
/* 112 */     if ((!"Upgrade".equalsIgnoreCase(paramFullHttpRequest.headers().get("Connection"))) || (!"WebSocket".equalsIgnoreCase(paramFullHttpRequest.headers().get("Upgrade"))))
/*     */     {
/* 114 */       throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
/*     */     }
/*     */     
/*     */ 
/* 118 */     int i = (paramFullHttpRequest.headers().contains("Sec-WebSocket-Key1")) && (paramFullHttpRequest.headers().contains("Sec-WebSocket-Key2")) ? 1 : 0;
/*     */     
/*     */ 
/* 121 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, i != 0 ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
/*     */     
/* 123 */     if (paramHttpHeaders != null) {
/* 124 */       localDefaultFullHttpResponse.headers().add(paramHttpHeaders);
/*     */     }
/*     */     
/* 127 */     localDefaultFullHttpResponse.headers().add("Upgrade", "WebSocket");
/* 128 */     localDefaultFullHttpResponse.headers().add("Connection", "Upgrade");
/*     */     
/*     */     String str1;
/* 131 */     if (i != 0)
/*     */     {
/* 133 */       localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Origin", paramFullHttpRequest.headers().get("Origin"));
/* 134 */       localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Location", uri());
/* 135 */       str1 = paramFullHttpRequest.headers().get("Sec-WebSocket-Protocol");
/* 136 */       if (str1 != null) {
/* 137 */         str2 = selectSubprotocol(str1);
/* 138 */         if (str2 == null) {
/* 139 */           if (logger.isDebugEnabled()) {
/* 140 */             logger.debug("Requested subprotocol(s) not supported: {}", str1);
/*     */           }
/*     */         } else {
/* 143 */           localDefaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", str2);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 148 */       String str2 = paramFullHttpRequest.headers().get("Sec-WebSocket-Key1");
/* 149 */       String str3 = paramFullHttpRequest.headers().get("Sec-WebSocket-Key2");
/* 150 */       int j = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(str2).replaceAll("")) / BEGINNING_SPACE.matcher(str2).replaceAll("").length());
/*     */       
/* 152 */       int k = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(str3).replaceAll("")) / BEGINNING_SPACE.matcher(str3).replaceAll("").length());
/*     */       
/* 154 */       long l = paramFullHttpRequest.content().readLong();
/* 155 */       ByteBuf localByteBuf = Unpooled.buffer(16);
/* 156 */       localByteBuf.writeInt(j);
/* 157 */       localByteBuf.writeInt(k);
/* 158 */       localByteBuf.writeLong(l);
/* 159 */       localDefaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(localByteBuf.array()));
/*     */     }
/*     */     else {
/* 162 */       localDefaultFullHttpResponse.headers().add("WebSocket-Origin", paramFullHttpRequest.headers().get("Origin"));
/* 163 */       localDefaultFullHttpResponse.headers().add("WebSocket-Location", uri());
/* 164 */       str1 = paramFullHttpRequest.headers().get("WebSocket-Protocol");
/* 165 */       if (str1 != null) {
/* 166 */         localDefaultFullHttpResponse.headers().add("WebSocket-Protocol", selectSubprotocol(str1));
/*     */       }
/*     */     }
/* 169 */     return localDefaultFullHttpResponse;
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
/*     */   public ChannelFuture close(Channel paramChannel, CloseWebSocketFrame paramCloseWebSocketFrame, ChannelPromise paramChannelPromise)
/*     */   {
/* 182 */     return paramChannel.writeAndFlush(paramCloseWebSocketFrame, paramChannelPromise);
/*     */   }
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder()
/*     */   {
/* 187 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder()
/*     */   {
/* 192 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker00.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */