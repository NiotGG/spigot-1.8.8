/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomePlains
/*    */   extends BiomeBase
/*    */ {
/*    */   protected boolean aD;
/*    */   
/*    */   protected BiomePlains(int paramInt)
/*    */   {
/* 15 */     super(paramInt);
/*    */     
/* 17 */     a(0.8F, 0.4F);
/* 18 */     a(e);
/*    */     
/* 20 */     this.au.add(new BiomeBase.BiomeMeta(EntityHorse.class, 5, 2, 6));
/*    */     
/* 22 */     this.as.A = 64537;
/* 23 */     this.as.B = 4;
/* 24 */     this.as.C = 10;
/*    */   }
/*    */   
/*    */   public BlockFlowers.EnumFlowerVarient a(Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 29 */     double d = af.a(paramBlockPosition.getX() / 200.0D, paramBlockPosition.getZ() / 200.0D);
/* 30 */     int i; if (d < -0.8D) {
/* 31 */       i = paramRandom.nextInt(4);
/* 32 */       switch (i) {
/*    */       case 0: 
/* 34 */         return BlockFlowers.EnumFlowerVarient.ORANGE_TULIP;
/*    */       case 1: 
/* 36 */         return BlockFlowers.EnumFlowerVarient.RED_TULIP;
/*    */       case 2: 
/* 38 */         return BlockFlowers.EnumFlowerVarient.PINK_TULIP;
/*    */       }
/*    */       
/* 41 */       return BlockFlowers.EnumFlowerVarient.WHITE_TULIP;
/*    */     }
/*    */     
/* 44 */     if (paramRandom.nextInt(3) > 0) {
/* 45 */       i = paramRandom.nextInt(3);
/* 46 */       if (i == 0)
/* 47 */         return BlockFlowers.EnumFlowerVarient.POPPY;
/* 48 */       if (i == 1) {
/* 49 */         return BlockFlowers.EnumFlowerVarient.HOUSTONIA;
/*    */       }
/* 51 */       return BlockFlowers.EnumFlowerVarient.OXEYE_DAISY;
/*    */     }
/*    */     
/* 54 */     return BlockFlowers.EnumFlowerVarient.DANDELION;
/*    */   }
/*    */   
/*    */ 
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 60 */     double d = af.a((paramBlockPosition.getX() + 8) / 200.0D, (paramBlockPosition.getZ() + 8) / 200.0D);
/* 61 */     int i; int j; int k; int m; if (d < -0.8D) {
/* 62 */       this.as.B = 15;
/* 63 */       this.as.C = 5;
/*    */     } else {
/* 65 */       this.as.B = 4;
/* 66 */       this.as.C = 10;
/*    */       
/* 68 */       ag.a(BlockTallPlant.EnumTallFlowerVariants.GRASS);
/* 69 */       for (i = 0; i < 7; i++) {
/* 70 */         j = paramRandom.nextInt(16) + 8;
/* 71 */         k = paramRandom.nextInt(16) + 8;
/* 72 */         m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)).getY() + 32);
/* 73 */         ag.generate(paramWorld, paramRandom, paramBlockPosition.a(j, m, k));
/*    */       }
/*    */     }
/* 76 */     if (this.aD) {
/* 77 */       ag.a(BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER);
/* 78 */       for (i = 0; i < 10; i++) {
/* 79 */         j = paramRandom.nextInt(16) + 8;
/* 80 */         k = paramRandom.nextInt(16) + 8;
/* 81 */         m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)).getY() + 32);
/* 82 */         ag.generate(paramWorld, paramRandom, paramBlockPosition.a(j, m, k));
/*    */       }
/*    */     }
/* 85 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */   }
/*    */   
/*    */   protected BiomeBase d(int paramInt)
/*    */   {
/* 90 */     BiomePlains localBiomePlains = new BiomePlains(paramInt);
/* 91 */     localBiomePlains.a("Sunflower Plains");
/* 92 */     localBiomePlains.aD = true;
/* 93 */     localBiomePlains.b(9286496);
/* 94 */     localBiomePlains.aj = 14273354;
/* 95 */     return localBiomePlains;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */