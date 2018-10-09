/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPistonExtendEvent
/*    */   extends BlockPistonEvent
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final int length;
/*    */   private List<Block> blocks;
/*    */   
/*    */   @Deprecated
/*    */   public BlockPistonExtendEvent(Block block, int length, BlockFace direction) {
/* 21 */     super(block, direction);
/*    */     
/* 23 */     this.length = length;
/*    */   }
/*    */   
/*    */   public BlockPistonExtendEvent(Block block, List<Block> blocks, BlockFace direction) {
/* 27 */     super(block, direction);
/*    */     
/* 29 */     this.length = blocks.size();
/* 30 */     this.blocks = blocks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public int getLength()
/*    */   {
/* 42 */     return this.length;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<Block> getBlocks()
/*    */   {
/* 52 */     if (this.blocks == null) {
/* 53 */       ArrayList<Block> tmp = new ArrayList();
/* 54 */       for (int i = 0; i < getLength(); i++) {
/* 55 */         tmp.add(this.block.getRelative(getDirection(), i + 1));
/*    */       }
/* 57 */       this.blocks = Collections.unmodifiableList(tmp);
/*    */     }
/* 59 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 64 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 68 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPistonExtendEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */