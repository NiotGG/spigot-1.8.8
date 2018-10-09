/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebeaninternal.server.transaction.log.FileTransactionLoggerWrapper;
/*    */ import com.avaje.ebeaninternal.server.transaction.log.JuliTransactionLogger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionLogManager
/*    */ {
/*    */   private final TransactionLogWriter logWriter;
/*    */   
/*    */   public TransactionLogManager(ServerConfig serverConfig)
/*    */   {
/* 43 */     if (serverConfig.isLoggingToJavaLogger()) {
/* 44 */       this.logWriter = new JuliTransactionLogger();
/*    */     } else {
/* 46 */       this.logWriter = new FileTransactionLoggerWrapper(serverConfig);
/*    */     }
/*    */   }
/*    */   
/*    */   public void shutdown() {
/* 51 */     this.logWriter.shutdown();
/*    */   }
/*    */   
/*    */   public void log(TransactionLogBuffer logBuffer) {
/* 55 */     this.logWriter.log(logBuffer);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */