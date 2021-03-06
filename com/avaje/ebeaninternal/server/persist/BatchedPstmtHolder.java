/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class BatchedPstmtHolder
/*     */ {
/*  41 */   private static final Logger logger = Logger.getLogger(BatchedPstmtHolder.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  47 */   private LinkedHashMap<String, BatchedPstmt> stmtMap = new LinkedHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int maxSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PreparedStatement getStmt(String stmtKey, BatchPostExecute postExecute)
/*     */   {
/*  63 */     BatchedPstmt bs = (BatchedPstmt)this.stmtMap.get(stmtKey);
/*  64 */     if (bs == null)
/*     */     {
/*  66 */       return null;
/*     */     }
/*     */     
/*  69 */     bs.add(postExecute);
/*     */     
/*     */ 
/*     */ 
/*  73 */     int bsSize = bs.size();
/*  74 */     if (bsSize > this.maxSize) {
/*  75 */       this.maxSize = bsSize;
/*     */     }
/*  77 */     return bs.getStatement();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addStmt(BatchedPstmt bs, BatchPostExecute postExecute)
/*     */   {
/*  85 */     bs.add(postExecute);
/*     */     
/*     */ 
/*  88 */     this.stmtMap.put(bs.getSql(), bs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  95 */     return this.stmtMap.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush(boolean getGeneratedKeys)
/*     */     throws PersistenceException
/*     */   {
/* 106 */     SQLException firstError = null;
/* 107 */     String errorSql = null;
/*     */     
/*     */ 
/*     */ 
/* 111 */     boolean isError = false;
/*     */     
/* 113 */     Iterator<BatchedPstmt> it = this.stmtMap.values().iterator();
/* 114 */     for (;;) { if (it.hasNext()) {
/* 115 */         BatchedPstmt bs = (BatchedPstmt)it.next();
/*     */         try {
/* 117 */           if (!isError) {
/* 118 */             bs.executeBatch(getGeneratedKeys);
/*     */           }
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
/*     */           try
/*     */           {
/* 137 */             bs.close();
/*     */           }
/*     */           catch (SQLException ex) {
/* 140 */             logger.log(Level.SEVERE, null, ex);
/*     */           }
/*     */           SQLException next;
/*     */         }
/*     */         catch (SQLException ex)
/*     */         {
/* 121 */           next = ex.getNextException();
/* 122 */           while (next != null) {
/* 123 */             logger.log(Level.SEVERE, "Next Exception during batch execution", next);
/* 124 */             next = next.getNextException();
/*     */           }
/*     */           
/* 127 */           if (firstError == null) {
/* 128 */             firstError = ex;
/* 129 */             errorSql = bs.getSql();
/*     */           } else {
/* 131 */             logger.log(Level.SEVERE, null, ex);
/*     */           }
/* 133 */           isError = true;
/*     */         }
/*     */         finally {
/*     */           try {
/* 137 */             bs.close();
/*     */           }
/*     */           catch (SQLException ex) {
/* 140 */             logger.log(Level.SEVERE, null, ex);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 146 */     this.stmtMap.clear();
/* 147 */     this.maxSize = 0;
/*     */     
/* 149 */     if (firstError != null) {
/* 150 */       String msg = "Error when batch flush on sql: " + errorSql;
/* 151 */       throw new PersistenceException(msg, firstError);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMaxSize()
/*     */   {
/* 162 */     return this.maxSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchedPstmtHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */