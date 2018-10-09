/*    */ package org.apache.commons.io.comparator;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ReverseComparator
/*    */   extends AbstractFileComparator
/*    */   implements Serializable
/*    */ {
/*    */   private final Comparator<File> delegate;
/*    */   
/*    */   public ReverseComparator(Comparator<File> paramComparator)
/*    */   {
/* 40 */     if (paramComparator == null) {
/* 41 */       throw new IllegalArgumentException("Delegate comparator is missing");
/*    */     }
/* 43 */     this.delegate = paramComparator;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compare(File paramFile1, File paramFile2)
/*    */   {
/* 55 */     return this.delegate.compare(paramFile2, paramFile1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 65 */     return super.toString() + "[" + this.delegate.toString() + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\comparator\ReverseComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */