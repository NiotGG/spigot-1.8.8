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
/*    */ public class BiomeSavanna
/*    */   extends BiomeBase
/*    */ {
/* 16 */   private static final WorldGenAcaciaTree aD = new WorldGenAcaciaTree(false);
/*    */   
/*    */   protected BiomeSavanna(int paramInt) {
/* 19 */     super(paramInt);
/*    */     
/* 21 */     this.au.add(new BiomeBase.BiomeMeta(EntityHorse.class, 1, 2, 6));
/*    */     
/* 23 */     this.as.A = 1;
/* 24 */     this.as.B = 4;
/* 25 */     this.as.C = 20;
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 30 */     if (paramRandom.nextInt(5) > 0) {
/* 31 */       return aD;
/*    */     }
/* 33 */     return this.aA;
/*    */   }
/*    */   
/*    */   protected BiomeBase d(int paramInt)
/*    */   {
/* 38 */     BiomeSavannaSub localBiomeSavannaSub = new BiomeSavannaSub(paramInt, this);
/*    */     
/* 40 */     localBiomeSavannaSub.temperature = ((this.temperature + 1.0F) * 0.5F);
/* 41 */     localBiomeSavannaSub.an = (this.an * 0.5F + 0.3F);
/* 42 */     localBiomeSavannaSub.ao = (this.ao * 0.5F + 1.2F);
/*    */     
/* 44 */     return localBiomeSavannaSub;
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 49 */     ag.a(BlockTallPlant.EnumTallFlowerVariants.GRASS);
/* 50 */     for (int i = 0; i < 7; i++) {
/* 51 */       int j = paramRandom.nextInt(16) + 8;
/* 52 */       int k = paramRandom.nextInt(16) + 8;
/* 53 */       int m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)).getY() + 32);
/* 54 */       ag.generate(paramWorld, paramRandom, paramBlockPosition.a(j, m, k));
/*    */     }
/* 56 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */   }
/*    */   
/*    */   public static class BiomeSavannaSub extends BiomeBaseSub {
/*    */     public BiomeSavannaSub(int paramInt, BiomeBase paramBiomeBase) {
/* 61 */       super(paramBiomeBase);
/*    */       
/* 63 */       this.as.A = 2;
/* 64 */       this.as.B = 2;
/* 65 */       this.as.C = 5;
/*    */     }
/*    */     
/*    */     public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*    */     {
/* 70 */       this.ak = Blocks.GRASS.getBlockData();
/* 71 */       this.al = Blocks.DIRT.getBlockData();
/* 72 */       if (paramDouble > 1.75D) {
/* 73 */         this.ak = Blocks.STONE.getBlockData();
/* 74 */         this.al = Blocks.STONE.getBlockData();
/* 75 */       } else if (paramDouble > -0.5D) {
/* 76 */         this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT);
/*    */       }
/* 78 */       b(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*    */     }
/*    */     
/*    */     public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */     {
/* 83 */       this.as.a(paramWorld, paramRandom, this, paramBlockPosition);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeSavanna.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */