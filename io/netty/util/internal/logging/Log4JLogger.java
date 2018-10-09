/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
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
/*     */ class Log4JLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = 2851357342488183058L;
/*     */   final transient Logger logger;
/*  59 */   static final String FQCN = Log4JLogger.class.getName();
/*     */   
/*     */   final boolean traceCapable;
/*     */   
/*     */ 
/*     */   Log4JLogger(Logger paramLogger)
/*     */   {
/*  66 */     super(paramLogger.getName());
/*  67 */     this.logger = paramLogger;
/*  68 */     this.traceCapable = isTraceCapable();
/*     */   }
/*     */   
/*     */   private boolean isTraceCapable() {
/*     */     try {
/*  73 */       this.logger.isTraceEnabled();
/*  74 */       return true;
/*     */     } catch (NoSuchMethodError localNoSuchMethodError) {}
/*  76 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTraceEnabled()
/*     */   {
/*  87 */     if (this.traceCapable) {
/*  88 */       return this.logger.isTraceEnabled();
/*     */     }
/*  90 */     return this.logger.isDebugEnabled();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(String paramString)
/*     */   {
/* 102 */     this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, paramString, null);
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
/*     */   public void trace(String paramString, Object paramObject)
/*     */   {
/* 121 */     if (isTraceEnabled()) {
/* 122 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 123 */       this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 146 */     if (isTraceEnabled()) {
/* 147 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 148 */       this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(String paramString, Object... paramVarArgs)
/*     */   {
/* 169 */     if (isTraceEnabled()) {
/* 170 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 171 */       this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void trace(String paramString, Throwable paramThrowable)
/*     */   {
/* 186 */     this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 196 */     return this.logger.isDebugEnabled();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void debug(String paramString)
/*     */   {
/* 207 */     this.logger.log(FQCN, Level.DEBUG, paramString, null);
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
/*     */   public void debug(String paramString, Object paramObject)
/*     */   {
/* 226 */     if (this.logger.isDebugEnabled()) {
/* 227 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 228 */       this.logger.log(FQCN, Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void debug(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 250 */     if (this.logger.isDebugEnabled()) {
/* 251 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 252 */       this.logger.log(FQCN, Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void debug(String paramString, Object... paramVarArgs)
/*     */   {
/* 271 */     if (this.logger.isDebugEnabled()) {
/* 272 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 273 */       this.logger.log(FQCN, Level.DEBUG, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void debug(String paramString, Throwable paramThrowable)
/*     */   {
/* 287 */     this.logger.log(FQCN, Level.DEBUG, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 297 */     return this.logger.isInfoEnabled();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void info(String paramString)
/*     */   {
/* 308 */     this.logger.log(FQCN, Level.INFO, paramString, null);
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
/*     */   public void info(String paramString, Object paramObject)
/*     */   {
/* 326 */     if (this.logger.isInfoEnabled()) {
/* 327 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 328 */       this.logger.log(FQCN, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void info(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 350 */     if (this.logger.isInfoEnabled()) {
/* 351 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 352 */       this.logger.log(FQCN, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void info(String paramString, Object... paramVarArgs)
/*     */   {
/* 372 */     if (this.logger.isInfoEnabled()) {
/* 373 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 374 */       this.logger.log(FQCN, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void info(String paramString, Throwable paramThrowable)
/*     */   {
/* 389 */     this.logger.log(FQCN, Level.INFO, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 399 */     return this.logger.isEnabledFor(Level.WARN);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void warn(String paramString)
/*     */   {
/* 410 */     this.logger.log(FQCN, Level.WARN, paramString, null);
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
/*     */   public void warn(String paramString, Object paramObject)
/*     */   {
/* 429 */     if (this.logger.isEnabledFor(Level.WARN)) {
/* 430 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 431 */       this.logger.log(FQCN, Level.WARN, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void warn(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 453 */     if (this.logger.isEnabledFor(Level.WARN)) {
/* 454 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 455 */       this.logger.log(FQCN, Level.WARN, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void warn(String paramString, Object... paramVarArgs)
/*     */   {
/* 475 */     if (this.logger.isEnabledFor(Level.WARN)) {
/* 476 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 477 */       this.logger.log(FQCN, Level.WARN, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void warn(String paramString, Throwable paramThrowable)
/*     */   {
/* 492 */     this.logger.log(FQCN, Level.WARN, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 502 */     return this.logger.isEnabledFor(Level.ERROR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString)
/*     */   {
/* 513 */     this.logger.log(FQCN, Level.ERROR, paramString, null);
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
/*     */   public void error(String paramString, Object paramObject)
/*     */   {
/* 532 */     if (this.logger.isEnabledFor(Level.ERROR)) {
/* 533 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 534 */       this.logger.log(FQCN, Level.ERROR, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 556 */     if (this.logger.isEnabledFor(Level.ERROR)) {
/* 557 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 558 */       this.logger.log(FQCN, Level.ERROR, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(String paramString, Object... paramVarArgs)
/*     */   {
/* 578 */     if (this.logger.isEnabledFor(Level.ERROR)) {
/* 579 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 580 */       this.logger.log(FQCN, Level.ERROR, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void error(String paramString, Throwable paramThrowable)
/*     */   {
/* 595 */     this.logger.log(FQCN, Level.ERROR, paramString, paramThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\Log4JLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */