/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AndFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements ConditionalFileFilter, Serializable
/*     */ {
/*     */   private final List<IOFileFilter> fileFilters;
/*     */   
/*     */   public AndFileFilter()
/*     */   {
/*  50 */     this.fileFilters = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AndFileFilter(List<IOFileFilter> paramList)
/*     */   {
/*  61 */     if (paramList == null) {
/*  62 */       this.fileFilters = new ArrayList();
/*     */     } else {
/*  64 */       this.fileFilters = new ArrayList(paramList);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AndFileFilter(IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*     */   {
/*  76 */     if ((paramIOFileFilter1 == null) || (paramIOFileFilter2 == null)) {
/*  77 */       throw new IllegalArgumentException("The filters must not be null");
/*     */     }
/*  79 */     this.fileFilters = new ArrayList(2);
/*  80 */     addFileFilter(paramIOFileFilter1);
/*  81 */     addFileFilter(paramIOFileFilter2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addFileFilter(IOFileFilter paramIOFileFilter)
/*     */   {
/*  88 */     this.fileFilters.add(paramIOFileFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<IOFileFilter> getFileFilters()
/*     */   {
/*  95 */     return Collections.unmodifiableList(this.fileFilters);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean removeFileFilter(IOFileFilter paramIOFileFilter)
/*     */   {
/* 102 */     return this.fileFilters.remove(paramIOFileFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFileFilters(List<IOFileFilter> paramList)
/*     */   {
/* 109 */     this.fileFilters.clear();
/* 110 */     this.fileFilters.addAll(paramList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 118 */     if (this.fileFilters.isEmpty()) {
/* 119 */       return false;
/*     */     }
/* 121 */     for (IOFileFilter localIOFileFilter : this.fileFilters) {
/* 122 */       if (!localIOFileFilter.accept(paramFile)) {
/* 123 */         return false;
/*     */       }
/*     */     }
/* 126 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile, String paramString)
/*     */   {
/* 134 */     if (this.fileFilters.isEmpty()) {
/* 135 */       return false;
/*     */     }
/* 137 */     for (IOFileFilter localIOFileFilter : this.fileFilters) {
/* 138 */       if (!localIOFileFilter.accept(paramFile, paramString)) {
/* 139 */         return false;
/*     */       }
/*     */     }
/* 142 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 152 */     StringBuilder localStringBuilder = new StringBuilder();
/* 153 */     localStringBuilder.append(super.toString());
/* 154 */     localStringBuilder.append("(");
/* 155 */     if (this.fileFilters != null) {
/* 156 */       for (int i = 0; i < this.fileFilters.size(); i++) {
/* 157 */         if (i > 0) {
/* 158 */           localStringBuilder.append(",");
/*     */         }
/* 160 */         Object localObject = this.fileFilters.get(i);
/* 161 */         localStringBuilder.append(localObject == null ? "null" : localObject.toString());
/*     */       }
/*     */     }
/* 164 */     localStringBuilder.append(")");
/* 165 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\AndFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */