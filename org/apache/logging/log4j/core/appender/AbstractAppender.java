/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.ErrorHandler;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*     */ public abstract class AbstractAppender
/*     */   extends AbstractFilterable
/*     */   implements Appender
/*     */ {
/*     */   private final boolean ignoreExceptions;
/*  37 */   private ErrorHandler handler = new DefaultErrorHandler(this);
/*     */   
/*     */ 
/*     */   private final Layout<? extends Serializable> layout;
/*     */   
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*  46 */   private boolean started = false;
/*     */   
/*     */   public static int parseInt(String paramString, int paramInt) {
/*     */     try {
/*  50 */       return Integers.parseInt(paramString, paramInt);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*  52 */       LOGGER.error("Could not parse \"{}\" as an integer,  using default value {}: {}", new Object[] { paramString, Integer.valueOf(paramInt), localNumberFormatException }); }
/*  53 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  64 */     this(paramString, paramFilter, paramLayout, true);
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
/*     */   protected AbstractAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout, boolean paramBoolean)
/*     */   {
/*  77 */     super(paramFilter);
/*  78 */     this.name = paramString;
/*  79 */     this.layout = paramLayout;
/*  80 */     this.ignoreExceptions = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString)
/*     */   {
/*  88 */     this.handler.error(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString, LogEvent paramLogEvent, Throwable paramThrowable)
/*     */   {
/*  98 */     this.handler.error(paramString, paramLogEvent, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString, Throwable paramThrowable)
/*     */   {
/* 107 */     this.handler.error(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ErrorHandler getHandler()
/*     */   {
/* 116 */     return this.handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Layout<? extends Serializable> getLayout()
/*     */   {
/* 125 */     return this.layout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 134 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean ignoreExceptions()
/*     */   {
/* 145 */     return this.ignoreExceptions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStarted()
/*     */   {
/* 154 */     return this.started;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHandler(ErrorHandler paramErrorHandler)
/*     */   {
/* 163 */     if (paramErrorHandler == null) {
/* 164 */       LOGGER.error("The handler cannot be set to null");
/*     */     }
/* 166 */     if (isStarted()) {
/* 167 */       LOGGER.error("The handler cannot be changed once the appender is started");
/* 168 */       return;
/*     */     }
/* 170 */     this.handler = paramErrorHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 178 */     startFilter();
/* 179 */     this.started = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 187 */     this.started = false;
/* 188 */     stopFilter();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 193 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AbstractAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */