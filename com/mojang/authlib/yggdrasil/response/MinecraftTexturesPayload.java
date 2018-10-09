/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MinecraftTexturesPayload
/*    */ {
/*    */   private long timestamp;
/*    */   private java.util.UUID profileId;
/*    */   private String profileName;
/*    */   private boolean isPublic;
/*    */   private Map<com.mojang.authlib.minecraft.MinecraftProfileTexture.Type, MinecraftProfileTexture> textures;
/*    */   
/*    */   public long getTimestamp()
/*    */   {
/* 16 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public java.util.UUID getProfileId() {
/* 20 */     return this.profileId;
/*    */   }
/*    */   
/*    */   public String getProfileName() {
/* 24 */     return this.profileName;
/*    */   }
/*    */   
/*    */   public boolean isPublic() {
/* 28 */     return this.isPublic;
/*    */   }
/*    */   
/*    */   public Map<com.mojang.authlib.minecraft.MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures() {
/* 32 */     return this.textures;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\response\MinecraftTexturesPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */