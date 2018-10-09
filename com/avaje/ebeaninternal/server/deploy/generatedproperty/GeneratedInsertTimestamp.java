/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.sql.Timestamp;
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
/*    */ public class GeneratedInsertTimestamp
/*    */   implements GeneratedProperty
/*    */ {
/*    */   public Object getInsertValue(BeanProperty prop, Object bean)
/*    */   {
/* 35 */     return new Timestamp(System.currentTimeMillis());
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


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedInsertTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */