/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import java.util.concurrent.Callable;
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
/*    */ public class CallableQueryRowCount<T>
/*    */   extends CallableQuery<T>
/*    */   implements Callable<Integer>
/*    */ {
/*    */   public CallableQueryRowCount(SpiEbeanServer server, Query<T> query, Transaction t)
/*    */   {
/* 37 */     super(server, query, t);
/*    */   }
/*    */   
/*    */ 
/*    */   public Integer call()
/*    */     throws Exception
/*    */   {
/* 44 */     return Integer.valueOf(this.server.findRowCountWithCopy(this.query, this.t));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CallableQueryRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */