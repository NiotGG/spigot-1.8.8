/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.FileManager;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.AbstractAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
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
/*     */ public class RollingFileManager
/*     */   extends FileManager
/*     */ {
/*  40 */   private static RollingFileManagerFactory factory = new RollingFileManagerFactory(null);
/*     */   
/*     */   private long size;
/*     */   private long initialTime;
/*     */   private final PatternProcessor patternProcessor;
/*  45 */   private final Semaphore semaphore = new Semaphore(1);
/*     */   
/*     */   private final TriggeringPolicy policy;
/*     */   private final RolloverStrategy strategy;
/*     */   
/*     */   protected RollingFileManager(String paramString1, String paramString2, OutputStream paramOutputStream, boolean paramBoolean, long paramLong1, long paramLong2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString3, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  52 */     super(paramString1, paramOutputStream, paramBoolean, false, paramString3, paramLayout);
/*  53 */     this.size = paramLong1;
/*  54 */     this.initialTime = paramLong2;
/*  55 */     this.policy = paramTriggeringPolicy;
/*  56 */     this.strategy = paramRolloverStrategy;
/*  57 */     this.patternProcessor = new PatternProcessor(paramString2);
/*  58 */     paramTriggeringPolicy.initialize(this);
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
/*     */   public static RollingFileManager getFileManager(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString3, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  78 */     return (RollingFileManager)getManager(paramString1, new FactoryData(paramString2, paramBoolean1, paramBoolean2, paramTriggeringPolicy, paramRolloverStrategy, paramString3, paramLayout), factory);
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*  84 */     this.size += paramInt2;
/*  85 */     super.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getFileSize()
/*     */   {
/*  93 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getFileTime()
/*     */   {
/* 101 */     return this.initialTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void checkRollover(LogEvent paramLogEvent)
/*     */   {
/* 109 */     if ((this.policy.isTriggeringEvent(paramLogEvent)) && (rollover(this.strategy))) {
/*     */       try {
/* 111 */         this.size = 0L;
/* 112 */         this.initialTime = System.currentTimeMillis();
/* 113 */         createFileAfterRollover();
/*     */       } catch (IOException localIOException) {
/* 115 */         LOGGER.error("FileManager (" + getFileName() + ") " + localIOException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void createFileAfterRollover() throws IOException {
/* 121 */     FileOutputStream localFileOutputStream = new FileOutputStream(getFileName(), isAppend());
/* 122 */     setOutputStream(localFileOutputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PatternProcessor getPatternProcessor()
/*     */   {
/* 130 */     return this.patternProcessor;
/*     */   }
/*     */   
/*     */   private boolean rollover(RolloverStrategy paramRolloverStrategy)
/*     */   {
/*     */     try
/*     */     {
/* 137 */       this.semaphore.acquire();
/*     */     } catch (InterruptedException localInterruptedException) {
/* 139 */       LOGGER.error("Thread interrupted while attempting to check rollover", localInterruptedException);
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     boolean bool1 = false;
/* 144 */     Thread localThread = null;
/*     */     try
/*     */     {
/* 147 */       RolloverDescription localRolloverDescription = paramRolloverStrategy.rollover(this);
/*     */       boolean bool2;
/* 149 */       if (localRolloverDescription != null)
/*     */       {
/* 151 */         close();
/*     */         
/* 153 */         if (localRolloverDescription.getSynchronous() != null) {
/*     */           try
/*     */           {
/* 156 */             bool1 = localRolloverDescription.getSynchronous().execute();
/*     */           } catch (Exception localException) {
/* 158 */             LOGGER.error("Error in synchronous task", localException);
/*     */           }
/*     */         }
/*     */         
/* 162 */         if ((bool1) && (localRolloverDescription.getAsynchronous() != null)) {
/* 163 */           localThread = new Thread(new AsyncAction(localRolloverDescription.getAsynchronous(), this));
/* 164 */           localThread.start();
/*     */         }
/* 166 */         return true;
/*     */       }
/* 168 */       return false;
/*     */     } finally {
/* 170 */       if (localThread == null) {
/* 171 */         this.semaphore.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class AsyncAction
/*     */     extends AbstractAction
/*     */   {
/*     */     private final Action action;
/*     */     
/*     */ 
/*     */     private final RollingFileManager manager;
/*     */     
/*     */ 
/*     */ 
/*     */     public AsyncAction(Action paramAction, RollingFileManager paramRollingFileManager)
/*     */     {
/* 191 */       this.action = paramAction;
/* 192 */       this.manager = paramRollingFileManager;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean execute()
/*     */       throws IOException
/*     */     {
/*     */       try
/*     */       {
/* 206 */         return this.action.execute();
/*     */       } finally {
/* 208 */         this.manager.semaphore.release();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void close()
/*     */     {
/* 217 */       this.action.close();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isComplete()
/*     */     {
/* 227 */       return this.action.isComplete();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String pattern;
/*     */     
/*     */ 
/*     */     private final boolean append;
/*     */     
/*     */ 
/*     */     private final boolean bufferedIO;
/*     */     
/*     */     private final TriggeringPolicy policy;
/*     */     
/*     */     private final RolloverStrategy strategy;
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */ 
/*     */     public FactoryData(String paramString1, boolean paramBoolean1, boolean paramBoolean2, TriggeringPolicy paramTriggeringPolicy, RolloverStrategy paramRolloverStrategy, String paramString2, Layout<? extends Serializable> paramLayout)
/*     */     {
/* 254 */       this.pattern = paramString1;
/* 255 */       this.append = paramBoolean1;
/* 256 */       this.bufferedIO = paramBoolean2;
/* 257 */       this.policy = paramTriggeringPolicy;
/* 258 */       this.strategy = paramRolloverStrategy;
/* 259 */       this.advertiseURI = paramString2;
/* 260 */       this.layout = paramLayout;
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
/*     */   private static class RollingFileManagerFactory
/*     */     implements ManagerFactory<RollingFileManager, RollingFileManager.FactoryData>
/*     */   {
/*     */     public RollingFileManager createManager(String paramString, RollingFileManager.FactoryData paramFactoryData)
/*     */     {
/* 277 */       File localFile1 = new File(paramString);
/* 278 */       File localFile2 = localFile1.getParentFile();
/* 279 */       if ((null != localFile2) && (!localFile2.exists())) {
/* 280 */         localFile2.mkdirs();
/*     */       }
/*     */       try {
/* 283 */         localFile1.createNewFile();
/*     */       } catch (IOException localIOException) {
/* 285 */         RollingFileManager.LOGGER.error("Unable to create file " + paramString, localIOException);
/* 286 */         return null;
/*     */       }
/* 288 */       long l1 = RollingFileManager.FactoryData.access$300(paramFactoryData) ? localFile1.length() : 0L;
/* 289 */       long l2 = localFile1.lastModified();
/*     */       
/*     */       try
/*     */       {
/* 293 */         Object localObject = new FileOutputStream(paramString, RollingFileManager.FactoryData.access$300(paramFactoryData));
/* 294 */         if (RollingFileManager.FactoryData.access$400(paramFactoryData)) {
/* 295 */           localObject = new BufferedOutputStream((OutputStream)localObject);
/*     */         }
/* 297 */         return new RollingFileManager(paramString, RollingFileManager.FactoryData.access$500(paramFactoryData), (OutputStream)localObject, RollingFileManager.FactoryData.access$300(paramFactoryData), l1, l2, RollingFileManager.FactoryData.access$600(paramFactoryData), RollingFileManager.FactoryData.access$700(paramFactoryData), RollingFileManager.FactoryData.access$800(paramFactoryData), RollingFileManager.FactoryData.access$900(paramFactoryData));
/*     */       }
/*     */       catch (FileNotFoundException localFileNotFoundException) {
/* 300 */         RollingFileManager.LOGGER.error("FileManager (" + paramString + ") " + localFileNotFoundException);
/*     */       }
/* 302 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RollingFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */