/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
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
/*    */ 
/*    */ public class BiomeBigHills
/*    */   extends BiomeBase
/*    */ {
/* 19 */   private WorldGenerator aD = new WorldGenMinable(Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.STONE), 9);
/* 20 */   private WorldGenTaiga2 aE = new WorldGenTaiga2(false);
/*    */   
/* 22 */   private int aF = 0;
/* 23 */   private int aG = 1;
/* 24 */   private int aH = 2;
/*    */   private int aI;
/*    */   
/*    */   protected BiomeBigHills(int paramInt, boolean paramBoolean)
/*    */   {
/* 29 */     super(paramInt);
/* 30 */     this.aI = this.aF;
/* 31 */     if (paramBoolean) {
/* 32 */       this.as.A = 3;
/* 33 */       this.aI = this.aG;
/*    */     }
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 39 */     if (paramRandom.nextInt(3) > 0) {
/* 40 */       return this.aE;
/*    */     }
/* 42 */     return super.a(paramRandom);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 47 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */     
/*    */ 
/* 50 */     int i = 3 + paramRandom.nextInt(6);
/* 51 */     int k; int m; for (int j = 0; j < i; j++) {
/* 52 */       k = paramRandom.nextInt(16);
/* 53 */       m = paramRandom.nextInt(28) + 4;
/* 54 */       int n = paramRandom.nextInt(16);
/*    */       
/* 56 */       BlockPosition localBlockPosition = paramBlockPosition.a(k, m, n);
/* 57 */       if (paramWorld.getType(localBlockPosition).getBlock() == Blocks.STONE) {
/* 58 */         paramWorld.setTypeAndData(localBlockPosition, Blocks.EMERALD_ORE.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 63 */     for (i = 0; i < 7; i++) {
/* 64 */       j = paramRandom.nextInt(16);
/* 65 */       k = paramRandom.nextInt(64);
/* 66 */       m = paramRandom.nextInt(16);
/* 67 */       this.aD.generate(paramWorld, paramRandom, paramBlockPosition.a(j, k, m));
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*    */   {
/* 73 */     this.ak = Blocks.GRASS.getBlockData();
/* 74 */     this.al = Blocks.DIRT.getBlockData();
/* 75 */     if (((paramDouble < -1.0D) || (paramDouble > 2.0D)) && (this.aI == this.aH)) {
/* 76 */       this.ak = Blocks.GRAVEL.getBlockData();
/* 77 */       this.al = Blocks.GRAVEL.getBlockData();
/* 78 */     } else if ((paramDouble > 1.0D) && (this.aI != this.aG)) {
/* 79 */       this.ak = Blocks.STONE.getBlockData();
/* 80 */       this.al = Blocks.STONE.getBlockData();
/*    */     }
/* 82 */     b(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */   
/*    */   private BiomeBigHills b(BiomeBase paramBiomeBase) {
/* 86 */     this.aI = this.aH;
/*    */     
/* 88 */     a(paramBiomeBase.ai, true);
/* 89 */     a(paramBiomeBase.ah + " M");
/* 90 */     a(new BiomeBase.BiomeTemperature(paramBiomeBase.an, paramBiomeBase.ao));
/* 91 */     a(paramBiomeBase.temperature, paramBiomeBase.humidity);
/* 92 */     return this;
/*    */   }
/*    */   
/*    */   protected BiomeBase d(int paramInt)
/*    */   {
/* 97 */     return new BiomeBigHills(paramInt, false).b(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeBigHills.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */