/*     */ package com.mojang.authlib.yggdrasil;
/*     */ 
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.HttpAuthenticationService;
/*     */ import com.mojang.authlib.HttpUserAuthentication;
/*     */ import com.mojang.authlib.UserType;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.authlib.yggdrasil.request.RefreshRequest;
/*     */ import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.RefreshResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.User;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilUserAuthentication extends HttpUserAuthentication
/*     */ {
/*  23 */   private static final Logger LOGGER = ;
/*     */   private static final String BASE_URL = "https://authserver.mojang.com/";
/*  25 */   private static final URL ROUTE_AUTHENTICATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/authenticate");
/*  26 */   private static final URL ROUTE_REFRESH = HttpAuthenticationService.constantURL("https://authserver.mojang.com/refresh");
/*  27 */   private static final URL ROUTE_VALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/validate");
/*  28 */   private static final URL ROUTE_INVALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/invalidate");
/*  29 */   private static final URL ROUTE_SIGNOUT = HttpAuthenticationService.constantURL("https://authserver.mojang.com/signout");
/*     */   
/*     */   private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
/*     */   private final Agent agent;
/*     */   private GameProfile[] profiles;
/*     */   private String accessToken;
/*     */   private boolean isOnline;
/*     */   
/*     */   public YggdrasilUserAuthentication(YggdrasilAuthenticationService paramYggdrasilAuthenticationService, Agent paramAgent)
/*     */   {
/*  39 */     super(paramYggdrasilAuthenticationService);
/*  40 */     this.agent = paramAgent;
/*     */   }
/*     */   
/*     */   public boolean canLogIn()
/*     */   {
/*  45 */     return (!canPlayOnline()) && (StringUtils.isNotBlank(getUsername())) && ((StringUtils.isNotBlank(getPassword())) || (StringUtils.isNotBlank(getAuthenticatedToken())));
/*     */   }
/*     */   
/*     */   public void logIn() throws AuthenticationException
/*     */   {
/*  50 */     if (StringUtils.isBlank(getUsername())) {
/*  51 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*     */     
/*  54 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/*  55 */       logInWithToken();
/*  56 */     } else if (StringUtils.isNotBlank(getPassword())) {
/*  57 */       logInWithPassword();
/*     */     } else {
/*  59 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void logInWithPassword() throws AuthenticationException {
/*  64 */     if (StringUtils.isBlank(getUsername())) {
/*  65 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*  67 */     if (StringUtils.isBlank(getPassword())) {
/*  68 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */     
/*  71 */     LOGGER.info("Logging in with username & password");
/*     */     
/*  73 */     com.mojang.authlib.yggdrasil.request.AuthenticationRequest localAuthenticationRequest = new com.mojang.authlib.yggdrasil.request.AuthenticationRequest(this, getUsername(), getPassword());
/*  74 */     AuthenticationResponse localAuthenticationResponse = (AuthenticationResponse)getAuthenticationService().makeRequest(ROUTE_AUTHENTICATE, localAuthenticationRequest, AuthenticationResponse.class);
/*     */     
/*  76 */     if (!localAuthenticationResponse.getClientToken().equals(getAuthenticationService().getClientToken())) {
/*  77 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/*  80 */     if (localAuthenticationResponse.getSelectedProfile() != null) {
/*  81 */       setUserType(localAuthenticationResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*  82 */     } else if (ArrayUtils.isNotEmpty(localAuthenticationResponse.getAvailableProfiles())) {
/*  83 */       setUserType(localAuthenticationResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     }
/*     */     
/*  86 */     User localUser = localAuthenticationResponse.getUser();
/*     */     
/*  88 */     if ((localUser != null) && (localUser.getId() != null)) {
/*  89 */       setUserid(localUser.getId());
/*     */     } else {
/*  91 */       setUserid(getUsername());
/*     */     }
/*     */     
/*  94 */     this.isOnline = true;
/*  95 */     this.accessToken = localAuthenticationResponse.getAccessToken();
/*  96 */     this.profiles = localAuthenticationResponse.getAvailableProfiles();
/*  97 */     setSelectedProfile(localAuthenticationResponse.getSelectedProfile());
/*  98 */     getModifiableUserProperties().clear();
/*     */     
/* 100 */     updateUserProperties(localUser);
/*     */   }
/*     */   
/*     */   protected void updateUserProperties(User paramUser) {
/* 104 */     if (paramUser == null) { return;
/*     */     }
/* 106 */     if (paramUser.getProperties() != null) {
/* 107 */       getModifiableUserProperties().putAll(paramUser.getProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void logInWithToken() throws AuthenticationException {
/* 112 */     if (StringUtils.isBlank(getUserID())) {
/* 113 */       if (StringUtils.isBlank(getUsername())) {
/* 114 */         setUserid(getUsername());
/*     */       } else {
/* 116 */         throw new InvalidCredentialsException("Invalid uuid & username");
/*     */       }
/*     */     }
/* 119 */     if (StringUtils.isBlank(getAuthenticatedToken())) {
/* 120 */       throw new InvalidCredentialsException("Invalid access token");
/*     */     }
/*     */     
/* 123 */     LOGGER.info("Logging in with access token");
/*     */     
/* 125 */     if (checkTokenValidity()) {
/* 126 */       LOGGER.debug("Skipping refresh call as we're safely logged in.");
/* 127 */       this.isOnline = true;
/* 128 */       return;
/*     */     }
/*     */     
/* 131 */     RefreshRequest localRefreshRequest = new RefreshRequest(this);
/* 132 */     RefreshResponse localRefreshResponse = (RefreshResponse)getAuthenticationService().makeRequest(ROUTE_REFRESH, localRefreshRequest, RefreshResponse.class);
/*     */     
/* 134 */     if (!localRefreshResponse.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 135 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 138 */     if (localRefreshResponse.getSelectedProfile() != null) {
/* 139 */       setUserType(localRefreshResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/* 140 */     } else if (ArrayUtils.isNotEmpty(localRefreshResponse.getAvailableProfiles())) {
/* 141 */       setUserType(localRefreshResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     }
/*     */     
/* 144 */     if ((localRefreshResponse.getUser() != null) && (localRefreshResponse.getUser().getId() != null)) {
/* 145 */       setUserid(localRefreshResponse.getUser().getId());
/*     */     } else {
/* 147 */       setUserid(getUsername());
/*     */     }
/*     */     
/* 150 */     this.isOnline = true;
/* 151 */     this.accessToken = localRefreshResponse.getAccessToken();
/* 152 */     this.profiles = localRefreshResponse.getAvailableProfiles();
/* 153 */     setSelectedProfile(localRefreshResponse.getSelectedProfile());
/* 154 */     getModifiableUserProperties().clear();
/*     */     
/* 156 */     updateUserProperties(localRefreshResponse.getUser());
/*     */   }
/*     */   
/*     */   protected boolean checkTokenValidity() throws AuthenticationException {
/* 160 */     com.mojang.authlib.yggdrasil.request.ValidateRequest localValidateRequest = new com.mojang.authlib.yggdrasil.request.ValidateRequest(this);
/*     */     try {
/* 162 */       getAuthenticationService().makeRequest(ROUTE_VALIDATE, localValidateRequest, com.mojang.authlib.yggdrasil.response.Response.class);
/* 163 */       return true;
/*     */     } catch (AuthenticationException localAuthenticationException) {}
/* 165 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void logOut()
/*     */   {
/* 171 */     super.logOut();
/*     */     
/* 173 */     this.accessToken = null;
/* 174 */     this.profiles = null;
/* 175 */     this.isOnline = false;
/*     */   }
/*     */   
/*     */   public GameProfile[] getAvailableProfiles()
/*     */   {
/* 180 */     return this.profiles;
/*     */   }
/*     */   
/*     */   public boolean isLoggedIn()
/*     */   {
/* 185 */     return StringUtils.isNotBlank(this.accessToken);
/*     */   }
/*     */   
/*     */   public boolean canPlayOnline()
/*     */   {
/* 190 */     return (isLoggedIn()) && (getSelectedProfile() != null) && (this.isOnline);
/*     */   }
/*     */   
/*     */   public void selectGameProfile(GameProfile paramGameProfile) throws AuthenticationException
/*     */   {
/* 195 */     if (!isLoggedIn()) {
/* 196 */       throw new AuthenticationException("Cannot change game profile whilst not logged in");
/*     */     }
/* 198 */     if (getSelectedProfile() != null) {
/* 199 */       throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
/*     */     }
/* 201 */     if ((paramGameProfile == null) || (!ArrayUtils.contains(this.profiles, paramGameProfile))) {
/* 202 */       throw new IllegalArgumentException("Invalid profile '" + paramGameProfile + "'");
/*     */     }
/*     */     
/* 205 */     RefreshRequest localRefreshRequest = new RefreshRequest(this, paramGameProfile);
/* 206 */     RefreshResponse localRefreshResponse = (RefreshResponse)getAuthenticationService().makeRequest(ROUTE_REFRESH, localRefreshRequest, RefreshResponse.class);
/*     */     
/* 208 */     if (!localRefreshResponse.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 209 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 212 */     this.isOnline = true;
/* 213 */     this.accessToken = localRefreshResponse.getAccessToken();
/* 214 */     setSelectedProfile(localRefreshResponse.getSelectedProfile());
/*     */   }
/*     */   
/*     */   public void loadFromStorage(Map<String, Object> paramMap)
/*     */   {
/* 219 */     super.loadFromStorage(paramMap);
/*     */     
/* 221 */     this.accessToken = String.valueOf(paramMap.get("accessToken"));
/*     */   }
/*     */   
/*     */   public Map<String, Object> saveForStorage()
/*     */   {
/* 226 */     Map localMap = super.saveForStorage();
/*     */     
/* 228 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/* 229 */       localMap.put("accessToken", getAuthenticatedToken());
/*     */     }
/*     */     
/* 232 */     return localMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public String getSessionToken()
/*     */   {
/* 240 */     if ((isLoggedIn()) && (getSelectedProfile() != null) && (canPlayOnline())) {
/* 241 */       return String.format("token:%s:%s", new Object[] { getAuthenticatedToken(), getSelectedProfile().getId() });
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getAuthenticatedToken()
/*     */   {
/* 249 */     return this.accessToken;
/*     */   }
/*     */   
/*     */   public Agent getAgent() {
/* 253 */     return this.agent;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 258 */     return "YggdrasilAuthenticationService{agent=" + this.agent + ", profiles=" + java.util.Arrays.toString(this.profiles) + ", selectedProfile=" + getSelectedProfile() + ", username='" + getUsername() + '\'' + ", isLoggedIn=" + isLoggedIn() + ", userType=" + getUserType() + ", canPlayOnline=" + canPlayOnline() + ", accessToken='" + this.accessToken + '\'' + ", clientToken='" + getAuthenticationService().getClientToken() + '\'' + '}';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public YggdrasilAuthenticationService getAuthenticationService()
/*     */   {
/* 273 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\YggdrasilUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */