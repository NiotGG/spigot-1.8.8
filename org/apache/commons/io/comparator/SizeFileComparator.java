/*     */ package org.apache.commons.io.comparator;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SizeFileComparator
/*     */   extends AbstractFileComparator
/*     */   implements Serializable
/*     */ {
/*  55 */   public static final Comparator<File> SIZE_COMPARATOR = new SizeFileComparator();
/*     */   
/*     */ 
/*  58 */   public static final Comparator<File> SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  64 */   public static final Comparator<File> SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  70 */   public static final Comparator<File> SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
/*     */   
/*     */ 
/*     */   private final boolean sumDirectoryContents;
/*     */   
/*     */ 
/*     */ 
/*     */   public SizeFileComparator()
/*     */   {
/*  79 */     this.sumDirectoryContents = false;
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
/*     */   public SizeFileComparator(boolean paramBoolean)
/*     */   {
/*  94 */     this.sumDirectoryContents = paramBoolean;
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
/*     */   public int compare(File paramFile1, File paramFile2)
/*     */   {
/* 109 */     long l1 = 0L;
/* 110 */     if (paramFile1.isDirectory()) {
/* 111 */       l1 = (this.sumDirectoryContents) && (paramFile1.exists()) ? FileUtils.sizeOfDirectory(paramFile1) : 0L;
/*     */     } else {
/* 113 */       l1 = paramFile1.length();
/*     */     }
/* 115 */     long l2 = 0L;
/* 116 */     if (paramFile2.isDirectory()) {
/* 117 */       l2 = (this.sumDirectoryContents) && (paramFile2.exists()) ? FileUtils.sizeOfDirectory(paramFile2) : 0L;
/*     */     } else {
/* 119 */       l2 = paramFile2.length();
/*     */     }
/* 121 */     long l3 = l1 - l2;
/* 122 */     if (l3 < 0L)
/* 123 */       return -1;
/* 124 */     if (l3 > 0L) {
/* 125 */       return 1;
/*     */     }
/* 127 */     return 0;
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
/* 138 */     return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\comparator\SizeFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */