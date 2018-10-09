/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityWeather;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftWeather extends CraftEntity implements org.bukkit.entity.Weather
/*    */ {
/*    */   public CraftWeather(CraftServer server, EntityWeather entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityWeather getHandle()
/*    */   {
/* 15 */     return (EntityWeather)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftWeather";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.WEATHER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftWeather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */