/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public abstract class AbstractProjectile extends CraftEntity implements org.bukkit.entity.Projectile
/*    */ {
/*    */   private boolean doesBounce;
/*    */   
/*    */   public AbstractProjectile(CraftServer server, net.minecraft.server.v1_8_R3.Entity entity)
/*    */   {
/* 11 */     super(server, entity);
/* 12 */     this.doesBounce = false;
/*    */   }
/*    */   
/*    */   public boolean doesBounce() {
/* 16 */     return this.doesBounce;
/*    */   }
/*    */   
/*    */   public void setBounce(boolean doesBounce) {
/* 20 */     this.doesBounce = doesBounce;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\AbstractProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */