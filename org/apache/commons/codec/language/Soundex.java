/*     */ package org.apache.commons.codec.language;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Soundex
/*     */   implements StringEncoder
/*     */ {
/*     */   public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
/*  52 */   private static final char[] US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  59 */   public static final Soundex US_ENGLISH = new Soundex();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*  66 */   private int maxLength = 4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char[] soundexMapping;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Soundex()
/*     */   {
/*  82 */     this.soundexMapping = US_ENGLISH_MAPPING;
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
/*     */   public Soundex(char[] paramArrayOfChar)
/*     */   {
/*  96 */     this.soundexMapping = new char[paramArrayOfChar.length];
/*  97 */     System.arraycopy(paramArrayOfChar, 0, this.soundexMapping, 0, paramArrayOfChar.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Soundex(String paramString)
/*     */   {
/* 109 */     this.soundexMapping = paramString.toCharArray();
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
/*     */   public int difference(String paramString1, String paramString2)
/*     */     throws EncoderException
/*     */   {
/* 132 */     return SoundexUtils.difference(this, paramString1, paramString2);
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 150 */     if (!(paramObject instanceof String)) {
/* 151 */       throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
/*     */     }
/* 153 */     return soundex((String)paramObject);
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
/*     */   public String encode(String paramString)
/*     */   {
/* 167 */     return soundex(paramString);
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
/*     */   private char getMappingCode(String paramString, int paramInt)
/*     */   {
/* 185 */     char c1 = map(paramString.charAt(paramInt));
/*     */     
/* 187 */     if ((paramInt > 1) && (c1 != '0')) {
/* 188 */       int i = paramString.charAt(paramInt - 1);
/* 189 */       if ((72 == i) || (87 == i)) {
/* 190 */         char c2 = paramString.charAt(paramInt - 2);
/* 191 */         char c3 = map(c2);
/* 192 */         if ((c3 == c1) || ('H' == c2) || ('W' == c2)) {
/* 193 */           return '\000';
/*     */         }
/*     */       }
/*     */     }
/* 197 */     return c1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getMaxLength()
/*     */   {
/* 208 */     return this.maxLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] getSoundexMapping()
/*     */   {
/* 217 */     return this.soundexMapping;
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
/*     */   private char map(char paramChar)
/*     */   {
/* 230 */     int i = paramChar - 'A';
/* 231 */     if ((i < 0) || (i >= getSoundexMapping().length)) {
/* 232 */       throw new IllegalArgumentException("The character is not mapped: " + paramChar);
/*     */     }
/* 234 */     return getSoundexMapping()[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void setMaxLength(int paramInt)
/*     */   {
/* 246 */     this.maxLength = paramInt;
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
/*     */   public String soundex(String paramString)
/*     */   {
/* 259 */     if (paramString == null) {
/* 260 */       return null;
/*     */     }
/* 262 */     paramString = SoundexUtils.clean(paramString);
/* 263 */     if (paramString.length() == 0) {
/* 264 */       return paramString;
/*     */     }
/* 266 */     char[] arrayOfChar = { '0', '0', '0', '0' };
/*     */     
/* 268 */     int i = 1;int j = 1;
/* 269 */     arrayOfChar[0] = paramString.charAt(0);
/*     */     
/* 271 */     int k = getMappingCode(paramString, 0);
/* 272 */     while ((i < paramString.length()) && (j < arrayOfChar.length)) {
/* 273 */       int m = getMappingCode(paramString, i++);
/* 274 */       if (m != 0) {
/* 275 */         if ((m != 48) && (m != k)) {
/* 276 */           arrayOfChar[(j++)] = m;
/*     */         }
/* 278 */         k = m;
/*     */       }
/*     */     }
/* 281 */     return new String(arrayOfChar);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\Soundex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */