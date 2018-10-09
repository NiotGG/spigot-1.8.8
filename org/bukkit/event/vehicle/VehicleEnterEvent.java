/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class VehicleEnterEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final Entity entered;
/*    */   
/*    */   public VehicleEnterEvent(Vehicle vehicle, Entity entered) {
/* 17 */     super(vehicle);
/* 18 */     this.entered = entered;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntered()
/*    */   {
/* 27 */     return this.entered;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 31 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 35 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleEnterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */