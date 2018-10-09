/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class BlockExplodeEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   private final List<Block> blocks;
/*    */   private float yield;
/*    */   
/*    */   public BlockExplodeEvent(Block what, List<Block> blocks, float yield) {
/* 19 */     super(what);
/* 20 */     this.blocks = blocks;
/* 21 */     this.yield = yield;
/* 22 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 26 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 30 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<Block> blockList()
/*    */   {
/* 40 */     return this.blocks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public float getYield()
/*    */   {
/* 49 */     return this.yield;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setYield(float yield)
/*    */   {
/* 58 */     this.yield = yield;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 63 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 67 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockExplodeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */