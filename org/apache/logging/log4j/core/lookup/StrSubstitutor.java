/*      */ package org.apache.logging.log4j.core.lookup;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import org.apache.logging.log4j.core.LogEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  113 */   public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
/*      */   
/*      */ 
/*      */ 
/*  117 */   public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
/*      */   
/*      */ 
/*      */ 
/*      */   private static final int BUF_SIZE = 256;
/*      */   
/*      */ 
/*      */ 
/*      */   private char escapeChar;
/*      */   
/*      */ 
/*      */ 
/*      */   private StrMatcher prefixMatcher;
/*      */   
/*      */ 
/*      */ 
/*      */   private StrMatcher suffixMatcher;
/*      */   
/*      */ 
/*      */ 
/*      */   private StrLookup variableResolver;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean enableSubstitutionInVariables;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor()
/*      */   {
/*  148 */     this(null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(Map<String, String> paramMap)
/*      */   {
/*  157 */     this(new MapLookup(paramMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(Map<String, String> paramMap, String paramString1, String paramString2)
/*      */   {
/*  169 */     this(new MapLookup(paramMap), paramString1, paramString2, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(Map<String, String> paramMap, String paramString1, String paramString2, char paramChar)
/*      */   {
/*  183 */     this(new MapLookup(paramMap), paramString1, paramString2, paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup paramStrLookup)
/*      */   {
/*  192 */     this(paramStrLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup paramStrLookup, String paramString1, String paramString2, char paramChar)
/*      */   {
/*  206 */     setVariableResolver(paramStrLookup);
/*  207 */     setVariablePrefix(paramString1);
/*  208 */     setVariableSuffix(paramString2);
/*  209 */     setEscapeChar(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrSubstitutor(StrLookup paramStrLookup, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2, char paramChar)
/*      */   {
/*  224 */     setVariableResolver(paramStrLookup);
/*  225 */     setVariablePrefixMatcher(paramStrMatcher1);
/*  226 */     setVariableSuffixMatcher(paramStrMatcher2);
/*  227 */     setEscapeChar(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String replace(Object paramObject, Map<String, String> paramMap)
/*      */   {
/*  239 */     return new StrSubstitutor(paramMap).replace(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String replace(Object paramObject, Map<String, String> paramMap, String paramString1, String paramString2)
/*      */   {
/*  256 */     return new StrSubstitutor(paramMap, paramString1, paramString2).replace(paramObject);
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
/*  268 */     if (paramProperties == null) {
/*  269 */       return paramObject.toString();
/*      */     }
/*  271 */     HashMap localHashMap = new HashMap();
/*  272 */     Enumeration localEnumeration = paramProperties.propertyNames();
/*  273 */     while (localEnumeration.hasMoreElements()) {
/*  274 */       String str1 = (String)localEnumeration.nextElement();
/*  275 */       String str2 = paramProperties.getProperty(str1);
/*  276 */       localHashMap.put(str1, str2);
/*      */     }
/*  278 */     return replace(paramObject, localHashMap);
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
/*  290 */     return replace(null, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, String paramString)
/*      */   {
/*  302 */     if (paramString == null) {
/*  303 */       return null;
/*      */     }
/*  305 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/*  306 */     if (!substitute(paramLogEvent, localStringBuilder, 0, paramString.length())) {
/*  307 */       return paramString;
/*      */     }
/*  309 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
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
/*  325 */     return replace(null, paramString, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  342 */     if (paramString == null) {
/*  343 */       return null;
/*      */     }
/*  345 */     StringBuilder localStringBuilder = new StringBuilder(paramInt2).append(paramString, paramInt1, paramInt2);
/*  346 */     if (!substitute(paramLogEvent, localStringBuilder, 0, paramInt2)) {
/*  347 */       return paramString.substring(paramInt1, paramInt1 + paramInt2);
/*      */     }
/*  349 */     return localStringBuilder.toString();
/*      */   }
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
/*  362 */     return replace(null, paramArrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, char[] paramArrayOfChar)
/*      */   {
/*  376 */     if (paramArrayOfChar == null) {
/*  377 */       return null;
/*      */     }
/*  379 */     StringBuilder localStringBuilder = new StringBuilder(paramArrayOfChar.length).append(paramArrayOfChar);
/*  380 */     substitute(paramLogEvent, localStringBuilder, 0, paramArrayOfChar.length);
/*  381 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
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
/*  398 */     return replace(null, paramArrayOfChar, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  416 */     if (paramArrayOfChar == null) {
/*  417 */       return null;
/*      */     }
/*  419 */     StringBuilder localStringBuilder = new StringBuilder(paramInt2).append(paramArrayOfChar, paramInt1, paramInt2);
/*  420 */     substitute(paramLogEvent, localStringBuilder, 0, paramInt2);
/*  421 */     return localStringBuilder.toString();
/*      */   }
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
/*  434 */     return replace(null, paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, StringBuffer paramStringBuffer)
/*      */   {
/*  448 */     if (paramStringBuffer == null) {
/*  449 */       return null;
/*      */     }
/*  451 */     StringBuilder localStringBuilder = new StringBuilder(paramStringBuffer.length()).append(paramStringBuffer);
/*  452 */     substitute(paramLogEvent, localStringBuilder, 0, localStringBuilder.length());
/*  453 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
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
/*  470 */     return replace(null, paramStringBuffer, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  488 */     if (paramStringBuffer == null) {
/*  489 */       return null;
/*      */     }
/*  491 */     StringBuilder localStringBuilder = new StringBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
/*  492 */     substitute(paramLogEvent, localStringBuilder, 0, paramInt2);
/*  493 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StringBuilder paramStringBuilder)
/*      */   {
/*  506 */     return replace(null, paramStringBuilder);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*      */   {
/*  520 */     if (paramStringBuilder == null) {
/*  521 */       return null;
/*      */     }
/*  523 */     StringBuilder localStringBuilder = new StringBuilder(paramStringBuilder.length()).append(paramStringBuilder);
/*  524 */     substitute(paramLogEvent, localStringBuilder, 0, localStringBuilder.length());
/*  525 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  541 */     return replace(null, paramStringBuilder, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  559 */     if (paramStringBuilder == null) {
/*  560 */       return null;
/*      */     }
/*  562 */     StringBuilder localStringBuilder = new StringBuilder(paramInt2).append(paramStringBuilder, paramInt1, paramInt2);
/*  563 */     substitute(paramLogEvent, localStringBuilder, 0, paramInt2);
/*  564 */     return localStringBuilder.toString();
/*      */   }
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
/*  577 */     return replace(null, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String replace(LogEvent paramLogEvent, Object paramObject)
/*      */   {
/*  590 */     if (paramObject == null) {
/*  591 */       return null;
/*      */     }
/*  593 */     StringBuilder localStringBuilder = new StringBuilder().append(paramObject);
/*  594 */     substitute(paramLogEvent, localStringBuilder, 0, localStringBuilder.length());
/*  595 */     return localStringBuilder.toString();
/*      */   }
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
/*  608 */     if (paramStringBuffer == null) {
/*  609 */       return false;
/*      */     }
/*  611 */     return replaceIn(paramStringBuffer, 0, paramStringBuffer.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
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
/*  628 */     return replaceIn(null, paramStringBuffer, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(LogEvent paramLogEvent, StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*      */   {
/*  646 */     if (paramStringBuffer == null) {
/*  647 */       return false;
/*      */     }
/*  649 */     StringBuilder localStringBuilder = new StringBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
/*  650 */     if (!substitute(paramLogEvent, localStringBuilder, 0, paramInt2)) {
/*  651 */       return false;
/*      */     }
/*  653 */     paramStringBuffer.replace(paramInt1, paramInt1 + paramInt2, localStringBuilder.toString());
/*  654 */     return true;
/*      */   }
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
/*  666 */     return replaceIn(null, paramStringBuilder);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*      */   {
/*  679 */     if (paramStringBuilder == null) {
/*  680 */       return false;
/*      */     }
/*  682 */     return substitute(paramLogEvent, paramStringBuilder, 0, paramStringBuilder.length());
/*      */   }
/*      */   
/*      */ 
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
/*  697 */     return replaceIn(null, paramStringBuilder, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replaceIn(LogEvent paramLogEvent, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  714 */     if (paramStringBuilder == null) {
/*  715 */       return false;
/*      */     }
/*  717 */     return substitute(paramLogEvent, paramStringBuilder, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean substitute(LogEvent paramLogEvent, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  737 */     return substitute(paramLogEvent, paramStringBuilder, paramInt1, paramInt2, null) > 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int substitute(LogEvent paramLogEvent, StringBuilder paramStringBuilder, int paramInt1, int paramInt2, List<String> paramList)
/*      */   {
/*  755 */     StrMatcher localStrMatcher1 = getVariablePrefixMatcher();
/*  756 */     StrMatcher localStrMatcher2 = getVariableSuffixMatcher();
/*  757 */     int i = getEscapeChar();
/*      */     
/*  759 */     int j = paramList == null ? 1 : 0;
/*  760 */     int k = 0;
/*  761 */     int m = 0;
/*  762 */     char[] arrayOfChar = getChars(paramStringBuilder);
/*  763 */     int n = paramInt1 + paramInt2;
/*  764 */     int i1 = paramInt1;
/*  765 */     while (i1 < n) {
/*  766 */       int i2 = localStrMatcher1.isMatch(arrayOfChar, i1, paramInt1, n);
/*      */       
/*  768 */       if (i2 == 0) {
/*  769 */         i1++;
/*      */ 
/*      */       }
/*  772 */       else if ((i1 > paramInt1) && (arrayOfChar[(i1 - 1)] == i))
/*      */       {
/*  774 */         paramStringBuilder.deleteCharAt(i1 - 1);
/*  775 */         arrayOfChar = getChars(paramStringBuilder);
/*  776 */         m--;
/*  777 */         k = 1;
/*  778 */         n--;
/*      */       }
/*      */       else {
/*  781 */         int i3 = i1;
/*  782 */         i1 += i2;
/*  783 */         int i4 = 0;
/*  784 */         int i5 = 0;
/*  785 */         while (i1 < n) {
/*  786 */           if ((isEnableSubstitutionInVariables()) && ((i4 = localStrMatcher1.isMatch(arrayOfChar, i1, paramInt1, n)) != 0))
/*      */           {
/*      */ 
/*      */ 
/*  790 */             i5++;
/*  791 */             i1 += i4;
/*      */           }
/*      */           else
/*      */           {
/*  795 */             i4 = localStrMatcher2.isMatch(arrayOfChar, i1, paramInt1, n);
/*      */             
/*  797 */             if (i4 == 0) {
/*  798 */               i1++;
/*      */             }
/*      */             else {
/*  801 */               if (i5 == 0) {
/*  802 */                 String str1 = new String(arrayOfChar, i3 + i2, i1 - i3 - i2);
/*      */                 
/*      */ 
/*  805 */                 if (isEnableSubstitutionInVariables()) {
/*  806 */                   StringBuilder localStringBuilder = new StringBuilder(str1);
/*  807 */                   substitute(paramLogEvent, localStringBuilder, 0, localStringBuilder.length());
/*  808 */                   str1 = localStringBuilder.toString();
/*      */                 }
/*  810 */                 i1 += i4;
/*  811 */                 int i6 = i1;
/*      */                 
/*      */ 
/*  814 */                 if (paramList == null) {
/*  815 */                   paramList = new ArrayList();
/*  816 */                   paramList.add(new String(arrayOfChar, paramInt1, paramInt2));
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  821 */                 checkCyclicSubstitution(str1, paramList);
/*  822 */                 paramList.add(str1);
/*      */                 
/*      */ 
/*  825 */                 String str2 = resolveVariable(paramLogEvent, str1, paramStringBuilder, i3, i6);
/*      */                 
/*  827 */                 if (str2 != null)
/*      */                 {
/*  829 */                   int i7 = str2.length();
/*  830 */                   paramStringBuilder.replace(i3, i6, str2);
/*  831 */                   k = 1;
/*  832 */                   int i8 = substitute(paramLogEvent, paramStringBuilder, i3, i7, paramList);
/*      */                   
/*  834 */                   i8 += i7 - (i6 - i3);
/*      */                   
/*  836 */                   i1 += i8;
/*  837 */                   n += i8;
/*  838 */                   m += i8;
/*  839 */                   arrayOfChar = getChars(paramStringBuilder);
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  844 */                 paramList.remove(paramList.size() - 1);
/*      */                 
/*  846 */                 break;
/*      */               }
/*  848 */               i5--;
/*  849 */               i1 += i4;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  856 */     if (j != 0) {
/*  857 */       return k != 0 ? 1 : 0;
/*      */     }
/*  859 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkCyclicSubstitution(String paramString, List<String> paramList)
/*      */   {
/*  869 */     if (!paramList.contains(paramString)) {
/*  870 */       return;
/*      */     }
/*  872 */     StringBuilder localStringBuilder = new StringBuilder(256);
/*  873 */     localStringBuilder.append("Infinite loop in property interpolation of ");
/*  874 */     localStringBuilder.append((String)paramList.remove(0));
/*  875 */     localStringBuilder.append(": ");
/*  876 */     appendWithSeparators(localStringBuilder, paramList, "->");
/*  877 */     throw new IllegalStateException(localStringBuilder.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String resolveVariable(LogEvent paramLogEvent, String paramString, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/*  900 */     StrLookup localStrLookup = getVariableResolver();
/*  901 */     if (localStrLookup == null) {
/*  902 */       return null;
/*      */     }
/*  904 */     return localStrLookup.lookup(paramLogEvent, paramString);
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
/*  915 */     return this.escapeChar;
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
/*  926 */     this.escapeChar = paramChar;
/*      */   }
/*      */   
/*      */ 
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
/*  941 */     return this.prefixMatcher;
/*      */   }
/*      */   
/*      */ 
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
/*  956 */     if (paramStrMatcher == null) {
/*  957 */       throw new IllegalArgumentException("Variable prefix matcher must not be null!");
/*      */     }
/*  959 */     this.prefixMatcher = paramStrMatcher;
/*  960 */     return this;
/*      */   }
/*      */   
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
/*  974 */     return setVariablePrefixMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
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
/*  988 */     if (paramString == null) {
/*  989 */       throw new IllegalArgumentException("Variable prefix must not be null!");
/*      */     }
/*  991 */     return setVariablePrefixMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
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
/* 1006 */     return this.suffixMatcher;
/*      */   }
/*      */   
/*      */ 
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
/* 1021 */     if (paramStrMatcher == null) {
/* 1022 */       throw new IllegalArgumentException("Variable suffix matcher must not be null!");
/*      */     }
/* 1024 */     this.suffixMatcher = paramStrMatcher;
/* 1025 */     return this;
/*      */   }
/*      */   
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
/* 1039 */     return setVariableSuffixMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
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
/* 1053 */     if (paramString == null) {
/* 1054 */       throw new IllegalArgumentException("Variable suffix must not be null!");
/*      */     }
/* 1056 */     return setVariableSuffixMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrLookup getVariableResolver()
/*      */   {
/* 1067 */     return this.variableResolver;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVariableResolver(StrLookup paramStrLookup)
/*      */   {
/* 1076 */     this.variableResolver = paramStrLookup;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnableSubstitutionInVariables()
/*      */   {
/* 1087 */     return this.enableSubstitutionInVariables;
/*      */   }
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
/* 1099 */     this.enableSubstitutionInVariables = paramBoolean;
/*      */   }
/*      */   
/*      */   private char[] getChars(StringBuilder paramStringBuilder) {
/* 1103 */     char[] arrayOfChar = new char[paramStringBuilder.length()];
/* 1104 */     paramStringBuilder.getChars(0, paramStringBuilder.length(), arrayOfChar, 0);
/* 1105 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void appendWithSeparators(StringBuilder paramStringBuilder, Iterable<?> paramIterable, String paramString)
/*      */   {
/* 1118 */     if (paramIterable != null) {
/* 1119 */       paramString = paramString == null ? "" : paramString;
/* 1120 */       Iterator localIterator = paramIterable.iterator();
/* 1121 */       while (localIterator.hasNext()) {
/* 1122 */         paramStringBuilder.append(localIterator.next());
/* 1123 */         if (localIterator.hasNext()) {
/* 1124 */           paramStringBuilder.append(paramString);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1132 */     return "StrSubstitutor(" + this.variableResolver.toString() + ")";
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\StrSubstitutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */