/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class PlayerPickupItemEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Item item;
/* 14 */   private boolean cancel = false;
/*    */   private final int remaining;
/*    */   
/*    */   public PlayerPickupItemEvent(Player player, Item item, int remaining) {
/* 18 */     super(player);
/* 19 */     this.item = item;
/* 20 */     this.remaining = remaining;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Item getItem()
/*    */   {
/* 29 */     return this.item;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getRemaining()
/*    */   {
/* 38 */     return this.remaining;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 42 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 46 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 51 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 55 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerPickupItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */