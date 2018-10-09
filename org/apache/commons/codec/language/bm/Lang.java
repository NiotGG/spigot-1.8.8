/*     */ package org.apache.commons.codec.language.bm;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Scanner;
/*     */ import java.util.Set;
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
/*     */ public class Lang
/*     */ {
/*     */   private static final class LangRule
/*     */   {
/*     */     private final boolean acceptOnMatch;
/*     */     private final Set<String> languages;
/*     */     private final Pattern pattern;
/*     */     
/*     */     private LangRule(Pattern paramPattern, Set<String> paramSet, boolean paramBoolean)
/*     */     {
/*  86 */       this.pattern = paramPattern;
/*  87 */       this.languages = paramSet;
/*  88 */       this.acceptOnMatch = paramBoolean;
/*     */     }
/*     */     
/*     */     public boolean matches(String paramString) {
/*  92 */       return this.pattern.matcher(paramString).find();
/*     */     }
/*     */   }
/*     */   
/*  96 */   private static final Map<NameType, Lang> Langs = new EnumMap(NameType.class);
/*     */   private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/lang.txt";
/*     */   private final Languages languages;
/*     */   private final List<LangRule> rules;
/*     */   
/* 101 */   static { for (NameType localNameType : NameType.values()) {
/* 102 */       Langs.put(localNameType, loadFromResource("org/apache/commons/codec/language/bm/lang.txt", Languages.getInstance(localNameType)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Lang instance(NameType paramNameType)
/*     */   {
/* 114 */     return (Lang)Langs.get(paramNameType);
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
/*     */   public static Lang loadFromResource(String paramString, Languages paramLanguages)
/*     */   {
/* 130 */     ArrayList localArrayList = new ArrayList();
/* 131 */     InputStream localInputStream = Lang.class.getClassLoader().getResourceAsStream(paramString);
/*     */     
/* 133 */     if (localInputStream == null) {
/* 134 */       throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/lang.txt");
/*     */     }
/*     */     
/* 137 */     Scanner localScanner = new Scanner(localInputStream, "UTF-8");
/*     */     try {
/* 139 */       int i = 0;
/* 140 */       while (localScanner.hasNextLine()) {
/* 141 */         String str1 = localScanner.nextLine();
/* 142 */         String str2 = str1;
/* 143 */         if (i != 0)
/*     */         {
/* 145 */           if (str2.endsWith("*/")) {
/* 146 */             i = 0;
/*     */           }
/*     */         }
/* 149 */         else if (str2.startsWith("/*")) {
/* 150 */           i = 1;
/*     */         }
/*     */         else {
/* 153 */           int j = str2.indexOf("//");
/* 154 */           if (j >= 0) {
/* 155 */             str2 = str2.substring(0, j);
/*     */           }
/*     */           
/*     */ 
/* 159 */           str2 = str2.trim();
/*     */           
/* 161 */           if (str2.length() != 0)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 166 */             String[] arrayOfString1 = str2.split("\\s+");
/*     */             
/* 168 */             if (arrayOfString1.length != 3) {
/* 169 */               throw new IllegalArgumentException("Malformed line '" + str1 + "' in language resource '" + paramString + "'");
/*     */             }
/*     */             
/*     */ 
/* 173 */             Pattern localPattern = Pattern.compile(arrayOfString1[0]);
/* 174 */             String[] arrayOfString2 = arrayOfString1[1].split("\\+");
/* 175 */             boolean bool = arrayOfString1[2].equals("true");
/*     */             
/* 177 */             localArrayList.add(new LangRule(localPattern, new HashSet(Arrays.asList(arrayOfString2)), bool, null));
/*     */           }
/*     */         }
/*     */       }
/*     */     } finally {
/* 182 */       localScanner.close();
/*     */     }
/* 184 */     return new Lang(localArrayList, paramLanguages);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Lang(List<LangRule> paramList, Languages paramLanguages)
/*     */   {
/* 191 */     this.rules = Collections.unmodifiableList(paramList);
/* 192 */     this.languages = paramLanguages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String guessLanguage(String paramString)
/*     */   {
/* 203 */     Languages.LanguageSet localLanguageSet = guessLanguages(paramString);
/* 204 */     return localLanguageSet.isSingleton() ? localLanguageSet.getAny() : "any";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Languages.LanguageSet guessLanguages(String paramString)
/*     */   {
/* 215 */     String str = paramString.toLowerCase(Locale.ENGLISH);
/*     */     
/* 217 */     HashSet localHashSet = new HashSet(this.languages.getLanguages());
/* 218 */     for (Object localObject = this.rules.iterator(); ((Iterator)localObject).hasNext();) { LangRule localLangRule = (LangRule)((Iterator)localObject).next();
/* 219 */       if (localLangRule.matches(str)) {
/* 220 */         if (localLangRule.acceptOnMatch) {
/* 221 */           localHashSet.retainAll(localLangRule.languages);
/*     */         } else {
/* 223 */           localHashSet.removeAll(localLangRule.languages);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 228 */     localObject = Languages.LanguageSet.from(localHashSet);
/* 229 */     return localObject.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : localObject;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\Lang.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */