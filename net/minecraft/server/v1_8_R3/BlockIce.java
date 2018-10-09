/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockIce extends BlockHalfTransparent
/*    */ {
/*    */   public BlockIce() {
/*  8 */     super(Material.ICE, false);
/*  9 */     this.frictionFactor = 0.98F;
/* 10 */     a(true);
/* 11 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity) {
/* 15 */     entityhuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/* 16 */     entityhuman.applyExhaustion(0.025F);
/* 17 */     if ((I()) && (EnchantmentManager.hasSilkTouchEnchantment(entityhuman))) {
/* 18 */       ItemStack itemstack = i(iblockdata);
/*    */       
/* 20 */       if (itemstack != null) {
/* 21 */         a(world, blockposition, itemstack);
/*    */       }
/*    */     } else {
/* 24 */       if (world.worldProvider.n()) {
/* 25 */         world.setAir(blockposition);
/* 26 */         return;
/*    */       }
/*    */       
/* 29 */       int i = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
/*    */       
/* 31 */       b(world, blockposition, iblockdata, i);
/* 32 */       Material material = world.getType(blockposition.down()).getBlock().getMaterial();
/*    */       
/* 34 */       if ((material.isSolid()) || (material.isLiquid())) {
/* 35 */         world.setTypeUpdate(blockposition, Blocks.FLOWING_WATER.getBlockData());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int a(Random random)
/*    */   {
/* 42 */     return 0;
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 46 */     if (world.b(EnumSkyBlock.BLOCK, blockposition) > 11 - p())
/*    */     {
/* 48 */       if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), world.worldProvider.n() ? Blocks.AIR : Blocks.WATER).isCancelled()) {
/* 49 */         return;
/*    */       }
/*    */       
/*    */ 
/* 53 */       if (world.worldProvider.n()) {
/* 54 */         world.setAir(blockposition);
/*    */       } else {
/* 56 */         b(world, blockposition, world.getType(blockposition), 0);
/* 57 */         world.setTypeUpdate(blockposition, Blocks.WATER.getBlockData());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int k() {
/* 63 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockIce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */