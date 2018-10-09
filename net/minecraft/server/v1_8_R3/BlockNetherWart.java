/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockNetherWart extends BlockPlant
/*    */ {
/*  7 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 3);
/*    */   
/*    */   protected BlockNetherWart() {
/* 10 */     super(Material.PLANT, MaterialMapColor.D);
/* 11 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
/* 12 */     a(true);
/* 13 */     float f = 0.5F;
/*    */     
/* 15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/* 16 */     a(null);
/*    */   }
/*    */   
/*    */   protected boolean c(Block block) {
/* 20 */     return block == Blocks.SOUL_SAND;
/*    */   }
/*    */   
/*    */   public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 24 */     return c(world.getType(blockposition.down()).getBlock());
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 28 */     int i = ((Integer)iblockdata.get(AGE)).intValue();
/*    */     
/* 30 */     if ((i < 3) && (random.nextInt(Math.max(1, (int)world.growthOdds / world.spigotConfig.wartModifier * 10)) == 0)) {
/* 31 */       iblockdata = iblockdata.set(AGE, Integer.valueOf(i + 1));
/*    */       
/* 33 */       org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(iblockdata));
/*    */     }
/*    */     
/* 36 */     super.b(world, blockposition, iblockdata, random);
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 40 */     if (!world.isClientSide) {
/* 41 */       int j = 1;
/*    */       
/* 43 */       if (((Integer)iblockdata.get(AGE)).intValue() >= 3) {
/* 44 */         j = 2 + world.random.nextInt(3);
/* 45 */         if (i > 0) {
/* 46 */           j += world.random.nextInt(i + 1);
/*    */         }
/*    */       }
/*    */       
/* 50 */       for (int k = 0; k < j; k++) {
/* 51 */         a(world, blockposition, new ItemStack(Items.NETHER_WART));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*    */   {
/* 58 */     return null;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 62 */     return 0;
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int i) {
/* 66 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*    */   }
/*    */   
/*    */   public int toLegacyData(IBlockData iblockdata) {
/* 70 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList() {
/* 74 */     return new BlockStateList(this, new IBlockState[] { AGE });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockNetherWart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */