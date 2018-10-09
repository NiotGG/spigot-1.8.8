/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.SystemUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WordUtils
/*     */ {
/*     */   public static String wrap(String paramString, int paramInt)
/*     */   {
/*  97 */     return wrap(paramString, paramInt, null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String wrap(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
/*     */   {
/* 173 */     if (paramString1 == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     if (paramString2 == null) {
/* 177 */       paramString2 = SystemUtils.LINE_SEPARATOR;
/*     */     }
/* 179 */     if (paramInt < 1) {
/* 180 */       paramInt = 1;
/*     */     }
/* 182 */     int i = paramString1.length();
/* 183 */     int j = 0;
/* 184 */     StringBuilder localStringBuilder = new StringBuilder(i + 32);
/*     */     
/* 186 */     while (i - j > paramInt) {
/* 187 */       if (paramString1.charAt(j) == ' ') {
/* 188 */         j++;
/*     */       }
/*     */       else {
/* 191 */         int k = paramString1.lastIndexOf(' ', paramInt + j);
/*     */         
/* 193 */         if (k >= j)
/*     */         {
/* 195 */           localStringBuilder.append(paramString1.substring(j, k));
/* 196 */           localStringBuilder.append(paramString2);
/* 197 */           j = k + 1;
/*     */ 
/*     */ 
/*     */         }
/* 201 */         else if (paramBoolean)
/*     */         {
/* 203 */           localStringBuilder.append(paramString1.substring(j, paramInt + j));
/* 204 */           localStringBuilder.append(paramString2);
/* 205 */           j += paramInt;
/*     */         }
/*     */         else {
/* 208 */           k = paramString1.indexOf(' ', paramInt + j);
/* 209 */           if (k >= 0) {
/* 210 */             localStringBuilder.append(paramString1.substring(j, k));
/* 211 */             localStringBuilder.append(paramString2);
/* 212 */             j = k + 1;
/*     */           } else {
/* 214 */             localStringBuilder.append(paramString1.substring(j));
/* 215 */             j = i;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 222 */     localStringBuilder.append(paramString1.substring(j));
/*     */     
/* 224 */     return localStringBuilder.toString();
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
/*     */   public static String capitalize(String paramString)
/*     */   {
/* 252 */     return capitalize(paramString, null);
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
/*     */ 
/*     */ 
/*     */   public static String capitalize(String paramString, char... paramVarArgs)
/*     */   {
/* 285 */     int i = paramVarArgs == null ? -1 : paramVarArgs.length;
/* 286 */     if ((StringUtils.isEmpty(paramString)) || (i == 0)) {
/* 287 */       return paramString;
/*     */     }
/* 289 */     char[] arrayOfChar = paramString.toCharArray();
/* 290 */     int j = 1;
/* 291 */     for (int k = 0; k < arrayOfChar.length; k++) {
/* 292 */       char c = arrayOfChar[k];
/* 293 */       if (isDelimiter(c, paramVarArgs)) {
/* 294 */         j = 1;
/* 295 */       } else if (j != 0) {
/* 296 */         arrayOfChar[k] = Character.toTitleCase(c);
/* 297 */         j = 0;
/*     */       }
/*     */     }
/* 300 */     return new String(arrayOfChar);
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
/*     */   public static String capitalizeFully(String paramString)
/*     */   {
/* 324 */     return capitalizeFully(paramString, null);
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
/*     */   public static String capitalizeFully(String paramString, char... paramVarArgs)
/*     */   {
/* 354 */     int i = paramVarArgs == null ? -1 : paramVarArgs.length;
/* 355 */     if ((StringUtils.isEmpty(paramString)) || (i == 0)) {
/* 356 */       return paramString;
/*     */     }
/* 358 */     paramString = paramString.toLowerCase();
/* 359 */     return capitalize(paramString, paramVarArgs);
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
/*     */   public static String uncapitalize(String paramString)
/*     */   {
/* 381 */     return uncapitalize(paramString, null);
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
/*     */   public static String uncapitalize(String paramString, char... paramVarArgs)
/*     */   {
/* 410 */     int i = paramVarArgs == null ? -1 : paramVarArgs.length;
/* 411 */     if ((StringUtils.isEmpty(paramString)) || (i == 0)) {
/* 412 */       return paramString;
/*     */     }
/* 414 */     char[] arrayOfChar = paramString.toCharArray();
/* 415 */     int j = 1;
/* 416 */     for (int k = 0; k < arrayOfChar.length; k++) {
/* 417 */       char c = arrayOfChar[k];
/* 418 */       if (isDelimiter(c, paramVarArgs)) {
/* 419 */         j = 1;
/* 420 */       } else if (j != 0) {
/* 421 */         arrayOfChar[k] = Character.toLowerCase(c);
/* 422 */         j = 0;
/*     */       }
/*     */     }
/* 425 */     return new String(arrayOfChar);
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
/*     */   public static String swapCase(String paramString)
/*     */   {
/* 452 */     if (StringUtils.isEmpty(paramString)) {
/* 453 */       return paramString;
/*     */     }
/* 455 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     
/* 457 */     boolean bool = true;
/*     */     
/* 459 */     for (int i = 0; i < arrayOfChar.length; i++) {
/* 460 */       char c = arrayOfChar[i];
/* 461 */       if (Character.isUpperCase(c)) {
/* 462 */         arrayOfChar[i] = Character.toLowerCase(c);
/* 463 */         bool = false;
/* 464 */       } else if (Character.isTitleCase(c)) {
/* 465 */         arrayOfChar[i] = Character.toLowerCase(c);
/* 466 */         bool = false;
/* 467 */       } else if (Character.isLowerCase(c)) {
/* 468 */         if (bool) {
/* 469 */           arrayOfChar[i] = Character.toTitleCase(c);
/* 470 */           bool = false;
/*     */         } else {
/* 472 */           arrayOfChar[i] = Character.toUpperCase(c);
/*     */         }
/*     */       } else {
/* 475 */         bool = Character.isWhitespace(c);
/*     */       }
/*     */     }
/* 478 */     return new String(arrayOfChar);
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
/*     */   public static String initials(String paramString)
/*     */   {
/* 505 */     return initials(paramString, null);
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
/*     */   public static String initials(String paramString, char... paramVarArgs)
/*     */   {
/* 536 */     if (StringUtils.isEmpty(paramString)) {
/* 537 */       return paramString;
/*     */     }
/* 539 */     if ((paramVarArgs != null) && (paramVarArgs.length == 0)) {
/* 540 */       return "";
/*     */     }
/* 542 */     int i = paramString.length();
/* 543 */     char[] arrayOfChar = new char[i / 2 + 1];
/* 544 */     int j = 0;
/* 545 */     int k = 1;
/* 546 */     for (int m = 0; m < i; m++) {
/* 547 */       char c = paramString.charAt(m);
/*     */       
/* 549 */       if (isDelimiter(c, paramVarArgs)) {
/* 550 */         k = 1;
/* 551 */       } else if (k != 0) {
/* 552 */         arrayOfChar[(j++)] = c;
/* 553 */         k = 0;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 558 */     return new String(arrayOfChar, 0, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isDelimiter(char paramChar, char[] paramArrayOfChar)
/*     */   {
/* 570 */     if (paramArrayOfChar == null) {
/* 571 */       return Character.isWhitespace(paramChar);
/*     */     }
/* 573 */     for (char c : paramArrayOfChar) {
/* 574 */       if (paramChar == c) {
/* 575 */         return true;
/*     */       }
/*     */     }
/* 578 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\WordUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */