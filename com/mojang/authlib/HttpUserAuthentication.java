/*    */ package com.mojang.authlib;
/*    */ 
/*    */ public abstract class HttpUserAuthentication extends BaseUserAuthentication {
/*    */   protected HttpUserAuthentication(HttpAuthenticationService paramHttpAuthenticationService) {
/*  5 */     super(paramHttpAuthenticationService);
/*    */   }
/*    */   
/*    */   public HttpAuthenticationService getAuthenticationService()
/*    */   {
/* 10 */     return (HttpAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\HttpUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */