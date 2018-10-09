/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class HttpVersion
/*     */   implements Comparable<HttpVersion>
/*     */ {
/*  31 */   private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
/*     */   
/*     */ 
/*     */   private static final String HTTP_1_0_STRING = "HTTP/1.0";
/*     */   
/*     */ 
/*     */   private static final String HTTP_1_1_STRING = "HTTP/1.1";
/*     */   
/*     */ 
/*  40 */   public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false, true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  45 */   public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true, true);
/*     */   
/*     */   private final String protocolName;
/*     */   private final int majorVersion;
/*     */   private final int minorVersion;
/*     */   private final String text;
/*     */   private final boolean keepAliveDefault;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public static HttpVersion valueOf(String paramString)
/*     */   {
/*  56 */     if (paramString == null) {
/*  57 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/*  60 */     paramString = paramString.trim();
/*     */     
/*  62 */     if (paramString.isEmpty()) {
/*  63 */       throw new IllegalArgumentException("text is empty");
/*     */     }
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
/*  77 */     HttpVersion localHttpVersion = version0(paramString);
/*  78 */     if (localHttpVersion == null) {
/*  79 */       paramString = paramString.toUpperCase();
/*     */       
/*  81 */       localHttpVersion = version0(paramString);
/*  82 */       if (localHttpVersion == null)
/*     */       {
/*  84 */         localHttpVersion = new HttpVersion(paramString, true);
/*     */       }
/*     */     }
/*  87 */     return localHttpVersion;
/*     */   }
/*     */   
/*     */   private static HttpVersion version0(String paramString) {
/*  91 */     if ("HTTP/1.1".equals(paramString)) {
/*  92 */       return HTTP_1_1;
/*     */     }
/*  94 */     if ("HTTP/1.0".equals(paramString)) {
/*  95 */       return HTTP_1_0;
/*     */     }
/*  97 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpVersion(String paramString, boolean paramBoolean)
/*     */   {
/* 119 */     if (paramString == null) {
/* 120 */       throw new NullPointerException("text");
/*     */     }
/*     */     
/* 123 */     paramString = paramString.trim().toUpperCase();
/* 124 */     if (paramString.isEmpty()) {
/* 125 */       throw new IllegalArgumentException("empty text");
/*     */     }
/*     */     
/* 128 */     Matcher localMatcher = VERSION_PATTERN.matcher(paramString);
/* 129 */     if (!localMatcher.matches()) {
/* 130 */       throw new IllegalArgumentException("invalid version format: " + paramString);
/*     */     }
/*     */     
/* 133 */     this.protocolName = localMatcher.group(1);
/* 134 */     this.majorVersion = Integer.parseInt(localMatcher.group(2));
/* 135 */     this.minorVersion = Integer.parseInt(localMatcher.group(3));
/* 136 */     this.text = (this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion);
/* 137 */     this.keepAliveDefault = paramBoolean;
/* 138 */     this.bytes = null;
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
/*     */ 
/*     */ 
/*     */   public HttpVersion(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 155 */     this(paramString, paramInt1, paramInt2, paramBoolean, false);
/*     */   }
/*     */   
/*     */ 
/*     */   private HttpVersion(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 161 */     if (paramString == null) {
/* 162 */       throw new NullPointerException("protocolName");
/*     */     }
/*     */     
/* 165 */     paramString = paramString.trim().toUpperCase();
/* 166 */     if (paramString.isEmpty()) {
/* 167 */       throw new IllegalArgumentException("empty protocolName");
/*     */     }
/*     */     
/* 170 */     for (int i = 0; i < paramString.length(); i++) {
/* 171 */       if ((Character.isISOControl(paramString.charAt(i))) || (Character.isWhitespace(paramString.charAt(i))))
/*     */       {
/* 173 */         throw new IllegalArgumentException("invalid character in protocolName");
/*     */       }
/*     */     }
/*     */     
/* 177 */     if (paramInt1 < 0) {
/* 178 */       throw new IllegalArgumentException("negative majorVersion");
/*     */     }
/* 180 */     if (paramInt2 < 0) {
/* 181 */       throw new IllegalArgumentException("negative minorVersion");
/*     */     }
/*     */     
/* 184 */     this.protocolName = paramString;
/* 185 */     this.majorVersion = paramInt1;
/* 186 */     this.minorVersion = paramInt2;
/* 187 */     this.text = (paramString + '/' + paramInt1 + '.' + paramInt2);
/* 188 */     this.keepAliveDefault = paramBoolean1;
/*     */     
/* 190 */     if (paramBoolean2) {
/* 191 */       this.bytes = this.text.getBytes(CharsetUtil.US_ASCII);
/*     */     } else {
/* 193 */       this.bytes = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String protocolName()
/*     */   {
/* 201 */     return this.protocolName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int majorVersion()
/*     */   {
/* 208 */     return this.majorVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int minorVersion()
/*     */   {
/* 215 */     return this.minorVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String text()
/*     */   {
/* 222 */     return this.text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isKeepAliveDefault()
/*     */   {
/* 230 */     return this.keepAliveDefault;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 238 */     return text();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 243 */     return (protocolName().hashCode() * 31 + majorVersion()) * 31 + minorVersion();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 249 */     if (!(paramObject instanceof HttpVersion)) {
/* 250 */       return false;
/*     */     }
/*     */     
/* 253 */     HttpVersion localHttpVersion = (HttpVersion)paramObject;
/* 254 */     return (minorVersion() == localHttpVersion.minorVersion()) && (majorVersion() == localHttpVersion.majorVersion()) && (protocolName().equals(localHttpVersion.protocolName()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int compareTo(HttpVersion paramHttpVersion)
/*     */   {
/* 261 */     int i = protocolName().compareTo(paramHttpVersion.protocolName());
/* 262 */     if (i != 0) {
/* 263 */       return i;
/*     */     }
/*     */     
/* 266 */     i = majorVersion() - paramHttpVersion.majorVersion();
/* 267 */     if (i != 0) {
/* 268 */       return i;
/*     */     }
/*     */     
/* 271 */     return minorVersion() - paramHttpVersion.minorVersion();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf paramByteBuf) {
/* 275 */     if (this.bytes == null) {
/* 276 */       HttpHeaders.encodeAscii0(this.text, paramByteBuf);
/*     */     } else {
/* 278 */       paramByteBuf.writeBytes(this.bytes);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */