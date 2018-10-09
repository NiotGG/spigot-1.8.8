/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ 
/*    */ public class HasJoinedMinecraftServerResponse extends Response
/*    */ {
/*    */   private java.util.UUID id;
/*    */   private PropertyMap properties;
/*    */   
/*    */   public java.util.UUID getId()
/*    */   {
/* 12 */     return this.id;
/*    */   }
/*    */   
/*    */   public PropertyMap getProperties() {
/* 16 */     return this.properties;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\response\HasJoinedMinecraftServerResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */