/*      */ package org.apache.commons.lang3.math;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NumberUtils
/*      */ {
/*   34 */   public static final Long LONG_ZERO = Long.valueOf(0L);
/*      */   
/*   36 */   public static final Long LONG_ONE = Long.valueOf(1L);
/*      */   
/*   38 */   public static final Long LONG_MINUS_ONE = Long.valueOf(-1L);
/*      */   
/*   40 */   public static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*      */   
/*   42 */   public static final Integer INTEGER_ONE = Integer.valueOf(1);
/*      */   
/*   44 */   public static final Integer INTEGER_MINUS_ONE = Integer.valueOf(-1);
/*      */   
/*   46 */   public static final Short SHORT_ZERO = Short.valueOf((short)0);
/*      */   
/*   48 */   public static final Short SHORT_ONE = Short.valueOf((short)1);
/*      */   
/*   50 */   public static final Short SHORT_MINUS_ONE = Short.valueOf((short)-1);
/*      */   
/*   52 */   public static final Byte BYTE_ZERO = Byte.valueOf((byte)0);
/*      */   
/*   54 */   public static final Byte BYTE_ONE = Byte.valueOf((byte)1);
/*      */   
/*   56 */   public static final Byte BYTE_MINUS_ONE = Byte.valueOf((byte)-1);
/*      */   
/*   58 */   public static final Double DOUBLE_ZERO = Double.valueOf(0.0D);
/*      */   
/*   60 */   public static final Double DOUBLE_ONE = Double.valueOf(1.0D);
/*      */   
/*   62 */   public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0D);
/*      */   
/*   64 */   public static final Float FLOAT_ZERO = Float.valueOf(0.0F);
/*      */   
/*   66 */   public static final Float FLOAT_ONE = Float.valueOf(1.0F);
/*      */   
/*   68 */   public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0F);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int toInt(String paramString)
/*      */   {
/*  100 */     return toInt(paramString, 0);
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
/*      */   public static int toInt(String paramString, int paramInt)
/*      */   {
/*  121 */     if (paramString == null) {
/*  122 */       return paramInt;
/*      */     }
/*      */     try {
/*  125 */       return Integer.parseInt(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  127 */     return paramInt;
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
/*      */   public static long toLong(String paramString)
/*      */   {
/*  149 */     return toLong(paramString, 0L);
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
/*      */   public static long toLong(String paramString, long paramLong)
/*      */   {
/*  170 */     if (paramString == null) {
/*  171 */       return paramLong;
/*      */     }
/*      */     try {
/*  174 */       return Long.parseLong(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  176 */     return paramLong;
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
/*      */   public static float toFloat(String paramString)
/*      */   {
/*  199 */     return toFloat(paramString, 0.0F);
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
/*      */   public static float toFloat(String paramString, float paramFloat)
/*      */   {
/*  222 */     if (paramString == null) {
/*  223 */       return paramFloat;
/*      */     }
/*      */     try {
/*  226 */       return Float.parseFloat(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  228 */     return paramFloat;
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
/*      */   public static double toDouble(String paramString)
/*      */   {
/*  251 */     return toDouble(paramString, 0.0D);
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
/*      */   public static double toDouble(String paramString, double paramDouble)
/*      */   {
/*  274 */     if (paramString == null) {
/*  275 */       return paramDouble;
/*      */     }
/*      */     try {
/*  278 */       return Double.parseDouble(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  280 */     return paramDouble;
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
/*      */   public static byte toByte(String paramString)
/*      */   {
/*  303 */     return toByte(paramString, (byte)0);
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
/*      */   public static byte toByte(String paramString, byte paramByte)
/*      */   {
/*  324 */     if (paramString == null) {
/*  325 */       return paramByte;
/*      */     }
/*      */     try {
/*  328 */       return Byte.parseByte(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  330 */     return paramByte;
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
/*      */   public static short toShort(String paramString)
/*      */   {
/*  352 */     return toShort(paramString, (short)0);
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
/*      */   public static short toShort(String paramString, short paramShort)
/*      */   {
/*  373 */     if (paramString == null) {
/*  374 */       return paramShort;
/*      */     }
/*      */     try {
/*  377 */       return Short.parseShort(paramString);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  379 */     return paramShort;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Number createNumber(String paramString)
/*      */     throws NumberFormatException
/*      */   {
/*  451 */     if (paramString == null) {
/*  452 */       return null;
/*      */     }
/*  454 */     if (StringUtils.isBlank(paramString)) {
/*  455 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*      */     
/*  458 */     String[] arrayOfString1 = { "0x", "0X", "-0x", "-0X", "#", "-#" };
/*  459 */     int i = 0;
/*  460 */     String str3; for (str3 : arrayOfString1) {
/*  461 */       if (paramString.startsWith(str3)) {
/*  462 */         i += str3.length();
/*  463 */         break;
/*      */       }
/*      */     }
/*  466 */     if (i > 0) {
/*  467 */       c = '\000';
/*  468 */       for (??? = i; ??? < paramString.length(); ???++) {
/*  469 */         c = paramString.charAt(???);
/*  470 */         if (c != '0') break;
/*  471 */         i++;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  476 */       ??? = paramString.length() - i;
/*  477 */       if ((??? > 16) || ((??? == 16) && (c > '7'))) {
/*  478 */         return createBigInteger(paramString);
/*      */       }
/*  480 */       if ((??? > 8) || ((??? == 8) && (c > '7'))) {
/*  481 */         return createLong(paramString);
/*      */       }
/*  483 */       return createInteger(paramString);
/*      */     }
/*  485 */     char c = paramString.charAt(paramString.length() - 1);
/*      */     
/*      */ 
/*      */ 
/*  489 */     int m = paramString.indexOf('.');
/*  490 */     int n = paramString.indexOf('e') + paramString.indexOf('E') + 1;
/*      */     
/*      */ 
/*      */ 
/*  494 */     int i1 = 0;
/*  495 */     String str2; String str1; if (m > -1)
/*      */     {
/*  497 */       if (n > -1) {
/*  498 */         if ((n < m) || (n > paramString.length())) {
/*  499 */           throw new NumberFormatException(paramString + " is not a valid number.");
/*      */         }
/*  501 */         str2 = paramString.substring(m + 1, n);
/*      */       } else {
/*  503 */         str2 = paramString.substring(m + 1);
/*      */       }
/*  505 */       str1 = paramString.substring(0, m);
/*  506 */       i1 = str2.length();
/*      */     } else {
/*  508 */       if (n > -1) {
/*  509 */         if (n > paramString.length()) {
/*  510 */           throw new NumberFormatException(paramString + " is not a valid number.");
/*      */         }
/*  512 */         str1 = paramString.substring(0, n);
/*      */       } else {
/*  514 */         str1 = paramString;
/*      */       }
/*  516 */       str2 = null;
/*      */     }
/*  518 */     if ((!Character.isDigit(c)) && (c != '.')) {
/*  519 */       if ((n > -1) && (n < paramString.length() - 1)) {
/*  520 */         str3 = paramString.substring(n + 1, paramString.length() - 1);
/*      */       } else {
/*  522 */         str3 = null;
/*      */       }
/*      */       
/*  525 */       String str4 = paramString.substring(0, paramString.length() - 1);
/*  526 */       int i3 = (isAllZeros(str1)) && (isAllZeros(str3)) ? 1 : 0;
/*  527 */       switch (c) {
/*      */       case 'L': 
/*      */       case 'l': 
/*  530 */         if ((str2 == null) && (str3 == null) && (((str4.charAt(0) == '-') && (isDigits(str4.substring(1)))) || (isDigits(str4))))
/*      */         {
/*      */           try
/*      */           {
/*  534 */             return createLong(str4);
/*      */           }
/*      */           catch (NumberFormatException localNumberFormatException5)
/*      */           {
/*  538 */             return createBigInteger(str4);
/*      */           }
/*      */         }
/*  541 */         throw new NumberFormatException(paramString + " is not a valid number.");
/*      */       case 'F': 
/*      */       case 'f': 
/*      */         try {
/*  545 */           Float localFloat2 = createFloat(str4);
/*  546 */           if ((!localFloat2.isInfinite()) && ((localFloat2.floatValue() != 0.0F) || (i3 != 0)))
/*      */           {
/*      */ 
/*  549 */             return localFloat2;
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException6) {}
/*      */       
/*      */ 
/*      */       case 'D': 
/*      */       case 'd': 
/*      */         try
/*      */         {
/*  559 */           Double localDouble2 = createDouble(str4);
/*  560 */           if ((!localDouble2.isInfinite()) && ((localDouble2.floatValue() != 0.0D) || (i3 != 0))) {
/*  561 */             return localDouble2;
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException7) {}
/*      */         try
/*      */         {
/*  567 */           return createBigDecimal(str4);
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException8) {}
/*      */       }
/*      */       
/*      */       
/*  573 */       throw new NumberFormatException(paramString + " is not a valid number.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  579 */     if ((n > -1) && (n < paramString.length() - 1)) {
/*  580 */       str3 = paramString.substring(n + 1, paramString.length());
/*      */     } else {
/*  582 */       str3 = null;
/*      */     }
/*  584 */     if ((str2 == null) && (str3 == null)) {
/*      */       try
/*      */       {
/*  587 */         return createInteger(paramString);
/*      */       }
/*      */       catch (NumberFormatException localNumberFormatException1)
/*      */       {
/*      */         try {
/*  592 */           return createLong(paramString);
/*      */         }
/*      */         catch (NumberFormatException localNumberFormatException2)
/*      */         {
/*  596 */           return createBigInteger(paramString);
/*      */         }
/*      */       }
/*      */     }
/*  600 */     int i2 = (isAllZeros(str1)) && (isAllZeros(str3)) ? 1 : 0;
/*      */     try {
/*  602 */       if (i1 <= 7) {
/*  603 */         Float localFloat1 = createFloat(paramString);
/*  604 */         if ((!localFloat1.isInfinite()) && ((localFloat1.floatValue() != 0.0F) || (i2 != 0))) {
/*  605 */           return localFloat1;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException3) {}
/*      */     try
/*      */     {
/*  612 */       if (i1 <= 16) {
/*  613 */         Double localDouble1 = createDouble(paramString);
/*  614 */         if ((!localDouble1.isInfinite()) && ((localDouble1.doubleValue() != 0.0D) || (i2 != 0))) {
/*  615 */           return localDouble1;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException4) {}
/*      */     
/*      */ 
/*  622 */     return createBigDecimal(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isAllZeros(String paramString)
/*      */   {
/*  634 */     if (paramString == null) {
/*  635 */       return true;
/*      */     }
/*  637 */     for (int i = paramString.length() - 1; i >= 0; i--) {
/*  638 */       if (paramString.charAt(i) != '0') {
/*  639 */         return false;
/*      */       }
/*      */     }
/*  642 */     return paramString.length() > 0;
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
/*      */   public static Float createFloat(String paramString)
/*      */   {
/*  656 */     if (paramString == null) {
/*  657 */       return null;
/*      */     }
/*  659 */     return Float.valueOf(paramString);
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
/*      */   public static Double createDouble(String paramString)
/*      */   {
/*  672 */     if (paramString == null) {
/*  673 */       return null;
/*      */     }
/*  675 */     return Double.valueOf(paramString);
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
/*      */   public static Integer createInteger(String paramString)
/*      */   {
/*  690 */     if (paramString == null) {
/*  691 */       return null;
/*      */     }
/*      */     
/*  694 */     return Integer.decode(paramString);
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
/*      */   public static Long createLong(String paramString)
/*      */   {
/*  709 */     if (paramString == null) {
/*  710 */       return null;
/*      */     }
/*  712 */     return Long.decode(paramString);
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
/*      */   public static BigInteger createBigInteger(String paramString)
/*      */   {
/*  726 */     if (paramString == null) {
/*  727 */       return null;
/*      */     }
/*  729 */     int i = 0;
/*  730 */     int j = 10;
/*  731 */     int k = 0;
/*  732 */     if (paramString.startsWith("-")) {
/*  733 */       k = 1;
/*  734 */       i = 1;
/*      */     }
/*  736 */     if ((paramString.startsWith("0x", i)) || (paramString.startsWith("0x", i))) {
/*  737 */       j = 16;
/*  738 */       i += 2;
/*  739 */     } else if (paramString.startsWith("#", i)) {
/*  740 */       j = 16;
/*  741 */       i++;
/*  742 */     } else if ((paramString.startsWith("0", i)) && (paramString.length() > i + 1)) {
/*  743 */       j = 8;
/*  744 */       i++;
/*      */     }
/*      */     
/*  747 */     BigInteger localBigInteger = new BigInteger(paramString.substring(i), j);
/*  748 */     return k != 0 ? localBigInteger.negate() : localBigInteger;
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
/*      */   public static BigDecimal createBigDecimal(String paramString)
/*      */   {
/*  761 */     if (paramString == null) {
/*  762 */       return null;
/*      */     }
/*      */     
/*  765 */     if (StringUtils.isBlank(paramString)) {
/*  766 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*  768 */     if (paramString.trim().startsWith("--"))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  773 */       throw new NumberFormatException(paramString + " is not a valid number.");
/*      */     }
/*  775 */     return new BigDecimal(paramString);
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
/*      */   public static long min(long[] paramArrayOfLong)
/*      */   {
/*  790 */     validateArray(paramArrayOfLong);
/*      */     
/*      */ 
/*  793 */     long l = paramArrayOfLong[0];
/*  794 */     for (int i = 1; i < paramArrayOfLong.length; i++) {
/*  795 */       if (paramArrayOfLong[i] < l) {
/*  796 */         l = paramArrayOfLong[i];
/*      */       }
/*      */     }
/*      */     
/*  800 */     return l;
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
/*      */   public static int min(int[] paramArrayOfInt)
/*      */   {
/*  813 */     validateArray(paramArrayOfInt);
/*      */     
/*      */ 
/*  816 */     int i = paramArrayOfInt[0];
/*  817 */     for (int j = 1; j < paramArrayOfInt.length; j++) {
/*  818 */       if (paramArrayOfInt[j] < i) {
/*  819 */         i = paramArrayOfInt[j];
/*      */       }
/*      */     }
/*      */     
/*  823 */     return i;
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
/*      */   public static short min(short[] paramArrayOfShort)
/*      */   {
/*  836 */     validateArray(paramArrayOfShort);
/*      */     
/*      */ 
/*  839 */     short s = paramArrayOfShort[0];
/*  840 */     for (int i = 1; i < paramArrayOfShort.length; i++) {
/*  841 */       if (paramArrayOfShort[i] < s) {
/*  842 */         s = paramArrayOfShort[i];
/*      */       }
/*      */     }
/*      */     
/*  846 */     return s;
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
/*      */   public static byte min(byte[] paramArrayOfByte)
/*      */   {
/*  859 */     validateArray(paramArrayOfByte);
/*      */     
/*      */ 
/*  862 */     byte b = paramArrayOfByte[0];
/*  863 */     for (int i = 1; i < paramArrayOfByte.length; i++) {
/*  864 */       if (paramArrayOfByte[i] < b) {
/*  865 */         b = paramArrayOfByte[i];
/*      */       }
/*      */     }
/*      */     
/*  869 */     return b;
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
/*      */   public static double min(double[] paramArrayOfDouble)
/*      */   {
/*  883 */     validateArray(paramArrayOfDouble);
/*      */     
/*      */ 
/*  886 */     double d = paramArrayOfDouble[0];
/*  887 */     for (int i = 1; i < paramArrayOfDouble.length; i++) {
/*  888 */       if (Double.isNaN(paramArrayOfDouble[i])) {
/*  889 */         return NaN.0D;
/*      */       }
/*  891 */       if (paramArrayOfDouble[i] < d) {
/*  892 */         d = paramArrayOfDouble[i];
/*      */       }
/*      */     }
/*      */     
/*  896 */     return d;
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
/*      */   public static float min(float[] paramArrayOfFloat)
/*      */   {
/*  910 */     validateArray(paramArrayOfFloat);
/*      */     
/*      */ 
/*  913 */     float f = paramArrayOfFloat[0];
/*  914 */     for (int i = 1; i < paramArrayOfFloat.length; i++) {
/*  915 */       if (Float.isNaN(paramArrayOfFloat[i])) {
/*  916 */         return NaN.0F;
/*      */       }
/*  918 */       if (paramArrayOfFloat[i] < f) {
/*  919 */         f = paramArrayOfFloat[i];
/*      */       }
/*      */     }
/*      */     
/*  923 */     return f;
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
/*      */   public static long max(long[] paramArrayOfLong)
/*      */   {
/*  938 */     validateArray(paramArrayOfLong);
/*      */     
/*      */ 
/*  941 */     long l = paramArrayOfLong[0];
/*  942 */     for (int i = 1; i < paramArrayOfLong.length; i++) {
/*  943 */       if (paramArrayOfLong[i] > l) {
/*  944 */         l = paramArrayOfLong[i];
/*      */       }
/*      */     }
/*      */     
/*  948 */     return l;
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
/*      */   public static int max(int[] paramArrayOfInt)
/*      */   {
/*  961 */     validateArray(paramArrayOfInt);
/*      */     
/*      */ 
/*  964 */     int i = paramArrayOfInt[0];
/*  965 */     for (int j = 1; j < paramArrayOfInt.length; j++) {
/*  966 */       if (paramArrayOfInt[j] > i) {
/*  967 */         i = paramArrayOfInt[j];
/*      */       }
/*      */     }
/*      */     
/*  971 */     return i;
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
/*      */   public static short max(short[] paramArrayOfShort)
/*      */   {
/*  984 */     validateArray(paramArrayOfShort);
/*      */     
/*      */ 
/*  987 */     short s = paramArrayOfShort[0];
/*  988 */     for (int i = 1; i < paramArrayOfShort.length; i++) {
/*  989 */       if (paramArrayOfShort[i] > s) {
/*  990 */         s = paramArrayOfShort[i];
/*      */       }
/*      */     }
/*      */     
/*  994 */     return s;
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
/*      */   public static byte max(byte[] paramArrayOfByte)
/*      */   {
/* 1007 */     validateArray(paramArrayOfByte);
/*      */     
/*      */ 
/* 1010 */     byte b = paramArrayOfByte[0];
/* 1011 */     for (int i = 1; i < paramArrayOfByte.length; i++) {
/* 1012 */       if (paramArrayOfByte[i] > b) {
/* 1013 */         b = paramArrayOfByte[i];
/*      */       }
/*      */     }
/*      */     
/* 1017 */     return b;
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
/*      */   public static double max(double[] paramArrayOfDouble)
/*      */   {
/* 1031 */     validateArray(paramArrayOfDouble);
/*      */     
/*      */ 
/* 1034 */     double d = paramArrayOfDouble[0];
/* 1035 */     for (int i = 1; i < paramArrayOfDouble.length; i++) {
/* 1036 */       if (Double.isNaN(paramArrayOfDouble[i])) {
/* 1037 */         return NaN.0D;
/*      */       }
/* 1039 */       if (paramArrayOfDouble[i] > d) {
/* 1040 */         d = paramArrayOfDouble[i];
/*      */       }
/*      */     }
/*      */     
/* 1044 */     return d;
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
/*      */   public static float max(float[] paramArrayOfFloat)
/*      */   {
/* 1058 */     validateArray(paramArrayOfFloat);
/*      */     
/*      */ 
/* 1061 */     float f = paramArrayOfFloat[0];
/* 1062 */     for (int i = 1; i < paramArrayOfFloat.length; i++) {
/* 1063 */       if (Float.isNaN(paramArrayOfFloat[i])) {
/* 1064 */         return NaN.0F;
/*      */       }
/* 1066 */       if (paramArrayOfFloat[i] > f) {
/* 1067 */         f = paramArrayOfFloat[i];
/*      */       }
/*      */     }
/*      */     
/* 1071 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void validateArray(Object paramObject)
/*      */   {
/* 1081 */     if (paramObject == null)
/* 1082 */       throw new IllegalArgumentException("The Array must not be null");
/* 1083 */     if (Array.getLength(paramObject) == 0) {
/* 1084 */       throw new IllegalArgumentException("Array cannot be empty.");
/*      */     }
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
/*      */   public static long min(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/* 1099 */     if (paramLong2 < paramLong1) {
/* 1100 */       paramLong1 = paramLong2;
/*      */     }
/* 1102 */     if (paramLong3 < paramLong1) {
/* 1103 */       paramLong1 = paramLong3;
/*      */     }
/* 1105 */     return paramLong1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int min(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1117 */     if (paramInt2 < paramInt1) {
/* 1118 */       paramInt1 = paramInt2;
/*      */     }
/* 1120 */     if (paramInt3 < paramInt1) {
/* 1121 */       paramInt1 = paramInt3;
/*      */     }
/* 1123 */     return paramInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short min(short paramShort1, short paramShort2, short paramShort3)
/*      */   {
/* 1135 */     if (paramShort2 < paramShort1) {
/* 1136 */       paramShort1 = paramShort2;
/*      */     }
/* 1138 */     if (paramShort3 < paramShort1) {
/* 1139 */       paramShort1 = paramShort3;
/*      */     }
/* 1141 */     return paramShort1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte min(byte paramByte1, byte paramByte2, byte paramByte3)
/*      */   {
/* 1153 */     if (paramByte2 < paramByte1) {
/* 1154 */       paramByte1 = paramByte2;
/*      */     }
/* 1156 */     if (paramByte3 < paramByte1) {
/* 1157 */       paramByte1 = paramByte3;
/*      */     }
/* 1159 */     return paramByte1;
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
/*      */   public static double min(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/* 1175 */     return Math.min(Math.min(paramDouble1, paramDouble2), paramDouble3);
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
/*      */   public static float min(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1191 */     return Math.min(Math.min(paramFloat1, paramFloat2), paramFloat3);
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
/*      */   public static long max(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/* 1205 */     if (paramLong2 > paramLong1) {
/* 1206 */       paramLong1 = paramLong2;
/*      */     }
/* 1208 */     if (paramLong3 > paramLong1) {
/* 1209 */       paramLong1 = paramLong3;
/*      */     }
/* 1211 */     return paramLong1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int max(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1223 */     if (paramInt2 > paramInt1) {
/* 1224 */       paramInt1 = paramInt2;
/*      */     }
/* 1226 */     if (paramInt3 > paramInt1) {
/* 1227 */       paramInt1 = paramInt3;
/*      */     }
/* 1229 */     return paramInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static short max(short paramShort1, short paramShort2, short paramShort3)
/*      */   {
/* 1241 */     if (paramShort2 > paramShort1) {
/* 1242 */       paramShort1 = paramShort2;
/*      */     }
/* 1244 */     if (paramShort3 > paramShort1) {
/* 1245 */       paramShort1 = paramShort3;
/*      */     }
/* 1247 */     return paramShort1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte max(byte paramByte1, byte paramByte2, byte paramByte3)
/*      */   {
/* 1259 */     if (paramByte2 > paramByte1) {
/* 1260 */       paramByte1 = paramByte2;
/*      */     }
/* 1262 */     if (paramByte3 > paramByte1) {
/* 1263 */       paramByte1 = paramByte3;
/*      */     }
/* 1265 */     return paramByte1;
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
/*      */   public static double max(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/* 1281 */     return Math.max(Math.max(paramDouble1, paramDouble2), paramDouble3);
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
/*      */   public static float max(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1297 */     return Math.max(Math.max(paramFloat1, paramFloat2), paramFloat3);
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
/*      */   public static boolean isDigits(String paramString)
/*      */   {
/* 1312 */     if (StringUtils.isEmpty(paramString)) {
/* 1313 */       return false;
/*      */     }
/* 1315 */     for (int i = 0; i < paramString.length(); i++) {
/* 1316 */       if (!Character.isDigit(paramString.charAt(i))) {
/* 1317 */         return false;
/*      */       }
/*      */     }
/* 1320 */     return true;
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
/*      */   public static boolean isNumber(String paramString)
/*      */   {
/* 1338 */     if (StringUtils.isEmpty(paramString)) {
/* 1339 */       return false;
/*      */     }
/* 1341 */     char[] arrayOfChar = paramString.toCharArray();
/* 1342 */     int i = arrayOfChar.length;
/* 1343 */     int j = 0;
/* 1344 */     int k = 0;
/* 1345 */     int m = 0;
/* 1346 */     boolean bool = false;
/*      */     
/* 1348 */     int n = arrayOfChar[0] == '-' ? 1 : 0;
/* 1349 */     if ((i > n + 1) && (arrayOfChar[n] == '0')) {
/* 1350 */       if ((arrayOfChar[(n + 1)] == 'x') || (arrayOfChar[(n + 1)] == 'X'))
/*      */       {
/*      */ 
/*      */ 
/* 1354 */         i1 = n + 2;
/* 1355 */         if (i1 == i) {
/* 1356 */           return false;
/*      */         }
/* 1359 */         for (; 
/* 1359 */             i1 < arrayOfChar.length; i1++) {
/* 1360 */           if (((arrayOfChar[i1] < '0') || (arrayOfChar[i1] > '9')) && ((arrayOfChar[i1] < 'a') || (arrayOfChar[i1] > 'f')) && ((arrayOfChar[i1] < 'A') || (arrayOfChar[i1] > 'F')))
/*      */           {
/*      */ 
/* 1363 */             return false;
/*      */           }
/*      */         }
/* 1366 */         return true; }
/* 1367 */       if (Character.isDigit(arrayOfChar[(n + 1)]))
/*      */       {
/* 1369 */         for (i1 = n + 1; 
/* 1370 */             i1 < arrayOfChar.length; i1++) {
/* 1371 */           if ((arrayOfChar[i1] < '0') || (arrayOfChar[i1] > '7')) {
/* 1372 */             return false;
/*      */           }
/*      */         }
/* 1375 */         return true;
/*      */       }
/*      */     }
/* 1378 */     i--;
/*      */     
/* 1380 */     int i1 = n;
/*      */     
/*      */ 
/* 1383 */     while ((i1 < i) || ((i1 < i + 1) && (m != 0) && (!bool))) {
/* 1384 */       if ((arrayOfChar[i1] >= '0') && (arrayOfChar[i1] <= '9')) {
/* 1385 */         bool = true;
/* 1386 */         m = 0;
/*      */       }
/* 1388 */       else if (arrayOfChar[i1] == '.') {
/* 1389 */         if ((k != 0) || (j != 0))
/*      */         {
/* 1391 */           return false;
/*      */         }
/* 1393 */         k = 1;
/* 1394 */       } else if ((arrayOfChar[i1] == 'e') || (arrayOfChar[i1] == 'E'))
/*      */       {
/* 1396 */         if (j != 0)
/*      */         {
/* 1398 */           return false;
/*      */         }
/* 1400 */         if (!bool) {
/* 1401 */           return false;
/*      */         }
/* 1403 */         j = 1;
/* 1404 */         m = 1;
/* 1405 */       } else if ((arrayOfChar[i1] == '+') || (arrayOfChar[i1] == '-')) {
/* 1406 */         if (m == 0) {
/* 1407 */           return false;
/*      */         }
/* 1409 */         m = 0;
/* 1410 */         bool = false;
/*      */       } else {
/* 1412 */         return false;
/*      */       }
/* 1414 */       i1++;
/*      */     }
/* 1416 */     if (i1 < arrayOfChar.length) {
/* 1417 */       if ((arrayOfChar[i1] >= '0') && (arrayOfChar[i1] <= '9'))
/*      */       {
/* 1419 */         return true;
/*      */       }
/* 1421 */       if ((arrayOfChar[i1] == 'e') || (arrayOfChar[i1] == 'E'))
/*      */       {
/* 1423 */         return false;
/*      */       }
/* 1425 */       if (arrayOfChar[i1] == '.') {
/* 1426 */         if ((k != 0) || (j != 0))
/*      */         {
/* 1428 */           return false;
/*      */         }
/*      */         
/* 1431 */         return bool;
/*      */       }
/* 1433 */       if ((m == 0) && ((arrayOfChar[i1] == 'd') || (arrayOfChar[i1] == 'D') || (arrayOfChar[i1] == 'f') || (arrayOfChar[i1] == 'F')))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1438 */         return bool;
/*      */       }
/* 1440 */       if ((arrayOfChar[i1] == 'l') || (arrayOfChar[i1] == 'L'))
/*      */       {
/*      */ 
/* 1443 */         return (bool) && (j == 0) && (k == 0);
/*      */       }
/*      */       
/* 1446 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1450 */     return (m == 0) && (bool);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\math\NumberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */