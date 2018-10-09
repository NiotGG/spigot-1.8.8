/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlBeanPersisterFactory;
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
/*    */ public class BeanManagerFactory
/*    */ {
/*    */   final BeanPersisterFactory peristerFactory;
/*    */   
/*    */   public BeanManagerFactory(ServerConfig config, DatabasePlatform dbPlatform)
/*    */   {
/* 36 */     this.peristerFactory = new DmlBeanPersisterFactory(dbPlatform);
/*    */   }
/*    */   
/*    */   public <T> BeanManager<T> create(BeanDescriptor<T> desc)
/*    */   {
/* 41 */     BeanPersister persister = this.peristerFactory.create(desc);
/*    */     
/* 43 */     return new BeanManager(desc, persister);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */