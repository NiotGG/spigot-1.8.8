/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DelegateFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private final FilenameFilter filenameFilter;
/*     */   private final FileFilter fileFilter;
/*     */   
/*     */   public DelegateFileFilter(FilenameFilter paramFilenameFilter)
/*     */   {
/*  46 */     if (paramFilenameFilter == null) {
/*  47 */       throw new IllegalArgumentException("The FilenameFilter must not be null");
/*     */     }
/*  49 */     this.filenameFilter = paramFilenameFilter;
/*  50 */     this.fileFilter = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DelegateFileFilter(FileFilter paramFileFilter)
/*     */   {
/*  59 */     if (paramFileFilter == null) {
/*  60 */       throw new IllegalArgumentException("The FileFilter must not be null");
/*     */     }
/*  62 */     this.fileFilter = paramFileFilter;
/*  63 */     this.filenameFilter = null;
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
/*  74 */     if (this.fileFilter != null) {
/*  75 */       return this.fileFilter.accept(paramFile);
/*     */     }
/*  77 */     return super.accept(paramFile);
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
/*  90 */     if (this.filenameFilter != null) {
/*  91 */       return this.filenameFilter.accept(paramFile, paramString);
/*     */     }
/*  93 */     return super.accept(paramFile, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 104 */     String str = this.fileFilter != null ? this.fileFilter.toString() : this.filenameFilter.toString();
/* 105 */     return super.toString() + "(" + str + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\DelegateFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */