/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ public abstract class AbstractOutputStreamAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   protected final boolean immediateFlush;
/*     */   private volatile OutputStreamManager manager;
/*  46 */   private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
/*  47 */   private final Lock readLock = this.rwLock.readLock();
/*  48 */   private final Lock writeLock = this.rwLock.writeLock();
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
/*     */   protected AbstractOutputStreamAppender(String paramString, Layout<? extends Serializable> paramLayout, Filter paramFilter, boolean paramBoolean1, boolean paramBoolean2, OutputStreamManager paramOutputStreamManager)
/*     */   {
/*  61 */     super(paramString, paramFilter, paramLayout, paramBoolean1);
/*  62 */     this.manager = paramOutputStreamManager;
/*  63 */     this.immediateFlush = paramBoolean2;
/*     */   }
/*     */   
/*     */   protected OutputStreamManager getManager() {
/*  67 */     return this.manager;
/*     */   }
/*     */   
/*     */   protected void replaceManager(OutputStreamManager paramOutputStreamManager)
/*     */   {
/*  72 */     this.writeLock.lock();
/*     */     try {
/*  74 */       OutputStreamManager localOutputStreamManager = this.manager;
/*  75 */       this.manager = paramOutputStreamManager;
/*  76 */       localOutputStreamManager.release();
/*     */     } finally {
/*  78 */       this.writeLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void start()
/*     */   {
/*  85 */     if (getLayout() == null) {
/*  86 */       LOGGER.error("No layout set for the appender named [" + getName() + "].");
/*     */     }
/*  88 */     if (this.manager == null) {
/*  89 */       LOGGER.error("No OutputStreamManager set for the appender named [" + getName() + "].");
/*     */     }
/*  91 */     super.start();
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  96 */     super.stop();
/*  97 */     this.manager.release();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/* 109 */     this.readLock.lock();
/*     */     try {
/* 111 */       byte[] arrayOfByte = getLayout().toByteArray(paramLogEvent);
/* 112 */       if (arrayOfByte.length > 0) {
/* 113 */         this.manager.write(arrayOfByte);
/* 114 */         if ((this.immediateFlush) || (paramLogEvent.isEndOfBatch())) {
/* 115 */           this.manager.flush();
/*     */         }
/*     */       }
/*     */     } catch (AppenderLoggingException localAppenderLoggingException) {
/* 119 */       error("Unable to write to stream " + this.manager.getName() + " for appender " + getName());
/* 120 */       throw localAppenderLoggingException;
/*     */     } finally {
/* 122 */       this.readLock.unlock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AbstractOutputStreamAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */