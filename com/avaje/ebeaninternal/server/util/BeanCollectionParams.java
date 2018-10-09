/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiQuery.Type;
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
/*    */ 
/*    */ public class BeanCollectionParams
/*    */ {
/*    */   private final SpiQuery.Type manyType;
/*    */   
/*    */   public BeanCollectionParams(SpiQuery.Type manyType)
/*    */   {
/* 35 */     this.manyType = manyType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public SpiQuery.Type getManyType()
/*    */   {
/* 42 */     return this.manyType;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\util\BeanCollectionParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */