/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
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
/*    */ public class GeneratedUpdateLong
/*    */   implements GeneratedProperty
/*    */ {
/*    */   public Object getInsertValue(BeanProperty prop, Object bean)
/*    */   {
/* 33 */     return Long.valueOf(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean)
/*    */   {
/* 40 */     return Long.valueOf(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInUpdate()
/*    */   {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInInsert()
/*    */   {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDDLNotNullable() {
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedUpdateLong.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */