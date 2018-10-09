/*     */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZipCompressAction
/*     */   extends AbstractAction
/*     */ {
/*     */   private static final int BUF_SIZE = 8102;
/*     */   private final File source;
/*     */   private final File destination;
/*     */   private final boolean deleteSource;
/*     */   private final int level;
/*     */   
/*     */   public ZipCompressAction(File paramFile1, File paramFile2, boolean paramBoolean, int paramInt)
/*     */   {
/*  64 */     if (paramFile1 == null) {
/*  65 */       throw new NullPointerException("source");
/*     */     }
/*     */     
/*  68 */     if (paramFile2 == null) {
/*  69 */       throw new NullPointerException("destination");
/*     */     }
/*     */     
/*  72 */     this.source = paramFile1;
/*  73 */     this.destination = paramFile2;
/*  74 */     this.deleteSource = paramBoolean;
/*  75 */     this.level = paramInt;
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
/*  86 */     return execute(this.source, this.destination, this.deleteSource, this.level);
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
/*     */   public static boolean execute(File paramFile1, File paramFile2, boolean paramBoolean, int paramInt)
/*     */     throws IOException
/*     */   {
/* 102 */     if (paramFile1.exists()) {
/* 103 */       FileInputStream localFileInputStream = new FileInputStream(paramFile1);
/* 104 */       FileOutputStream localFileOutputStream = new FileOutputStream(paramFile2);
/* 105 */       ZipOutputStream localZipOutputStream = new ZipOutputStream(localFileOutputStream);
/* 106 */       localZipOutputStream.setLevel(paramInt);
/*     */       
/* 108 */       ZipEntry localZipEntry = new ZipEntry(paramFile1.getName());
/* 109 */       localZipOutputStream.putNextEntry(localZipEntry);
/*     */       
/* 111 */       byte[] arrayOfByte = new byte['ᾦ'];
/*     */       
/*     */       int i;
/* 114 */       while ((i = localFileInputStream.read(arrayOfByte)) != -1) {
/* 115 */         localZipOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/*     */       
/* 118 */       localZipOutputStream.close();
/* 119 */       localFileInputStream.close();
/*     */       
/* 121 */       if ((paramBoolean) && (!paramFile1.delete())) {
/* 122 */         LOGGER.warn("Unable to delete " + paramFile1.toString() + '.');
/*     */       }
/*     */       
/* 125 */       return true;
/*     */     }
/*     */     
/* 128 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void reportException(Exception paramException)
/*     */   {
/* 138 */     LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", paramException);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\ZipCompressAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */