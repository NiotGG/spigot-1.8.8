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
/*     */ import io.netty.handler.codec.http.HttpContentCompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import io.netty.handler.codec.http.HttpServerCodec;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WebSocketServerHandshaker
/*     */ {
/*  45 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String uri;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String[] subprotocols;
/*     */   
/*     */ 
/*     */ 
/*     */   private final WebSocketVersion version;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */ 
/*     */ 
/*     */   private String selectedSubprotocol;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final String SUB_PROTOCOL_WILDCARD = "*";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected WebSocketServerHandshaker(WebSocketVersion paramWebSocketVersion, String paramString1, String paramString2, int paramInt)
/*     */   {
/*  78 */     this.version = paramWebSocketVersion;
/*  79 */     this.uri = paramString1;
/*  80 */     if (paramString2 != null) {
/*  81 */       String[] arrayOfString = StringUtil.split(paramString2, ',');
/*  82 */       for (int i = 0; i < arrayOfString.length; i++) {
/*  83 */         arrayOfString[i] = arrayOfString[i].trim();
/*     */       }
/*  85 */       this.subprotocols = arrayOfString;
/*     */     } else {
/*  87 */       this.subprotocols = EmptyArrays.EMPTY_STRINGS;
/*     */     }
/*  89 */     this.maxFramePayloadLength = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String uri()
/*     */   {
/*  96 */     return this.uri;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Set<String> subprotocols()
/*     */   {
/* 103 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 104 */     Collections.addAll(localLinkedHashSet, this.subprotocols);
/* 105 */     return localLinkedHashSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public WebSocketVersion version()
/*     */   {
/* 112 */     return this.version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int maxFramePayloadLength()
/*     */   {
/* 121 */     return this.maxFramePayloadLength;
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
/*     */   public ChannelFuture handshake(Channel paramChannel, FullHttpRequest paramFullHttpRequest)
/*     */   {
/* 136 */     return handshake(paramChannel, paramFullHttpRequest, null, paramChannel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel paramChannel, FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders, final ChannelPromise paramChannelPromise)
/*     */   {
/* 158 */     if (logger.isDebugEnabled()) {
/* 159 */       logger.debug("{} WebSocket version {} server handshake", paramChannel, version());
/*     */     }
/* 161 */     FullHttpResponse localFullHttpResponse = newHandshakeResponse(paramFullHttpRequest, paramHttpHeaders);
/* 162 */     ChannelPipeline localChannelPipeline = paramChannel.pipeline();
/* 163 */     if (localChannelPipeline.get(HttpObjectAggregator.class) != null) {
/* 164 */       localChannelPipeline.remove(HttpObjectAggregator.class);
/*     */     }
/* 166 */     if (localChannelPipeline.get(HttpContentCompressor.class) != null) {
/* 167 */       localChannelPipeline.remove(HttpContentCompressor.class);
/*     */     }
/* 169 */     ChannelHandlerContext localChannelHandlerContext = localChannelPipeline.context(HttpRequestDecoder.class);
/*     */     final String str;
/* 171 */     if (localChannelHandlerContext == null)
/*     */     {
/* 173 */       localChannelHandlerContext = localChannelPipeline.context(HttpServerCodec.class);
/* 174 */       if (localChannelHandlerContext == null) {
/* 175 */         paramChannelPromise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
/*     */         
/* 177 */         return paramChannelPromise;
/*     */       }
/* 179 */       localChannelPipeline.addBefore(localChannelHandlerContext.name(), "wsdecoder", newWebsocketDecoder());
/* 180 */       localChannelPipeline.addBefore(localChannelHandlerContext.name(), "wsencoder", newWebSocketEncoder());
/* 181 */       str = localChannelHandlerContext.name();
/*     */     } else {
/* 183 */       localChannelPipeline.replace(localChannelHandlerContext.name(), "wsdecoder", newWebsocketDecoder());
/*     */       
/* 185 */       str = localChannelPipeline.context(HttpResponseEncoder.class).name();
/* 186 */       localChannelPipeline.addBefore(str, "wsencoder", newWebSocketEncoder());
/*     */     }
/* 188 */     paramChannel.writeAndFlush(localFullHttpResponse).addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 191 */         if (paramAnonymousChannelFuture.isSuccess()) {
/* 192 */           ChannelPipeline localChannelPipeline = paramAnonymousChannelFuture.channel().pipeline();
/* 193 */           localChannelPipeline.remove(str);
/* 194 */           paramChannelPromise.setSuccess();
/*     */         } else {
/* 196 */           paramChannelPromise.setFailure(paramAnonymousChannelFuture.cause());
/*     */         }
/*     */       }
/* 199 */     });
/* 200 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture close(Channel paramChannel, CloseWebSocketFrame paramCloseWebSocketFrame)
/*     */   {
/* 217 */     if (paramChannel == null) {
/* 218 */       throw new NullPointerException("channel");
/*     */     }
/* 220 */     return close(paramChannel, paramCloseWebSocketFrame, paramChannel.newPromise());
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
/* 234 */     if (paramChannel == null) {
/* 235 */       throw new NullPointerException("channel");
/*     */     }
/* 237 */     return paramChannel.writeAndFlush(paramCloseWebSocketFrame, paramChannelPromise).addListener(ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String selectSubprotocol(String paramString)
/*     */   {
/* 248 */     if ((paramString == null) || (this.subprotocols.length == 0)) {
/* 249 */       return null;
/*     */     }
/*     */     
/* 252 */     String[] arrayOfString1 = StringUtil.split(paramString, ',');
/* 253 */     for (String str1 : arrayOfString1) {
/* 254 */       String str2 = str1.trim();
/*     */       
/* 256 */       for (String str3 : this.subprotocols) {
/* 257 */         if (("*".equals(str3)) || (str2.equals(str3)))
/*     */         {
/* 259 */           this.selectedSubprotocol = str2;
/* 260 */           return str2;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 266 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String selectedSubprotocol()
/*     */   {
/* 276 */     return this.selectedSubprotocol;
/*     */   }
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */