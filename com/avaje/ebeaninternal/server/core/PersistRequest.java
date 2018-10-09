/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchControl;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchPostExecute;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
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
/*     */ public abstract class PersistRequest
/*     */   extends BeanRequest
/*     */   implements BatchPostExecute
/*     */ {
/*     */   boolean persistCascade;
/*     */   Type type;
/*     */   final PersistExecute persistExecute;
/*     */   
/*     */   public static enum Type
/*     */   {
/*  34 */     INSERT,  UPDATE,  DELETE,  ORMUPDATE,  UPDATESQL,  CALLABLESQL;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Type() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PersistRequest(SpiEbeanServer server, SpiTransaction t, PersistExecute persistExecute)
/*     */   {
/*  50 */     super(server, t);
/*  51 */     this.persistExecute = persistExecute;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract int executeOrQueue();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract int executeNow();
/*     */   
/*     */ 
/*     */   public PstmtBatch getPstmtBatch()
/*     */   {
/*  65 */     return this.ebeanServer.getPstmtBatch();
/*     */   }
/*     */   
/*     */   public boolean isLogSql() {
/*  69 */     return this.transaction.isLogSql();
/*     */   }
/*     */   
/*     */   public boolean isLogSummary() {
/*  73 */     return this.transaction.isLogSummary();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int executeStatement()
/*     */   {
/*  81 */     boolean batch = this.transaction.isBatchThisRequest();
/*     */     
/*     */ 
/*  84 */     BatchControl control = this.transaction.getBatchControl();
/*  85 */     int rows; int rows; if (control != null) {
/*  86 */       rows = control.executeStatementOrBatch(this, batch);
/*     */     } else { int rows;
/*  88 */       if (batch)
/*     */       {
/*  90 */         control = this.persistExecute.createBatchControl(this.transaction);
/*  91 */         rows = control.executeStatementOrBatch(this, batch);
/*     */       } else {
/*  93 */         rows = executeNow();
/*     */       }
/*     */     }
/*  96 */     return rows;
/*     */   }
/*     */   
/*     */   public void initTransIfRequired() {
/* 100 */     createImplicitTransIfRequired(false);
/* 101 */     this.persistCascade = this.transaction.isPersistCascade();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getType()
/*     */   {
/* 109 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(Type type)
/*     */   {
/* 117 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isPersistCascade()
/*     */   {
/* 124 */     return this.persistCascade;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PersistRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */