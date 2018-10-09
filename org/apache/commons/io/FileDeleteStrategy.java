/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
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
/*     */ public class FileDeleteStrategy
/*     */ {
/*  40 */   public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  45 */   public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FileDeleteStrategy(String paramString)
/*     */   {
/*  57 */     this.name = paramString;
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
/*     */   public boolean deleteQuietly(File paramFile)
/*     */   {
/*  72 */     if ((paramFile == null) || (!paramFile.exists())) {
/*  73 */       return true;
/*     */     }
/*     */     try {
/*  76 */       return doDelete(paramFile);
/*     */     } catch (IOException localIOException) {}
/*  78 */     return false;
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
/*     */   public void delete(File paramFile)
/*     */     throws IOException
/*     */   {
/*  93 */     if ((paramFile.exists()) && (!doDelete(paramFile))) {
/*  94 */       throw new IOException("Deletion failed: " + paramFile);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean doDelete(File paramFile)
/*     */     throws IOException
/*     */   {
/* 115 */     return paramFile.delete();
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
/* 126 */     return "FileDeleteStrategy[" + this.name + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static class ForceFileDeleteStrategy
/*     */     extends FileDeleteStrategy
/*     */   {
/*     */     ForceFileDeleteStrategy()
/*     */     {
/* 136 */       super();
/*     */     }
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
/*     */     protected boolean doDelete(File paramFile)
/*     */       throws IOException
/*     */     {
/* 152 */       FileUtils.forceDelete(paramFile);
/* 153 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\FileDeleteStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */