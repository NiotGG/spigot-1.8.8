/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClientCookieEncoder
/*     */ {
/*     */   public static String encode(String paramString1, String paramString2)
/*     */   {
/*  37 */     return encode(new DefaultCookie(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   public static String encode(Cookie paramCookie) {
/*  41 */     if (paramCookie == null) {
/*  42 */       throw new NullPointerException("cookie");
/*     */     }
/*     */     
/*  45 */     StringBuilder localStringBuilder = CookieEncoderUtil.stringBuilder();
/*  46 */     encode(localStringBuilder, paramCookie);
/*  47 */     return CookieEncoderUtil.stripTrailingSeparator(localStringBuilder);
/*     */   }
/*     */   
/*     */   public static String encode(Cookie... paramVarArgs) {
/*  51 */     if (paramVarArgs == null) {
/*  52 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/*  55 */     StringBuilder localStringBuilder = CookieEncoderUtil.stringBuilder();
/*  56 */     for (Cookie localCookie : paramVarArgs) {
/*  57 */       if (localCookie == null) {
/*     */         break;
/*     */       }
/*     */       
/*  61 */       encode(localStringBuilder, localCookie);
/*     */     }
/*  63 */     return CookieEncoderUtil.stripTrailingSeparator(localStringBuilder);
/*     */   }
/*     */   
/*     */   public static String encode(Iterable<Cookie> paramIterable) {
/*  67 */     if (paramIterable == null) {
/*  68 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/*  71 */     StringBuilder localStringBuilder = CookieEncoderUtil.stringBuilder();
/*  72 */     for (Cookie localCookie : paramIterable) {
/*  73 */       if (localCookie == null) {
/*     */         break;
/*     */       }
/*     */       
/*  77 */       encode(localStringBuilder, localCookie);
/*     */     }
/*  79 */     return CookieEncoderUtil.stripTrailingSeparator(localStringBuilder);
/*     */   }
/*     */   
/*     */   private static void encode(StringBuilder paramStringBuilder, Cookie paramCookie) {
/*  83 */     if (paramCookie.getVersion() >= 1) {
/*  84 */       CookieEncoderUtil.add(paramStringBuilder, "$Version", 1L);
/*     */     }
/*     */     
/*  87 */     CookieEncoderUtil.add(paramStringBuilder, paramCookie.getName(), paramCookie.getValue());
/*     */     
/*  89 */     if (paramCookie.getPath() != null) {
/*  90 */       CookieEncoderUtil.add(paramStringBuilder, "$Path", paramCookie.getPath());
/*     */     }
/*     */     
/*  93 */     if (paramCookie.getDomain() != null) {
/*  94 */       CookieEncoderUtil.add(paramStringBuilder, "$Domain", paramCookie.getDomain());
/*     */     }
/*     */     
/*  97 */     if ((paramCookie.getVersion() >= 1) && 
/*  98 */       (!paramCookie.getPorts().isEmpty())) {
/*  99 */       paramStringBuilder.append('$');
/* 100 */       paramStringBuilder.append("Port");
/* 101 */       paramStringBuilder.append('=');
/* 102 */       paramStringBuilder.append('"');
/* 103 */       for (Iterator localIterator = paramCookie.getPorts().iterator(); localIterator.hasNext();) { int i = ((Integer)localIterator.next()).intValue();
/* 104 */         paramStringBuilder.append(i);
/* 105 */         paramStringBuilder.append(',');
/*     */       }
/* 107 */       paramStringBuilder.setCharAt(paramStringBuilder.length() - 1, '"');
/* 108 */       paramStringBuilder.append(';');
/* 109 */       paramStringBuilder.append(' ');
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\ClientCookieEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */