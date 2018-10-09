/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SuffixFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final String[] suffixes;
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */   public SuffixFileFilter(String paramString)
/*     */   {
/*  60 */     this(paramString, IOCase.SENSITIVE);
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
/*     */   public SuffixFileFilter(String paramString, IOCase paramIOCase)
/*     */   {
/*  73 */     if (paramString == null) {
/*  74 */       throw new IllegalArgumentException("The suffix must not be null");
/*     */     }
/*  76 */     this.suffixes = new String[] { paramString };
/*  77 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public SuffixFileFilter(String[] paramArrayOfString)
/*     */   {
/*  90 */     this(paramArrayOfString, IOCase.SENSITIVE);
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
/*     */   public SuffixFileFilter(String[] paramArrayOfString, IOCase paramIOCase)
/*     */   {
/* 106 */     if (paramArrayOfString == null) {
/* 107 */       throw new IllegalArgumentException("The array of suffixes must not be null");
/*     */     }
/* 109 */     this.suffixes = new String[paramArrayOfString.length];
/* 110 */     System.arraycopy(paramArrayOfString, 0, this.suffixes, 0, paramArrayOfString.length);
/* 111 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SuffixFileFilter(List<String> paramList)
/*     */   {
/* 122 */     this(paramList, IOCase.SENSITIVE);
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
/*     */   public SuffixFileFilter(List<String> paramList, IOCase paramIOCase)
/*     */   {
/* 136 */     if (paramList == null) {
/* 137 */       throw new IllegalArgumentException("The list of suffixes must not be null");
/*     */     }
/* 139 */     this.suffixes = ((String[])paramList.toArray(new String[paramList.size()]));
/* 140 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 151 */     String str1 = paramFile.getName();
/* 152 */     for (String str2 : this.suffixes) {
/* 153 */       if (this.caseSensitivity.checkEndsWith(str1, str2)) {
/* 154 */         return true;
/*     */       }
/*     */     }
/* 157 */     return false;
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
/* 169 */     for (String str : this.suffixes) {
/* 170 */       if (this.caseSensitivity.checkEndsWith(paramString, str)) {
/* 171 */         return true;
/*     */       }
/*     */     }
/* 174 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 184 */     StringBuilder localStringBuilder = new StringBuilder();
/* 185 */     localStringBuilder.append(super.toString());
/* 186 */     localStringBuilder.append("(");
/* 187 */     if (this.suffixes != null) {
/* 188 */       for (int i = 0; i < this.suffixes.length; i++) {
/* 189 */         if (i > 0) {
/* 190 */           localStringBuilder.append(",");
/*     */         }
/* 192 */         localStringBuilder.append(this.suffixes[i]);
/*     */       }
/*     */     }
/* 195 */     localStringBuilder.append(")");
/* 196 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\SuffixFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */