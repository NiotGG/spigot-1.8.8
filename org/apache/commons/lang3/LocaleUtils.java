/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public class LocaleUtils
/*     */ {
/*  42 */   private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*  46 */   private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap();
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
/*     */   public static Locale toLocale(String paramString)
/*     */   {
/*  91 */     if (paramString == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     if (paramString.isEmpty()) {
/*  95 */       return new Locale("", "");
/*     */     }
/*  97 */     if (paramString.contains("#")) {
/*  98 */       throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */     }
/* 100 */     int i = paramString.length();
/* 101 */     if (i < 2) {
/* 102 */       throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */     }
/* 104 */     int j = paramString.charAt(0);
/* 105 */     if (j == 95) {
/* 106 */       if (i < 3) {
/* 107 */         throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */       }
/* 109 */       char c1 = paramString.charAt(1);
/* 110 */       char c2 = paramString.charAt(2);
/* 111 */       if ((!Character.isUpperCase(c1)) || (!Character.isUpperCase(c2))) {
/* 112 */         throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */       }
/* 114 */       if (i == 3) {
/* 115 */         return new Locale("", paramString.substring(1, 3));
/*     */       }
/* 117 */       if (i < 5) {
/* 118 */         throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */       }
/* 120 */       if (paramString.charAt(3) != '_') {
/* 121 */         throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */       }
/* 123 */       return new Locale("", paramString.substring(1, 3), paramString.substring(4));
/*     */     }
/*     */     
/* 126 */     String[] arrayOfString = paramString.split("_", -1);
/* 127 */     int k = arrayOfString.length - 1;
/* 128 */     switch (k) {
/*     */     case 0: 
/* 130 */       if ((StringUtils.isAllLowerCase(paramString)) && ((i == 2) || (i == 3))) {
/* 131 */         return new Locale(paramString);
/*     */       }
/* 133 */       throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */     
/*     */ 
/*     */     case 1: 
/* 137 */       if ((StringUtils.isAllLowerCase(arrayOfString[0])) && ((arrayOfString[0].length() == 2) || (arrayOfString[0].length() == 3)) && (arrayOfString[1].length() == 2) && (StringUtils.isAllUpperCase(arrayOfString[1])))
/*     */       {
/*     */ 
/* 140 */         return new Locale(arrayOfString[0], arrayOfString[1]);
/*     */       }
/* 142 */       throw new IllegalArgumentException("Invalid locale format: " + paramString);
/*     */     
/*     */ 
/*     */     case 2: 
/* 146 */       if ((StringUtils.isAllLowerCase(arrayOfString[0])) && ((arrayOfString[0].length() == 2) || (arrayOfString[0].length() == 3)) && ((arrayOfString[1].length() == 0) || ((arrayOfString[1].length() == 2) && (StringUtils.isAllUpperCase(arrayOfString[1])))) && (arrayOfString[2].length() > 0))
/*     */       {
/*     */ 
/*     */ 
/* 150 */         return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
/*     */       }
/*     */       break;
/*     */     }
/*     */     
/* 155 */     throw new IllegalArgumentException("Invalid locale format: " + paramString);
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
/*     */   public static List<Locale> localeLookupList(Locale paramLocale)
/*     */   {
/* 173 */     return localeLookupList(paramLocale, paramLocale);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<Locale> localeLookupList(Locale paramLocale1, Locale paramLocale2)
/*     */   {
/* 195 */     ArrayList localArrayList = new ArrayList(4);
/* 196 */     if (paramLocale1 != null) {
/* 197 */       localArrayList.add(paramLocale1);
/* 198 */       if (paramLocale1.getVariant().length() > 0) {
/* 199 */         localArrayList.add(new Locale(paramLocale1.getLanguage(), paramLocale1.getCountry()));
/*     */       }
/* 201 */       if (paramLocale1.getCountry().length() > 0) {
/* 202 */         localArrayList.add(new Locale(paramLocale1.getLanguage(), ""));
/*     */       }
/* 204 */       if (!localArrayList.contains(paramLocale2)) {
/* 205 */         localArrayList.add(paramLocale2);
/*     */       }
/*     */     }
/* 208 */     return Collections.unmodifiableList(localArrayList);
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
/*     */   public static List<Locale> availableLocaleList()
/*     */   {
/* 222 */     return SyncAvoid.AVAILABLE_LOCALE_LIST;
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
/*     */   public static Set<Locale> availableLocaleSet()
/*     */   {
/* 236 */     return SyncAvoid.AVAILABLE_LOCALE_SET;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isAvailableLocale(Locale paramLocale)
/*     */   {
/* 247 */     return availableLocaleList().contains(paramLocale);
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
/*     */   public static List<Locale> languagesByCountry(String paramString)
/*     */   {
/* 261 */     if (paramString == null) {
/* 262 */       return Collections.emptyList();
/*     */     }
/* 264 */     Object localObject = (List)cLanguagesByCountry.get(paramString);
/* 265 */     if (localObject == null) {
/* 266 */       localObject = new ArrayList();
/* 267 */       List localList = availableLocaleList();
/* 268 */       for (int i = 0; i < localList.size(); i++) {
/* 269 */         Locale localLocale = (Locale)localList.get(i);
/* 270 */         if ((paramString.equals(localLocale.getCountry())) && (localLocale.getVariant().isEmpty()))
/*     */         {
/* 272 */           ((List)localObject).add(localLocale);
/*     */         }
/*     */       }
/* 275 */       localObject = Collections.unmodifiableList((List)localObject);
/* 276 */       cLanguagesByCountry.putIfAbsent(paramString, localObject);
/* 277 */       localObject = (List)cLanguagesByCountry.get(paramString);
/*     */     }
/* 279 */     return (List<Locale>)localObject;
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
/*     */   public static List<Locale> countriesByLanguage(String paramString)
/*     */   {
/* 293 */     if (paramString == null) {
/* 294 */       return Collections.emptyList();
/*     */     }
/* 296 */     Object localObject = (List)cCountriesByLanguage.get(paramString);
/* 297 */     if (localObject == null) {
/* 298 */       localObject = new ArrayList();
/* 299 */       List localList = availableLocaleList();
/* 300 */       for (int i = 0; i < localList.size(); i++) {
/* 301 */         Locale localLocale = (Locale)localList.get(i);
/* 302 */         if ((paramString.equals(localLocale.getLanguage())) && (localLocale.getCountry().length() != 0) && (localLocale.getVariant().isEmpty()))
/*     */         {
/*     */ 
/* 305 */           ((List)localObject).add(localLocale);
/*     */         }
/*     */       }
/* 308 */       localObject = Collections.unmodifiableList((List)localObject);
/* 309 */       cCountriesByLanguage.putIfAbsent(paramString, localObject);
/* 310 */       localObject = (List)cCountriesByLanguage.get(paramString);
/*     */     }
/* 312 */     return (List<Locale>)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   static class SyncAvoid
/*     */   {
/*     */     private static final List<Locale> AVAILABLE_LOCALE_LIST;
/*     */     
/*     */     private static final Set<Locale> AVAILABLE_LOCALE_SET;
/*     */     
/*     */     static
/*     */     {
/* 324 */       ArrayList localArrayList = new ArrayList(Arrays.asList(Locale.getAvailableLocales()));
/* 325 */       AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(localArrayList);
/* 326 */       AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet(localArrayList));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */