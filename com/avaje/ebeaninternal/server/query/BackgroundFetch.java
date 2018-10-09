/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import java.util.concurrent.Callable;
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
/*    */ public class BackgroundFetch
/*    */   implements Callable<Integer>
/*    */ {
/* 35 */   private static final Logger logger = Logger.getLogger(BackgroundFetch.class.getName());
/*    */   
/*    */ 
/*    */   private final CQuery<?> cquery;
/*    */   
/*    */   private final SpiTransaction transaction;
/*    */   
/*    */ 
/*    */   public BackgroundFetch(CQuery<?> cquery)
/*    */   {
/* 45 */     this.cquery = cquery;
/* 46 */     this.transaction = cquery.getTransaction();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Integer call()
/*    */   {
/*    */     try
/*    */     {
/* 55 */       BeanCollection<?> bc = this.cquery.continueFetchingInBackground();
/*    */       
/* 57 */       return Integer.valueOf(bc.size());
/*    */     } catch (Exception e) {
/*    */       Integer localInteger;
/* 60 */       logger.log(Level.SEVERE, null, e);
/* 61 */       return Integer.valueOf(0);
/*    */     }
/*    */     finally {
/*    */       try {
/* 65 */         this.cquery.close();
/*    */       } catch (Exception e) {
/* 67 */         logger.log(Level.SEVERE, null, e);
/*    */       }
/*    */       
/*    */ 
/*    */       try
/*    */       {
/* 73 */         this.transaction.rollback();
/*    */       } catch (Exception e) {
/* 75 */         logger.log(Level.SEVERE, null, e);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 82 */     StringBuffer sb = new StringBuffer();
/* 83 */     sb.append("BackgroundFetch ").append(this.cquery);
/* 84 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\BackgroundFetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */