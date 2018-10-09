/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityPigZombie;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.PigZombie;
/*    */ 
/*    */ public class CraftPigZombie extends CraftZombie implements PigZombie
/*    */ {
/*    */   public CraftPigZombie(CraftServer server, EntityPigZombie entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public int getAnger() {
/* 16 */     return getHandle().angerLevel;
/*    */   }
/*    */   
/*    */   public void setAnger(int level) {
/* 20 */     getHandle().angerLevel = level;
/*    */   }
/*    */   
/*    */   public void setAngry(boolean angry) {
/* 24 */     setAnger(angry ? 400 : 0);
/*    */   }
/*    */   
/*    */   public boolean isAngry() {
/* 28 */     return getAnger() > 0;
/*    */   }
/*    */   
/*    */   public EntityPigZombie getHandle()
/*    */   {
/* 33 */     return (EntityPigZombie)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 38 */     return "CraftPigZombie";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 42 */     return EntityType.PIG_ZOMBIE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftPigZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */