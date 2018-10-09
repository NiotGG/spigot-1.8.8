/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerToggleSneakEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final boolean isSneaking;
/* 13 */   private boolean cancel = false;
/*    */   
/*    */   public PlayerToggleSneakEvent(Player player, boolean isSneaking) {
/* 16 */     super(player);
/* 17 */     this.isSneaking = isSneaking;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isSneaking()
/*    */   {
/* 26 */     return this.isSneaking;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 34 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 39 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 43 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerToggleSneakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */