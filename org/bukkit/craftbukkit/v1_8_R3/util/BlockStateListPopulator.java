/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.IBlockData;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.BlockState;
/*    */ 
/*    */ 
/*    */ public class BlockStateListPopulator
/*    */ {
/*    */   private final World world;
/*    */   private final List<BlockState> list;
/*    */   
/*    */   public BlockStateListPopulator(World world)
/*    */   {
/* 18 */     this(world, new ArrayList());
/*    */   }
/*    */   
/*    */   public BlockStateListPopulator(World world, List<BlockState> list) {
/* 22 */     this.world = world;
/* 23 */     this.list = list;
/*    */   }
/*    */   
/*    */   public void setTypeAndData(int x, int y, int z, net.minecraft.server.v1_8_R3.Block block, int data, int light) {
/* 27 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 28 */     state.setTypeId(net.minecraft.server.v1_8_R3.Block.getId(block));
/* 29 */     state.setRawData((byte)data);
/* 30 */     this.list.add(state);
/*    */   }
/*    */   
/* 33 */   public void setTypeId(int x, int y, int z, int type) { BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 34 */     state.setTypeId(type);
/* 35 */     this.list.add(state);
/*    */   }
/*    */   
/*    */   public void setTypeUpdate(int x, int y, int z, net.minecraft.server.v1_8_R3.Block block) {
/* 39 */     setType(x, y, z, block);
/*    */   }
/*    */   
/*    */   public void setTypeUpdate(BlockPosition position, IBlockData data) {
/* 43 */     setTypeAndData(position.getX(), position.getY(), position.getZ(), data.getBlock(), data.getBlock().toLegacyData(data), 0);
/*    */   }
/*    */   
/*    */   public void setType(int x, int y, int z, net.minecraft.server.v1_8_R3.Block block)
/*    */   {
/* 48 */     BlockState state = this.world.getBlockAt(x, y, z).getState();
/* 49 */     state.setTypeId(net.minecraft.server.v1_8_R3.Block.getId(block));
/* 50 */     this.list.add(state);
/*    */   }
/*    */   
/*    */   public void updateList() {
/* 54 */     for (BlockState state : this.list) {
/* 55 */       state.update(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<BlockState> getList() {
/* 60 */     return this.list;
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 64 */     return this.world;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\BlockStateListPopulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */