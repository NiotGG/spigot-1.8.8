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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneratedCounterInteger
/*    */   implements GeneratedProperty
/*    */ {
/*    */   public Object getInsertValue(BeanProperty prop, Object bean)
/*    */   {
/* 37 */     return Integer.valueOf(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean)
/*    */   {
/* 44 */     Integer i = (Integer)prop.getValue(bean);
/* 45 */     return Integer.valueOf(i.intValue() + 1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInUpdate()
/*    */   {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInInsert()
/*    */   {
/* 59 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDDLNotNullable() {
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedCounterInteger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */