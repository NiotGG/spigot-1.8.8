/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class BlockCrops extends BlockPlant implements IBlockFragilePlantElement
/*     */ {
/*   9 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 7);
/*     */   
/*     */   protected BlockCrops() {
/*  12 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
/*  13 */     a(true);
/*  14 */     float f = 0.5F;
/*     */     
/*  16 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  17 */     a(null);
/*  18 */     c(0.0F);
/*  19 */     a(h);
/*  20 */     K();
/*     */   }
/*     */   
/*     */   protected boolean c(Block block) {
/*  24 */     return block == Blocks.FARMLAND;
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  28 */     super.b(world, blockposition, iblockdata, random);
/*  29 */     if (world.getLightLevel(blockposition.up()) >= 9) {
/*  30 */       int i = ((Integer)iblockdata.get(AGE)).intValue();
/*     */       
/*  32 */       if (i < 7) {
/*  33 */         float f = a(this, world, blockposition);
/*     */         
/*  35 */         if (random.nextInt((int)(world.growthOdds / world.spigotConfig.wheatModifier * (25.0F / f)) + 1) == 0) {
/*  36 */           IBlockData data = iblockdata.set(AGE, Integer.valueOf(i + 1));
/*  37 */           CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(data));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void g(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  46 */     int i = ((Integer)iblockdata.get(AGE)).intValue() + MathHelper.nextInt(world.random, 2, 5);
/*     */     
/*  48 */     if (i > 7) {
/*  49 */       i = 7;
/*     */     }
/*     */     
/*     */ 
/*  53 */     IBlockData data = iblockdata.set(AGE, Integer.valueOf(i));
/*  54 */     CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(data));
/*     */   }
/*     */   
/*     */   protected static float a(Block block, World world, BlockPosition blockposition)
/*     */   {
/*  59 */     float f = 1.0F;
/*  60 */     BlockPosition blockposition1 = blockposition.down();
/*     */     
/*  62 */     for (int i = -1; i <= 1; i++) {
/*  63 */       for (int j = -1; j <= 1; j++) {
/*  64 */         float f1 = 0.0F;
/*  65 */         IBlockData iblockdata = world.getType(blockposition1.a(i, 0, j));
/*     */         
/*  67 */         if (iblockdata.getBlock() == Blocks.FARMLAND) {
/*  68 */           f1 = 1.0F;
/*  69 */           if (((Integer)iblockdata.get(BlockSoil.MOISTURE)).intValue() > 0) {
/*  70 */             f1 = 3.0F;
/*     */           }
/*     */         }
/*     */         
/*  74 */         if ((i != 0) || (j != 0)) {
/*  75 */           f1 /= 4.0F;
/*     */         }
/*     */         
/*  78 */         f += f1;
/*     */       }
/*     */     }
/*     */     
/*  82 */     BlockPosition blockposition2 = blockposition.north();
/*  83 */     BlockPosition blockposition3 = blockposition.south();
/*  84 */     BlockPosition blockposition4 = blockposition.west();
/*  85 */     BlockPosition blockposition5 = blockposition.east();
/*  86 */     boolean flag = (block == world.getType(blockposition4).getBlock()) || (block == world.getType(blockposition5).getBlock());
/*  87 */     boolean flag1 = (block == world.getType(blockposition2).getBlock()) || (block == world.getType(blockposition3).getBlock());
/*     */     
/*  89 */     if ((flag) && (flag1)) {
/*  90 */       f /= 2.0F;
/*     */     } else {
/*  92 */       boolean flag2 = (block == world.getType(blockposition4.north()).getBlock()) || (block == world.getType(blockposition5.north()).getBlock()) || (block == world.getType(blockposition5.south()).getBlock()) || (block == world.getType(blockposition4.south()).getBlock());
/*     */       
/*  94 */       if (flag2) {
/*  95 */         f /= 2.0F;
/*     */       }
/*     */     }
/*     */     
/*  99 */     return f;
/*     */   }
/*     */   
/*     */   public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 103 */     return ((world.k(blockposition) >= 8) || (world.i(blockposition))) && (c(world.getType(blockposition.down()).getBlock()));
/*     */   }
/*     */   
/*     */   protected Item l() {
/* 107 */     return Items.WHEAT_SEEDS;
/*     */   }
/*     */   
/*     */   protected Item n() {
/* 111 */     return Items.WHEAT;
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 115 */     super.dropNaturally(world, blockposition, iblockdata, f, 0);
/* 116 */     if (!world.isClientSide) {
/* 117 */       int j = ((Integer)iblockdata.get(AGE)).intValue();
/*     */       
/* 119 */       if (j >= 7) {
/* 120 */         int k = 3 + i;
/*     */         
/* 122 */         for (int l = 0; l < k; l++) {
/* 123 */           if (world.random.nextInt(15) <= j) {
/* 124 */             a(world, blockposition, new ItemStack(l(), 1, 0));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/* 133 */     return ((Integer)iblockdata.get(AGE)).intValue() == 7 ? n() : l();
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
/* 137 */     return ((Integer)iblockdata.get(AGE)).intValue() < 7;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 145 */     g(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 149 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 153 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 157 */     return new BlockStateList(this, new IBlockState[] { AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCrops.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */