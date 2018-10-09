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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatchRatingApproachEncoder
/*     */   implements StringEncoder
/*     */ {
/*     */   private static final String SPACE = " ";
/*     */   private static final String EMPTY = "";
/*     */   private static final int ONE = 1;
/*     */   private static final int TWO = 2;
/*     */   private static final int THREE = 3;
/*     */   private static final int FOUR = 4;
/*     */   private static final int FIVE = 5;
/*     */   private static final int SIX = 6;
/*     */   private static final int SEVEN = 7;
/*     */   private static final int EIGHT = 8;
/*     */   private static final int ELEVEN = 11;
/*     */   private static final int TWELVE = 12;
/*     */   private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
/*     */   private static final String UNICODE = "ÀàÈèÌìÒòÙùÁáÉéÍíÓóÚúÝýÂâÊêÎîÔôÛûŶŷÃãÕõÑñÄäËëÏïÖöÜüŸÿÅåÇçŐőŰű";
/*  66 */   private static final String[] DOUBLE_CONSONANT = { "BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String cleanName(String paramString)
/*     */   {
/*  84 */     String str1 = paramString.toUpperCase(Locale.ENGLISH);
/*     */     
/*  86 */     String[] arrayOfString1 = { "\\-", "[&]", "\\'", "\\.", "[\\,]" };
/*  87 */     for (String str2 : arrayOfString1) {
/*  88 */       str1 = str1.replaceAll(str2, "");
/*     */     }
/*     */     
/*  91 */     str1 = removeAccents(str1);
/*  92 */     str1 = str1.replaceAll("\\s+", "");
/*     */     
/*  94 */     return str1;
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
/*     */   public final Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 110 */     if (!(paramObject instanceof String)) {
/* 111 */       throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
/*     */     }
/*     */     
/* 114 */     return encode((String)paramObject);
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
/*     */   public final String encode(String paramString)
/*     */   {
/* 127 */     if ((paramString == null) || ("".equalsIgnoreCase(paramString)) || (" ".equalsIgnoreCase(paramString)) || (paramString.length() == 1)) {
/* 128 */       return "";
/*     */     }
/*     */     
/*     */ 
/* 132 */     paramString = cleanName(paramString);
/*     */     
/*     */ 
/*     */ 
/* 136 */     paramString = removeVowels(paramString);
/*     */     
/*     */ 
/* 139 */     paramString = removeDoubleConsonants(paramString);
/*     */     
/*     */ 
/* 142 */     paramString = getFirst3Last3(paramString);
/*     */     
/* 144 */     return paramString;
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
/*     */   String getFirst3Last3(String paramString)
/*     */   {
/* 160 */     int i = paramString.length();
/*     */     
/* 162 */     if (i > 6) {
/* 163 */       String str1 = paramString.substring(0, 3);
/* 164 */       String str2 = paramString.substring(i - 3, i);
/* 165 */       return str1 + str2;
/*     */     }
/* 167 */     return paramString;
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
/*     */   int getMinRating(int paramInt)
/*     */   {
/* 185 */     int i = 0;
/*     */     
/* 187 */     if (paramInt <= 4) {
/* 188 */       i = 5;
/* 189 */     } else if ((paramInt >= 5) && (paramInt <= 7)) {
/* 190 */       i = 4;
/* 191 */     } else if ((paramInt >= 8) && (paramInt <= 11)) {
/* 192 */       i = 3;
/* 193 */     } else if (paramInt == 12) {
/* 194 */       i = 2;
/*     */     } else {
/* 196 */       i = 1;
/*     */     }
/*     */     
/* 199 */     return i;
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
/*     */   public boolean isEncodeEquals(String paramString1, String paramString2)
/*     */   {
/* 214 */     if ((paramString1 == null) || ("".equalsIgnoreCase(paramString1)) || (" ".equalsIgnoreCase(paramString1)))
/* 215 */       return false;
/* 216 */     if ((paramString2 == null) || ("".equalsIgnoreCase(paramString2)) || (" ".equalsIgnoreCase(paramString2)))
/* 217 */       return false;
/* 218 */     if ((paramString1.length() == 1) || (paramString2.length() == 1))
/* 219 */       return false;
/* 220 */     if (paramString1.equalsIgnoreCase(paramString2)) {
/* 221 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 225 */     paramString1 = cleanName(paramString1);
/* 226 */     paramString2 = cleanName(paramString2);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 231 */     paramString1 = removeVowels(paramString1);
/* 232 */     paramString2 = removeVowels(paramString2);
/*     */     
/*     */ 
/* 235 */     paramString1 = removeDoubleConsonants(paramString1);
/* 236 */     paramString2 = removeDoubleConsonants(paramString2);
/*     */     
/*     */ 
/* 239 */     paramString1 = getFirst3Last3(paramString1);
/* 240 */     paramString2 = getFirst3Last3(paramString2);
/*     */     
/*     */ 
/*     */ 
/* 244 */     if (Math.abs(paramString1.length() - paramString2.length()) >= 3) {
/* 245 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 250 */     int i = Math.abs(paramString1.length() + paramString2.length());
/* 251 */     int j = 0;
/* 252 */     j = getMinRating(i);
/*     */     
/*     */ 
/*     */ 
/* 256 */     int k = leftToRightThenRightToLeftProcessing(paramString1, paramString2);
/*     */     
/*     */ 
/*     */ 
/* 260 */     return k >= j;
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
/*     */   boolean isVowel(String paramString)
/*     */   {
/* 277 */     return (paramString.equalsIgnoreCase("E")) || (paramString.equalsIgnoreCase("A")) || (paramString.equalsIgnoreCase("O")) || (paramString.equalsIgnoreCase("I")) || (paramString.equalsIgnoreCase("U"));
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
/*     */   int leftToRightThenRightToLeftProcessing(String paramString1, String paramString2)
/*     */   {
/* 295 */     char[] arrayOfChar1 = paramString1.toCharArray();
/* 296 */     char[] arrayOfChar2 = paramString2.toCharArray();
/*     */     
/* 298 */     int i = paramString1.length() - 1;
/* 299 */     int j = paramString2.length() - 1;
/*     */     
/* 301 */     String str1 = "";
/* 302 */     String str2 = "";
/*     */     
/* 304 */     String str3 = "";
/* 305 */     String str4 = "";
/*     */     
/* 307 */     for (int k = 0; k < arrayOfChar1.length; k++) {
/* 308 */       if (k > j) {
/*     */         break;
/*     */       }
/*     */       
/* 312 */       str1 = paramString1.substring(k, k + 1);
/* 313 */       str2 = paramString1.substring(i - k, i - k + 1);
/*     */       
/* 315 */       str3 = paramString2.substring(k, k + 1);
/* 316 */       str4 = paramString2.substring(j - k, j - k + 1);
/*     */       
/*     */ 
/* 319 */       if (str1.equals(str3)) {
/* 320 */         arrayOfChar1[k] = ' ';
/* 321 */         arrayOfChar2[k] = ' ';
/*     */       }
/*     */       
/*     */ 
/* 325 */       if (str2.equals(str4)) {
/* 326 */         arrayOfChar1[(i - k)] = ' ';
/* 327 */         arrayOfChar2[(j - k)] = ' ';
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 332 */     String str5 = new String(arrayOfChar1).replaceAll("\\s+", "");
/* 333 */     String str6 = new String(arrayOfChar2).replaceAll("\\s+", "");
/*     */     
/*     */ 
/* 336 */     if (str5.length() > str6.length()) {
/* 337 */       return Math.abs(6 - str5.length());
/*     */     }
/* 339 */     return Math.abs(6 - str6.length());
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
/*     */   String removeAccents(String paramString)
/*     */   {
/* 352 */     if (paramString == null) {
/* 353 */       return null;
/*     */     }
/*     */     
/* 356 */     StringBuilder localStringBuilder = new StringBuilder();
/* 357 */     int i = paramString.length();
/*     */     
/* 359 */     for (int j = 0; j < i; j++) {
/* 360 */       char c = paramString.charAt(j);
/* 361 */       int k = "ÀàÈèÌìÒòÙùÁáÉéÍíÓóÚúÝýÂâÊêÎîÔôÛûŶŷÃãÕõÑñÄäËëÏïÖöÜüŸÿÅåÇçŐőŰű".indexOf(c);
/* 362 */       if (k > -1) {
/* 363 */         localStringBuilder.append("AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu".charAt(k));
/*     */       } else {
/* 365 */         localStringBuilder.append(c);
/*     */       }
/*     */     }
/*     */     
/* 369 */     return localStringBuilder.toString();
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
/*     */   String removeDoubleConsonants(String paramString)
/*     */   {
/* 385 */     String str1 = paramString.toUpperCase();
/* 386 */     for (String str2 : DOUBLE_CONSONANT) {
/* 387 */       if (str1.contains(str2)) {
/* 388 */         String str3 = str2.substring(0, 1);
/* 389 */         str1 = str1.replace(str2, str3);
/*     */       }
/*     */     }
/* 392 */     return str1;
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
/*     */   String removeVowels(String paramString)
/*     */   {
/* 409 */     String str = paramString.substring(0, 1);
/*     */     
/* 411 */     paramString = paramString.replaceAll("A", "");
/* 412 */     paramString = paramString.replaceAll("E", "");
/* 413 */     paramString = paramString.replaceAll("I", "");
/* 414 */     paramString = paramString.replaceAll("O", "");
/* 415 */     paramString = paramString.replaceAll("U", "");
/*     */     
/* 417 */     paramString = paramString.replaceAll("\\s{2,}\\b", " ");
/*     */     
/*     */ 
/* 420 */     if (isVowel(str)) {
/* 421 */       return str + paramString;
/*     */     }
/* 423 */     return paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\MatchRatingApproachEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */