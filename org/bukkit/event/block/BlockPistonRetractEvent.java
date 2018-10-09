/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class BlockPistonRetractEvent
/*    */   extends BlockPistonEvent
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private List<Block> blocks;
/*    */   
/*    */   public BlockPistonRetractEvent(Block block, List<Block> blocks, BlockFace direction) {
/* 17 */     super(block, direction);
/*    */     
/* 19 */     this.blocks = blocks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Location getRetractLocation()
/*    */   {
/* 30 */     return getBlock().getRelative(getDirection(), 2).getLocation();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<Block> getBlocks()
/*    */   {
/* 40 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 45 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 49 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPistonRetractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */