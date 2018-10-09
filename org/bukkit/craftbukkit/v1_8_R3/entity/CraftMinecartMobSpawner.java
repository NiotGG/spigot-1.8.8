/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ final class CraftMinecartMobSpawner extends CraftMinecart implements org.bukkit.entity.minecart.SpawnerMinecart
/*    */ {
/*    */   CraftMinecartMobSpawner(CraftServer server, EntityMinecartMobSpawner entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 16 */     return "CraftMinecartMobSpawner";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 20 */     return EntityType.MINECART_MOB_SPAWNER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */