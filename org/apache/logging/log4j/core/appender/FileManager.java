/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileManager
/*     */   extends OutputStreamManager
/*     */ {
/*  39 */   private static final FileManagerFactory FACTORY = new FileManagerFactory(null);
/*     */   
/*     */   private final boolean isAppend;
/*     */   private final boolean isLocking;
/*     */   private final String advertiseURI;
/*     */   
/*     */   protected FileManager(String paramString1, OutputStream paramOutputStream, boolean paramBoolean1, boolean paramBoolean2, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  47 */     super(paramOutputStream, paramString1, paramLayout);
/*  48 */     this.isAppend = paramBoolean1;
/*  49 */     this.isLocking = paramBoolean2;
/*  50 */     this.advertiseURI = paramString2;
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
/*     */   public static FileManager getFileManager(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  67 */     if ((paramBoolean2) && (paramBoolean3)) {
/*  68 */       paramBoolean2 = false;
/*     */     }
/*  70 */     return (FileManager)getManager(paramString1, new FactoryData(paramBoolean1, paramBoolean2, paramBoolean3, paramString2, paramLayout), FACTORY);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  77 */     if (this.isLocking) {
/*  78 */       FileChannel localFileChannel = ((FileOutputStream)getOutputStream()).getChannel();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/*  87 */         FileLock localFileLock = localFileChannel.lock(0L, Long.MAX_VALUE, false);
/*     */         try {
/*  89 */           super.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */         } finally {
/*  91 */           localFileLock.release();
/*     */         }
/*     */       } catch (IOException localIOException) {
/*  94 */         throw new AppenderLoggingException("Unable to obtain lock on " + getName(), localIOException);
/*     */       }
/*     */     }
/*     */     else {
/*  98 */       super.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 107 */     return getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAppend()
/*     */   {
/* 115 */     return this.isAppend;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLocking()
/*     */   {
/* 123 */     return this.isLocking;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 133 */     HashMap localHashMap = new HashMap(super.getContentFormat());
/* 134 */     localHashMap.put("fileURI", this.advertiseURI);
/* 135 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final boolean append;
/*     */     
/*     */ 
/*     */     private final boolean locking;
/*     */     
/*     */ 
/*     */     private final boolean bufferedIO;
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */ 
/*     */     public FactoryData(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString, Layout<? extends Serializable> paramLayout)
/*     */     {
/* 157 */       this.append = paramBoolean1;
/* 158 */       this.locking = paramBoolean2;
/* 159 */       this.bufferedIO = paramBoolean3;
/* 160 */       this.advertiseURI = paramString;
/* 161 */       this.layout = paramLayout;
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
/*     */   private static class FileManagerFactory
/*     */     implements ManagerFactory<FileManager, FileManager.FactoryData>
/*     */   {
/*     */     public FileManager createManager(String paramString, FileManager.FactoryData paramFactoryData)
/*     */     {
/* 178 */       File localFile1 = new File(paramString);
/* 179 */       File localFile2 = localFile1.getParentFile();
/* 180 */       if ((null != localFile2) && (!localFile2.exists())) {
/* 181 */         localFile2.mkdirs();
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 186 */         Object localObject = new FileOutputStream(paramString, FileManager.FactoryData.access$100(paramFactoryData));
/* 187 */         if (FileManager.FactoryData.access$200(paramFactoryData)) {
/* 188 */           localObject = new BufferedOutputStream((OutputStream)localObject);
/*     */         }
/* 190 */         return new FileManager(paramString, (OutputStream)localObject, FileManager.FactoryData.access$100(paramFactoryData), FileManager.FactoryData.access$300(paramFactoryData), FileManager.FactoryData.access$400(paramFactoryData), FileManager.FactoryData.access$500(paramFactoryData));
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/* 192 */         AbstractManager.LOGGER.error("FileManager (" + paramString + ") " + localFileNotFoundException);
/*     */       }
/* 194 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */