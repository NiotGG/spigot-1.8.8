/*     */ package org.apache.commons.codec.language.bm;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Scanner;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rule
/*     */ {
/*     */   public static final class Phoneme
/*     */     implements Rule.PhonemeExpr
/*     */   {
/*  85 */     public static final Comparator<Phoneme> COMPARATOR = new Comparator()
/*     */     {
/*     */       public int compare(Rule.Phoneme paramAnonymousPhoneme1, Rule.Phoneme paramAnonymousPhoneme2) {
/*  88 */         for (int i = 0; i < paramAnonymousPhoneme1.phonemeText.length(); i++) {
/*  89 */           if (i >= paramAnonymousPhoneme2.phonemeText.length()) {
/*  90 */             return 1;
/*     */           }
/*  92 */           int j = paramAnonymousPhoneme1.phonemeText.charAt(i) - paramAnonymousPhoneme2.phonemeText.charAt(i);
/*  93 */           if (j != 0) {
/*  94 */             return j;
/*     */           }
/*     */         }
/*     */         
/*  98 */         if (paramAnonymousPhoneme1.phonemeText.length() < paramAnonymousPhoneme2.phonemeText.length()) {
/*  99 */           return -1;
/*     */         }
/*     */         
/* 102 */         return 0;
/*     */       }
/*     */     };
/*     */     private final StringBuilder phonemeText;
/*     */     private final Languages.LanguageSet languages;
/*     */     
/*     */     public Phoneme(CharSequence paramCharSequence, Languages.LanguageSet paramLanguageSet)
/*     */     {
/* 110 */       this.phonemeText = new StringBuilder(paramCharSequence);
/* 111 */       this.languages = paramLanguageSet;
/*     */     }
/*     */     
/*     */     public Phoneme(Phoneme paramPhoneme1, Phoneme paramPhoneme2) {
/* 115 */       this(paramPhoneme1.phonemeText, paramPhoneme1.languages);
/* 116 */       this.phonemeText.append(paramPhoneme2.phonemeText);
/*     */     }
/*     */     
/*     */     public Phoneme(Phoneme paramPhoneme1, Phoneme paramPhoneme2, Languages.LanguageSet paramLanguageSet) {
/* 120 */       this(paramPhoneme1.phonemeText, paramLanguageSet);
/* 121 */       this.phonemeText.append(paramPhoneme2.phonemeText);
/*     */     }
/*     */     
/*     */     public Phoneme append(CharSequence paramCharSequence) {
/* 125 */       this.phonemeText.append(paramCharSequence);
/* 126 */       return this;
/*     */     }
/*     */     
/*     */     public Languages.LanguageSet getLanguages() {
/* 130 */       return this.languages;
/*     */     }
/*     */     
/*     */     public Iterable<Phoneme> getPhonemes()
/*     */     {
/* 135 */       return Collections.singleton(this);
/*     */     }
/*     */     
/*     */     public CharSequence getPhonemeText() {
/* 139 */       return this.phonemeText;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     @Deprecated
/*     */     public Phoneme join(Phoneme paramPhoneme)
/*     */     {
/* 147 */       return new Phoneme(this.phonemeText.toString() + paramPhoneme.phonemeText.toString(), this.languages.restrictTo(paramPhoneme.languages));
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract interface PhonemeExpr
/*     */   {
/*     */     public abstract Iterable<Rule.Phoneme> getPhonemes();
/*     */   }
/*     */   
/*     */   public static final class PhonemeList implements Rule.PhonemeExpr {
/*     */     private final List<Rule.Phoneme> phonemes;
/*     */     
/*     */     public PhonemeList(List<Rule.Phoneme> paramList) {
/* 160 */       this.phonemes = paramList;
/*     */     }
/*     */     
/*     */     public List<Rule.Phoneme> getPhonemes()
/*     */     {
/* 165 */       return this.phonemes;
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
/* 176 */   public static final RPattern ALL_STRINGS_RMATCHER = new RPattern()
/*     */   {
/*     */     public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 179 */       return true;
/*     */     }
/*     */   };
/*     */   
/*     */   public static final String ALL = "ALL";
/*     */   
/*     */   private static final String DOUBLE_QUOTE = "\"";
/*     */   
/*     */   private static final String HASH_INCLUDE = "#include";
/*     */   
/* 189 */   private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES = new EnumMap(NameType.class);
/*     */   private final RPattern lContext;
/*     */   private final String pattern;
/*     */   
/* 193 */   static { for (NameType localNameType : NameType.values()) {
/* 194 */       EnumMap localEnumMap = new EnumMap(RuleType.class);
/*     */       
/*     */ 
/* 197 */       for (RuleType localRuleType : RuleType.values()) {
/* 198 */         HashMap localHashMap = new HashMap();
/*     */         
/* 200 */         Languages localLanguages = Languages.getInstance(localNameType);
/* 201 */         for (String str : localLanguages.getLanguages()) {
/*     */           try {
/* 203 */             localHashMap.put(str, parseRules(createScanner(localNameType, localRuleType, str), createResourceName(localNameType, localRuleType, str)));
/*     */           } catch (IllegalStateException localIllegalStateException) {
/* 205 */             throw new IllegalStateException("Problem processing " + createResourceName(localNameType, localRuleType, str), localIllegalStateException);
/*     */           }
/*     */         }
/* 208 */         if (!localRuleType.equals(RuleType.RULES)) {
/* 209 */           localHashMap.put("common", parseRules(createScanner(localNameType, localRuleType, "common"), createResourceName(localNameType, localRuleType, "common")));
/*     */         }
/*     */         
/* 212 */         localEnumMap.put(localRuleType, Collections.unmodifiableMap(localHashMap));
/*     */       }
/*     */       
/* 215 */       RULES.put(localNameType, Collections.unmodifiableMap(localEnumMap));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean contains(CharSequence paramCharSequence, char paramChar) {
/* 220 */     for (int i = 0; i < paramCharSequence.length(); i++) {
/* 221 */       if (paramCharSequence.charAt(i) == paramChar) {
/* 222 */         return true;
/*     */       }
/*     */     }
/* 225 */     return false;
/*     */   }
/*     */   
/*     */   private static String createResourceName(NameType paramNameType, RuleType paramRuleType, String paramString) {
/* 229 */     return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", new Object[] { paramNameType.getName(), paramRuleType.getName(), paramString });
/*     */   }
/*     */   
/*     */   private static Scanner createScanner(NameType paramNameType, RuleType paramRuleType, String paramString)
/*     */   {
/* 234 */     String str = createResourceName(paramNameType, paramRuleType, paramString);
/* 235 */     InputStream localInputStream = Languages.class.getClassLoader().getResourceAsStream(str);
/*     */     
/* 237 */     if (localInputStream == null) {
/* 238 */       throw new IllegalArgumentException("Unable to load resource: " + str);
/*     */     }
/*     */     
/* 241 */     return new Scanner(localInputStream, "UTF-8");
/*     */   }
/*     */   
/*     */   private static Scanner createScanner(String paramString) {
/* 245 */     String str = String.format("org/apache/commons/codec/language/bm/%s.txt", new Object[] { paramString });
/* 246 */     InputStream localInputStream = Languages.class.getClassLoader().getResourceAsStream(str);
/*     */     
/* 248 */     if (localInputStream == null) {
/* 249 */       throw new IllegalArgumentException("Unable to load resource: " + str);
/*     */     }
/*     */     
/* 252 */     return new Scanner(localInputStream, "UTF-8");
/*     */   }
/*     */   
/*     */   private static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
/* 256 */     if (paramCharSequence2.length() > paramCharSequence1.length()) {
/* 257 */       return false;
/*     */     }
/* 259 */     int i = paramCharSequence1.length() - 1; for (int j = paramCharSequence2.length() - 1; j >= 0; j--) {
/* 260 */       if (paramCharSequence1.charAt(i) != paramCharSequence2.charAt(j)) {
/* 261 */         return false;
/*     */       }
/* 259 */       i--;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 264 */     return true;
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
/*     */   public static List<Rule> getInstance(NameType paramNameType, RuleType paramRuleType, Languages.LanguageSet paramLanguageSet)
/*     */   {
/* 280 */     Map localMap = getInstanceMap(paramNameType, paramRuleType, paramLanguageSet);
/* 281 */     ArrayList localArrayList = new ArrayList();
/* 282 */     for (List localList : localMap.values()) {
/* 283 */       localArrayList.addAll(localList);
/*     */     }
/* 285 */     return localArrayList;
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
/*     */   public static List<Rule> getInstance(NameType paramNameType, RuleType paramRuleType, String paramString)
/*     */   {
/* 300 */     return getInstance(paramNameType, paramRuleType, Languages.LanguageSet.from(new HashSet(Arrays.asList(new String[] { paramString }))));
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
/*     */   public static Map<String, List<Rule>> getInstanceMap(NameType paramNameType, RuleType paramRuleType, Languages.LanguageSet paramLanguageSet)
/*     */   {
/* 317 */     return paramLanguageSet.isSingleton() ? getInstanceMap(paramNameType, paramRuleType, paramLanguageSet.getAny()) : getInstanceMap(paramNameType, paramRuleType, "any");
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
/*     */   public static Map<String, List<Rule>> getInstanceMap(NameType paramNameType, RuleType paramRuleType, String paramString)
/*     */   {
/* 335 */     Map localMap = (Map)((Map)((Map)RULES.get(paramNameType)).get(paramRuleType)).get(paramString);
/*     */     
/* 337 */     if (localMap == null) {
/* 338 */       throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", new Object[] { paramNameType.getName(), paramRuleType.getName(), paramString }));
/*     */     }
/*     */     
/*     */ 
/* 342 */     return localMap;
/*     */   }
/*     */   
/*     */   private static Phoneme parsePhoneme(String paramString) {
/* 346 */     int i = paramString.indexOf("[");
/* 347 */     if (i >= 0) {
/* 348 */       if (!paramString.endsWith("]")) {
/* 349 */         throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
/*     */       }
/* 351 */       String str1 = paramString.substring(0, i);
/* 352 */       String str2 = paramString.substring(i + 1, paramString.length() - 1);
/* 353 */       HashSet localHashSet = new HashSet(Arrays.asList(str2.split("[+]")));
/*     */       
/* 355 */       return new Phoneme(str1, Languages.LanguageSet.from(localHashSet));
/*     */     }
/* 357 */     return new Phoneme(paramString, Languages.ANY_LANGUAGE);
/*     */   }
/*     */   
/*     */   private static PhonemeExpr parsePhonemeExpr(String paramString)
/*     */   {
/* 362 */     if (paramString.startsWith("(")) {
/* 363 */       if (!paramString.endsWith(")")) {
/* 364 */         throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
/*     */       }
/*     */       
/* 367 */       ArrayList localArrayList = new ArrayList();
/* 368 */       String str1 = paramString.substring(1, paramString.length() - 1);
/* 369 */       for (String str2 : str1.split("[|]")) {
/* 370 */         localArrayList.add(parsePhoneme(str2));
/*     */       }
/* 372 */       if ((str1.startsWith("|")) || (str1.endsWith("|"))) {
/* 373 */         localArrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
/*     */       }
/*     */       
/* 376 */       return new PhonemeList(localArrayList);
/*     */     }
/* 378 */     return parsePhoneme(paramString);
/*     */   }
/*     */   
/*     */   private static Map<String, List<Rule>> parseRules(Scanner paramScanner, final String paramString)
/*     */   {
/* 383 */     HashMap localHashMap = new HashMap();
/* 384 */     int i = 0;
/*     */     
/* 386 */     int j = 0;
/* 387 */     while (paramScanner.hasNextLine()) {
/* 388 */       i++;
/* 389 */       String str1 = paramScanner.nextLine();
/* 390 */       String str2 = str1;
/*     */       
/* 392 */       if (j != 0) {
/* 393 */         if (str2.endsWith("*/")) {
/* 394 */           j = 0;
/*     */         }
/*     */       }
/* 397 */       else if (str2.startsWith("/*")) {
/* 398 */         j = 1;
/*     */       }
/*     */       else {
/* 401 */         int k = str2.indexOf("//");
/* 402 */         if (k >= 0) {
/* 403 */           str2 = str2.substring(0, k);
/*     */         }
/*     */         
/*     */ 
/* 407 */         str2 = str2.trim();
/*     */         
/* 409 */         if (str2.length() != 0)
/*     */         {
/*     */           Object localObject1;
/*     */           
/* 413 */           if (str2.startsWith("#include"))
/*     */           {
/* 415 */             localObject1 = str2.substring("#include".length()).trim();
/* 416 */             if (((String)localObject1).contains(" ")) {
/* 417 */               throw new IllegalArgumentException("Malformed import statement '" + str1 + "' in " + paramString);
/*     */             }
/*     */             
/* 420 */             localHashMap.putAll(parseRules(createScanner((String)localObject1), paramString + "->" + (String)localObject1));
/*     */           }
/*     */           else
/*     */           {
/* 424 */             localObject1 = str2.split("\\s+");
/* 425 */             if (localObject1.length != 4) {
/* 426 */               throw new IllegalArgumentException("Malformed rule statement split into " + localObject1.length + " parts: " + str1 + " in " + paramString);
/*     */             }
/*     */             try
/*     */             {
/* 430 */               String str3 = stripQuotes(localObject1[0]);
/* 431 */               String str4 = stripQuotes(localObject1[1]);
/* 432 */               String str5 = stripQuotes(localObject1[2]);
/* 433 */               PhonemeExpr localPhonemeExpr = parsePhonemeExpr(stripQuotes(localObject1[3]));
/* 434 */               final int m = i;
/* 435 */               Rule local2 = new Rule(str3, str4, str5, localPhonemeExpr) {
/* 436 */                 private final int myLine = m;
/* 437 */                 private final String loc = paramString;
/*     */                 
/*     */                 public String toString()
/*     */                 {
/* 441 */                   StringBuilder localStringBuilder = new StringBuilder();
/* 442 */                   localStringBuilder.append("Rule");
/* 443 */                   localStringBuilder.append("{line=").append(this.myLine);
/* 444 */                   localStringBuilder.append(", loc='").append(this.loc).append('\'');
/* 445 */                   localStringBuilder.append('}');
/* 446 */                   return localStringBuilder.toString();
/*     */                 }
/* 448 */               };
/* 449 */               String str6 = local2.pattern.substring(0, 1);
/* 450 */               Object localObject2 = (List)localHashMap.get(str6);
/* 451 */               if (localObject2 == null) {
/* 452 */                 localObject2 = new ArrayList();
/* 453 */                 localHashMap.put(str6, localObject2);
/*     */               }
/* 455 */               ((List)localObject2).add(local2);
/*     */             } catch (IllegalArgumentException localIllegalArgumentException) {
/* 457 */               throw new IllegalStateException("Problem parsing line '" + i + "' in " + paramString, localIllegalArgumentException);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 466 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static RPattern pattern(String paramString)
/*     */   {
/* 477 */     boolean bool1 = paramString.startsWith("^");
/* 478 */     boolean bool2 = paramString.endsWith("$");
/* 479 */     String str1 = paramString.substring(bool1 ? 1 : 0, bool2 ? paramString.length() - 1 : paramString.length());
/* 480 */     boolean bool3 = str1.contains("[");
/*     */     
/* 482 */     if (!bool3) {
/* 483 */       if ((bool1) && (bool2))
/*     */       {
/* 485 */         if (str1.length() == 0)
/*     */         {
/* 487 */           new RPattern()
/*     */           {
/*     */             public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 490 */               return paramAnonymousCharSequence.length() == 0;
/*     */             }
/*     */           };
/*     */         }
/* 494 */         new RPattern()
/*     */         {
/*     */           public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 497 */             return paramAnonymousCharSequence.equals(this.val$content);
/*     */           }
/*     */         };
/*     */       }
/* 501 */       if (((bool1) || (bool2)) && (str1.length() == 0))
/*     */       {
/* 503 */         return ALL_STRINGS_RMATCHER; }
/* 504 */       if (bool1)
/*     */       {
/* 506 */         new RPattern()
/*     */         {
/*     */           public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 509 */             return Rule.startsWith(paramAnonymousCharSequence, this.val$content);
/*     */           }
/*     */         }; }
/* 512 */       if (bool2)
/*     */       {
/* 514 */         new RPattern()
/*     */         {
/*     */           public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 517 */             return Rule.endsWith(paramAnonymousCharSequence, this.val$content);
/*     */           }
/*     */         };
/*     */       }
/*     */     } else {
/* 522 */       boolean bool4 = str1.startsWith("[");
/* 523 */       boolean bool5 = str1.endsWith("]");
/*     */       
/* 525 */       if ((bool4) && (bool5)) {
/* 526 */         String str2 = str1.substring(1, str1.length() - 1);
/* 527 */         if (!str2.contains("["))
/*     */         {
/* 529 */           boolean bool6 = str2.startsWith("^");
/* 530 */           if (bool6) {
/* 531 */             str2 = str2.substring(1);
/*     */           }
/* 533 */           String str3 = str2;
/* 534 */           final boolean bool7 = !bool6;
/*     */           
/* 536 */           if ((bool1) && (bool2))
/*     */           {
/* 538 */             new RPattern()
/*     */             {
/*     */               public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 541 */                 return (paramAnonymousCharSequence.length() == 1) && (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(0)) == bool7);
/*     */               }
/*     */             }; }
/* 544 */           if (bool1)
/*     */           {
/* 546 */             new RPattern()
/*     */             {
/*     */               public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 549 */                 return (paramAnonymousCharSequence.length() > 0) && (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(0)) == bool7);
/*     */               }
/*     */             }; }
/* 552 */           if (bool2)
/*     */           {
/* 554 */             new RPattern()
/*     */             {
/*     */               public boolean isMatch(CharSequence paramAnonymousCharSequence) {
/* 557 */                 return (paramAnonymousCharSequence.length() > 0) && (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(paramAnonymousCharSequence.length() - 1)) == bool7);
/*     */               }
/*     */             };
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 566 */     new RPattern() {
/* 567 */       Pattern pattern = Pattern.compile(this.val$regex);
/*     */       
/*     */       public boolean isMatch(CharSequence paramAnonymousCharSequence)
/*     */       {
/* 571 */         Matcher localMatcher = this.pattern.matcher(paramAnonymousCharSequence);
/* 572 */         return localMatcher.find();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   private static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
/* 578 */     if (paramCharSequence2.length() > paramCharSequence1.length()) {
/* 579 */       return false;
/*     */     }
/* 581 */     for (int i = 0; i < paramCharSequence2.length(); i++) {
/* 582 */       if (paramCharSequence1.charAt(i) != paramCharSequence2.charAt(i)) {
/* 583 */         return false;
/*     */       }
/*     */     }
/* 586 */     return true;
/*     */   }
/*     */   
/*     */   private static String stripQuotes(String paramString) {
/* 590 */     if (paramString.startsWith("\"")) {
/* 591 */       paramString = paramString.substring(1);
/*     */     }
/*     */     
/* 594 */     if (paramString.endsWith("\"")) {
/* 595 */       paramString = paramString.substring(0, paramString.length() - 1);
/*     */     }
/*     */     
/* 598 */     return paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final PhonemeExpr phoneme;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final RPattern rContext;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Rule(String paramString1, String paramString2, String paramString3, PhonemeExpr paramPhonemeExpr)
/*     */   {
/* 622 */     this.pattern = paramString1;
/* 623 */     this.lContext = pattern(paramString2 + "$");
/* 624 */     this.rContext = pattern("^" + paramString3);
/* 625 */     this.phoneme = paramPhonemeExpr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RPattern getLContext()
/*     */   {
/* 634 */     return this.lContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPattern()
/*     */   {
/* 643 */     return this.pattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PhonemeExpr getPhoneme()
/*     */   {
/* 652 */     return this.phoneme;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RPattern getRContext()
/*     */   {
/* 661 */     return this.rContext;
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
/*     */   public boolean patternAndContextMatches(CharSequence paramCharSequence, int paramInt)
/*     */   {
/* 676 */     if (paramInt < 0) {
/* 677 */       throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
/*     */     }
/*     */     
/* 680 */     int i = this.pattern.length();
/* 681 */     int j = paramInt + i;
/*     */     
/* 683 */     if (j > paramCharSequence.length())
/*     */     {
/* 685 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 690 */     if (!paramCharSequence.subSequence(paramInt, j).equals(this.pattern))
/* 691 */       return false;
/* 692 */     if (!this.rContext.isMatch(paramCharSequence.subSequence(j, paramCharSequence.length()))) {
/* 693 */       return false;
/*     */     }
/* 695 */     return this.lContext.isMatch(paramCharSequence.subSequence(0, paramInt));
/*     */   }
/*     */   
/*     */   public static abstract interface RPattern
/*     */   {
/*     */     public abstract boolean isMatch(CharSequence paramCharSequence);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\Rule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */