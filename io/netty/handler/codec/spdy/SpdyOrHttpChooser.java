/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandler;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import io.netty.handler.ssl.SslHandler;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpdyOrHttpChooser
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maxSpdyContentLength;
/*     */   private final int maxHttpContentLength;
/*     */   
/*     */   public static enum SelectedProtocol
/*     */   {
/*  43 */     SPDY_3_1("spdy/3.1"), 
/*  44 */     HTTP_1_1("http/1.1"), 
/*  45 */     HTTP_1_0("http/1.0"), 
/*  46 */     UNKNOWN("Unknown");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private SelectedProtocol(String paramString) {
/*  51 */       this.name = paramString;
/*     */     }
/*     */     
/*     */     public String protocolName() {
/*  55 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public static SelectedProtocol protocol(String paramString)
/*     */     {
/*  66 */       for (SelectedProtocol localSelectedProtocol : ) {
/*  67 */         if (localSelectedProtocol.protocolName().equals(paramString)) {
/*  68 */           return localSelectedProtocol;
/*     */         }
/*     */       }
/*  71 */       return UNKNOWN;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SpdyOrHttpChooser(int paramInt1, int paramInt2)
/*     */   {
/*  79 */     this.maxSpdyContentLength = paramInt1;
/*  80 */     this.maxHttpContentLength = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SelectedProtocol getProtocol(SSLEngine paramSSLEngine)
/*     */   {
/*  89 */     String[] arrayOfString = StringUtil.split(paramSSLEngine.getSession().getProtocol(), ':');
/*  90 */     if (arrayOfString.length < 2)
/*     */     {
/*  92 */       return SelectedProtocol.HTTP_1_1;
/*     */     }
/*  94 */     SelectedProtocol localSelectedProtocol = SelectedProtocol.protocol(arrayOfString[1]);
/*  95 */     return localSelectedProtocol;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/* 100 */     if (initPipeline(paramChannelHandlerContext))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 105 */       paramChannelHandlerContext.pipeline().remove(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean initPipeline(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 112 */     SslHandler localSslHandler = (SslHandler)paramChannelHandlerContext.pipeline().get(SslHandler.class);
/* 113 */     if (localSslHandler == null)
/*     */     {
/* 115 */       throw new IllegalStateException("SslHandler is needed for SPDY");
/*     */     }
/*     */     
/* 118 */     SelectedProtocol localSelectedProtocol = getProtocol(localSslHandler.engine());
/* 119 */     switch (localSelectedProtocol)
/*     */     {
/*     */     case UNKNOWN: 
/* 122 */       return false;
/*     */     case SPDY_3_1: 
/* 124 */       addSpdyHandlers(paramChannelHandlerContext, SpdyVersion.SPDY_3_1);
/* 125 */       break;
/*     */     case HTTP_1_0: 
/*     */     case HTTP_1_1: 
/* 128 */       addHttpHandlers(paramChannelHandlerContext);
/* 129 */       break;
/*     */     default: 
/* 131 */       throw new IllegalStateException("Unknown SelectedProtocol");
/*     */     }
/* 133 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void addSpdyHandlers(ChannelHandlerContext paramChannelHandlerContext, SpdyVersion paramSpdyVersion)
/*     */   {
/* 140 */     ChannelPipeline localChannelPipeline = paramChannelHandlerContext.pipeline();
/* 141 */     localChannelPipeline.addLast("spdyFrameCodec", new SpdyFrameCodec(paramSpdyVersion));
/* 142 */     localChannelPipeline.addLast("spdySessionHandler", new SpdySessionHandler(paramSpdyVersion, true));
/* 143 */     localChannelPipeline.addLast("spdyHttpEncoder", new SpdyHttpEncoder(paramSpdyVersion));
/* 144 */     localChannelPipeline.addLast("spdyHttpDecoder", new SpdyHttpDecoder(paramSpdyVersion, this.maxSpdyContentLength));
/* 145 */     localChannelPipeline.addLast("spdyStreamIdHandler", new SpdyHttpResponseStreamIdHandler());
/* 146 */     localChannelPipeline.addLast("httpRequestHandler", createHttpRequestHandlerForSpdy());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void addHttpHandlers(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 153 */     ChannelPipeline localChannelPipeline = paramChannelHandlerContext.pipeline();
/* 154 */     localChannelPipeline.addLast("httpRequestDecoder", new HttpRequestDecoder());
/* 155 */     localChannelPipeline.addLast("httpResponseEncoder", new HttpResponseEncoder());
/* 156 */     localChannelPipeline.addLast("httpChunkAggregator", new HttpObjectAggregator(this.maxHttpContentLength));
/* 157 */     localChannelPipeline.addLast("httpRequestHandler", createHttpRequestHandlerForHttp());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract ChannelInboundHandler createHttpRequestHandlerForHttp();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ChannelInboundHandler createHttpRequestHandlerForSpdy()
/*     */   {
/* 175 */     return createHttpRequestHandlerForHttp();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyOrHttpChooser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */