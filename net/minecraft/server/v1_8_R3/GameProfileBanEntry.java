/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class GameProfileBanEntry extends ExpirableListEntry<GameProfile>
/*    */ {
/*    */   public GameProfileBanEntry(GameProfile gameprofile)
/*    */   {
/* 11 */     this(gameprofile, null, null, null, null);
/*    */   }
/*    */   
/*    */   public GameProfileBanEntry(GameProfile gameprofile, Date date, String s, Date date1, String s1) {
/* 15 */     super(gameprofile, date, s, date1, s1);
/*    */   }
/*    */   
/*    */   public GameProfileBanEntry(JsonObject jsonobject) {
/* 19 */     super(b(jsonobject), jsonobject);
/*    */   }
/*    */   
/*    */   protected void a(JsonObject jsonobject) {
/* 23 */     if (getKey() != null) {
/* 24 */       jsonobject.addProperty("uuid", ((GameProfile)getKey()).getId() == null ? "" : ((GameProfile)getKey()).getId().toString());
/* 25 */       jsonobject.addProperty("name", ((GameProfile)getKey()).getName());
/* 26 */       super.a(jsonobject);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   private static GameProfile b(JsonObject jsonobject)
/*    */   {
/* 33 */     java.util.UUID uuid = null;
/* 34 */     String name = null;
/* 35 */     if (jsonobject.has("uuid")) {
/* 36 */       String s = jsonobject.get("uuid").getAsString();
/*    */       try
/*    */       {
/* 39 */         uuid = java.util.UUID.fromString(s);
/*    */       }
/*    */       catch (Throwable localThrowable) {}
/*    */     }
/*    */     
/* 44 */     if (jsonobject.has("name"))
/*    */     {
/* 46 */       name = jsonobject.get("name").getAsString();
/*    */     }
/* 48 */     if ((uuid != null) || (name != null))
/*    */     {
/* 50 */       return new GameProfile(uuid, name);
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GameProfileBanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */