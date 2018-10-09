/*     */ package org.bukkit.event.block;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIgniteEvent
/*     */   extends BlockEvent
/*     */   implements Cancellable
/*     */ {
/*  16 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final IgniteCause cause;
/*     */   private final Entity ignitingEntity;
/*     */   private final Block ignitingBlock;
/*     */   private boolean cancel;
/*     */   
/*     */   @Deprecated
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Player thePlayer) {
/*  24 */     this(theBlock, cause, thePlayer);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Entity ignitingEntity) {
/*  28 */     this(theBlock, cause, ignitingEntity, null);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Block ignitingBlock) {
/*  32 */     this(theBlock, cause, null, ignitingBlock);
/*     */   }
/*     */   
/*     */   public BlockIgniteEvent(Block theBlock, IgniteCause cause, Entity ignitingEntity, Block ignitingBlock) {
/*  36 */     super(theBlock);
/*  37 */     this.cause = cause;
/*  38 */     this.ignitingEntity = ignitingEntity;
/*  39 */     this.ignitingBlock = ignitingBlock;
/*  40 */     this.cancel = false;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  44 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  48 */     this.cancel = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IgniteCause getCause()
/*     */   {
/*  57 */     return this.cause;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Player getPlayer()
/*     */   {
/*  66 */     if ((this.ignitingEntity instanceof Player)) {
/*  67 */       return (Player)this.ignitingEntity;
/*     */     }
/*     */     
/*  70 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Entity getIgnitingEntity()
/*     */   {
/*  79 */     return this.ignitingEntity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Block getIgnitingBlock()
/*     */   {
/*  88 */     return this.ignitingBlock;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum IgniteCause
/*     */   {
/*  96 */     LAVA, 
/*     */     
/*     */ 
/*     */ 
/* 100 */     FLINT_AND_STEEL, 
/*     */     
/*     */ 
/*     */ 
/* 104 */     SPREAD, 
/*     */     
/*     */ 
/*     */ 
/* 108 */     LIGHTNING, 
/*     */     
/*     */ 
/*     */ 
/* 112 */     FIREBALL, 
/*     */     
/*     */ 
/*     */ 
/* 116 */     ENDER_CRYSTAL, 
/*     */     
/*     */ 
/*     */ 
/* 120 */     EXPLOSION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HandlerList getHandlers()
/*     */   {
/* 128 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 132 */     return handlers;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockIgniteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */