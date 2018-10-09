/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMultiPlaceEvent
/*    */   extends BlockPlaceEvent
/*    */ {
/*    */   private final List<BlockState> states;
/*    */   
/*    */   public BlockMultiPlaceEvent(List<BlockState> states, Block clicked, ItemStack itemInHand, Player thePlayer, boolean canBuild)
/*    */   {
/* 22 */     super(((BlockState)states.get(0)).getBlock(), (BlockState)states.get(0), clicked, itemInHand, thePlayer, canBuild);
/* 23 */     this.states = ImmutableList.copyOf(states);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<BlockState> getReplacedBlockStates()
/*    */   {
/* 34 */     return this.states;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockMultiPlaceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */