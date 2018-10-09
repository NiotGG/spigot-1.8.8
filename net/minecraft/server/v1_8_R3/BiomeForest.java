/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeForest
/*     */   extends BiomeBase
/*     */ {
/*     */   private int aG;
/*  24 */   protected static final WorldGenForest aD = new WorldGenForest(false, true);
/*  25 */   protected static final WorldGenForest aE = new WorldGenForest(false, false);
/*  26 */   protected static final WorldGenForestTree aF = new WorldGenForestTree(false);
/*     */   
/*     */   public BiomeForest(int paramInt1, int paramInt2) {
/*  29 */     super(paramInt1);
/*  30 */     this.aG = paramInt2;
/*  31 */     this.as.A = 10;
/*  32 */     this.as.C = 2;
/*     */     
/*  34 */     if (this.aG == 1) {
/*  35 */       this.as.A = 6;
/*  36 */       this.as.B = 100;
/*  37 */       this.as.C = 1;
/*     */     }
/*  39 */     a(5159473);
/*  40 */     a(0.7F, 0.8F);
/*     */     
/*  42 */     if (this.aG == 2) {
/*  43 */       this.aj = 353825;
/*  44 */       this.ai = 3175492;
/*  45 */       a(0.6F, 0.6F);
/*     */     }
/*     */     
/*  48 */     if (this.aG == 0) {
/*  49 */       this.au.add(new BiomeBase.BiomeMeta(EntityWolf.class, 5, 4, 4));
/*     */     }
/*     */     
/*  52 */     if (this.aG == 3) {
/*  53 */       this.as.A = 64537;
/*     */     }
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int paramInt, boolean paramBoolean)
/*     */   {
/*  59 */     if (this.aG == 2) {
/*  60 */       this.aj = 353825;
/*  61 */       this.ai = paramInt;
/*     */       
/*  63 */       if (paramBoolean) {
/*  64 */         this.aj = ((this.aj & 0xFEFEFE) >> 1);
/*     */       }
/*  66 */       return this;
/*     */     }
/*  68 */     return super.a(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   public WorldGenTreeAbstract a(Random paramRandom)
/*     */   {
/*  74 */     if ((this.aG == 3) && (paramRandom.nextInt(3) > 0)) {
/*  75 */       return aF;
/*     */     }
/*  77 */     if ((this.aG == 2) || (paramRandom.nextInt(5) == 0)) {
/*  78 */       return aE;
/*     */     }
/*  80 */     return this.aA;
/*     */   }
/*     */   
/*     */   public BlockFlowers.EnumFlowerVarient a(Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  85 */     if (this.aG == 1) {
/*  86 */       double d = MathHelper.a((1.0D + af.a(paramBlockPosition.getX() / 48.0D, paramBlockPosition.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  87 */       BlockFlowers.EnumFlowerVarient localEnumFlowerVarient = BlockFlowers.EnumFlowerVarient.values()[((int)(d * BlockFlowers.EnumFlowerVarient.values().length))];
/*  88 */       if (localEnumFlowerVarient == BlockFlowers.EnumFlowerVarient.BLUE_ORCHID) {
/*  89 */         return BlockFlowers.EnumFlowerVarient.POPPY;
/*     */       }
/*  91 */       return localEnumFlowerVarient;
/*     */     }
/*     */     
/*  94 */     return super.a(paramRandom, paramBlockPosition);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition) { int k;
/*     */     int m;
/*  99 */     if (this.aG == 3) {
/* 100 */       for (i = 0; i < 4; i++) {
/* 101 */         for (j = 0; j < 4; j++) {
/* 102 */           k = i * 4 + 1 + 8 + paramRandom.nextInt(3);
/* 103 */           m = j * 4 + 1 + 8 + paramRandom.nextInt(3);
/*     */           
/* 105 */           BlockPosition localBlockPosition = paramWorld.getHighestBlockYAt(paramBlockPosition.a(k, 0, m));
/* 106 */           Object localObject; if (paramRandom.nextInt(20) == 0) {
/* 107 */             localObject = new WorldGenHugeMushroom();
/* 108 */             ((WorldGenHugeMushroom)localObject).generate(paramWorld, paramRandom, localBlockPosition);
/*     */           } else {
/* 110 */             localObject = a(paramRandom);
/* 111 */             ((WorldGenTreeAbstract)localObject).e();
/* 112 */             if (((WorldGenTreeAbstract)localObject).generate(paramWorld, paramRandom, localBlockPosition)) {
/* 113 */               ((WorldGenTreeAbstract)localObject).a(paramWorld, paramRandom, localBlockPosition);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 119 */     int i = paramRandom.nextInt(5) - 3;
/* 120 */     if (this.aG == 1) {
/* 121 */       i += 2;
/*     */     }
/* 123 */     for (int j = 0; j < i; j++) {
/* 124 */       k = paramRandom.nextInt(3);
/* 125 */       if (k == 0) {
/* 126 */         ag.a(BlockTallPlant.EnumTallFlowerVariants.SYRINGA);
/* 127 */       } else if (k == 1) {
/* 128 */         ag.a(BlockTallPlant.EnumTallFlowerVariants.ROSE);
/* 129 */       } else if (k == 2) {
/* 130 */         ag.a(BlockTallPlant.EnumTallFlowerVariants.PAEONIA);
/*     */       }
/*     */       
/* 133 */       for (m = 0; m < 5; m++) {
/* 134 */         int n = paramRandom.nextInt(16) + 8;
/* 135 */         int i1 = paramRandom.nextInt(16) + 8;
/* 136 */         int i2 = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(n, 0, i1)).getY() + 32);
/*     */         
/* 138 */         if (ag.generate(paramWorld, paramRandom, new BlockPosition(paramBlockPosition.getX() + n, i2, paramBlockPosition.getZ() + i1))) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 143 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BiomeBase d(int paramInt)
/*     */   {
/* 159 */     if (this.id == BiomeBase.FOREST.id) {
/* 160 */       BiomeForest localBiomeForest = new BiomeForest(paramInt, 1);
/* 161 */       localBiomeForest.a(new BiomeBase.BiomeTemperature(this.an, this.ao + 0.2F));
/* 162 */       localBiomeForest.a("Flower Forest");
/* 163 */       localBiomeForest.a(6976549, true);
/* 164 */       localBiomeForest.a(8233509);
/* 165 */       return localBiomeForest;
/*     */     }
/* 167 */     if ((this.id == BiomeBase.BIRCH_FOREST.id) || (this.id == BiomeBase.BIRCH_FOREST_HILLS.id)) {
/* 168 */       new BiomeBaseSub(paramInt, this)
/*     */       {
/*     */         public WorldGenTreeAbstract a(Random paramAnonymousRandom) {
/* 171 */           if (paramAnonymousRandom.nextBoolean()) {
/* 172 */             return BiomeForest.aD;
/*     */           }
/* 174 */           return BiomeForest.aE;
/*     */         }
/*     */       };
/*     */     }
/* 178 */     new BiomeBaseSub(paramInt, this)
/*     */     {
/*     */       public void a(World paramAnonymousWorld, Random paramAnonymousRandom, BlockPosition paramAnonymousBlockPosition) {
/* 181 */         this.aE.a(paramAnonymousWorld, paramAnonymousRandom, paramAnonymousBlockPosition);
/*     */       }
/*     */     };
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */