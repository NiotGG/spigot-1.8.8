/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockBloodStone extends Block
/*    */ {
/*    */   public BlockBloodStone() {
/*  8 */     super(Material.STONE);
/*  9 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData iblockdata) {
/* 13 */     return MaterialMapColor.K;
/*    */   }
/*    */   
/*    */ 
/*    */   public void doPhysics(World world, BlockPosition position, IBlockData iblockdata, Block block)
/*    */   {
/* 19 */     if ((block != null) && (block.isPowerSource())) {
/* 20 */       org.bukkit.block.Block bl = world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ());
/* 21 */       int power = bl.getBlockPower();
/*    */       
/* 23 */       BlockRedstoneEvent event = new BlockRedstoneEvent(bl, power, power);
/* 24 */       world.getServer().getPluginManager().callEvent(event);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBloodStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */