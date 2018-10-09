/*    */ package org.bukkit.event.vehicle;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Vehicle;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class VehicleDamageEvent
/*    */   extends VehicleEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Entity attacker;
/*    */   private double damage;
/*    */   private boolean cancelled;
/*    */   
/*    */   @Deprecated
/*    */   public VehicleDamageEvent(Vehicle vehicle, Entity attacker, int damage) {
/* 20 */     this(vehicle, attacker, damage);
/*    */   }
/*    */   
/*    */   public VehicleDamageEvent(Vehicle vehicle, Entity attacker, double damage) {
/* 24 */     super(vehicle);
/* 25 */     this.attacker = attacker;
/* 26 */     this.damage = damage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getAttacker()
/*    */   {
/* 35 */     return this.attacker;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDamage()
/*    */   {
/* 44 */     return this.damage;
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
/*    */ 
/*    */ 
/*    */   public void setDamage(double damage)
/*    */   {
/* 65 */     this.damage = damage;
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
/*    */   public boolean isCancelled()
/*    */   {
/* 81 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 85 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 90 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 94 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\vehicle\VehicleDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */