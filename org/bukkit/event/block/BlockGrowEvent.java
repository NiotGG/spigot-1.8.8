/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGrowEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 23 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final BlockState newState;
/* 25 */   private boolean cancelled = false;
/*    */   
/*    */   public BlockGrowEvent(Block block, BlockState newState) {
/* 28 */     super(block);
/* 29 */     this.newState = newState;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockState getNewState()
/*    */   {
/* 38 */     return this.newState;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 42 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 46 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers() {
/* 50 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 54 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockGrowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */