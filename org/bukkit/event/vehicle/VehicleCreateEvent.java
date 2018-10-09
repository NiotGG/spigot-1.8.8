/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class VehicleCreateEvent
/*    */   extends VehicleEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public VehicleCreateEvent(Vehicle vehicle) {
/* 13 */     super(vehicle);
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 18 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 22 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleCreateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */