/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
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
/*     */ public final class CharsetUtil
/*     */ {
/*  36 */   public static final Charset UTF_16 = Charset.forName("UTF-16");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  41 */   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  46 */   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  56 */   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  62 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharsetEncoder getEncoder(Charset paramCharset)
/*     */   {
/*  69 */     if (paramCharset == null) {
/*  70 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  73 */     Map localMap = InternalThreadLocalMap.get().charsetEncoderCache();
/*  74 */     CharsetEncoder localCharsetEncoder = (CharsetEncoder)localMap.get(paramCharset);
/*  75 */     if (localCharsetEncoder != null) {
/*  76 */       localCharsetEncoder.reset();
/*  77 */       localCharsetEncoder.onMalformedInput(CodingErrorAction.REPLACE);
/*  78 */       localCharsetEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  79 */       return localCharsetEncoder;
/*     */     }
/*     */     
/*  82 */     localCharsetEncoder = paramCharset.newEncoder();
/*  83 */     localCharsetEncoder.onMalformedInput(CodingErrorAction.REPLACE);
/*  84 */     localCharsetEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  85 */     localMap.put(paramCharset, localCharsetEncoder);
/*  86 */     return localCharsetEncoder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharsetDecoder getDecoder(Charset paramCharset)
/*     */   {
/*  94 */     if (paramCharset == null) {
/*  95 */       throw new NullPointerException("charset");
/*     */     }
/*     */     
/*  98 */     Map localMap = InternalThreadLocalMap.get().charsetDecoderCache();
/*  99 */     CharsetDecoder localCharsetDecoder = (CharsetDecoder)localMap.get(paramCharset);
/* 100 */     if (localCharsetDecoder != null) {
/* 101 */       localCharsetDecoder.reset();
/* 102 */       localCharsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
/* 103 */       localCharsetDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 104 */       return localCharsetDecoder;
/*     */     }
/*     */     
/* 107 */     localCharsetDecoder = paramCharset.newDecoder();
/* 108 */     localCharsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
/* 109 */     localCharsetDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 110 */     localMap.put(paramCharset, localCharsetDecoder);
/* 111 */     return localCharsetDecoder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\CharsetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */