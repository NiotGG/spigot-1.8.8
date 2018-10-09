/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import org.apache.commons.lang3.ArrayUtils;
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
/*      */ public class StrTokenizer
/*      */   implements ListIterator<String>, Cloneable
/*      */ {
/*   93 */   private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
/*   94 */   static { CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
/*   95 */     CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
/*   96 */     CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
/*   97 */     CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
/*   98 */     CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
/*   99 */     CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
/*      */     
/*  101 */     TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
/*  102 */     TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
/*  103 */     TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
/*  104 */     TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
/*  105 */     TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
/*  106 */     TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
/*  107 */     TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
/*      */   }
/*      */   
/*      */ 
/*      */   private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
/*      */   
/*      */   private char[] chars;
/*      */   
/*      */   private String[] tokens;
/*      */   
/*      */   private int tokenPos;
/*  118 */   private StrMatcher delimMatcher = StrMatcher.splitMatcher();
/*      */   
/*  120 */   private StrMatcher quoteMatcher = StrMatcher.noneMatcher();
/*      */   
/*  122 */   private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();
/*      */   
/*  124 */   private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();
/*      */   
/*      */ 
/*  127 */   private boolean emptyAsNull = false;
/*      */   
/*  129 */   private boolean ignoreEmptyTokens = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static StrTokenizer getCSVClone()
/*      */   {
/*  139 */     return (StrTokenizer)CSV_TOKENIZER_PROTOTYPE.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getCSVInstance()
/*      */   {
/*  152 */     return getCSVClone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getCSVInstance(String paramString)
/*      */   {
/*  165 */     StrTokenizer localStrTokenizer = getCSVClone();
/*  166 */     localStrTokenizer.reset(paramString);
/*  167 */     return localStrTokenizer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getCSVInstance(char[] paramArrayOfChar)
/*      */   {
/*  180 */     StrTokenizer localStrTokenizer = getCSVClone();
/*  181 */     localStrTokenizer.reset(paramArrayOfChar);
/*  182 */     return localStrTokenizer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static StrTokenizer getTSVClone()
/*      */   {
/*  191 */     return (StrTokenizer)TSV_TOKENIZER_PROTOTYPE.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getTSVInstance()
/*      */   {
/*  204 */     return getTSVClone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getTSVInstance(String paramString)
/*      */   {
/*  215 */     StrTokenizer localStrTokenizer = getTSVClone();
/*  216 */     localStrTokenizer.reset(paramString);
/*  217 */     return localStrTokenizer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static StrTokenizer getTSVInstance(char[] paramArrayOfChar)
/*      */   {
/*  228 */     StrTokenizer localStrTokenizer = getTSVClone();
/*  229 */     localStrTokenizer.reset(paramArrayOfChar);
/*  230 */     return localStrTokenizer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer()
/*      */   {
/*  242 */     this.chars = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString)
/*      */   {
/*  253 */     if (paramString != null) {
/*  254 */       this.chars = paramString.toCharArray();
/*      */     } else {
/*  256 */       this.chars = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString, char paramChar)
/*      */   {
/*  267 */     this(paramString);
/*  268 */     setDelimiterChar(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString1, String paramString2)
/*      */   {
/*  278 */     this(paramString1);
/*  279 */     setDelimiterString(paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString, StrMatcher paramStrMatcher)
/*      */   {
/*  289 */     this(paramString);
/*  290 */     setDelimiterMatcher(paramStrMatcher);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString, char paramChar1, char paramChar2)
/*      */   {
/*  302 */     this(paramString, paramChar1);
/*  303 */     setQuoteChar(paramChar2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(String paramString, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2)
/*      */   {
/*  315 */     this(paramString, paramStrMatcher1);
/*  316 */     setQuoteMatcher(paramStrMatcher2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar)
/*      */   {
/*  327 */     this.chars = ArrayUtils.clone(paramArrayOfChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar, char paramChar)
/*      */   {
/*  337 */     this(paramArrayOfChar);
/*  338 */     setDelimiterChar(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar, String paramString)
/*      */   {
/*  348 */     this(paramArrayOfChar);
/*  349 */     setDelimiterString(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar, StrMatcher paramStrMatcher)
/*      */   {
/*  359 */     this(paramArrayOfChar);
/*  360 */     setDelimiterMatcher(paramStrMatcher);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar, char paramChar1, char paramChar2)
/*      */   {
/*  372 */     this(paramArrayOfChar, paramChar1);
/*  373 */     setQuoteChar(paramChar2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer(char[] paramArrayOfChar, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2)
/*      */   {
/*  385 */     this(paramArrayOfChar, paramStrMatcher1);
/*  386 */     setQuoteMatcher(paramStrMatcher2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  397 */     checkTokenized();
/*  398 */     return this.tokens.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String nextToken()
/*      */   {
/*  409 */     if (hasNext()) {
/*  410 */       return this.tokens[(this.tokenPos++)];
/*      */     }
/*  412 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String previousToken()
/*      */   {
/*  421 */     if (hasPrevious()) {
/*  422 */       return this.tokens[(--this.tokenPos)];
/*      */     }
/*  424 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getTokenArray()
/*      */   {
/*  433 */     checkTokenized();
/*  434 */     return (String[])this.tokens.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getTokenList()
/*      */   {
/*  443 */     checkTokenized();
/*  444 */     ArrayList localArrayList = new ArrayList(this.tokens.length);
/*  445 */     for (String str : this.tokens) {
/*  446 */       localArrayList.add(str);
/*      */     }
/*  448 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer reset()
/*      */   {
/*  459 */     this.tokenPos = 0;
/*  460 */     this.tokens = null;
/*  461 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer reset(String paramString)
/*      */   {
/*  473 */     reset();
/*  474 */     if (paramString != null) {
/*  475 */       this.chars = paramString.toCharArray();
/*      */     } else {
/*  477 */       this.chars = null;
/*      */     }
/*  479 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer reset(char[] paramArrayOfChar)
/*      */   {
/*  491 */     reset();
/*  492 */     this.chars = ArrayUtils.clone(paramArrayOfChar);
/*  493 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasNext()
/*      */   {
/*  505 */     checkTokenized();
/*  506 */     return this.tokenPos < this.tokens.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String next()
/*      */   {
/*  517 */     if (hasNext()) {
/*  518 */       return this.tokens[(this.tokenPos++)];
/*      */     }
/*  520 */     throw new NoSuchElementException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nextIndex()
/*      */   {
/*  530 */     return this.tokenPos;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasPrevious()
/*      */   {
/*  540 */     checkTokenized();
/*  541 */     return this.tokenPos > 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String previous()
/*      */   {
/*  551 */     if (hasPrevious()) {
/*  552 */       return this.tokens[(--this.tokenPos)];
/*      */     }
/*  554 */     throw new NoSuchElementException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int previousIndex()
/*      */   {
/*  564 */     return this.tokenPos - 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void remove()
/*      */   {
/*  574 */     throw new UnsupportedOperationException("remove() is unsupported");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void set(String paramString)
/*      */   {
/*  584 */     throw new UnsupportedOperationException("set() is unsupported");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void add(String paramString)
/*      */   {
/*  594 */     throw new UnsupportedOperationException("add() is unsupported");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkTokenized()
/*      */   {
/*  603 */     if (this.tokens == null) { List localList;
/*  604 */       if (this.chars == null)
/*      */       {
/*  606 */         localList = tokenize(null, 0, 0);
/*  607 */         this.tokens = ((String[])localList.toArray(new String[localList.size()]));
/*      */       } else {
/*  609 */         localList = tokenize(this.chars, 0, this.chars.length);
/*  610 */         this.tokens = ((String[])localList.toArray(new String[localList.size()]));
/*      */       }
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
/*      */ 
/*      */   protected List<String> tokenize(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  636 */     if ((paramArrayOfChar == null) || (paramInt2 == 0)) {
/*  637 */       return Collections.emptyList();
/*      */     }
/*  639 */     StrBuilder localStrBuilder = new StrBuilder();
/*  640 */     ArrayList localArrayList = new ArrayList();
/*  641 */     int i = paramInt1;
/*      */     
/*      */ 
/*  644 */     while ((i >= 0) && (i < paramInt2))
/*      */     {
/*  646 */       i = readNextToken(paramArrayOfChar, i, paramInt2, localStrBuilder, localArrayList);
/*      */       
/*      */ 
/*  649 */       if (i >= paramInt2) {
/*  650 */         addToken(localArrayList, "");
/*      */       }
/*      */     }
/*  653 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addToken(List<String> paramList, String paramString)
/*      */   {
/*  663 */     if (StringUtils.isEmpty(paramString)) {
/*  664 */       if (isIgnoreEmptyTokens()) {
/*  665 */         return;
/*      */       }
/*  667 */       if (isEmptyTokenAsNull()) {
/*  668 */         paramString = null;
/*      */       }
/*      */     }
/*  671 */     paramList.add(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int readNextToken(char[] paramArrayOfChar, int paramInt1, int paramInt2, StrBuilder paramStrBuilder, List<String> paramList)
/*      */   {
/*  688 */     while (paramInt1 < paramInt2) {
/*  689 */       i = Math.max(getIgnoredMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2), getTrimmerMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2));
/*      */       
/*      */ 
/*  692 */       if ((i == 0) || (getDelimiterMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2) > 0) || (getQuoteMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2) > 0)) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*  697 */       paramInt1 += i;
/*      */     }
/*      */     
/*      */ 
/*  701 */     if (paramInt1 >= paramInt2) {
/*  702 */       addToken(paramList, "");
/*  703 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*  707 */     int i = getDelimiterMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2);
/*  708 */     if (i > 0) {
/*  709 */       addToken(paramList, "");
/*  710 */       return paramInt1 + i;
/*      */     }
/*      */     
/*      */ 
/*  714 */     int j = getQuoteMatcher().isMatch(paramArrayOfChar, paramInt1, paramInt1, paramInt2);
/*  715 */     if (j > 0) {
/*  716 */       return readWithQuotes(paramArrayOfChar, paramInt1 + j, paramInt2, paramStrBuilder, paramList, paramInt1, j);
/*      */     }
/*  718 */     return readWithQuotes(paramArrayOfChar, paramInt1, paramInt2, paramStrBuilder, paramList, 0, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int readWithQuotes(char[] paramArrayOfChar, int paramInt1, int paramInt2, StrBuilder paramStrBuilder, List<String> paramList, int paramInt3, int paramInt4)
/*      */   {
/*  739 */     paramStrBuilder.clear();
/*  740 */     int i = paramInt1;
/*  741 */     int j = paramInt4 > 0 ? 1 : 0;
/*  742 */     int k = 0;
/*      */     
/*  744 */     while (i < paramInt2)
/*      */     {
/*      */ 
/*      */ 
/*  748 */       if (j != 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  755 */         if (isQuote(paramArrayOfChar, i, paramInt2, paramInt3, paramInt4)) {
/*  756 */           if (isQuote(paramArrayOfChar, i + paramInt4, paramInt2, paramInt3, paramInt4))
/*      */           {
/*  758 */             paramStrBuilder.append(paramArrayOfChar, i, paramInt4);
/*  759 */             i += paramInt4 * 2;
/*  760 */             k = paramStrBuilder.size();
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  765 */             j = 0;
/*  766 */             i += paramInt4;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  771 */           paramStrBuilder.append(paramArrayOfChar[(i++)]);
/*  772 */           k = paramStrBuilder.size();
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  778 */         int m = getDelimiterMatcher().isMatch(paramArrayOfChar, i, paramInt1, paramInt2);
/*  779 */         if (m > 0)
/*      */         {
/*  781 */           addToken(paramList, paramStrBuilder.substring(0, k));
/*  782 */           return i + m;
/*      */         }
/*      */         
/*      */ 
/*  786 */         if ((paramInt4 > 0) && (isQuote(paramArrayOfChar, i, paramInt2, paramInt3, paramInt4))) {
/*  787 */           j = 1;
/*  788 */           i += paramInt4;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  793 */           int n = getIgnoredMatcher().isMatch(paramArrayOfChar, i, paramInt1, paramInt2);
/*  794 */           if (n > 0) {
/*  795 */             i += n;
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  802 */             int i1 = getTrimmerMatcher().isMatch(paramArrayOfChar, i, paramInt1, paramInt2);
/*  803 */             if (i1 > 0) {
/*  804 */               paramStrBuilder.append(paramArrayOfChar, i, i1);
/*  805 */               i += i1;
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  810 */               paramStrBuilder.append(paramArrayOfChar[(i++)]);
/*  811 */               k = paramStrBuilder.size();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/*  816 */     addToken(paramList, paramStrBuilder.substring(0, k));
/*  817 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isQuote(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  832 */     for (int i = 0; i < paramInt4; i++) {
/*  833 */       if ((paramInt1 + i >= paramInt2) || (paramArrayOfChar[(paramInt1 + i)] != paramArrayOfChar[(paramInt3 + i)])) {
/*  834 */         return false;
/*      */       }
/*      */     }
/*  837 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getDelimiterMatcher()
/*      */   {
/*  848 */     return this.delimMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setDelimiterMatcher(StrMatcher paramStrMatcher)
/*      */   {
/*  860 */     if (paramStrMatcher == null) {
/*  861 */       this.delimMatcher = StrMatcher.noneMatcher();
/*      */     } else {
/*  863 */       this.delimMatcher = paramStrMatcher;
/*      */     }
/*  865 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setDelimiterChar(char paramChar)
/*      */   {
/*  875 */     return setDelimiterMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setDelimiterString(String paramString)
/*      */   {
/*  885 */     return setDelimiterMatcher(StrMatcher.stringMatcher(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getQuoteMatcher()
/*      */   {
/*  900 */     return this.quoteMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setQuoteMatcher(StrMatcher paramStrMatcher)
/*      */   {
/*  913 */     if (paramStrMatcher != null) {
/*  914 */       this.quoteMatcher = paramStrMatcher;
/*      */     }
/*  916 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setQuoteChar(char paramChar)
/*      */   {
/*  929 */     return setQuoteMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getIgnoredMatcher()
/*      */   {
/*  944 */     return this.ignoredMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setIgnoredMatcher(StrMatcher paramStrMatcher)
/*      */   {
/*  957 */     if (paramStrMatcher != null) {
/*  958 */       this.ignoredMatcher = paramStrMatcher;
/*      */     }
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
/*      */   public StrTokenizer setIgnoredChar(char paramChar)
/*      */   {
/*  973 */     return setIgnoredMatcher(StrMatcher.charMatcher(paramChar));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrMatcher getTrimmerMatcher()
/*      */   {
/*  988 */     return this.trimmerMatcher;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setTrimmerMatcher(StrMatcher paramStrMatcher)
/*      */   {
/* 1001 */     if (paramStrMatcher != null) {
/* 1002 */       this.trimmerMatcher = paramStrMatcher;
/*      */     }
/* 1004 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmptyTokenAsNull()
/*      */   {
/* 1015 */     return this.emptyAsNull;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setEmptyTokenAsNull(boolean paramBoolean)
/*      */   {
/* 1026 */     this.emptyAsNull = paramBoolean;
/* 1027 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isIgnoreEmptyTokens()
/*      */   {
/* 1038 */     return this.ignoreEmptyTokens;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StrTokenizer setIgnoreEmptyTokens(boolean paramBoolean)
/*      */   {
/* 1049 */     this.ignoreEmptyTokens = paramBoolean;
/* 1050 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getContent()
/*      */   {
/* 1060 */     if (this.chars == null) {
/* 1061 */       return null;
/*      */     }
/* 1063 */     return new String(this.chars);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/* 1077 */       return cloneReset();
/*      */     } catch (CloneNotSupportedException localCloneNotSupportedException) {}
/* 1079 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   Object cloneReset()
/*      */     throws CloneNotSupportedException
/*      */   {
/* 1092 */     StrTokenizer localStrTokenizer = (StrTokenizer)super.clone();
/* 1093 */     if (localStrTokenizer.chars != null) {
/* 1094 */       localStrTokenizer.chars = ((char[])localStrTokenizer.chars.clone());
/*      */     }
/* 1096 */     localStrTokenizer.reset();
/* 1097 */     return localStrTokenizer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1108 */     if (this.tokens == null) {
/* 1109 */       return "StrTokenizer[not tokenized yet]";
/*      */     }
/* 1111 */     return "StrTokenizer" + getTokenList();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\StrTokenizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */