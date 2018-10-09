/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrSubstitutor
/*      */ {
/*      */   public static final char DEFAULT_ESCAPE = '$';
/*  134 */   public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
/*      */   
/*      */ 
/*      */ 
/*  138 */   public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  143 */   public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private char escapeChar;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private StrMatcher prefixMatcher;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private StrMatcher suffixMatcher;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private StrMatcher valueDelimiterMatcher;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private StrLookup<?> variableResolver;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean enableSubstitutionInVariables;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> String replace(Object paramObject, Map<String, V> paramMap)
/*      */   {
/*  181 */     return new StrSubstitutor(paramMap).replace(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> String replace(Object paramObject, Map<String, V> paramMap, String paramString1, String paramString2)
/*      */   {
/*  198 */     return new StrSubstitutor(paramMap, paramString1, paramString2).replace(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String replace(Object paramObject, Properties paramProperties)
/*      */   {
/*  210 */     if (paramProperties == null) {
/*  211 */       return paramObject.toString();
/*      */     }
/*  213 */     HashMap localHashMap = new HashMap();
/*  214 */     Enumeration localEnumeration = paramProperties.propertyNames();
/*  215 */     while (localEnumeration.hasMoreElements()) {
/*  216 */       String str1 = (String)localEnumeration.nextElement();
/*  217 */       String str2 = paramProperties.getProperty(str1);
/*  218 */       localHashMap.put(str1, str2);
/*      */     }
/*  220 */     return replace(paramObject, localHashMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String replaceSystemProperties(Object paramObject)
/*      */   {
/*  231 */     return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor()
/*      */   {
/*  240 */     this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <V> StrSubstitutor(Map<String, V> paramMap)
/*      */   {
/*  251 */     this(StrLookup.mapLookup(paramMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <V> StrSubstitutor(Map<String, V> paramMap, String paramString1, String paramString2)
/*      */   {
/*  264 */     this(StrLookup.mapLookup(paramMap), paramString1, paramString2, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <V> StrSubstitutor(Map<String, V> paramMap, String paramString1, String paramString2, char paramChar)
/*      */   {
/*  279 */     this(StrLookup.mapLookup(paramMap), paramString1, paramString2, paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <V> StrSubstitutor(Map<String, V> paramMap, String paramString1, String paramString2, char paramChar, String paramString3)
/*      */   {
/*  296 */     this(StrLookup.mapLookup(paramMap), paramString1, paramString2, paramChar, paramString3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup<?> paramStrLookup)
/*      */   {
/*  305 */     this(paramStrLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup<?> paramStrLookup, String paramString1, String paramString2, char paramChar)
/*      */   {
/*  319 */     setVariableResolver(paramStrLookup);
/*  320 */     setVariablePrefix(paramString1);
/*  321 */     setVariableSuffix(paramString2);
/*  322 */     setEscapeChar(paramChar);
/*  323 */     setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup<?> paramStrLookup, String paramString1, String paramString2, char paramChar, String paramString3)
/*      */   {
/*  339 */     setVariableResolver(paramStrLookup);
/*  340 */     setVariablePrefix(paramString1);
/*  341 */     setVariableSuffix(paramString2);
/*  342 */     setEscapeChar(paramChar);
/*  343 */     setValueDelimiter(paramString3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup<?> paramStrLookup, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2, char paramChar)
/*      */   {
/*  358 */     this(paramStrLookup, paramStrMatcher1, paramStrMatcher2, paramChar, DEFAULT_VALUE_DELIMITER);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup<?> paramStrLookup, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2, char paramChar, StrMatcher paramStrMatcher3)
/*      */   {
/*  375 */     setVariableResolver(paramStrLookup);
/*  376 */     setVariablePrefixMatcher(paramStrMatcher1);
/*  377 */     setVariableSuffixMatcher(paramStrMatcher2);
/*  378 */     setEscapeChar(paramChar);
/*  379 */     setValueDelimiterMatcher(paramStrMatcher3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(String paramString)
/*      */   {
/*  391 */     if (paramString == null) {
/*  392 */       return null;
/*      */     }
/*  394 */     StrBuilder localStrBuilder = new StrBuilder(paramString);
/*  395 */     if (!substitute(localStrBuilder, 0, paramString.length())) {
/*  396 */       return paramString;
/*      */     }
/*  398 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  414 */     if (paramString == null) {
/*  415 */       return null;
/*      */     }
/*  417 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramString, paramInt1, paramInt2);
/*  418 */     if (!substitute(localStrBuilder, 0, paramInt2)) {
/*  419 */       return paramString.substring(paramInt1, paramInt1 + paramInt2);
/*      */     }
/*  421 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(char[] paramArrayOfChar)
/*      */   {
/*  434 */     if (paramArrayOfChar == null) {
/*  435 */       return null;
/*      */     }
/*  437 */     StrBuilder localStrBuilder = new StrBuilder(paramArrayOfChar.length).append(paramArrayOfChar);
/*  438 */     substitute(localStrBuilder, 0, paramArrayOfChar.length);
/*  439 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  456 */     if (paramArrayOfChar == null) {
/*  457 */       return null;
/*      */     }
/*  459 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramArrayOfChar, paramInt1, paramInt2);
/*  460 */     substitute(localStrBuilder, 0, paramInt2);
/*  461 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StringBuffer paramStringBuffer)
/*      */   {
/*  474 */     if (paramStringBuffer == null) {
/*  475 */       return null;
/*      */     }
/*  477 */     StrBuilder localStrBuilder = new StrBuilder(paramStringBuffer.length()).append(paramStringBuffer);
/*  478 */     substitute(localStrBuilder, 0, localStrBuilder.length());
/*  479 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  496 */     if (paramStringBuffer == null) {
/*  497 */       return null;
/*      */     }
/*  499 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
/*  500 */     substitute(localStrBuilder, 0, paramInt2);
/*  501 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(CharSequence paramCharSequence)
/*      */   {
/*  514 */     if (paramCharSequence == null) {
/*  515 */       return null;
/*      */     }
/*  517 */     return replace(paramCharSequence, 0, paramCharSequence.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*      */   {
/*  535 */     if (paramCharSequence == null) {
/*  536 */       return null;
/*      */     }
/*  538 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramCharSequence, paramInt1, paramInt2);
/*  539 */     substitute(localStrBuilder, 0, paramInt2);
/*  540 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StrBuilder paramStrBuilder)
/*      */   {
/*  553 */     if (paramStrBuilder == null) {
/*  554 */       return null;
/*      */     }
/*  556 */     StrBuilder localStrBuilder = new StrBuilder(paramStrBuilder.length()).append(paramStrBuilder);
/*  557 */     substitute(localStrBuilder, 0, localStrBuilder.length());
/*  558 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  575 */     if (paramStrBuilder == null) {
/*  576 */       return null;
/*      */     }
/*  578 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramStrBuilder, paramInt1, paramInt2);
/*  579 */     substitute(localStrBuilder, 0, paramInt2);
/*  580 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(Object paramObject)
/*      */   {
/*  593 */     if (paramObject == null) {
/*  594 */       return null;
/*      */     }
/*  596 */     StrBuilder localStrBuilder = new StrBuilder().append(paramObject);
/*  597 */     substitute(localStrBuilder, 0, localStrBuilder.length());
/*  598 */     return localStrBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StringBuffer paramStringBuffer)
/*      */   {
/*  611 */     if (paramStringBuffer == null) {
/*  612 */       return false;
/*      */     }
/*  614 */     return replaceIn(paramStringBuffer, 0, paramStringBuffer.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  631 */     if (paramStringBuffer == null) {
/*  632 */       return false;
/*      */     }
/*  634 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
/*  635 */     if (!substitute(localStrBuilder, 0, paramInt2)) {
/*  636 */       return false;
/*      */     }
/*  638 */     paramStringBuffer.replace(paramInt1, paramInt1 + paramInt2, localStrBuilder.toString());
/*  639 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StringBuilder paramStringBuilder)
/*      */   {
/*  653 */     if (paramStringBuilder == null) {
/*  654 */       return false;
/*      */     }
/*  656 */     return replaceIn(paramStringBuilder, 0, paramStringBuilder.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  674 */     if (paramStringBuilder == null) {
/*  675 */       return false;
/*      */     }
/*  677 */     StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramStringBuilder, paramInt1, paramInt2);
/*  678 */     if (!substitute(localStrBuilder, 0, paramInt2)) {
/*  679 */       return false;
/*      */     }
/*  681 */     paramStringBuilder.replace(paramInt1, paramInt1 + paramInt2, localStrBuilder.toString());
/*  682 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StrBuilder paramStrBuilder)
/*      */   {
/*  694 */     if (paramStrBuilder == null) {
/*  695 */       return false;
/*      */     }
/*  697 */     return substitute(paramStrBuilder, 0, paramStrBuilder.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  713 */     if (paramStrBuilder == null) {
/*  714 */       return false;
/*      */     }
/*  716 */     return substitute(paramStrBuilder, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean substitute(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  735 */     return substitute(paramStrBuilder, paramInt1, paramInt2, null) > 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int substitute(StrBuilder paramStrBuilder, int paramInt1, int paramInt2, List<String> paramList)
/*      */   {
/*  751 */     StrMatcher localStrMatcher1 = getVariablePrefixMatcher();
/*  752 */     StrMatcher localStrMatcher2 = getVariableSuffixMatcher();
/*  753 */     int i = getEscapeChar();
/*  754 */     StrMatcher localStrMatcher3 = getValueDelimiterMatcher();
/*  755 */     boolean bool = isEnableSubstitutionInVariables();
/*      */     
/*  757 */     int j = paramList == null ? 1 : 0;
/*  758 */     int k = 0;
/*  759 */     int m = 0;
/*  760 */     char[] arrayOfChar = paramStrBuilder.buffer;
/*  761 */     int n = paramInt1 + paramInt2;
/*  762 */     int i1 = paramInt1;
/*  763 */     while (i1 < n) {
/*  764 */       int i2 = localStrMatcher1.isMatch(arrayOfChar, i1, paramInt1, n);
/*      */       
/*  766 */       if (i2 == 0) {
/*  767 */         i1++;
/*      */ 
/*      */       }
/*  770 */       else if ((i1 > paramInt1) && (arrayOfChar[(i1 - 1)] == i))
/*      */       {
/*  772 */         paramStrBuilder.deleteCharAt(i1 - 1);
/*  773 */         arrayOfChar = paramStrBuilder.buffer;
/*  774 */         m--;
/*  775 */         k = 1;
/*  776 */         n--;
/*      */       }
/*      */       else {
/*  779 */         int i3 = i1;
/*  780 */         i1 += i2;
/*  781 */         int i4 = 0;
/*  782 */         int i5 = 0;
/*  783 */         while (i1 < n) {
/*  784 */           if ((bool) && ((i4 = localStrMatcher1.isMatch(arrayOfChar, i1, paramInt1, n)) != 0))
/*      */           {
/*      */ 
/*      */ 
/*  788 */             i5++;
/*  789 */             i1 += i4;
/*      */           }
/*      */           else
/*      */           {
/*  793 */             i4 = localStrMatcher2.isMatch(arrayOfChar, i1, paramInt1, n);
/*      */             
/*  795 */             if (i4 == 0) {
/*  796 */               i1++;
/*      */             }
/*      */             else {
/*  799 */               if (i5 == 0) {
/*  800 */                 String str1 = new String(arrayOfChar, i3 + i2, i1 - i3 - i2);
/*      */                 
/*      */ 
/*  803 */                 if (bool) {
/*  804 */                   StrBuilder localStrBuilder = new StrBuilder(str1);
/*  805 */                   substitute(localStrBuilder, 0, localStrBuilder.length());
/*  806 */                   str1 = localStrBuilder.toString();
/*      */                 }
/*  808 */                 i1 += i4;
/*  809 */                 int i6 = i1;
/*      */                 
/*  811 */                 String str2 = str1;
/*  812 */                 String str3 = null;
/*      */                 int i7;
/*  814 */                 int i8; if (localStrMatcher3 != null) {
/*  815 */                   localObject = str1.toCharArray();
/*  816 */                   i7 = 0;
/*  817 */                   for (i8 = 0; i8 < localObject.length; i8++)
/*      */                   {
/*  819 */                     if ((!bool) && (localStrMatcher1.isMatch((char[])localObject, i8, i8, localObject.length) != 0)) {
/*      */                       break;
/*      */                     }
/*      */                     
/*  823 */                     if ((i7 = localStrMatcher3.isMatch((char[])localObject, i8)) != 0) {
/*  824 */                       str2 = str1.substring(0, i8);
/*  825 */                       str3 = str1.substring(i8 + i7);
/*  826 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*  832 */                 if (paramList == null) {
/*  833 */                   paramList = new ArrayList();
/*  834 */                   paramList.add(new String(arrayOfChar, paramInt1, paramInt2));
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  839 */                 checkCyclicSubstitution(str2, paramList);
/*  840 */                 paramList.add(str2);
/*      */                 
/*      */ 
/*  843 */                 Object localObject = resolveVariable(str2, paramStrBuilder, i3, i6);
/*      */                 
/*  845 */                 if (localObject == null) {
/*  846 */                   localObject = str3;
/*      */                 }
/*  848 */                 if (localObject != null)
/*      */                 {
/*  850 */                   i7 = ((String)localObject).length();
/*  851 */                   paramStrBuilder.replace(i3, i6, (String)localObject);
/*  852 */                   k = 1;
/*  853 */                   i8 = substitute(paramStrBuilder, i3, i7, paramList);
/*      */                   
/*  855 */                   i8 = i8 + i7 - (i6 - i3);
/*      */                   
/*  857 */                   i1 += i8;
/*  858 */                   n += i8;
/*  859 */                   m += i8;
/*  860 */                   arrayOfChar = paramStrBuilder.buffer;
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  865 */                 paramList.remove(paramList.size() - 1);
/*      */                 
/*  867 */                 break;
/*      */               }
/*  869 */               i5--;
/*  870 */               i1 += i4;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  877 */     if (j != 0) {
/*  878 */       return k != 0 ? 1 : 0;
/*      */     }
/*  880 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkCyclicSubstitution(String paramString, List<String> paramList)
/*      */   {
/*  890 */     if (!paramList.contains(paramString)) {
/*  891 */       return;
/*      */     }
/*  893 */     StrBuilder localStrBuilder = new StrBuilder(256);
/*  894 */     localStrBuilder.append("Infinite loop in property interpolation of ");
/*  895 */     localStrBuilder.append((String)paramList.remove(0));
/*  896 */     localStrBuilder.append(": ");
/*  897 */     localStrBuilder.appendWithSeparators(paramList, "->");
/*  898 */     throw new IllegalStateException(localStrBuilder.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String resolveVariable(String paramString, StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  919 */     StrLookup localStrLookup = getVariableResolver();
/*  920 */     if (localStrLookup == null) {
/*  921 */       return null;
/*      */     }
/*  923 */     return localStrLookup.lookup(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char getEscapeChar()
/*      */   {
/*  934 */     return this.escapeChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEscapeChar(char paramChar)
/*      */   {
/*  945 */     this.escapeChar = paramChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getVariablePrefixMatcher()
/*      */   {
/*  960 */     return this.prefixMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariablePrefixMatcher(StrMatcher paramStrMatcher)
/*      */   {
/*  975 */     if (paramStrMatcher == null) {
/*  976 */       throw new IllegalArgumentException("Variable prefix matcher must not be null!");
/*      */     }
/*  978 */     this.prefixMatcher = paramStrMatcher;
/*  979 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariablePrefix(char paramChar)
/*      */   {
/*  993 */     return setVariablePrefixMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariablePrefix(String paramString)
/*      */   {
/* 1007 */     if (paramString == null) {
/* 1008 */       throw new IllegalArgumentException("Variable prefix must not be null!");
/*      */     }
/* 1010 */     return setVariablePrefixMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getVariableSuffixMatcher()
/*      */   {
/* 1025 */     return this.suffixMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariableSuffixMatcher(StrMatcher paramStrMatcher)
/*      */   {
/* 1040 */     if (paramStrMatcher == null) {
/* 1041 */       throw new IllegalArgumentException("Variable suffix matcher must not be null!");
/*      */     }
/* 1043 */     this.suffixMatcher = paramStrMatcher;
/* 1044 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariableSuffix(char paramChar)
/*      */   {
/* 1058 */     return setVariableSuffixMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setVariableSuffix(String paramString)
/*      */   {
/* 1072 */     if (paramString == null) {
/* 1073 */       throw new IllegalArgumentException("Variable suffix must not be null!");
/*      */     }
/* 1075 */     return setVariableSuffixMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getValueDelimiterMatcher()
/*      */   {
/* 1093 */     return this.valueDelimiterMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setValueDelimiterMatcher(StrMatcher paramStrMatcher)
/*      */   {
/* 1111 */     this.valueDelimiterMatcher = paramStrMatcher;
/* 1112 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setValueDelimiter(char paramChar)
/*      */   {
/* 1127 */     return setValueDelimiterMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor setValueDelimiter(String paramString)
/*      */   {
/* 1145 */     if (StringUtils.isEmpty(paramString)) {
/* 1146 */       setValueDelimiterMatcher(null);
/* 1147 */       return this;
/*      */     }
/* 1149 */     return setValueDelimiterMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrLookup<?> getVariableResolver()
/*      */   {
/* 1160 */     return this.variableResolver;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVariableResolver(StrLookup<?> paramStrLookup)
/*      */   {
/* 1169 */     this.variableResolver = paramStrLookup;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnableSubstitutionInVariables()
/*      */   {
/* 1181 */     return this.enableSubstitutionInVariables;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEnableSubstitutionInVariables(boolean paramBoolean)
/*      */   {
/* 1195 */     this.enableSubstitutionInVariables = paramBoolean;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\StrSubstitutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */