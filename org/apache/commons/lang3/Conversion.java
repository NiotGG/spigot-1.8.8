/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.UUID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Conversion
/*      */ {
/*      */   public static int hexDigitToInt(char paramChar)
/*      */   {
/*   81 */     int i = Character.digit(paramChar, 16);
/*   82 */     if (i < 0) {
/*   83 */       throw new IllegalArgumentException("Cannot interpret '" + paramChar + "' as a hexadecimal digit");
/*      */     }
/*      */     
/*      */ 
/*   87 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int hexDigitMsb0ToInt(char paramChar)
/*      */   {
/*  103 */     switch (paramChar) {
/*      */     case '0': 
/*  105 */       return 0;
/*      */     case '1': 
/*  107 */       return 8;
/*      */     case '2': 
/*  109 */       return 4;
/*      */     case '3': 
/*  111 */       return 12;
/*      */     case '4': 
/*  113 */       return 2;
/*      */     case '5': 
/*  115 */       return 10;
/*      */     case '6': 
/*  117 */       return 6;
/*      */     case '7': 
/*  119 */       return 14;
/*      */     case '8': 
/*  121 */       return 1;
/*      */     case '9': 
/*  123 */       return 9;
/*      */     case 'A': 
/*      */     case 'a': 
/*  126 */       return 5;
/*      */     case 'B': 
/*      */     case 'b': 
/*  129 */       return 13;
/*      */     case 'C': 
/*      */     case 'c': 
/*  132 */       return 3;
/*      */     case 'D': 
/*      */     case 'd': 
/*  135 */       return 11;
/*      */     case 'E': 
/*      */     case 'e': 
/*  138 */       return 7;
/*      */     case 'F': 
/*      */     case 'f': 
/*  141 */       return 15;
/*      */     }
/*  143 */     throw new IllegalArgumentException("Cannot interpret '" + paramChar + "' as a hexadecimal digit");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] hexDigitToBinary(char paramChar)
/*      */   {
/*  163 */     switch (paramChar) {
/*      */     case '0': 
/*  165 */       return new boolean[] { false, false, false, false };
/*      */     case '1': 
/*  167 */       return new boolean[] { true, false, false, false };
/*      */     case '2': 
/*  169 */       return new boolean[] { false, true, false, false };
/*      */     case '3': 
/*  171 */       return new boolean[] { true, true, false, false };
/*      */     case '4': 
/*  173 */       return new boolean[] { false, false, true, false };
/*      */     case '5': 
/*  175 */       return new boolean[] { true, false, true, false };
/*      */     case '6': 
/*  177 */       return new boolean[] { false, true, true, false };
/*      */     case '7': 
/*  179 */       return new boolean[] { true, true, true, false };
/*      */     case '8': 
/*  181 */       return new boolean[] { false, false, false, true };
/*      */     case '9': 
/*  183 */       return new boolean[] { true, false, false, true };
/*      */     case 'A': 
/*      */     case 'a': 
/*  186 */       return new boolean[] { false, true, false, true };
/*      */     case 'B': 
/*      */     case 'b': 
/*  189 */       return new boolean[] { true, true, false, true };
/*      */     case 'C': 
/*      */     case 'c': 
/*  192 */       return new boolean[] { false, false, true, true };
/*      */     case 'D': 
/*      */     case 'd': 
/*  195 */       return new boolean[] { true, false, true, true };
/*      */     case 'E': 
/*      */     case 'e': 
/*  198 */       return new boolean[] { false, true, true, true };
/*      */     case 'F': 
/*      */     case 'f': 
/*  201 */       return new boolean[] { true, true, true, true };
/*      */     }
/*  203 */     throw new IllegalArgumentException("Cannot interpret '" + paramChar + "' as a hexadecimal digit");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] hexDigitMsb0ToBinary(char paramChar)
/*      */   {
/*  223 */     switch (paramChar) {
/*      */     case '0': 
/*  225 */       return new boolean[] { false, false, false, false };
/*      */     case '1': 
/*  227 */       return new boolean[] { false, false, false, true };
/*      */     case '2': 
/*  229 */       return new boolean[] { false, false, true, false };
/*      */     case '3': 
/*  231 */       return new boolean[] { false, false, true, true };
/*      */     case '4': 
/*  233 */       return new boolean[] { false, true, false, false };
/*      */     case '5': 
/*  235 */       return new boolean[] { false, true, false, true };
/*      */     case '6': 
/*  237 */       return new boolean[] { false, true, true, false };
/*      */     case '7': 
/*  239 */       return new boolean[] { false, true, true, true };
/*      */     case '8': 
/*  241 */       return new boolean[] { true, false, false, false };
/*      */     case '9': 
/*  243 */       return new boolean[] { true, false, false, true };
/*      */     case 'A': 
/*      */     case 'a': 
/*  246 */       return new boolean[] { true, false, true, false };
/*      */     case 'B': 
/*      */     case 'b': 
/*  249 */       return new boolean[] { true, false, true, true };
/*      */     case 'C': 
/*      */     case 'c': 
/*  252 */       return new boolean[] { true, true, false, false };
/*      */     case 'D': 
/*      */     case 'd': 
/*  255 */       return new boolean[] { true, true, false, true };
/*      */     case 'E': 
/*      */     case 'e': 
/*  258 */       return new boolean[] { true, true, true, false };
/*      */     case 'F': 
/*      */     case 'f': 
/*  261 */       return new boolean[] { true, true, true, true };
/*      */     }
/*  263 */     throw new IllegalArgumentException("Cannot interpret '" + paramChar + "' as a hexadecimal digit");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryToHexDigit(boolean[] paramArrayOfBoolean)
/*      */   {
/*  284 */     return binaryToHexDigit(paramArrayOfBoolean, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryToHexDigit(boolean[] paramArrayOfBoolean, int paramInt)
/*      */   {
/*  303 */     if (paramArrayOfBoolean.length == 0) {
/*  304 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  306 */     if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  307 */       if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  308 */         if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  309 */           if (paramArrayOfBoolean[paramInt] != 0) {
/*  310 */             return 'f';
/*      */           }
/*  312 */           return 'e';
/*      */         }
/*      */         
/*  315 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  316 */           return 'd';
/*      */         }
/*  318 */         return 'c';
/*      */       }
/*      */       
/*      */ 
/*  322 */       if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  323 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  324 */           return 'b';
/*      */         }
/*  326 */         return 'a';
/*      */       }
/*      */       
/*  329 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  330 */         return '9';
/*      */       }
/*  332 */       return '8';
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  337 */     if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  338 */       if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  339 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  340 */           return '7';
/*      */         }
/*  342 */         return '6';
/*      */       }
/*      */       
/*  345 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  346 */         return '5';
/*      */       }
/*  348 */       return '4';
/*      */     }
/*      */     
/*      */ 
/*  352 */     if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  353 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  354 */         return '3';
/*      */       }
/*  356 */       return '2';
/*      */     }
/*      */     
/*  359 */     if (paramArrayOfBoolean[paramInt] != 0) {
/*  360 */       return '1';
/*      */     }
/*  362 */     return '0';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] paramArrayOfBoolean)
/*      */   {
/*  385 */     return binaryToHexDigitMsb0_4bits(paramArrayOfBoolean, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] paramArrayOfBoolean, int paramInt)
/*      */   {
/*  406 */     if (paramArrayOfBoolean.length > 8) {
/*  407 */       throw new IllegalArgumentException("src.length>8: src.length=" + paramArrayOfBoolean.length);
/*      */     }
/*  409 */     if (paramArrayOfBoolean.length - paramInt < 4) {
/*  410 */       throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + paramArrayOfBoolean.length + ", srcPos=" + paramInt);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  415 */     if (paramArrayOfBoolean[(paramInt + 3)] != 0) {
/*  416 */       if (paramArrayOfBoolean[(paramInt + 2)] != 0) {
/*  417 */         if (paramArrayOfBoolean[(paramInt + 1)] != 0) {
/*  418 */           if (paramArrayOfBoolean[paramInt] != 0) {
/*  419 */             return 'f';
/*      */           }
/*  421 */           return '7';
/*      */         }
/*      */         
/*  424 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  425 */           return 'b';
/*      */         }
/*  427 */         return '3';
/*      */       }
/*      */       
/*      */ 
/*  431 */       if (paramArrayOfBoolean[(paramInt + 1)] != 0) {
/*  432 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  433 */           return 'd';
/*      */         }
/*  435 */         return '5';
/*      */       }
/*      */       
/*  438 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  439 */         return '9';
/*      */       }
/*  441 */       return '1';
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  446 */     if (paramArrayOfBoolean[(paramInt + 2)] != 0) {
/*  447 */       if (paramArrayOfBoolean[(paramInt + 1)] != 0) {
/*  448 */         if (paramArrayOfBoolean[paramInt] != 0) {
/*  449 */           return 'e';
/*      */         }
/*  451 */         return '6';
/*      */       }
/*      */       
/*  454 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  455 */         return 'a';
/*      */       }
/*  457 */       return '2';
/*      */     }
/*      */     
/*      */ 
/*  461 */     if (paramArrayOfBoolean[(paramInt + 1)] != 0) {
/*  462 */       if (paramArrayOfBoolean[paramInt] != 0) {
/*  463 */         return 'c';
/*      */       }
/*  465 */       return '4';
/*      */     }
/*      */     
/*  468 */     if (paramArrayOfBoolean[paramInt] != 0) {
/*  469 */       return '8';
/*      */     }
/*  471 */     return '0';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] paramArrayOfBoolean)
/*      */   {
/*  494 */     return binaryBeMsb0ToHexDigit(paramArrayOfBoolean, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] paramArrayOfBoolean, int paramInt)
/*      */   {
/*  514 */     if (paramArrayOfBoolean.length == 0) {
/*  515 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  517 */     int i = paramArrayOfBoolean.length - 1 - paramInt;
/*  518 */     int j = Math.min(4, i + 1);
/*  519 */     boolean[] arrayOfBoolean = new boolean[4];
/*  520 */     System.arraycopy(paramArrayOfBoolean, i + 1 - j, arrayOfBoolean, 4 - j, j);
/*  521 */     paramArrayOfBoolean = arrayOfBoolean;
/*  522 */     paramInt = 0;
/*  523 */     if (paramArrayOfBoolean[paramInt] != 0) {
/*  524 */       if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  525 */         if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  526 */           if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  527 */             return 'f';
/*      */           }
/*  529 */           return 'e';
/*      */         }
/*      */         
/*  532 */         if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  533 */           return 'd';
/*      */         }
/*  535 */         return 'c';
/*      */       }
/*      */       
/*      */ 
/*  539 */       if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  540 */         if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  541 */           return 'b';
/*      */         }
/*  543 */         return 'a';
/*      */       }
/*      */       
/*  546 */       if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  547 */         return '9';
/*      */       }
/*  549 */       return '8';
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  554 */     if ((paramArrayOfBoolean.length > paramInt + 1) && (paramArrayOfBoolean[(paramInt + 1)] != 0)) {
/*  555 */       if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  556 */         if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  557 */           return '7';
/*      */         }
/*  559 */         return '6';
/*      */       }
/*      */       
/*  562 */       if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  563 */         return '5';
/*      */       }
/*  565 */       return '4';
/*      */     }
/*      */     
/*      */ 
/*  569 */     if ((paramArrayOfBoolean.length > paramInt + 2) && (paramArrayOfBoolean[(paramInt + 2)] != 0)) {
/*  570 */       if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  571 */         return '3';
/*      */       }
/*  573 */       return '2';
/*      */     }
/*      */     
/*  576 */     if ((paramArrayOfBoolean.length > paramInt + 3) && (paramArrayOfBoolean[(paramInt + 3)] != 0)) {
/*  577 */       return '1';
/*      */     }
/*  579 */     return '0';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char intToHexDigit(int paramInt)
/*      */   {
/*  605 */     char c = Character.forDigit(paramInt, 16);
/*  606 */     if (c == 0) {
/*  607 */       throw new IllegalArgumentException("nibble value not between 0 and 15: " + paramInt);
/*      */     }
/*  609 */     return c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static char intToHexDigitMsb0(int paramInt)
/*      */   {
/*  631 */     switch (paramInt) {
/*      */     case 0: 
/*  633 */       return '0';
/*      */     case 1: 
/*  635 */       return '8';
/*      */     case 2: 
/*  637 */       return '4';
/*      */     case 3: 
/*  639 */       return 'c';
/*      */     case 4: 
/*  641 */       return '2';
/*      */     case 5: 
/*  643 */       return 'a';
/*      */     case 6: 
/*  645 */       return '6';
/*      */     case 7: 
/*  647 */       return 'e';
/*      */     case 8: 
/*  649 */       return '1';
/*      */     case 9: 
/*  651 */       return '9';
/*      */     case 10: 
/*  653 */       return '5';
/*      */     case 11: 
/*  655 */       return 'd';
/*      */     case 12: 
/*  657 */       return '3';
/*      */     case 13: 
/*  659 */       return 'b';
/*      */     case 14: 
/*  661 */       return '7';
/*      */     case 15: 
/*  663 */       return 'f';
/*      */     }
/*  665 */     throw new IllegalArgumentException("nibble value not between 0 and 15: " + paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long intArrayToLong(int[] paramArrayOfInt, int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*      */   {
/*  687 */     if (((paramArrayOfInt.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/*  688 */       return paramLong;
/*      */     }
/*  690 */     if ((paramInt3 - 1) * 32 + paramInt2 >= 64) {
/*  691 */       throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  694 */     long l1 = paramLong;
/*  695 */     int i = 0;
/*  696 */     for (int j = 0; j < paramInt3; j++) {
/*  697 */       i = j * 32 + paramInt2;
/*  698 */       long l2 = (0xFFFFFFFF & paramArrayOfInt[(j + paramInt1)]) << i;
/*  699 */       long l3 = 4294967295L << i;
/*  700 */       l1 = l1 & (l3 ^ 0xFFFFFFFFFFFFFFFF) | l2;
/*      */     }
/*  702 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long shortArrayToLong(short[] paramArrayOfShort, int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*      */   {
/*  724 */     if (((paramArrayOfShort.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/*  725 */       return paramLong;
/*      */     }
/*  727 */     if ((paramInt3 - 1) * 16 + paramInt2 >= 64) {
/*  728 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  731 */     long l1 = paramLong;
/*  732 */     int i = 0;
/*  733 */     for (int j = 0; j < paramInt3; j++) {
/*  734 */       i = j * 16 + paramInt2;
/*  735 */       long l2 = (0xFFFF & paramArrayOfShort[(j + paramInt1)]) << i;
/*  736 */       long l3 = 65535L << i;
/*  737 */       l1 = l1 & (l3 ^ 0xFFFFFFFFFFFFFFFF) | l2;
/*      */     }
/*  739 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int shortArrayToInt(short[] paramArrayOfShort, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  761 */     if (((paramArrayOfShort.length == 0) && (paramInt1 == 0)) || (0 == paramInt4)) {
/*  762 */       return paramInt2;
/*      */     }
/*  764 */     if ((paramInt4 - 1) * 16 + paramInt3 >= 32) {
/*  765 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  768 */     int i = paramInt2;
/*  769 */     int j = 0;
/*  770 */     for (int k = 0; k < paramInt4; k++) {
/*  771 */       j = k * 16 + paramInt3;
/*  772 */       int m = (0xFFFF & paramArrayOfShort[(k + paramInt1)]) << j;
/*  773 */       int n = 65535 << j;
/*  774 */       i = i & (n ^ 0xFFFFFFFF) | m;
/*      */     }
/*  776 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long byteArrayToLong(byte[] paramArrayOfByte, int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*      */   {
/*  798 */     if (((paramArrayOfByte.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/*  799 */       return paramLong;
/*      */     }
/*  801 */     if ((paramInt3 - 1) * 8 + paramInt2 >= 64) {
/*  802 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  805 */     long l1 = paramLong;
/*  806 */     int i = 0;
/*  807 */     for (int j = 0; j < paramInt3; j++) {
/*  808 */       i = j * 8 + paramInt2;
/*  809 */       long l2 = (0xFF & paramArrayOfByte[(j + paramInt1)]) << i;
/*  810 */       long l3 = 255L << i;
/*  811 */       l1 = l1 & (l3 ^ 0xFFFFFFFFFFFFFFFF) | l2;
/*      */     }
/*  813 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int byteArrayToInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  834 */     if (((paramArrayOfByte.length == 0) && (paramInt1 == 0)) || (0 == paramInt4)) {
/*  835 */       return paramInt2;
/*      */     }
/*  837 */     if ((paramInt4 - 1) * 8 + paramInt3 >= 32) {
/*  838 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  841 */     int i = paramInt2;
/*  842 */     int j = 0;
/*  843 */     for (int k = 0; k < paramInt4; k++) {
/*  844 */       j = k * 8 + paramInt3;
/*  845 */       int m = (0xFF & paramArrayOfByte[(k + paramInt1)]) << j;
/*  846 */       int n = 255 << j;
/*  847 */       i = i & (n ^ 0xFFFFFFFF) | m;
/*      */     }
/*  849 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short byteArrayToShort(byte[] paramArrayOfByte, int paramInt1, short paramShort, int paramInt2, int paramInt3)
/*      */   {
/*  871 */     if (((paramArrayOfByte.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/*  872 */       return paramShort;
/*      */     }
/*  874 */     if ((paramInt3 - 1) * 8 + paramInt2 >= 16) {
/*  875 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  878 */     short s = paramShort;
/*  879 */     int i = 0;
/*  880 */     for (int j = 0; j < paramInt3; j++) {
/*  881 */       i = j * 8 + paramInt2;
/*  882 */       int k = (0xFF & paramArrayOfByte[(j + paramInt1)]) << i;
/*  883 */       int m = 255 << i;
/*  884 */       s = (short)(s & (m ^ 0xFFFFFFFF) | k);
/*      */     }
/*  886 */     return s;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long hexToLong(String paramString, int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*      */   {
/*  905 */     if (0 == paramInt3) {
/*  906 */       return paramLong;
/*      */     }
/*  908 */     if ((paramInt3 - 1) * 4 + paramInt2 >= 64) {
/*  909 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  912 */     long l1 = paramLong;
/*  913 */     int i = 0;
/*  914 */     for (int j = 0; j < paramInt3; j++) {
/*  915 */       i = j * 4 + paramInt2;
/*  916 */       long l2 = (0xF & hexDigitToInt(paramString.charAt(j + paramInt1))) << i;
/*  917 */       long l3 = 15L << i;
/*  918 */       l1 = l1 & (l3 ^ 0xFFFFFFFFFFFFFFFF) | l2;
/*      */     }
/*  920 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int hexToInt(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  939 */     if (0 == paramInt4) {
/*  940 */       return paramInt2;
/*      */     }
/*  942 */     if ((paramInt4 - 1) * 4 + paramInt3 >= 32) {
/*  943 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  946 */     int i = paramInt2;
/*  947 */     int j = 0;
/*  948 */     for (int k = 0; k < paramInt4; k++) {
/*  949 */       j = k * 4 + paramInt3;
/*  950 */       int m = (0xF & hexDigitToInt(paramString.charAt(k + paramInt1))) << j;
/*  951 */       int n = 15 << j;
/*  952 */       i = i & (n ^ 0xFFFFFFFF) | m;
/*      */     }
/*  954 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short hexToShort(String paramString, int paramInt1, short paramShort, int paramInt2, int paramInt3)
/*      */   {
/*  973 */     if (0 == paramInt3) {
/*  974 */       return paramShort;
/*      */     }
/*  976 */     if ((paramInt3 - 1) * 4 + paramInt2 >= 16) {
/*  977 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  980 */     short s = paramShort;
/*  981 */     int i = 0;
/*  982 */     for (int j = 0; j < paramInt3; j++) {
/*  983 */       i = j * 4 + paramInt2;
/*  984 */       int k = (0xF & hexDigitToInt(paramString.charAt(j + paramInt1))) << i;
/*  985 */       int m = 15 << i;
/*  986 */       s = (short)(s & (m ^ 0xFFFFFFFF) | k);
/*      */     }
/*  988 */     return s;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte hexToByte(String paramString, int paramInt1, byte paramByte, int paramInt2, int paramInt3)
/*      */   {
/* 1007 */     if (0 == paramInt3) {
/* 1008 */       return paramByte;
/*      */     }
/* 1010 */     if ((paramInt3 - 1) * 4 + paramInt2 >= 8) {
/* 1011 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1014 */     byte b = paramByte;
/* 1015 */     int i = 0;
/* 1016 */     for (int j = 0; j < paramInt3; j++) {
/* 1017 */       i = j * 4 + paramInt2;
/* 1018 */       int k = (0xF & hexDigitToInt(paramString.charAt(j + paramInt1))) << i;
/* 1019 */       int m = 15 << i;
/* 1020 */       b = (byte)(b & (m ^ 0xFFFFFFFF) | k);
/*      */     }
/* 1022 */     return b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long binaryToLong(boolean[] paramArrayOfBoolean, int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*      */   {
/* 1044 */     if (((paramArrayOfBoolean.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/* 1045 */       return paramLong;
/*      */     }
/* 1047 */     if (paramInt3 - 1 + paramInt2 >= 64) {
/* 1048 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1051 */     long l1 = paramLong;
/* 1052 */     int i = 0;
/* 1053 */     for (int j = 0; j < paramInt3; j++) {
/* 1054 */       i = j * 1 + paramInt2;
/* 1055 */       long l2 = (paramArrayOfBoolean[(j + paramInt1)] != 0 ? 1L : 0L) << i;
/* 1056 */       long l3 = 1L << i;
/* 1057 */       l1 = l1 & (l3 ^ 0xFFFFFFFFFFFFFFFF) | l2;
/*      */     }
/* 1059 */     return l1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int binaryToInt(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1080 */     if (((paramArrayOfBoolean.length == 0) && (paramInt1 == 0)) || (0 == paramInt4)) {
/* 1081 */       return paramInt2;
/*      */     }
/* 1083 */     if (paramInt4 - 1 + paramInt3 >= 32) {
/* 1084 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1087 */     int i = paramInt2;
/* 1088 */     int j = 0;
/* 1089 */     for (int k = 0; k < paramInt4; k++) {
/* 1090 */       j = k * 1 + paramInt3;
/* 1091 */       int m = (paramArrayOfBoolean[(k + paramInt1)] != 0 ? 1 : 0) << j;
/* 1092 */       int n = 1 << j;
/* 1093 */       i = i & (n ^ 0xFFFFFFFF) | m;
/*      */     }
/* 1095 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short binaryToShort(boolean[] paramArrayOfBoolean, int paramInt1, short paramShort, int paramInt2, int paramInt3)
/*      */   {
/* 1117 */     if (((paramArrayOfBoolean.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/* 1118 */       return paramShort;
/*      */     }
/* 1120 */     if (paramInt3 - 1 + paramInt2 >= 16) {
/* 1121 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1124 */     short s = paramShort;
/* 1125 */     int i = 0;
/* 1126 */     for (int j = 0; j < paramInt3; j++) {
/* 1127 */       i = j * 1 + paramInt2;
/* 1128 */       int k = (paramArrayOfBoolean[(j + paramInt1)] != 0 ? 1 : 0) << i;
/* 1129 */       int m = 1 << i;
/* 1130 */       s = (short)(s & (m ^ 0xFFFFFFFF) | k);
/*      */     }
/* 1132 */     return s;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte binaryToByte(boolean[] paramArrayOfBoolean, int paramInt1, byte paramByte, int paramInt2, int paramInt3)
/*      */   {
/* 1154 */     if (((paramArrayOfBoolean.length == 0) && (paramInt1 == 0)) || (0 == paramInt3)) {
/* 1155 */       return paramByte;
/*      */     }
/* 1157 */     if (paramInt3 - 1 + paramInt2 >= 8) {
/* 1158 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 8");
/*      */     }
/* 1160 */     byte b = paramByte;
/* 1161 */     int i = 0;
/* 1162 */     for (int j = 0; j < paramInt3; j++) {
/* 1163 */       i = j * 1 + paramInt2;
/* 1164 */       int k = (paramArrayOfBoolean[(j + paramInt1)] != 0 ? 1 : 0) << i;
/* 1165 */       int m = 1 << i;
/* 1166 */       b = (byte)(b & (m ^ 0xFFFFFFFF) | k);
/*      */     }
/* 1168 */     return b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int[] longToIntArray(long paramLong, int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
/*      */   {
/* 1189 */     if (0 == paramInt3) {
/* 1190 */       return paramArrayOfInt;
/*      */     }
/* 1192 */     if ((paramInt3 - 1) * 32 + paramInt1 >= 64) {
/* 1193 */       throw new IllegalArgumentException("(nInts-1)*32+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1196 */     int i = 0;
/* 1197 */     for (int j = 0; j < paramInt3; j++) {
/* 1198 */       i = j * 32 + paramInt1;
/* 1199 */       paramArrayOfInt[(paramInt2 + j)] = ((int)(0xFFFFFFFFFFFFFFFF & paramLong >> i));
/*      */     }
/* 1201 */     return paramArrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short[] longToShortArray(long paramLong, int paramInt1, short[] paramArrayOfShort, int paramInt2, int paramInt3)
/*      */   {
/* 1223 */     if (0 == paramInt3) {
/* 1224 */       return paramArrayOfShort;
/*      */     }
/* 1226 */     if ((paramInt3 - 1) * 16 + paramInt1 >= 64) {
/* 1227 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1230 */     int i = 0;
/* 1231 */     for (int j = 0; j < paramInt3; j++) {
/* 1232 */       i = j * 16 + paramInt1;
/* 1233 */       paramArrayOfShort[(paramInt2 + j)] = ((short)(int)(0xFFFF & paramLong >> i));
/*      */     }
/* 1235 */     return paramArrayOfShort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short[] intToShortArray(int paramInt1, int paramInt2, short[] paramArrayOfShort, int paramInt3, int paramInt4)
/*      */   {
/* 1257 */     if (0 == paramInt4) {
/* 1258 */       return paramArrayOfShort;
/*      */     }
/* 1260 */     if ((paramInt4 - 1) * 16 + paramInt2 >= 32) {
/* 1261 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1264 */     int i = 0;
/* 1265 */     for (int j = 0; j < paramInt4; j++) {
/* 1266 */       i = j * 16 + paramInt2;
/* 1267 */       paramArrayOfShort[(paramInt3 + j)] = ((short)(0xFFFF & paramInt1 >> i));
/*      */     }
/* 1269 */     return paramArrayOfShort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] longToByteArray(long paramLong, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 1291 */     if (0 == paramInt3) {
/* 1292 */       return paramArrayOfByte;
/*      */     }
/* 1294 */     if ((paramInt3 - 1) * 8 + paramInt1 >= 64) {
/* 1295 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1298 */     int i = 0;
/* 1299 */     for (int j = 0; j < paramInt3; j++) {
/* 1300 */       i = j * 8 + paramInt1;
/* 1301 */       paramArrayOfByte[(paramInt2 + j)] = ((byte)(int)(0xFF & paramLong >> i));
/*      */     }
/* 1303 */     return paramArrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] intToByteArray(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4)
/*      */   {
/* 1324 */     if (0 == paramInt4) {
/* 1325 */       return paramArrayOfByte;
/*      */     }
/* 1327 */     if ((paramInt4 - 1) * 8 + paramInt2 >= 32) {
/* 1328 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1331 */     int i = 0;
/* 1332 */     for (int j = 0; j < paramInt4; j++) {
/* 1333 */       i = j * 8 + paramInt2;
/* 1334 */       paramArrayOfByte[(paramInt3 + j)] = ((byte)(0xFF & paramInt1 >> i));
/*      */     }
/* 1336 */     return paramArrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] shortToByteArray(short paramShort, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/* 1358 */     if (0 == paramInt3) {
/* 1359 */       return paramArrayOfByte;
/*      */     }
/* 1361 */     if ((paramInt3 - 1) * 8 + paramInt1 >= 16) {
/* 1362 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1365 */     short s = 0;
/* 1366 */     for (int i = 0; i < paramInt3; i++) {
/* 1367 */       s = i * 8 + paramInt1;
/* 1368 */       paramArrayOfByte[(paramInt2 + i)] = ((byte)(0xFF & paramShort >> s));
/*      */     }
/* 1370 */     return paramArrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String longToHex(long paramLong, int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1390 */     if (0 == paramInt3) {
/* 1391 */       return paramString;
/*      */     }
/* 1393 */     if ((paramInt3 - 1) * 4 + paramInt1 >= 64) {
/* 1394 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1397 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1398 */     int i = 0;
/* 1399 */     int j = localStringBuilder.length();
/* 1400 */     for (int k = 0; k < paramInt3; k++) {
/* 1401 */       i = k * 4 + paramInt1;
/* 1402 */       int m = (int)(0xF & paramLong >> i);
/* 1403 */       if (paramInt2 + k == j) {
/* 1404 */         j++;
/* 1405 */         localStringBuilder.append(intToHexDigit(m));
/*      */       } else {
/* 1407 */         localStringBuilder.setCharAt(paramInt2 + k, intToHexDigit(m));
/*      */       }
/*      */     }
/* 1410 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String intToHex(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4)
/*      */   {
/* 1430 */     if (0 == paramInt4) {
/* 1431 */       return paramString;
/*      */     }
/* 1433 */     if ((paramInt4 - 1) * 4 + paramInt2 >= 32) {
/* 1434 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1437 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1438 */     int i = 0;
/* 1439 */     int j = localStringBuilder.length();
/* 1440 */     for (int k = 0; k < paramInt4; k++) {
/* 1441 */       i = k * 4 + paramInt2;
/* 1442 */       int m = 0xF & paramInt1 >> i;
/* 1443 */       if (paramInt3 + k == j) {
/* 1444 */         j++;
/* 1445 */         localStringBuilder.append(intToHexDigit(m));
/*      */       } else {
/* 1447 */         localStringBuilder.setCharAt(paramInt3 + k, intToHexDigit(m));
/*      */       }
/*      */     }
/* 1450 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String shortToHex(short paramShort, int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1470 */     if (0 == paramInt3) {
/* 1471 */       return paramString;
/*      */     }
/* 1473 */     if ((paramInt3 - 1) * 4 + paramInt1 >= 16) {
/* 1474 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1477 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1478 */     short s = 0;
/* 1479 */     int i = localStringBuilder.length();
/* 1480 */     for (int j = 0; j < paramInt3; j++) {
/* 1481 */       s = j * 4 + paramInt1;
/* 1482 */       int k = 0xF & paramShort >> s;
/* 1483 */       if (paramInt2 + j == i) {
/* 1484 */         i++;
/* 1485 */         localStringBuilder.append(intToHexDigit(k));
/*      */       } else {
/* 1487 */         localStringBuilder.setCharAt(paramInt2 + j, intToHexDigit(k));
/*      */       }
/*      */     }
/* 1490 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String byteToHex(byte paramByte, int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1510 */     if (0 == paramInt3) {
/* 1511 */       return paramString;
/*      */     }
/* 1513 */     if ((paramInt3 - 1) * 4 + paramInt1 >= 8) {
/* 1514 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1517 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1518 */     byte b = 0;
/* 1519 */     int i = localStringBuilder.length();
/* 1520 */     for (int j = 0; j < paramInt3; j++) {
/* 1521 */       b = j * 4 + paramInt1;
/* 1522 */       int k = 0xF & paramByte >> b;
/* 1523 */       if (paramInt2 + j == i) {
/* 1524 */         i++;
/* 1525 */         localStringBuilder.append(intToHexDigit(k));
/*      */       } else {
/* 1527 */         localStringBuilder.setCharAt(paramInt2 + j, intToHexDigit(k));
/*      */       }
/*      */     }
/* 1530 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] longToBinary(long paramLong, int paramInt1, boolean[] paramArrayOfBoolean, int paramInt2, int paramInt3)
/*      */   {
/* 1552 */     if (0 == paramInt3) {
/* 1553 */       return paramArrayOfBoolean;
/*      */     }
/* 1555 */     if (paramInt3 - 1 + paramInt1 >= 64) {
/* 1556 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1559 */     int i = 0;
/* 1560 */     for (int j = 0; j < paramInt3; j++) {
/* 1561 */       i = j * 1 + paramInt1;
/* 1562 */       paramArrayOfBoolean[(paramInt2 + j)] = ((1L & paramLong >> i) != 0L ? 1 : false);
/*      */     }
/* 1564 */     return paramArrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] intToBinary(int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean, int paramInt3, int paramInt4)
/*      */   {
/* 1586 */     if (0 == paramInt4) {
/* 1587 */       return paramArrayOfBoolean;
/*      */     }
/* 1589 */     if (paramInt4 - 1 + paramInt2 >= 32) {
/* 1590 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1593 */     int i = 0;
/* 1594 */     for (int j = 0; j < paramInt4; j++) {
/* 1595 */       i = j * 1 + paramInt2;
/* 1596 */       paramArrayOfBoolean[(paramInt3 + j)] = ((0x1 & paramInt1 >> i) != 0 ? 1 : false);
/*      */     }
/* 1598 */     return paramArrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] shortToBinary(short paramShort, int paramInt1, boolean[] paramArrayOfBoolean, int paramInt2, int paramInt3)
/*      */   {
/* 1620 */     if (0 == paramInt3) {
/* 1621 */       return paramArrayOfBoolean;
/*      */     }
/* 1623 */     if (paramInt3 - 1 + paramInt1 >= 16) {
/* 1624 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1627 */     short s = 0;
/* 1628 */     assert ((paramInt3 - 1) * 1 < 16 - paramInt1);
/* 1629 */     for (int i = 0; i < paramInt3; i++) {
/* 1630 */       s = i * 1 + paramInt1;
/* 1631 */       paramArrayOfBoolean[(paramInt2 + i)] = ((0x1 & paramShort >> s) != 0 ? 1 : false);
/*      */     }
/* 1633 */     return paramArrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean[] byteToBinary(byte paramByte, int paramInt1, boolean[] paramArrayOfBoolean, int paramInt2, int paramInt3)
/*      */   {
/* 1655 */     if (0 == paramInt3) {
/* 1656 */       return paramArrayOfBoolean;
/*      */     }
/* 1658 */     if (paramInt3 - 1 + paramInt1 >= 8) {
/* 1659 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 8");
/*      */     }
/* 1661 */     byte b = 0;
/* 1662 */     for (int i = 0; i < paramInt3; i++) {
/* 1663 */       b = i * 1 + paramInt1;
/* 1664 */       paramArrayOfBoolean[(paramInt2 + i)] = ((0x1 & paramByte >> b) != 0 ? 1 : false);
/*      */     }
/* 1666 */     return paramArrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] uuidToByteArray(UUID paramUUID, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1686 */     if (0 == paramInt2) {
/* 1687 */       return paramArrayOfByte;
/*      */     }
/* 1689 */     if (paramInt2 > 16) {
/* 1690 */       throw new IllegalArgumentException("nBytes is greather than 16");
/*      */     }
/* 1692 */     longToByteArray(paramUUID.getMostSignificantBits(), 0, paramArrayOfByte, paramInt1, paramInt2 > 8 ? 8 : paramInt2);
/* 1693 */     if (paramInt2 >= 8) {
/* 1694 */       longToByteArray(paramUUID.getLeastSignificantBits(), 0, paramArrayOfByte, paramInt1 + 8, paramInt2 - 8);
/*      */     }
/* 1696 */     return paramArrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static UUID byteArrayToUuid(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1713 */     if (paramArrayOfByte.length - paramInt < 16) {
/* 1714 */       throw new IllegalArgumentException("Need at least 16 bytes for UUID");
/*      */     }
/* 1716 */     return new UUID(byteArrayToLong(paramArrayOfByte, paramInt, 0L, 0, 8), byteArrayToLong(paramArrayOfByte, paramInt + 8, 0L, 0, 8));
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\Conversion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */