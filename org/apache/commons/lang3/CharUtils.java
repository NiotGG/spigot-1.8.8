/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharUtils
/*     */ {
/*  32 */   private static final String[] CHAR_STRING_ARRAY = new String[''];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char LF = '\n';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char CR = '\r';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*  54 */     for (int i = 0; i < CHAR_STRING_ARRAY.length; j = (char)(i + 1)) {
/*  55 */       CHAR_STRING_ARRAY[i] = String.valueOf(i);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Character toCharacterObject(char paramChar)
/*     */   {
/*  88 */     return Character.valueOf(paramChar);
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
/*     */   public static Character toCharacterObject(String paramString)
/*     */   {
/* 109 */     if (StringUtils.isEmpty(paramString)) {
/* 110 */       return null;
/*     */     }
/* 112 */     return Character.valueOf(paramString.charAt(0));
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
/*     */   public static char toChar(Character paramCharacter)
/*     */   {
/* 130 */     if (paramCharacter == null) {
/* 131 */       throw new IllegalArgumentException("The Character must not be null");
/*     */     }
/* 133 */     return paramCharacter.charValue();
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
/*     */   public static char toChar(Character paramCharacter, char paramChar)
/*     */   {
/* 150 */     if (paramCharacter == null) {
/* 151 */       return paramChar;
/*     */     }
/* 153 */     return paramCharacter.charValue();
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
/*     */   public static char toChar(String paramString)
/*     */   {
/* 173 */     if (StringUtils.isEmpty(paramString)) {
/* 174 */       throw new IllegalArgumentException("The String must not be empty");
/*     */     }
/* 176 */     return paramString.charAt(0);
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
/*     */   public static char toChar(String paramString, char paramChar)
/*     */   {
/* 195 */     if (StringUtils.isEmpty(paramString)) {
/* 196 */       return paramChar;
/*     */     }
/* 198 */     return paramString.charAt(0);
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
/*     */   public static int toIntValue(char paramChar)
/*     */   {
/* 218 */     if (!isAsciiNumeric(paramChar)) {
/* 219 */       throw new IllegalArgumentException("The character " + paramChar + " is not in the range '0' - '9'");
/*     */     }
/* 221 */     return paramChar - '0';
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
/*     */   public static int toIntValue(char paramChar, int paramInt)
/*     */   {
/* 240 */     if (!isAsciiNumeric(paramChar)) {
/* 241 */       return paramInt;
/*     */     }
/* 243 */     return paramChar - '0';
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
/*     */   public static int toIntValue(Character paramCharacter)
/*     */   {
/* 263 */     if (paramCharacter == null) {
/* 264 */       throw new IllegalArgumentException("The character must not be null");
/*     */     }
/* 266 */     return toIntValue(paramCharacter.charValue());
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
/*     */   public static int toIntValue(Character paramCharacter, int paramInt)
/*     */   {
/* 286 */     if (paramCharacter == null) {
/* 287 */       return paramInt;
/*     */     }
/* 289 */     return toIntValue(paramCharacter.charValue(), paramInt);
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
/*     */   public static String toString(char paramChar)
/*     */   {
/* 308 */     if (paramChar < '') {
/* 309 */       return CHAR_STRING_ARRAY[paramChar];
/*     */     }
/* 311 */     return new String(new char[] { paramChar });
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
/*     */   public static String toString(Character paramCharacter)
/*     */   {
/* 332 */     if (paramCharacter == null) {
/* 333 */       return null;
/*     */     }
/* 335 */     return toString(paramCharacter.charValue());
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
/*     */   public static String unicodeEscaped(char paramChar)
/*     */   {
/* 353 */     if (paramChar < '\020')
/* 354 */       return "\\u000" + Integer.toHexString(paramChar);
/* 355 */     if (paramChar < 'Ā')
/* 356 */       return "\\u00" + Integer.toHexString(paramChar);
/* 357 */     if (paramChar < 'က') {
/* 358 */       return "\\u0" + Integer.toHexString(paramChar);
/*     */     }
/* 360 */     return "\\u" + Integer.toHexString(paramChar);
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
/*     */   public static String unicodeEscaped(Character paramCharacter)
/*     */   {
/* 380 */     if (paramCharacter == null) {
/* 381 */       return null;
/*     */     }
/* 383 */     return unicodeEscaped(paramCharacter.charValue());
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
/*     */   public static boolean isAscii(char paramChar)
/*     */   {
/* 403 */     return paramChar < '';
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
/*     */   public static boolean isAsciiPrintable(char paramChar)
/*     */   {
/* 422 */     return (paramChar >= ' ') && (paramChar < '');
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
/*     */   public static boolean isAsciiControl(char paramChar)
/*     */   {
/* 441 */     return (paramChar < ' ') || (paramChar == '');
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
/*     */   public static boolean isAsciiAlpha(char paramChar)
/*     */   {
/* 460 */     return (isAsciiAlphaUpper(paramChar)) || (isAsciiAlphaLower(paramChar));
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
/*     */   public static boolean isAsciiAlphaUpper(char paramChar)
/*     */   {
/* 479 */     return (paramChar >= 'A') && (paramChar <= 'Z');
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
/*     */   public static boolean isAsciiAlphaLower(char paramChar)
/*     */   {
/* 498 */     return (paramChar >= 'a') && (paramChar <= 'z');
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
/*     */   public static boolean isAsciiNumeric(char paramChar)
/*     */   {
/* 517 */     return (paramChar >= '0') && (paramChar <= '9');
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
/*     */   public static boolean isAsciiAlphanumeric(char paramChar)
/*     */   {
/* 536 */     return (isAsciiAlpha(paramChar)) || (isAsciiNumeric(paramChar));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\CharUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */