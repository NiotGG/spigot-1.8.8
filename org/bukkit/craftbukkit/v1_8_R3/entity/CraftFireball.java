/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityFireball;
/*    */ import net.minecraft.server.v1_8_R3.MathHelper;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Fireball;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class CraftFireball extends AbstractProjectile implements Fireball
/*    */ {
/*    */   public CraftFireball(CraftServer server, EntityFireball entity)
/*    */   {
/* 16 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public float getYield() {
/* 20 */     return getHandle().bukkitYield;
/*    */   }
/*    */   
/*    */   public boolean isIncendiary() {
/* 24 */     return getHandle().isIncendiary;
/*    */   }
/*    */   
/*    */   public void setIsIncendiary(boolean isIncendiary) {
/* 28 */     getHandle().isIncendiary = isIncendiary;
/*    */   }
/*    */   
/*    */   public void setYield(float yield) {
/* 32 */     getHandle().bukkitYield = yield;
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/* 36 */     return getHandle().projectileSource;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/* 40 */     if ((shooter instanceof CraftLivingEntity)) {
/* 41 */       getHandle().shooter = ((CraftLivingEntity)shooter).getHandle();
/*    */     } else {
/* 43 */       getHandle().shooter = null;
/*    */     }
/* 45 */     getHandle().projectileSource = shooter;
/*    */   }
/*    */   
/*    */   public Vector getDirection() {
/* 49 */     return new Vector(getHandle().dirX, getHandle().dirY, getHandle().dirZ);
/*    */   }
/*    */   
/*    */   public void setDirection(Vector direction) {
/* 53 */     Validate.notNull(direction, "Direction can not be null");
/* 54 */     double x = direction.getX();
/* 55 */     double y = direction.getY();
/* 56 */     double z = direction.getZ();
/* 57 */     double magnitude = MathHelper.sqrt(x * x + y * y + z * z);
/* 58 */     getHandle().dirX = (x / magnitude);
/* 59 */     getHandle().dirY = (y / magnitude);
/* 60 */     getHandle().dirZ = (z / magnitude);
/*    */   }
/*    */   
/*    */   public EntityFireball getHandle()
/*    */   {
/* 65 */     return (EntityFireball)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 70 */     return "CraftFireball";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 74 */     return EntityType.UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */