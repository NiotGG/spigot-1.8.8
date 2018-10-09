/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerTeleportEvent
/*    */   extends PlayerMoveEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/* 12 */   private TeleportCause cause = TeleportCause.UNKNOWN;
/*    */   
/*    */   public PlayerTeleportEvent(Player player, Location from, Location to) {
/* 15 */     super(player, from, to);
/*    */   }
/*    */   
/*    */   public PlayerTeleportEvent(Player player, Location from, Location to, TeleportCause cause) {
/* 19 */     this(player, from, to);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TeleportCause getCause()
/*    */   {
/* 30 */     return this.cause;
/*    */   }
/*    */   
/*    */   public static enum TeleportCause {
/* 34 */     ENDER_PEARL, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 39 */     COMMAND, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 44 */     PLUGIN, 
/*    */     
/*    */ 
/*    */ 
/* 48 */     NETHER_PORTAL, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 53 */     END_PORTAL, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 58 */     SPECTATE, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 63 */     UNKNOWN;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 72 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 76 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerTeleportEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */