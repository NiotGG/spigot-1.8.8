/*    */ package com.avaje.ebeaninternal.server.deploy.meta;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployBeanEmbedded
/*    */ {
/* 37 */   Map<String, String> propMap = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */   public void put(String propertyName, String dbCoumn)
/*    */   {
/* 43 */     this.propMap.put(propertyName, dbCoumn);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void putAll(Map<String, String> propertyColumnMap)
/*    */   {
/* 50 */     this.propMap.putAll(propertyColumnMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Map<String, String> getPropertyColumnMap()
/*    */   {
/* 57 */     return this.propMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */