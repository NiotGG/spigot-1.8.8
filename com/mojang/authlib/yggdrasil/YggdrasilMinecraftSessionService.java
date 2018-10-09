/*     */ package com.mojang.authlib.yggdrasil;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.HttpAuthenticationService;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
/*     */ import com.mojang.authlib.minecraft.InsecureTextureException;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
/*     */ import com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
/*     */ import com.mojang.authlib.yggdrasil.response.Response;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService
/*     */ {
/*  42 */   private static final String[] WHITELISTED_DOMAINS = { ".minecraft.net", ".mojang.com" };
/*     */   
/*     */ 
/*     */ 
/*  46 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
/*  48 */   private static final URL JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
/*  49 */   private static final URL CHECK_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/hasJoined");
/*     */   
/*     */   private final PublicKey publicKey;
/*  52 */   private final Gson gson = new GsonBuilder().registerTypeAdapter(java.util.UUID.class, new UUIDTypeAdapter()).create();
/*  53 */   private final LoadingCache<GameProfile, GameProfile> insecureProfiles = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader()
/*     */   {
/*     */ 
/*     */     public GameProfile load(GameProfile paramAnonymousGameProfile)
/*     */       throws Exception
/*     */     {
/*  59 */       return YggdrasilMinecraftSessionService.this.fillGameProfile(paramAnonymousGameProfile, false);
/*     */     }
/*  53 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected YggdrasilMinecraftSessionService(YggdrasilAuthenticationService paramYggdrasilAuthenticationService)
/*     */   {
/*  64 */     super(paramYggdrasilAuthenticationService);
/*     */     try
/*     */     {
/*  67 */       X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
/*  68 */       KeyFactory localKeyFactory = KeyFactory.getInstance("RSA");
/*  69 */       this.publicKey = localKeyFactory.generatePublic(localX509EncodedKeySpec);
/*     */     } catch (Exception localException) {
/*  71 */       throw new Error("Missing/invalid yggdrasil public key!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void joinServer(GameProfile paramGameProfile, String paramString1, String paramString2) throws AuthenticationException
/*     */   {
/*  77 */     JoinMinecraftServerRequest localJoinMinecraftServerRequest = new JoinMinecraftServerRequest();
/*  78 */     localJoinMinecraftServerRequest.accessToken = paramString1;
/*  79 */     localJoinMinecraftServerRequest.selectedProfile = paramGameProfile.getId();
/*  80 */     localJoinMinecraftServerRequest.serverId = paramString2;
/*     */     
/*  82 */     getAuthenticationService().makeRequest(JOIN_URL, localJoinMinecraftServerRequest, Response.class);
/*     */   }
/*     */   
/*     */   public GameProfile hasJoinedServer(GameProfile paramGameProfile, String paramString) throws AuthenticationUnavailableException
/*     */   {
/*  87 */     HashMap localHashMap = new HashMap();
/*     */     
/*  89 */     localHashMap.put("username", paramGameProfile.getName());
/*  90 */     localHashMap.put("serverId", paramString);
/*     */     
/*  92 */     URL localURL = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(localHashMap));
/*     */     try
/*     */     {
/*  95 */       HasJoinedMinecraftServerResponse localHasJoinedMinecraftServerResponse = (HasJoinedMinecraftServerResponse)getAuthenticationService().makeRequest(localURL, null, HasJoinedMinecraftServerResponse.class);
/*     */       
/*  97 */       if ((localHasJoinedMinecraftServerResponse != null) && (localHasJoinedMinecraftServerResponse.getId() != null)) {
/*  98 */         GameProfile localGameProfile = new GameProfile(localHasJoinedMinecraftServerResponse.getId(), paramGameProfile.getName());
/*     */         
/* 100 */         if (localHasJoinedMinecraftServerResponse.getProperties() != null) {
/* 101 */           localGameProfile.getProperties().putAll(localHasJoinedMinecraftServerResponse.getProperties());
/*     */         }
/*     */         
/* 104 */         return localGameProfile;
/*     */       }
/* 106 */       return null;
/*     */     }
/*     */     catch (AuthenticationUnavailableException localAuthenticationUnavailableException) {
/* 109 */       throw localAuthenticationUnavailableException;
/*     */     } catch (AuthenticationException localAuthenticationException) {}
/* 111 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile paramGameProfile, boolean paramBoolean)
/*     */   {
/* 117 */     Property localProperty = (Property)Iterables.getFirst(paramGameProfile.getProperties().get("textures"), null);
/*     */     
/* 119 */     if (localProperty == null) {
/* 120 */       return new HashMap();
/*     */     }
/*     */     
/* 123 */     if (paramBoolean) {
/* 124 */       if (!localProperty.hasSignature()) {
/* 125 */         LOGGER.error("Signature is missing from textures payload");
/* 126 */         throw new InsecureTextureException("Signature is missing from textures payload");
/*     */       }
/*     */       
/* 129 */       if (!localProperty.isSignatureValid(this.publicKey)) {
/* 130 */         LOGGER.error("Textures payload has been tampered with (signature invalid)");
/* 131 */         throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
/*     */       }
/*     */     }
/*     */     MinecraftTexturesPayload localMinecraftTexturesPayload;
/*     */     try
/*     */     {
/* 137 */       String str = new String(org.apache.commons.codec.binary.Base64.decodeBase64(localProperty.getValue()), org.apache.commons.codec.Charsets.UTF_8);
/* 138 */       localMinecraftTexturesPayload = (MinecraftTexturesPayload)this.gson.fromJson(str, MinecraftTexturesPayload.class);
/*     */     } catch (JsonParseException localJsonParseException) {
/* 140 */       LOGGER.error("Could not decode textures payload", localJsonParseException);
/* 141 */       return new HashMap();
/*     */     }
/*     */     
/* 144 */     if (localMinecraftTexturesPayload.getTextures() == null) {
/* 145 */       return new HashMap();
/*     */     }
/*     */     
/* 148 */     for (Map.Entry localEntry : localMinecraftTexturesPayload.getTextures().entrySet()) {
/* 149 */       if (!isWhitelistedDomain(((MinecraftProfileTexture)localEntry.getValue()).getUrl())) {
/* 150 */         LOGGER.error("Textures payload has been tampered with (non-whitelisted domain)");
/* 151 */         return new HashMap();
/*     */       }
/*     */     }
/*     */     
/* 155 */     return localMinecraftTexturesPayload.getTextures();
/*     */   }
/*     */   
/*     */   public GameProfile fillProfileProperties(GameProfile paramGameProfile, boolean paramBoolean)
/*     */   {
/* 160 */     if (paramGameProfile.getId() == null) {
/* 161 */       return paramGameProfile;
/*     */     }
/*     */     
/* 164 */     if (!paramBoolean) {
/* 165 */       return (GameProfile)this.insecureProfiles.getUnchecked(paramGameProfile);
/*     */     }
/*     */     
/* 168 */     return fillGameProfile(paramGameProfile, true);
/*     */   }
/*     */   
/*     */   protected GameProfile fillGameProfile(GameProfile paramGameProfile, boolean paramBoolean) {
/*     */     try {
/* 173 */       URL localURL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDTypeAdapter.fromUUID(paramGameProfile.getId()));
/* 174 */       localURL = HttpAuthenticationService.concatenateURL(localURL, "unsigned=" + (!paramBoolean));
/* 175 */       MinecraftProfilePropertiesResponse localMinecraftProfilePropertiesResponse = (MinecraftProfilePropertiesResponse)getAuthenticationService().makeRequest(localURL, null, MinecraftProfilePropertiesResponse.class);
/*     */       
/* 177 */       if (localMinecraftProfilePropertiesResponse == null) {
/* 178 */         LOGGER.debug("Couldn't fetch profile properties for " + paramGameProfile + " as the profile does not exist");
/* 179 */         return paramGameProfile;
/*     */       }
/* 181 */       GameProfile localGameProfile = new GameProfile(localMinecraftProfilePropertiesResponse.getId(), localMinecraftProfilePropertiesResponse.getName());
/* 182 */       localGameProfile.getProperties().putAll(localMinecraftProfilePropertiesResponse.getProperties());
/* 183 */       paramGameProfile.getProperties().putAll(localMinecraftProfilePropertiesResponse.getProperties());
/* 184 */       LOGGER.debug("Successfully fetched profile properties for " + paramGameProfile);
/* 185 */       return localGameProfile;
/*     */     }
/*     */     catch (AuthenticationException localAuthenticationException) {
/* 188 */       LOGGER.warn("Couldn't look up profile properties for " + paramGameProfile, localAuthenticationException); }
/* 189 */     return paramGameProfile;
/*     */   }
/*     */   
/*     */ 
/*     */   public YggdrasilAuthenticationService getAuthenticationService()
/*     */   {
/* 195 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */   
/*     */   private static boolean isWhitelistedDomain(String paramString) {
/* 199 */     URI localURI = null;
/*     */     try
/*     */     {
/* 202 */       localURI = new URI(paramString);
/*     */     } catch (URISyntaxException localURISyntaxException) {
/* 204 */       throw new IllegalArgumentException("Invalid URL '" + paramString + "'");
/*     */     }
/*     */     
/* 207 */     String str = localURI.getHost();
/*     */     
/* 209 */     for (int i = 0; i < WHITELISTED_DOMAINS.length; i++) {
/* 210 */       if (str.endsWith(WHITELISTED_DOMAINS[i])) {
/* 211 */         return true;
/*     */       }
/*     */     }
/* 214 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\YggdrasilMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */