/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   public static enum ServerHandshakeStateEvent
/*     */   {
/*  60 */     HANDSHAKE_COMPLETE;
/*     */     
/*     */     private ServerHandshakeStateEvent() {} }
/*  63 */   private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class.getName() + ".HANDSHAKER");
/*     */   
/*     */   private final String websocketPath;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   public WebSocketServerProtocolHandler(String paramString)
/*     */   {
/*  72 */     this(paramString, null, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String paramString1, String paramString2) {
/*  76 */     this(paramString1, paramString2, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String paramString1, String paramString2, boolean paramBoolean) {
/*  80 */     this(paramString1, paramString2, paramBoolean, 65536);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
/*     */   {
/*  85 */     this.websocketPath = paramString1;
/*  86 */     this.subprotocols = paramString2;
/*  87 */     this.allowExtensions = paramBoolean;
/*  88 */     this.maxFramePayloadLength = paramInt;
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/*  93 */     ChannelPipeline localChannelPipeline = paramChannelHandlerContext.pipeline();
/*  94 */     if (localChannelPipeline.get(WebSocketServerProtocolHandshakeHandler.class) == null)
/*     */     {
/*  96 */       paramChannelHandlerContext.pipeline().addBefore(paramChannelHandlerContext.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 104 */     if ((paramWebSocketFrame instanceof CloseWebSocketFrame)) {
/* 105 */       WebSocketServerHandshaker localWebSocketServerHandshaker = getHandshaker(paramChannelHandlerContext);
/* 106 */       if (localWebSocketServerHandshaker != null) {
/* 107 */         paramWebSocketFrame.retain();
/* 108 */         localWebSocketServerHandshaker.close(paramChannelHandlerContext.channel(), (CloseWebSocketFrame)paramWebSocketFrame);
/*     */       } else {
/* 110 */         paramChannelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
/*     */       }
/* 112 */       return;
/*     */     }
/* 114 */     super.decode(paramChannelHandlerContext, paramWebSocketFrame, paramList);
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */   {
/* 119 */     if ((paramThrowable instanceof WebSocketHandshakeException)) {
/* 120 */       DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(paramThrowable.getMessage().getBytes()));
/*     */       
/* 122 */       paramChannelHandlerContext.channel().writeAndFlush(localDefaultFullHttpResponse).addListener(ChannelFutureListener.CLOSE);
/*     */     } else {
/* 124 */       paramChannelHandlerContext.close();
/*     */     }
/*     */   }
/*     */   
/*     */   static WebSocketServerHandshaker getHandshaker(ChannelHandlerContext paramChannelHandlerContext) {
/* 129 */     return (WebSocketServerHandshaker)paramChannelHandlerContext.attr(HANDSHAKER_ATTR_KEY).get();
/*     */   }
/*     */   
/*     */   static void setHandshaker(ChannelHandlerContext paramChannelHandlerContext, WebSocketServerHandshaker paramWebSocketServerHandshaker) {
/* 133 */     paramChannelHandlerContext.attr(HANDSHAKER_ATTR_KEY).set(paramWebSocketServerHandshaker);
/*     */   }
/*     */   
/*     */   static ChannelHandler forbiddenHttpRequestResponder() {
/* 137 */     new ChannelInboundHandlerAdapter()
/*     */     {
/*     */       public void channelRead(ChannelHandlerContext paramAnonymousChannelHandlerContext, Object paramAnonymousObject) throws Exception {
/* 140 */         if ((paramAnonymousObject instanceof FullHttpRequest)) {
/* 141 */           ((FullHttpRequest)paramAnonymousObject).release();
/* 142 */           DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
/*     */           
/* 144 */           paramAnonymousChannelHandlerContext.channel().writeAndFlush(localDefaultFullHttpResponse);
/*     */         } else {
/* 146 */           paramAnonymousChannelHandlerContext.fireChannelRead(paramAnonymousObject);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */