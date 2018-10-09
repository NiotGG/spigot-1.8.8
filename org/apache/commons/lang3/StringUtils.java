/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.text.Normalizer;
/*      */ import java.text.Normalizer.Form;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringUtils
/*      */ {
/*      */   public static final String SPACE = " ";
/*      */   public static final String EMPTY = "";
/*      */   public static final String LF = "\n";
/*      */   public static final String CR = "\r";
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */   private static final int PAD_LIMIT = 8192;
/*  183 */   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(?: |\\u00A0|\\s|[\\s&&[^ ]])\\s*");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(CharSequence paramCharSequence)
/*      */   {
/*  219 */     return (paramCharSequence == null) || (paramCharSequence.length() == 0);
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
/*      */   public static boolean isNotEmpty(CharSequence paramCharSequence)
/*      */   {
/*  238 */     return !isEmpty(paramCharSequence);
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
/*      */   public static boolean isAnyEmpty(CharSequence... paramVarArgs)
/*      */   {
/*  259 */     if (ArrayUtils.isEmpty(paramVarArgs)) {
/*  260 */       return true;
/*      */     }
/*  262 */     for (CharSequence localCharSequence : paramVarArgs) {
/*  263 */       if (isEmpty(localCharSequence)) {
/*  264 */         return true;
/*      */       }
/*      */     }
/*  267 */     return false;
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
/*      */   public static boolean isNoneEmpty(CharSequence... paramVarArgs)
/*      */   {
/*  288 */     return !isAnyEmpty(paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isBlank(CharSequence paramCharSequence)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  308 */     if ((paramCharSequence == null) || ((i = paramCharSequence.length()) == 0)) {
/*  309 */       return true;
/*      */     }
/*  311 */     for (int j = 0; j < i; j++) {
/*  312 */       if (!Character.isWhitespace(paramCharSequence.charAt(j))) {
/*  313 */         return false;
/*      */       }
/*      */     }
/*  316 */     return true;
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
/*      */   public static boolean isNotBlank(CharSequence paramCharSequence)
/*      */   {
/*  337 */     return !isBlank(paramCharSequence);
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
/*      */   public static boolean isAnyBlank(CharSequence... paramVarArgs)
/*      */   {
/*  359 */     if (ArrayUtils.isEmpty(paramVarArgs)) {
/*  360 */       return true;
/*      */     }
/*  362 */     for (CharSequence localCharSequence : paramVarArgs) {
/*  363 */       if (isBlank(localCharSequence)) {
/*  364 */         return true;
/*      */       }
/*      */     }
/*  367 */     return false;
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
/*      */   public static boolean isNoneBlank(CharSequence... paramVarArgs)
/*      */   {
/*  389 */     return !isAnyBlank(paramVarArgs);
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
/*      */   public static String trim(String paramString)
/*      */   {
/*  418 */     return paramString == null ? null : paramString.trim();
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
/*      */   public static String trimToNull(String paramString)
/*      */   {
/*  444 */     String str = trim(paramString);
/*  445 */     return isEmpty(str) ? null : str;
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
/*      */   public static String trimToEmpty(String paramString)
/*      */   {
/*  470 */     return paramString == null ? "" : paramString.trim();
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
/*      */   public static String strip(String paramString)
/*      */   {
/*  498 */     return strip(paramString, null);
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
/*      */   public static String stripToNull(String paramString)
/*      */   {
/*  525 */     if (paramString == null) {
/*  526 */       return null;
/*      */     }
/*  528 */     paramString = strip(paramString, null);
/*  529 */     return paramString.isEmpty() ? null : paramString;
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
/*      */   public static String stripToEmpty(String paramString)
/*      */   {
/*  555 */     return paramString == null ? "" : strip(paramString, null);
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
/*      */   public static String strip(String paramString1, String paramString2)
/*      */   {
/*  585 */     if (isEmpty(paramString1)) {
/*  586 */       return paramString1;
/*      */     }
/*  588 */     paramString1 = stripStart(paramString1, paramString2);
/*  589 */     return stripEnd(paramString1, paramString2);
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
/*      */   public static String stripStart(String paramString1, String paramString2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  618 */     if ((paramString1 == null) || ((i = paramString1.length()) == 0)) {
/*  619 */       return paramString1;
/*      */     }
/*  621 */     int j = 0;
/*  622 */     if (paramString2 == null) {
/*  623 */       while ((j != i) && (Character.isWhitespace(paramString1.charAt(j))))
/*  624 */         j++;
/*      */     }
/*  626 */     if (paramString2.isEmpty()) {
/*  627 */       return paramString1;
/*      */     }
/*  629 */     while ((j != i) && (paramString2.indexOf(paramString1.charAt(j)) != -1)) {
/*  630 */       j++;
/*      */     }
/*      */     
/*  633 */     return paramString1.substring(j);
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
/*      */   public static String stripEnd(String paramString1, String paramString2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  663 */     if ((paramString1 == null) || ((i = paramString1.length()) == 0)) {
/*  664 */       return paramString1;
/*      */     }
/*      */     
/*  667 */     if (paramString2 == null) {
/*  668 */       while ((i != 0) && (Character.isWhitespace(paramString1.charAt(i - 1))))
/*  669 */         i--;
/*      */     }
/*  671 */     if (paramString2.isEmpty()) {
/*  672 */       return paramString1;
/*      */     }
/*  674 */     while ((i != 0) && (paramString2.indexOf(paramString1.charAt(i - 1)) != -1)) {
/*  675 */       i--;
/*      */     }
/*      */     
/*  678 */     return paramString1.substring(0, i);
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
/*      */   public static String[] stripAll(String... paramVarArgs)
/*      */   {
/*  703 */     return stripAll(paramVarArgs, null);
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
/*      */   public static String[] stripAll(String[] paramArrayOfString, String paramString)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  733 */     if ((paramArrayOfString == null) || ((i = paramArrayOfString.length) == 0)) {
/*  734 */       return paramArrayOfString;
/*      */     }
/*  736 */     String[] arrayOfString = new String[i];
/*  737 */     for (int j = 0; j < i; j++) {
/*  738 */       arrayOfString[j] = strip(paramArrayOfString[j], paramString);
/*      */     }
/*  740 */     return arrayOfString;
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
/*      */   public static String stripAccents(String paramString)
/*      */   {
/*  762 */     if (paramString == null) {
/*  763 */       return null;
/*      */     }
/*  765 */     Pattern localPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
/*  766 */     String str = Normalizer.normalize(paramString, Normalizer.Form.NFD);
/*      */     
/*  768 */     return localPattern.matcher(str).replaceAll("");
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
/*      */   public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/*  795 */     if (paramCharSequence1 == paramCharSequence2) {
/*  796 */       return true;
/*      */     }
/*  798 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/*  799 */       return false;
/*      */     }
/*  801 */     if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
/*  802 */       return paramCharSequence1.equals(paramCharSequence2);
/*      */     }
/*  804 */     return CharSequenceUtils.regionMatches(paramCharSequence1, false, 0, paramCharSequence2, 0, Math.max(paramCharSequence1.length(), paramCharSequence2.length()));
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
/*      */   public static boolean equalsIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/*  829 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null))
/*  830 */       return paramCharSequence1 == paramCharSequence2;
/*  831 */     if (paramCharSequence1 == paramCharSequence2)
/*  832 */       return true;
/*  833 */     if (paramCharSequence1.length() != paramCharSequence2.length()) {
/*  834 */       return false;
/*      */     }
/*  836 */     return CharSequenceUtils.regionMatches(paramCharSequence1, true, 0, paramCharSequence2, 0, paramCharSequence1.length());
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
/*      */   public static int indexOf(CharSequence paramCharSequence, int paramInt)
/*      */   {
/*  863 */     if (isEmpty(paramCharSequence)) {
/*  864 */       return -1;
/*      */     }
/*  866 */     return CharSequenceUtils.indexOf(paramCharSequence, paramInt, 0);
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
/*      */   public static int indexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*      */   {
/*  896 */     if (isEmpty(paramCharSequence)) {
/*  897 */       return -1;
/*      */     }
/*  899 */     return CharSequenceUtils.indexOf(paramCharSequence, paramInt1, paramInt2);
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
/*      */   public static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/*  927 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/*  928 */       return -1;
/*      */     }
/*  930 */     return CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, 0);
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
/*      */   public static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/*  967 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/*  968 */       return -1;
/*      */     }
/*  970 */     return CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, paramInt);
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
/*      */   public static int ordinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 1008 */     return ordinalIndexOf(paramCharSequence1, paramCharSequence2, paramInt, false);
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
/*      */   private static int ordinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt, boolean paramBoolean)
/*      */   {
/* 1026 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null) || (paramInt <= 0)) {
/* 1027 */       return -1;
/*      */     }
/* 1029 */     if (paramCharSequence2.length() == 0) {
/* 1030 */       return paramBoolean ? paramCharSequence1.length() : 0;
/*      */     }
/* 1032 */     int i = 0;
/* 1033 */     int j = paramBoolean ? paramCharSequence1.length() : -1;
/*      */     do {
/* 1035 */       if (paramBoolean) {
/* 1036 */         j = CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, j - 1);
/*      */       } else {
/* 1038 */         j = CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, j + 1);
/*      */       }
/* 1040 */       if (j < 0) {
/* 1041 */         return j;
/*      */       }
/* 1043 */       i++;
/* 1044 */     } while (i < paramInt);
/* 1045 */     return j;
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
/*      */   public static int indexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1074 */     return indexOfIgnoreCase(paramCharSequence1, paramCharSequence2, 0);
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
/*      */   public static int indexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 1110 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1111 */       return -1;
/*      */     }
/* 1113 */     if (paramInt < 0) {
/* 1114 */       paramInt = 0;
/*      */     }
/* 1116 */     int i = paramCharSequence1.length() - paramCharSequence2.length() + 1;
/* 1117 */     if (paramInt > i) {
/* 1118 */       return -1;
/*      */     }
/* 1120 */     if (paramCharSequence2.length() == 0) {
/* 1121 */       return paramInt;
/*      */     }
/* 1123 */     for (int j = paramInt; j < i; j++) {
/* 1124 */       if (CharSequenceUtils.regionMatches(paramCharSequence1, true, j, paramCharSequence2, 0, paramCharSequence2.length())) {
/* 1125 */         return j;
/*      */       }
/*      */     }
/* 1128 */     return -1;
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
/*      */   public static int lastIndexOf(CharSequence paramCharSequence, int paramInt)
/*      */   {
/* 1154 */     if (isEmpty(paramCharSequence)) {
/* 1155 */       return -1;
/*      */     }
/* 1157 */     return CharSequenceUtils.lastIndexOf(paramCharSequence, paramInt, paramCharSequence.length());
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
/*      */   public static int lastIndexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*      */   {
/* 1192 */     if (isEmpty(paramCharSequence)) {
/* 1193 */       return -1;
/*      */     }
/* 1195 */     return CharSequenceUtils.lastIndexOf(paramCharSequence, paramInt1, paramInt2);
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
/*      */   public static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1222 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1223 */       return -1;
/*      */     }
/* 1225 */     return CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, paramCharSequence1.length());
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
/*      */   public static int lastOrdinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 1263 */     return ordinalIndexOf(paramCharSequence1, paramCharSequence2, paramInt, true);
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
/*      */   public static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 1303 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1304 */       return -1;
/*      */     }
/* 1306 */     return CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, paramInt);
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1333 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1334 */       return -1;
/*      */     }
/* 1336 */     return lastIndexOfIgnoreCase(paramCharSequence1, paramCharSequence2, paramCharSequence1.length());
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 1372 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1373 */       return -1;
/*      */     }
/* 1375 */     if (paramInt > paramCharSequence1.length() - paramCharSequence2.length()) {
/* 1376 */       paramInt = paramCharSequence1.length() - paramCharSequence2.length();
/*      */     }
/* 1378 */     if (paramInt < 0) {
/* 1379 */       return -1;
/*      */     }
/* 1381 */     if (paramCharSequence2.length() == 0) {
/* 1382 */       return paramInt;
/*      */     }
/*      */     
/* 1385 */     for (int i = paramInt; i >= 0; i--) {
/* 1386 */       if (CharSequenceUtils.regionMatches(paramCharSequence1, true, i, paramCharSequence2, 0, paramCharSequence2.length())) {
/* 1387 */         return i;
/*      */       }
/*      */     }
/* 1390 */     return -1;
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
/*      */   public static boolean contains(CharSequence paramCharSequence, int paramInt)
/*      */   {
/* 1416 */     if (isEmpty(paramCharSequence)) {
/* 1417 */       return false;
/*      */     }
/* 1419 */     return CharSequenceUtils.indexOf(paramCharSequence, paramInt, 0) >= 0;
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
/*      */   public static boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1445 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1446 */       return false;
/*      */     }
/* 1448 */     return CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, 0) >= 0;
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
/*      */   public static boolean containsIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1476 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1477 */       return false;
/*      */     }
/* 1479 */     int i = paramCharSequence2.length();
/* 1480 */     int j = paramCharSequence1.length() - i;
/* 1481 */     for (int k = 0; k <= j; k++) {
/* 1482 */       if (CharSequenceUtils.regionMatches(paramCharSequence1, true, k, paramCharSequence2, 0, i)) {
/* 1483 */         return true;
/*      */       }
/*      */     }
/* 1486 */     return false;
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
/*      */   public static boolean containsWhitespace(CharSequence paramCharSequence)
/*      */   {
/* 1499 */     if (isEmpty(paramCharSequence)) {
/* 1500 */       return false;
/*      */     }
/* 1502 */     int i = paramCharSequence.length();
/* 1503 */     for (int j = 0; j < i; j++) {
/* 1504 */       if (Character.isWhitespace(paramCharSequence.charAt(j))) {
/* 1505 */         return true;
/*      */       }
/*      */     }
/* 1508 */     return false;
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
/*      */   public static int indexOfAny(CharSequence paramCharSequence, char... paramVarArgs)
/*      */   {
/* 1537 */     if ((isEmpty(paramCharSequence)) || (ArrayUtils.isEmpty(paramVarArgs))) {
/* 1538 */       return -1;
/*      */     }
/* 1540 */     int i = paramCharSequence.length();
/* 1541 */     int j = i - 1;
/* 1542 */     int k = paramVarArgs.length;
/* 1543 */     int m = k - 1;
/* 1544 */     for (int n = 0; n < i; n++) {
/* 1545 */       char c = paramCharSequence.charAt(n);
/* 1546 */       for (int i1 = 0; i1 < k; i1++) {
/* 1547 */         if (paramVarArgs[i1] == c) {
/* 1548 */           if ((n < j) && (i1 < m) && (Character.isHighSurrogate(c)))
/*      */           {
/* 1550 */             if (paramVarArgs[(i1 + 1)] == paramCharSequence.charAt(n + 1)) {
/* 1551 */               return n;
/*      */             }
/*      */           } else {
/* 1554 */             return n;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1559 */     return -1;
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
/*      */   public static int indexOfAny(CharSequence paramCharSequence, String paramString)
/*      */   {
/* 1586 */     if ((isEmpty(paramCharSequence)) || (isEmpty(paramString))) {
/* 1587 */       return -1;
/*      */     }
/* 1589 */     return indexOfAny(paramCharSequence, paramString.toCharArray());
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
/*      */   public static boolean containsAny(CharSequence paramCharSequence, char... paramVarArgs)
/*      */   {
/* 1619 */     if ((isEmpty(paramCharSequence)) || (ArrayUtils.isEmpty(paramVarArgs))) {
/* 1620 */       return false;
/*      */     }
/* 1622 */     int i = paramCharSequence.length();
/* 1623 */     int j = paramVarArgs.length;
/* 1624 */     int k = i - 1;
/* 1625 */     int m = j - 1;
/* 1626 */     for (int n = 0; n < i; n++) {
/* 1627 */       char c = paramCharSequence.charAt(n);
/* 1628 */       for (int i1 = 0; i1 < j; i1++) {
/* 1629 */         if (paramVarArgs[i1] == c) {
/* 1630 */           if (Character.isHighSurrogate(c)) {
/* 1631 */             if (i1 == m)
/*      */             {
/* 1633 */               return true;
/*      */             }
/* 1635 */             if ((n < k) && (paramVarArgs[(i1 + 1)] == paramCharSequence.charAt(n + 1))) {
/* 1636 */               return true;
/*      */             }
/*      */           }
/*      */           else {
/* 1640 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1645 */     return false;
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
/*      */   public static boolean containsAny(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1677 */     if (paramCharSequence2 == null) {
/* 1678 */       return false;
/*      */     }
/* 1680 */     return containsAny(paramCharSequence1, CharSequenceUtils.toCharArray(paramCharSequence2));
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
/*      */   public static int indexOfAnyBut(CharSequence paramCharSequence, char... paramVarArgs)
/*      */   {
/* 1710 */     if ((isEmpty(paramCharSequence)) || (ArrayUtils.isEmpty(paramVarArgs))) {
/* 1711 */       return -1;
/*      */     }
/* 1713 */     int i = paramCharSequence.length();
/* 1714 */     int j = i - 1;
/* 1715 */     int k = paramVarArgs.length;
/* 1716 */     int m = k - 1;
/*      */     label127:
/* 1718 */     for (int n = 0; n < i; n++) {
/* 1719 */       char c = paramCharSequence.charAt(n);
/* 1720 */       for (int i1 = 0; i1 < k; i1++) {
/* 1721 */         if ((paramVarArgs[i1] == c) && (
/* 1722 */           (n >= j) || (i1 >= m) || (!Character.isHighSurrogate(c)) || 
/* 1723 */           (paramVarArgs[(i1 + 1)] == paramCharSequence.charAt(n + 1)))) {
/*      */           break label127;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1731 */       return n;
/*      */     }
/* 1733 */     return -1;
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
/*      */   public static int indexOfAnyBut(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1760 */     if ((isEmpty(paramCharSequence1)) || (isEmpty(paramCharSequence2))) {
/* 1761 */       return -1;
/*      */     }
/* 1763 */     int i = paramCharSequence1.length();
/* 1764 */     for (int j = 0; j < i; j++) {
/* 1765 */       char c = paramCharSequence1.charAt(j);
/* 1766 */       int k = CharSequenceUtils.indexOf(paramCharSequence2, c, 0) >= 0 ? 1 : 0;
/* 1767 */       if ((j + 1 < i) && (Character.isHighSurrogate(c))) {
/* 1768 */         int m = paramCharSequence1.charAt(j + 1);
/* 1769 */         if ((k != 0) && (CharSequenceUtils.indexOf(paramCharSequence2, m, 0) < 0)) {
/* 1770 */           return j;
/*      */         }
/*      */       }
/* 1773 */       else if (k == 0) {
/* 1774 */         return j;
/*      */       }
/*      */     }
/*      */     
/* 1778 */     return -1;
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
/*      */   public static boolean containsOnly(CharSequence paramCharSequence, char... paramVarArgs)
/*      */   {
/* 1807 */     if ((paramVarArgs == null) || (paramCharSequence == null)) {
/* 1808 */       return false;
/*      */     }
/* 1810 */     if (paramCharSequence.length() == 0) {
/* 1811 */       return true;
/*      */     }
/* 1813 */     if (paramVarArgs.length == 0) {
/* 1814 */       return false;
/*      */     }
/* 1816 */     return indexOfAnyBut(paramCharSequence, paramVarArgs) == -1;
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
/*      */   public static boolean containsOnly(CharSequence paramCharSequence, String paramString)
/*      */   {
/* 1843 */     if ((paramCharSequence == null) || (paramString == null)) {
/* 1844 */       return false;
/*      */     }
/* 1846 */     return containsOnly(paramCharSequence, paramString.toCharArray());
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
/*      */   public static boolean containsNone(CharSequence paramCharSequence, char... paramVarArgs)
/*      */   {
/* 1875 */     if ((paramCharSequence == null) || (paramVarArgs == null)) {
/* 1876 */       return true;
/*      */     }
/* 1878 */     int i = paramCharSequence.length();
/* 1879 */     int j = i - 1;
/* 1880 */     int k = paramVarArgs.length;
/* 1881 */     int m = k - 1;
/* 1882 */     for (int n = 0; n < i; n++) {
/* 1883 */       char c = paramCharSequence.charAt(n);
/* 1884 */       for (int i1 = 0; i1 < k; i1++) {
/* 1885 */         if (paramVarArgs[i1] == c) {
/* 1886 */           if (Character.isHighSurrogate(c)) {
/* 1887 */             if (i1 == m)
/*      */             {
/* 1889 */               return false;
/*      */             }
/* 1891 */             if ((n < j) && (paramVarArgs[(i1 + 1)] == paramCharSequence.charAt(n + 1))) {
/* 1892 */               return false;
/*      */             }
/*      */           }
/*      */           else {
/* 1896 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1901 */     return true;
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
/*      */   public static boolean containsNone(CharSequence paramCharSequence, String paramString)
/*      */   {
/* 1928 */     if ((paramCharSequence == null) || (paramString == null)) {
/* 1929 */       return true;
/*      */     }
/* 1931 */     return containsNone(paramCharSequence, paramString.toCharArray());
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
/*      */   public static int indexOfAny(CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 1964 */     if ((paramCharSequence == null) || (paramVarArgs == null)) {
/* 1965 */       return -1;
/*      */     }
/* 1967 */     int i = paramVarArgs.length;
/*      */     
/*      */ 
/* 1970 */     int j = Integer.MAX_VALUE;
/*      */     
/* 1972 */     int k = 0;
/* 1973 */     for (int m = 0; m < i; m++) {
/* 1974 */       CharSequence localCharSequence = paramVarArgs[m];
/* 1975 */       if (localCharSequence != null)
/*      */       {
/*      */ 
/* 1978 */         k = CharSequenceUtils.indexOf(paramCharSequence, localCharSequence, 0);
/* 1979 */         if (k != -1)
/*      */         {
/*      */ 
/*      */ 
/* 1983 */           if (k < j)
/* 1984 */             j = k;
/*      */         }
/*      */       }
/*      */     }
/* 1988 */     return j == Integer.MAX_VALUE ? -1 : j;
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
/*      */   public static int lastIndexOfAny(CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 2018 */     if ((paramCharSequence == null) || (paramVarArgs == null)) {
/* 2019 */       return -1;
/*      */     }
/* 2021 */     int i = paramVarArgs.length;
/* 2022 */     int j = -1;
/* 2023 */     int k = 0;
/* 2024 */     for (int m = 0; m < i; m++) {
/* 2025 */       CharSequence localCharSequence = paramVarArgs[m];
/* 2026 */       if (localCharSequence != null)
/*      */       {
/*      */ 
/* 2029 */         k = CharSequenceUtils.lastIndexOf(paramCharSequence, localCharSequence, paramCharSequence.length());
/* 2030 */         if (k > j)
/* 2031 */           j = k;
/*      */       }
/*      */     }
/* 2034 */     return j;
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
/*      */   public static String substring(String paramString, int paramInt)
/*      */   {
/* 2064 */     if (paramString == null) {
/* 2065 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2069 */     if (paramInt < 0) {
/* 2070 */       paramInt = paramString.length() + paramInt;
/*      */     }
/*      */     
/* 2073 */     if (paramInt < 0) {
/* 2074 */       paramInt = 0;
/*      */     }
/* 2076 */     if (paramInt > paramString.length()) {
/* 2077 */       return "";
/*      */     }
/*      */     
/* 2080 */     return paramString.substring(paramInt);
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
/*      */   public static String substring(String paramString, int paramInt1, int paramInt2)
/*      */   {
/* 2119 */     if (paramString == null) {
/* 2120 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2124 */     if (paramInt2 < 0) {
/* 2125 */       paramInt2 = paramString.length() + paramInt2;
/*      */     }
/* 2127 */     if (paramInt1 < 0) {
/* 2128 */       paramInt1 = paramString.length() + paramInt1;
/*      */     }
/*      */     
/*      */ 
/* 2132 */     if (paramInt2 > paramString.length()) {
/* 2133 */       paramInt2 = paramString.length();
/*      */     }
/*      */     
/*      */ 
/* 2137 */     if (paramInt1 > paramInt2) {
/* 2138 */       return "";
/*      */     }
/*      */     
/* 2141 */     if (paramInt1 < 0) {
/* 2142 */       paramInt1 = 0;
/*      */     }
/* 2144 */     if (paramInt2 < 0) {
/* 2145 */       paramInt2 = 0;
/*      */     }
/*      */     
/* 2148 */     return paramString.substring(paramInt1, paramInt2);
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
/*      */   public static String left(String paramString, int paramInt)
/*      */   {
/* 2174 */     if (paramString == null) {
/* 2175 */       return null;
/*      */     }
/* 2177 */     if (paramInt < 0) {
/* 2178 */       return "";
/*      */     }
/* 2180 */     if (paramString.length() <= paramInt) {
/* 2181 */       return paramString;
/*      */     }
/* 2183 */     return paramString.substring(0, paramInt);
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
/*      */   public static String right(String paramString, int paramInt)
/*      */   {
/* 2207 */     if (paramString == null) {
/* 2208 */       return null;
/*      */     }
/* 2210 */     if (paramInt < 0) {
/* 2211 */       return "";
/*      */     }
/* 2213 */     if (paramString.length() <= paramInt) {
/* 2214 */       return paramString;
/*      */     }
/* 2216 */     return paramString.substring(paramString.length() - paramInt);
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
/*      */   public static String mid(String paramString, int paramInt1, int paramInt2)
/*      */   {
/* 2245 */     if (paramString == null) {
/* 2246 */       return null;
/*      */     }
/* 2248 */     if ((paramInt2 < 0) || (paramInt1 > paramString.length())) {
/* 2249 */       return "";
/*      */     }
/* 2251 */     if (paramInt1 < 0) {
/* 2252 */       paramInt1 = 0;
/*      */     }
/* 2254 */     if (paramString.length() <= paramInt1 + paramInt2) {
/* 2255 */       return paramString.substring(paramInt1);
/*      */     }
/* 2257 */     return paramString.substring(paramInt1, paramInt1 + paramInt2);
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
/*      */   public static String substringBefore(String paramString1, String paramString2)
/*      */   {
/* 2290 */     if ((isEmpty(paramString1)) || (paramString2 == null)) {
/* 2291 */       return paramString1;
/*      */     }
/* 2293 */     if (paramString2.isEmpty()) {
/* 2294 */       return "";
/*      */     }
/* 2296 */     int i = paramString1.indexOf(paramString2);
/* 2297 */     if (i == -1) {
/* 2298 */       return paramString1;
/*      */     }
/* 2300 */     return paramString1.substring(0, i);
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
/*      */   public static String substringAfter(String paramString1, String paramString2)
/*      */   {
/* 2332 */     if (isEmpty(paramString1)) {
/* 2333 */       return paramString1;
/*      */     }
/* 2335 */     if (paramString2 == null) {
/* 2336 */       return "";
/*      */     }
/* 2338 */     int i = paramString1.indexOf(paramString2);
/* 2339 */     if (i == -1) {
/* 2340 */       return "";
/*      */     }
/* 2342 */     return paramString1.substring(i + paramString2.length());
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
/*      */   public static String substringBeforeLast(String paramString1, String paramString2)
/*      */   {
/* 2373 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 2374 */       return paramString1;
/*      */     }
/* 2376 */     int i = paramString1.lastIndexOf(paramString2);
/* 2377 */     if (i == -1) {
/* 2378 */       return paramString1;
/*      */     }
/* 2380 */     return paramString1.substring(0, i);
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
/*      */   public static String substringAfterLast(String paramString1, String paramString2)
/*      */   {
/* 2413 */     if (isEmpty(paramString1)) {
/* 2414 */       return paramString1;
/*      */     }
/* 2416 */     if (isEmpty(paramString2)) {
/* 2417 */       return "";
/*      */     }
/* 2419 */     int i = paramString1.lastIndexOf(paramString2);
/* 2420 */     if ((i == -1) || (i == paramString1.length() - paramString2.length())) {
/* 2421 */       return "";
/*      */     }
/* 2423 */     return paramString1.substring(i + paramString2.length());
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
/*      */   public static String substringBetween(String paramString1, String paramString2)
/*      */   {
/* 2450 */     return substringBetween(paramString1, paramString2, paramString2);
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
/*      */   public static String substringBetween(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 2481 */     if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null)) {
/* 2482 */       return null;
/*      */     }
/* 2484 */     int i = paramString1.indexOf(paramString2);
/* 2485 */     if (i != -1) {
/* 2486 */       int j = paramString1.indexOf(paramString3, i + paramString2.length());
/* 2487 */       if (j != -1) {
/* 2488 */         return paramString1.substring(i + paramString2.length(), j);
/*      */       }
/*      */     }
/* 2491 */     return null;
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
/*      */   public static String[] substringsBetween(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 2517 */     if ((paramString1 == null) || (isEmpty(paramString2)) || (isEmpty(paramString3))) {
/* 2518 */       return null;
/*      */     }
/* 2520 */     int i = paramString1.length();
/* 2521 */     if (i == 0) {
/* 2522 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2524 */     int j = paramString3.length();
/* 2525 */     int k = paramString2.length();
/* 2526 */     ArrayList localArrayList = new ArrayList();
/* 2527 */     int m = 0;
/* 2528 */     while (m < i - j) {
/* 2529 */       int n = paramString1.indexOf(paramString2, m);
/* 2530 */       if (n < 0) {
/*      */         break;
/*      */       }
/* 2533 */       n += k;
/* 2534 */       int i1 = paramString1.indexOf(paramString3, n);
/* 2535 */       if (i1 < 0) {
/*      */         break;
/*      */       }
/* 2538 */       localArrayList.add(paramString1.substring(n, i1));
/* 2539 */       m = i1 + j;
/*      */     }
/* 2541 */     if (localArrayList.isEmpty()) {
/* 2542 */       return null;
/*      */     }
/* 2544 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public static String[] split(String paramString)
/*      */   {
/* 2575 */     return split(paramString, null, -1);
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
/*      */   public static String[] split(String paramString, char paramChar)
/*      */   {
/* 2603 */     return splitWorker(paramString, paramChar, false);
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
/*      */   public static String[] split(String paramString1, String paramString2)
/*      */   {
/* 2632 */     return splitWorker(paramString1, paramString2, -1, false);
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
/*      */   public static String[] split(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 2666 */     return splitWorker(paramString1, paramString2, paramInt, false);
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
/*      */   public static String[] splitByWholeSeparator(String paramString1, String paramString2)
/*      */   {
/* 2693 */     return splitByWholeSeparatorWorker(paramString1, paramString2, -1, false);
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
/*      */   public static String[] splitByWholeSeparator(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 2724 */     return splitByWholeSeparatorWorker(paramString1, paramString2, paramInt, false);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String paramString1, String paramString2)
/*      */   {
/* 2753 */     return splitByWholeSeparatorWorker(paramString1, paramString2, -1, true);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 2786 */     return splitByWholeSeparatorWorker(paramString1, paramString2, paramInt, true);
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
/*      */   private static String[] splitByWholeSeparatorWorker(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
/*      */   {
/* 2805 */     if (paramString1 == null) {
/* 2806 */       return null;
/*      */     }
/*      */     
/* 2809 */     int i = paramString1.length();
/*      */     
/* 2811 */     if (i == 0) {
/* 2812 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 2815 */     if ((paramString2 == null) || ("".equals(paramString2)))
/*      */     {
/* 2817 */       return splitWorker(paramString1, null, paramInt, paramBoolean);
/*      */     }
/*      */     
/* 2820 */     int j = paramString2.length();
/*      */     
/* 2822 */     ArrayList localArrayList = new ArrayList();
/* 2823 */     int k = 0;
/* 2824 */     int m = 0;
/* 2825 */     int n = 0;
/* 2826 */     while (n < i) {
/* 2827 */       n = paramString1.indexOf(paramString2, m);
/*      */       
/* 2829 */       if (n > -1) {
/* 2830 */         if (n > m) {
/* 2831 */           k++;
/*      */           
/* 2833 */           if (k == paramInt) {
/* 2834 */             n = i;
/* 2835 */             localArrayList.add(paramString1.substring(m));
/*      */           }
/*      */           else
/*      */           {
/* 2839 */             localArrayList.add(paramString1.substring(m, n));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 2844 */             m = n + j;
/*      */           }
/*      */         }
/*      */         else {
/* 2848 */           if (paramBoolean) {
/* 2849 */             k++;
/* 2850 */             if (k == paramInt) {
/* 2851 */               n = i;
/* 2852 */               localArrayList.add(paramString1.substring(m));
/*      */             } else {
/* 2854 */               localArrayList.add("");
/*      */             }
/*      */           }
/* 2857 */           m = n + j;
/*      */         }
/*      */       }
/*      */       else {
/* 2861 */         localArrayList.add(paramString1.substring(m));
/* 2862 */         n = i;
/*      */       }
/*      */     }
/*      */     
/* 2866 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String paramString)
/*      */   {
/* 2895 */     return splitWorker(paramString, null, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String paramString, char paramChar)
/*      */   {
/* 2931 */     return splitWorker(paramString, paramChar, true);
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
/*      */   private static String[] splitWorker(String paramString, char paramChar, boolean paramBoolean)
/*      */   {
/* 2949 */     if (paramString == null) {
/* 2950 */       return null;
/*      */     }
/* 2952 */     int i = paramString.length();
/* 2953 */     if (i == 0) {
/* 2954 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2956 */     ArrayList localArrayList = new ArrayList();
/* 2957 */     int j = 0;int k = 0;
/* 2958 */     int m = 0;
/* 2959 */     int n = 0;
/* 2960 */     while (j < i)
/* 2961 */       if (paramString.charAt(j) == paramChar) {
/* 2962 */         if ((m != 0) || (paramBoolean)) {
/* 2963 */           localArrayList.add(paramString.substring(k, j));
/* 2964 */           m = 0;
/* 2965 */           n = 1;
/*      */         }
/* 2967 */         j++;k = j;
/*      */       }
/*      */       else {
/* 2970 */         n = 0;
/* 2971 */         m = 1;
/* 2972 */         j++;
/*      */       }
/* 2974 */     if ((m != 0) || ((paramBoolean) && (n != 0))) {
/* 2975 */       localArrayList.add(paramString.substring(k, j));
/*      */     }
/* 2977 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String paramString1, String paramString2)
/*      */   {
/* 3014 */     return splitWorker(paramString1, paramString2, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 3054 */     return splitWorker(paramString1, paramString2, paramInt, true);
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
/*      */   private static String[] splitWorker(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
/*      */   {
/* 3076 */     if (paramString1 == null) {
/* 3077 */       return null;
/*      */     }
/* 3079 */     int i = paramString1.length();
/* 3080 */     if (i == 0) {
/* 3081 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3083 */     ArrayList localArrayList = new ArrayList();
/* 3084 */     int j = 1;
/* 3085 */     int k = 0;int m = 0;
/* 3086 */     int n = 0;
/* 3087 */     int i1 = 0;
/* 3088 */     if (paramString2 == null)
/*      */     {
/* 3090 */       while (k < i)
/* 3091 */         if (Character.isWhitespace(paramString1.charAt(k))) {
/* 3092 */           if ((n != 0) || (paramBoolean)) {
/* 3093 */             i1 = 1;
/* 3094 */             if (j++ == paramInt) {
/* 3095 */               k = i;
/* 3096 */               i1 = 0;
/*      */             }
/* 3098 */             localArrayList.add(paramString1.substring(m, k));
/* 3099 */             n = 0;
/*      */           }
/* 3101 */           k++;m = k;
/*      */         }
/*      */         else {
/* 3104 */           i1 = 0;
/* 3105 */           n = 1;
/* 3106 */           k++;
/*      */         } }
/* 3108 */     if (paramString2.length() == 1)
/*      */     {
/* 3110 */       int i2 = paramString2.charAt(0);
/* 3111 */       while (k < i) {
/* 3112 */         if (paramString1.charAt(k) == i2) {
/* 3113 */           if ((n != 0) || (paramBoolean)) {
/* 3114 */             i1 = 1;
/* 3115 */             if (j++ == paramInt) {
/* 3116 */               k = i;
/* 3117 */               i1 = 0;
/*      */             }
/* 3119 */             localArrayList.add(paramString1.substring(m, k));
/* 3120 */             n = 0;
/*      */           }
/* 3122 */           k++;m = k;
/*      */         }
/*      */         else {
/* 3125 */           i1 = 0;
/* 3126 */           n = 1;
/* 3127 */           k++;
/*      */         }
/*      */       }
/*      */     } else {
/* 3131 */       while (k < i)
/* 3132 */         if (paramString2.indexOf(paramString1.charAt(k)) >= 0) {
/* 3133 */           if ((n != 0) || (paramBoolean)) {
/* 3134 */             i1 = 1;
/* 3135 */             if (j++ == paramInt) {
/* 3136 */               k = i;
/* 3137 */               i1 = 0;
/*      */             }
/* 3139 */             localArrayList.add(paramString1.substring(m, k));
/* 3140 */             n = 0;
/*      */           }
/* 3142 */           k++;m = k;
/*      */         }
/*      */         else {
/* 3145 */           i1 = 0;
/* 3146 */           n = 1;
/* 3147 */           k++;
/*      */         }
/*      */     }
/* 3150 */     if ((n != 0) || ((paramBoolean) && (i1 != 0))) {
/* 3151 */       localArrayList.add(paramString1.substring(m, k));
/*      */     }
/* 3153 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public static String[] splitByCharacterType(String paramString)
/*      */   {
/* 3176 */     return splitByCharacterType(paramString, false);
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
/*      */   public static String[] splitByCharacterTypeCamelCase(String paramString)
/*      */   {
/* 3204 */     return splitByCharacterType(paramString, true);
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
/*      */   private static String[] splitByCharacterType(String paramString, boolean paramBoolean)
/*      */   {
/* 3222 */     if (paramString == null) {
/* 3223 */       return null;
/*      */     }
/* 3225 */     if (paramString.isEmpty()) {
/* 3226 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3228 */     char[] arrayOfChar = paramString.toCharArray();
/* 3229 */     ArrayList localArrayList = new ArrayList();
/* 3230 */     int i = 0;
/* 3231 */     int j = Character.getType(arrayOfChar[i]);
/* 3232 */     for (int k = i + 1; k < arrayOfChar.length; k++) {
/* 3233 */       int m = Character.getType(arrayOfChar[k]);
/* 3234 */       if (m != j)
/*      */       {
/*      */ 
/* 3237 */         if ((paramBoolean) && (m == 2) && (j == 1)) {
/* 3238 */           int n = k - 1;
/* 3239 */           if (n != i) {
/* 3240 */             localArrayList.add(new String(arrayOfChar, i, n - i));
/* 3241 */             i = n;
/*      */           }
/*      */         } else {
/* 3244 */           localArrayList.add(new String(arrayOfChar, i, k - i));
/* 3245 */           i = k;
/*      */         }
/* 3247 */         j = m;
/*      */       } }
/* 3249 */     localArrayList.add(new String(arrayOfChar, i, arrayOfChar.length - i));
/* 3250 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
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
/*      */   public static <T> String join(T... paramVarArgs)
/*      */   {
/* 3278 */     return join(paramVarArgs, null);
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
/*      */   public static String join(Object[] paramArrayOfObject, char paramChar)
/*      */   {
/* 3304 */     if (paramArrayOfObject == null) {
/* 3305 */       return null;
/*      */     }
/* 3307 */     return join(paramArrayOfObject, paramChar, 0, paramArrayOfObject.length);
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
/*      */   public static String join(long[] paramArrayOfLong, char paramChar)
/*      */   {
/* 3336 */     if (paramArrayOfLong == null) {
/* 3337 */       return null;
/*      */     }
/* 3339 */     return join(paramArrayOfLong, paramChar, 0, paramArrayOfLong.length);
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
/*      */   public static String join(int[] paramArrayOfInt, char paramChar)
/*      */   {
/* 3368 */     if (paramArrayOfInt == null) {
/* 3369 */       return null;
/*      */     }
/* 3371 */     return join(paramArrayOfInt, paramChar, 0, paramArrayOfInt.length);
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
/*      */   public static String join(short[] paramArrayOfShort, char paramChar)
/*      */   {
/* 3400 */     if (paramArrayOfShort == null) {
/* 3401 */       return null;
/*      */     }
/* 3403 */     return join(paramArrayOfShort, paramChar, 0, paramArrayOfShort.length);
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
/*      */   public static String join(byte[] paramArrayOfByte, char paramChar)
/*      */   {
/* 3432 */     if (paramArrayOfByte == null) {
/* 3433 */       return null;
/*      */     }
/* 3435 */     return join(paramArrayOfByte, paramChar, 0, paramArrayOfByte.length);
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
/*      */   public static String join(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 3464 */     if (paramArrayOfChar == null) {
/* 3465 */       return null;
/*      */     }
/* 3467 */     return join(paramArrayOfChar, paramChar, 0, paramArrayOfChar.length);
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
/*      */   public static String join(float[] paramArrayOfFloat, char paramChar)
/*      */   {
/* 3496 */     if (paramArrayOfFloat == null) {
/* 3497 */       return null;
/*      */     }
/* 3499 */     return join(paramArrayOfFloat, paramChar, 0, paramArrayOfFloat.length);
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
/*      */   public static String join(double[] paramArrayOfDouble, char paramChar)
/*      */   {
/* 3528 */     if (paramArrayOfDouble == null) {
/* 3529 */       return null;
/*      */     }
/* 3531 */     return join(paramArrayOfDouble, paramChar, 0, paramArrayOfDouble.length);
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
/*      */   public static String join(Object[] paramArrayOfObject, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3562 */     if (paramArrayOfObject == null) {
/* 3563 */       return null;
/*      */     }
/* 3565 */     int i = paramInt2 - paramInt1;
/* 3566 */     if (i <= 0) {
/* 3567 */       return "";
/*      */     }
/* 3569 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3570 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3571 */       if (j > paramInt1) {
/* 3572 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3574 */       if (paramArrayOfObject[j] != null) {
/* 3575 */         localStringBuilder.append(paramArrayOfObject[j]);
/*      */       }
/*      */     }
/* 3578 */     return localStringBuilder.toString();
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
/*      */   public static String join(long[] paramArrayOfLong, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3613 */     if (paramArrayOfLong == null) {
/* 3614 */       return null;
/*      */     }
/* 3616 */     int i = paramInt2 - paramInt1;
/* 3617 */     if (i <= 0) {
/* 3618 */       return "";
/*      */     }
/* 3620 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3621 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3622 */       if (j > paramInt1) {
/* 3623 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3625 */       localStringBuilder.append(paramArrayOfLong[j]);
/*      */     }
/* 3627 */     return localStringBuilder.toString();
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
/*      */   public static String join(int[] paramArrayOfInt, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3662 */     if (paramArrayOfInt == null) {
/* 3663 */       return null;
/*      */     }
/* 3665 */     int i = paramInt2 - paramInt1;
/* 3666 */     if (i <= 0) {
/* 3667 */       return "";
/*      */     }
/* 3669 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3670 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3671 */       if (j > paramInt1) {
/* 3672 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3674 */       localStringBuilder.append(paramArrayOfInt[j]);
/*      */     }
/* 3676 */     return localStringBuilder.toString();
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
/*      */   public static String join(byte[] paramArrayOfByte, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3711 */     if (paramArrayOfByte == null) {
/* 3712 */       return null;
/*      */     }
/* 3714 */     int i = paramInt2 - paramInt1;
/* 3715 */     if (i <= 0) {
/* 3716 */       return "";
/*      */     }
/* 3718 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3719 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3720 */       if (j > paramInt1) {
/* 3721 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3723 */       localStringBuilder.append(paramArrayOfByte[j]);
/*      */     }
/* 3725 */     return localStringBuilder.toString();
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
/*      */   public static String join(short[] paramArrayOfShort, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3760 */     if (paramArrayOfShort == null) {
/* 3761 */       return null;
/*      */     }
/* 3763 */     int i = paramInt2 - paramInt1;
/* 3764 */     if (i <= 0) {
/* 3765 */       return "";
/*      */     }
/* 3767 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3768 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3769 */       if (j > paramInt1) {
/* 3770 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3772 */       localStringBuilder.append(paramArrayOfShort[j]);
/*      */     }
/* 3774 */     return localStringBuilder.toString();
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
/*      */   public static String join(char[] paramArrayOfChar, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3809 */     if (paramArrayOfChar == null) {
/* 3810 */       return null;
/*      */     }
/* 3812 */     int i = paramInt2 - paramInt1;
/* 3813 */     if (i <= 0) {
/* 3814 */       return "";
/*      */     }
/* 3816 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3817 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3818 */       if (j > paramInt1) {
/* 3819 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3821 */       localStringBuilder.append(paramArrayOfChar[j]);
/*      */     }
/* 3823 */     return localStringBuilder.toString();
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
/*      */   public static String join(double[] paramArrayOfDouble, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3858 */     if (paramArrayOfDouble == null) {
/* 3859 */       return null;
/*      */     }
/* 3861 */     int i = paramInt2 - paramInt1;
/* 3862 */     if (i <= 0) {
/* 3863 */       return "";
/*      */     }
/* 3865 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3866 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3867 */       if (j > paramInt1) {
/* 3868 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3870 */       localStringBuilder.append(paramArrayOfDouble[j]);
/*      */     }
/* 3872 */     return localStringBuilder.toString();
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
/*      */   public static String join(float[] paramArrayOfFloat, char paramChar, int paramInt1, int paramInt2)
/*      */   {
/* 3907 */     if (paramArrayOfFloat == null) {
/* 3908 */       return null;
/*      */     }
/* 3910 */     int i = paramInt2 - paramInt1;
/* 3911 */     if (i <= 0) {
/* 3912 */       return "";
/*      */     }
/* 3914 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/* 3915 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 3916 */       if (j > paramInt1) {
/* 3917 */         localStringBuilder.append(paramChar);
/*      */       }
/* 3919 */       localStringBuilder.append(paramArrayOfFloat[j]);
/*      */     }
/* 3921 */     return localStringBuilder.toString();
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
/*      */   public static String join(Object[] paramArrayOfObject, String paramString)
/*      */   {
/* 3949 */     if (paramArrayOfObject == null) {
/* 3950 */       return null;
/*      */     }
/* 3952 */     return join(paramArrayOfObject, paramString, 0, paramArrayOfObject.length);
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
/*      */   public static String join(Object[] paramArrayOfObject, String paramString, int paramInt1, int paramInt2)
/*      */   {
/* 3991 */     if (paramArrayOfObject == null) {
/* 3992 */       return null;
/*      */     }
/* 3994 */     if (paramString == null) {
/* 3995 */       paramString = "";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4000 */     int i = paramInt2 - paramInt1;
/* 4001 */     if (i <= 0) {
/* 4002 */       return "";
/*      */     }
/*      */     
/* 4005 */     StringBuilder localStringBuilder = new StringBuilder(i * 16);
/*      */     
/* 4007 */     for (int j = paramInt1; j < paramInt2; j++) {
/* 4008 */       if (j > paramInt1) {
/* 4009 */         localStringBuilder.append(paramString);
/*      */       }
/* 4011 */       if (paramArrayOfObject[j] != null) {
/* 4012 */         localStringBuilder.append(paramArrayOfObject[j]);
/*      */       }
/*      */     }
/* 4015 */     return localStringBuilder.toString();
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
/*      */   public static String join(Iterator<?> paramIterator, char paramChar)
/*      */   {
/* 4035 */     if (paramIterator == null) {
/* 4036 */       return null;
/*      */     }
/* 4038 */     if (!paramIterator.hasNext()) {
/* 4039 */       return "";
/*      */     }
/* 4041 */     Object localObject1 = paramIterator.next();
/* 4042 */     if (!paramIterator.hasNext())
/*      */     {
/* 4044 */       localObject2 = ObjectUtils.toString(localObject1);
/* 4045 */       return (String)localObject2;
/*      */     }
/*      */     
/*      */ 
/* 4049 */     Object localObject2 = new StringBuilder(256);
/* 4050 */     if (localObject1 != null) {
/* 4051 */       ((StringBuilder)localObject2).append(localObject1);
/*      */     }
/*      */     
/* 4054 */     while (paramIterator.hasNext()) {
/* 4055 */       ((StringBuilder)localObject2).append(paramChar);
/* 4056 */       Object localObject3 = paramIterator.next();
/* 4057 */       if (localObject3 != null) {
/* 4058 */         ((StringBuilder)localObject2).append(localObject3);
/*      */       }
/*      */     }
/*      */     
/* 4062 */     return ((StringBuilder)localObject2).toString();
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
/*      */   public static String join(Iterator<?> paramIterator, String paramString)
/*      */   {
/* 4081 */     if (paramIterator == null) {
/* 4082 */       return null;
/*      */     }
/* 4084 */     if (!paramIterator.hasNext()) {
/* 4085 */       return "";
/*      */     }
/* 4087 */     Object localObject1 = paramIterator.next();
/* 4088 */     if (!paramIterator.hasNext())
/*      */     {
/* 4090 */       localObject2 = ObjectUtils.toString(localObject1);
/* 4091 */       return (String)localObject2;
/*      */     }
/*      */     
/*      */ 
/* 4095 */     Object localObject2 = new StringBuilder(256);
/* 4096 */     if (localObject1 != null) {
/* 4097 */       ((StringBuilder)localObject2).append(localObject1);
/*      */     }
/*      */     
/* 4100 */     while (paramIterator.hasNext()) {
/* 4101 */       if (paramString != null) {
/* 4102 */         ((StringBuilder)localObject2).append(paramString);
/*      */       }
/* 4104 */       Object localObject3 = paramIterator.next();
/* 4105 */       if (localObject3 != null) {
/* 4106 */         ((StringBuilder)localObject2).append(localObject3);
/*      */       }
/*      */     }
/* 4109 */     return ((StringBuilder)localObject2).toString();
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
/*      */   public static String join(Iterable<?> paramIterable, char paramChar)
/*      */   {
/* 4127 */     if (paramIterable == null) {
/* 4128 */       return null;
/*      */     }
/* 4130 */     return join(paramIterable.iterator(), paramChar);
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
/*      */   public static String join(Iterable<?> paramIterable, String paramString)
/*      */   {
/* 4148 */     if (paramIterable == null) {
/* 4149 */       return null;
/*      */     }
/* 4151 */     return join(paramIterable.iterator(), paramString);
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
/*      */   public static String deleteWhitespace(String paramString)
/*      */   {
/* 4171 */     if (isEmpty(paramString)) {
/* 4172 */       return paramString;
/*      */     }
/* 4174 */     int i = paramString.length();
/* 4175 */     char[] arrayOfChar = new char[i];
/* 4176 */     int j = 0;
/* 4177 */     for (int k = 0; k < i; k++) {
/* 4178 */       if (!Character.isWhitespace(paramString.charAt(k))) {
/* 4179 */         arrayOfChar[(j++)] = paramString.charAt(k);
/*      */       }
/*      */     }
/* 4182 */     if (j == i) {
/* 4183 */       return paramString;
/*      */     }
/* 4185 */     return new String(arrayOfChar, 0, j);
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
/*      */   public static String removeStart(String paramString1, String paramString2)
/*      */   {
/* 4215 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4216 */       return paramString1;
/*      */     }
/* 4218 */     if (paramString1.startsWith(paramString2)) {
/* 4219 */       return paramString1.substring(paramString2.length());
/*      */     }
/* 4221 */     return paramString1;
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
/*      */   public static String removeStartIgnoreCase(String paramString1, String paramString2)
/*      */   {
/* 4250 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4251 */       return paramString1;
/*      */     }
/* 4253 */     if (startsWithIgnoreCase(paramString1, paramString2)) {
/* 4254 */       return paramString1.substring(paramString2.length());
/*      */     }
/* 4256 */     return paramString1;
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
/*      */   public static String removeEnd(String paramString1, String paramString2)
/*      */   {
/* 4284 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4285 */       return paramString1;
/*      */     }
/* 4287 */     if (paramString1.endsWith(paramString2)) {
/* 4288 */       return paramString1.substring(0, paramString1.length() - paramString2.length());
/*      */     }
/* 4290 */     return paramString1;
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
/*      */   public static String removeEndIgnoreCase(String paramString1, String paramString2)
/*      */   {
/* 4320 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4321 */       return paramString1;
/*      */     }
/* 4323 */     if (endsWithIgnoreCase(paramString1, paramString2)) {
/* 4324 */       return paramString1.substring(0, paramString1.length() - paramString2.length());
/*      */     }
/* 4326 */     return paramString1;
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
/*      */   public static String remove(String paramString1, String paramString2)
/*      */   {
/* 4353 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4354 */       return paramString1;
/*      */     }
/* 4356 */     return replace(paramString1, paramString2, "", -1);
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
/*      */   public static String remove(String paramString, char paramChar)
/*      */   {
/* 4379 */     if ((isEmpty(paramString)) || (paramString.indexOf(paramChar) == -1)) {
/* 4380 */       return paramString;
/*      */     }
/* 4382 */     char[] arrayOfChar = paramString.toCharArray();
/* 4383 */     int i = 0;
/* 4384 */     for (int j = 0; j < arrayOfChar.length; j++) {
/* 4385 */       if (arrayOfChar[j] != paramChar) {
/* 4386 */         arrayOfChar[(i++)] = arrayOfChar[j];
/*      */       }
/*      */     }
/* 4389 */     return new String(arrayOfChar, 0, i);
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
/*      */   public static String replaceOnce(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 4418 */     return replace(paramString1, paramString2, paramString3, 1);
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
/*      */   public static String replacePattern(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 4442 */     return Pattern.compile(paramString2, 32).matcher(paramString1).replaceAll(paramString3);
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
/*      */   public static String removePattern(String paramString1, String paramString2)
/*      */   {
/* 4458 */     return replacePattern(paramString1, paramString2, "");
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
/*      */   public static String replace(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 4485 */     return replace(paramString1, paramString2, paramString3, -1);
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
/*      */   public static String replace(String paramString1, String paramString2, String paramString3, int paramInt)
/*      */   {
/* 4517 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2)) || (paramString3 == null) || (paramInt == 0)) {
/* 4518 */       return paramString1;
/*      */     }
/* 4520 */     int i = 0;
/* 4521 */     int j = paramString1.indexOf(paramString2, i);
/* 4522 */     if (j == -1) {
/* 4523 */       return paramString1;
/*      */     }
/* 4525 */     int k = paramString2.length();
/* 4526 */     int m = paramString3.length() - k;
/* 4527 */     m = m < 0 ? 0 : m;
/* 4528 */     m *= (paramInt > 64 ? 64 : paramInt < 0 ? 16 : paramInt);
/* 4529 */     StringBuilder localStringBuilder = new StringBuilder(paramString1.length() + m);
/* 4530 */     while (j != -1) {
/* 4531 */       localStringBuilder.append(paramString1.substring(i, j)).append(paramString3);
/* 4532 */       i = j + k;
/* 4533 */       paramInt--; if (paramInt == 0) {
/*      */         break;
/*      */       }
/* 4536 */       j = paramString1.indexOf(paramString2, i);
/*      */     }
/* 4538 */     localStringBuilder.append(paramString1.substring(i));
/* 4539 */     return localStringBuilder.toString();
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
/*      */   public static String replaceEach(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*      */   {
/* 4582 */     return replaceEach(paramString, paramArrayOfString1, paramArrayOfString2, false, 0);
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
/*      */   public static String replaceEachRepeatedly(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*      */   {
/* 4632 */     int i = paramArrayOfString1 == null ? 0 : paramArrayOfString1.length;
/* 4633 */     return replaceEach(paramString, paramArrayOfString1, paramArrayOfString2, true, i);
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
/*      */   private static String replaceEach(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, int paramInt)
/*      */   {
/* 4690 */     if ((paramString == null) || (paramString.isEmpty()) || (paramArrayOfString1 == null) || (paramArrayOfString1.length == 0) || (paramArrayOfString2 == null) || (paramArrayOfString2.length == 0))
/*      */     {
/* 4692 */       return paramString;
/*      */     }
/*      */     
/*      */ 
/* 4696 */     if (paramInt < 0) {
/* 4697 */       throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
/*      */     }
/*      */     
/*      */ 
/* 4701 */     int i = paramArrayOfString1.length;
/* 4702 */     int j = paramArrayOfString2.length;
/*      */     
/*      */ 
/* 4705 */     if (i != j) {
/* 4706 */       throw new IllegalArgumentException("Search and Replace array lengths don't match: " + i + " vs " + j);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4713 */     boolean[] arrayOfBoolean = new boolean[i];
/*      */     
/*      */ 
/* 4716 */     int k = -1;
/* 4717 */     int m = -1;
/* 4718 */     int n = -1;
/*      */     
/*      */ 
/*      */ 
/* 4722 */     for (int i1 = 0; i1 < i; i1++) {
/* 4723 */       if ((arrayOfBoolean[i1] == 0) && (paramArrayOfString1[i1] != null) && (!paramArrayOfString1[i1].isEmpty()) && (paramArrayOfString2[i1] != null))
/*      */       {
/*      */ 
/*      */ 
/* 4727 */         n = paramString.indexOf(paramArrayOfString1[i1]);
/*      */         
/*      */ 
/* 4730 */         if (n == -1) {
/* 4731 */           arrayOfBoolean[i1] = true;
/*      */         }
/* 4733 */         else if ((k == -1) || (n < k)) {
/* 4734 */           k = n;
/* 4735 */           m = i1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4742 */     if (k == -1) {
/* 4743 */       return paramString;
/*      */     }
/*      */     
/* 4746 */     i1 = 0;
/*      */     
/*      */ 
/* 4749 */     int i2 = 0;
/*      */     
/*      */ 
/* 4752 */     for (int i3 = 0; i3 < paramArrayOfString1.length; i3++) {
/* 4753 */       if ((paramArrayOfString1[i3] != null) && (paramArrayOfString2[i3] != null))
/*      */       {
/*      */ 
/* 4756 */         i4 = paramArrayOfString2[i3].length() - paramArrayOfString1[i3].length();
/* 4757 */         if (i4 > 0) {
/* 4758 */           i2 += 3 * i4;
/*      */         }
/*      */       }
/*      */     }
/* 4762 */     i2 = Math.min(i2, paramString.length() / 5);
/*      */     
/* 4764 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length() + i2);
/*      */     
/* 4766 */     while (k != -1)
/*      */     {
/* 4768 */       for (i4 = i1; i4 < k; i4++) {
/* 4769 */         localStringBuilder.append(paramString.charAt(i4));
/*      */       }
/* 4771 */       localStringBuilder.append(paramArrayOfString2[m]);
/*      */       
/* 4773 */       i1 = k + paramArrayOfString1[m].length();
/*      */       
/* 4775 */       k = -1;
/* 4776 */       m = -1;
/* 4777 */       n = -1;
/*      */       
/*      */ 
/* 4780 */       for (i4 = 0; i4 < i; i4++) {
/* 4781 */         if ((arrayOfBoolean[i4] == 0) && (paramArrayOfString1[i4] != null) && (!paramArrayOfString1[i4].isEmpty()) && (paramArrayOfString2[i4] != null))
/*      */         {
/*      */ 
/*      */ 
/* 4785 */           n = paramString.indexOf(paramArrayOfString1[i4], i1);
/*      */           
/*      */ 
/* 4788 */           if (n == -1) {
/* 4789 */             arrayOfBoolean[i4] = true;
/*      */           }
/* 4791 */           else if ((k == -1) || (n < k)) {
/* 4792 */             k = n;
/* 4793 */             m = i4;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 4800 */     int i4 = paramString.length();
/* 4801 */     for (int i5 = i1; i5 < i4; i5++) {
/* 4802 */       localStringBuilder.append(paramString.charAt(i5));
/*      */     }
/* 4804 */     String str = localStringBuilder.toString();
/* 4805 */     if (!paramBoolean) {
/* 4806 */       return str;
/*      */     }
/*      */     
/* 4809 */     return replaceEach(str, paramArrayOfString1, paramArrayOfString2, paramBoolean, paramInt - 1);
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
/*      */   public static String replaceChars(String paramString, char paramChar1, char paramChar2)
/*      */   {
/* 4835 */     if (paramString == null) {
/* 4836 */       return null;
/*      */     }
/* 4838 */     return paramString.replace(paramChar1, paramChar2);
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
/*      */   public static String replaceChars(String paramString1, String paramString2, String paramString3)
/*      */   {
/* 4878 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 4879 */       return paramString1;
/*      */     }
/* 4881 */     if (paramString3 == null) {
/* 4882 */       paramString3 = "";
/*      */     }
/* 4884 */     int i = 0;
/* 4885 */     int j = paramString3.length();
/* 4886 */     int k = paramString1.length();
/* 4887 */     StringBuilder localStringBuilder = new StringBuilder(k);
/* 4888 */     for (int m = 0; m < k; m++) {
/* 4889 */       char c = paramString1.charAt(m);
/* 4890 */       int n = paramString2.indexOf(c);
/* 4891 */       if (n >= 0) {
/* 4892 */         i = 1;
/* 4893 */         if (n < j) {
/* 4894 */           localStringBuilder.append(paramString3.charAt(n));
/*      */         }
/*      */       } else {
/* 4897 */         localStringBuilder.append(c);
/*      */       }
/*      */     }
/* 4900 */     if (i != 0) {
/* 4901 */       return localStringBuilder.toString();
/*      */     }
/* 4903 */     return paramString1;
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
/*      */   public static String overlay(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*      */   {
/* 4938 */     if (paramString1 == null) {
/* 4939 */       return null;
/*      */     }
/* 4941 */     if (paramString2 == null) {
/* 4942 */       paramString2 = "";
/*      */     }
/* 4944 */     int i = paramString1.length();
/* 4945 */     if (paramInt1 < 0) {
/* 4946 */       paramInt1 = 0;
/*      */     }
/* 4948 */     if (paramInt1 > i) {
/* 4949 */       paramInt1 = i;
/*      */     }
/* 4951 */     if (paramInt2 < 0) {
/* 4952 */       paramInt2 = 0;
/*      */     }
/* 4954 */     if (paramInt2 > i) {
/* 4955 */       paramInt2 = i;
/*      */     }
/* 4957 */     if (paramInt1 > paramInt2) {
/* 4958 */       int j = paramInt1;
/* 4959 */       paramInt1 = paramInt2;
/* 4960 */       paramInt2 = j;
/*      */     }
/* 4962 */     return i + paramInt1 - paramInt2 + paramString2.length() + 1 + paramString1.substring(0, paramInt1) + paramString2 + paramString1.substring(paramInt2);
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
/*      */   public static String chomp(String paramString)
/*      */   {
/* 4997 */     if (isEmpty(paramString)) {
/* 4998 */       return paramString;
/*      */     }
/*      */     
/* 5001 */     if (paramString.length() == 1) {
/* 5002 */       i = paramString.charAt(0);
/* 5003 */       if ((i == 13) || (i == 10)) {
/* 5004 */         return "";
/*      */       }
/* 5006 */       return paramString;
/*      */     }
/*      */     
/* 5009 */     int i = paramString.length() - 1;
/* 5010 */     int j = paramString.charAt(i);
/*      */     
/* 5012 */     if (j == 10) {
/* 5013 */       if (paramString.charAt(i - 1) == '\r') {
/* 5014 */         i--;
/*      */       }
/* 5016 */     } else if (j != 13) {
/* 5017 */       i++;
/*      */     }
/* 5019 */     return paramString.substring(0, i);
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
/*      */   @Deprecated
/*      */   public static String chomp(String paramString1, String paramString2)
/*      */   {
/* 5051 */     return removeEnd(paramString1, paramString2);
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
/*      */   public static String chop(String paramString)
/*      */   {
/* 5080 */     if (paramString == null) {
/* 5081 */       return null;
/*      */     }
/* 5083 */     int i = paramString.length();
/* 5084 */     if (i < 2) {
/* 5085 */       return "";
/*      */     }
/* 5087 */     int j = i - 1;
/* 5088 */     String str = paramString.substring(0, j);
/* 5089 */     int k = paramString.charAt(j);
/* 5090 */     if ((k == 10) && (str.charAt(j - 1) == '\r')) {
/* 5091 */       return str.substring(0, j - 1);
/*      */     }
/* 5093 */     return str;
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
/*      */   public static String repeat(String paramString, int paramInt)
/*      */   {
/* 5122 */     if (paramString == null) {
/* 5123 */       return null;
/*      */     }
/* 5125 */     if (paramInt <= 0) {
/* 5126 */       return "";
/*      */     }
/* 5128 */     int i = paramString.length();
/* 5129 */     if ((paramInt == 1) || (i == 0)) {
/* 5130 */       return paramString;
/*      */     }
/* 5132 */     if ((i == 1) && (paramInt <= 8192)) {
/* 5133 */       return repeat(paramString.charAt(0), paramInt);
/*      */     }
/*      */     
/* 5136 */     int j = i * paramInt;
/* 5137 */     switch (i) {
/*      */     case 1: 
/* 5139 */       return repeat(paramString.charAt(0), paramInt);
/*      */     case 2: 
/* 5141 */       int k = paramString.charAt(0);
/* 5142 */       int m = paramString.charAt(1);
/* 5143 */       char[] arrayOfChar = new char[j];
/* 5144 */       for (int n = paramInt * 2 - 2; n >= 0; n--) {
/* 5145 */         arrayOfChar[n] = k;
/* 5146 */         arrayOfChar[(n + 1)] = m;n--;
/*      */       }
/*      */       
/* 5148 */       return new String(arrayOfChar);
/*      */     }
/* 5150 */     StringBuilder localStringBuilder = new StringBuilder(j);
/* 5151 */     for (int i1 = 0; i1 < paramInt; i1++) {
/* 5152 */       localStringBuilder.append(paramString);
/*      */     }
/* 5154 */     return localStringBuilder.toString();
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
/*      */   public static String repeat(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 5179 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 5180 */       return repeat(paramString1, paramInt);
/*      */     }
/*      */     
/* 5183 */     String str = repeat(paramString1 + paramString2, paramInt);
/* 5184 */     return removeEnd(str, paramString2);
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
/*      */   public static String repeat(char paramChar, int paramInt)
/*      */   {
/* 5210 */     char[] arrayOfChar = new char[paramInt];
/* 5211 */     for (int i = paramInt - 1; i >= 0; i--) {
/* 5212 */       arrayOfChar[i] = paramChar;
/*      */     }
/* 5214 */     return new String(arrayOfChar);
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
/*      */   public static String rightPad(String paramString, int paramInt)
/*      */   {
/* 5237 */     return rightPad(paramString, paramInt, ' ');
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
/*      */   public static String rightPad(String paramString, int paramInt, char paramChar)
/*      */   {
/* 5262 */     if (paramString == null) {
/* 5263 */       return null;
/*      */     }
/* 5265 */     int i = paramInt - paramString.length();
/* 5266 */     if (i <= 0) {
/* 5267 */       return paramString;
/*      */     }
/* 5269 */     if (i > 8192) {
/* 5270 */       return rightPad(paramString, paramInt, String.valueOf(paramChar));
/*      */     }
/* 5272 */     return paramString.concat(repeat(paramChar, i));
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
/*      */   public static String rightPad(String paramString1, int paramInt, String paramString2)
/*      */   {
/* 5299 */     if (paramString1 == null) {
/* 5300 */       return null;
/*      */     }
/* 5302 */     if (isEmpty(paramString2)) {
/* 5303 */       paramString2 = " ";
/*      */     }
/* 5305 */     int i = paramString2.length();
/* 5306 */     int j = paramString1.length();
/* 5307 */     int k = paramInt - j;
/* 5308 */     if (k <= 0) {
/* 5309 */       return paramString1;
/*      */     }
/* 5311 */     if ((i == 1) && (k <= 8192)) {
/* 5312 */       return rightPad(paramString1, paramInt, paramString2.charAt(0));
/*      */     }
/*      */     
/* 5315 */     if (k == i)
/* 5316 */       return paramString1.concat(paramString2);
/* 5317 */     if (k < i) {
/* 5318 */       return paramString1.concat(paramString2.substring(0, k));
/*      */     }
/* 5320 */     char[] arrayOfChar1 = new char[k];
/* 5321 */     char[] arrayOfChar2 = paramString2.toCharArray();
/* 5322 */     for (int m = 0; m < k; m++) {
/* 5323 */       arrayOfChar1[m] = arrayOfChar2[(m % i)];
/*      */     }
/* 5325 */     return paramString1.concat(new String(arrayOfChar1));
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
/*      */   public static String leftPad(String paramString, int paramInt)
/*      */   {
/* 5349 */     return leftPad(paramString, paramInt, ' ');
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
/*      */   public static String leftPad(String paramString, int paramInt, char paramChar)
/*      */   {
/* 5374 */     if (paramString == null) {
/* 5375 */       return null;
/*      */     }
/* 5377 */     int i = paramInt - paramString.length();
/* 5378 */     if (i <= 0) {
/* 5379 */       return paramString;
/*      */     }
/* 5381 */     if (i > 8192) {
/* 5382 */       return leftPad(paramString, paramInt, String.valueOf(paramChar));
/*      */     }
/* 5384 */     return repeat(paramChar, i).concat(paramString);
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
/*      */   public static String leftPad(String paramString1, int paramInt, String paramString2)
/*      */   {
/* 5411 */     if (paramString1 == null) {
/* 5412 */       return null;
/*      */     }
/* 5414 */     if (isEmpty(paramString2)) {
/* 5415 */       paramString2 = " ";
/*      */     }
/* 5417 */     int i = paramString2.length();
/* 5418 */     int j = paramString1.length();
/* 5419 */     int k = paramInt - j;
/* 5420 */     if (k <= 0) {
/* 5421 */       return paramString1;
/*      */     }
/* 5423 */     if ((i == 1) && (k <= 8192)) {
/* 5424 */       return leftPad(paramString1, paramInt, paramString2.charAt(0));
/*      */     }
/*      */     
/* 5427 */     if (k == i)
/* 5428 */       return paramString2.concat(paramString1);
/* 5429 */     if (k < i) {
/* 5430 */       return paramString2.substring(0, k).concat(paramString1);
/*      */     }
/* 5432 */     char[] arrayOfChar1 = new char[k];
/* 5433 */     char[] arrayOfChar2 = paramString2.toCharArray();
/* 5434 */     for (int m = 0; m < k; m++) {
/* 5435 */       arrayOfChar1[m] = arrayOfChar2[(m % i)];
/*      */     }
/* 5437 */     return new String(arrayOfChar1).concat(paramString1);
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
/*      */   public static int length(CharSequence paramCharSequence)
/*      */   {
/* 5453 */     return paramCharSequence == null ? 0 : paramCharSequence.length();
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
/*      */   public static String center(String paramString, int paramInt)
/*      */   {
/* 5482 */     return center(paramString, paramInt, ' ');
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
/*      */   public static String center(String paramString, int paramInt, char paramChar)
/*      */   {
/* 5510 */     if ((paramString == null) || (paramInt <= 0)) {
/* 5511 */       return paramString;
/*      */     }
/* 5513 */     int i = paramString.length();
/* 5514 */     int j = paramInt - i;
/* 5515 */     if (j <= 0) {
/* 5516 */       return paramString;
/*      */     }
/* 5518 */     paramString = leftPad(paramString, i + j / 2, paramChar);
/* 5519 */     paramString = rightPad(paramString, paramInt, paramChar);
/* 5520 */     return paramString;
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
/*      */   public static String center(String paramString1, int paramInt, String paramString2)
/*      */   {
/* 5550 */     if ((paramString1 == null) || (paramInt <= 0)) {
/* 5551 */       return paramString1;
/*      */     }
/* 5553 */     if (isEmpty(paramString2)) {
/* 5554 */       paramString2 = " ";
/*      */     }
/* 5556 */     int i = paramString1.length();
/* 5557 */     int j = paramInt - i;
/* 5558 */     if (j <= 0) {
/* 5559 */       return paramString1;
/*      */     }
/* 5561 */     paramString1 = leftPad(paramString1, i + j / 2, paramString2);
/* 5562 */     paramString1 = rightPad(paramString1, paramInt, paramString2);
/* 5563 */     return paramString1;
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
/*      */   public static String upperCase(String paramString)
/*      */   {
/* 5588 */     if (paramString == null) {
/* 5589 */       return null;
/*      */     }
/* 5591 */     return paramString.toUpperCase();
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
/*      */   public static String upperCase(String paramString, Locale paramLocale)
/*      */   {
/* 5611 */     if (paramString == null) {
/* 5612 */       return null;
/*      */     }
/* 5614 */     return paramString.toUpperCase(paramLocale);
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
/*      */   public static String lowerCase(String paramString)
/*      */   {
/* 5637 */     if (paramString == null) {
/* 5638 */       return null;
/*      */     }
/* 5640 */     return paramString.toLowerCase();
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
/*      */   public static String lowerCase(String paramString, Locale paramLocale)
/*      */   {
/* 5660 */     if (paramString == null) {
/* 5661 */       return null;
/*      */     }
/* 5663 */     return paramString.toLowerCase(paramLocale);
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
/*      */   public static String capitalize(String paramString)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5688 */     if ((paramString == null) || ((i = paramString.length()) == 0)) {
/* 5689 */       return paramString;
/*      */     }
/*      */     
/* 5692 */     char c = paramString.charAt(0);
/* 5693 */     if (Character.isTitleCase(c))
/*      */     {
/* 5695 */       return paramString;
/*      */     }
/*      */     
/* 5698 */     return i + Character.toTitleCase(c) + paramString.substring(1);
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
/*      */   public static String uncapitalize(String paramString)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5726 */     if ((paramString == null) || ((i = paramString.length()) == 0)) {
/* 5727 */       return paramString;
/*      */     }
/*      */     
/* 5730 */     char c = paramString.charAt(0);
/* 5731 */     if (Character.isLowerCase(c))
/*      */     {
/* 5733 */       return paramString;
/*      */     }
/*      */     
/* 5736 */     return i + Character.toLowerCase(c) + paramString.substring(1);
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
/*      */   public static String swapCase(String paramString)
/*      */   {
/* 5770 */     if (isEmpty(paramString)) {
/* 5771 */       return paramString;
/*      */     }
/*      */     
/* 5774 */     char[] arrayOfChar = paramString.toCharArray();
/*      */     
/* 5776 */     for (int i = 0; i < arrayOfChar.length; i++) {
/* 5777 */       char c = arrayOfChar[i];
/* 5778 */       if (Character.isUpperCase(c)) {
/* 5779 */         arrayOfChar[i] = Character.toLowerCase(c);
/* 5780 */       } else if (Character.isTitleCase(c)) {
/* 5781 */         arrayOfChar[i] = Character.toLowerCase(c);
/* 5782 */       } else if (Character.isLowerCase(c)) {
/* 5783 */         arrayOfChar[i] = Character.toUpperCase(c);
/*      */       }
/*      */     }
/* 5786 */     return new String(arrayOfChar);
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
/*      */   public static int countMatches(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 5812 */     if ((isEmpty(paramCharSequence1)) || (isEmpty(paramCharSequence2))) {
/* 5813 */       return 0;
/*      */     }
/* 5815 */     int i = 0;
/* 5816 */     int j = 0;
/* 5817 */     while ((j = CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, j)) != -1) {
/* 5818 */       i++;
/* 5819 */       j += paramCharSequence2.length();
/*      */     }
/* 5821 */     return i;
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
/*      */   public static boolean isAlpha(CharSequence paramCharSequence)
/*      */   {
/* 5847 */     if (isEmpty(paramCharSequence)) {
/* 5848 */       return false;
/*      */     }
/* 5850 */     int i = paramCharSequence.length();
/* 5851 */     for (int j = 0; j < i; j++) {
/* 5852 */       if (!Character.isLetter(paramCharSequence.charAt(j))) {
/* 5853 */         return false;
/*      */       }
/*      */     }
/* 5856 */     return true;
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
/*      */   public static boolean isAlphaSpace(CharSequence paramCharSequence)
/*      */   {
/* 5882 */     if (paramCharSequence == null) {
/* 5883 */       return false;
/*      */     }
/* 5885 */     int i = paramCharSequence.length();
/* 5886 */     for (int j = 0; j < i; j++) {
/* 5887 */       if ((!Character.isLetter(paramCharSequence.charAt(j))) && (paramCharSequence.charAt(j) != ' ')) {
/* 5888 */         return false;
/*      */       }
/*      */     }
/* 5891 */     return true;
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
/*      */   public static boolean isAlphanumeric(CharSequence paramCharSequence)
/*      */   {
/* 5917 */     if (isEmpty(paramCharSequence)) {
/* 5918 */       return false;
/*      */     }
/* 5920 */     int i = paramCharSequence.length();
/* 5921 */     for (int j = 0; j < i; j++) {
/* 5922 */       if (!Character.isLetterOrDigit(paramCharSequence.charAt(j))) {
/* 5923 */         return false;
/*      */       }
/*      */     }
/* 5926 */     return true;
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
/*      */   public static boolean isAlphanumericSpace(CharSequence paramCharSequence)
/*      */   {
/* 5952 */     if (paramCharSequence == null) {
/* 5953 */       return false;
/*      */     }
/* 5955 */     int i = paramCharSequence.length();
/* 5956 */     for (int j = 0; j < i; j++) {
/* 5957 */       if ((!Character.isLetterOrDigit(paramCharSequence.charAt(j))) && (paramCharSequence.charAt(j) != ' ')) {
/* 5958 */         return false;
/*      */       }
/*      */     }
/* 5961 */     return true;
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
/*      */   public static boolean isAsciiPrintable(CharSequence paramCharSequence)
/*      */   {
/* 5991 */     if (paramCharSequence == null) {
/* 5992 */       return false;
/*      */     }
/* 5994 */     int i = paramCharSequence.length();
/* 5995 */     for (int j = 0; j < i; j++) {
/* 5996 */       if (!CharUtils.isAsciiPrintable(paramCharSequence.charAt(j))) {
/* 5997 */         return false;
/*      */       }
/*      */     }
/* 6000 */     return true;
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
/*      */   public static boolean isNumeric(CharSequence paramCharSequence)
/*      */   {
/* 6034 */     if (isEmpty(paramCharSequence)) {
/* 6035 */       return false;
/*      */     }
/* 6037 */     int i = paramCharSequence.length();
/* 6038 */     for (int j = 0; j < i; j++) {
/* 6039 */       if (!Character.isDigit(paramCharSequence.charAt(j))) {
/* 6040 */         return false;
/*      */       }
/*      */     }
/* 6043 */     return true;
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
/*      */   public static boolean isNumericSpace(CharSequence paramCharSequence)
/*      */   {
/* 6071 */     if (paramCharSequence == null) {
/* 6072 */       return false;
/*      */     }
/* 6074 */     int i = paramCharSequence.length();
/* 6075 */     for (int j = 0; j < i; j++) {
/* 6076 */       if ((!Character.isDigit(paramCharSequence.charAt(j))) && (paramCharSequence.charAt(j) != ' ')) {
/* 6077 */         return false;
/*      */       }
/*      */     }
/* 6080 */     return true;
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
/*      */   public static boolean isWhitespace(CharSequence paramCharSequence)
/*      */   {
/* 6104 */     if (paramCharSequence == null) {
/* 6105 */       return false;
/*      */     }
/* 6107 */     int i = paramCharSequence.length();
/* 6108 */     for (int j = 0; j < i; j++) {
/* 6109 */       if (!Character.isWhitespace(paramCharSequence.charAt(j))) {
/* 6110 */         return false;
/*      */       }
/*      */     }
/* 6113 */     return true;
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
/*      */   public static boolean isAllLowerCase(CharSequence paramCharSequence)
/*      */   {
/* 6136 */     if ((paramCharSequence == null) || (isEmpty(paramCharSequence))) {
/* 6137 */       return false;
/*      */     }
/* 6139 */     int i = paramCharSequence.length();
/* 6140 */     for (int j = 0; j < i; j++) {
/* 6141 */       if (!Character.isLowerCase(paramCharSequence.charAt(j))) {
/* 6142 */         return false;
/*      */       }
/*      */     }
/* 6145 */     return true;
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
/*      */   public static boolean isAllUpperCase(CharSequence paramCharSequence)
/*      */   {
/* 6168 */     if ((paramCharSequence == null) || (isEmpty(paramCharSequence))) {
/* 6169 */       return false;
/*      */     }
/* 6171 */     int i = paramCharSequence.length();
/* 6172 */     for (int j = 0; j < i; j++) {
/* 6173 */       if (!Character.isUpperCase(paramCharSequence.charAt(j))) {
/* 6174 */         return false;
/*      */       }
/*      */     }
/* 6177 */     return true;
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
/*      */   public static String defaultString(String paramString)
/*      */   {
/* 6199 */     return paramString == null ? "" : paramString;
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
/*      */   public static String defaultString(String paramString1, String paramString2)
/*      */   {
/* 6220 */     return paramString1 == null ? paramString2 : paramString1;
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
/*      */   public static <T extends CharSequence> T defaultIfBlank(T paramT1, T paramT2)
/*      */   {
/* 6242 */     return isBlank(paramT1) ? paramT2 : paramT1;
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
/*      */   public static <T extends CharSequence> T defaultIfEmpty(T paramT1, T paramT2)
/*      */   {
/* 6264 */     return isEmpty(paramT1) ? paramT2 : paramT1;
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
/*      */   public static String reverse(String paramString)
/*      */   {
/* 6284 */     if (paramString == null) {
/* 6285 */       return null;
/*      */     }
/* 6287 */     return new StringBuilder(paramString).reverse().toString();
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
/*      */   public static String reverseDelimited(String paramString, char paramChar)
/*      */   {
/* 6310 */     if (paramString == null) {
/* 6311 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 6315 */     String[] arrayOfString = split(paramString, paramChar);
/* 6316 */     ArrayUtils.reverse(arrayOfString);
/* 6317 */     return join(arrayOfString, paramChar);
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
/*      */   public static String abbreviate(String paramString, int paramInt)
/*      */   {
/* 6354 */     return abbreviate(paramString, 0, paramInt);
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
/*      */   public static String abbreviate(String paramString, int paramInt1, int paramInt2)
/*      */   {
/* 6393 */     if (paramString == null) {
/* 6394 */       return null;
/*      */     }
/* 6396 */     if (paramInt2 < 4) {
/* 6397 */       throw new IllegalArgumentException("Minimum abbreviation width is 4");
/*      */     }
/* 6399 */     if (paramString.length() <= paramInt2) {
/* 6400 */       return paramString;
/*      */     }
/* 6402 */     if (paramInt1 > paramString.length()) {
/* 6403 */       paramInt1 = paramString.length();
/*      */     }
/* 6405 */     if (paramString.length() - paramInt1 < paramInt2 - 3) {
/* 6406 */       paramInt1 = paramString.length() - (paramInt2 - 3);
/*      */     }
/* 6408 */     String str = "...";
/* 6409 */     if (paramInt1 <= 4) {
/* 6410 */       return paramString.substring(0, paramInt2 - 3) + "...";
/*      */     }
/* 6412 */     if (paramInt2 < 7) {
/* 6413 */       throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
/*      */     }
/* 6415 */     if (paramInt1 + paramInt2 - 3 < paramString.length()) {
/* 6416 */       return "..." + abbreviate(paramString.substring(paramInt1), paramInt2 - 3);
/*      */     }
/* 6418 */     return "..." + paramString.substring(paramString.length() - (paramInt2 - 3));
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
/*      */   public static String abbreviateMiddle(String paramString1, String paramString2, int paramInt)
/*      */   {
/* 6451 */     if ((isEmpty(paramString1)) || (isEmpty(paramString2))) {
/* 6452 */       return paramString1;
/*      */     }
/*      */     
/* 6455 */     if ((paramInt >= paramString1.length()) || (paramInt < paramString2.length() + 2)) {
/* 6456 */       return paramString1;
/*      */     }
/*      */     
/* 6459 */     int i = paramInt - paramString2.length();
/* 6460 */     int j = i / 2 + i % 2;
/* 6461 */     int k = paramString1.length() - i / 2;
/*      */     
/* 6463 */     StringBuilder localStringBuilder = new StringBuilder(paramInt);
/* 6464 */     localStringBuilder.append(paramString1.substring(0, j));
/* 6465 */     localStringBuilder.append(paramString2);
/* 6466 */     localStringBuilder.append(paramString1.substring(k));
/*      */     
/* 6468 */     return localStringBuilder.toString();
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
/*      */   public static String difference(String paramString1, String paramString2)
/*      */   {
/* 6502 */     if (paramString1 == null) {
/* 6503 */       return paramString2;
/*      */     }
/* 6505 */     if (paramString2 == null) {
/* 6506 */       return paramString1;
/*      */     }
/* 6508 */     int i = indexOfDifference(paramString1, paramString2);
/* 6509 */     if (i == -1) {
/* 6510 */       return "";
/*      */     }
/* 6512 */     return paramString2.substring(i);
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
/*      */   public static int indexOfDifference(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 6541 */     if (paramCharSequence1 == paramCharSequence2) {
/* 6542 */       return -1;
/*      */     }
/* 6544 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 6545 */       return 0;
/*      */     }
/*      */     
/* 6548 */     for (int i = 0; (i < paramCharSequence1.length()) && (i < paramCharSequence2.length()); i++) {
/* 6549 */       if (paramCharSequence1.charAt(i) != paramCharSequence2.charAt(i)) {
/*      */         break;
/*      */       }
/*      */     }
/* 6553 */     if ((i < paramCharSequence2.length()) || (i < paramCharSequence1.length())) {
/* 6554 */       return i;
/*      */     }
/* 6556 */     return -1;
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
/*      */   public static int indexOfDifference(CharSequence... paramVarArgs)
/*      */   {
/* 6592 */     if ((paramVarArgs == null) || (paramVarArgs.length <= 1)) {
/* 6593 */       return -1;
/*      */     }
/* 6595 */     int i = 0;
/* 6596 */     int j = 1;
/* 6597 */     int k = paramVarArgs.length;
/* 6598 */     int m = Integer.MAX_VALUE;
/* 6599 */     int n = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 6604 */     for (int i1 = 0; i1 < k; i1++) {
/* 6605 */       if (paramVarArgs[i1] == null) {
/* 6606 */         i = 1;
/* 6607 */         m = 0;
/*      */       } else {
/* 6609 */         j = 0;
/* 6610 */         m = Math.min(paramVarArgs[i1].length(), m);
/* 6611 */         n = Math.max(paramVarArgs[i1].length(), n);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 6616 */     if ((j != 0) || ((n == 0) && (i == 0))) {
/* 6617 */       return -1;
/*      */     }
/*      */     
/*      */ 
/* 6621 */     if (m == 0) {
/* 6622 */       return 0;
/*      */     }
/*      */     
/*      */ 
/* 6626 */     i1 = -1;
/* 6627 */     for (int i2 = 0; i2 < m; i2++) {
/* 6628 */       int i3 = paramVarArgs[0].charAt(i2);
/* 6629 */       for (int i4 = 1; i4 < k; i4++) {
/* 6630 */         if (paramVarArgs[i4].charAt(i2) != i3) {
/* 6631 */           i1 = i2;
/* 6632 */           break;
/*      */         }
/*      */       }
/* 6635 */       if (i1 != -1) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/* 6640 */     if ((i1 == -1) && (m != n))
/*      */     {
/*      */ 
/*      */ 
/* 6644 */       return m;
/*      */     }
/* 6646 */     return i1;
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
/*      */   public static String getCommonPrefix(String... paramVarArgs)
/*      */   {
/* 6683 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 6684 */       return "";
/*      */     }
/* 6686 */     int i = indexOfDifference(paramVarArgs);
/* 6687 */     if (i == -1)
/*      */     {
/* 6689 */       if (paramVarArgs[0] == null) {
/* 6690 */         return "";
/*      */       }
/* 6692 */       return paramVarArgs[0]; }
/* 6693 */     if (i == 0)
/*      */     {
/* 6695 */       return "";
/*      */     }
/*      */     
/* 6698 */     return paramVarArgs[0].substring(0, i);
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
/*      */   public static int getLevenshteinDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 6741 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 6742 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6762 */     int i = paramCharSequence1.length();
/* 6763 */     int j = paramCharSequence2.length();
/*      */     
/* 6765 */     if (i == 0)
/* 6766 */       return j;
/* 6767 */     if (j == 0) {
/* 6768 */       return i;
/*      */     }
/*      */     
/* 6771 */     if (i > j)
/*      */     {
/* 6773 */       localObject1 = paramCharSequence1;
/* 6774 */       paramCharSequence1 = paramCharSequence2;
/* 6775 */       paramCharSequence2 = (CharSequence)localObject1;
/* 6776 */       i = j;
/* 6777 */       j = paramCharSequence2.length();
/*      */     }
/*      */     
/* 6780 */     Object localObject1 = new int[i + 1];
/* 6781 */     Object localObject2 = new int[i + 1];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6792 */     for (int k = 0; k <= i; k++) {
/* 6793 */       localObject1[k] = k;
/*      */     }
/*      */     
/* 6796 */     for (int m = 1; m <= j; m++) {
/* 6797 */       int n = paramCharSequence2.charAt(m - 1);
/* 6798 */       localObject2[0] = m;
/*      */       
/* 6800 */       for (k = 1; k <= i; k++) {
/* 6801 */         int i1 = paramCharSequence1.charAt(k - 1) == n ? 0 : 1;
/*      */         
/* 6803 */         localObject2[k] = Math.min(Math.min(localObject2[(k - 1)] + 1, localObject1[k] + 1), localObject1[(k - 1)] + i1);
/*      */       }
/*      */       
/*      */ 
/* 6807 */       Object localObject3 = localObject1;
/* 6808 */       localObject1 = localObject2;
/* 6809 */       localObject2 = localObject3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 6814 */     return localObject1[i];
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
/*      */   public static int getLevenshteinDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 6850 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 6851 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/* 6853 */     if (paramInt < 0) {
/* 6854 */       throw new IllegalArgumentException("Threshold must not be negative");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6901 */     int i = paramCharSequence1.length();
/* 6902 */     int j = paramCharSequence2.length();
/*      */     
/*      */ 
/* 6905 */     if (i == 0)
/* 6906 */       return j <= paramInt ? j : -1;
/* 6907 */     if (j == 0) {
/* 6908 */       return i <= paramInt ? i : -1;
/*      */     }
/*      */     
/* 6911 */     if (i > j)
/*      */     {
/* 6913 */       localObject1 = paramCharSequence1;
/* 6914 */       paramCharSequence1 = paramCharSequence2;
/* 6915 */       paramCharSequence2 = (CharSequence)localObject1;
/* 6916 */       i = j;
/* 6917 */       j = paramCharSequence2.length();
/*      */     }
/*      */     
/* 6920 */     Object localObject1 = new int[i + 1];
/* 6921 */     Object localObject2 = new int[i + 1];
/*      */     
/*      */ 
/*      */ 
/* 6925 */     int k = Math.min(i, paramInt) + 1;
/* 6926 */     for (int m = 0; m < k; m++) {
/* 6927 */       localObject1[m] = m;
/*      */     }
/*      */     
/*      */ 
/* 6931 */     Arrays.fill((int[])localObject1, k, localObject1.length, Integer.MAX_VALUE);
/* 6932 */     Arrays.fill((int[])localObject2, Integer.MAX_VALUE);
/*      */     
/*      */ 
/* 6935 */     for (m = 1; m <= j; m++) {
/* 6936 */       int n = paramCharSequence2.charAt(m - 1);
/* 6937 */       localObject2[0] = m;
/*      */       
/*      */ 
/* 6940 */       int i1 = Math.max(1, m - paramInt);
/* 6941 */       int i2 = m > Integer.MAX_VALUE - paramInt ? i : Math.min(i, m + paramInt);
/*      */       
/*      */ 
/* 6944 */       if (i1 > i2) {
/* 6945 */         return -1;
/*      */       }
/*      */       
/*      */ 
/* 6949 */       if (i1 > 1) {
/* 6950 */         localObject2[(i1 - 1)] = Integer.MAX_VALUE;
/*      */       }
/*      */       
/*      */ 
/* 6954 */       for (int i3 = i1; i3 <= i2; i3++) {
/* 6955 */         if (paramCharSequence1.charAt(i3 - 1) == n)
/*      */         {
/* 6957 */           localObject2[i3] = localObject1[(i3 - 1)];
/*      */         }
/*      */         else {
/* 6960 */           localObject2[i3] = (1 + Math.min(Math.min(localObject2[(i3 - 1)], localObject1[i3]), localObject1[(i3 - 1)]));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 6965 */       Object localObject3 = localObject1;
/* 6966 */       localObject1 = localObject2;
/* 6967 */       localObject2 = localObject3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 6972 */     if (localObject1[i] <= paramInt) {
/* 6973 */       return localObject1[i];
/*      */     }
/* 6975 */     return -1;
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
/*      */   public static double getJaroWinklerDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7011 */     double d1 = 0.1D;
/*      */     
/* 7013 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 7014 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */     
/* 7017 */     double d2 = score(paramCharSequence1, paramCharSequence2);
/* 7018 */     int i = commonPrefixLength(paramCharSequence1, paramCharSequence2);
/* 7019 */     double d3 = Math.round((d2 + 0.1D * i * (1.0D - d2)) * 100.0D) / 100.0D;
/*      */     
/* 7021 */     return d3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static double score(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/*      */     String str1;
/*      */     
/*      */ 
/*      */     String str2;
/*      */     
/*      */ 
/* 7035 */     if (paramCharSequence1.length() > paramCharSequence2.length()) {
/* 7036 */       str1 = paramCharSequence1.toString().toLowerCase();
/* 7037 */       str2 = paramCharSequence2.toString().toLowerCase();
/*      */     } else {
/* 7039 */       str1 = paramCharSequence2.toString().toLowerCase();
/* 7040 */       str2 = paramCharSequence1.toString().toLowerCase();
/*      */     }
/*      */     
/*      */ 
/* 7044 */     int i = str2.length() / 2 + 1;
/*      */     
/*      */ 
/*      */ 
/* 7048 */     String str3 = getSetOfMatchingCharacterWithin(str2, str1, i);
/* 7049 */     String str4 = getSetOfMatchingCharacterWithin(str1, str2, i);
/*      */     
/*      */ 
/*      */ 
/* 7053 */     if ((str3.length() == 0) || (str4.length() == 0)) {
/* 7054 */       return 0.0D;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7059 */     if (str3.length() != str4.length()) {
/* 7060 */       return 0.0D;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7065 */     int j = transpositions(str3, str4);
/*      */     
/*      */ 
/* 7068 */     double d = (str3.length() / str2.length() + str4.length() / str1.length() + (str3.length() - j) / str3.length()) / 3.0D;
/*      */     
/*      */ 
/*      */ 
/* 7072 */     return d;
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
/*      */   private static String getSetOfMatchingCharacterWithin(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*      */   {
/* 7087 */     StringBuilder localStringBuilder1 = new StringBuilder();
/* 7088 */     StringBuilder localStringBuilder2 = new StringBuilder(paramCharSequence2);
/*      */     
/* 7090 */     for (int i = 0; i < paramCharSequence1.length(); i++) {
/* 7091 */       char c = paramCharSequence1.charAt(i);
/* 7092 */       int j = 0;
/*      */       
/*      */ 
/* 7095 */       for (int k = Math.max(0, i - paramInt); (j == 0) && (k < Math.min(i + paramInt, paramCharSequence2.length())); k++) {
/* 7096 */         if (localStringBuilder2.charAt(k) == c) {
/* 7097 */           j = 1;
/* 7098 */           localStringBuilder1.append(c);
/* 7099 */           localStringBuilder2.setCharAt(k, '*');
/*      */         }
/*      */       }
/*      */     }
/* 7103 */     return localStringBuilder1.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int transpositions(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7113 */     int i = 0;
/* 7114 */     for (int j = 0; j < paramCharSequence1.length(); j++) {
/* 7115 */       if (paramCharSequence1.charAt(j) != paramCharSequence2.charAt(j)) {
/* 7116 */         i++;
/*      */       }
/*      */     }
/* 7119 */     return i / 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int commonPrefixLength(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7130 */     int i = getCommonPrefix(new String[] { paramCharSequence1.toString(), paramCharSequence2.toString() }).length();
/*      */     
/*      */ 
/* 7133 */     return i > 4 ? 4 : i;
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
/*      */   public static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7162 */     return startsWith(paramCharSequence1, paramCharSequence2, false);
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
/*      */   public static boolean startsWithIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7188 */     return startsWith(paramCharSequence1, paramCharSequence2, true);
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
/*      */   private static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
/*      */   {
/* 7203 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 7204 */       return (paramCharSequence1 == null) && (paramCharSequence2 == null);
/*      */     }
/* 7206 */     if (paramCharSequence2.length() > paramCharSequence1.length()) {
/* 7207 */       return false;
/*      */     }
/* 7209 */     return CharSequenceUtils.regionMatches(paramCharSequence1, paramBoolean, 0, paramCharSequence2, 0, paramCharSequence2.length());
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
/*      */   public static boolean startsWithAny(CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7232 */     if ((isEmpty(paramCharSequence)) || (ArrayUtils.isEmpty(paramVarArgs))) {
/* 7233 */       return false;
/*      */     }
/* 7235 */     for (CharSequence localCharSequence : paramVarArgs) {
/* 7236 */       if (startsWith(paramCharSequence, localCharSequence)) {
/* 7237 */         return true;
/*      */       }
/*      */     }
/* 7240 */     return false;
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
/*      */   public static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7270 */     return endsWith(paramCharSequence1, paramCharSequence2, false);
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
/*      */   public static boolean endsWithIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 7297 */     return endsWith(paramCharSequence1, paramCharSequence2, true);
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
/*      */   private static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
/*      */   {
/* 7312 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 7313 */       return (paramCharSequence1 == null) && (paramCharSequence2 == null);
/*      */     }
/* 7315 */     if (paramCharSequence2.length() > paramCharSequence1.length()) {
/* 7316 */       return false;
/*      */     }
/* 7318 */     int i = paramCharSequence1.length() - paramCharSequence2.length();
/* 7319 */     return CharSequenceUtils.regionMatches(paramCharSequence1, paramBoolean, i, paramCharSequence2, 0, paramCharSequence2.length());
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
/*      */   public static String normalizeSpace(String paramString)
/*      */   {
/* 7364 */     if (paramString == null) {
/* 7365 */       return null;
/*      */     }
/* 7367 */     return WHITESPACE_PATTERN.matcher(trim(paramString)).replaceAll(" ");
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
/*      */   public static boolean endsWithAny(CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7389 */     if ((isEmpty(paramCharSequence)) || (ArrayUtils.isEmpty(paramVarArgs))) {
/* 7390 */       return false;
/*      */     }
/* 7392 */     for (CharSequence localCharSequence : paramVarArgs) {
/* 7393 */       if (endsWith(paramCharSequence, localCharSequence)) {
/* 7394 */         return true;
/*      */       }
/*      */     }
/* 7397 */     return false;
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
/*      */   private static String appendIfMissing(String paramString, CharSequence paramCharSequence, boolean paramBoolean, CharSequence... paramVarArgs)
/*      */   {
/* 7412 */     if ((paramString == null) || (isEmpty(paramCharSequence)) || (endsWith(paramString, paramCharSequence, paramBoolean))) {
/* 7413 */       return paramString;
/*      */     }
/* 7415 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0)) {
/* 7416 */       for (CharSequence localCharSequence : paramVarArgs) {
/* 7417 */         if (endsWith(paramString, localCharSequence, paramBoolean)) {
/* 7418 */           return paramString;
/*      */         }
/*      */       }
/*      */     }
/* 7422 */     return paramString + paramCharSequence.toString();
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
/*      */   public static String appendIfMissing(String paramString, CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7460 */     return appendIfMissing(paramString, paramCharSequence, false, paramVarArgs);
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
/*      */   public static String appendIfMissingIgnoreCase(String paramString, CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7498 */     return appendIfMissing(paramString, paramCharSequence, true, paramVarArgs);
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
/*      */   private static String prependIfMissing(String paramString, CharSequence paramCharSequence, boolean paramBoolean, CharSequence... paramVarArgs)
/*      */   {
/* 7513 */     if ((paramString == null) || (isEmpty(paramCharSequence)) || (startsWith(paramString, paramCharSequence, paramBoolean))) {
/* 7514 */       return paramString;
/*      */     }
/* 7516 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0)) {
/* 7517 */       for (CharSequence localCharSequence : paramVarArgs) {
/* 7518 */         if (startsWith(paramString, localCharSequence, paramBoolean)) {
/* 7519 */           return paramString;
/*      */         }
/*      */       }
/*      */     }
/* 7523 */     return paramCharSequence.toString() + paramString;
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
/*      */   public static String prependIfMissing(String paramString, CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7561 */     return prependIfMissing(paramString, paramCharSequence, false, paramVarArgs);
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
/*      */   public static String prependIfMissingIgnoreCase(String paramString, CharSequence paramCharSequence, CharSequence... paramVarArgs)
/*      */   {
/* 7599 */     return prependIfMissing(paramString, paramCharSequence, true, paramVarArgs);
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
/*      */   @Deprecated
/*      */   public static String toString(byte[] paramArrayOfByte, String paramString)
/*      */     throws UnsupportedEncodingException
/*      */   {
/* 7619 */     return paramString != null ? new String(paramArrayOfByte, paramString) : new String(paramArrayOfByte, Charset.defaultCharset());
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
/*      */   public static String toEncodedString(byte[] paramArrayOfByte, Charset paramCharset)
/*      */   {
/* 7636 */     return new String(paramArrayOfByte, paramCharset != null ? paramCharset : Charset.defaultCharset());
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */