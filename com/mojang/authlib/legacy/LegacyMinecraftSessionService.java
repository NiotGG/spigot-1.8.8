/*    */ package com.mojang.authlib.legacy;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*    */ import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
/*    */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*    */ import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LegacyMinecraftSessionService extends HttpMinecraftSessionService
/*    */ {
/*    */   private static final String BASE_URL = "http://session.minecraft.net/game/";
/* 18 */   private static final URL JOIN_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/joinserver.jsp");
/* 19 */   private static final URL CHECK_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/checkserver.jsp");
/*    */   
/*    */   protected LegacyMinecraftSessionService(LegacyAuthenticationService paramLegacyAuthenticationService) {
/* 22 */     super(paramLegacyAuthenticationService);
/*    */   }
/*    */   
/*    */   public void joinServer(GameProfile paramGameProfile, String paramString1, String paramString2) throws AuthenticationException
/*    */   {
/* 27 */     HashMap localHashMap = new HashMap();
/*    */     
/* 29 */     localHashMap.put("user", paramGameProfile.getName());
/* 30 */     localHashMap.put("sessionId", paramString1);
/* 31 */     localHashMap.put("serverId", paramString2);
/*    */     
/* 33 */     URL localURL = HttpAuthenticationService.concatenateURL(JOIN_URL, HttpAuthenticationService.buildQuery(localHashMap));
/*    */     try
/*    */     {
/* 36 */       String str = getAuthenticationService().performGetRequest(localURL);
/*    */       
/* 38 */       if (!str.equals("OK")) {
/* 39 */         throw new AuthenticationException(str);
/*    */       }
/*    */     } catch (IOException localIOException) {
/* 42 */       throw new AuthenticationUnavailableException(localIOException);
/*    */     }
/*    */   }
/*    */   
/*    */   public GameProfile hasJoinedServer(GameProfile paramGameProfile, String paramString) throws AuthenticationUnavailableException
/*    */   {
/* 48 */     HashMap localHashMap = new HashMap();
/*    */     
/* 50 */     localHashMap.put("user", paramGameProfile.getName());
/* 51 */     localHashMap.put("serverId", paramString);
/*    */     
/* 53 */     URL localURL = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(localHashMap));
/*    */     try
/*    */     {
/* 56 */       String str = getAuthenticationService().performGetRequest(localURL);
/*    */       
/* 58 */       return str.equals("YES") ? paramGameProfile : null;
/*    */     } catch (IOException localIOException) {
/* 60 */       throw new AuthenticationUnavailableException(localIOException);
/*    */     }
/*    */   }
/*    */   
/*    */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile paramGameProfile, boolean paramBoolean)
/*    */   {
/* 66 */     return new HashMap();
/*    */   }
/*    */   
/*    */   public GameProfile fillProfileProperties(GameProfile paramGameProfile, boolean paramBoolean)
/*    */   {
/* 71 */     return paramGameProfile;
/*    */   }
/*    */   
/*    */   public LegacyAuthenticationService getAuthenticationService()
/*    */   {
/* 76 */     return (LegacyAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\legacy\LegacyMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */