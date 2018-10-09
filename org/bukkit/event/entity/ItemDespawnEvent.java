/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemDespawnEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   private final Location location;
/*    */   
/*    */   public ItemDespawnEvent(Item despawnee, Location loc) {
/* 21 */     super(despawnee);
/* 22 */     this.location = loc;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 26 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 30 */     this.canceled = cancel;
/*    */   }
/*    */   
/*    */   public Item getEntity()
/*    */   {
/* 35 */     return (Item)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Location getLocation()
/*    */   {
/* 44 */     return this.location;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 49 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 53 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ItemDespawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */