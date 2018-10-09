/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.TNTPrimed;
/*    */ 
/*    */ public class CraftTNTPrimed extends CraftEntity implements TNTPrimed
/*    */ {
/*    */   public CraftTNTPrimed(CraftServer server, EntityTNTPrimed entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public float getYield() {
/* 18 */     return getHandle().yield;
/*    */   }
/*    */   
/*    */   public boolean isIncendiary() {
/* 22 */     return getHandle().isIncendiary;
/*    */   }
/*    */   
/*    */   public void setIsIncendiary(boolean isIncendiary) {
/* 26 */     getHandle().isIncendiary = isIncendiary;
/*    */   }
/*    */   
/*    */   public void setYield(float yield) {
/* 30 */     getHandle().yield = yield;
/*    */   }
/*    */   
/*    */   public int getFuseTicks() {
/* 34 */     return getHandle().fuseTicks;
/*    */   }
/*    */   
/*    */   public void setFuseTicks(int fuseTicks) {
/* 38 */     getHandle().fuseTicks = fuseTicks;
/*    */   }
/*    */   
/*    */   public EntityTNTPrimed getHandle()
/*    */   {
/* 43 */     return (EntityTNTPrimed)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 48 */     return "CraftTNTPrimed";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 52 */     return EntityType.PRIMED_TNT;
/*    */   }
/*    */   
/*    */   public Entity getSource() {
/* 56 */     EntityLiving source = getHandle().getSource();
/*    */     
/* 58 */     if (source != null) {
/* 59 */       Entity bukkitEntity = source.getBukkitEntity();
/*    */       
/* 61 */       if (bukkitEntity.isValid()) {
/* 62 */         return bukkitEntity;
/*    */       }
/*    */     }
/*    */     
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftTNTPrimed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */