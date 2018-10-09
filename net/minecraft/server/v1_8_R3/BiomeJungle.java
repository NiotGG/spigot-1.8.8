/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeJungle
/*    */   extends BiomeBase
/*    */ {
/*    */   private boolean aD;
/* 20 */   private static final IBlockData aE = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/* 21 */   private static final IBlockData aF = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/* 22 */   private static final IBlockData aG = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*    */   
/*    */   public BiomeJungle(int paramInt, boolean paramBoolean) {
/* 25 */     super(paramInt);
/* 26 */     this.aD = paramBoolean;
/* 27 */     if (paramBoolean) {
/* 28 */       this.as.A = 2;
/*    */     } else {
/* 30 */       this.as.A = 50;
/*    */     }
/* 32 */     this.as.C = 25;
/* 33 */     this.as.B = 4;
/*    */     
/* 35 */     if (!paramBoolean) {
/* 36 */       this.at.add(new BiomeBase.BiomeMeta(EntityOcelot.class, 2, 1, 1));
/*    */     }
/*    */     
/*    */ 
/* 40 */     this.au.add(new BiomeBase.BiomeMeta(EntityChicken.class, 10, 4, 4));
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 45 */     if (paramRandom.nextInt(10) == 0) {
/* 46 */       return this.aB;
/*    */     }
/* 48 */     if (paramRandom.nextInt(2) == 0) {
/* 49 */       return new WorldGenGroundBush(aE, aG);
/*    */     }
/* 51 */     if ((!this.aD) && (paramRandom.nextInt(3) == 0)) {
/* 52 */       return new WorldGenJungleTree(false, 10, 20, aE, aF);
/*    */     }
/* 54 */     return new WorldGenTrees(false, 4 + paramRandom.nextInt(7), aE, aF, true);
/*    */   }
/*    */   
/*    */   public WorldGenerator b(Random paramRandom)
/*    */   {
/* 59 */     if (paramRandom.nextInt(4) == 0) {
/* 60 */       return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.FERN);
/*    */     }
/* 62 */     return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 67 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */     
/*    */ 
/* 70 */     int i = paramRandom.nextInt(16) + 8;
/* 71 */     int j = paramRandom.nextInt(16) + 8;
/* 72 */     int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(i, 0, j)).getY() * 2);
/* 73 */     new WorldGenMelon().generate(paramWorld, paramRandom, paramBlockPosition.a(i, k, j));
/*    */     
/*    */ 
/* 76 */     WorldGenVines localWorldGenVines = new WorldGenVines();
/*    */     
/* 78 */     for (j = 0; j < 50; j++) {
/* 79 */       k = paramRandom.nextInt(16) + 8;
/* 80 */       int m = 128;
/* 81 */       int n = paramRandom.nextInt(16) + 8;
/*    */       
/* 83 */       localWorldGenVines.generate(paramWorld, paramRandom, paramBlockPosition.a(k, 128, n));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeJungle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */