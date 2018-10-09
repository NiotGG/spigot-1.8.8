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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderHell
/*     */   implements IChunkProvider
/*     */ {
/*     */   private final World h;
/*     */   private final boolean i;
/*     */   private final Random j;
/*  33 */   private double[] k = new double['Ā'];
/*  34 */   private double[] l = new double['Ā'];
/*  35 */   private double[] m = new double['Ā'];
/*     */   
/*     */   private double[] n;
/*     */   
/*     */   private final NoiseGeneratorOctaves o;
/*     */   private final NoiseGeneratorOctaves p;
/*     */   private final NoiseGeneratorOctaves q;
/*     */   private final NoiseGeneratorOctaves r;
/*     */   private final NoiseGeneratorOctaves s;
/*     */   public final NoiseGeneratorOctaves a;
/*     */   public final NoiseGeneratorOctaves b;
/*  46 */   private final WorldGenFire t = new WorldGenFire();
/*  47 */   private final WorldGenLightStone1 u = new WorldGenLightStone1();
/*  48 */   private final WorldGenLightStone2 v = new WorldGenLightStone2();
/*  49 */   private final WorldGenerator w = new WorldGenMinable(Blocks.QUARTZ_ORE.getBlockData(), 14, BlockPredicate.a(Blocks.NETHERRACK));
/*  50 */   private final WorldGenHellLava x = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);
/*  51 */   private final WorldGenHellLava y = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
/*  52 */   private final WorldGenMushrooms z = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
/*  53 */   private final WorldGenMushrooms A = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
/*  54 */   private final WorldGenNether B = new WorldGenNether();
/*  55 */   private final WorldGenBase C = new WorldGenCavesHell();
/*     */   double[] c;
/*     */   
/*  58 */   public ChunkProviderHell(World paramWorld, boolean paramBoolean, long paramLong) { this.h = paramWorld;
/*  59 */     this.i = paramBoolean;
/*     */     
/*  61 */     this.j = new Random(paramLong);
/*  62 */     this.o = new NoiseGeneratorOctaves(this.j, 16);
/*  63 */     this.p = new NoiseGeneratorOctaves(this.j, 16);
/*  64 */     this.q = new NoiseGeneratorOctaves(this.j, 8);
/*  65 */     this.r = new NoiseGeneratorOctaves(this.j, 4);
/*  66 */     this.s = new NoiseGeneratorOctaves(this.j, 4);
/*     */     
/*  68 */     this.a = new NoiseGeneratorOctaves(this.j, 10);
/*  69 */     this.b = new NoiseGeneratorOctaves(this.j, 16);
/*     */     
/*  71 */     paramWorld.b(63);
/*     */   }
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot) {
/*  75 */     int i1 = 4;
/*  76 */     int i2 = this.h.F() / 2 + 1;
/*     */     
/*  78 */     int i3 = i1 + 1;
/*  79 */     int i4 = 17;
/*  80 */     int i5 = i1 + 1;
/*  81 */     this.n = a(this.n, paramInt1 * i1, 0, paramInt2 * i1, i3, i4, i5);
/*     */     
/*  83 */     for (int i6 = 0; i6 < i1; i6++) {
/*  84 */       for (int i7 = 0; i7 < i1; i7++) {
/*  85 */         for (int i8 = 0; i8 < 16; i8++) {
/*  86 */           double d1 = 0.125D;
/*  87 */           double d2 = this.n[(((i6 + 0) * i5 + (i7 + 0)) * i4 + (i8 + 0))];
/*  88 */           double d3 = this.n[(((i6 + 0) * i5 + (i7 + 1)) * i4 + (i8 + 0))];
/*  89 */           double d4 = this.n[(((i6 + 1) * i5 + (i7 + 0)) * i4 + (i8 + 0))];
/*  90 */           double d5 = this.n[(((i6 + 1) * i5 + (i7 + 1)) * i4 + (i8 + 0))];
/*     */           
/*  92 */           double d6 = (this.n[(((i6 + 0) * i5 + (i7 + 0)) * i4 + (i8 + 1))] - d2) * d1;
/*  93 */           double d7 = (this.n[(((i6 + 0) * i5 + (i7 + 1)) * i4 + (i8 + 1))] - d3) * d1;
/*  94 */           double d8 = (this.n[(((i6 + 1) * i5 + (i7 + 0)) * i4 + (i8 + 1))] - d4) * d1;
/*  95 */           double d9 = (this.n[(((i6 + 1) * i5 + (i7 + 1)) * i4 + (i8 + 1))] - d5) * d1;
/*     */           
/*  97 */           for (int i9 = 0; i9 < 8; i9++) {
/*  98 */             double d10 = 0.25D;
/*     */             
/* 100 */             double d11 = d2;
/* 101 */             double d12 = d3;
/* 102 */             double d13 = (d4 - d2) * d10;
/* 103 */             double d14 = (d5 - d3) * d10;
/*     */             
/* 105 */             for (int i10 = 0; i10 < 4; i10++) {
/* 106 */               double d15 = 0.25D;
/*     */               
/* 108 */               double d16 = d11;
/* 109 */               double d17 = (d12 - d11) * d15;
/* 110 */               for (int i11 = 0; i11 < 4; i11++) {
/* 111 */                 IBlockData localIBlockData = null;
/* 112 */                 if (i8 * 8 + i9 < i2) {
/* 113 */                   localIBlockData = Blocks.LAVA.getBlockData();
/*     */                 }
/* 115 */                 if (d16 > 0.0D) {
/* 116 */                   localIBlockData = Blocks.NETHERRACK.getBlockData();
/*     */                 }
/*     */                 
/* 119 */                 int i12 = i10 + i6 * 4;
/* 120 */                 int i13 = i9 + i8 * 8;
/* 121 */                 int i14 = i11 + i7 * 4;
/* 122 */                 paramChunkSnapshot.a(i12, i13, i14, localIBlockData);
/* 123 */                 d16 += d17;
/*     */               }
/* 125 */               d11 += d13;
/* 126 */               d12 += d14;
/*     */             }
/*     */             
/* 129 */             d2 += d6;
/* 130 */             d3 += d7;
/* 131 */             d4 += d8;
/* 132 */             d5 += d9;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot) {
/* 140 */     int i1 = this.h.F() + 1;
/*     */     
/* 142 */     double d1 = 0.03125D;
/* 143 */     this.k = this.r.a(this.k, paramInt1 * 16, paramInt2 * 16, 0, 16, 16, 1, d1, d1, 1.0D);
/* 144 */     this.l = this.r.a(this.l, paramInt1 * 16, 109, paramInt2 * 16, 16, 1, 16, d1, 1.0D, d1);
/* 145 */     this.m = this.s.a(this.m, paramInt1 * 16, paramInt2 * 16, 0, 16, 16, 1, d1 * 2.0D, d1 * 2.0D, d1 * 2.0D);
/*     */     
/* 147 */     for (int i2 = 0; i2 < 16; i2++) {
/* 148 */       for (int i3 = 0; i3 < 16; i3++) {
/* 149 */         int i4 = this.k[(i2 + i3 * 16)] + this.j.nextDouble() * 0.2D > 0.0D ? 1 : 0;
/* 150 */         int i5 = this.l[(i2 + i3 * 16)] + this.j.nextDouble() * 0.2D > 0.0D ? 1 : 0;
/* 151 */         int i6 = (int)(this.m[(i2 + i3 * 16)] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
/*     */         
/* 153 */         int i7 = -1;
/*     */         
/* 155 */         IBlockData localIBlockData1 = Blocks.NETHERRACK.getBlockData();
/* 156 */         IBlockData localIBlockData2 = Blocks.NETHERRACK.getBlockData();
/*     */         
/* 158 */         for (int i8 = 127; i8 >= 0; i8--) {
/* 159 */           if ((i8 >= 127 - this.j.nextInt(5)) || (i8 <= this.j.nextInt(5))) {
/* 160 */             paramChunkSnapshot.a(i3, i8, i2, Blocks.BEDROCK.getBlockData());
/*     */           } else {
/* 162 */             IBlockData localIBlockData3 = paramChunkSnapshot.a(i3, i8, i2);
/* 163 */             if ((localIBlockData3.getBlock() == null) || (localIBlockData3.getBlock().getMaterial() == Material.AIR)) {
/* 164 */               i7 = -1;
/* 165 */             } else if (localIBlockData3.getBlock() == Blocks.NETHERRACK) {
/* 166 */               if (i7 == -1) {
/* 167 */                 if (i6 <= 0) {
/* 168 */                   localIBlockData1 = null;
/* 169 */                   localIBlockData2 = Blocks.NETHERRACK.getBlockData();
/* 170 */                 } else if ((i8 >= i1 - 4) && (i8 <= i1 + 1)) {
/* 171 */                   localIBlockData1 = Blocks.NETHERRACK.getBlockData();
/* 172 */                   localIBlockData2 = Blocks.NETHERRACK.getBlockData();
/* 173 */                   if (i5 != 0) {
/* 174 */                     localIBlockData1 = Blocks.GRAVEL.getBlockData();
/* 175 */                     localIBlockData2 = Blocks.NETHERRACK.getBlockData();
/*     */                   }
/* 177 */                   if (i4 != 0) {
/* 178 */                     localIBlockData1 = Blocks.SOUL_SAND.getBlockData();
/* 179 */                     localIBlockData2 = Blocks.SOUL_SAND.getBlockData();
/*     */                   }
/*     */                 }
/*     */                 
/* 183 */                 if ((i8 < i1) && ((localIBlockData1 == null) || (localIBlockData1.getBlock().getMaterial() == Material.AIR))) {
/* 184 */                   localIBlockData1 = Blocks.LAVA.getBlockData();
/*     */                 }
/*     */                 
/* 187 */                 i7 = i6;
/* 188 */                 if (i8 >= i1 - 1) {
/* 189 */                   paramChunkSnapshot.a(i3, i8, i2, localIBlockData1);
/*     */                 } else {
/* 191 */                   paramChunkSnapshot.a(i3, i8, i2, localIBlockData2);
/*     */                 }
/* 193 */               } else if (i7 > 0) {
/* 194 */                 i7--;
/* 195 */                 paramChunkSnapshot.a(i3, i8, i2, localIBlockData2);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/* 211 */     this.j.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
/*     */     
/* 213 */     ChunkSnapshot localChunkSnapshot = new ChunkSnapshot();
/*     */     
/* 215 */     a(paramInt1, paramInt2, localChunkSnapshot);
/* 216 */     b(paramInt1, paramInt2, localChunkSnapshot);
/*     */     
/* 218 */     this.C.a(this, this.h, paramInt1, paramInt2, localChunkSnapshot);
/* 219 */     if (this.i) {
/* 220 */       this.B.a(this, this.h, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/*     */     
/* 223 */     Chunk localChunk = new Chunk(this.h, localChunkSnapshot, paramInt1, paramInt2);
/* 224 */     BiomeBase[] arrayOfBiomeBase = this.h.getWorldChunkManager().getBiomeBlock(null, paramInt1 * 16, paramInt2 * 16, 16, 16);
/* 225 */     byte[] arrayOfByte = localChunk.getBiomeIndex();
/*     */     
/* 227 */     for (int i1 = 0; i1 < arrayOfByte.length; i1++) {
/* 228 */       arrayOfByte[i1] = ((byte)arrayOfBiomeBase[i1].id);
/*     */     }
/*     */     
/* 231 */     localChunk.l();
/*     */     
/* 233 */     return localChunk;
/*     */   }
/*     */   
/*     */ 
/*     */   private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 239 */     if (paramArrayOfDouble == null) {
/* 240 */       paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
/*     */     }
/*     */     
/* 243 */     double d1 = 684.412D;
/* 244 */     double d2 = 2053.236D;
/*     */     
/* 246 */     this.f = this.a.a(this.f, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 1.0D, 0.0D, 1.0D);
/* 247 */     this.g = this.b.a(this.g, paramInt1, paramInt2, paramInt3, paramInt4, 1, paramInt6, 100.0D, 0.0D, 100.0D);
/*     */     
/* 249 */     this.c = this.q.a(this.c, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 60.0D, d1 / 80.0D);
/* 250 */     this.d = this.o.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/* 251 */     this.e = this.p.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/*     */     
/* 253 */     int i1 = 0;
/* 254 */     double[] arrayOfDouble = new double[paramInt5];
/* 255 */     for (int i2 = 0; i2 < paramInt5; i2++) {
/* 256 */       arrayOfDouble[i2] = (Math.cos(i2 * 3.141592653589793D * 6.0D / paramInt5) * 2.0D);
/*     */       
/* 258 */       double d3 = i2;
/* 259 */       if (i2 > paramInt5 / 2) {
/* 260 */         d3 = paramInt5 - 1 - i2;
/*     */       }
/* 262 */       if (d3 < 4.0D) {
/* 263 */         d3 = 4.0D - d3;
/* 264 */         arrayOfDouble[i2] -= d3 * d3 * d3 * 10.0D;
/*     */       }
/*     */     }
/*     */     
/* 268 */     for (i2 = 0; i2 < paramInt4; i2++) {
/* 269 */       for (int i3 = 0; i3 < paramInt6; i3++) {
/* 270 */         double d4 = 0.0D;
/*     */         
/* 272 */         for (int i4 = 0; i4 < paramInt5; i4++) {
/* 273 */           double d5 = 0.0D;
/* 274 */           double d6 = arrayOfDouble[i4];
/* 275 */           double d7 = this.d[i1] / 512.0D;
/* 276 */           double d8 = this.e[i1] / 512.0D;
/* 277 */           double d9 = (this.c[i1] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 279 */           if (d9 < 0.0D) {
/* 280 */             d5 = d7;
/* 281 */           } else if (d9 > 1.0D) {
/* 282 */             d5 = d8;
/*     */           } else {
/* 284 */             d5 = d7 + (d8 - d7) * d9;
/*     */           }
/*     */           
/* 287 */           d5 -= d6;
/*     */           double d10;
/* 289 */           if (i4 > paramInt5 - 4) {
/* 290 */             d10 = (i4 - (paramInt5 - 4)) / 3.0F;
/* 291 */             d5 = d5 * (1.0D - d10) + -10.0D * d10;
/*     */           }
/*     */           
/* 294 */           if (i4 < d4) {
/* 295 */             d10 = (d4 - i4) / 4.0D;
/* 296 */             d10 = MathHelper.a(d10, 0.0D, 1.0D);
/* 297 */             d5 = d5 * (1.0D - d10) + -10.0D * d10;
/*     */           }
/*     */           
/* 300 */           paramArrayOfDouble[i1] = d5;
/* 301 */           i1++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 306 */     return paramArrayOfDouble;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int paramInt1, int paramInt2)
/*     */   {
/* 311 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
/*     */   {
/* 316 */     BlockFalling.instaFall = true;
/* 317 */     BlockPosition localBlockPosition = new BlockPosition(paramInt1 * 16, 0, paramInt2 * 16);
/*     */     
/* 319 */     ChunkCoordIntPair localChunkCoordIntPair = new ChunkCoordIntPair(paramInt1, paramInt2);
/*     */     
/* 321 */     this.B.a(this.h, this.j, localChunkCoordIntPair);
/*     */     
/* 323 */     for (int i1 = 0; i1 < 8; i1++) {
/* 324 */       this.y.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 327 */     for (i1 = 0; i1 < this.j.nextInt(this.j.nextInt(10) + 1) + 1; i1++) {
/* 328 */       this.t.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 331 */     for (i1 = 0; i1 < this.j.nextInt(this.j.nextInt(10) + 1); i1++) {
/* 332 */       this.u.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 335 */     for (i1 = 0; i1 < 10; i1++) {
/* 336 */       this.v.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 339 */     if (this.j.nextBoolean()) {
/* 340 */       this.z.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 343 */     if (this.j.nextBoolean()) {
/* 344 */       this.A.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
/*     */     }
/*     */     
/* 347 */     for (i1 = 0; i1 < 16; i1++) {
/* 348 */       this.w.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
/*     */     }
/*     */     
/* 351 */     for (i1 = 0; i1 < 16; i1++) {
/* 352 */       this.x.generate(this.h, this.j, localBlockPosition.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
/*     */     }
/*     */     
/* 355 */     BlockFalling.instaFall = false;
/*     */   }
/*     */   
/*     */   public boolean a(IChunkProvider paramIChunkProvider, Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 360 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 365 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c() {}
/*     */   
/*     */ 
/*     */   public boolean unloadChunks()
/*     */   {
/* 374 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave()
/*     */   {
/* 379 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 384 */     return "HellRandomLevelSource";
/*     */   }
/*     */   
/*     */ 
/*     */   public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType paramEnumCreatureType, BlockPosition paramBlockPosition)
/*     */   {
/* 390 */     if (paramEnumCreatureType == EnumCreatureType.MONSTER) {
/* 391 */       if (this.B.b(paramBlockPosition)) {
/* 392 */         return this.B.b();
/*     */       }
/* 394 */       if ((this.B.a(this.h, paramBlockPosition)) && (this.h.getType(paramBlockPosition.down()).getBlock() == Blocks.NETHER_BRICK)) {
/* 395 */         return this.B.b();
/*     */       }
/*     */     }
/*     */     
/* 399 */     BiomeBase localBiomeBase = this.h.getBiome(paramBlockPosition);
/* 400 */     return localBiomeBase.getMobs(paramEnumCreatureType);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(World paramWorld, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 405 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks()
/*     */   {
/* 410 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 415 */     this.B.a(this, this.h, paramInt1, paramInt2, null);
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(BlockPosition paramBlockPosition)
/*     */   {
/* 420 */     return getOrCreateChunk(paramBlockPosition.getX() >> 4, paramBlockPosition.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkProviderHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */