/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractLoggerWrapper
/*     */   extends AbstractLogger
/*     */ {
/*     */   protected final AbstractLogger logger;
/*     */   
/*     */   public AbstractLoggerWrapper(AbstractLogger paramAbstractLogger, String paramString, MessageFactory paramMessageFactory)
/*     */   {
/*  41 */     super(paramString, paramMessageFactory);
/*  42 */     this.logger = paramAbstractLogger;
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
/*     */   public void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  55 */     this.logger.log(paramMarker, paramString, paramLevel, paramMessage, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString)
/*     */   {
/*  67 */     return this.logger.isEnabled(paramLevel, paramMarker, paramString);
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
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable)
/*     */   {
/*  80 */     return this.logger.isEnabled(paramLevel, paramMarker, paramString, paramThrowable);
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
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/*  93 */     return this.logger.isEnabled(paramLevel, paramMarker, paramString, paramVarArgs);
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
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 106 */     return this.logger.isEnabled(paramLevel, paramMarker, paramObject, paramThrowable);
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
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 119 */     return this.logger.isEnabled(paramLevel, paramMarker, paramMessage, paramThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\AbstractLoggerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */