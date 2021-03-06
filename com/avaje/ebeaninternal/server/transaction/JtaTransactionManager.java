/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebean.config.ExternalTransactionManager;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ import javax.transaction.HeuristicMixedException;
/*     */ import javax.transaction.HeuristicRollbackException;
/*     */ import javax.transaction.NotSupportedException;
/*     */ import javax.transaction.RollbackException;
/*     */ import javax.transaction.Synchronization;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.TransactionSynchronizationRegistry;
/*     */ import javax.transaction.UserTransaction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JtaTransactionManager
/*     */   implements ExternalTransactionManager
/*     */ {
/*  50 */   private static final Logger logger = Logger.getLogger(JtaTransactionManager.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String EBEAN_TXN_RESOURCE = "EBEAN_TXN_RESOURCE";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private DataSource dataSource;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private TransactionManager transactionManager;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String serverName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTransactionManager(Object txnMgr)
/*     */   {
/*  83 */     this.transactionManager = ((TransactionManager)txnMgr);
/*  84 */     this.dataSource = this.transactionManager.getDataSource();
/*  85 */     this.serverName = this.transactionManager.getServerName();
/*     */   }
/*     */   
/*     */   private TransactionSynchronizationRegistry getSyncRegistry() {
/*     */     try {
/*  90 */       InitialContext ctx = new InitialContext();
/*  91 */       return (TransactionSynchronizationRegistry)ctx.lookup("java:comp/TransactionSynchronizationRegistry");
/*     */     } catch (NamingException e) {
/*  93 */       throw new PersistenceException(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private UserTransaction getUserTransaction() {
/*     */     try {
/*  99 */       InitialContext ctx = new InitialContext();
/* 100 */       return (UserTransaction)ctx.lookup("java:comp/UserTransaction");
/*     */     }
/*     */     catch (NamingException e) {}
/* 103 */     return new DummyUserTransaction(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getCurrentTransaction()
/*     */   {
/* 115 */     TransactionSynchronizationRegistry syncRegistry = getSyncRegistry();
/*     */     
/* 117 */     SpiTransaction t = (SpiTransaction)syncRegistry.getResource("EBEAN_TXN_RESOURCE");
/* 118 */     if (t != null)
/*     */     {
/* 120 */       return t;
/*     */     }
/*     */     
/*     */ 
/* 124 */     SpiTransaction currentEbeanTransaction = DefaultTransactionThreadLocal.get(this.serverName);
/* 125 */     if (currentEbeanTransaction != null)
/*     */     {
/* 127 */       String msg = "JTA Transaction - no current txn BUT using current Ebean one " + currentEbeanTransaction.getId();
/* 128 */       logger.log(Level.WARNING, msg);
/* 129 */       return currentEbeanTransaction;
/*     */     }
/*     */     
/* 132 */     UserTransaction ut = getUserTransaction();
/* 133 */     if (ut == null)
/*     */     {
/* 135 */       if (logger.isLoggable(Level.FINE)) {
/* 136 */         logger.fine("JTA Transaction - no current txn");
/*     */       }
/* 138 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 144 */     String txnId = String.valueOf(System.currentTimeMillis());
/* 145 */     JtaTransaction newTrans = new JtaTransaction(txnId, true, LogLevel.NONE, ut, this.dataSource, this.transactionManager);
/*     */     
/*     */ 
/* 148 */     JtaTxnListener txnListener = createJtaTxnListener(newTrans);
/*     */     
/* 150 */     syncRegistry.putResource("EBEAN_TXN_RESOURCE", newTrans);
/* 151 */     syncRegistry.registerInterposedSynchronization(txnListener);
/*     */     
/*     */ 
/* 154 */     DefaultTransactionThreadLocal.set(this.serverName, newTrans);
/* 155 */     return newTrans;
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
/*     */   private JtaTxnListener createJtaTxnListener(SpiTransaction t)
/*     */   {
/* 168 */     return new JtaTxnListener(this.transactionManager, t, null);
/*     */   }
/*     */   
/*     */   private static class DummyUserTransaction implements UserTransaction
/*     */   {
/*     */     public void begin() throws NotSupportedException, SystemException
/*     */     {}
/*     */     
/*     */     public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException
/*     */     {}
/*     */     
/*     */     public int getStatus() throws SystemException
/*     */     {
/* 181 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */     public void rollback()
/*     */       throws IllegalStateException, SecurityException, SystemException
/*     */     {}
/*     */     
/*     */ 
/*     */     public void setRollbackOnly()
/*     */       throws IllegalStateException, SystemException
/*     */     {}
/*     */     
/*     */ 
/*     */     public void setTransactionTimeout(int seconds)
/*     */       throws SystemException
/*     */     {}
/*     */   }
/*     */   
/*     */ 
/*     */   private static class JtaTxnListener
/*     */     implements Synchronization
/*     */   {
/*     */     private final TransactionManager transactionManager;
/*     */     
/*     */     private final SpiTransaction transaction;
/*     */     private final String serverName;
/*     */     
/*     */     private JtaTxnListener(TransactionManager transactionManager, SpiTransaction t)
/*     */     {
/* 211 */       this.transactionManager = transactionManager;
/* 212 */       this.transaction = t;
/* 213 */       this.serverName = transactionManager.getServerName();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void beforeCompletion() {}
/*     */     
/*     */ 
/*     */     public void afterCompletion(int status)
/*     */     {
/* 223 */       switch (status) {
/*     */       case 3: 
/* 225 */         if (JtaTransactionManager.logger.isLoggable(Level.FINE)) {
/* 226 */           JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] committed");
/*     */         }
/* 228 */         this.transactionManager.notifyOfCommit(this.transaction);
/*     */         
/* 230 */         DefaultTransactionThreadLocal.replace(this.serverName, null);
/* 231 */         break;
/*     */       
/*     */       case 4: 
/* 234 */         if (JtaTransactionManager.logger.isLoggable(Level.FINE)) {
/* 235 */           JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] rollback");
/*     */         }
/* 237 */         this.transactionManager.notifyOfRollback(this.transaction, null);
/*     */         
/* 239 */         DefaultTransactionThreadLocal.replace(this.serverName, null);
/* 240 */         break;
/*     */       
/*     */       default: 
/* 243 */         JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] status:" + status);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\JtaTransactionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */