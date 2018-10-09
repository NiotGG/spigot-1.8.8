/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PlayerChangedWorldEvent
/*    */   extends PlayerEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final World from;
/*    */   
/*    */   public PlayerChangedWorldEvent(Player player, World from) {
/* 15 */     super(player);
/* 16 */     this.from = from;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public World getFrom()
/*    */   {
/* 25 */     return this.from;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 30 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 34 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerChangedWorldEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */