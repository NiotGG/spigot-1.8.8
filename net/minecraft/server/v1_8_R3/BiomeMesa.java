/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class BiomeMesa
/*     */   extends BiomeBase
/*     */ {
/*     */   private IBlockData[] aD;
/*     */   private long aE;
/*     */   private NoiseGenerator3 aF;
/*     */   private NoiseGenerator3 aG;
/*     */   private NoiseGenerator3 aH;
/*     */   private boolean aI;
/*     */   private boolean aJ;
/*     */   
/*     */   public BiomeMesa(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  29 */     super(paramInt);
/*  30 */     this.aI = paramBoolean1;
/*  31 */     this.aJ = paramBoolean2;
/*     */     
/*  33 */     b();
/*  34 */     a(2.0F, 0.0F);
/*     */     
/*     */ 
/*  37 */     this.au.clear();
/*  38 */     this.ak = Blocks.SAND.getBlockData().set(BlockSand.VARIANT, BlockSand.EnumSandVariant.RED_SAND);
/*  39 */     this.al = Blocks.STAINED_HARDENED_CLAY.getBlockData();
/*     */     
/*  41 */     this.as.A = 64537;
/*  42 */     this.as.D = 20;
/*  43 */     this.as.F = 3;
/*  44 */     this.as.G = 5;
/*  45 */     this.as.B = 0;
/*     */     
/*  47 */     this.au.clear();
/*     */     
/*  49 */     if (paramBoolean2) {
/*  50 */       this.as.A = 5;
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenTreeAbstract a(Random paramRandom)
/*     */   {
/*  56 */     return this.aA;
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
/*     */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  71 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*     */   {
/*  76 */     if ((this.aD == null) || (this.aE != paramWorld.getSeed())) {
/*  77 */       a(paramWorld.getSeed());
/*     */     }
/*  79 */     if ((this.aF == null) || (this.aG == null) || (this.aE != paramWorld.getSeed())) {
/*  80 */       Random localRandom = new Random(this.aE);
/*  81 */       this.aF = new NoiseGenerator3(localRandom, 4);
/*  82 */       this.aG = new NoiseGenerator3(localRandom, 1);
/*     */     }
/*  84 */     this.aE = paramWorld.getSeed();
/*     */     
/*  86 */     double d1 = 0.0D;
/*  87 */     if (this.aI) {
/*  88 */       i = (paramInt1 & 0xFFFFFFF0) + (paramInt2 & 0xF);
/*  89 */       j = (paramInt2 & 0xFFFFFFF0) + (paramInt1 & 0xF);
/*     */       
/*  91 */       double d2 = Math.min(Math.abs(paramDouble), this.aF.a(i * 0.25D, j * 0.25D));
/*  92 */       if (d2 > 0.0D) {
/*  93 */         double d3 = 0.001953125D;
/*  94 */         double d4 = Math.abs(this.aG.a(i * d3, j * d3));
/*  95 */         d1 = d2 * d2 * 2.5D;
/*  96 */         double d5 = Math.ceil(d4 * 50.0D) + 14.0D;
/*  97 */         if (d1 > d5) {
/*  98 */           d1 = d5;
/*     */         }
/* 100 */         d1 += 64.0D;
/*     */       }
/*     */     }
/*     */     
/* 104 */     int i = paramInt1 & 0xF;
/* 105 */     int j = paramInt2 & 0xF;
/*     */     
/* 107 */     int k = paramWorld.F();
/*     */     
/* 109 */     IBlockData localIBlockData1 = Blocks.STAINED_HARDENED_CLAY.getBlockData();
/* 110 */     IBlockData localIBlockData2 = this.al;
/*     */     
/* 112 */     int m = (int)(paramDouble / 3.0D + 3.0D + paramRandom.nextDouble() * 0.25D);
/* 113 */     int n = Math.cos(paramDouble / 3.0D * 3.141592653589793D) > 0.0D ? 1 : 0;
/* 114 */     int i1 = -1;
/* 115 */     int i2 = 0;
/*     */     
/* 117 */     for (int i3 = 255; i3 >= 0; i3--) {
/* 118 */       if ((paramChunkSnapshot.a(j, i3, i).getBlock().getMaterial() == Material.AIR) && (i3 < (int)d1)) {
/* 119 */         paramChunkSnapshot.a(j, i3, i, Blocks.STONE.getBlockData());
/*     */       }
/*     */       
/* 122 */       if (i3 <= paramRandom.nextInt(5)) {
/* 123 */         paramChunkSnapshot.a(j, i3, i, Blocks.BEDROCK.getBlockData());
/*     */       } else {
/* 125 */         IBlockData localIBlockData3 = paramChunkSnapshot.a(j, i3, i);
/*     */         
/* 127 */         if (localIBlockData3.getBlock().getMaterial() == Material.AIR) {
/* 128 */           i1 = -1;
/* 129 */         } else if (localIBlockData3.getBlock() == Blocks.STONE) { IBlockData localIBlockData4;
/* 130 */           if (i1 == -1) {
/* 131 */             i2 = 0;
/* 132 */             if (m <= 0) {
/* 133 */               localIBlockData1 = null;
/* 134 */               localIBlockData2 = Blocks.STONE.getBlockData();
/* 135 */             } else if ((i3 >= k - 4) && (i3 <= k + 1)) {
/* 136 */               localIBlockData1 = Blocks.STAINED_HARDENED_CLAY.getBlockData();
/* 137 */               localIBlockData2 = this.al;
/*     */             }
/*     */             
/* 140 */             if ((i3 < k) && ((localIBlockData1 == null) || (localIBlockData1.getBlock().getMaterial() == Material.AIR))) {
/* 141 */               localIBlockData1 = Blocks.WATER.getBlockData();
/*     */             }
/*     */             
/* 144 */             i1 = m + Math.max(0, i3 - k);
/* 145 */             if (i3 >= k - 1) {
/* 146 */               if ((this.aJ) && (i3 > 86 + m * 2)) {
/* 147 */                 if (n != 0) {
/* 148 */                   paramChunkSnapshot.a(j, i3, i, Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT));
/*     */                 } else {
/* 150 */                   paramChunkSnapshot.a(j, i3, i, Blocks.GRASS.getBlockData());
/*     */                 }
/* 152 */               } else if (i3 > k + 3 + m)
/*     */               {
/* 154 */                 if ((i3 < 64) || (i3 > 127)) {
/* 155 */                   localIBlockData4 = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE);
/* 156 */                 } else if (n != 0) {
/* 157 */                   localIBlockData4 = Blocks.HARDENED_CLAY.getBlockData();
/*     */                 } else {
/* 159 */                   localIBlockData4 = a(paramInt1, i3, paramInt2);
/*     */                 }
/* 161 */                 paramChunkSnapshot.a(j, i3, i, localIBlockData4);
/*     */               } else {
/* 163 */                 paramChunkSnapshot.a(j, i3, i, this.ak);
/* 164 */                 i2 = 1;
/*     */               }
/*     */             } else {
/* 167 */               paramChunkSnapshot.a(j, i3, i, localIBlockData2);
/* 168 */               if (localIBlockData2.getBlock() == Blocks.STAINED_HARDENED_CLAY) {
/* 169 */                 paramChunkSnapshot.a(j, i3, i, localIBlockData2.getBlock().getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE));
/*     */               }
/*     */             }
/* 172 */           } else if (i1 > 0) {
/* 173 */             i1--;
/*     */             
/* 175 */             if (i2 != 0) {
/* 176 */               paramChunkSnapshot.a(j, i3, i, Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE));
/*     */             } else {
/* 178 */               localIBlockData4 = a(paramInt1, i3, paramInt2);
/* 179 */               paramChunkSnapshot.a(j, i3, i, localIBlockData4);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(long paramLong) {
/* 188 */     this.aD = new IBlockData[64];
/* 189 */     Arrays.fill(this.aD, Blocks.HARDENED_CLAY.getBlockData());
/*     */     
/* 191 */     Random localRandom = new Random(paramLong);
/* 192 */     this.aH = new NoiseGenerator3(localRandom, 1);
/*     */     
/* 194 */     for (int i = 0; i < 64; i++) {
/* 195 */       i += localRandom.nextInt(5) + 1;
/* 196 */       if (i < 64) {
/* 197 */         this.aD[i] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE);
/*     */       }
/*     */     }
/*     */     
/* 201 */     i = localRandom.nextInt(4) + 2;
/* 202 */     for (int j = 0; j < i; j++) {
/* 203 */       k = localRandom.nextInt(3) + 1;
/* 204 */       m = localRandom.nextInt(64);
/*     */       
/* 206 */       for (n = 0; (m + n < 64) && (n < k); n++) {
/* 207 */         this.aD[(m + n)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.YELLOW);
/*     */       }
/*     */     }
/* 210 */     j = localRandom.nextInt(4) + 2;
/* 211 */     for (int k = 0; k < j; k++) {
/* 212 */       m = localRandom.nextInt(3) + 2;
/* 213 */       n = localRandom.nextInt(64);
/*     */       
/* 215 */       for (i1 = 0; (n + i1 < 64) && (i1 < m); i1++) {
/* 216 */         this.aD[(n + i1)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.BROWN);
/*     */       }
/*     */     }
/* 219 */     k = localRandom.nextInt(4) + 2;
/* 220 */     int i2; for (int m = 0; m < k; m++) {
/* 221 */       n = localRandom.nextInt(3) + 1;
/* 222 */       i1 = localRandom.nextInt(64);
/*     */       
/* 224 */       for (i2 = 0; (i1 + i2 < 64) && (i2 < n); i2++) {
/* 225 */         this.aD[(i1 + i2)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.RED);
/*     */       }
/*     */     }
/* 228 */     m = localRandom.nextInt(3) + 3;
/* 229 */     int n = 0;
/* 230 */     for (int i1 = 0; i1 < m; i1++) {
/* 231 */       i2 = 1;
/* 232 */       n += localRandom.nextInt(16) + 4;
/*     */       
/* 234 */       for (int i3 = 0; (n + i3 < 64) && (i3 < i2); i3++) {
/* 235 */         this.aD[(n + i3)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.WHITE);
/* 236 */         if ((n + i3 > 1) && (localRandom.nextBoolean())) {
/* 237 */           this.aD[(n + i3 - 1)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.SILVER);
/*     */         }
/* 239 */         if ((n + i3 < 63) && (localRandom.nextBoolean())) {
/* 240 */           this.aD[(n + i3 + 1)] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.SILVER);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private IBlockData a(int paramInt1, int paramInt2, int paramInt3) {
/* 247 */     int i = (int)Math.round(this.aH.a(paramInt1 * 1.0D / 512.0D, paramInt1 * 1.0D / 512.0D) * 2.0D);
/* 248 */     return this.aD[((paramInt2 + i + 64) % 64)];
/*     */   }
/*     */   
/*     */   protected BiomeBase d(int paramInt)
/*     */   {
/* 253 */     boolean bool = this.id == BiomeBase.MESA.id;
/*     */     
/* 255 */     BiomeMesa localBiomeMesa = new BiomeMesa(paramInt, bool, this.aJ);
/*     */     
/* 257 */     if (!bool) {
/* 258 */       localBiomeMesa.a(g);
/* 259 */       localBiomeMesa.a(this.ah + " M");
/*     */     } else {
/* 261 */       localBiomeMesa.a(this.ah + " (Bryce)");
/*     */     }
/* 263 */     localBiomeMesa.a(this.ai, true);
/*     */     
/* 265 */     return localBiomeMesa;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeMesa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */