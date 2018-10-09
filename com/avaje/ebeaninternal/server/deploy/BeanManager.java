/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
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
/*    */ public class BeanManager<T>
/*    */ {
/*    */   private final BeanPersister persister;
/*    */   private final BeanDescriptor<T> descriptor;
/*    */   
/*    */   public BeanManager(BeanDescriptor<T> descriptor, BeanPersister persister)
/*    */   {
/* 34 */     this.descriptor = descriptor;
/* 35 */     this.persister = persister;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public BeanPersister getBeanPersister()
/*    */   {
/* 42 */     return this.persister;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public BeanDescriptor<T> getBeanDescriptor()
/*    */   {
/* 49 */     return this.descriptor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isLdapEntityType()
/*    */   {
/* 56 */     return this.descriptor.isLdapEntityType();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */