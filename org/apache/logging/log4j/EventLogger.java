/*    */ package org.apache.logging.log4j;
/*    */ 
/*    */ import org.apache.logging.log4j.message.StructuredDataMessage;
/*    */ import org.apache.logging.log4j.spi.AbstractLogger;
/*    */ import org.apache.logging.log4j.spi.AbstractLoggerWrapper;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EventLogger
/*    */ {
/*    */   private static final String NAME = "EventLogger";
/* 33 */   public static final Marker EVENT_MARKER = MarkerManager.getMarker("EVENT");
/*    */   
/* 35 */   private static final String FQCN = EventLogger.class.getName();
/*    */   private static AbstractLoggerWrapper loggerWrapper;
/*    */   
/*    */   static
/*    */   {
/* 40 */     Logger localLogger = LogManager.getLogger("EventLogger");
/* 41 */     if (!(localLogger instanceof AbstractLogger)) {
/* 42 */       throw new LoggingException("Logger returned must be based on AbstractLogger");
/*    */     }
/* 44 */     loggerWrapper = new AbstractLoggerWrapper((AbstractLogger)localLogger, "EventLogger", null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void logEvent(StructuredDataMessage paramStructuredDataMessage)
/*    */   {
/* 56 */     loggerWrapper.log(EVENT_MARKER, FQCN, Level.OFF, paramStructuredDataMessage, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void logEvent(StructuredDataMessage paramStructuredDataMessage, Level paramLevel)
/*    */   {
/* 65 */     loggerWrapper.log(EVENT_MARKER, FQCN, paramLevel, paramStructuredDataMessage, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\EventLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */