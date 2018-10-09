/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FilenameUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WildcardFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final String[] wildcards;
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */   public WildcardFileFilter(String paramString)
/*     */   {
/*  65 */     this(paramString, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WildcardFileFilter(String paramString, IOCase paramIOCase)
/*     */   {
/*  76 */     if (paramString == null) {
/*  77 */       throw new IllegalArgumentException("The wildcard must not be null");
/*     */     }
/*  79 */     this.wildcards = new String[] { paramString };
/*  80 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public WildcardFileFilter(String[] paramArrayOfString)
/*     */   {
/*  93 */     this(paramArrayOfString, null);
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
/*     */   public WildcardFileFilter(String[] paramArrayOfString, IOCase paramIOCase)
/*     */   {
/* 107 */     if (paramArrayOfString == null) {
/* 108 */       throw new IllegalArgumentException("The wildcard array must not be null");
/*     */     }
/* 110 */     this.wildcards = new String[paramArrayOfString.length];
/* 111 */     System.arraycopy(paramArrayOfString, 0, this.wildcards, 0, paramArrayOfString.length);
/* 112 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WildcardFileFilter(List<String> paramList)
/*     */   {
/* 123 */     this(paramList, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WildcardFileFilter(List<String> paramList, IOCase paramIOCase)
/*     */   {
/* 135 */     if (paramList == null) {
/* 136 */       throw new IllegalArgumentException("The wildcard list must not be null");
/*     */     }
/* 138 */     this.wildcards = ((String[])paramList.toArray(new String[paramList.size()]));
/* 139 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public boolean accept(File paramFile, String paramString)
/*     */   {
/* 152 */     for (String str : this.wildcards) {
/* 153 */       if (FilenameUtils.wildcardMatch(paramString, str, this.caseSensitivity)) {
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
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 168 */     String str1 = paramFile.getName();
/* 169 */     for (String str2 : this.wildcards) {
/* 170 */       if (FilenameUtils.wildcardMatch(str1, str2, this.caseSensitivity)) {
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
/* 187 */     if (this.wildcards != null) {
/* 188 */       for (int i = 0; i < this.wildcards.length; i++) {
/* 189 */         if (i > 0) {
/* 190 */           localStringBuilder.append(",");
/*     */         }
/* 192 */         localStringBuilder.append(this.wildcards[i]);
/*     */       }
/*     */     }
/* 195 */     localStringBuilder.append(")");
/* 196 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\WildcardFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */