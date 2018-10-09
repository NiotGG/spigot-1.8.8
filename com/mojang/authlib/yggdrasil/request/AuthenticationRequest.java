/*    */ package com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class AuthenticationRequest
/*    */ {
/*    */   private com.mojang.authlib.Agent agent;
/*    */   private String username;
/*    */   private String password;
/*    */   private String clientToken;
/* 11 */   private boolean requestUser = true;
/*    */   
/*    */   public AuthenticationRequest(YggdrasilUserAuthentication paramYggdrasilUserAuthentication, String paramString1, String paramString2) {
/* 14 */     this.agent = paramYggdrasilUserAuthentication.getAgent();
/* 15 */     this.username = paramString1;
/* 16 */     this.clientToken = paramYggdrasilUserAuthentication.getAuthenticationService().getClientToken();
/* 17 */     this.password = paramString2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\request\AuthenticationRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */