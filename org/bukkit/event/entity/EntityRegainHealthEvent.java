/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ public class EntityRegainHealthEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  12 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancelled;
/*     */   private double amount;
/*     */   private final RegainReason regainReason;
/*     */   
/*     */   @Deprecated
/*     */   public EntityRegainHealthEvent(Entity entity, int amount, RegainReason regainReason) {
/*  19 */     this(entity, amount, regainReason);
/*     */   }
/*     */   
/*     */   public EntityRegainHealthEvent(Entity entity, double amount, RegainReason regainReason) {
/*  23 */     super(entity);
/*  24 */     this.amount = amount;
/*  25 */     this.regainReason = regainReason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getAmount()
/*     */   {
/*  34 */     return this.amount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAmount(double amount)
/*     */   {
/*  55 */     this.amount = amount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCancelled()
/*     */   {
/*  72 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel)
/*     */   {
/*  77 */     this.cancelled = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegainReason getRegainReason()
/*     */   {
/*  87 */     return this.regainReason;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/*  92 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/*  96 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum RegainReason
/*     */   {
/* 104 */     REGEN, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 109 */     SATIATED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 114 */     EATING, 
/*     */     
/*     */ 
/*     */ 
/* 118 */     ENDER_CRYSTAL, 
/*     */     
/*     */ 
/*     */ 
/* 122 */     MAGIC, 
/*     */     
/*     */ 
/*     */ 
/* 126 */     MAGIC_REGEN, 
/*     */     
/*     */ 
/*     */ 
/* 130 */     WITHER_SPAWN, 
/*     */     
/*     */ 
/*     */ 
/* 134 */     WITHER, 
/*     */     
/*     */ 
/*     */ 
/* 138 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityRegainHealthEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */