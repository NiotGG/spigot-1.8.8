/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequest.Type;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public final class BeanPersistIdMap
/*    */ {
/* 35 */   private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap();
/*    */   
/*    */   public String toString() {
/* 38 */     return this.beanMap.toString();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 42 */     return this.beanMap.isEmpty();
/*    */   }
/*    */   
/*    */   public Collection<BeanPersistIds> values() {
/* 46 */     return this.beanMap.values();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(BeanDescriptor<?> desc, PersistRequest.Type type, Object id)
/*    */   {
/* 54 */     BeanPersistIds r = getPersistIds(desc);
/* 55 */     r.addId(type, (Serializable)id);
/*    */   }
/*    */   
/*    */   private BeanPersistIds getPersistIds(BeanDescriptor<?> desc) {
/* 59 */     String beanType = desc.getFullName();
/* 60 */     BeanPersistIds r = (BeanPersistIds)this.beanMap.get(beanType);
/* 61 */     if (r == null) {
/* 62 */       r = new BeanPersistIds(desc);
/* 63 */       this.beanMap.put(beanType, r);
/*    */     }
/* 65 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanPersistIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */