/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import java.util.List;
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
/*    */ public class CallableQueryList<T>
/*    */   extends CallableQuery<T>
/*    */   implements Callable<List<T>>
/*    */ {
/*    */   public CallableQueryList(SpiEbeanServer server, Query<T> query, Transaction t)
/*    */   {
/* 38 */     super(server, query, t);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<T> call()
/*    */     throws Exception
/*    */   {
/* 45 */     return this.server.findList(this.query, this.t);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\CallableQueryList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */