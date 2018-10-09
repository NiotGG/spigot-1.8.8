/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class WhiteListEntry extends JsonListEntry<GameProfile>
/*    */ {
/*    */   public WhiteListEntry(GameProfile paramGameProfile)
/*    */   {
/* 10 */     super(paramGameProfile);
/*    */   }
/*    */   
/*    */   public WhiteListEntry(JsonObject paramJsonObject) {
/* 14 */     super(b(paramJsonObject), paramJsonObject);
/*    */   }
/*    */   
/*    */   protected void a(JsonObject paramJsonObject)
/*    */   {
/* 19 */     if (getKey() == null) {
/* 20 */       return;
/*    */     }
/* 22 */     paramJsonObject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
/* 23 */     paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
/* 24 */     super.a(paramJsonObject);
/*    */   }
/*    */   
/*    */   private static GameProfile b(JsonObject paramJsonObject) {
/* 28 */     if ((!paramJsonObject.has("uuid")) || (!paramJsonObject.has("name"))) {
/* 29 */       return null;
/*    */     }
/* 31 */     String str = paramJsonObject.get("uuid").getAsString();
/*    */     java.util.UUID localUUID;
/*    */     try {
/* 34 */       localUUID = java.util.UUID.fromString(str);
/*    */     } catch (Throwable localThrowable) {
/* 36 */       return null;
/*    */     }
/* 38 */     return new GameProfile(localUUID, paramJsonObject.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WhiteListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */