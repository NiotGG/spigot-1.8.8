/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class BlockRedstoneEvent
/*    */   extends BlockEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final int oldCurrent;
/*    */   private int newCurrent;
/*    */   
/*    */   public BlockRedstoneEvent(Block block, int oldCurrent, int newCurrent) {
/* 15 */     super(block);
/* 16 */     this.oldCurrent = oldCurrent;
/* 17 */     this.newCurrent = newCurrent;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getOldCurrent()
/*    */   {
/* 26 */     return this.oldCurrent;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getNewCurrent()
/*    */   {
/* 35 */     return this.newCurrent;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setNewCurrent(int newCurrent)
/*    */   {
/* 44 */     this.newCurrent = newCurrent;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 49 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 53 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockRedstoneEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */