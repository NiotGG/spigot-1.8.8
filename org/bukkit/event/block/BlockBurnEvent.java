/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBurnEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   
/*    */   public BlockBurnEvent(Block block) {
/* 18 */     super(block);
/* 19 */     this.cancelled = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 23 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 27 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 32 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 36 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockBurnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */