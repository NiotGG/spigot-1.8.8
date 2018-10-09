/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class OpListEntry extends JsonListEntry<GameProfile>
/*    */ {
/*    */   private final int a;
/*    */   private final boolean b;
/*    */   
/*    */   public OpListEntry(GameProfile paramGameProfile, int paramInt, boolean paramBoolean)
/*    */   {
/* 14 */     super(paramGameProfile);
/* 15 */     this.a = paramInt;
/* 16 */     this.b = paramBoolean;
/*    */   }
/*    */   
/*    */   public OpListEntry(JsonObject paramJsonObject) {
/* 20 */     super(b(paramJsonObject), paramJsonObject);
/* 21 */     this.a = (paramJsonObject.has("level") ? paramJsonObject.get("level").getAsInt() : 0);
/* 22 */     this.b = ((paramJsonObject.has("bypassesPlayerLimit")) && (paramJsonObject.get("bypassesPlayerLimit").getAsBoolean()));
/*    */   }
/*    */   
/*    */   public int a() {
/* 26 */     return this.a;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 30 */     return this.b;
/*    */   }
/*    */   
/*    */   protected void a(JsonObject paramJsonObject)
/*    */   {
/* 35 */     if (getKey() == null) {
/* 36 */       return;
/*    */     }
/* 38 */     paramJsonObject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
/* 39 */     paramJsonObject.addProperty("name", ((GameProfile)getKey()).getName());
/* 40 */     super.a(paramJsonObject);
/* 41 */     paramJsonObject.addProperty("level", Integer.valueOf(this.a));
/* 42 */     paramJsonObject.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.b));
/*    */   }
/*    */   
/*    */   private static GameProfile b(JsonObject paramJsonObject) {
/* 46 */     if ((!paramJsonObject.has("uuid")) || (!paramJsonObject.has("name"))) {
/* 47 */       return null;
/*    */     }
/* 49 */     String str = paramJsonObject.get("uuid").getAsString();
/*    */     java.util.UUID localUUID;
/*    */     try {
/* 52 */       localUUID = java.util.UUID.fromString(str);
/*    */     } catch (Throwable localThrowable) {
/* 54 */       return null;
/*    */     }
/* 56 */     return new GameProfile(localUUID, paramJsonObject.get("name").getAsString());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\OpListEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */