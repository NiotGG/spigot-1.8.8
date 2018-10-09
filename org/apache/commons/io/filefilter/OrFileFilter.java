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
/*     */ public class OrFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements ConditionalFileFilter, Serializable
/*     */ {
/*     */   private final List<IOFileFilter> fileFilters;
/*     */   
/*     */   public OrFileFilter()
/*     */   {
/*  49 */     this.fileFilters = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OrFileFilter(List<IOFileFilter> paramList)
/*     */   {
/*  60 */     if (paramList == null) {
/*  61 */       this.fileFilters = new ArrayList();
/*     */     } else {
/*  63 */       this.fileFilters = new ArrayList(paramList);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OrFileFilter(IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*     */   {
/*  75 */     if ((paramIOFileFilter1 == null) || (paramIOFileFilter2 == null)) {
/*  76 */       throw new IllegalArgumentException("The filters must not be null");
/*     */     }
/*  78 */     this.fileFilters = new ArrayList(2);
/*  79 */     addFileFilter(paramIOFileFilter1);
/*  80 */     addFileFilter(paramIOFileFilter2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addFileFilter(IOFileFilter paramIOFileFilter)
/*     */   {
/*  87 */     this.fileFilters.add(paramIOFileFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<IOFileFilter> getFileFilters()
/*     */   {
/*  94 */     return Collections.unmodifiableList(this.fileFilters);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean removeFileFilter(IOFileFilter paramIOFileFilter)
/*     */   {
/* 101 */     return this.fileFilters.remove(paramIOFileFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFileFilters(List<IOFileFilter> paramList)
/*     */   {
/* 108 */     this.fileFilters.clear();
/* 109 */     this.fileFilters.addAll(paramList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 117 */     for (IOFileFilter localIOFileFilter : this.fileFilters) {
/* 118 */       if (localIOFileFilter.accept(paramFile)) {
/* 119 */         return true;
/*     */       }
/*     */     }
/* 122 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean accept(File paramFile, String paramString)
/*     */   {
/* 130 */     for (IOFileFilter localIOFileFilter : this.fileFilters) {
/* 131 */       if (localIOFileFilter.accept(paramFile, paramString)) {
/* 132 */         return true;
/*     */       }
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 145 */     StringBuilder localStringBuilder = new StringBuilder();
/* 146 */     localStringBuilder.append(super.toString());
/* 147 */     localStringBuilder.append("(");
/* 148 */     if (this.fileFilters != null) {
/* 149 */       for (int i = 0; i < this.fileFilters.size(); i++) {
/* 150 */         if (i > 0) {
/* 151 */           localStringBuilder.append(",");
/*     */         }
/* 153 */         Object localObject = this.fileFilters.get(i);
/* 154 */         localStringBuilder.append(localObject == null ? "null" : localObject.toString());
/*     */       }
/*     */     }
/* 157 */     localStringBuilder.append(")");
/* 158 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\OrFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */