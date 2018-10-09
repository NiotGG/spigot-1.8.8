/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NameAbbreviator
/*     */ {
/*  30 */   private static final NameAbbreviator DEFAULT = new NOPAbbreviator();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NameAbbreviator getAbbreviator(String paramString)
/*     */   {
/*  44 */     if (paramString.length() > 0)
/*     */     {
/*     */ 
/*  47 */       String str = paramString.trim();
/*     */       
/*  49 */       if (str.isEmpty()) {
/*  50 */         return DEFAULT;
/*     */       }
/*     */       
/*  53 */       int i = 0;
/*     */       
/*  55 */       while ((i < str.length()) && (str.charAt(i) >= '0') && (str.charAt(i) <= '9'))
/*     */       {
/*  57 */         i++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  63 */       if (i == str.length()) {
/*  64 */         return new MaxElementAbbreviator(Integer.parseInt(str));
/*     */       }
/*     */       
/*  67 */       ArrayList localArrayList = new ArrayList(5);
/*     */       
/*     */ 
/*  70 */       int j = 0;
/*     */       
/*  72 */       while ((j < str.length()) && (j >= 0)) {
/*  73 */         int k = j;
/*     */         int m;
/*  75 */         if (str.charAt(j) == '*') {
/*  76 */           m = Integer.MAX_VALUE;
/*  77 */           k++;
/*     */         }
/*  79 */         else if ((str.charAt(j) >= '0') && (str.charAt(j) <= '9')) {
/*  80 */           m = str.charAt(j) - '0';
/*  81 */           k++;
/*     */         } else {
/*  83 */           m = 0;
/*     */         }
/*     */         
/*     */ 
/*  87 */         char c = '\000';
/*     */         
/*  89 */         if (k < str.length()) {
/*  90 */           c = str.charAt(k);
/*     */           
/*  92 */           if (c == '.') {
/*  93 */             c = '\000';
/*     */           }
/*     */         }
/*     */         
/*  97 */         localArrayList.add(new PatternAbbreviatorFragment(m, c));
/*  98 */         j = str.indexOf('.', j);
/*     */         
/* 100 */         if (j == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 104 */         j++;
/*     */       }
/*     */       
/* 107 */       return new PatternAbbreviator(localArrayList);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 113 */     return DEFAULT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NameAbbreviator getDefaultAbbreviator()
/*     */   {
/* 122 */     return DEFAULT;
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
/*     */   public abstract String abbreviate(String paramString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class NOPAbbreviator
/*     */     extends NameAbbreviator
/*     */   {
/*     */     public String abbreviate(String paramString)
/*     */     {
/* 148 */       return paramString;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class MaxElementAbbreviator
/*     */     extends NameAbbreviator
/*     */   {
/*     */     private final int count;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaxElementAbbreviator(int paramInt)
/*     */     {
/* 167 */       this.count = (paramInt < 1 ? 1 : paramInt);
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
/*     */ 
/*     */ 
/*     */     public String abbreviate(String paramString)
/*     */     {
/* 182 */       int i = paramString.length() - 1;
/*     */       
/* 184 */       for (int j = this.count; j > 0; j--) {
/* 185 */         i = paramString.lastIndexOf('.', i - 1);
/* 186 */         if (i == -1) {
/* 187 */           return paramString;
/*     */         }
/*     */       }
/*     */       
/* 191 */       return paramString.substring(i + 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class PatternAbbreviatorFragment
/*     */   {
/*     */     private final int charCount;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final char ellipsis;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public PatternAbbreviatorFragment(int paramInt, char paramChar)
/*     */     {
/* 219 */       this.charCount = paramInt;
/* 220 */       this.ellipsis = paramChar;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int abbreviate(StringBuilder paramStringBuilder, int paramInt)
/*     */     {
/* 231 */       int i = paramStringBuilder.toString().indexOf('.', paramInt);
/*     */       
/* 233 */       if (i != -1) {
/* 234 */         if (i - paramInt > this.charCount) {
/* 235 */           paramStringBuilder.delete(paramInt + this.charCount, i);
/* 236 */           i = paramInt + this.charCount;
/*     */           
/* 238 */           if (this.ellipsis != 0) {
/* 239 */             paramStringBuilder.insert(i, this.ellipsis);
/* 240 */             i++;
/*     */           }
/*     */         }
/*     */         
/* 244 */         i++;
/*     */       }
/*     */       
/* 247 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class PatternAbbreviator
/*     */     extends NameAbbreviator
/*     */   {
/*     */     private final NameAbbreviator.PatternAbbreviatorFragment[] fragments;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public PatternAbbreviator(List<NameAbbreviator.PatternAbbreviatorFragment> paramList)
/*     */     {
/* 266 */       if (paramList.size() == 0) {
/* 267 */         throw new IllegalArgumentException("fragments must have at least one element");
/*     */       }
/*     */       
/*     */ 
/* 271 */       this.fragments = new NameAbbreviator.PatternAbbreviatorFragment[paramList.size()];
/* 272 */       paramList.toArray(this.fragments);
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
/*     */     public String abbreviate(String paramString)
/*     */     {
/* 285 */       int i = 0;
/* 286 */       StringBuilder localStringBuilder = new StringBuilder(paramString);
/*     */       
/* 288 */       for (int j = 0; (j < this.fragments.length - 1) && (i < paramString.length()); 
/* 289 */           j++) {
/* 290 */         i = this.fragments[j].abbreviate(localStringBuilder, i);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 296 */       NameAbbreviator.PatternAbbreviatorFragment localPatternAbbreviatorFragment = this.fragments[(this.fragments.length - 1)];
/*     */       
/* 298 */       while ((i < paramString.length()) && (i >= 0)) {
/* 299 */         i = localPatternAbbreviatorFragment.abbreviate(localStringBuilder, i);
/*     */       }
/* 301 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\NameAbbreviator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */