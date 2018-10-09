/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CommonsLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = 8647838678388394885L;
/*     */   private final transient Log logger;
/*     */   
/*     */   CommonsLogger(Log paramLog, String paramString)
/*     */   {
/*  55 */     super(paramString);
/*  56 */     if (paramLog == null) {
/*  57 */       throw new NullPointerException("logger");
/*     */     }
/*  59 */     this.logger = paramLog;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTraceEnabled()
/*     */   {
/*  68 */     return this.logger.isTraceEnabled();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(String paramString)
/*     */   {
/*  79 */     this.logger.trace(paramString);
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
/*  98 */     if (this.logger.isTraceEnabled()) {
/*  99 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 100 */       this.logger.trace(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void trace(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 122 */     if (this.logger.isTraceEnabled()) {
/* 123 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 124 */       this.logger.trace(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void trace(String paramString, Object... paramVarArgs)
/*     */   {
/* 142 */     if (this.logger.isTraceEnabled()) {
/* 143 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 144 */       this.logger.trace(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 159 */     this.logger.trace(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 168 */     return this.logger.isDebugEnabled();
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
/*     */   public void debug(String paramString)
/*     */   {
/* 181 */     this.logger.debug(paramString);
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
/* 200 */     if (this.logger.isDebugEnabled()) {
/* 201 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 202 */       this.logger.debug(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 224 */     if (this.logger.isDebugEnabled()) {
/* 225 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 226 */       this.logger.debug(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void debug(String paramString, Object... paramVarArgs)
/*     */   {
/* 244 */     if (this.logger.isDebugEnabled()) {
/* 245 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 246 */       this.logger.debug(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void debug(String paramString, Throwable paramThrowable)
/*     */   {
/* 261 */     this.logger.debug(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 270 */     return this.logger.isInfoEnabled();
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
/* 281 */     this.logger.info(paramString);
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
/*     */   public void info(String paramString, Object paramObject)
/*     */   {
/* 301 */     if (this.logger.isInfoEnabled()) {
/* 302 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 303 */       this.logger.info(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void info(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 324 */     if (this.logger.isInfoEnabled()) {
/* 325 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 326 */       this.logger.info(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void info(String paramString, Object... paramVarArgs)
/*     */   {
/* 344 */     if (this.logger.isInfoEnabled()) {
/* 345 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 346 */       this.logger.info(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 361 */     this.logger.info(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 370 */     return this.logger.isWarnEnabled();
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
/* 381 */     this.logger.warn(paramString);
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
/* 400 */     if (this.logger.isWarnEnabled()) {
/* 401 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 402 */       this.logger.warn(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 424 */     if (this.logger.isWarnEnabled()) {
/* 425 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 426 */       this.logger.warn(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void warn(String paramString, Object... paramVarArgs)
/*     */   {
/* 444 */     if (this.logger.isWarnEnabled()) {
/* 445 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 446 */       this.logger.warn(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void warn(String paramString, Throwable paramThrowable)
/*     */   {
/* 462 */     this.logger.warn(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 471 */     return this.logger.isErrorEnabled();
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
/* 482 */     this.logger.error(paramString);
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
/* 501 */     if (this.logger.isErrorEnabled()) {
/* 502 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 503 */       this.logger.error(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 525 */     if (this.logger.isErrorEnabled()) {
/* 526 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 527 */       this.logger.error(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void error(String paramString, Object... paramVarArgs)
/*     */   {
/* 545 */     if (this.logger.isErrorEnabled()) {
/* 546 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 547 */       this.logger.error(localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 562 */     this.logger.error(paramString, paramThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\CommonsLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */