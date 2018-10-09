/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.Cancellable;
/*    */ 
/*    */ public abstract class BlockPistonEvent
/*    */   extends BlockEvent implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   private final BlockFace direction;
/*    */   
/*    */   public BlockPistonEvent(Block block, BlockFace direction)
/*    */   {
/* 16 */     super(block);
/* 17 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 21 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 25 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isSticky()
/*    */   {
/* 34 */     return (this.block.getType() == Material.PISTON_STICKY_BASE) || (this.block.getType() == Material.PISTON_MOVING_PIECE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockFace getDirection()
/*    */   {
/* 46 */     return this.direction;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPistonEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */