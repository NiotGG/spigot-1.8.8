/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OpList extends JsonList<GameProfile, OpListEntry>
/*    */ {
/*    */   public OpList(File paramFile)
/*    */   {
/* 11 */     super(paramFile);
/*    */   }
/*    */   
/*    */   protected JsonListEntry<GameProfile> a(com.google.gson.JsonObject paramJsonObject)
/*    */   {
/* 16 */     return new OpListEntry(paramJsonObject);
/*    */   }
/*    */   
/*    */   public String[] getEntries()
/*    */   {
/* 21 */     String[] arrayOfString = new String[e().size()];
/* 22 */     int i = 0;
/* 23 */     for (OpListEntry localOpListEntry : e().values()) {
/* 24 */       arrayOfString[(i++)] = ((GameProfile)localOpListEntry.getKey()).getName();
/*    */     }
/* 26 */     return arrayOfString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean b(GameProfile paramGameProfile)
/*    */   {
/* 40 */     OpListEntry localOpListEntry = (OpListEntry)get(paramGameProfile);
/*    */     
/* 42 */     if (localOpListEntry != null) {
/* 43 */       return localOpListEntry.b();
/*    */     }
/*    */     
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   protected String c(GameProfile paramGameProfile)
/*    */   {
/* 51 */     return paramGameProfile.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile a(String paramString) {
/* 55 */     for (OpListEntry localOpListEntry : e().values()) {
/* 56 */       if (paramString.equalsIgnoreCase(((GameProfile)localOpListEntry.getKey()).getName())) {
/* 57 */         return (GameProfile)localOpListEntry.getKey();
/*    */       }
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\OpList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */