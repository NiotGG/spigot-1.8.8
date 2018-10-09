/*    */ package com.avaje.ebean.config.ldap;
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
/*    */ public class LdapConfig
/*    */ {
/*    */   private LdapContextFactory contextFactory;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private boolean vanillaMode;
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
/*    */   public LdapContextFactory getContextFactory()
/*    */   {
/* 38 */     return this.contextFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setContextFactory(LdapContextFactory contextFactory)
/*    */   {
/* 45 */     this.contextFactory = contextFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isVanillaMode()
/*    */   {
/* 52 */     return this.vanillaMode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setVanillaMode(boolean vanillaMode)
/*    */   {
/* 59 */     this.vanillaMode = vanillaMode;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ldap\LdapConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */