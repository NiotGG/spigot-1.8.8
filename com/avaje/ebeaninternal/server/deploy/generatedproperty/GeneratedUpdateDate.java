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
/*    */ 
/*    */ public class GeneratedUpdateDate
/*    */   implements GeneratedProperty
/*    */ {
/*    */   public Object getInsertValue(BeanProperty prop, Object bean)
/*    */   {
/* 36 */     return new Date(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getUpdateValue(BeanProperty prop, Object bean)
/*    */   {
/* 43 */     return new Date(System.currentTimeMillis());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInUpdate()
/*    */   {
/* 50 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean includeInInsert()
/*    */   {
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDDLNotNullable() {
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedUpdateDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */