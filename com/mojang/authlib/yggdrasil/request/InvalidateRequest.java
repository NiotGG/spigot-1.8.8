/*    */ package com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class InvalidateRequest {
/*    */   private String accessToken;
/*    */   private String clientToken;
/*    */   
/*    */   public InvalidateRequest(YggdrasilUserAuthentication paramYggdrasilUserAuthentication) {
/* 10 */     this.accessToken = paramYggdrasilUserAuthentication.getAuthenticatedToken();
/* 11 */     this.clientToken = paramYggdrasilUserAuthentication.getAuthenticationService().getClientToken();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\request\InvalidateRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */