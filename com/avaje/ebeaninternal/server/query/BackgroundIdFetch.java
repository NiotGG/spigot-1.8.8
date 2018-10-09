/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BackgroundIdFetch
/*     */   implements Callable<Integer>
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(BackgroundIdFetch.class.getName());
/*     */   
/*     */ 
/*     */   private final ResultSet rset;
/*     */   
/*     */ 
/*     */   private final PreparedStatement pstmt;
/*     */   
/*     */ 
/*     */   private final SpiTransaction transaction;
/*     */   
/*     */ 
/*     */   private final DbReadContext ctx;
/*     */   
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   
/*     */   private final BeanIdList idList;
/*     */   
/*     */ 
/*     */   public BackgroundIdFetch(SpiTransaction transaction, ResultSet rset, PreparedStatement pstmt, DbReadContext ctx, BeanDescriptor<?> beanDescriptor, BeanIdList idList)
/*     */   {
/*  61 */     this.ctx = ctx;
/*  62 */     this.transaction = transaction;
/*  63 */     this.rset = rset;
/*  64 */     this.pstmt = pstmt;
/*  65 */     this.beanDescriptor = beanDescriptor;
/*  66 */     this.idList = idList;
/*     */   }
/*     */   
/*     */ 
/*     */   public Integer call()
/*     */   {
/*     */     try
/*     */     {
/*  74 */       int startSize = this.idList.getIdList().size();
/*  75 */       rowsRead = 0;
/*  76 */       Object idValue; while (this.rset.next()) {
/*  77 */         idValue = this.beanDescriptor.getIdBinder().read(this.ctx);
/*  78 */         this.idList.add(idValue);
/*  79 */         this.ctx.getDataReader().resetColumnPosition();
/*  80 */         rowsRead++;
/*     */       }
/*     */       
/*  83 */       if (logger.isLoggable(Level.INFO)) {
/*  84 */         logger.info("BG FetchIds read:" + rowsRead + " total:" + (startSize + rowsRead));
/*     */       }
/*     */       
/*  87 */       return Integer.valueOf(rowsRead);
/*     */     } catch (Exception e) {
/*     */       int rowsRead;
/*  90 */       logger.log(Level.SEVERE, null, e);
/*  91 */       return Integer.valueOf(0);
/*     */     }
/*     */     finally {
/*     */       try {
/*  95 */         close();
/*     */       } catch (Exception e) {
/*  97 */         logger.log(Level.SEVERE, null, e);
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 103 */         this.transaction.rollback();
/*     */       } catch (Exception e) {
/* 105 */         logger.log(Level.SEVERE, null, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void close()
/*     */   {
/*     */     try {
/* 113 */       if (this.rset != null) {
/* 114 */         this.rset.close();
/*     */       }
/*     */     } catch (SQLException e) {
/* 117 */       logger.log(Level.SEVERE, null, e);
/*     */     }
/*     */     try {
/* 120 */       if (this.pstmt != null) {
/* 121 */         this.pstmt.close();
/*     */       }
/*     */     } catch (SQLException e) {
/* 124 */       logger.log(Level.SEVERE, null, e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\BackgroundIdFetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */