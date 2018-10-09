/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SoundexUtils
/*     */ {
/*     */   static String clean(String paramString)
/*     */   {
/*  42 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  43 */       return paramString;
/*     */     }
/*  45 */     int i = paramString.length();
/*  46 */     char[] arrayOfChar = new char[i];
/*  47 */     int j = 0;
/*  48 */     for (int k = 0; k < i; k++) {
/*  49 */       if (Character.isLetter(paramString.charAt(k))) {
/*  50 */         arrayOfChar[(j++)] = paramString.charAt(k);
/*     */       }
/*     */     }
/*  53 */     if (j == i) {
/*  54 */       return paramString.toUpperCase(Locale.ENGLISH);
/*     */     }
/*  56 */     return new String(arrayOfChar, 0, j).toUpperCase(Locale.ENGLISH);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int difference(StringEncoder paramStringEncoder, String paramString1, String paramString2)
/*     */     throws EncoderException
/*     */   {
/*  86 */     return differenceEncoded(paramStringEncoder.encode(paramString1), paramStringEncoder.encode(paramString2));
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
/*     */ 
/*     */   static int differenceEncoded(String paramString1, String paramString2)
/*     */   {
/* 111 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 112 */       return 0;
/*     */     }
/* 114 */     int i = Math.min(paramString1.length(), paramString2.length());
/* 115 */     int j = 0;
/* 116 */     for (int k = 0; k < i; k++) {
/* 117 */       if (paramString1.charAt(k) == paramString2.charAt(k)) {
/* 118 */         j++;
/*     */       }
/*     */     }
/* 121 */     return j;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\SoundexUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */