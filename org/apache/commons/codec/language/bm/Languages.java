/*     */ package org.apache.commons.codec.language.bm;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Scanner;
/*     */ import java.util.Set;
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
/*     */ public class Languages
/*     */ {
/*     */   public static final String ANY = "any";
/*     */   
/*     */   public static abstract class LanguageSet
/*     */   {
/*     */     public static LanguageSet from(Set<String> paramSet)
/*     */     {
/*  64 */       return paramSet.isEmpty() ? Languages.NO_LANGUAGES : new Languages.SomeLanguages(paramSet, null);
/*     */     }
/*     */     
/*     */     public abstract boolean contains(String paramString);
/*     */     
/*     */     public abstract String getAny();
/*     */     
/*     */     public abstract boolean isEmpty();
/*     */     
/*     */     public abstract boolean isSingleton();
/*     */     
/*     */     public abstract LanguageSet restrictTo(LanguageSet paramLanguageSet);
/*     */   }
/*     */   
/*     */   public static final class SomeLanguages
/*     */     extends Languages.LanguageSet
/*     */   {
/*     */     private final Set<String> languages;
/*     */     
/*     */     private SomeLanguages(Set<String> paramSet)
/*     */     {
/*  85 */       this.languages = Collections.unmodifiableSet(paramSet);
/*     */     }
/*     */     
/*     */     public boolean contains(String paramString)
/*     */     {
/*  90 */       return this.languages.contains(paramString);
/*     */     }
/*     */     
/*     */     public String getAny()
/*     */     {
/*  95 */       return (String)this.languages.iterator().next();
/*     */     }
/*     */     
/*     */     public Set<String> getLanguages() {
/*  99 */       return this.languages;
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/* 104 */       return this.languages.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean isSingleton()
/*     */     {
/* 109 */       return this.languages.size() == 1;
/*     */     }
/*     */     
/*     */     public Languages.LanguageSet restrictTo(Languages.LanguageSet paramLanguageSet)
/*     */     {
/* 114 */       if (paramLanguageSet == Languages.NO_LANGUAGES)
/* 115 */         return paramLanguageSet;
/* 116 */       if (paramLanguageSet == Languages.ANY_LANGUAGE) {
/* 117 */         return this;
/*     */       }
/* 119 */       SomeLanguages localSomeLanguages = (SomeLanguages)paramLanguageSet;
/* 120 */       HashSet localHashSet = new HashSet(Math.min(this.languages.size(), localSomeLanguages.languages.size()));
/* 121 */       for (String str : this.languages) {
/* 122 */         if (localSomeLanguages.languages.contains(str)) {
/* 123 */           localHashSet.add(str);
/*     */         }
/*     */       }
/* 126 */       return from(localHashSet);
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 132 */       return "Languages(" + this.languages.toString() + ")";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 139 */   private static final Map<NameType, Languages> LANGUAGES = new EnumMap(NameType.class);
/*     */   private final Set<String> languages;
/*     */   
/* 142 */   static { for (NameType localNameType : NameType.values()) {
/* 143 */       LANGUAGES.put(localNameType, getInstance(langResourceName(localNameType)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static Languages getInstance(NameType paramNameType) {
/* 148 */     return (Languages)LANGUAGES.get(paramNameType);
/*     */   }
/*     */   
/*     */   public static Languages getInstance(String paramString)
/*     */   {
/* 153 */     HashSet localHashSet = new HashSet();
/* 154 */     InputStream localInputStream = Languages.class.getClassLoader().getResourceAsStream(paramString);
/*     */     
/* 156 */     if (localInputStream == null) {
/* 157 */       throw new IllegalArgumentException("Unable to resolve required resource: " + paramString);
/*     */     }
/*     */     
/* 160 */     Scanner localScanner = new Scanner(localInputStream, "UTF-8");
/*     */     try {
/* 162 */       int i = 0;
/* 163 */       while (localScanner.hasNextLine()) {
/* 164 */         String str = localScanner.nextLine().trim();
/* 165 */         if (i != 0) {
/* 166 */           if (str.endsWith("*/")) {
/* 167 */             i = 0;
/*     */           }
/*     */         }
/* 170 */         else if (str.startsWith("/*")) {
/* 171 */           i = 1;
/* 172 */         } else if (str.length() > 0) {
/* 173 */           localHashSet.add(str);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally {
/* 178 */       localScanner.close();
/*     */     }
/*     */     
/* 181 */     return new Languages(Collections.unmodifiableSet(localHashSet));
/*     */   }
/*     */   
/*     */   private static String langResourceName(NameType paramNameType) {
/* 185 */     return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", new Object[] { paramNameType.getName() });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 193 */   public static final LanguageSet NO_LANGUAGES = new LanguageSet()
/*     */   {
/*     */     public boolean contains(String paramAnonymousString) {
/* 196 */       return false;
/*     */     }
/*     */     
/*     */     public String getAny()
/*     */     {
/* 201 */       throw new NoSuchElementException("Can't fetch any language from the empty language set.");
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/* 206 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isSingleton()
/*     */     {
/* 211 */       return false;
/*     */     }
/*     */     
/*     */     public Languages.LanguageSet restrictTo(Languages.LanguageSet paramAnonymousLanguageSet)
/*     */     {
/* 216 */       return this;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 221 */       return "NO_LANGUAGES";
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 228 */   public static final LanguageSet ANY_LANGUAGE = new LanguageSet()
/*     */   {
/*     */     public boolean contains(String paramAnonymousString) {
/* 231 */       return true;
/*     */     }
/*     */     
/*     */     public String getAny()
/*     */     {
/* 236 */       throw new NoSuchElementException("Can't fetch any language from the any language set.");
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/* 241 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isSingleton()
/*     */     {
/* 246 */       return false;
/*     */     }
/*     */     
/*     */     public Languages.LanguageSet restrictTo(Languages.LanguageSet paramAnonymousLanguageSet)
/*     */     {
/* 251 */       return paramAnonymousLanguageSet;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 256 */       return "ANY_LANGUAGE";
/*     */     }
/*     */   };
/*     */   
/*     */   private Languages(Set<String> paramSet) {
/* 261 */     this.languages = paramSet;
/*     */   }
/*     */   
/*     */   public Set<String> getLanguages() {
/* 265 */     return this.languages;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\bm\Languages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */