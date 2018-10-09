/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntityPortalEnterEvent
/*    */   extends EntityEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Location location;
/*    */   
/*    */   public EntityPortalEnterEvent(Entity entity, Location location) {
/* 15 */     super(entity);
/* 16 */     this.location = location;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Location getLocation()
/*    */   {
/* 25 */     return this.location;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 30 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 34 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityPortalEnterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */