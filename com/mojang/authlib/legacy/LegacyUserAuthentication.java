/*     */ package com.mojang.authlib.legacy;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.HttpAuthenticationService;
/*     */ import com.mojang.authlib.HttpUserAuthentication;
/*     */ import com.mojang.authlib.UserType;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class LegacyUserAuthentication extends HttpUserAuthentication
/*     */ {
/*  18 */   private static final URL AUTHENTICATION_URL = HttpAuthenticationService.constantURL("https://login.minecraft.net");
/*     */   
/*     */   private static final int AUTHENTICATION_VERSION = 14;
/*     */   
/*     */   private static final int RESPONSE_PART_PROFILE_NAME = 2;
/*     */   
/*     */   private static final int RESPONSE_PART_SESSION_TOKEN = 3;
/*     */   private static final int RESPONSE_PART_PROFILE_ID = 4;
/*     */   private String sessionToken;
/*     */   
/*     */   protected LegacyUserAuthentication(LegacyAuthenticationService paramLegacyAuthenticationService)
/*     */   {
/*  30 */     super(paramLegacyAuthenticationService);
/*     */   }
/*     */   
/*     */   public void logIn() throws AuthenticationException
/*     */   {
/*  35 */     if (StringUtils.isBlank(getUsername())) {
/*  36 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*  38 */     if (StringUtils.isBlank(getPassword())) {
/*  39 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */     
/*  42 */     HashMap localHashMap = new HashMap();
/*  43 */     localHashMap.put("user", getUsername());
/*  44 */     localHashMap.put("password", getPassword());
/*  45 */     localHashMap.put("version", Integer.valueOf(14));
/*     */     String str1;
/*     */     try
/*     */     {
/*  49 */       str1 = getAuthenticationService().performPostRequest(AUTHENTICATION_URL, HttpAuthenticationService.buildQuery(localHashMap), "application/x-www-form-urlencoded").trim();
/*     */     } catch (IOException localIOException) {
/*  51 */       throw new AuthenticationException("Authentication server is not responding", localIOException);
/*     */     }
/*     */     
/*  54 */     String[] arrayOfString = str1.split(":");
/*     */     
/*  56 */     if (arrayOfString.length == 5) {
/*  57 */       String str2 = arrayOfString[4];
/*  58 */       String str3 = arrayOfString[2];
/*  59 */       String str4 = arrayOfString[3];
/*     */       
/*  61 */       if ((StringUtils.isBlank(str2)) || (StringUtils.isBlank(str3)) || (StringUtils.isBlank(str4))) {
/*  62 */         throw new AuthenticationException("Unknown response from authentication server: " + str1);
/*     */       }
/*     */       
/*  65 */       setSelectedProfile(new GameProfile(UUIDTypeAdapter.fromString(str2), str3));
/*  66 */       this.sessionToken = str4;
/*  67 */       setUserType(UserType.LEGACY);
/*     */     } else {
/*  69 */       throw new InvalidCredentialsException(str1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void logOut()
/*     */   {
/*  75 */     super.logOut();
/*  76 */     this.sessionToken = null;
/*     */   }
/*     */   
/*     */   public boolean canPlayOnline()
/*     */   {
/*  81 */     return (isLoggedIn()) && (getSelectedProfile() != null) && (getAuthenticatedToken() != null);
/*     */   }
/*     */   
/*     */   public GameProfile[] getAvailableProfiles()
/*     */   {
/*  86 */     if (getSelectedProfile() != null) {
/*  87 */       return new GameProfile[] { getSelectedProfile() };
/*     */     }
/*  89 */     return new GameProfile[0];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void selectGameProfile(GameProfile paramGameProfile)
/*     */     throws AuthenticationException
/*     */   {
/* 100 */     throw new UnsupportedOperationException("Game profiles cannot be changed in the legacy authentication service");
/*     */   }
/*     */   
/*     */   public String getAuthenticatedToken()
/*     */   {
/* 105 */     return this.sessionToken;
/*     */   }
/*     */   
/*     */   public String getUserID()
/*     */   {
/* 110 */     return getUsername();
/*     */   }
/*     */   
/*     */   public LegacyAuthenticationService getAuthenticationService()
/*     */   {
/* 115 */     return (LegacyAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\legacy\LegacyUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */