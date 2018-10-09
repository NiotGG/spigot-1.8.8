/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public abstract class AbstractFilter
/*     */   implements Filter, LifeCycle
/*     */ {
/*  39 */   protected static final org.apache.logging.log4j.Logger LOGGER = ;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Filter.Result onMatch;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Filter.Result onMismatch;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean started;
/*     */   
/*     */ 
/*     */ 
/*     */   protected AbstractFilter()
/*     */   {
/*  57 */     this(null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractFilter(Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  66 */     this.onMatch = (paramResult1 == null ? Filter.Result.NEUTRAL : paramResult1);
/*  67 */     this.onMismatch = (paramResult2 == null ? Filter.Result.DENY : paramResult2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/*  75 */     this.started = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStarted()
/*     */   {
/*  84 */     return this.started;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/*  92 */     this.started = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Filter.Result getOnMismatch()
/*     */   {
/* 101 */     return this.onMismatch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Filter.Result getOnMatch()
/*     */   {
/* 110 */     return this.onMatch;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 115 */     return getClass().getSimpleName();
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
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/* 130 */     return Filter.Result.NEUTRAL;
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
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 145 */     return Filter.Result.NEUTRAL;
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
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 160 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/* 170 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\AbstractFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */