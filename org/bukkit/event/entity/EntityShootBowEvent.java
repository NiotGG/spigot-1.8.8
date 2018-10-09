/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class EntityShootBowEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack bow;
/*    */   private Entity projectile;
/*    */   private final float force;
/*    */   private boolean cancelled;
/*    */   
/*    */   public EntityShootBowEvent(LivingEntity shooter, ItemStack bow, Projectile projectile, float force) {
/* 21 */     super(shooter);
/* 22 */     this.bow = bow;
/* 23 */     this.projectile = projectile;
/* 24 */     this.force = force;
/*    */   }
/*    */   
/*    */   public LivingEntity getEntity()
/*    */   {
/* 29 */     return (LivingEntity)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack getBow()
/*    */   {
/* 38 */     return this.bow;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getProjectile()
/*    */   {
/* 47 */     return this.projectile;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setProjectile(Entity projectile)
/*    */   {
/* 56 */     this.projectile = projectile;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getForce()
/*    */   {
/* 65 */     return this.force;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 69 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 73 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 78 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 82 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityShootBowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */