/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpClientCodec;
/*     */ import io.netty.handler.codec.http.HttpContentDecompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpRequestEncoder;
/*     */ import io.netty.handler.codec.http.HttpResponseDecoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WebSocketClientHandshaker
/*     */ {
/*     */   private final URI uri;
/*     */   private final WebSocketVersion version;
/*     */   private volatile boolean handshakeComplete;
/*     */   private final String expectedSubprotocol;
/*     */   private volatile String actualSubprotocol;
/*     */   protected final HttpHeaders customHeaders;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   protected WebSocketClientHandshaker(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, HttpHeaders paramHttpHeaders, int paramInt)
/*     */   {
/*  70 */     this.uri = paramURI;
/*  71 */     this.version = paramWebSocketVersion;
/*  72 */     this.expectedSubprotocol = paramString;
/*  73 */     this.customHeaders = paramHttpHeaders;
/*  74 */     this.maxFramePayloadLength = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public URI uri()
/*     */   {
/*  81 */     return this.uri;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public WebSocketVersion version()
/*     */   {
/*  88 */     return this.version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int maxFramePayloadLength()
/*     */   {
/*  95 */     return this.maxFramePayloadLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isHandshakeComplete()
/*     */   {
/* 102 */     return this.handshakeComplete;
/*     */   }
/*     */   
/*     */   private void setHandshakeComplete() {
/* 106 */     this.handshakeComplete = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String expectedSubprotocol()
/*     */   {
/* 113 */     return this.expectedSubprotocol;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String actualSubprotocol()
/*     */   {
/* 121 */     return this.actualSubprotocol;
/*     */   }
/*     */   
/*     */   private void setActualSubprotocol(String paramString) {
/* 125 */     this.actualSubprotocol = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture handshake(Channel paramChannel)
/*     */   {
/* 135 */     if (paramChannel == null) {
/* 136 */       throw new NullPointerException("channel");
/*     */     }
/* 138 */     return handshake(paramChannel, paramChannel.newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ChannelFuture handshake(Channel paramChannel, final ChannelPromise paramChannelPromise)
/*     */   {
/* 150 */     FullHttpRequest localFullHttpRequest = newHandshakeRequest();
/*     */     
/* 152 */     HttpResponseDecoder localHttpResponseDecoder = (HttpResponseDecoder)paramChannel.pipeline().get(HttpResponseDecoder.class);
/* 153 */     if (localHttpResponseDecoder == null) {
/* 154 */       HttpClientCodec localHttpClientCodec = (HttpClientCodec)paramChannel.pipeline().get(HttpClientCodec.class);
/* 155 */       if (localHttpClientCodec == null) {
/* 156 */         paramChannelPromise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
/*     */         
/* 158 */         return paramChannelPromise;
/*     */       }
/*     */     }
/*     */     
/* 162 */     paramChannel.writeAndFlush(localFullHttpRequest).addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) {
/* 165 */         if (paramAnonymousChannelFuture.isSuccess()) {
/* 166 */           ChannelPipeline localChannelPipeline = paramAnonymousChannelFuture.channel().pipeline();
/* 167 */           ChannelHandlerContext localChannelHandlerContext = localChannelPipeline.context(HttpRequestEncoder.class);
/* 168 */           if (localChannelHandlerContext == null) {
/* 169 */             localChannelHandlerContext = localChannelPipeline.context(HttpClientCodec.class);
/*     */           }
/* 171 */           if (localChannelHandlerContext == null) {
/* 172 */             paramChannelPromise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
/*     */             
/* 174 */             return;
/*     */           }
/* 176 */           localChannelPipeline.addAfter(localChannelHandlerContext.name(), "ws-encoder", WebSocketClientHandshaker.this.newWebSocketEncoder());
/*     */           
/* 178 */           paramChannelPromise.setSuccess();
/*     */         } else {
/* 180 */           paramChannelPromise.setFailure(paramAnonymousChannelFuture.cause());
/*     */         }
/*     */       }
/* 183 */     });
/* 184 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract FullHttpRequest newHandshakeRequest();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void finishHandshake(Channel paramChannel, FullHttpResponse paramFullHttpResponse)
/*     */   {
/* 201 */     verify(paramFullHttpResponse);
/* 202 */     setActualSubprotocol(paramFullHttpResponse.headers().get("Sec-WebSocket-Protocol"));
/* 203 */     setHandshakeComplete();
/*     */     
/* 205 */     ChannelPipeline localChannelPipeline = paramChannel.pipeline();
/*     */     
/* 207 */     HttpContentDecompressor localHttpContentDecompressor = (HttpContentDecompressor)localChannelPipeline.get(HttpContentDecompressor.class);
/* 208 */     if (localHttpContentDecompressor != null) {
/* 209 */       localChannelPipeline.remove(localHttpContentDecompressor);
/*     */     }
/*     */     
/* 212 */     ChannelHandlerContext localChannelHandlerContext = localChannelPipeline.context(HttpResponseDecoder.class);
/* 213 */     if (localChannelHandlerContext == null) {
/* 214 */       localChannelHandlerContext = localChannelPipeline.context(HttpClientCodec.class);
/* 215 */       if (localChannelHandlerContext == null) {
/* 216 */         throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
/*     */       }
/*     */       
/* 219 */       localChannelPipeline.replace(localChannelHandlerContext.name(), "ws-decoder", newWebsocketDecoder());
/*     */     } else {
/* 221 */       if (localChannelPipeline.get(HttpRequestEncoder.class) != null) {
/* 222 */         localChannelPipeline.remove(HttpRequestEncoder.class);
/*     */       }
/* 224 */       localChannelPipeline.replace(localChannelHandlerContext.name(), "ws-decoder", newWebsocketDecoder());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void verify(FullHttpResponse paramFullHttpResponse);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture close(Channel paramChannel, CloseWebSocketFrame paramCloseWebSocketFrame)
/*     */   {
/* 253 */     if (paramChannel == null) {
/* 254 */       throw new NullPointerException("channel");
/*     */     }
/* 256 */     return close(paramChannel, paramCloseWebSocketFrame, paramChannel.newPromise());
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
/*     */   public ChannelFuture close(Channel paramChannel, CloseWebSocketFrame paramCloseWebSocketFrame, ChannelPromise paramChannelPromise)
/*     */   {
/* 270 */     if (paramChannel == null) {
/* 271 */       throw new NullPointerException("channel");
/*     */     }
/* 273 */     return paramChannel.writeAndFlush(paramCloseWebSocketFrame, paramChannelPromise);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */