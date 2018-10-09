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
/*    */ public class BiomeIcePlains
/*    */   extends BiomeBase
/*    */ {
/*    */   private boolean aD;
/* 15 */   private WorldGenPackedIce2 aE = new WorldGenPackedIce2();
/* 16 */   private WorldGenPackedIce1 aF = new WorldGenPackedIce1(4);
/*    */   
/*    */   public BiomeIcePlains(int paramInt, boolean paramBoolean) {
/* 19 */     super(paramInt);
/* 20 */     this.aD = paramBoolean;
/*    */     
/* 22 */     if (paramBoolean) {
/* 23 */       this.ak = Blocks.SNOW.getBlockData();
/*    */     }
/* 25 */     this.au.clear();
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 30 */     if (this.aD) { int j;
/* 31 */       int k; for (int i = 0; i < 3; i++) {
/* 32 */         j = paramRandom.nextInt(16) + 8;
/* 33 */         k = paramRandom.nextInt(16) + 8;
/* 34 */         this.aE.generate(paramWorld, paramRandom, paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)));
/*    */       }
/* 36 */       for (i = 0; i < 2; i++) {
/* 37 */         j = paramRandom.nextInt(16) + 8;
/* 38 */         k = paramRandom.nextInt(16) + 8;
/* 39 */         this.aF.generate(paramWorld, paramRandom, paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)));
/*    */       }
/*    */     }
/*    */     
/* 43 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 48 */     return new WorldGenTaiga2(false);
/*    */   }
/*    */   
/*    */   protected BiomeBase d(int paramInt)
/*    */   {
/* 53 */     BiomeBase localBiomeBase = new BiomeIcePlains(paramInt, true).a(13828095, true).a(this.ah + " Spikes").c().a(0.0F, 0.5F).a(new BiomeBase.BiomeTemperature(this.an + 0.1F, this.ao + 0.1F));
/*    */     
/* 55 */     localBiomeBase.an = (this.an + 0.3F);
/* 56 */     localBiomeBase.ao = (this.ao + 0.4F);
/*    */     
/* 58 */     return localBiomeBase;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeIcePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */