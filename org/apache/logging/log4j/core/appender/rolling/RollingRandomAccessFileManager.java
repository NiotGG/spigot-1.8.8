/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
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
/*     */ public class RollingRandomAccessFileManager
/*     */   extends RollingFileManager
/*     */ {
/*     */   static final int DEFAULT_BUFFER_SIZE = 262144;
/*  38 */   private static final RollingRandomAccessFileManagerFactory FACTORY = new RollingRandomAccessFileManagerFactory(null);
/*     */   
/*     */   private final boolean isImmediateFlush;
/*     */   private RandomAccessFile randomAccessFile;
/*     */   private final ByteBuffer buffer;
/*  43 */   private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RollingRandomAccessFileManager(RandomAccessFile paramRandomAccessFile, String paramString1, String paramString2, OutputStream paramOutputStream, boolean paramBoolean1, boolean paramBoolean2, long paramLong1, long paramLong2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString3, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  50 */     super(paramString1, paramString2, paramOutputStream, paramBoolean1, paramLong1, paramLong2, paramTriggeringPolicy, paramRolloverStrategy, paramString3, paramLayout);
/*  51 */     this.isImmediateFlush = paramBoolean2;
/*  52 */     this.randomAccessFile = paramRandomAccessFile;
/*  53 */     this.isEndOfBatch.set(Boolean.FALSE);
/*     */     
/*     */ 
/*  56 */     this.buffer = ByteBuffer.allocate(262144);
/*     */   }
/*     */   
/*     */ 
/*     */   public static RollingRandomAccessFileManager getRollingRandomAccessFileManager(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString3, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  62 */     return (RollingRandomAccessFileManager)getManager(paramString1, new FactoryData(paramString2, paramBoolean1, paramBoolean2, paramTriggeringPolicy, paramRolloverStrategy, paramString3, paramLayout), FACTORY);
/*     */   }
/*     */   
/*     */   public Boolean isEndOfBatch()
/*     */   {
/*  67 */     return (Boolean)this.isEndOfBatch.get();
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean paramBoolean) {
/*  71 */     this.isEndOfBatch.set(Boolean.valueOf(paramBoolean));
/*     */   }
/*     */   
/*     */   protected synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  76 */     super.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */     
/*  78 */     int i = 0;
/*     */     do {
/*  80 */       if (paramInt2 > this.buffer.remaining()) {
/*  81 */         flush();
/*     */       }
/*  83 */       i = Math.min(paramInt2, this.buffer.remaining());
/*  84 */       this.buffer.put(paramArrayOfByte, paramInt1, i);
/*  85 */       paramInt1 += i;
/*  86 */       paramInt2 -= i;
/*  87 */     } while (paramInt2 > 0);
/*     */     
/*  89 */     if ((this.isImmediateFlush) || (this.isEndOfBatch.get() == Boolean.TRUE)) {
/*  90 */       flush();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void createFileAfterRollover() throws IOException
/*     */   {
/*  96 */     this.randomAccessFile = new RandomAccessFile(getFileName(), "rw");
/*  97 */     if (isAppend()) {
/*  98 */       this.randomAccessFile.seek(this.randomAccessFile.length());
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void flush()
/*     */   {
/* 104 */     this.buffer.flip();
/*     */     try {
/* 106 */       this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
/*     */     } catch (IOException localIOException) {
/* 108 */       String str = "Error writing to RandomAccessFile " + getName();
/* 109 */       throw new AppenderLoggingException(str, localIOException);
/*     */     }
/* 111 */     this.buffer.clear();
/*     */   }
/*     */   
/*     */   public synchronized void close()
/*     */   {
/* 116 */     flush();
/*     */     try {
/* 118 */       this.randomAccessFile.close();
/*     */     } catch (IOException localIOException) {
/* 120 */       LOGGER.error("Unable to close RandomAccessFile " + getName() + ". " + localIOException);
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
/*     */   private static class RollingRandomAccessFileManagerFactory
/*     */     implements ManagerFactory<RollingRandomAccessFileManager, RollingRandomAccessFileManager.FactoryData>
/*     */   {
/*     */     public RollingRandomAccessFileManager createManager(String paramString, RollingRandomAccessFileManager.FactoryData paramFactoryData)
/*     */     {
/* 139 */       File localFile1 = new File(paramString);
/* 140 */       File localFile2 = localFile1.getParentFile();
/* 141 */       if ((null != localFile2) && (!localFile2.exists())) {
/* 142 */         localFile2.mkdirs();
/*     */       }
/*     */       
/* 145 */       if (!paramFactoryData.append) {
/* 146 */         localFile1.delete();
/*     */       }
/* 148 */       long l1 = paramFactoryData.append ? localFile1.length() : 0L;
/* 149 */       long l2 = localFile1.exists() ? localFile1.lastModified() : System.currentTimeMillis();
/*     */       
/* 151 */       RandomAccessFile localRandomAccessFile = null;
/*     */       try {
/* 153 */         localRandomAccessFile = new RandomAccessFile(paramString, "rw");
/* 154 */         if (paramFactoryData.append) {
/* 155 */           long l3 = localRandomAccessFile.length();
/* 156 */           RollingRandomAccessFileManager.LOGGER.trace("RandomAccessFile {} seek to {}", new Object[] { paramString, Long.valueOf(l3) });
/* 157 */           localRandomAccessFile.seek(l3);
/*     */         } else {
/* 159 */           RollingRandomAccessFileManager.LOGGER.trace("RandomAccessFile {} set length to 0", new Object[] { paramString });
/* 160 */           localRandomAccessFile.setLength(0L);
/*     */         }
/* 162 */         return new RollingRandomAccessFileManager(localRandomAccessFile, paramString, paramFactoryData.pattern, new RollingRandomAccessFileManager.DummyOutputStream(), paramFactoryData.append, paramFactoryData.immediateFlush, l1, l2, paramFactoryData.policy, paramFactoryData.strategy, paramFactoryData.advertiseURI, paramFactoryData.layout);
/*     */       }
/*     */       catch (IOException localIOException1) {
/* 165 */         RollingRandomAccessFileManager.LOGGER.error("Cannot access RandomAccessFile {}) " + localIOException1);
/* 166 */         if (localRandomAccessFile != null) {
/*     */           try {
/* 168 */             localRandomAccessFile.close();
/*     */           } catch (IOException localIOException2) {
/* 170 */             RollingRandomAccessFileManager.LOGGER.error("Cannot close RandomAccessFile {}", new Object[] { paramString, localIOException2 });
/*     */           }
/*     */         }
/*     */       }
/* 174 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class DummyOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     public void write(int paramInt)
/*     */       throws IOException
/*     */     {}
/*     */     
/*     */ 
/*     */     public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */       throws IOException
/*     */     {}
/*     */   }
/*     */   
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String pattern;
/*     */     
/*     */     private final boolean append;
/*     */     
/*     */     private final boolean immediateFlush;
/*     */     
/*     */     private final TriggeringPolicy policy;
/*     */     
/*     */     private final RolloverStrategy strategy;
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(String paramString1, boolean paramBoolean1, boolean paramBoolean2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */     {
/* 211 */       this.pattern = paramString1;
/* 212 */       this.append = paramBoolean1;
/* 213 */       this.immediateFlush = paramBoolean2;
/* 214 */       this.policy = paramTriggeringPolicy;
/* 215 */       this.strategy = paramRolloverStrategy;
/* 216 */       this.advertiseURI = paramString2;
/* 217 */       this.layout = paramLayout;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RollingRandomAccessFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */