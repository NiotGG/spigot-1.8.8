/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ public class RandomAccessFileManager
/*     */   extends OutputStreamManager
/*     */ {
/*     */   static final int DEFAULT_BUFFER_SIZE = 262144;
/*  38 */   private static final RandomAccessFileManagerFactory FACTORY = new RandomAccessFileManagerFactory(null);
/*     */   
/*     */   private final boolean isImmediateFlush;
/*     */   private final String advertiseURI;
/*     */   private final RandomAccessFile randomAccessFile;
/*     */   private final ByteBuffer buffer;
/*  44 */   private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */   protected RandomAccessFileManager(RandomAccessFile paramRandomAccessFile, String paramString1, OutputStream paramOutputStream, boolean paramBoolean, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  50 */     super(paramOutputStream, paramString1, paramLayout);
/*  51 */     this.isImmediateFlush = paramBoolean;
/*  52 */     this.randomAccessFile = paramRandomAccessFile;
/*  53 */     this.advertiseURI = paramString2;
/*  54 */     this.isEndOfBatch.set(Boolean.FALSE);
/*     */     
/*     */ 
/*  57 */     this.buffer = ByteBuffer.allocate(262144);
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
/*     */   public static RandomAccessFileManager getFileManager(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  75 */     return (RandomAccessFileManager)getManager(paramString1, new FactoryData(paramBoolean1, paramBoolean2, paramString2, paramLayout), FACTORY);
/*     */   }
/*     */   
/*     */   public Boolean isEndOfBatch()
/*     */   {
/*  80 */     return (Boolean)this.isEndOfBatch.get();
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean paramBoolean) {
/*  84 */     this.isEndOfBatch.set(Boolean.valueOf(paramBoolean));
/*     */   }
/*     */   
/*     */   protected synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  89 */     super.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */     
/*  91 */     int i = 0;
/*     */     do {
/*  93 */       if (paramInt2 > this.buffer.remaining()) {
/*  94 */         flush();
/*     */       }
/*  96 */       i = Math.min(paramInt2, this.buffer.remaining());
/*  97 */       this.buffer.put(paramArrayOfByte, paramInt1, i);
/*  98 */       paramInt1 += i;
/*  99 */       paramInt2 -= i;
/* 100 */     } while (paramInt2 > 0);
/*     */     
/* 102 */     if ((this.isImmediateFlush) || (this.isEndOfBatch.get() == Boolean.TRUE)) {
/* 103 */       flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void flush()
/*     */   {
/* 109 */     this.buffer.flip();
/*     */     try {
/* 111 */       this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
/*     */     } catch (IOException localIOException) {
/* 113 */       String str = "Error writing to RandomAccessFile " + getName();
/* 114 */       throw new AppenderLoggingException(str, localIOException);
/*     */     }
/* 116 */     this.buffer.clear();
/*     */   }
/*     */   
/*     */   public synchronized void close()
/*     */   {
/* 121 */     flush();
/*     */     try {
/* 123 */       this.randomAccessFile.close();
/*     */     } catch (IOException localIOException) {
/* 125 */       LOGGER.error("Unable to close RandomAccessFile " + getName() + ". " + localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 136 */     return getName();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 159 */     HashMap localHashMap = new HashMap(super.getContentFormat());
/*     */     
/* 161 */     localHashMap.put("fileURI", this.advertiseURI);
/* 162 */     return localHashMap;
/*     */   }
/*     */   
/*     */   static class DummyOutputStream extends OutputStream
/*     */   {
/*     */     public void write(int paramInt) throws IOException
/*     */     {}
/*     */     
/*     */     public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {}
/*     */   }
/*     */   
/*     */   private static class FactoryData {
/*     */     private final boolean append;
/*     */     private final boolean immediateFlush;
/*     */     private final String advertiseURI;
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(boolean paramBoolean1, boolean paramBoolean2, String paramString, Layout<? extends Serializable> paramLayout) {
/* 181 */       this.append = paramBoolean1;
/* 182 */       this.immediateFlush = paramBoolean2;
/* 183 */       this.advertiseURI = paramString;
/* 184 */       this.layout = paramLayout;
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
/*     */   private static class RandomAccessFileManagerFactory
/*     */     implements ManagerFactory<RandomAccessFileManager, RandomAccessFileManager.FactoryData>
/*     */   {
/*     */     public RandomAccessFileManager createManager(String paramString, RandomAccessFileManager.FactoryData paramFactoryData)
/*     */     {
/* 203 */       File localFile1 = new File(paramString);
/* 204 */       File localFile2 = localFile1.getParentFile();
/* 205 */       if ((null != localFile2) && (!localFile2.exists())) {
/* 206 */         localFile2.mkdirs();
/*     */       }
/* 208 */       if (!RandomAccessFileManager.FactoryData.access$100(paramFactoryData)) {
/* 209 */         localFile1.delete();
/*     */       }
/*     */       
/* 212 */       RandomAccessFileManager.DummyOutputStream localDummyOutputStream = new RandomAccessFileManager.DummyOutputStream();
/*     */       try
/*     */       {
/* 215 */         RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString, "rw");
/* 216 */         if (RandomAccessFileManager.FactoryData.access$100(paramFactoryData)) {
/* 217 */           localRandomAccessFile.seek(localRandomAccessFile.length());
/*     */         } else {
/* 219 */           localRandomAccessFile.setLength(0L);
/*     */         }
/* 221 */         return new RandomAccessFileManager(localRandomAccessFile, paramString, localDummyOutputStream, RandomAccessFileManager.FactoryData.access$200(paramFactoryData), RandomAccessFileManager.FactoryData.access$300(paramFactoryData), RandomAccessFileManager.FactoryData.access$400(paramFactoryData));
/*     */       }
/*     */       catch (Exception localException) {
/* 224 */         AbstractManager.LOGGER.error("RandomAccessFileManager (" + paramString + ") " + localException);
/*     */       }
/* 226 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RandomAccessFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */