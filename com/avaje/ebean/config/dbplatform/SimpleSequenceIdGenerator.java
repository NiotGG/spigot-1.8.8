/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ import com.avaje.ebean.Transaction;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleSequenceIdGenerator
/*     */   implements IdGenerator
/*     */ {
/*  23 */   private static final Logger logger = Logger.getLogger(SimpleSequenceIdGenerator.class.getName());
/*     */   
/*     */ 
/*     */   private final String sql;
/*     */   
/*     */   private final DataSource dataSource;
/*     */   
/*     */   private final String seqName;
/*     */   
/*     */ 
/*     */   public SimpleSequenceIdGenerator(DataSource dataSource, String sql, String seqName)
/*     */   {
/*  35 */     this.dataSource = dataSource;
/*  36 */     this.sql = sql;
/*  37 */     this.seqName = seqName;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  41 */     return this.seqName;
/*     */   }
/*     */   
/*     */   public boolean isDbSequence() {
/*  45 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void preAllocateIds(int batchSize) {}
/*     */   
/*     */ 
/*     */   public Object nextId(Transaction t)
/*     */   {
/*  54 */     boolean useTxnConnection = t != null;
/*     */     
/*  56 */     Connection c = null;
/*  57 */     PreparedStatement pstmt = null;
/*  58 */     ResultSet rset = null;
/*     */     try {
/*  60 */       c = useTxnConnection ? t.getConnection() : this.dataSource.getConnection();
/*  61 */       pstmt = c.prepareStatement(this.sql);
/*  62 */       rset = pstmt.executeQuery();
/*  63 */       if (rset.next()) {
/*  64 */         int val = rset.getInt(1);
/*  65 */         return Integer.valueOf(val);
/*     */       }
/*  67 */       String m = "Always expecting 1 row from " + this.sql;
/*  68 */       throw new PersistenceException(m);
/*     */     }
/*     */     catch (SQLException e) {
/*  71 */       throw new PersistenceException("Error getting sequence nextval", e);
/*     */     }
/*     */     finally {
/*  74 */       if (useTxnConnection) {
/*  75 */         closeResources(rset, pstmt, null);
/*     */       } else {
/*  77 */         closeResources(rset, pstmt, c);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeResources(ResultSet rset, PreparedStatement pstmt, Connection c) {
/*     */     try {
/*  84 */       if (rset != null) {
/*  85 */         rset.close();
/*     */       }
/*     */     } catch (SQLException e) {
/*  88 */       logger.log(Level.SEVERE, "Error closing ResultSet", e);
/*     */     }
/*     */     try {
/*  91 */       if (pstmt != null) {
/*  92 */         pstmt.close();
/*     */       }
/*     */     } catch (SQLException e) {
/*  95 */       logger.log(Level.SEVERE, "Error closing PreparedStatement", e);
/*     */     }
/*     */     try {
/*  98 */       if (c != null) {
/*  99 */         c.close();
/*     */       }
/*     */     } catch (SQLException e) {
/* 102 */       logger.log(Level.SEVERE, "Error closing Connection", e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SimpleSequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */