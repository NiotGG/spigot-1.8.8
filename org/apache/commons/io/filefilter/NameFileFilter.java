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
/*     */ public class NameFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final String[] names;
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */   public NameFileFilter(String paramString)
/*     */   {
/*  58 */     this(paramString, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameFileFilter(String paramString, IOCase paramIOCase)
/*     */   {
/*  69 */     if (paramString == null) {
/*  70 */       throw new IllegalArgumentException("The wildcard must not be null");
/*     */     }
/*  72 */     this.names = new String[] { paramString };
/*  73 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public NameFileFilter(String[] paramArrayOfString)
/*     */   {
/*  86 */     this(paramArrayOfString, null);
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
/*     */   public NameFileFilter(String[] paramArrayOfString, IOCase paramIOCase)
/*     */   {
/* 100 */     if (paramArrayOfString == null) {
/* 101 */       throw new IllegalArgumentException("The array of names must not be null");
/*     */     }
/* 103 */     this.names = new String[paramArrayOfString.length];
/* 104 */     System.arraycopy(paramArrayOfString, 0, this.names, 0, paramArrayOfString.length);
/* 105 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameFileFilter(List<String> paramList)
/*     */   {
/* 116 */     this(paramList, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameFileFilter(List<String> paramList, IOCase paramIOCase)
/*     */   {
/* 128 */     if (paramList == null) {
/* 129 */       throw new IllegalArgumentException("The list of names must not be null");
/*     */     }
/* 131 */     this.names = ((String[])paramList.toArray(new String[paramList.size()]));
/* 132 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 144 */     String str1 = paramFile.getName();
/* 145 */     for (String str2 : this.names) {
/* 146 */       if (this.caseSensitivity.checkEquals(str1, str2)) {
/* 147 */         return true;
/*     */       }
/*     */     }
/* 150 */     return false;
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
/* 162 */     for (String str : this.names) {
/* 163 */       if (this.caseSensitivity.checkEquals(paramString, str)) {
/* 164 */         return true;
/*     */       }
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     StringBuilder localStringBuilder = new StringBuilder();
/* 178 */     localStringBuilder.append(super.toString());
/* 179 */     localStringBuilder.append("(");
/* 180 */     if (this.names != null) {
/* 181 */       for (int i = 0; i < this.names.length; i++) {
/* 182 */         if (i > 0) {
/* 183 */           localStringBuilder.append(",");
/*     */         }
/* 185 */         localStringBuilder.append(this.names[i]);
/*     */       }
/*     */     }
/* 188 */     localStringBuilder.append(")");
/* 189 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\NameFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */