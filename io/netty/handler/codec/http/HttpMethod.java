/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class HttpMethod
/*     */   implements Comparable<HttpMethod>
/*     */ {
/*  37 */   public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  45 */   public static final HttpMethod GET = new HttpMethod("GET", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  51 */   public static final HttpMethod HEAD = new HttpMethod("HEAD", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  58 */   public static final HttpMethod POST = new HttpMethod("POST", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  63 */   public static final HttpMethod PUT = new HttpMethod("PUT", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */   public static final HttpMethod PATCH = new HttpMethod("PATCH", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  75 */   public static final HttpMethod DELETE = new HttpMethod("DELETE", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */   public static final HttpMethod TRACE = new HttpMethod("TRACE", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */   public static final HttpMethod CONNECT = new HttpMethod("CONNECT", true);
/*     */   
/*  89 */   private static final Map<String, HttpMethod> methodMap = new HashMap();
/*     */   private final String name;
/*     */   private final byte[] bytes;
/*     */   
/*  93 */   static { methodMap.put(OPTIONS.toString(), OPTIONS);
/*  94 */     methodMap.put(GET.toString(), GET);
/*  95 */     methodMap.put(HEAD.toString(), HEAD);
/*  96 */     methodMap.put(POST.toString(), POST);
/*  97 */     methodMap.put(PUT.toString(), PUT);
/*  98 */     methodMap.put(PATCH.toString(), PATCH);
/*  99 */     methodMap.put(DELETE.toString(), DELETE);
/* 100 */     methodMap.put(TRACE.toString(), TRACE);
/* 101 */     methodMap.put(CONNECT.toString(), CONNECT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HttpMethod valueOf(String paramString)
/*     */   {
/* 110 */     if (paramString == null) {
/* 111 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 114 */     paramString = paramString.trim();
/* 115 */     if (paramString.isEmpty()) {
/* 116 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 119 */     HttpMethod localHttpMethod = (HttpMethod)methodMap.get(paramString);
/* 120 */     if (localHttpMethod != null) {
/* 121 */       return localHttpMethod;
/*     */     }
/* 123 */     return new HttpMethod(paramString);
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
/*     */   public HttpMethod(String paramString)
/*     */   {
/* 138 */     this(paramString, false);
/*     */   }
/*     */   
/*     */   private HttpMethod(String paramString, boolean paramBoolean) {
/* 142 */     if (paramString == null) {
/* 143 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 146 */     paramString = paramString.trim();
/* 147 */     if (paramString.isEmpty()) {
/* 148 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/* 151 */     for (int i = 0; i < paramString.length(); i++) {
/* 152 */       if ((Character.isISOControl(paramString.charAt(i))) || (Character.isWhitespace(paramString.charAt(i))))
/*     */       {
/* 154 */         throw new IllegalArgumentException("invalid character in name");
/*     */       }
/*     */     }
/*     */     
/* 158 */     this.name = paramString;
/* 159 */     if (paramBoolean) {
/* 160 */       this.bytes = paramString.getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 162 */       this.bytes = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String name()
/*     */   {
/* 170 */     return this.name;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 175 */     return name().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 180 */     if (!(paramObject instanceof HttpMethod)) {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     HttpMethod localHttpMethod = (HttpMethod)paramObject;
/* 185 */     return name().equals(localHttpMethod.name());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 190 */     return name();
/*     */   }
/*     */   
/*     */   public int compareTo(HttpMethod paramHttpMethod)
/*     */   {
/* 195 */     return name().compareTo(paramHttpMethod.name());
/*     */   }
/*     */   
/*     */   void encode(ByteBuf paramByteBuf) {
/* 199 */     if (this.bytes == null) {
/* 200 */       HttpHeaders.encodeAscii0(this.name, paramByteBuf);
/*     */     } else {
/* 202 */       paramByteBuf.writeBytes(this.bytes);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */