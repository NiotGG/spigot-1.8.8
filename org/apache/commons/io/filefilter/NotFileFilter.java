/*    */ package org.apache.commons.io.filefilter;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ public class NotFileFilter
/*    */   extends AbstractFileFilter
/*    */   implements Serializable
/*    */ {
/*    */   private final IOFileFilter filter;
/*    */   
/*    */   public NotFileFilter(IOFileFilter paramIOFileFilter)
/*    */   {
/* 41 */     if (paramIOFileFilter == null) {
/* 42 */       throw new IllegalArgumentException("The filter must not be null");
/*    */     }
/* 44 */     this.filter = paramIOFileFilter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean accept(File paramFile)
/*    */   {
/* 55 */     return !this.filter.accept(paramFile);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean accept(File paramFile, String paramString)
/*    */   {
/* 67 */     return !this.filter.accept(paramFile, paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 77 */     return super.toString() + "(" + this.filter.toString() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\NotFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */