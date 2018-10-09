/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import org.apache.commons.lang3.math.NumberUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BooleanUtils
/*      */ {
/*      */   public static Boolean negate(Boolean paramBoolean)
/*      */   {
/*   64 */     if (paramBoolean == null) {
/*   65 */       return null;
/*      */     }
/*   67 */     return paramBoolean.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean isTrue(Boolean paramBoolean)
/*      */   {
/*   87 */     return Boolean.TRUE.equals(paramBoolean);
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
/*      */   public static boolean isNotTrue(Boolean paramBoolean)
/*      */   {
/*  105 */     return !isTrue(paramBoolean);
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
/*      */   public static boolean isFalse(Boolean paramBoolean)
/*      */   {
/*  123 */     return Boolean.FALSE.equals(paramBoolean);
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
/*      */   public static boolean isNotFalse(Boolean paramBoolean)
/*      */   {
/*  141 */     return !isFalse(paramBoolean);
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
/*      */   public static boolean toBoolean(Boolean paramBoolean)
/*      */   {
/*  159 */     return (paramBoolean != null) && (paramBoolean.booleanValue());
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
/*      */   public static boolean toBooleanDefaultIfNull(Boolean paramBoolean, boolean paramBoolean1)
/*      */   {
/*  176 */     if (paramBoolean == null) {
/*  177 */       return paramBoolean1;
/*      */     }
/*  179 */     return paramBoolean.booleanValue();
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
/*      */   public static boolean toBoolean(int paramInt)
/*      */   {
/*  199 */     return paramInt != 0;
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
/*      */   public static Boolean toBooleanObject(int paramInt)
/*      */   {
/*  217 */     return paramInt == 0 ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static Boolean toBooleanObject(Integer paramInteger)
/*      */   {
/*  239 */     if (paramInteger == null) {
/*  240 */       return null;
/*      */     }
/*  242 */     return paramInteger.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean toBoolean(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  262 */     if (paramInt1 == paramInt2) {
/*  263 */       return true;
/*      */     }
/*  265 */     if (paramInt1 == paramInt3) {
/*  266 */       return false;
/*      */     }
/*      */     
/*  269 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static boolean toBoolean(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
/*      */   {
/*  290 */     if (paramInteger1 == null) {
/*  291 */       if (paramInteger2 == null) {
/*  292 */         return true;
/*      */       }
/*  294 */       if (paramInteger3 == null)
/*  295 */         return false;
/*      */     } else {
/*  297 */       if (paramInteger1.equals(paramInteger2))
/*  298 */         return true;
/*  299 */       if (paramInteger1.equals(paramInteger3)) {
/*  300 */         return false;
/*      */       }
/*      */     }
/*  303 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static Boolean toBooleanObject(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  325 */     if (paramInt1 == paramInt2) {
/*  326 */       return Boolean.TRUE;
/*      */     }
/*  328 */     if (paramInt1 == paramInt3) {
/*  329 */       return Boolean.FALSE;
/*      */     }
/*  331 */     if (paramInt1 == paramInt4) {
/*  332 */       return null;
/*      */     }
/*      */     
/*  335 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static Boolean toBooleanObject(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4)
/*      */   {
/*  357 */     if (paramInteger1 == null) {
/*  358 */       if (paramInteger2 == null) {
/*  359 */         return Boolean.TRUE;
/*      */       }
/*  361 */       if (paramInteger3 == null) {
/*  362 */         return Boolean.FALSE;
/*      */       }
/*  364 */       if (paramInteger4 == null)
/*  365 */         return null;
/*      */     } else {
/*  367 */       if (paramInteger1.equals(paramInteger2))
/*  368 */         return Boolean.TRUE;
/*  369 */       if (paramInteger1.equals(paramInteger3))
/*  370 */         return Boolean.FALSE;
/*  371 */       if (paramInteger1.equals(paramInteger4)) {
/*  372 */         return null;
/*      */       }
/*      */     }
/*  375 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static int toInteger(boolean paramBoolean)
/*      */   {
/*  393 */     return paramBoolean ? 1 : 0;
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
/*      */   public static Integer toIntegerObject(boolean paramBoolean)
/*      */   {
/*  409 */     return paramBoolean ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static Integer toIntegerObject(Boolean paramBoolean)
/*      */   {
/*  427 */     if (paramBoolean == null) {
/*  428 */       return null;
/*      */     }
/*  430 */     return paramBoolean.booleanValue() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static int toInteger(boolean paramBoolean, int paramInt1, int paramInt2)
/*      */   {
/*  447 */     return paramBoolean ? paramInt1 : paramInt2;
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
/*      */   public static int toInteger(Boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  466 */     if (paramBoolean == null) {
/*  467 */       return paramInt3;
/*      */     }
/*  469 */     return paramBoolean.booleanValue() ? paramInt1 : paramInt2;
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
/*      */   public static Integer toIntegerObject(boolean paramBoolean, Integer paramInteger1, Integer paramInteger2)
/*      */   {
/*  486 */     return paramBoolean ? paramInteger1 : paramInteger2;
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
/*      */   public static Integer toIntegerObject(Boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
/*      */   {
/*  505 */     if (paramBoolean == null) {
/*  506 */       return paramInteger3;
/*      */     }
/*  508 */     return paramBoolean.booleanValue() ? paramInteger1 : paramInteger2;
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
/*      */   public static Boolean toBooleanObject(String paramString)
/*      */   {
/*  554 */     if (paramString == "true") {
/*  555 */       return Boolean.TRUE;
/*      */     }
/*  557 */     if (paramString == null)
/*  558 */       return null;
/*      */     int i;
/*  560 */     int j; int k; int m; switch (paramString.length()) {
/*      */     case 1: 
/*  562 */       i = paramString.charAt(0);
/*  563 */       if ((i == 121) || (i == 89) || (i == 116) || (i == 84))
/*      */       {
/*  565 */         return Boolean.TRUE;
/*      */       }
/*  567 */       if ((i == 110) || (i == 78) || (i == 102) || (i == 70))
/*      */       {
/*  569 */         return Boolean.FALSE;
/*      */       }
/*      */       
/*      */       break;
/*      */     case 2: 
/*  574 */       i = paramString.charAt(0);
/*  575 */       j = paramString.charAt(1);
/*  576 */       if (((i == 111) || (i == 79)) && ((j == 110) || (j == 78)))
/*      */       {
/*  578 */         return Boolean.TRUE;
/*      */       }
/*  580 */       if (((i == 110) || (i == 78)) && ((j == 111) || (j == 79)))
/*      */       {
/*  582 */         return Boolean.FALSE;
/*      */       }
/*      */       
/*      */       break;
/*      */     case 3: 
/*  587 */       i = paramString.charAt(0);
/*  588 */       j = paramString.charAt(1);
/*  589 */       k = paramString.charAt(2);
/*  590 */       if (((i == 121) || (i == 89)) && ((j == 101) || (j == 69)) && ((k == 115) || (k == 83)))
/*      */       {
/*      */ 
/*  593 */         return Boolean.TRUE;
/*      */       }
/*  595 */       if (((i == 111) || (i == 79)) && ((j == 102) || (j == 70)) && ((k == 102) || (k == 70)))
/*      */       {
/*      */ 
/*  598 */         return Boolean.FALSE;
/*      */       }
/*      */       
/*      */       break;
/*      */     case 4: 
/*  603 */       i = paramString.charAt(0);
/*  604 */       j = paramString.charAt(1);
/*  605 */       k = paramString.charAt(2);
/*  606 */       m = paramString.charAt(3);
/*  607 */       if (((i == 116) || (i == 84)) && ((j == 114) || (j == 82)) && ((k == 117) || (k == 85)) && ((m == 101) || (m == 69)))
/*      */       {
/*      */ 
/*      */ 
/*  611 */         return Boolean.TRUE;
/*      */       }
/*      */       
/*      */       break;
/*      */     case 5: 
/*  616 */       i = paramString.charAt(0);
/*  617 */       j = paramString.charAt(1);
/*  618 */       k = paramString.charAt(2);
/*  619 */       m = paramString.charAt(3);
/*  620 */       int n = paramString.charAt(4);
/*  621 */       if (((i == 102) || (i == 70)) && ((j == 97) || (j == 65)) && ((k == 108) || (k == 76)) && ((m == 115) || (m == 83)) && ((n == 101) || (n == 69)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  626 */         return Boolean.FALSE;
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */     
/*  634 */     return null;
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
/*      */   public static Boolean toBooleanObject(String paramString1, String paramString2, String paramString3, String paramString4)
/*      */   {
/*  657 */     if (paramString1 == null) {
/*  658 */       if (paramString2 == null) {
/*  659 */         return Boolean.TRUE;
/*      */       }
/*  661 */       if (paramString3 == null) {
/*  662 */         return Boolean.FALSE;
/*      */       }
/*  664 */       if (paramString4 == null)
/*  665 */         return null;
/*      */     } else {
/*  667 */       if (paramString1.equals(paramString2))
/*  668 */         return Boolean.TRUE;
/*  669 */       if (paramString1.equals(paramString3))
/*  670 */         return Boolean.FALSE;
/*  671 */       if (paramString1.equals(paramString4)) {
/*  672 */         return null;
/*      */       }
/*      */     }
/*  675 */     throw new IllegalArgumentException("The String did not match any specified value");
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
/*      */   public static boolean toBoolean(String paramString)
/*      */   {
/*  710 */     return toBooleanObject(paramString) == Boolean.TRUE;
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
/*      */   public static boolean toBoolean(String paramString1, String paramString2, String paramString3)
/*      */   {
/*  728 */     if (paramString1 == paramString2)
/*  729 */       return true;
/*  730 */     if (paramString1 == paramString3)
/*  731 */       return false;
/*  732 */     if (paramString1 != null) {
/*  733 */       if (paramString1.equals(paramString2))
/*  734 */         return true;
/*  735 */       if (paramString1.equals(paramString3)) {
/*  736 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  740 */     throw new IllegalArgumentException("The String did not match either specified value");
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
/*      */   public static String toStringTrueFalse(Boolean paramBoolean)
/*      */   {
/*  759 */     return toString(paramBoolean, "true", "false", null);
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
/*      */   public static String toStringOnOff(Boolean paramBoolean)
/*      */   {
/*  776 */     return toString(paramBoolean, "on", "off", null);
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
/*      */   public static String toStringYesNo(Boolean paramBoolean)
/*      */   {
/*  793 */     return toString(paramBoolean, "yes", "no", null);
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
/*      */   public static String toString(Boolean paramBoolean, String paramString1, String paramString2, String paramString3)
/*      */   {
/*  812 */     if (paramBoolean == null) {
/*  813 */       return paramString3;
/*      */     }
/*  815 */     return paramBoolean.booleanValue() ? paramString1 : paramString2;
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
/*      */   public static String toStringTrueFalse(boolean paramBoolean)
/*      */   {
/*  833 */     return toString(paramBoolean, "true", "false");
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
/*      */   public static String toStringOnOff(boolean paramBoolean)
/*      */   {
/*  849 */     return toString(paramBoolean, "on", "off");
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
/*      */   public static String toStringYesNo(boolean paramBoolean)
/*      */   {
/*  865 */     return toString(paramBoolean, "yes", "no");
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
/*      */   public static String toString(boolean paramBoolean, String paramString1, String paramString2)
/*      */   {
/*  882 */     return paramBoolean ? paramString1 : paramString2;
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
/*      */   public static boolean and(boolean... paramVarArgs)
/*      */   {
/*  906 */     if (paramVarArgs == null) {
/*  907 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  909 */     if (paramVarArgs.length == 0) {
/*  910 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*  912 */     for (int k : paramVarArgs) {
/*  913 */       if (k == 0) {
/*  914 */         return false;
/*      */       }
/*      */     }
/*  917 */     return true;
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
/*      */   public static Boolean and(Boolean... paramVarArgs)
/*      */   {
/*  940 */     if (paramVarArgs == null) {
/*  941 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  943 */     if (paramVarArgs.length == 0) {
/*  944 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/*  947 */       boolean[] arrayOfBoolean = ArrayUtils.toPrimitive(paramVarArgs);
/*  948 */       return and(arrayOfBoolean) ? Boolean.TRUE : Boolean.FALSE;
/*      */     } catch (NullPointerException localNullPointerException) {
/*  950 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean or(boolean... paramVarArgs)
/*      */   {
/*  974 */     if (paramVarArgs == null) {
/*  975 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  977 */     if (paramVarArgs.length == 0) {
/*  978 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*  980 */     for (int k : paramVarArgs) {
/*  981 */       if (k != 0) {
/*  982 */         return true;
/*      */       }
/*      */     }
/*  985 */     return false;
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
/*      */   public static Boolean or(Boolean... paramVarArgs)
/*      */   {
/* 1009 */     if (paramVarArgs == null) {
/* 1010 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1012 */     if (paramVarArgs.length == 0) {
/* 1013 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1016 */       boolean[] arrayOfBoolean = ArrayUtils.toPrimitive(paramVarArgs);
/* 1017 */       return or(arrayOfBoolean) ? Boolean.TRUE : Boolean.FALSE;
/*      */     } catch (NullPointerException localNullPointerException) {
/* 1019 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean xor(boolean... paramVarArgs)
/*      */   {
/* 1042 */     if (paramVarArgs == null) {
/* 1043 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1045 */     if (paramVarArgs.length == 0) {
/* 1046 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     
/*      */ 
/* 1050 */     int i = 0;
/* 1051 */     for (int m : paramVarArgs) {
/* 1052 */       i ^= m;
/*      */     }
/*      */     
/* 1055 */     return i;
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
/*      */   public static Boolean xor(Boolean... paramVarArgs)
/*      */   {
/* 1074 */     if (paramVarArgs == null) {
/* 1075 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1077 */     if (paramVarArgs.length == 0) {
/* 1078 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1081 */       boolean[] arrayOfBoolean = ArrayUtils.toPrimitive(paramVarArgs);
/* 1082 */       return xor(arrayOfBoolean) ? Boolean.TRUE : Boolean.FALSE;
/*      */     } catch (NullPointerException localNullPointerException) {
/* 1084 */       throw new IllegalArgumentException("The array must not contain any null elements");
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\BooleanUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */