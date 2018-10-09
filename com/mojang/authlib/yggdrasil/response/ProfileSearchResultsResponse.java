/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class ProfileSearchResultsResponse extends Response
/*    */ {
/*    */   private GameProfile[] profiles;
/*    */   
/*    */   public GameProfile[] getProfiles()
/*    */   {
/* 12 */     return this.profiles;
/*    */   }
/*    */   
/*    */   public static class Serializer implements com.google.gson.JsonDeserializer<ProfileSearchResultsResponse>
/*    */   {
/*    */     public ProfileSearchResultsResponse deserialize(com.google.gson.JsonElement paramJsonElement, java.lang.reflect.Type paramType, com.google.gson.JsonDeserializationContext paramJsonDeserializationContext) throws com.google.gson.JsonParseException {
/* 18 */       ProfileSearchResultsResponse localProfileSearchResultsResponse = new ProfileSearchResultsResponse();
/*    */       
/* 20 */       if ((paramJsonElement instanceof JsonObject)) {
/* 21 */         JsonObject localJsonObject = (JsonObject)paramJsonElement;
/* 22 */         if (localJsonObject.has("error")) {
/* 23 */           localProfileSearchResultsResponse.setError(localJsonObject.getAsJsonPrimitive("error").getAsString());
/*    */         }
/* 25 */         if (localJsonObject.has("errorMessage")) {
/* 26 */           localProfileSearchResultsResponse.setError(localJsonObject.getAsJsonPrimitive("errorMessage").getAsString());
/*    */         }
/* 28 */         if (localJsonObject.has("cause")) {
/* 29 */           localProfileSearchResultsResponse.setError(localJsonObject.getAsJsonPrimitive("cause").getAsString());
/*    */         }
/*    */       } else {
/* 32 */         localProfileSearchResultsResponse.profiles = ((GameProfile[])paramJsonDeserializationContext.deserialize(paramJsonElement, GameProfile[].class));
/*    */       }
/*    */       
/* 35 */       return localProfileSearchResultsResponse;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\response\ProfileSearchResultsResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */