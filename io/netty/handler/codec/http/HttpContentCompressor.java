/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*     */ import io.netty.handler.codec.compression.ZlibWrapper;
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
/*     */ 
/*     */ public class HttpContentCompressor
/*     */   extends HttpContentEncoder
/*     */ {
/*     */   private final int compressionLevel;
/*     */   private final int windowBits;
/*     */   private final int memLevel;
/*     */   
/*     */   public HttpContentCompressor()
/*     */   {
/*  41 */     this(6);
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
/*     */   public HttpContentCompressor(int paramInt)
/*     */   {
/*  54 */     this(paramInt, 15, 8);
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
/*     */ 
/*     */   public HttpContentCompressor(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  77 */     if ((paramInt1 < 0) || (paramInt1 > 9)) {
/*  78 */       throw new IllegalArgumentException("compressionLevel: " + paramInt1 + " (expected: 0-9)");
/*     */     }
/*     */     
/*     */ 
/*  82 */     if ((paramInt2 < 9) || (paramInt2 > 15)) {
/*  83 */       throw new IllegalArgumentException("windowBits: " + paramInt2 + " (expected: 9-15)");
/*     */     }
/*     */     
/*  86 */     if ((paramInt3 < 1) || (paramInt3 > 9)) {
/*  87 */       throw new IllegalArgumentException("memLevel: " + paramInt3 + " (expected: 1-9)");
/*     */     }
/*     */     
/*  90 */     this.compressionLevel = paramInt1;
/*  91 */     this.windowBits = paramInt2;
/*  92 */     this.memLevel = paramInt3;
/*     */   }
/*     */   
/*     */   protected HttpContentEncoder.Result beginEncode(HttpResponse paramHttpResponse, String paramString) throws Exception
/*     */   {
/*  97 */     String str1 = paramHttpResponse.headers().get("Content-Encoding");
/*  98 */     if ((str1 != null) && (!"identity".equalsIgnoreCase(str1)))
/*     */     {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     ZlibWrapper localZlibWrapper = determineWrapper(paramString);
/* 104 */     if (localZlibWrapper == null) {
/* 105 */       return null;
/*     */     }
/*     */     
/*     */     String str2;
/* 109 */     switch (localZlibWrapper) {
/*     */     case GZIP: 
/* 111 */       str2 = "gzip";
/* 112 */       break;
/*     */     case ZLIB: 
/* 114 */       str2 = "deflate";
/* 115 */       break;
/*     */     default: 
/* 117 */       throw new Error();
/*     */     }
/*     */     
/* 120 */     return new HttpContentEncoder.Result(str2, new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibEncoder(localZlibWrapper, this.compressionLevel, this.windowBits, this.memLevel) }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ZlibWrapper determineWrapper(String paramString)
/*     */   {
/* 128 */     float f1 = -1.0F;
/* 129 */     float f2 = -1.0F;
/* 130 */     float f3 = -1.0F;
/* 131 */     for (String str : StringUtil.split(paramString, ',')) {
/* 132 */       float f4 = 1.0F;
/* 133 */       int k = str.indexOf('=');
/* 134 */       if (k != -1) {
/*     */         try {
/* 136 */           f4 = Float.valueOf(str.substring(k + 1)).floatValue();
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException) {
/* 139 */           f4 = 0.0F;
/*     */         }
/*     */       }
/* 142 */       if (str.contains("*")) {
/* 143 */         f1 = f4;
/* 144 */       } else if ((str.contains("gzip")) && (f4 > f2)) {
/* 145 */         f2 = f4;
/* 146 */       } else if ((str.contains("deflate")) && (f4 > f3)) {
/* 147 */         f3 = f4;
/*     */       }
/*     */     }
/* 150 */     if ((f2 > 0.0F) || (f3 > 0.0F)) {
/* 151 */       if (f2 >= f3) {
/* 152 */         return ZlibWrapper.GZIP;
/*     */       }
/* 154 */       return ZlibWrapper.ZLIB;
/*     */     }
/*     */     
/* 157 */     if (f1 > 0.0F) {
/* 158 */       if (f2 == -1.0F) {
/* 159 */         return ZlibWrapper.GZIP;
/*     */       }
/* 161 */       if (f3 == -1.0F) {
/* 162 */         return ZlibWrapper.ZLIB;
/*     */       }
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpContentCompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */