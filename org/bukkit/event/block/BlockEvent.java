/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public abstract class BlockEvent
/*    */   extends Event
/*    */ {
/*    */   protected Block block;
/*    */   
/*    */   public BlockEvent(Block theBlock)
/*    */   {
/* 13 */     this.block = theBlock;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final Block getBlock()
/*    */   {
/* 22 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */