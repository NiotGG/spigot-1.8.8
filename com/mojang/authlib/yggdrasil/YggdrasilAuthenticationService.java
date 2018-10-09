/*    */ package com.mojang.authlib.yggdrasil;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*    */ import com.mojang.authlib.yggdrasil.response.Response;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class YggdrasilAuthenticationService extends HttpAuthenticationService
/*    */ {
/*    */   private final String clientToken;
/*    */   private final Gson gson;
/*    */   
/*    */   public YggdrasilAuthenticationService(Proxy paramProxy, String paramString)
/*    */   {
/* 27 */     super(paramProxy);
/* 28 */     this.clientToken = paramString;
/* 29 */     GsonBuilder localGsonBuilder = new GsonBuilder();
/* 30 */     localGsonBuilder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer(null));
/* 31 */     localGsonBuilder.registerTypeAdapter(com.mojang.authlib.properties.PropertyMap.class, new com.mojang.authlib.properties.PropertyMap.Serializer());
/* 32 */     localGsonBuilder.registerTypeAdapter(UUID.class, new com.mojang.util.UUIDTypeAdapter());
/* 33 */     localGsonBuilder.registerTypeAdapter(com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse.class, new com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse.Serializer());
/* 34 */     this.gson = localGsonBuilder.create();
/*    */   }
/*    */   
/*    */   public com.mojang.authlib.UserAuthentication createUserAuthentication(com.mojang.authlib.Agent paramAgent)
/*    */   {
/* 39 */     return new YggdrasilUserAuthentication(this, paramAgent);
/*    */   }
/*    */   
/*    */   public com.mojang.authlib.minecraft.MinecraftSessionService createMinecraftSessionService()
/*    */   {
/* 44 */     return new YggdrasilMinecraftSessionService(this);
/*    */   }
/*    */   
/*    */   public com.mojang.authlib.GameProfileRepository createProfileRepository()
/*    */   {
/* 49 */     return new YggdrasilGameProfileRepository(this);
/*    */   }
/*    */   
/*    */   protected <T extends Response> T makeRequest(URL paramURL, Object paramObject, Class<T> paramClass) throws com.mojang.authlib.exceptions.AuthenticationException {
/*    */     try {
/* 54 */       String str = paramObject == null ? performGetRequest(paramURL) : performPostRequest(paramURL, this.gson.toJson(paramObject), "application/json");
/* 55 */       Response localResponse = (Response)this.gson.fromJson(str, paramClass);
/*    */       
/* 57 */       if (localResponse == null) { return null;
/*    */       }
/* 59 */       if (org.apache.commons.lang3.StringUtils.isNotBlank(localResponse.getError())) {
/* 60 */         if ("UserMigratedException".equals(localResponse.getCause()))
/* 61 */           throw new com.mojang.authlib.exceptions.UserMigratedException(localResponse.getErrorMessage());
/* 62 */         if (localResponse.getError().equals("ForbiddenOperationException")) {
/* 63 */           throw new com.mojang.authlib.exceptions.InvalidCredentialsException(localResponse.getErrorMessage());
/*    */         }
/* 65 */         throw new com.mojang.authlib.exceptions.AuthenticationException(localResponse.getErrorMessage());
/*    */       }
/*    */       
/*    */ 
/* 69 */       return localResponse;
/*    */     } catch (IOException localIOException) {
/* 71 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", localIOException);
/*    */     } catch (IllegalStateException localIllegalStateException) {
/* 73 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", localIllegalStateException);
/*    */     } catch (JsonParseException localJsonParseException) {
/* 75 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", localJsonParseException);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getClientToken() {
/* 80 */     return this.clientToken;
/*    */   }
/*    */   
/*    */   private static class GameProfileSerializer implements com.google.gson.JsonSerializer<GameProfile>, com.google.gson.JsonDeserializer<GameProfile>
/*    */   {
/*    */     public GameProfile deserialize(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
/* 86 */       JsonObject localJsonObject = (JsonObject)paramJsonElement;
/* 87 */       UUID localUUID = localJsonObject.has("id") ? (UUID)paramJsonDeserializationContext.deserialize(localJsonObject.get("id"), UUID.class) : null;
/* 88 */       String str = localJsonObject.has("name") ? localJsonObject.getAsJsonPrimitive("name").getAsString() : null;
/* 89 */       return new GameProfile(localUUID, str);
/*    */     }
/*    */     
/*    */     public JsonElement serialize(GameProfile paramGameProfile, Type paramType, JsonSerializationContext paramJsonSerializationContext)
/*    */     {
/* 94 */       JsonObject localJsonObject = new JsonObject();
/* 95 */       if (paramGameProfile.getId() != null) localJsonObject.add("id", paramJsonSerializationContext.serialize(paramGameProfile.getId()));
/* 96 */       if (paramGameProfile.getName() != null) localJsonObject.addProperty("name", paramGameProfile.getName());
/* 97 */       return localJsonObject;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\YggdrasilAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */