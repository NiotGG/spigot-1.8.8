/*    */ package com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class RefreshRequest
/*    */ {
/*    */   private String clientToken;
/*    */   private String accessToken;
/*    */   private com.mojang.authlib.GameProfile selectedProfile;
/* 10 */   private boolean requestUser = true;
/*    */   
/*    */   public RefreshRequest(YggdrasilUserAuthentication paramYggdrasilUserAuthentication) {
/* 13 */     this(paramYggdrasilUserAuthentication, null);
/*    */   }
/*    */   
/*    */   public RefreshRequest(YggdrasilUserAuthentication paramYggdrasilUserAuthentication, com.mojang.authlib.GameProfile paramGameProfile) {
/* 17 */     this.clientToken = paramYggdrasilUserAuthentication.getAuthenticationService().getClientToken();
/* 18 */     this.accessToken = paramYggdrasilUserAuthentication.getAuthenticatedToken();
/* 19 */     this.selectedProfile = paramGameProfile;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\request\RefreshRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */