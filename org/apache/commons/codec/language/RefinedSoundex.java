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
/*     */ public class RefinedSoundex
/*     */   implements StringEncoder
/*     */ {
/*     */   public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
/*  44 */   private static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
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
/*  57 */   public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RefinedSoundex()
/*     */   {
/*  64 */     this.soundexMapping = US_ENGLISH_MAPPING;
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
/*     */   public RefinedSoundex(char[] paramArrayOfChar)
/*     */   {
/*  77 */     this.soundexMapping = new char[paramArrayOfChar.length];
/*  78 */     System.arraycopy(paramArrayOfChar, 0, this.soundexMapping, 0, paramArrayOfChar.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RefinedSoundex(String paramString)
/*     */   {
/*  90 */     this.soundexMapping = paramString.toCharArray();
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
/*     */   public int difference(String paramString1, String paramString2)
/*     */     throws EncoderException
/*     */   {
/* 116 */     return SoundexUtils.difference(this, paramString1, paramString2);
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
/* 134 */     if (!(paramObject instanceof String)) {
/* 135 */       throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
/*     */     }
/* 137 */     return soundex((String)paramObject);
/*     */   }
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
/* 149 */     return soundex(paramString);
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
/*     */   char getMappingCode(char paramChar)
/*     */   {
/* 162 */     if (!Character.isLetter(paramChar)) {
/* 163 */       return '\000';
/*     */     }
/* 165 */     return this.soundexMapping[(Character.toUpperCase(paramChar) - 'A')];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String soundex(String paramString)
/*     */   {
/* 176 */     if (paramString == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     paramString = SoundexUtils.clean(paramString);
/* 180 */     if (paramString.length() == 0) {
/* 181 */       return paramString;
/*     */     }
/*     */     
/* 184 */     StringBuilder localStringBuilder = new StringBuilder();
/* 185 */     localStringBuilder.append(paramString.charAt(0));
/*     */     
/*     */ 
/* 188 */     int i = 42;
/*     */     
/* 190 */     for (int j = 0; j < paramString.length(); j++)
/*     */     {
/* 192 */       char c = getMappingCode(paramString.charAt(j));
/* 193 */       if (c != i)
/*     */       {
/* 195 */         if (c != 0) {
/* 196 */           localStringBuilder.append(c);
/*     */         }
/*     */         
/* 199 */         i = c;
/*     */       }
/*     */     }
/*     */     
/* 203 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\RefinedSoundex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */