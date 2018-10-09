/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WhiteList extends JsonList<GameProfile, WhiteListEntry>
/*    */ {
/*    */   public WhiteList(java.io.File paramFile)
/*    */   {
/* 10 */     super(paramFile);
/*    */   }
/*    */   
/*    */   protected JsonListEntry<GameProfile> a(com.google.gson.JsonObject paramJsonObject)
/*    */   {
/* 15 */     return new WhiteListEntry(paramJsonObject);
/*    */   }
/*    */   
/*    */   public boolean isWhitelisted(GameProfile paramGameProfile) {
/* 19 */     return d(paramGameProfile);
/*    */   }
/*    */   
/*    */   public String[] getEntries()
/*    */   {
/* 24 */     String[] arrayOfString = new String[e().size()];
/* 25 */     int i = 0;
/* 26 */     for (WhiteListEntry localWhiteListEntry : e().values()) {
/* 27 */       arrayOfString[(i++)] = ((GameProfile)localWhiteListEntry.getKey()).getName();
/*    */     }
/* 29 */     return arrayOfString;
/*    */   }
/*    */   
/*    */   protected String b(GameProfile paramGameProfile)
/*    */   {
/* 34 */     return paramGameProfile.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile a(String paramString) {
/* 38 */     for (WhiteListEntry localWhiteListEntry : e().values()) {
/* 39 */       if (paramString.equalsIgnoreCase(((GameProfile)localWhiteListEntry.getKey()).getName())) {
/* 40 */         return (GameProfile)localWhiteListEntry.getKey();
/*    */       }
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WhiteList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */