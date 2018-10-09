/*     */ package org.apache.logging.log4j.core.appender.db;
/*     */ 
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
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
/*     */ public abstract class AbstractDatabaseAppender<T extends AbstractDatabaseManager>
/*     */   extends AbstractAppender
/*     */ {
/*  39 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*  40 */   private final Lock readLock = this.lock.readLock();
/*  41 */   private final Lock writeLock = this.lock.writeLock();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private T manager;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractDatabaseAppender(String paramString, Filter paramFilter, boolean paramBoolean, T paramT)
/*     */   {
/*  56 */     super(paramString, paramFilter, null, paramBoolean);
/*  57 */     this.manager = paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Layout<LogEvent> getLayout()
/*     */   {
/*  68 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final T getManager()
/*     */   {
/*  77 */     return this.manager;
/*     */   }
/*     */   
/*     */   public final void start()
/*     */   {
/*  82 */     if (getManager() == null) {
/*  83 */       LOGGER.error("No AbstractDatabaseManager set for the appender named [{}].", new Object[] { getName() });
/*     */     }
/*  85 */     super.start();
/*  86 */     if (getManager() != null) {
/*  87 */       getManager().connect();
/*     */     }
/*     */   }
/*     */   
/*     */   public final void stop()
/*     */   {
/*  93 */     super.stop();
/*  94 */     if (getManager() != null) {
/*  95 */       getManager().release();
/*     */     }
/*     */   }
/*     */   
/*     */   public final void append(LogEvent paramLogEvent)
/*     */   {
/* 101 */     this.readLock.lock();
/*     */     try {
/* 103 */       getManager().write(paramLogEvent);
/*     */     } catch (LoggingException localLoggingException) {
/* 105 */       LOGGER.error("Unable to write to database [{}] for appender [{}].", new Object[] { getManager().getName(), getName(), localLoggingException });
/*     */       
/* 107 */       throw localLoggingException;
/*     */     } catch (Exception localException) {
/* 109 */       LOGGER.error("Unable to write to database [{}] for appender [{}].", new Object[] { getManager().getName(), getName(), localException });
/*     */       
/* 111 */       throw new AppenderLoggingException("Unable to write to database in appender: " + localException.getMessage(), localException);
/*     */     } finally {
/* 113 */       this.readLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void replaceManager(T paramT)
/*     */   {
/* 125 */     this.writeLock.lock();
/*     */     try {
/* 127 */       AbstractDatabaseManager localAbstractDatabaseManager = getManager();
/* 128 */       if (!paramT.isConnected()) {
/* 129 */         paramT.connect();
/*     */       }
/* 131 */       this.manager = paramT;
/* 132 */       localAbstractDatabaseManager.release();
/*     */     } finally {
/* 134 */       this.writeLock.unlock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\AbstractDatabaseAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */