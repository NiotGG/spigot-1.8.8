/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.util.ArrayList;
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
/*     */ public class QueryStringEncoder
/*     */ {
/*     */   private final Charset charset;
/*     */   private final String uri;
/*  42 */   private final List<Param> params = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringEncoder(String paramString)
/*     */   {
/*  49 */     this(paramString, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringEncoder(String paramString, Charset paramCharset)
/*     */   {
/*  57 */     if (paramString == null) {
/*  58 */       throw new NullPointerException("getUri");
/*     */     }
/*  60 */     if (paramCharset == null) {
/*  61 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  64 */     this.uri = paramString;
/*  65 */     this.charset = paramCharset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addParam(String paramString1, String paramString2)
/*     */   {
/*  72 */     if (paramString1 == null) {
/*  73 */       throw new NullPointerException("name");
/*     */     }
/*  75 */     this.params.add(new Param(paramString1, paramString2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public URI toUri()
/*     */     throws URISyntaxException
/*     */   {
/*  84 */     return new URI(toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     if (this.params.isEmpty()) {
/*  95 */       return this.uri;
/*     */     }
/*  97 */     StringBuilder localStringBuilder = new StringBuilder(this.uri).append('?');
/*  98 */     for (int i = 0; i < this.params.size(); i++) {
/*  99 */       Param localParam = (Param)this.params.get(i);
/* 100 */       localStringBuilder.append(encodeComponent(localParam.name, this.charset));
/* 101 */       if (localParam.value != null) {
/* 102 */         localStringBuilder.append('=');
/* 103 */         localStringBuilder.append(encodeComponent(localParam.value, this.charset));
/*     */       }
/* 105 */       if (i != this.params.size() - 1) {
/* 106 */         localStringBuilder.append('&');
/*     */       }
/*     */     }
/* 109 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static String encodeComponent(String paramString, Charset paramCharset)
/*     */   {
/*     */     try
/*     */     {
/* 116 */       return URLEncoder.encode(paramString, paramCharset.name()).replace("+", "%20");
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 118 */       throw new UnsupportedCharsetException(paramCharset.name());
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Param
/*     */   {
/*     */     final String name;
/*     */     final String value;
/*     */     
/*     */     Param(String paramString1, String paramString2) {
/* 128 */       this.value = paramString2;
/* 129 */       this.name = paramString1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\QueryStringEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */