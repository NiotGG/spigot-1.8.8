/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ public class PlayerMoveEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  13 */   private static final HandlerList handlers = new HandlerList();
/*  14 */   private boolean cancel = false;
/*     */   private Location from;
/*     */   private Location to;
/*     */   
/*     */   public PlayerMoveEvent(Player player, Location from, Location to) {
/*  19 */     super(player);
/*  20 */     this.from = from;
/*  21 */     this.to = to;
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
/*     */   public boolean isCancelled()
/*     */   {
/*  35 */     return this.cancel;
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
/*     */   public void setCancelled(boolean cancel)
/*     */   {
/*  49 */     this.cancel = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getFrom()
/*     */   {
/*  58 */     return this.from;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFrom(Location from)
/*     */   {
/*  67 */     validateLocation(from);
/*  68 */     this.from = from;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location getTo()
/*     */   {
/*  77 */     return this.to;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTo(Location to)
/*     */   {
/*  86 */     validateLocation(to);
/*  87 */     this.to = to;
/*     */   }
/*     */   
/*     */   private void validateLocation(Location loc) {
/*  91 */     Preconditions.checkArgument(loc != null, "Cannot use null location!");
/*  92 */     Preconditions.checkArgument(loc.getWorld() != null, "Cannot use null location with null world!");
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/*  97 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 101 */     return handlers;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerMoveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */