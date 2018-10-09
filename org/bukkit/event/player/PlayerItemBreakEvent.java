/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerItemBreakEvent
/*    */   extends PlayerEvent
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack brokenItem;
/*    */   
/*    */   public PlayerItemBreakEvent(Player player, ItemStack brokenItem) {
/* 18 */     super(player);
/* 19 */     this.brokenItem = brokenItem;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack getBrokenItem()
/*    */   {
/* 28 */     return this.brokenItem;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 33 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 37 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerItemBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */