/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Arrow.Spigot;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ 
/*    */ public class CraftArrow extends AbstractProjectile implements org.bukkit.entity.Arrow
/*    */ {
/*    */   public CraftArrow(CraftServer server, EntityArrow entity)
/*    */   {
/* 15 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public void setKnockbackStrength(int knockbackStrength) {
/* 19 */     Validate.isTrue(knockbackStrength >= 0, "Knockback cannot be negative");
/* 20 */     getHandle().setKnockbackStrength(knockbackStrength);
/*    */   }
/*    */   
/*    */   public int getKnockbackStrength() {
/* 24 */     return getHandle().knockbackStrength;
/*    */   }
/*    */   
/*    */   public boolean isCritical() {
/* 28 */     return getHandle().isCritical();
/*    */   }
/*    */   
/*    */   public void setCritical(boolean critical) {
/* 32 */     getHandle().setCritical(critical);
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/* 36 */     return getHandle().projectileSource;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/* 40 */     if ((shooter instanceof LivingEntity)) {
/* 41 */       getHandle().shooter = ((CraftLivingEntity)shooter).getHandle();
/*    */     } else {
/* 43 */       getHandle().shooter = null;
/*    */     }
/* 45 */     getHandle().projectileSource = shooter;
/*    */   }
/*    */   
/*    */   public EntityArrow getHandle()
/*    */   {
/* 50 */     return (EntityArrow)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     return "CraftArrow";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 59 */     return EntityType.ARROW;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 76 */   private final Arrow.Spigot spigot = new Arrow.Spigot()
/*    */   {
/*    */ 
/*    */     public double getDamage()
/*    */     {
/* 81 */       return CraftArrow.this.getHandle().j();
/*    */     }
/*    */     
/*    */ 
/*    */     public void setDamage(double damage)
/*    */     {
/* 87 */       CraftArrow.this.getHandle().b(damage);
/*    */     }
/*    */   };
/*    */   
/*    */   public Arrow.Spigot spigot()
/*    */   {
/* 93 */     return this.spigot;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */