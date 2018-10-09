/*     */ package org.apache.commons.io.comparator;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
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
/*     */ public class ExtensionFileComparator
/*     */   extends AbstractFileComparator
/*     */   implements Serializable
/*     */ {
/*  57 */   public static final Comparator<File> EXTENSION_COMPARATOR = new ExtensionFileComparator();
/*     */   
/*     */ 
/*  60 */   public static final Comparator<File> EXTENSION_REVERSE = new ReverseComparator(EXTENSION_COMPARATOR);
/*     */   
/*     */ 
/*  63 */   public static final Comparator<File> EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
/*     */   
/*     */ 
/*     */ 
/*  67 */   public static final Comparator<File> EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(EXTENSION_INSENSITIVE_COMPARATOR);
/*     */   
/*     */ 
/*     */ 
/*  71 */   public static final Comparator<File> EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
/*     */   
/*     */ 
/*  74 */   public static final Comparator<File> EXTENSION_SYSTEM_REVERSE = new ReverseComparator(EXTENSION_SYSTEM_COMPARATOR);
/*     */   
/*     */ 
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */ 
/*     */ 
/*     */   public ExtensionFileComparator()
/*     */   {
/*  83 */     this.caseSensitivity = IOCase.SENSITIVE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExtensionFileComparator(IOCase paramIOCase)
/*     */   {
/*  92 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/* 107 */     String str1 = FilenameUtils.getExtension(paramFile1.getName());
/* 108 */     String str2 = FilenameUtils.getExtension(paramFile2.getName());
/* 109 */     return this.caseSensitivity.checkCompareTo(str1, str2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 119 */     return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\comparator\ExtensionFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */