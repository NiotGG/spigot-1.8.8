/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Stack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FilenameUtils
/*      */ {
/*      */   public static final char EXTENSION_SEPARATOR = '.';
/*   95 */   public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final char UNIX_SEPARATOR = '/';
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final char WINDOWS_SEPARATOR = '\\';
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  110 */   private static final char SYSTEM_SEPARATOR = File.separatorChar;
/*      */   
/*      */   private static final char OTHER_SEPARATOR;
/*      */   
/*      */ 
/*      */   static
/*      */   {
/*  117 */     if (isSystemWindows()) {
/*  118 */       OTHER_SEPARATOR = '/';
/*      */     } else {
/*  120 */       OTHER_SEPARATOR = '\\';
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
/*      */   static boolean isSystemWindows()
/*      */   {
/*  138 */     return SYSTEM_SEPARATOR == '\\';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isSeparator(char paramChar)
/*      */   {
/*  149 */     return (paramChar == '/') || (paramChar == '\\');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String normalize(String paramString)
/*      */   {
/*  194 */     return doNormalize(paramString, SYSTEM_SEPARATOR, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String normalize(String paramString, boolean paramBoolean)
/*      */   {
/*  241 */     char c = paramBoolean ? '/' : '\\';
/*  242 */     return doNormalize(paramString, c, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String normalizeNoEndSeparator(String paramString)
/*      */   {
/*  288 */     return doNormalize(paramString, SYSTEM_SEPARATOR, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String normalizeNoEndSeparator(String paramString, boolean paramBoolean)
/*      */   {
/*  335 */     char c = paramBoolean ? '/' : '\\';
/*  336 */     return doNormalize(paramString, c, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String doNormalize(String paramString, char paramChar, boolean paramBoolean)
/*      */   {
/*  348 */     if (paramString == null) {
/*  349 */       return null;
/*      */     }
/*  351 */     int i = paramString.length();
/*  352 */     if (i == 0) {
/*  353 */       return paramString;
/*      */     }
/*  355 */     int j = getPrefixLength(paramString);
/*  356 */     if (j < 0) {
/*  357 */       return null;
/*      */     }
/*      */     
/*  360 */     char[] arrayOfChar = new char[i + 2];
/*  361 */     paramString.getChars(0, paramString.length(), arrayOfChar, 0);
/*      */     
/*      */ 
/*  364 */     int k = paramChar == SYSTEM_SEPARATOR ? OTHER_SEPARATOR : SYSTEM_SEPARATOR;
/*  365 */     for (int m = 0; m < arrayOfChar.length; m++) {
/*  366 */       if (arrayOfChar[m] == k) {
/*  367 */         arrayOfChar[m] = paramChar;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  372 */     m = 1;
/*  373 */     if (arrayOfChar[(i - 1)] != paramChar) {
/*  374 */       arrayOfChar[(i++)] = paramChar;
/*  375 */       m = 0;
/*      */     }
/*      */     
/*      */ 
/*  379 */     for (int n = j + 1; n < i; n++) {
/*  380 */       if ((arrayOfChar[n] == paramChar) && (arrayOfChar[(n - 1)] == paramChar)) {
/*  381 */         System.arraycopy(arrayOfChar, n, arrayOfChar, n - 1, i - n);
/*  382 */         i--;
/*  383 */         n--;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  388 */     for (n = j + 1; n < i; n++) {
/*  389 */       if ((arrayOfChar[n] == paramChar) && (arrayOfChar[(n - 1)] == '.') && ((n == j + 1) || (arrayOfChar[(n - 2)] == paramChar)))
/*      */       {
/*  391 */         if (n == i - 1) {
/*  392 */           m = 1;
/*      */         }
/*  394 */         System.arraycopy(arrayOfChar, n + 1, arrayOfChar, n - 1, i - n);
/*  395 */         i -= 2;
/*  396 */         n--;
/*      */       }
/*      */     }
/*      */     
/*      */     label464:
/*      */     
/*  402 */     for (n = j + 2; n < i; n++) {
/*  403 */       if ((arrayOfChar[n] == paramChar) && (arrayOfChar[(n - 1)] == '.') && (arrayOfChar[(n - 2)] == '.') && ((n == j + 2) || (arrayOfChar[(n - 3)] == paramChar)))
/*      */       {
/*  405 */         if (n == j + 2) {
/*  406 */           return null;
/*      */         }
/*  408 */         if (n == i - 1) {
/*  409 */           m = 1;
/*      */         }
/*      */         
/*  412 */         for (int i1 = n - 4; i1 >= j; i1--) {
/*  413 */           if (arrayOfChar[i1] == paramChar)
/*      */           {
/*  415 */             System.arraycopy(arrayOfChar, n + 1, arrayOfChar, i1 + 1, i - n);
/*  416 */             i -= n - i1;
/*  417 */             n = i1 + 1;
/*      */             
/*      */             break label464;
/*      */           }
/*      */         }
/*  422 */         System.arraycopy(arrayOfChar, n + 1, arrayOfChar, j, i - n);
/*  423 */         i -= n + 1 - j;
/*  424 */         n = j + 1;
/*      */       }
/*      */     }
/*      */     
/*  428 */     if (i <= 0) {
/*  429 */       return "";
/*      */     }
/*  431 */     if (i <= j) {
/*  432 */       return new String(arrayOfChar, 0, i);
/*      */     }
/*  434 */     if ((m != 0) && (paramBoolean)) {
/*  435 */       return new String(arrayOfChar, 0, i);
/*      */     }
/*  437 */     return new String(arrayOfChar, 0, i - 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String concat(String paramString1, String paramString2)
/*      */   {
/*  482 */     int i = getPrefixLength(paramString2);
/*  483 */     if (i < 0) {
/*  484 */       return null;
/*      */     }
/*  486 */     if (i > 0) {
/*  487 */       return normalize(paramString2);
/*      */     }
/*  489 */     if (paramString1 == null) {
/*  490 */       return null;
/*      */     }
/*  492 */     int j = paramString1.length();
/*  493 */     if (j == 0) {
/*  494 */       return normalize(paramString2);
/*      */     }
/*  496 */     char c = paramString1.charAt(j - 1);
/*  497 */     if (isSeparator(c)) {
/*  498 */       return normalize(paramString1 + paramString2);
/*      */     }
/*  500 */     return normalize(paramString1 + '/' + paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean directoryContains(String paramString1, String paramString2)
/*      */     throws IOException
/*      */   {
/*  531 */     if (paramString1 == null) {
/*  532 */       throw new IllegalArgumentException("Directory must not be null");
/*      */     }
/*      */     
/*  535 */     if (paramString2 == null) {
/*  536 */       return false;
/*      */     }
/*      */     
/*  539 */     if (IOCase.SYSTEM.checkEquals(paramString1, paramString2)) {
/*  540 */       return false;
/*      */     }
/*      */     
/*  543 */     return IOCase.SYSTEM.checkStartsWith(paramString2, paramString1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String separatorsToUnix(String paramString)
/*      */   {
/*  554 */     if ((paramString == null) || (paramString.indexOf('\\') == -1)) {
/*  555 */       return paramString;
/*      */     }
/*  557 */     return paramString.replace('\\', '/');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String separatorsToWindows(String paramString)
/*      */   {
/*  567 */     if ((paramString == null) || (paramString.indexOf('/') == -1)) {
/*  568 */       return paramString;
/*      */     }
/*  570 */     return paramString.replace('/', '\\');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String separatorsToSystem(String paramString)
/*      */   {
/*  580 */     if (paramString == null) {
/*  581 */       return null;
/*      */     }
/*  583 */     if (isSystemWindows()) {
/*  584 */       return separatorsToWindows(paramString);
/*      */     }
/*  586 */     return separatorsToUnix(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getPrefixLength(String paramString)
/*      */   {
/*  623 */     if (paramString == null) {
/*  624 */       return -1;
/*      */     }
/*  626 */     int i = paramString.length();
/*  627 */     if (i == 0) {
/*  628 */       return 0;
/*      */     }
/*  630 */     char c = paramString.charAt(0);
/*  631 */     if (c == ':') {
/*  632 */       return -1;
/*      */     }
/*  634 */     if (i == 1) {
/*  635 */       if (c == '~') {
/*  636 */         return 2;
/*      */       }
/*  638 */       return isSeparator(c) ? 1 : 0; }
/*      */     int k;
/*  640 */     if (c == '~') {
/*  641 */       j = paramString.indexOf('/', 1);
/*  642 */       k = paramString.indexOf('\\', 1);
/*  643 */       if ((j == -1) && (k == -1)) {
/*  644 */         return i + 1;
/*      */       }
/*  646 */       j = j == -1 ? k : j;
/*  647 */       k = k == -1 ? j : k;
/*  648 */       return Math.min(j, k) + 1;
/*      */     }
/*  650 */     int j = paramString.charAt(1);
/*  651 */     if (j == 58) {
/*  652 */       c = Character.toUpperCase(c);
/*  653 */       if ((c >= 'A') && (c <= 'Z')) {
/*  654 */         if ((i == 2) || (!isSeparator(paramString.charAt(2)))) {
/*  655 */           return 2;
/*      */         }
/*  657 */         return 3;
/*      */       }
/*  659 */       return -1;
/*      */     }
/*  661 */     if ((isSeparator(c)) && (isSeparator(j))) {
/*  662 */       k = paramString.indexOf('/', 2);
/*  663 */       int m = paramString.indexOf('\\', 2);
/*  664 */       if (((k == -1) && (m == -1)) || (k == 2) || (m == 2)) {
/*  665 */         return -1;
/*      */       }
/*  667 */       k = k == -1 ? m : k;
/*  668 */       m = m == -1 ? k : m;
/*  669 */       return Math.min(k, m) + 1;
/*      */     }
/*  671 */     return isSeparator(c) ? 1 : 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int indexOfLastSeparator(String paramString)
/*      */   {
/*  689 */     if (paramString == null) {
/*  690 */       return -1;
/*      */     }
/*  692 */     int i = paramString.lastIndexOf('/');
/*  693 */     int j = paramString.lastIndexOf('\\');
/*  694 */     return Math.max(i, j);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int indexOfExtension(String paramString)
/*      */   {
/*  711 */     if (paramString == null) {
/*  712 */       return -1;
/*      */     }
/*  714 */     int i = paramString.lastIndexOf('.');
/*  715 */     int j = indexOfLastSeparator(paramString);
/*  716 */     return j > i ? -1 : i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPrefix(String paramString)
/*      */   {
/*  750 */     if (paramString == null) {
/*  751 */       return null;
/*      */     }
/*  753 */     int i = getPrefixLength(paramString);
/*  754 */     if (i < 0) {
/*  755 */       return null;
/*      */     }
/*  757 */     if (i > paramString.length()) {
/*  758 */       return paramString + '/';
/*      */     }
/*  760 */     return paramString.substring(0, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPath(String paramString)
/*      */   {
/*  786 */     return doGetPath(paramString, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPathNoEndSeparator(String paramString)
/*      */   {
/*  813 */     return doGetPath(paramString, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String doGetPath(String paramString, int paramInt)
/*      */   {
/*  824 */     if (paramString == null) {
/*  825 */       return null;
/*      */     }
/*  827 */     int i = getPrefixLength(paramString);
/*  828 */     if (i < 0) {
/*  829 */       return null;
/*      */     }
/*  831 */     int j = indexOfLastSeparator(paramString);
/*  832 */     int k = j + paramInt;
/*  833 */     if ((i >= paramString.length()) || (j < 0) || (i >= k)) {
/*  834 */       return "";
/*      */     }
/*  836 */     return paramString.substring(i, k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getFullPath(String paramString)
/*      */   {
/*  865 */     return doGetFullPath(paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getFullPathNoEndSeparator(String paramString)
/*      */   {
/*  895 */     return doGetFullPath(paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String doGetFullPath(String paramString, boolean paramBoolean)
/*      */   {
/*  906 */     if (paramString == null) {
/*  907 */       return null;
/*      */     }
/*  909 */     int i = getPrefixLength(paramString);
/*  910 */     if (i < 0) {
/*  911 */       return null;
/*      */     }
/*  913 */     if (i >= paramString.length()) {
/*  914 */       if (paramBoolean) {
/*  915 */         return getPrefix(paramString);
/*      */       }
/*  917 */       return paramString;
/*      */     }
/*      */     
/*  920 */     int j = indexOfLastSeparator(paramString);
/*  921 */     if (j < 0) {
/*  922 */       return paramString.substring(0, i);
/*      */     }
/*  924 */     int k = j + (paramBoolean ? 1 : 0);
/*  925 */     if (k == 0) {
/*  926 */       k++;
/*      */     }
/*  928 */     return paramString.substring(0, k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getName(String paramString)
/*      */   {
/*  949 */     if (paramString == null) {
/*  950 */       return null;
/*      */     }
/*  952 */     int i = indexOfLastSeparator(paramString);
/*  953 */     return paramString.substring(i + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getBaseName(String paramString)
/*      */   {
/*  974 */     return removeExtension(getName(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getExtension(String paramString)
/*      */   {
/*  996 */     if (paramString == null) {
/*  997 */       return null;
/*      */     }
/*  999 */     int i = indexOfExtension(paramString);
/* 1000 */     if (i == -1) {
/* 1001 */       return "";
/*      */     }
/* 1003 */     return paramString.substring(i + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String removeExtension(String paramString)
/*      */   {
/* 1026 */     if (paramString == null) {
/* 1027 */       return null;
/*      */     }
/* 1029 */     int i = indexOfExtension(paramString);
/* 1030 */     if (i == -1) {
/* 1031 */       return paramString;
/*      */     }
/* 1033 */     return paramString.substring(0, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equals(String paramString1, String paramString2)
/*      */   {
/* 1050 */     return equals(paramString1, paramString2, false, IOCase.SENSITIVE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equalsOnSystem(String paramString1, String paramString2)
/*      */   {
/* 1065 */     return equals(paramString1, paramString2, false, IOCase.SYSTEM);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equalsNormalized(String paramString1, String paramString2)
/*      */   {
/* 1081 */     return equals(paramString1, paramString2, true, IOCase.SENSITIVE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equalsNormalizedOnSystem(String paramString1, String paramString2)
/*      */   {
/* 1098 */     return equals(paramString1, paramString2, true, IOCase.SYSTEM);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equals(String paramString1, String paramString2, boolean paramBoolean, IOCase paramIOCase)
/*      */   {
/* 1116 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 1117 */       return (paramString1 == null) && (paramString2 == null);
/*      */     }
/* 1119 */     if (paramBoolean) {
/* 1120 */       paramString1 = normalize(paramString1);
/* 1121 */       paramString2 = normalize(paramString2);
/* 1122 */       if ((paramString1 == null) || (paramString2 == null)) {
/* 1123 */         throw new NullPointerException("Error normalizing one or both of the file names");
/*      */       }
/*      */     }
/*      */     
/* 1127 */     if (paramIOCase == null) {
/* 1128 */       paramIOCase = IOCase.SENSITIVE;
/*      */     }
/* 1130 */     return paramIOCase.checkEquals(paramString1, paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isExtension(String paramString1, String paramString2)
/*      */   {
/* 1146 */     if (paramString1 == null) {
/* 1147 */       return false;
/*      */     }
/* 1149 */     if ((paramString2 == null) || (paramString2.length() == 0)) {
/* 1150 */       return indexOfExtension(paramString1) == -1;
/*      */     }
/* 1152 */     String str = getExtension(paramString1);
/* 1153 */     return str.equals(paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isExtension(String paramString, String[] paramArrayOfString)
/*      */   {
/* 1168 */     if (paramString == null) {
/* 1169 */       return false;
/*      */     }
/* 1171 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 1172 */       return indexOfExtension(paramString) == -1;
/*      */     }
/* 1174 */     String str1 = getExtension(paramString);
/* 1175 */     for (String str2 : paramArrayOfString) {
/* 1176 */       if (str1.equals(str2)) {
/* 1177 */         return true;
/*      */       }
/*      */     }
/* 1180 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isExtension(String paramString, Collection<String> paramCollection)
/*      */   {
/* 1195 */     if (paramString == null) {
/* 1196 */       return false;
/*      */     }
/* 1198 */     if ((paramCollection == null) || (paramCollection.isEmpty())) {
/* 1199 */       return indexOfExtension(paramString) == -1;
/*      */     }
/* 1201 */     String str1 = getExtension(paramString);
/* 1202 */     for (String str2 : paramCollection) {
/* 1203 */       if (str1.equals(str2)) {
/* 1204 */         return true;
/*      */       }
/*      */     }
/* 1207 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean wildcardMatch(String paramString1, String paramString2)
/*      */   {
/* 1234 */     return wildcardMatch(paramString1, paramString2, IOCase.SENSITIVE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean wildcardMatchOnSystem(String paramString1, String paramString2)
/*      */   {
/* 1260 */     return wildcardMatch(paramString1, paramString2, IOCase.SYSTEM);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean wildcardMatch(String paramString1, String paramString2, IOCase paramIOCase)
/*      */   {
/* 1278 */     if ((paramString1 == null) && (paramString2 == null)) {
/* 1279 */       return true;
/*      */     }
/* 1281 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 1282 */       return false;
/*      */     }
/* 1284 */     if (paramIOCase == null) {
/* 1285 */       paramIOCase = IOCase.SENSITIVE;
/*      */     }
/* 1287 */     String[] arrayOfString = splitOnTokens(paramString2);
/* 1288 */     int i = 0;
/* 1289 */     int j = 0;
/* 1290 */     int k = 0;
/* 1291 */     Stack localStack = new Stack();
/*      */     
/*      */     do
/*      */     {
/* 1295 */       if (localStack.size() > 0) {
/* 1296 */         int[] arrayOfInt = (int[])localStack.pop();
/* 1297 */         k = arrayOfInt[0];
/* 1298 */         j = arrayOfInt[1];
/* 1299 */         i = 1;
/*      */       }
/*      */       
/*      */ 
/* 1303 */       while (k < arrayOfString.length)
/*      */       {
/* 1305 */         if (arrayOfString[k].equals("?"))
/*      */         {
/* 1307 */           j++;
/* 1308 */           if (j > paramString1.length()) {
/*      */             break;
/*      */           }
/* 1311 */           i = 0;
/*      */         }
/* 1313 */         else if (arrayOfString[k].equals("*"))
/*      */         {
/* 1315 */           i = 1;
/* 1316 */           if (k == arrayOfString.length - 1) {
/* 1317 */             j = paramString1.length();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1322 */           if (i != 0)
/*      */           {
/* 1324 */             j = paramIOCase.checkIndexOf(paramString1, j, arrayOfString[k]);
/* 1325 */             if (j == -1) {
/*      */               break;
/*      */             }
/*      */             
/* 1329 */             int m = paramIOCase.checkIndexOf(paramString1, j + 1, arrayOfString[k]);
/* 1330 */             if (m >= 0) {
/* 1331 */               localStack.push(new int[] { k, m });
/*      */             }
/*      */           }
/*      */           else {
/* 1335 */             if (!paramIOCase.checkRegionMatches(paramString1, j, arrayOfString[k])) {
/*      */               break;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1342 */           j += arrayOfString[k].length();
/* 1343 */           i = 0;
/*      */         }
/*      */         
/* 1346 */         k++;
/*      */       }
/*      */       
/*      */ 
/* 1350 */       if ((k == arrayOfString.length) && (j == paramString1.length())) {
/* 1351 */         return true;
/*      */       }
/*      */       
/* 1354 */     } while (localStack.size() > 0);
/*      */     
/* 1356 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String[] splitOnTokens(String paramString)
/*      */   {
/* 1371 */     if ((paramString.indexOf('?') == -1) && (paramString.indexOf('*') == -1)) {
/* 1372 */       return new String[] { paramString };
/*      */     }
/*      */     
/* 1375 */     char[] arrayOfChar = paramString.toCharArray();
/* 1376 */     ArrayList localArrayList = new ArrayList();
/* 1377 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1378 */     for (int i = 0; i < arrayOfChar.length; i++) {
/* 1379 */       if ((arrayOfChar[i] == '?') || (arrayOfChar[i] == '*')) {
/* 1380 */         if (localStringBuilder.length() != 0) {
/* 1381 */           localArrayList.add(localStringBuilder.toString());
/* 1382 */           localStringBuilder.setLength(0);
/*      */         }
/* 1384 */         if (arrayOfChar[i] == '?') {
/* 1385 */           localArrayList.add("?");
/* 1386 */         } else if ((localArrayList.isEmpty()) || ((i > 0) && (!((String)localArrayList.get(localArrayList.size() - 1)).equals("*"))))
/*      */         {
/* 1388 */           localArrayList.add("*");
/*      */         }
/*      */       } else {
/* 1391 */         localStringBuilder.append(arrayOfChar[i]);
/*      */       }
/*      */     }
/* 1394 */     if (localStringBuilder.length() != 0) {
/* 1395 */       localArrayList.add(localStringBuilder.toString());
/*      */     }
/*      */     
/* 1398 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\FilenameUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */