/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.common.BeanMap;
/*    */ import com.avaje.ebean.common.BeanSet;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery.Type;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.LinkedHashSet;
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
/*    */ public class BeanCollectionFactory
/*    */ {
/*    */   private static final int defaultListInitialCapacity = 20;
/*    */   private static final int defaultSetInitialCapacity = 32;
/*    */   private static final int defaultMapInitialCapacity = 32;
/*    */   
/*    */   private static class BeanCollectionFactoryHolder
/*    */   {
/* 41 */     private static BeanCollectionFactory me = new BeanCollectionFactory(null);
/*    */   }
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
/*    */   public static BeanCollection<?> create(BeanCollectionParams params)
/*    */   {
/* 56 */     return BeanCollectionFactoryHolder.me.createMany(params);
/*    */   }
/*    */   
/*    */ 
/*    */   private BeanCollection<?> createMany(BeanCollectionParams params)
/*    */   {
/* 62 */     SpiQuery.Type manyType = params.getManyType();
/* 63 */     switch (manyType) {
/*    */     case MAP: 
/* 65 */       return createMap(params);
/*    */     case LIST: 
/* 67 */       return createList(params);
/*    */     case SET: 
/* 69 */       return createSet(params);
/*    */     }
/*    */     
/* 72 */     throw new RuntimeException("Invalid Arg " + manyType);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private BeanMap createMap(BeanCollectionParams params)
/*    */   {
/* 80 */     return new BeanMap(new LinkedHashMap(32));
/*    */   }
/*    */   
/*    */ 
/*    */   private BeanSet createSet(BeanCollectionParams params)
/*    */   {
/* 86 */     return new BeanSet(new LinkedHashSet(32));
/*    */   }
/*    */   
/*    */ 
/*    */   private BeanList createList(BeanCollectionParams params)
/*    */   {
/* 92 */     return new BeanList(new ArrayList(20));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\util\BeanCollectionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */