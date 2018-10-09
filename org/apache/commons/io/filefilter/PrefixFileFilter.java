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
/*     */ public class PrefixFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final String[] prefixes;
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */   public PrefixFileFilter(String paramString)
/*     */   {
/*  59 */     this(paramString, IOCase.SENSITIVE);
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
/*     */   public PrefixFileFilter(String paramString, IOCase paramIOCase)
/*     */   {
/*  72 */     if (paramString == null) {
/*  73 */       throw new IllegalArgumentException("The prefix must not be null");
/*     */     }
/*  75 */     this.prefixes = new String[] { paramString };
/*  76 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public PrefixFileFilter(String[] paramArrayOfString)
/*     */   {
/*  89 */     this(paramArrayOfString, IOCase.SENSITIVE);
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
/*     */   public PrefixFileFilter(String[] paramArrayOfString, IOCase paramIOCase)
/*     */   {
/* 105 */     if (paramArrayOfString == null) {
/* 106 */       throw new IllegalArgumentException("The array of prefixes must not be null");
/*     */     }
/* 108 */     this.prefixes = new String[paramArrayOfString.length];
/* 109 */     System.arraycopy(paramArrayOfString, 0, this.prefixes, 0, paramArrayOfString.length);
/* 110 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PrefixFileFilter(List<String> paramList)
/*     */   {
/* 121 */     this(paramList, IOCase.SENSITIVE);
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
/*     */   public PrefixFileFilter(List<String> paramList, IOCase paramIOCase)
/*     */   {
/* 135 */     if (paramList == null) {
/* 136 */       throw new IllegalArgumentException("The list of prefixes must not be null");
/*     */     }
/* 138 */     this.prefixes = ((String[])paramList.toArray(new String[paramList.size()]));
/* 139 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/* 150 */     String str1 = paramFile.getName();
/* 151 */     for (String str2 : this.prefixes) {
/* 152 */       if (this.caseSensitivity.checkStartsWith(str1, str2)) {
/* 153 */         return true;
/*     */       }
/*     */     }
/* 156 */     return false;
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
/* 168 */     for (String str : this.prefixes) {
/* 169 */       if (this.caseSensitivity.checkStartsWith(paramString, str)) {
/* 170 */         return true;
/*     */       }
/*     */     }
/* 173 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 183 */     StringBuilder localStringBuilder = new StringBuilder();
/* 184 */     localStringBuilder.append(super.toString());
/* 185 */     localStringBuilder.append("(");
/* 186 */     if (this.prefixes != null) {
/* 187 */       for (int i = 0; i < this.prefixes.length; i++) {
/* 188 */         if (i > 0) {
/* 189 */           localStringBuilder.append(",");
/*     */         }
/* 191 */         localStringBuilder.append(this.prefixes[i]);
/*     */       }
/*     */     }
/* 194 */     localStringBuilder.append(")");
/* 195 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\PrefixFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */