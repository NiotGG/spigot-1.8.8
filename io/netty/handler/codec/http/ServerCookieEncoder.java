/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public final class ServerCookieEncoder
/*     */ {
/*     */   public static String encode(String paramString1, String paramString2)
/*     */   {
/*  42 */     return encode(new DefaultCookie(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   public static String encode(Cookie paramCookie) {
/*  46 */     if (paramCookie == null) {
/*  47 */       throw new NullPointerException("cookie");
/*     */     }
/*     */     
/*  50 */     StringBuilder localStringBuilder = CookieEncoderUtil.stringBuilder();
/*     */     
/*  52 */     CookieEncoderUtil.add(localStringBuilder, paramCookie.getName(), paramCookie.getValue());
/*     */     
/*  54 */     if (paramCookie.getMaxAge() != Long.MIN_VALUE) {
/*  55 */       if (paramCookie.getVersion() == 0) {
/*  56 */         CookieEncoderUtil.addUnquoted(localStringBuilder, "Expires", HttpHeaderDateFormat.get().format(new Date(System.currentTimeMillis() + paramCookie.getMaxAge() * 1000L)));
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  61 */         CookieEncoderUtil.add(localStringBuilder, "Max-Age", paramCookie.getMaxAge());
/*     */       }
/*     */     }
/*     */     
/*  65 */     if (paramCookie.getPath() != null) {
/*  66 */       if (paramCookie.getVersion() > 0) {
/*  67 */         CookieEncoderUtil.add(localStringBuilder, "Path", paramCookie.getPath());
/*     */       } else {
/*  69 */         CookieEncoderUtil.addUnquoted(localStringBuilder, "Path", paramCookie.getPath());
/*     */       }
/*     */     }
/*     */     
/*  73 */     if (paramCookie.getDomain() != null) {
/*  74 */       if (paramCookie.getVersion() > 0) {
/*  75 */         CookieEncoderUtil.add(localStringBuilder, "Domain", paramCookie.getDomain());
/*     */       } else {
/*  77 */         CookieEncoderUtil.addUnquoted(localStringBuilder, "Domain", paramCookie.getDomain());
/*     */       }
/*     */     }
/*  80 */     if (paramCookie.isSecure()) {
/*  81 */       localStringBuilder.append("Secure");
/*  82 */       localStringBuilder.append(';');
/*  83 */       localStringBuilder.append(' ');
/*     */     }
/*  85 */     if (paramCookie.isHttpOnly()) {
/*  86 */       localStringBuilder.append("HTTPOnly");
/*  87 */       localStringBuilder.append(';');
/*  88 */       localStringBuilder.append(' ');
/*     */     }
/*  90 */     if (paramCookie.getVersion() >= 1) {
/*  91 */       if (paramCookie.getComment() != null) {
/*  92 */         CookieEncoderUtil.add(localStringBuilder, "Comment", paramCookie.getComment());
/*     */       }
/*     */       
/*  95 */       CookieEncoderUtil.add(localStringBuilder, "Version", 1L);
/*     */       
/*  97 */       if (paramCookie.getCommentUrl() != null) {
/*  98 */         CookieEncoderUtil.addQuoted(localStringBuilder, "CommentURL", paramCookie.getCommentUrl());
/*     */       }
/*     */       
/* 101 */       if (!paramCookie.getPorts().isEmpty()) {
/* 102 */         localStringBuilder.append("Port");
/* 103 */         localStringBuilder.append('=');
/* 104 */         localStringBuilder.append('"');
/* 105 */         for (Iterator localIterator = paramCookie.getPorts().iterator(); localIterator.hasNext();) { int i = ((Integer)localIterator.next()).intValue();
/* 106 */           localStringBuilder.append(i);
/* 107 */           localStringBuilder.append(',');
/*     */         }
/* 109 */         localStringBuilder.setCharAt(localStringBuilder.length() - 1, '"');
/* 110 */         localStringBuilder.append(';');
/* 111 */         localStringBuilder.append(' ');
/*     */       }
/* 113 */       if (paramCookie.isDiscard()) {
/* 114 */         localStringBuilder.append("Discard");
/* 115 */         localStringBuilder.append(';');
/* 116 */         localStringBuilder.append(' ');
/*     */       }
/*     */     }
/*     */     
/* 120 */     return CookieEncoderUtil.stripTrailingSeparator(localStringBuilder);
/*     */   }
/*     */   
/*     */   public static List<String> encode(Cookie... paramVarArgs) {
/* 124 */     if (paramVarArgs == null) {
/* 125 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 128 */     ArrayList localArrayList = new ArrayList(paramVarArgs.length);
/* 129 */     for (Cookie localCookie : paramVarArgs) {
/* 130 */       if (localCookie == null) {
/*     */         break;
/*     */       }
/* 133 */       localArrayList.add(encode(localCookie));
/*     */     }
/* 135 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public static List<String> encode(Collection<Cookie> paramCollection) {
/* 139 */     if (paramCollection == null) {
/* 140 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 143 */     ArrayList localArrayList = new ArrayList(paramCollection.size());
/* 144 */     for (Cookie localCookie : paramCollection) {
/* 145 */       if (localCookie == null) {
/*     */         break;
/*     */       }
/* 148 */       localArrayList.add(encode(localCookie));
/*     */     }
/* 150 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public static List<String> encode(Iterable<Cookie> paramIterable) {
/* 154 */     if (paramIterable == null) {
/* 155 */       throw new NullPointerException("cookies");
/*     */     }
/*     */     
/* 158 */     ArrayList localArrayList = new ArrayList();
/* 159 */     for (Cookie localCookie : paramIterable) {
/* 160 */       if (localCookie == null) {
/*     */         break;
/*     */       }
/* 163 */       localArrayList.add(encode(localCookie));
/*     */     }
/* 165 */     return localArrayList;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\ServerCookieEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */