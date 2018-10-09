/*    */ package com.mojang.authlib.legacy;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfileRepository;
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ import java.net.Proxy;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyAuthenticationService
/*    */   extends HttpAuthenticationService
/*    */ {
/*    */   protected LegacyAuthenticationService(Proxy paramProxy)
/*    */   {
/* 20 */     super(paramProxy);
/*    */   }
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
/*    */   public LegacyUserAuthentication createUserAuthentication(Agent paramAgent)
/*    */   {
/* 34 */     Validate.notNull(paramAgent);
/* 35 */     if (paramAgent != Agent.MINECRAFT) throw new IllegalArgumentException("Legacy authentication cannot handle anything but Minecraft");
/* 36 */     return new LegacyUserAuthentication(this);
/*    */   }
/*    */   
/*    */   public LegacyMinecraftSessionService createMinecraftSessionService()
/*    */   {
/* 41 */     return new LegacyMinecraftSessionService(this);
/*    */   }
/*    */   
/*    */   public GameProfileRepository createProfileRepository()
/*    */   {
/* 46 */     throw new UnsupportedOperationException("Legacy authentication service has no profile repository");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\legacy\LegacyAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */