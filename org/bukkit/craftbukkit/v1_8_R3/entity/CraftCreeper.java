/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityCreeper;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Creeper;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.entity.CreeperPowerEvent;
/*    */ 
/*    */ public class CraftCreeper extends CraftMonster implements Creeper
/*    */ {
/*    */   public CraftCreeper(CraftServer server, EntityCreeper entity)
/*    */   {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public boolean isPowered() {
/* 17 */     return getHandle().isPowered();
/*    */   }
/*    */   
/*    */   public void setPowered(boolean powered) {
/* 21 */     CraftServer server = this.server;
/* 22 */     Creeper entity = (Creeper)getHandle().getBukkitEntity();
/*    */     
/* 24 */     if (powered) {
/* 25 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, org.bukkit.event.entity.CreeperPowerEvent.PowerCause.SET_ON);
/* 26 */       server.getPluginManager().callEvent(event);
/*    */       
/* 28 */       if (!event.isCancelled()) {
/* 29 */         getHandle().setPowered(true);
/*    */       }
/*    */     } else {
/* 32 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, org.bukkit.event.entity.CreeperPowerEvent.PowerCause.SET_OFF);
/* 33 */       server.getPluginManager().callEvent(event);
/*    */       
/* 35 */       if (!event.isCancelled()) {
/* 36 */         getHandle().setPowered(false);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public EntityCreeper getHandle()
/*    */   {
/* 43 */     return (EntityCreeper)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 48 */     return "CraftCreeper";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 52 */     return EntityType.CREEPER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftCreeper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */