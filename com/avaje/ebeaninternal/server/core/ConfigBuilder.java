/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigBuilder
/*    */ {
/*    */   public ServerConfig build(String serverName)
/*    */   {
/* 16 */     ServerConfig config = new ServerConfig();
/* 17 */     config.setName(serverName);
/*    */     
/* 19 */     config.loadFromProperties();
/*    */     
/* 21 */     return config;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\ConfigBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */