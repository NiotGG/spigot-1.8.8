/*    */ package com.avaje.ebeaninternal.server.transaction.log;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer.LogEntry;
/*    */ import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ public class JuliTransactionLogger
/*    */   implements TransactionLogWriter
/*    */ {
/* 40 */   private static Logger logger = Logger.getLogger(JuliTransactionLogger.class.getName());
/*    */   
/*    */   public void log(TransactionLogBuffer logBuffer)
/*    */   {
/* 44 */     String txnId = logBuffer.getTransactionId();
/*    */     
/* 46 */     List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
/* 47 */     for (int i = 0; i < messages.size(); i++) {
/* 48 */       TransactionLogBuffer.LogEntry logEntry = (TransactionLogBuffer.LogEntry)messages.get(i);
/* 49 */       log(txnId, logEntry);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void shutdown() {}
/*    */   
/*    */   private void log(String txnId, TransactionLogBuffer.LogEntry entry)
/*    */   {
/* 58 */     String message = entry.getMsg();
/* 59 */     if ((txnId != null) && (message != null) && (!message.startsWith("Trans["))) {
/* 60 */       message = "Trans[" + txnId + "] " + message;
/*    */     }
/*    */     
/* 63 */     logger.log(Level.INFO, message);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\log\JuliTransactionLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */