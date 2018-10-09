/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.event.block.BlockPhysicsEvent;
/*    */ 
/*    */ public class BlockPlant extends Block
/*    */ {
/*    */   protected BlockPlant()
/*    */   {
/* 12 */     this(Material.PLANT);
/*    */   }
/*    */   
/*    */   protected BlockPlant(Material material) {
/* 16 */     this(material, material.r());
/*    */   }
/*    */   
/*    */   protected BlockPlant(Material material, MaterialMapColor materialmapcolor) {
/* 20 */     super(material, materialmapcolor);
/* 21 */     a(true);
/* 22 */     float f = 0.2F;
/*    */     
/* 24 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
/* 25 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean canPlace(World world, BlockPosition blockposition) {
/* 29 */     return (super.canPlace(world, blockposition)) && (c(world.getType(blockposition.down()).getBlock()));
/*    */   }
/*    */   
/*    */   protected boolean c(Block block) {
/* 33 */     return (block == Blocks.GRASS) || (block == Blocks.DIRT) || (block == Blocks.FARMLAND);
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/* 37 */     super.doPhysics(world, blockposition, iblockdata, block);
/* 38 */     e(world, blockposition, iblockdata);
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 42 */     e(world, blockposition, iblockdata);
/*    */   }
/*    */   
/*    */   protected void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 46 */     if (!f(world, blockposition, iblockdata))
/*    */     {
/* 48 */       org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 49 */       BlockPhysicsEvent event = new BlockPhysicsEvent(block, block.getTypeId());
/* 50 */       world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 52 */       if (event.isCancelled()) {
/* 53 */         return;
/*    */       }
/*    */       
/* 56 */       b(world, blockposition, iblockdata, 0);
/* 57 */       world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata)
/*    */   {
/* 63 */     return c(world.getType(blockposition.down()).getBlock());
/*    */   }
/*    */   
/*    */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 67 */     return null;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */