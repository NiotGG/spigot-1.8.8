/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.codec.Charsets;
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
/*     */ public class StringUtils
/*     */ {
/*     */   private static byte[] getBytes(String paramString, Charset paramCharset)
/*     */   {
/*  50 */     if (paramString == null) {
/*  51 */       return null;
/*     */     }
/*  53 */     return paramString.getBytes(paramCharset);
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
/*     */   public static byte[] getBytesIso8859_1(String paramString)
/*     */   {
/*  71 */     return getBytes(paramString, Charsets.ISO_8859_1);
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
/*     */ 
/*     */   public static byte[] getBytesUnchecked(String paramString1, String paramString2)
/*     */   {
/*  95 */     if (paramString1 == null) {
/*  96 */       return null;
/*     */     }
/*     */     try {
/*  99 */       return paramString1.getBytes(paramString2);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 101 */       throw newIllegalStateException(paramString2, localUnsupportedEncodingException);
/*     */     }
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
/*     */   public static byte[] getBytesUsAscii(String paramString)
/*     */   {
/* 120 */     return getBytes(paramString, Charsets.US_ASCII);
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
/*     */   public static byte[] getBytesUtf16(String paramString)
/*     */   {
/* 138 */     return getBytes(paramString, Charsets.UTF_16);
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
/*     */   public static byte[] getBytesUtf16Be(String paramString)
/*     */   {
/* 156 */     return getBytes(paramString, Charsets.UTF_16BE);
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
/*     */   public static byte[] getBytesUtf16Le(String paramString)
/*     */   {
/* 174 */     return getBytes(paramString, Charsets.UTF_16LE);
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
/*     */   public static byte[] getBytesUtf8(String paramString)
/*     */   {
/* 192 */     return getBytes(paramString, Charsets.UTF_8);
/*     */   }
/*     */   
/*     */   private static IllegalStateException newIllegalStateException(String paramString, UnsupportedEncodingException paramUnsupportedEncodingException)
/*     */   {
/* 197 */     return new IllegalStateException(paramString + ": " + paramUnsupportedEncodingException);
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
/*     */   private static String newString(byte[] paramArrayOfByte, Charset paramCharset)
/*     */   {
/* 214 */     return paramArrayOfByte == null ? null : new String(paramArrayOfByte, paramCharset);
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
/*     */   public static String newString(byte[] paramArrayOfByte, String paramString)
/*     */   {
/* 237 */     if (paramArrayOfByte == null) {
/* 238 */       return null;
/*     */     }
/*     */     try {
/* 241 */       return new String(paramArrayOfByte, paramString);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 243 */       throw newIllegalStateException(paramString, localUnsupportedEncodingException);
/*     */     }
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
/*     */   public static String newStringIso8859_1(byte[] paramArrayOfByte)
/*     */   {
/* 260 */     return new String(paramArrayOfByte, Charsets.ISO_8859_1);
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
/*     */   public static String newStringUsAscii(byte[] paramArrayOfByte)
/*     */   {
/* 276 */     return new String(paramArrayOfByte, Charsets.US_ASCII);
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
/*     */   public static String newStringUtf16(byte[] paramArrayOfByte)
/*     */   {
/* 292 */     return new String(paramArrayOfByte, Charsets.UTF_16);
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
/*     */   public static String newStringUtf16Be(byte[] paramArrayOfByte)
/*     */   {
/* 308 */     return new String(paramArrayOfByte, Charsets.UTF_16BE);
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
/*     */   public static String newStringUtf16Le(byte[] paramArrayOfByte)
/*     */   {
/* 324 */     return new String(paramArrayOfByte, Charsets.UTF_16LE);
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
/*     */   public static String newStringUtf8(byte[] paramArrayOfByte)
/*     */   {
/* 340 */     return newString(paramArrayOfByte, Charsets.UTF_8);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */