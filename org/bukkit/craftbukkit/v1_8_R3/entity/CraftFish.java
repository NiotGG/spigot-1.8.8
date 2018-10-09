/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.EntityFishingHook;
/*    */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*    */ import net.minecraft.server.v1_8_R3.MathHelper;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Fish;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ 
/*    */ public class CraftFish extends AbstractProjectile implements Fish
/*    */ {
/* 16 */   private double biteChance = -1.0D;
/*    */   
/*    */   public CraftFish(CraftServer server, EntityFishingHook entity) {
/* 19 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/* 23 */     if (getHandle().owner != null) {
/* 24 */       return getHandle().owner.getBukkitEntity();
/*    */     }
/*    */     
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/* 31 */     if ((shooter instanceof CraftHumanEntity)) {
/* 32 */       getHandle().owner = ((EntityHuman)((CraftHumanEntity)shooter).entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public EntityFishingHook getHandle()
/*    */   {
/* 38 */     return (EntityFishingHook)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "CraftFish";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 47 */     return EntityType.FISHING_HOOK;
/*    */   }
/*    */   
/*    */   public double getBiteChance() {
/* 51 */     EntityFishingHook hook = getHandle();
/*    */     
/* 53 */     if (this.biteChance == -1.0D) {
/* 54 */       if (hook.world.isRainingAt(new BlockPosition(MathHelper.floor(hook.locX), MathHelper.floor(hook.locY) + 1, MathHelper.floor(hook.locZ)))) {
/* 55 */         return 0.0033333333333333335D;
/*    */       }
/* 57 */       return 0.002D;
/*    */     }
/* 59 */     return this.biteChance;
/*    */   }
/*    */   
/*    */   public void setBiteChance(double chance) {
/* 63 */     Validate.isTrue((chance >= 0.0D) && (chance <= 1.0D), "The bite chance must be between 0 and 1.");
/* 64 */     this.biteChance = chance;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftFish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */