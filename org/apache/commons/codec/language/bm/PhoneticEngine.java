/*     */ package org.apache.commons.codec.language.bm;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhoneticEngine
/*     */ {
/*     */   static final class PhonemeBuilder
/*     */   {
/*     */     private final Set<Rule.Phoneme> phonemes;
/*     */     
/*     */     public static PhonemeBuilder empty(Languages.LanguageSet paramLanguageSet)
/*     */     {
/*  72 */       return new PhonemeBuilder(new Rule.Phoneme("", paramLanguageSet));
/*     */     }
/*     */     
/*     */ 
/*     */     private PhonemeBuilder(Rule.Phoneme paramPhoneme)
/*     */     {
/*  78 */       this.phonemes = new LinkedHashSet();
/*  79 */       this.phonemes.add(paramPhoneme);
/*     */     }
/*     */     
/*     */     private PhonemeBuilder(Set<Rule.Phoneme> paramSet) {
/*  83 */       this.phonemes = paramSet;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void append(CharSequence paramCharSequence)
/*     */     {
/*  92 */       for (Rule.Phoneme localPhoneme : this.phonemes) {
/*  93 */         localPhoneme.append(paramCharSequence);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void apply(Rule.PhonemeExpr paramPhonemeExpr, int paramInt)
/*     */     {
/* 107 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(paramInt);
/*     */       
/* 109 */       for (Iterator localIterator1 = this.phonemes.iterator(); localIterator1.hasNext();) { localPhoneme1 = (Rule.Phoneme)localIterator1.next();
/* 110 */         for (Rule.Phoneme localPhoneme2 : paramPhonemeExpr.getPhonemes()) {
/* 111 */           Languages.LanguageSet localLanguageSet = localPhoneme1.getLanguages().restrictTo(localPhoneme2.getLanguages());
/* 112 */           if (!localLanguageSet.isEmpty()) {
/* 113 */             Rule.Phoneme localPhoneme3 = new Rule.Phoneme(localPhoneme1, localPhoneme2, localLanguageSet);
/* 114 */             if (localLinkedHashSet.size() < paramInt) {
/* 115 */               localLinkedHashSet.add(localPhoneme3);
/* 116 */               if (localLinkedHashSet.size() >= paramInt)
/*     */                 break label153;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       Rule.Phoneme localPhoneme1;
/*     */       label153:
/* 124 */       this.phonemes.clear();
/* 125 */       this.phonemes.addAll(localLinkedHashSet);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Set<Rule.Phoneme> getPhonemes()
/*     */     {
/* 134 */       return this.phonemes;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String makeString()
/*     */     {
/* 145 */       StringBuilder localStringBuilder = new StringBuilder();
/*     */       
/* 147 */       for (Rule.Phoneme localPhoneme : this.phonemes) {
/* 148 */         if (localStringBuilder.length() > 0) {
/* 149 */           localStringBuilder.append("|");
/*     */         }
/* 151 */         localStringBuilder.append(localPhoneme.getPhonemeText());
/*     */       }
/*     */       
/* 154 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class RulesApplication
/*     */   {
/*     */     private final Map<String, List<Rule>> finalRules;
/*     */     
/*     */ 
/*     */     private final CharSequence input;
/*     */     
/*     */ 
/*     */     private PhoneticEngine.PhonemeBuilder phonemeBuilder;
/*     */     
/*     */ 
/*     */     private int i;
/*     */     
/*     */ 
/*     */     private final int maxPhonemes;
/*     */     
/*     */     private boolean found;
/*     */     
/*     */ 
/*     */     public RulesApplication(Map<String, List<Rule>> paramMap, CharSequence paramCharSequence, PhoneticEngine.PhonemeBuilder paramPhonemeBuilder, int paramInt1, int paramInt2)
/*     */     {
/* 181 */       if (paramMap == null) {
/* 182 */         throw new NullPointerException("The finalRules argument must not be null");
/*     */       }
/* 184 */       this.finalRules = paramMap;
/* 185 */       this.phonemeBuilder = paramPhonemeBuilder;
/* 186 */       this.input = paramCharSequence;
/* 187 */       this.i = paramInt1;
/* 188 */       this.maxPhonemes = paramInt2;
/*     */     }
/*     */     
/*     */     public int getI() {
/* 192 */       return this.i;
/*     */     }
/*     */     
/*     */     public PhoneticEngine.PhonemeBuilder getPhonemeBuilder() {
/* 196 */       return this.phonemeBuilder;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public RulesApplication invoke()
/*     */     {
/* 207 */       this.found = false;
/* 208 */       int j = 1;
/* 209 */       List localList = (List)this.finalRules.get(this.input.subSequence(this.i, this.i + j));
/* 210 */       if (localList != null) {
/* 211 */         for (Rule localRule : localList) {
/* 212 */           String str = localRule.getPattern();
/* 213 */           j = str.length();
/* 214 */           if (localRule.patternAndContextMatches(this.input, this.i)) {
/* 215 */             this.phonemeBuilder.apply(localRule.getPhoneme(), this.maxPhonemes);
/* 216 */             this.found = true;
/* 217 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 222 */       if (!this.found) {
/* 223 */         j = 1;
/*     */       }
/*     */       
/* 226 */       this.i += j;
/* 227 */       return this;
/*     */     }
/*     */     
/*     */     public boolean isFound() {
/* 231 */       return this.found;
/*     */     }
/*     */   }
/*     */   
/* 235 */   private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap(NameType.class);
/*     */   private static final int DEFAULT_MAX_PHONEMES = 20;
/*     */   
/* 238 */   static { NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "bar", "ben", "da", "de", "van", "von" }))));
/*     */     
/*     */ 
/* 241 */     NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von" }))));
/*     */     
/*     */ 
/*     */ 
/* 245 */     NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von" }))));
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
/*     */   private static String join(Iterable<String> paramIterable, String paramString)
/*     */   {
/* 258 */     StringBuilder localStringBuilder = new StringBuilder();
/* 259 */     Iterator localIterator = paramIterable.iterator();
/* 260 */     if (localIterator.hasNext()) {
/* 261 */       localStringBuilder.append((String)localIterator.next());
/*     */     }
/* 263 */     while (localIterator.hasNext()) {
/* 264 */       localStringBuilder.append(paramString).append((String)localIterator.next());
/*     */     }
/*     */     
/* 267 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Lang lang;
/*     */   
/*     */ 
/*     */ 
/*     */   private final NameType nameType;
/*     */   
/*     */ 
/*     */ 
/*     */   private final RuleType ruleType;
/*     */   
/*     */ 
/*     */ 
/*     */   private final boolean concat;
/*     */   
/*     */ 
/*     */   private final int maxPhonemes;
/*     */   
/*     */ 
/*     */   public PhoneticEngine(NameType paramNameType, RuleType paramRuleType, boolean paramBoolean)
/*     */   {
/* 293 */     this(paramNameType, paramRuleType, paramBoolean, 20);
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
/*     */   public PhoneticEngine(NameType paramNameType, RuleType paramRuleType, boolean paramBoolean, int paramInt)
/*     */   {
/* 311 */     if (paramRuleType == RuleType.RULES) {
/* 312 */       throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
/*     */     }
/* 314 */     this.nameType = paramNameType;
/* 315 */     this.ruleType = paramRuleType;
/* 316 */     this.concat = paramBoolean;
/* 317 */     this.lang = Lang.instance(paramNameType);
/* 318 */     this.maxPhonemes = paramInt;
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
/*     */   private PhonemeBuilder applyFinalRules(PhonemeBuilder paramPhonemeBuilder, Map<String, List<Rule>> paramMap)
/*     */   {
/* 331 */     if (paramMap == null) {
/* 332 */       throw new NullPointerException("finalRules can not be null");
/*     */     }
/* 334 */     if (paramMap.isEmpty()) {
/* 335 */       return paramPhonemeBuilder;
/*     */     }
/*     */     
/* 338 */     TreeSet localTreeSet = new TreeSet(Rule.Phoneme.COMPARATOR);
/*     */     
/* 340 */     for (Rule.Phoneme localPhoneme : paramPhonemeBuilder.getPhonemes()) {
/* 341 */       PhonemeBuilder localPhonemeBuilder = PhonemeBuilder.empty(localPhoneme.getLanguages());
/* 342 */       String str = localPhoneme.getPhonemeText().toString();
/*     */       
/* 344 */       for (int i = 0; i < str.length();) {
/* 345 */         RulesApplication localRulesApplication = new RulesApplication(paramMap, str, localPhonemeBuilder, i, this.maxPhonemes).invoke();
/*     */         
/* 347 */         boolean bool = localRulesApplication.isFound();
/* 348 */         localPhonemeBuilder = localRulesApplication.getPhonemeBuilder();
/*     */         
/* 350 */         if (!bool)
/*     */         {
/* 352 */           localPhonemeBuilder.append(str.subSequence(i, i + 1));
/*     */         }
/*     */         
/* 355 */         i = localRulesApplication.getI();
/*     */       }
/*     */       
/* 358 */       localTreeSet.addAll(localPhonemeBuilder.getPhonemes());
/*     */     }
/*     */     
/* 361 */     return new PhonemeBuilder(localTreeSet, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encode(String paramString)
/*     */   {
/* 372 */     Languages.LanguageSet localLanguageSet = this.lang.guessLanguages(paramString);
/* 373 */     return encode(paramString, localLanguageSet);
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
/*     */   public String encode(String paramString, Languages.LanguageSet paramLanguageSet)
/*     */   {
/* 386 */     Map localMap1 = Rule.getInstanceMap(this.nameType, RuleType.RULES, paramLanguageSet);
/*     */     
/* 388 */     Map localMap2 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
/*     */     
/* 390 */     Map localMap3 = Rule.getInstanceMap(this.nameType, this.ruleType, paramLanguageSet);
/*     */     
/*     */ 
/*     */ 
/* 394 */     paramString = paramString.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
/*     */     
/* 396 */     if (this.nameType == NameType.GENERIC) {
/* 397 */       if ((paramString.length() >= 2) && (paramString.substring(0, 2).equals("d'"))) {
/* 398 */         localObject1 = paramString.substring(2);
/* 399 */         localObject2 = "d" + (String)localObject1;
/* 400 */         return "(" + encode((String)localObject1) + ")-(" + encode((String)localObject2) + ")";
/*     */       }
/* 402 */       for (localObject1 = ((Set)NAME_PREFIXES.get(this.nameType)).iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (String)((Iterator)localObject1).next();
/*     */         
/* 404 */         if (paramString.startsWith((String)localObject2 + " "))
/*     */         {
/* 406 */           localObject3 = paramString.substring(((String)localObject2).length() + 1);
/* 407 */           localObject4 = (String)localObject2 + (String)localObject3;
/* 408 */           return "(" + encode((String)localObject3) + ")-(" + encode((String)localObject4) + ")";
/*     */         }
/*     */       }
/*     */     }
/*     */     Object localObject4;
/* 413 */     Object localObject1 = Arrays.asList(paramString.split("\\s+"));
/* 414 */     Object localObject2 = new ArrayList();
/*     */     
/*     */     Object localObject5;
/* 417 */     switch (this.nameType) {
/*     */     case SEPHARDIC: 
/* 419 */       for (localObject3 = ((List)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (String)((Iterator)localObject3).next();
/* 420 */         localObject5 = ((String)localObject4).split("'");
/* 421 */         Object localObject6 = localObject5[(localObject5.length - 1)];
/* 422 */         ((List)localObject2).add(localObject6);
/*     */       }
/* 424 */       ((List)localObject2).removeAll((Collection)NAME_PREFIXES.get(this.nameType));
/* 425 */       break;
/*     */     case ASHKENAZI: 
/* 427 */       ((List)localObject2).addAll((Collection)localObject1);
/* 428 */       ((List)localObject2).removeAll((Collection)NAME_PREFIXES.get(this.nameType));
/* 429 */       break;
/*     */     case GENERIC: 
/* 431 */       ((List)localObject2).addAll((Collection)localObject1);
/* 432 */       break;
/*     */     default: 
/* 434 */       throw new IllegalStateException("Unreachable case: " + this.nameType);
/*     */     }
/*     */     
/* 437 */     if (this.concat)
/*     */     {
/* 439 */       paramString = join((Iterable)localObject2, " ");
/* 440 */     } else if (((List)localObject2).size() == 1)
/*     */     {
/* 442 */       paramString = (String)((List)localObject1).iterator().next();
/*     */     }
/*     */     else {
/* 445 */       localObject3 = new StringBuilder();
/* 446 */       for (localObject4 = ((List)localObject2).iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (String)((Iterator)localObject4).next();
/* 447 */         ((StringBuilder)localObject3).append("-").append(encode((String)localObject5));
/*     */       }
/*     */       
/* 450 */       return ((StringBuilder)localObject3).substring(1);
/*     */     }
/*     */     
/* 453 */     Object localObject3 = PhonemeBuilder.empty(paramLanguageSet);
/*     */     
/*     */ 
/* 456 */     for (int i = 0; i < paramString.length();) {
/* 457 */       localObject5 = new RulesApplication(localMap1, paramString, (PhonemeBuilder)localObject3, i, this.maxPhonemes).invoke();
/*     */       
/* 459 */       i = ((RulesApplication)localObject5).getI();
/* 460 */       localObject3 = ((RulesApplication)localObject5).getPhonemeBuilder();
/*     */     }
/*     */     
/*     */ 
/* 464 */     localObject3 = applyFinalRules((PhonemeBuilder)localObject3, localMap2);
/*     */     
/* 466 */     localObject3 = applyFinalRules((PhonemeBuilder)localObject3, localMap3);
/*     */     
/* 468 */     return ((PhonemeBuilder)localObject3).makeString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Lang getLang()
/*     */   {
/* 477 */     return this.lang;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameType getNameType()
/*     */   {
/* 486 */     return this.nameType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RuleType getRuleType()
/*     */   {
/* 495 */     return this.ruleType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConcat()
/*     */   {
/* 504 */     return this.concat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMaxPhonemes()
/*     */   {
/* 514 */     return this.maxPhonemes;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\PhoneticEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */