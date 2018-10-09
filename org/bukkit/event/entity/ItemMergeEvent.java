/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ItemMergeEvent extends EntityEvent implements Cancellable
/*    */ {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final Item target;
/*    */   
/*    */   public ItemMergeEvent(Item item, Item target) {
/* 14 */     super(item);
/* 15 */     this.target = target;
/*    */   }
/*    */   
/*    */   public boolean isCancelled()
/*    */   {
/* 20 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled)
/*    */   {
/* 25 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public Item getEntity()
/*    */   {
/* 30 */     return (Item)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Item getTarget()
/*    */   {
/* 39 */     return this.target;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 44 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 48 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ItemMergeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */