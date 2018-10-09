/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import com.lmax.disruptor.EventTranslator;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*    */ import org.apache.logging.log4j.message.Message;
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
/*    */ 
/*    */ 
/*    */ public class RingBufferLogEventTranslator
/*    */   implements EventTranslator<RingBufferLogEvent>
/*    */ {
/*    */   private AsyncLogger asyncLogger;
/*    */   private String loggerName;
/*    */   private Marker marker;
/*    */   private String fqcn;
/*    */   private Level level;
/*    */   private Message message;
/*    */   private Throwable thrown;
/*    */   private Map<String, String> contextMap;
/*    */   private ThreadContext.ContextStack contextStack;
/*    */   private String threadName;
/*    */   private StackTraceElement location;
/*    */   private long currentTimeMillis;
/*    */   
/*    */   public void translateTo(RingBufferLogEvent paramRingBufferLogEvent, long paramLong)
/*    */   {
/* 53 */     paramRingBufferLogEvent.setValues(this.asyncLogger, this.loggerName, this.marker, this.fqcn, this.level, this.message, this.thrown, this.contextMap, this.contextStack, this.threadName, this.location, this.currentTimeMillis);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setValues(AsyncLogger paramAsyncLogger, String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, Throwable paramThrowable, Map<String, String> paramMap, ThreadContext.ContextStack paramContextStack, String paramString3, StackTraceElement paramStackTraceElement, long paramLong)
/*    */   {
/* 63 */     this.asyncLogger = paramAsyncLogger;
/* 64 */     this.loggerName = paramString1;
/* 65 */     this.marker = paramMarker;
/* 66 */     this.fqcn = paramString2;
/* 67 */     this.level = paramLevel;
/* 68 */     this.message = paramMessage;
/* 69 */     this.thrown = paramThrowable;
/* 70 */     this.contextMap = paramMap;
/* 71 */     this.contextStack = paramContextStack;
/* 72 */     this.threadName = paramString3;
/* 73 */     this.location = paramStackTraceElement;
/* 74 */     this.currentTimeMillis = paramLong;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\RingBufferLogEventTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */