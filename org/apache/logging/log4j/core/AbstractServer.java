/*    */ package org.apache.logging.log4j.core;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
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
/*    */ public class AbstractServer
/*    */ {
/*    */   private final LoggerContext context;
/*    */   
/*    */   protected AbstractServer()
/*    */   {
/* 29 */     this.context = ((LoggerContext)LogManager.getContext(false));
/*    */   }
/*    */   
/*    */   protected void log(LogEvent paramLogEvent) {
/* 33 */     Logger localLogger = this.context.getLogger(paramLogEvent.getLoggerName());
/* 34 */     if (localLogger.config.filter(paramLogEvent.getLevel(), paramLogEvent.getMarker(), paramLogEvent.getMessage(), paramLogEvent.getThrown())) {
/* 35 */       localLogger.config.logEvent(paramLogEvent);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\AbstractServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */