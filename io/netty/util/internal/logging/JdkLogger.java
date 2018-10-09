/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JdkLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = -1767272577989225979L;
/*     */   final transient Logger logger;
/*     */   
/*     */   JdkLogger(Logger paramLogger)
/*     */   {
/*  57 */     super(paramLogger.getName());
/*  58 */     this.logger = paramLogger;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTraceEnabled()
/*     */   {
/*  68 */     return this.logger.isLoggable(Level.FINEST);
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
/*  79 */     if (this.logger.isLoggable(Level.FINEST)) {
/*  80 */       log(SELF, Level.FINEST, paramString, null);
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
/*     */   public void trace(String paramString, Object paramObject)
/*     */   {
/* 100 */     if (this.logger.isLoggable(Level.FINEST)) {
/* 101 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 102 */       log(SELF, Level.FINEST, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 124 */     if (this.logger.isLoggable(Level.FINEST)) {
/* 125 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 126 */       log(SELF, Level.FINEST, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void trace(String paramString, Object... paramVarArgs)
/*     */   {
/* 146 */     if (this.logger.isLoggable(Level.FINEST)) {
/* 147 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 148 */       log(SELF, Level.FINEST, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void trace(String paramString, Throwable paramThrowable)
/*     */   {
/* 162 */     if (this.logger.isLoggable(Level.FINEST)) {
/* 163 */       log(SELF, Level.FINEST, paramString, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 174 */     return this.logger.isLoggable(Level.FINE);
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
/* 185 */     if (this.logger.isLoggable(Level.FINE)) {
/* 186 */       log(SELF, Level.FINE, paramString, null);
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
/*     */   public void debug(String paramString, Object paramObject)
/*     */   {
/* 205 */     if (this.logger.isLoggable(Level.FINE)) {
/* 206 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 207 */       log(SELF, Level.FINE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 229 */     if (this.logger.isLoggable(Level.FINE)) {
/* 230 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 231 */       log(SELF, Level.FINE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/*     */   public void debug(String paramString, Object... paramVarArgs)
/*     */   {
/* 251 */     if (this.logger.isLoggable(Level.FINE)) {
/* 252 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 253 */       log(SELF, Level.FINE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 267 */     if (this.logger.isLoggable(Level.FINE)) {
/* 268 */       log(SELF, Level.FINE, paramString, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 279 */     return this.logger.isLoggable(Level.INFO);
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
/* 290 */     if (this.logger.isLoggable(Level.INFO)) {
/* 291 */       log(SELF, Level.INFO, paramString, null);
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
/*     */   public void info(String paramString, Object paramObject)
/*     */   {
/* 310 */     if (this.logger.isLoggable(Level.INFO)) {
/* 311 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 312 */       log(SELF, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 334 */     if (this.logger.isLoggable(Level.INFO)) {
/* 335 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 336 */       log(SELF, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 356 */     if (this.logger.isLoggable(Level.INFO)) {
/* 357 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 358 */       log(SELF, Level.INFO, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 373 */     if (this.logger.isLoggable(Level.INFO)) {
/* 374 */       log(SELF, Level.INFO, paramString, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 386 */     return this.logger.isLoggable(Level.WARNING);
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
/* 397 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 398 */       log(SELF, Level.WARNING, paramString, null);
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
/*     */   public void warn(String paramString, Object paramObject)
/*     */   {
/* 418 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 419 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 420 */       log(SELF, Level.WARNING, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 442 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 443 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 444 */       log(SELF, Level.WARNING, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 464 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 465 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 466 */       log(SELF, Level.WARNING, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 481 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 482 */       log(SELF, Level.WARNING, paramString, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 493 */     return this.logger.isLoggable(Level.SEVERE);
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
/* 504 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 505 */       log(SELF, Level.SEVERE, paramString, null);
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
/*     */   public void error(String paramString, Object paramObject)
/*     */   {
/* 525 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 526 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject);
/* 527 */       log(SELF, Level.SEVERE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 549 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 550 */       FormattingTuple localFormattingTuple = MessageFormatter.format(paramString, paramObject1, paramObject2);
/* 551 */       log(SELF, Level.SEVERE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 571 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 572 */       FormattingTuple localFormattingTuple = MessageFormatter.arrayFormat(paramString, paramVarArgs);
/* 573 */       log(SELF, Level.SEVERE, localFormattingTuple.getMessage(), localFormattingTuple.getThrowable());
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
/* 588 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 589 */       log(SELF, Level.SEVERE, paramString, paramThrowable);
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
/*     */   private void log(String paramString1, Level paramLevel, String paramString2, Throwable paramThrowable)
/*     */   {
/* 602 */     LogRecord localLogRecord = new LogRecord(paramLevel, paramString2);
/* 603 */     localLogRecord.setLoggerName(name());
/* 604 */     localLogRecord.setThrown(paramThrowable);
/* 605 */     fillCallerData(paramString1, localLogRecord);
/* 606 */     this.logger.log(localLogRecord);
/*     */   }
/*     */   
/* 609 */   static final String SELF = JdkLogger.class.getName();
/* 610 */   static final String SUPER = AbstractInternalLogger.class.getName();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void fillCallerData(String paramString, LogRecord paramLogRecord)
/*     */   {
/* 619 */     StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
/*     */     
/* 621 */     int i = -1;
/* 622 */     for (int j = 0; j < arrayOfStackTraceElement.length; j++) {
/* 623 */       String str1 = arrayOfStackTraceElement[j].getClassName();
/* 624 */       if ((str1.equals(paramString)) || (str1.equals(SUPER))) {
/* 625 */         i = j;
/* 626 */         break;
/*     */       }
/*     */     }
/*     */     
/* 630 */     j = -1;
/* 631 */     for (int k = i + 1; k < arrayOfStackTraceElement.length; k++) {
/* 632 */       String str2 = arrayOfStackTraceElement[k].getClassName();
/* 633 */       if ((!str2.equals(paramString)) && (!str2.equals(SUPER))) {
/* 634 */         j = k;
/* 635 */         break;
/*     */       }
/*     */     }
/*     */     
/* 639 */     if (j != -1) {
/* 640 */       StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
/*     */       
/*     */ 
/* 643 */       paramLogRecord.setSourceClassName(localStackTraceElement.getClassName());
/* 644 */       paramLogRecord.setSourceMethodName(localStackTraceElement.getMethodName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\JdkLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */