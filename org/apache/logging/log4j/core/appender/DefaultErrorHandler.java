/*    */ package org.apache.logging.log4j.core.appender;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Appender;
/*    */ import org.apache.logging.log4j.core.ErrorHandler;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultErrorHandler
/*    */   implements ErrorHandler
/*    */ {
/* 30 */   private static final Logger LOGGER = ;
/*    */   
/*    */   private static final int MAX_EXCEPTIONS = 3;
/*    */   
/*    */   private static final int EXCEPTION_INTERVAL = 300000;
/*    */   
/* 36 */   private int exceptionCount = 0;
/*    */   
/*    */   private long lastException;
/*    */   private final Appender appender;
/*    */   
/*    */   public DefaultErrorHandler(Appender paramAppender)
/*    */   {
/* 43 */     this.appender = paramAppender;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void error(String paramString)
/*    */   {
/* 53 */     long l = System.currentTimeMillis();
/* 54 */     if ((this.lastException + 300000L < l) || (this.exceptionCount++ < 3)) {
/* 55 */       LOGGER.error(paramString);
/*    */     }
/* 57 */     this.lastException = l;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void error(String paramString, Throwable paramThrowable)
/*    */   {
/* 67 */     long l = System.currentTimeMillis();
/* 68 */     if ((this.lastException + 300000L < l) || (this.exceptionCount++ < 3)) {
/* 69 */       LOGGER.error(paramString, paramThrowable);
/*    */     }
/* 71 */     this.lastException = l;
/* 72 */     if ((!this.appender.ignoreExceptions()) && (paramThrowable != null) && (!(paramThrowable instanceof AppenderLoggingException))) {
/* 73 */       throw new AppenderLoggingException(paramString, paramThrowable);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void error(String paramString, LogEvent paramLogEvent, Throwable paramThrowable)
/*    */   {
/* 85 */     long l = System.currentTimeMillis();
/* 86 */     if ((this.lastException + 300000L < l) || (this.exceptionCount++ < 3)) {
/* 87 */       LOGGER.error(paramString, paramThrowable);
/*    */     }
/* 89 */     this.lastException = l;
/* 90 */     if ((!this.appender.ignoreExceptions()) && (paramThrowable != null) && (!(paramThrowable instanceof AppenderLoggingException))) {
/* 91 */       throw new AppenderLoggingException(paramString, paramThrowable);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\DefaultErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */