/*    */ package com.mojang.authlib.minecraft;
/*    */ 
/*    */ import com.mojang.authlib.AuthenticationService;
/*    */ 
/*    */ public abstract class BaseMinecraftSessionService implements MinecraftSessionService {
/*    */   private final AuthenticationService authenticationService;
/*    */   
/*    */   protected BaseMinecraftSessionService(AuthenticationService paramAuthenticationService) {
/*  9 */     this.authenticationService = paramAuthenticationService;
/*    */   }
/*    */   
/*    */   public AuthenticationService getAuthenticationService() {
/* 13 */     return this.authenticationService;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\minecraft\BaseMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */