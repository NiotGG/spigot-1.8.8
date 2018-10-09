/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityZombie;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Zombie;
/*    */ 
/*    */ public class CraftZombie extends CraftMonster implements Zombie
/*    */ {
/*    */   public CraftZombie(CraftServer server, EntityZombie entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityZombie getHandle()
/*    */   {
/* 17 */     return (EntityZombie)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftZombie";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.ZOMBIE;
/*    */   }
/*    */   
/*    */   public boolean isBaby() {
/* 30 */     return getHandle().isBaby();
/*    */   }
/*    */   
/*    */   public void setBaby(boolean flag) {
/* 34 */     getHandle().setBaby(flag);
/*    */   }
/*    */   
/*    */   public boolean isVillager() {
/* 38 */     return getHandle().isVillager();
/*    */   }
/*    */   
/*    */   public void setVillager(boolean flag) {
/* 42 */     getHandle().setVillager(flag);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */