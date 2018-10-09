/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.CallableSql;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.BindParams.Param;
/*     */ import com.avaje.ebeaninternal.api.SpiCallableSql;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import java.io.Serializable;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultCallableSql
/*     */   implements Serializable, SpiCallableSql
/*     */ {
/*     */   private static final long serialVersionUID = 8984272253185424701L;
/*     */   private final transient EbeanServer server;
/*     */   private String sql;
/*     */   private String label;
/*     */   private int timeout;
/*  56 */   private TransactionEventTable transactionEvent = new TransactionEventTable();
/*     */   
/*  58 */   private BindParams bindParameters = new BindParams();
/*     */   
/*     */ 
/*     */ 
/*     */   public DefaultCallableSql(EbeanServer server, String sql)
/*     */   {
/*  64 */     this.server = server;
/*  65 */     this.sql = sql;
/*     */   }
/*     */   
/*     */   public void execute() {
/*  69 */     this.server.execute(this, null);
/*     */   }
/*     */   
/*     */   public String getLabel() {
/*  73 */     return this.label;
/*     */   }
/*     */   
/*     */   public CallableSql setLabel(String label) {
/*  77 */     this.label = label;
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public int getTimeout() {
/*  82 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public String getSql() {
/*  86 */     return this.sql;
/*     */   }
/*     */   
/*     */   public CallableSql setTimeout(int secs) {
/*  90 */     this.timeout = secs;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql setSql(String sql) {
/*  95 */     this.sql = sql;
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql bind(int position, Object value) {
/* 100 */     this.bindParameters.setParameter(position, value);
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql setParameter(int position, Object value) {
/* 105 */     this.bindParameters.setParameter(position, value);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public CallableSql registerOut(int position, int type) {
/* 110 */     this.bindParameters.registerOut(position, type);
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public Object getObject(int position) {
/* 115 */     BindParams.Param p = this.bindParameters.getParameter(position);
/* 116 */     return p.getOutValue();
/*     */   }
/*     */   
/*     */   public boolean executeOverride(CallableStatement cstmt) throws SQLException {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public CallableSql addModification(String tableName, boolean inserts, boolean updates, boolean deletes)
/*     */   {
/* 126 */     this.transactionEvent.add(tableName, inserts, updates, deletes);
/* 127 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TransactionEventTable getTransactionEventTable()
/*     */   {
/* 136 */     return this.transactionEvent;
/*     */   }
/*     */   
/*     */   public BindParams getBindParams() {
/* 140 */     return this.bindParameters;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */