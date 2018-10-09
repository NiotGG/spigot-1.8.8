/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.Date;
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
/*    */ public class GeneratedInsertDate
/*    */   implements GeneratedProperty
/*    */ {
/*    */   public Object getInsertValue(BeanProperty prop, Object bean)
/*    */   {
/* 35 */     return new Date(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean)
/*    */   {
/* 42 */     return prop.getValue(bean);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInUpdate()
/*    */   {
/* 49 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInInsert()
/*    */   {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDDLNotNullable() {
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedInsertDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */