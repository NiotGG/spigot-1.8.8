/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.IOCase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegexFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final Pattern pattern;
/*     */   
/*     */   public RegexFileFilter(String paramString)
/*     */   {
/*  57 */     if (paramString == null) {
/*  58 */       throw new IllegalArgumentException("Pattern is missing");
/*     */     }
/*     */     
/*  61 */     this.pattern = Pattern.compile(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegexFileFilter(String paramString, IOCase paramIOCase)
/*     */   {
/*  72 */     if (paramString == null) {
/*  73 */       throw new IllegalArgumentException("Pattern is missing");
/*     */     }
/*  75 */     int i = 0;
/*  76 */     if ((paramIOCase != null) && (!paramIOCase.isCaseSensitive())) {
/*  77 */       i = 2;
/*     */     }
/*  79 */     this.pattern = Pattern.compile(paramString, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegexFileFilter(String paramString, int paramInt)
/*     */   {
/*  90 */     if (paramString == null) {
/*  91 */       throw new IllegalArgumentException("Pattern is missing");
/*     */     }
/*  93 */     this.pattern = Pattern.compile(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegexFileFilter(Pattern paramPattern)
/*     */   {
/* 103 */     if (paramPattern == null) {
/* 104 */       throw new IllegalArgumentException("Pattern is missing");
/*     */     }
/*     */     
/* 107 */     this.pattern = paramPattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile, String paramString)
/*     */   {
/* 119 */     return this.pattern.matcher(paramString).matches();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\RegexFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */