/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public abstract class VehicleEvent
/*    */   extends Event
/*    */ {
/*    */   protected Vehicle vehicle;
/*    */   
/*    */   public VehicleEvent(Vehicle vehicle)
/*    */   {
/* 13 */     this.vehicle = vehicle;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final Vehicle getVehicle()
/*    */   {
/* 22 */     return this.vehicle;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */