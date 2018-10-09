/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntitySpawnEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   
/*    */   public EntitySpawnEvent(Entity spawnee) {
/* 17 */     super(spawnee);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 21 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 25 */     this.canceled = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Location getLocation()
/*    */   {
/* 34 */     return getEntity().getLocation();
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 39 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 43 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntitySpawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */