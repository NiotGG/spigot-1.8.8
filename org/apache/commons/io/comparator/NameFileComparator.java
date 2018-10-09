/*     */ package org.apache.commons.io.comparator;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
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
/*     */ public class NameFileComparator
/*     */   extends AbstractFileComparator
/*     */   implements Serializable
/*     */ {
/*  55 */   public static final Comparator<File> NAME_COMPARATOR = new NameFileComparator();
/*     */   
/*     */ 
/*  58 */   public static final Comparator<File> NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
/*     */   
/*     */ 
/*  61 */   public static final Comparator<File> NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
/*     */   
/*     */ 
/*  64 */   public static final Comparator<File> NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
/*     */   
/*     */ 
/*  67 */   public static final Comparator<File> NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
/*     */   
/*     */ 
/*  70 */   public static final Comparator<File> NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);
/*     */   
/*     */ 
/*     */   private final IOCase caseSensitivity;
/*     */   
/*     */ 
/*     */ 
/*     */   public NameFileComparator()
/*     */   {
/*  79 */     this.caseSensitivity = IOCase.SENSITIVE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NameFileComparator(IOCase paramIOCase)
/*     */   {
/*  88 */     this.caseSensitivity = (paramIOCase == null ? IOCase.SENSITIVE : paramIOCase);
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
/*     */   public int compare(File paramFile1, File paramFile2)
/*     */   {
/* 102 */     return this.caseSensitivity.checkCompareTo(paramFile1.getName(), paramFile2.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\comparator\NameFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */