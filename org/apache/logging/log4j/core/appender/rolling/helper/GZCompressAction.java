/*     */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public final class GZCompressAction
/*     */   extends AbstractAction
/*     */ {
/*     */   private static final int BUF_SIZE = 8102;
/*     */   private final File source;
/*     */   private final File destination;
/*     */   private final boolean deleteSource;
/*     */   
/*     */   public GZCompressAction(File paramFile1, File paramFile2, boolean paramBoolean)
/*     */   {
/*  57 */     if (paramFile1 == null) {
/*  58 */       throw new NullPointerException("source");
/*     */     }
/*     */     
/*  61 */     if (paramFile2 == null) {
/*  62 */       throw new NullPointerException("destination");
/*     */     }
/*     */     
/*  65 */     this.source = paramFile1;
/*  66 */     this.destination = paramFile2;
/*  67 */     this.deleteSource = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean execute()
/*     */     throws IOException
/*     */   {
/*  78 */     return execute(this.source, this.destination, this.deleteSource);
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
/*     */   public static boolean execute(File paramFile1, File paramFile2, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  93 */     if (paramFile1.exists()) {
/*  94 */       FileInputStream localFileInputStream = new FileInputStream(paramFile1);
/*  95 */       FileOutputStream localFileOutputStream = new FileOutputStream(paramFile2);
/*  96 */       GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localFileOutputStream);
/*  97 */       BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localGZIPOutputStream);
/*  98 */       byte[] arrayOfByte = new byte['ᾦ'];
/*     */       
/*     */       int i;
/* 101 */       while ((i = localFileInputStream.read(arrayOfByte)) != -1) {
/* 102 */         localBufferedOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/*     */       
/* 105 */       localBufferedOutputStream.close();
/* 106 */       localFileInputStream.close();
/*     */       
/* 108 */       if ((paramBoolean) && (!paramFile1.delete())) {
/* 109 */         LOGGER.warn("Unable to delete " + paramFile1.toString() + '.');
/*     */       }
/*     */       
/* 112 */       return true;
/*     */     }
/*     */     
/* 115 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void reportException(Exception paramException)
/*     */   {
/* 126 */     LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", paramException);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\GZCompressAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */