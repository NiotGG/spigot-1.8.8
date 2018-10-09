/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.ErrorHandler;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.filter.Filterable;
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
/*     */ public class AppenderControl
/*     */   extends AbstractFilterable
/*     */ {
/*  32 */   private final ThreadLocal<AppenderControl> recursive = new ThreadLocal();
/*     */   
/*     */ 
/*     */   private final Appender appender;
/*     */   
/*     */ 
/*     */   private final Level level;
/*     */   
/*     */ 
/*     */   private final int intLevel;
/*     */   
/*     */ 
/*     */ 
/*     */   public AppenderControl(Appender paramAppender, Level paramLevel, Filter paramFilter)
/*     */   {
/*  47 */     super(paramFilter);
/*  48 */     this.appender = paramAppender;
/*  49 */     this.level = paramLevel;
/*  50 */     this.intLevel = (paramLevel == null ? Level.ALL.intLevel() : paramLevel.intLevel());
/*  51 */     startFilter();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Appender getAppender()
/*     */   {
/*  59 */     return this.appender;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void callAppender(LogEvent paramLogEvent)
/*     */   {
/*  67 */     if (getFilter() != null) {
/*  68 */       Filter.Result localResult = getFilter().filter(paramLogEvent);
/*  69 */       if (localResult == Filter.Result.DENY) {
/*  70 */         return;
/*     */       }
/*     */     }
/*  73 */     if ((this.level != null) && 
/*  74 */       (this.intLevel < paramLogEvent.getLevel().intLevel())) {
/*  75 */       return;
/*     */     }
/*     */     
/*  78 */     if (this.recursive.get() != null) {
/*  79 */       this.appender.getHandler().error("Recursive call to appender " + this.appender.getName());
/*  80 */       return;
/*     */     }
/*     */     try {
/*  83 */       this.recursive.set(this);
/*     */       
/*  85 */       if (!this.appender.isStarted()) {
/*  86 */         this.appender.getHandler().error("Attempted to append to non-started appender " + this.appender.getName());
/*     */         
/*  88 */         if (!this.appender.ignoreExceptions()) {
/*  89 */           throw new AppenderLoggingException("Attempted to append to non-started appender " + this.appender.getName());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  94 */       if (((this.appender instanceof Filterable)) && (((Filterable)this.appender).isFiltered(paramLogEvent))) {
/*     */         return;
/*     */       }
/*     */       try
/*     */       {
/*  99 */         this.appender.append(paramLogEvent);
/*     */       } catch (RuntimeException localRuntimeException) {
/* 101 */         this.appender.getHandler().error("An exception occurred processing Appender " + this.appender.getName(), localRuntimeException);
/* 102 */         if (!this.appender.ignoreExceptions()) {
/* 103 */           throw localRuntimeException;
/*     */         }
/*     */       } catch (Exception localException) {
/* 106 */         this.appender.getHandler().error("An exception occurred processing Appender " + this.appender.getName(), localException);
/* 107 */         if (!this.appender.ignoreExceptions()) {
/* 108 */           throw new AppenderLoggingException(localException);
/*     */         }
/*     */       }
/*     */     } finally {
/* 112 */       this.recursive.set(null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\AppenderControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */