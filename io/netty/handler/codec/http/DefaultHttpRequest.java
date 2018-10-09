/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpRequest
/*     */   extends DefaultHttpMessage
/*     */   implements HttpRequest
/*     */ {
/*     */   private HttpMethod method;
/*     */   private String uri;
/*     */   
/*     */   public DefaultHttpRequest(HttpVersion paramHttpVersion, HttpMethod paramHttpMethod, String paramString)
/*     */   {
/*  36 */     this(paramHttpVersion, paramHttpMethod, paramString, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultHttpRequest(HttpVersion paramHttpVersion, HttpMethod paramHttpMethod, String paramString, boolean paramBoolean)
/*     */   {
/*  48 */     super(paramHttpVersion, paramBoolean);
/*  49 */     if (paramHttpMethod == null) {
/*  50 */       throw new NullPointerException("method");
/*     */     }
/*  52 */     if (paramString == null) {
/*  53 */       throw new NullPointerException("uri");
/*     */     }
/*  55 */     this.method = paramHttpMethod;
/*  56 */     this.uri = paramString;
/*     */   }
/*     */   
/*     */   public HttpMethod getMethod()
/*     */   {
/*  61 */     return this.method;
/*     */   }
/*     */   
/*     */   public String getUri()
/*     */   {
/*  66 */     return this.uri;
/*     */   }
/*     */   
/*     */   public HttpRequest setMethod(HttpMethod paramHttpMethod)
/*     */   {
/*  71 */     if (paramHttpMethod == null) {
/*  72 */       throw new NullPointerException("method");
/*     */     }
/*  74 */     this.method = paramHttpMethod;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public HttpRequest setUri(String paramString)
/*     */   {
/*  80 */     if (paramString == null) {
/*  81 */       throw new NullPointerException("uri");
/*     */     }
/*  83 */     this.uri = paramString;
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public HttpRequest setProtocolVersion(HttpVersion paramHttpVersion)
/*     */   {
/*  89 */     super.setProtocolVersion(paramHttpVersion);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  95 */     StringBuilder localStringBuilder = new StringBuilder();
/*  96 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/*  97 */     localStringBuilder.append("(decodeResult: ");
/*  98 */     localStringBuilder.append(getDecoderResult());
/*  99 */     localStringBuilder.append(')');
/* 100 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 101 */     localStringBuilder.append(getMethod());
/* 102 */     localStringBuilder.append(' ');
/* 103 */     localStringBuilder.append(getUri());
/* 104 */     localStringBuilder.append(' ');
/* 105 */     localStringBuilder.append(getProtocolVersion().text());
/* 106 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 107 */     appendHeaders(localStringBuilder);
/*     */     
/*     */ 
/* 110 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 111 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */