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
/*     */ 
/*     */ 
/*     */ public class DefaultFullHttpResponse
/*     */   extends DefaultHttpResponse
/*     */   implements FullHttpResponse
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeaders;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion paramHttpVersion, HttpResponseStatus paramHttpResponseStatus)
/*     */   {
/*  32 */     this(paramHttpVersion, paramHttpResponseStatus, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion paramHttpVersion, HttpResponseStatus paramHttpResponseStatus, ByteBuf paramByteBuf) {
/*  36 */     this(paramHttpVersion, paramHttpResponseStatus, paramByteBuf, true);
/*     */   }
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion paramHttpVersion, HttpResponseStatus paramHttpResponseStatus, ByteBuf paramByteBuf, boolean paramBoolean)
/*     */   {
/*  41 */     super(paramHttpVersion, paramHttpResponseStatus, paramBoolean);
/*  42 */     if (paramByteBuf == null) {
/*  43 */       throw new NullPointerException("content");
/*     */     }
/*  45 */     this.content = paramByteBuf;
/*  46 */     this.trailingHeaders = new DefaultHttpHeaders(paramBoolean);
/*  47 */     this.validateHeaders = paramBoolean;
/*     */   }
/*     */   
/*     */   public HttpHeaders trailingHeaders()
/*     */   {
/*  52 */     return this.trailingHeaders;
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/*  57 */     return this.content;
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/*  62 */     return this.content.refCnt();
/*     */   }
/*     */   
/*     */   public FullHttpResponse retain()
/*     */   {
/*  67 */     this.content.retain();
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpResponse retain(int paramInt)
/*     */   {
/*  73 */     this.content.retain(paramInt);
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/*  79 */     return this.content.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/*  84 */     return this.content.release(paramInt);
/*     */   }
/*     */   
/*     */   public FullHttpResponse setProtocolVersion(HttpVersion paramHttpVersion)
/*     */   {
/*  89 */     super.setProtocolVersion(paramHttpVersion);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus)
/*     */   {
/*  95 */     super.setStatus(paramHttpResponseStatus);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public FullHttpResponse copy()
/*     */   {
/* 101 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().copy(), this.validateHeaders);
/*     */     
/* 103 */     localDefaultFullHttpResponse.headers().set(headers());
/* 104 */     localDefaultFullHttpResponse.trailingHeaders().set(trailingHeaders());
/* 105 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */   
/*     */   public FullHttpResponse duplicate()
/*     */   {
/* 110 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().duplicate(), this.validateHeaders);
/*     */     
/* 112 */     localDefaultFullHttpResponse.headers().set(headers());
/* 113 */     localDefaultFullHttpResponse.trailingHeaders().set(trailingHeaders());
/* 114 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultFullHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */