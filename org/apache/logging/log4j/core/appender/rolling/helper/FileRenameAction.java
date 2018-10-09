/*     */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ public class FileRenameAction
/*     */   extends AbstractAction
/*     */ {
/*     */   private final File source;
/*     */   private final File destination;
/*     */   private final boolean renameEmptyFiles;
/*     */   
/*     */   public FileRenameAction(File paramFile1, File paramFile2, boolean paramBoolean)
/*     */   {
/*  53 */     this.source = paramFile1;
/*  54 */     this.destination = paramFile2;
/*  55 */     this.renameEmptyFiles = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean execute()
/*     */   {
/*  65 */     return execute(this.source, this.destination, this.renameEmptyFiles);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean execute(File paramFile1, File paramFile2, boolean paramBoolean)
/*     */   {
/*  77 */     if ((paramBoolean) || (paramFile1.length() > 0L)) {
/*  78 */       File localFile = paramFile2.getParentFile();
/*  79 */       if ((localFile != null) && (!localFile.exists()) && 
/*  80 */         (!localFile.mkdirs())) {
/*  81 */         LOGGER.error("Unable to create directory {}", new Object[] { localFile.getAbsolutePath() });
/*  82 */         return false;
/*     */       }
/*     */       try
/*     */       {
/*  86 */         if (!paramFile1.renameTo(paramFile2)) {
/*     */           try {
/*  88 */             copyFile(paramFile1, paramFile2);
/*  89 */             return paramFile1.delete();
/*     */           } catch (IOException localIOException1) {
/*  91 */             LOGGER.error("Unable to rename file {} to {} - {}", new Object[] { paramFile1.getAbsolutePath(), paramFile2.getAbsolutePath(), localIOException1.getMessage() });
/*     */           }
/*     */         }
/*     */         
/*  95 */         return true;
/*     */       } catch (Exception localException2) {
/*     */         try {
/*  98 */           copyFile(paramFile1, paramFile2);
/*  99 */           return paramFile1.delete();
/*     */         } catch (IOException localIOException2) {
/* 101 */           LOGGER.error("Unable to rename file {} to {} - {}", new Object[] { paramFile1.getAbsolutePath(), paramFile2.getAbsolutePath(), localIOException2.getMessage() });
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*     */       try {
/* 107 */         paramFile1.delete();
/*     */       } catch (Exception localException1) {
/* 109 */         LOGGER.error("Unable to delete empty file " + paramFile1.getAbsolutePath());
/*     */       }
/*     */     }
/*     */     
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   private static void copyFile(File paramFile1, File paramFile2) throws IOException {
/* 117 */     if (!paramFile2.exists()) {
/* 118 */       paramFile2.createNewFile();
/*     */     }
/*     */     
/* 121 */     FileChannel localFileChannel1 = null;
/* 122 */     FileChannel localFileChannel2 = null;
/* 123 */     FileInputStream localFileInputStream = null;
/* 124 */     FileOutputStream localFileOutputStream = null;
/*     */     try {
/* 126 */       localFileInputStream = new FileInputStream(paramFile1);
/* 127 */       localFileOutputStream = new FileOutputStream(paramFile2);
/* 128 */       localFileChannel1 = localFileInputStream.getChannel();
/* 129 */       localFileChannel2 = localFileOutputStream.getChannel();
/* 130 */       localFileChannel2.transferFrom(localFileChannel1, 0L, localFileChannel1.size());
/*     */     } finally {
/* 132 */       if (localFileChannel1 != null) {
/* 133 */         localFileChannel1.close();
/*     */       }
/* 135 */       if (localFileInputStream != null) {
/* 136 */         localFileInputStream.close();
/*     */       }
/* 138 */       if (localFileChannel2 != null) {
/* 139 */         localFileChannel2.close();
/*     */       }
/* 141 */       if (localFileOutputStream != null) {
/* 142 */         localFileOutputStream.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\FileRenameAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */