/*    */ package com.avaje.ebeaninternal.server.idgen;
/*    */ 
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UuidIdGenerator
/*    */   implements IdGenerator
/*    */ {
/*    */   public Object nextId(Transaction t)
/*    */   {
/* 17 */     return UUID.randomUUID();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 24 */     return "uuid";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isDbSequence()
/*    */   {
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   public void preAllocateIds(int allocateSize) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\idgen\UuidIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */