/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ 
/*    */ public class MinecraftProfilePropertiesResponse extends Response
/*    */ {
/*    */   private java.util.UUID id;
/*    */   private String name;
/*    */   private PropertyMap properties;
/*    */   
/*    */   public java.util.UUID getId()
/*    */   {
/* 13 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */   
/*    */   public PropertyMap getProperties() {
/* 21 */     return this.properties;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\response\MinecraftProfilePropertiesResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */