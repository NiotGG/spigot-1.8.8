/*    */ package com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class ValidateRequest {
/*    */   private String clientToken;
/*    */   private String accessToken;
/*    */   
/*    */   public ValidateRequest(YggdrasilUserAuthentication paramYggdrasilUserAuthentication) {
/* 10 */     this.clientToken = paramYggdrasilUserAuthentication.getAuthenticationService().getClientToken();
/* 11 */     this.accessToken = paramYggdrasilUserAuthentication.getAuthenticatedToken();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\request\ValidateRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */