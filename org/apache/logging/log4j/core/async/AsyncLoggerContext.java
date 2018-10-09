/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.apache.logging.log4j.core.Logger;
/*    */ import org.apache.logging.log4j.core.LoggerContext;
/*    */ import org.apache.logging.log4j.message.MessageFactory;
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
/*    */ public class AsyncLoggerContext
/*    */   extends LoggerContext
/*    */ {
/*    */   public AsyncLoggerContext(String paramString)
/*    */   {
/* 31 */     super(paramString);
/*    */   }
/*    */   
/*    */   public AsyncLoggerContext(String paramString, Object paramObject) {
/* 35 */     super(paramString, paramObject);
/*    */   }
/*    */   
/*    */   public AsyncLoggerContext(String paramString, Object paramObject, URI paramURI)
/*    */   {
/* 40 */     super(paramString, paramObject, paramURI);
/*    */   }
/*    */   
/*    */   public AsyncLoggerContext(String paramString1, Object paramObject, String paramString2)
/*    */   {
/* 45 */     super(paramString1, paramObject, paramString2);
/*    */   }
/*    */   
/*    */ 
/*    */   protected Logger newInstance(LoggerContext paramLoggerContext, String paramString, MessageFactory paramMessageFactory)
/*    */   {
/* 51 */     return new AsyncLogger(paramLoggerContext, paramString, paramMessageFactory);
/*    */   }
/*    */   
/*    */   public void stop()
/*    */   {
/* 56 */     AsyncLogger.stop();
/* 57 */     super.stop();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */