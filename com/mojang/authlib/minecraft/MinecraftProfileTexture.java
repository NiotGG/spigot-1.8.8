/*    */ package com.mojang.authlib.minecraft;
/*    */ 
/*    */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*    */ 
/*    */ public class MinecraftProfileTexture
/*    */ {
/*    */   private final String url;
/*    */   private final java.util.Map<String, String> metadata;
/*    */   
/*    */   public static enum Type
/*    */   {
/* 12 */     SKIN, 
/* 13 */     CAPE;
/*    */     
/*    */ 
/*    */     private Type() {}
/*    */   }
/*    */   
/*    */   public MinecraftProfileTexture(String paramString, java.util.Map<String, String> paramMap)
/*    */   {
/* 21 */     this.url = paramString;
/* 22 */     this.metadata = paramMap;
/*    */   }
/*    */   
/*    */   public String getUrl() {
/* 26 */     return this.url;
/*    */   }
/*    */   
/*    */   @javax.annotation.Nullable
/*    */   public String getMetadata(String paramString) {
/* 31 */     if (this.metadata == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     return (String)this.metadata.get(paramString);
/*    */   }
/*    */   
/*    */   public String getHash() {
/* 38 */     return org.apache.commons.io.FilenameUtils.getBaseName(this.url);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return new ToStringBuilder(this).append("url", this.url).append("hash", getHash()).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\minecraft\MinecraftProfileTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */