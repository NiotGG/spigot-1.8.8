/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Fish;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ public class PlayerFishEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  13 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final Entity entity;
/*  15 */   private boolean cancel = false;
/*     */   
/*     */ 
/*     */   private int exp;
/*     */   
/*     */ 
/*     */   private final State state;
/*     */   
/*     */   private final Fish hookEntity;
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public PlayerFishEvent(Player player, Entity entity, State state)
/*     */   {
/*  29 */     this(player, entity, null, state);
/*     */   }
/*     */   
/*     */   public PlayerFishEvent(Player player, Entity entity, Fish hookEntity, State state) {
/*  33 */     super(player);
/*  34 */     this.entity = entity;
/*  35 */     this.hookEntity = hookEntity;
/*  36 */     this.state = state;
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
/*     */   public Entity getCaught()
/*     */   {
/*  49 */     return this.entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Fish getHook()
/*     */   {
/*  58 */     return this.hookEntity;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  62 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  66 */     this.cancel = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getExpToDrop()
/*     */   {
/*  78 */     return this.exp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExpToDrop(int amount)
/*     */   {
/*  90 */     this.exp = amount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public State getState()
/*     */   {
/*  99 */     return this.state;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/* 104 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 108 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum State
/*     */   {
/* 116 */     FISHING, 
/*     */     
/*     */ 
/*     */ 
/* 120 */     CAUGHT_FISH, 
/*     */     
/*     */ 
/*     */ 
/* 124 */     CAUGHT_ENTITY, 
/*     */     
/*     */ 
/*     */ 
/* 128 */     IN_GROUND, 
/*     */     
/*     */ 
/*     */ 
/* 132 */     FAILED_ATTEMPT;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerFishEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */