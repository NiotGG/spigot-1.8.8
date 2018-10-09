/*    */ package com.mojang.authlib.minecraft;
/*    */ 
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ 
/*    */ public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService {
/*    */   protected HttpMinecraftSessionService(HttpAuthenticationService paramHttpAuthenticationService) {
/*  7 */     super(paramHttpAuthenticationService);
/*    */   }
/*    */   
/*    */   public HttpAuthenticationService getAuthenticationService()
/*    */   {
/* 12 */     return (HttpAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\minecraft\HttpMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */