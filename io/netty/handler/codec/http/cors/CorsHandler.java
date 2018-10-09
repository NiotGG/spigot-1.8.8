/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ 
/*     */ 
/*     */ public class CorsHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CorsHandler.class);
/*     */   private final CorsConfig config;
/*     */   private HttpRequest request;
/*     */   
/*     */   public CorsHandler(CorsConfig paramCorsConfig)
/*     */   {
/*  47 */     this.config = paramCorsConfig;
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/*  52 */     if ((this.config.isCorsSupportEnabled()) && ((paramObject instanceof HttpRequest))) {
/*  53 */       this.request = ((HttpRequest)paramObject);
/*  54 */       if (isPreflightRequest(this.request)) {
/*  55 */         handlePreflight(paramChannelHandlerContext, this.request);
/*  56 */         return;
/*     */       }
/*  58 */       if ((this.config.isShortCurcuit()) && (!validateOrigin())) {
/*  59 */         forbidden(paramChannelHandlerContext, this.request);
/*  60 */         return;
/*     */       }
/*     */     }
/*  63 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */   private void handlePreflight(ChannelHandlerContext paramChannelHandlerContext, HttpRequest paramHttpRequest) {
/*  67 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(paramHttpRequest.getProtocolVersion(), HttpResponseStatus.OK);
/*  68 */     if (setOrigin(localDefaultFullHttpResponse)) {
/*  69 */       setAllowMethods(localDefaultFullHttpResponse);
/*  70 */       setAllowHeaders(localDefaultFullHttpResponse);
/*  71 */       setAllowCredentials(localDefaultFullHttpResponse);
/*  72 */       setMaxAge(localDefaultFullHttpResponse);
/*  73 */       setPreflightHeaders(localDefaultFullHttpResponse);
/*     */     }
/*  75 */     paramChannelHandlerContext.writeAndFlush(localDefaultFullHttpResponse).addListener(ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setPreflightHeaders(HttpResponse paramHttpResponse)
/*     */   {
/*  85 */     paramHttpResponse.headers().add(this.config.preflightResponseHeaders());
/*     */   }
/*     */   
/*     */   private boolean setOrigin(HttpResponse paramHttpResponse) {
/*  89 */     String str = this.request.headers().get("Origin");
/*  90 */     if (str != null) {
/*  91 */       if (("null".equals(str)) && (this.config.isNullOriginAllowed())) {
/*  92 */         setAnyOrigin(paramHttpResponse);
/*  93 */         return true;
/*     */       }
/*  95 */       if (this.config.isAnyOriginSupported()) {
/*  96 */         if (this.config.isCredentialsAllowed()) {
/*  97 */           echoRequestOrigin(paramHttpResponse);
/*  98 */           setVaryHeader(paramHttpResponse);
/*     */         } else {
/* 100 */           setAnyOrigin(paramHttpResponse);
/*     */         }
/* 102 */         return true;
/*     */       }
/* 104 */       if (this.config.origins().contains(str)) {
/* 105 */         setOrigin(paramHttpResponse, str);
/* 106 */         setVaryHeader(paramHttpResponse);
/* 107 */         return true;
/*     */       }
/* 109 */       logger.debug("Request origin [" + str + "] was not among the configured origins " + this.config.origins());
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   private boolean validateOrigin() {
/* 115 */     if (this.config.isAnyOriginSupported()) {
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     String str = this.request.headers().get("Origin");
/* 120 */     if (str == null)
/*     */     {
/* 122 */       return true;
/*     */     }
/*     */     
/* 125 */     if (("null".equals(str)) && (this.config.isNullOriginAllowed())) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     return this.config.origins().contains(str);
/*     */   }
/*     */   
/*     */   private void echoRequestOrigin(HttpResponse paramHttpResponse) {
/* 133 */     setOrigin(paramHttpResponse, this.request.headers().get("Origin"));
/*     */   }
/*     */   
/*     */   private static void setVaryHeader(HttpResponse paramHttpResponse) {
/* 137 */     paramHttpResponse.headers().set("Vary", "Origin");
/*     */   }
/*     */   
/*     */   private static void setAnyOrigin(HttpResponse paramHttpResponse) {
/* 141 */     setOrigin(paramHttpResponse, "*");
/*     */   }
/*     */   
/*     */   private static void setOrigin(HttpResponse paramHttpResponse, String paramString) {
/* 145 */     paramHttpResponse.headers().set("Access-Control-Allow-Origin", paramString);
/*     */   }
/*     */   
/*     */   private void setAllowCredentials(HttpResponse paramHttpResponse) {
/* 149 */     if (this.config.isCredentialsAllowed()) {
/* 150 */       paramHttpResponse.headers().set("Access-Control-Allow-Credentials", "true");
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isPreflightRequest(HttpRequest paramHttpRequest) {
/* 155 */     HttpHeaders localHttpHeaders = paramHttpRequest.headers();
/* 156 */     return (paramHttpRequest.getMethod().equals(HttpMethod.OPTIONS)) && (localHttpHeaders.contains("Origin")) && (localHttpHeaders.contains("Access-Control-Request-Method"));
/*     */   }
/*     */   
/*     */ 
/*     */   private void setExposeHeaders(HttpResponse paramHttpResponse)
/*     */   {
/* 162 */     if (!this.config.exposedHeaders().isEmpty()) {
/* 163 */       paramHttpResponse.headers().set("Access-Control-Expose-Headers", this.config.exposedHeaders());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setAllowMethods(HttpResponse paramHttpResponse) {
/* 168 */     paramHttpResponse.headers().set("Access-Control-Allow-Methods", this.config.allowedRequestMethods());
/*     */   }
/*     */   
/*     */   private void setAllowHeaders(HttpResponse paramHttpResponse) {
/* 172 */     paramHttpResponse.headers().set("Access-Control-Allow-Headers", this.config.allowedRequestHeaders());
/*     */   }
/*     */   
/*     */   private void setMaxAge(HttpResponse paramHttpResponse) {
/* 176 */     paramHttpResponse.headers().set("Access-Control-Max-Age", Long.valueOf(this.config.maxAge()));
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 182 */     if ((this.config.isCorsSupportEnabled()) && ((paramObject instanceof HttpResponse))) {
/* 183 */       HttpResponse localHttpResponse = (HttpResponse)paramObject;
/* 184 */       if (setOrigin(localHttpResponse)) {
/* 185 */         setAllowCredentials(localHttpResponse);
/* 186 */         setAllowHeaders(localHttpResponse);
/* 187 */         setExposeHeaders(localHttpResponse);
/*     */       }
/*     */     }
/* 190 */     paramChannelHandlerContext.writeAndFlush(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */   {
/* 195 */     logger.error("Caught error in CorsHandler", paramThrowable);
/* 196 */     paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*     */   }
/*     */   
/*     */   private static void forbidden(ChannelHandlerContext paramChannelHandlerContext, HttpRequest paramHttpRequest) {
/* 200 */     paramChannelHandlerContext.writeAndFlush(new DefaultFullHttpResponse(paramHttpRequest.getProtocolVersion(), HttpResponseStatus.FORBIDDEN)).addListener(ChannelFutureListener.CLOSE);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\cors\CorsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */