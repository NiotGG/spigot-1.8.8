/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Creeper;
/*    */ import org.bukkit.entity.LightningStrike;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreeperPowerEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean canceled;
/*    */   private final PowerCause cause;
/*    */   private LightningStrike bolt;
/*    */   
/*    */   public CreeperPowerEvent(Creeper creeper, LightningStrike bolt, PowerCause cause) {
/* 20 */     this(creeper, cause);
/* 21 */     this.bolt = bolt;
/*    */   }
/*    */   
/*    */   public CreeperPowerEvent(Creeper creeper, PowerCause cause) {
/* 25 */     super(creeper);
/* 26 */     this.cause = cause;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.canceled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.canceled = cancel;
/*    */   }
/*    */   
/*    */   public Creeper getEntity()
/*    */   {
/* 39 */     return (Creeper)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LightningStrike getLightning()
/*    */   {
/* 48 */     return this.bolt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PowerCause getCause()
/*    */   {
/* 57 */     return this.cause;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 62 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 66 */     return handlers;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum PowerCause
/*    */   {
/* 74 */     LIGHTNING, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 80 */     SET_ON, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 86 */     SET_OFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\CreeperPowerEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */