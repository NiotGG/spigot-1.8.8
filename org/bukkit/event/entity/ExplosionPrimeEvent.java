/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Explosive;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ExplosionPrimeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   private float radius;
/*    */   private boolean fire;
/*    */   
/*    */   public ExplosionPrimeEvent(Entity what, float radius, boolean fire) {
/* 18 */     super(what);
/* 19 */     this.cancel = false;
/* 20 */     this.radius = radius;
/* 21 */     this.fire = fire;
/*    */   }
/*    */   
/*    */   public ExplosionPrimeEvent(Explosive explosive) {
/* 25 */     this(explosive, explosive.getYield(), explosive.isIncendiary());
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 29 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 33 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getRadius()
/*    */   {
/* 42 */     return this.radius;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setRadius(float radius)
/*    */   {
/* 51 */     this.radius = radius;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getFire()
/*    */   {
/* 60 */     return this.fire;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setFire(boolean fire)
/*    */   {
/* 69 */     this.fire = fire;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 74 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 78 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ExplosionPrimeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */