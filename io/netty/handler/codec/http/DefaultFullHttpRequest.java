/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFullHttpRequest
/*     */   extends DefaultHttpRequest
/*     */   implements FullHttpRequest
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeader;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion paramHttpVersion, HttpMethod paramHttpMethod, String paramString)
/*     */   {
/*  30 */     this(paramHttpVersion, paramHttpMethod, paramString, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion paramHttpVersion, HttpMethod paramHttpMethod, String paramString, ByteBuf paramByteBuf) {
/*  34 */     this(paramHttpVersion, paramHttpMethod, paramString, paramByteBuf, true);
/*     */   }
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion paramHttpVersion, HttpMethod paramHttpMethod, String paramString, ByteBuf paramByteBuf, boolean paramBoolean)
/*     */   {
/*  39 */     super(paramHttpVersion, paramHttpMethod, paramString, paramBoolean);
/*  40 */     if (paramByteBuf == null) {
/*  41 */       throw new NullPointerException("content");
/*     */     }
/*  43 */     this.content = paramByteBuf;
/*  44 */     this.trailingHeader = new DefaultHttpHeaders(paramBoolean);
/*  45 */     this.validateHeaders = paramBoolean;
/*     */   }
/*     */   
/*     */   public HttpHeaders trailingHeaders()
/*     */   {
/*  50 */     return this.trailingHeader;
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/*  55 */     return this.content;
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/*  60 */     return this.content.refCnt();
/*     */   }
/*     */   
/*     */   public FullHttpRequest retain()
/*     */   {
/*  65 */     this.content.retain();
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpRequest retain(int paramInt)
/*     */   {
/*  71 */     this.content.retain(paramInt);
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/*  77 */     return this.content.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/*  82 */     return this.content.release(paramInt);
/*     */   }
/*     */   
/*     */   public FullHttpRequest setProtocolVersion(HttpVersion paramHttpVersion)
/*     */   {
/*  87 */     super.setProtocolVersion(paramHttpVersion);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpRequest setMethod(HttpMethod paramHttpMethod)
/*     */   {
/*  93 */     super.setMethod(paramHttpMethod);
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpRequest setUri(String paramString)
/*     */   {
/*  99 */     super.setUri(paramString);
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpRequest copy()
/*     */   {
/* 105 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy(), this.validateHeaders);
/*     */     
/* 107 */     localDefaultFullHttpRequest.headers().set(headers());
/* 108 */     localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 109 */     return localDefaultFullHttpRequest;
/*     */   }
/*     */   
/*     */   public FullHttpRequest duplicate()
/*     */   {
/* 114 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate(), this.validateHeaders);
/*     */     
/* 116 */     localDefaultFullHttpRequest.headers().set(headers());
/* 117 */     localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 118 */     return localDefaultFullHttpRequest;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultFullHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */