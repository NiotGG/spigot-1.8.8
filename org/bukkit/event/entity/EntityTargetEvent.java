/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ public class EntityTargetEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  11 */   private static final HandlerList handlers = new HandlerList();
/*  12 */   private boolean cancel = false;
/*     */   private Entity target;
/*     */   private final TargetReason reason;
/*     */   
/*     */   public EntityTargetEvent(Entity entity, Entity target, TargetReason reason) {
/*  17 */     super(entity);
/*  18 */     this.target = target;
/*  19 */     this.reason = reason;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  23 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  27 */     this.cancel = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TargetReason getReason()
/*     */   {
/*  36 */     return this.reason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getTarget()
/*     */   {
/*  48 */     return this.target;
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
/*     */   public void setTarget(Entity target)
/*     */   {
/*  64 */     this.target = target;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/*  69 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/*  73 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum TargetReason
/*     */   {
/*  81 */     TARGET_DIED, 
/*     */     
/*     */ 
/*     */ 
/*  85 */     CLOSEST_PLAYER, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  90 */     TARGET_ATTACKED_ENTITY, 
/*     */     
/*     */ 
/*     */ 
/*  94 */     PIG_ZOMBIE_TARGET, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  99 */     FORGOT_TARGET, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */     TARGET_ATTACKED_OWNER, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 111 */     OWNER_ATTACKED_TARGET, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 116 */     RANDOM_TARGET, 
/*     */     
/*     */ 
/*     */ 
/* 120 */     DEFEND_VILLAGE, 
/*     */     
/*     */ 
/*     */ 
/* 124 */     TARGET_ATTACKED_NEARBY_ENTITY, 
/*     */     
/*     */ 
/*     */ 
/* 128 */     REINFORCEMENT_TARGET, 
/*     */     
/*     */ 
/*     */ 
/* 132 */     COLLISION, 
/*     */     
/*     */ 
/*     */ 
/* 136 */     CUSTOM, 
/*     */     
/*     */ 
/*     */ 
/* 140 */     CLOSEST_ENTITY, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 145 */     UNKNOWN;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityTargetEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */