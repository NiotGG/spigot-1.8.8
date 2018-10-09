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
/*     */ public class ChunkProviderGenerate
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random h;
/*     */   private NoiseGeneratorOctaves i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   private NoiseGenerator3 l;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   public NoiseGeneratorOctaves c;
/*     */   private World m;
/*     */   private final boolean n;
/*     */   private WorldType o;
/*     */   private final double[] p;
/*     */   private final float[] q;
/*     */   private CustomWorldSettingsFinal r;
/*  64 */   private Block s = Blocks.WATER;
/*     */   
/*     */   public ChunkProviderGenerate(World paramWorld, long paramLong, boolean paramBoolean, String paramString) {
/*  67 */     this.m = paramWorld;
/*  68 */     this.n = paramBoolean;
/*  69 */     this.o = paramWorld.getWorldData().getType();
/*     */     
/*  71 */     this.h = new Random(paramLong);
/*  72 */     this.i = new NoiseGeneratorOctaves(this.h, 16);
/*  73 */     this.j = new NoiseGeneratorOctaves(this.h, 16);
/*  74 */     this.k = new NoiseGeneratorOctaves(this.h, 8);
/*  75 */     this.l = new NoiseGenerator3(this.h, 4);
/*     */     
/*  77 */     this.a = new NoiseGeneratorOctaves(this.h, 10);
/*  78 */     this.b = new NoiseGeneratorOctaves(this.h, 16);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */     this.c = new NoiseGeneratorOctaves(this.h, 8);
/*     */     
/*  87 */     this.p = new double['̹'];
/*     */     
/*  89 */     this.q = new float[25];
/*  90 */     for (int i1 = -2; i1 <= 2; i1++) {
/*  91 */       for (int i2 = -2; i2 <= 2; i2++) {
/*  92 */         float f1 = 10.0F / MathHelper.c(i1 * i1 + i2 * i2 + 0.2F);
/*  93 */         this.q[(i1 + 2 + (i2 + 2) * 5)] = f1;
/*     */       }
/*     */     }
/*     */     
/*  97 */     if (paramString != null) {
/*  98 */       this.r = CustomWorldSettingsFinal.CustomWorldSettings.a(paramString).b();
/*  99 */       this.s = (this.r.E ? Blocks.LAVA : Blocks.WATER);
/* 100 */       paramWorld.b(this.r.q);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot)
/*     */   {
/* 109 */     this.B = this.m.getWorldChunkManager().getBiomes(this.B, paramInt1 * 4 - 2, paramInt2 * 4 - 2, 10, 10);
/* 110 */     a(paramInt1 * 4, 0, paramInt2 * 4);
/*     */     
/* 112 */     for (int i1 = 0; i1 < 4; i1++) {
/* 113 */       int i2 = i1 * 5;
/* 114 */       int i3 = (i1 + 1) * 5;
/* 115 */       for (int i4 = 0; i4 < 4; i4++) {
/* 116 */         int i5 = (i2 + i4) * 33;
/* 117 */         int i6 = (i2 + i4 + 1) * 33;
/* 118 */         int i7 = (i3 + i4) * 33;
/* 119 */         int i8 = (i3 + i4 + 1) * 33;
/* 120 */         for (int i9 = 0; i9 < 32; i9++) {
/* 121 */           double d1 = 0.125D;
/* 122 */           double d2 = this.p[(i5 + i9)];
/* 123 */           double d3 = this.p[(i6 + i9)];
/* 124 */           double d4 = this.p[(i7 + i9)];
/* 125 */           double d5 = this.p[(i8 + i9)];
/*     */           
/* 127 */           double d6 = (this.p[(i5 + i9 + 1)] - d2) * d1;
/* 128 */           double d7 = (this.p[(i6 + i9 + 1)] - d3) * d1;
/* 129 */           double d8 = (this.p[(i7 + i9 + 1)] - d4) * d1;
/* 130 */           double d9 = (this.p[(i8 + i9 + 1)] - d5) * d1;
/*     */           
/* 132 */           for (int i10 = 0; i10 < 8; i10++) {
/* 133 */             double d10 = 0.25D;
/*     */             
/* 135 */             double d11 = d2;
/* 136 */             double d12 = d3;
/* 137 */             double d13 = (d4 - d2) * d10;
/* 138 */             double d14 = (d5 - d3) * d10;
/*     */             
/* 140 */             for (int i11 = 0; i11 < 4; i11++) {
/* 141 */               double d15 = 0.25D;
/*     */               
/* 143 */               double d16 = d11;
/* 144 */               double d17 = (d12 - d11) * d15;
/* 145 */               d16 -= d17;
/* 146 */               for (int i12 = 0; i12 < 4; i12++) {
/* 147 */                 if (d16 += d17 > 0.0D) {
/* 148 */                   paramChunkSnapshot.a(i1 * 4 + i11, i9 * 8 + i10, i4 * 4 + i12, Blocks.STONE.getBlockData());
/* 149 */                 } else if (i9 * 8 + i10 < this.r.q) {
/* 150 */                   paramChunkSnapshot.a(i1 * 4 + i11, i9 * 8 + i10, i4 * 4 + i12, this.s.getBlockData());
/*     */                 }
/*     */               }
/* 153 */               d11 += d13;
/* 154 */               d12 += d14;
/*     */             }
/*     */             
/* 157 */             d2 += d6;
/* 158 */             d3 += d7;
/* 159 */             d4 += d8;
/* 160 */             d5 += d9;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 167 */   private double[] t = new double['Ā'];
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, BiomeBase[] paramArrayOfBiomeBase) {
/* 170 */     double d1 = 0.03125D;
/* 171 */     this.t = this.l.a(this.t, paramInt1 * 16, paramInt2 * 16, 16, 16, d1 * 2.0D, d1 * 2.0D, 1.0D);
/*     */     
/* 173 */     for (int i1 = 0; i1 < 16; i1++) {
/* 174 */       for (int i2 = 0; i2 < 16; i2++) {
/* 175 */         BiomeBase localBiomeBase = paramArrayOfBiomeBase[(i2 + i1 * 16)];
/* 176 */         localBiomeBase.a(this.m, this.h, paramChunkSnapshot, paramInt1 * 16 + i1, paramInt2 * 16 + i2, this.t[(i2 + i1 * 16)]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 181 */   private WorldGenBase u = new WorldGenCaves();
/* 182 */   private WorldGenStronghold v = new WorldGenStronghold();
/* 183 */   private WorldGenVillage w = new WorldGenVillage();
/* 184 */   private WorldGenMineshaft x = new WorldGenMineshaft();
/* 185 */   private WorldGenLargeFeature y = new WorldGenLargeFeature();
/* 186 */   private WorldGenBase z = new WorldGenCanyon();
/* 187 */   private WorldGenMonument A = new WorldGenMonument();
/*     */   
/*     */   private BiomeBase[] B;
/*     */   
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/* 198 */     this.h.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
/*     */     
/* 200 */     ChunkSnapshot localChunkSnapshot = new ChunkSnapshot();
/*     */     
/* 202 */     a(paramInt1, paramInt2, localChunkSnapshot);
/* 203 */     this.B = this.m.getWorldChunkManager().getBiomeBlock(this.B, paramInt1 * 16, paramInt2 * 16, 16, 16);
/* 204 */     a(paramInt1, paramInt2, localChunkSnapshot, this.B);
/*     */     
/* 206 */     if (this.r.r) {
/* 207 */       this.u.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 209 */     if (this.r.z) {
/* 210 */       this.z.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 212 */     if ((this.r.w) && (this.n)) {
/* 213 */       this.x.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 215 */     if ((this.r.v) && (this.n)) {
/* 216 */       this.w.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 218 */     if ((this.r.u) && (this.n)) {
/* 219 */       this.v.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 221 */     if ((this.r.x) && (this.n)) {
/* 222 */       this.y.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/* 224 */     if ((this.r.y) && (this.n)) {
/* 225 */       this.A.a(this, this.m, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/*     */     
/* 228 */     Chunk localChunk = new Chunk(this.m, localChunkSnapshot, paramInt1, paramInt2);
/* 229 */     byte[] arrayOfByte = localChunk.getBiomeIndex();
/*     */     
/* 231 */     for (int i1 = 0; i1 < arrayOfByte.length; i1++) {
/* 232 */       arrayOfByte[i1] = ((byte)this.B[i1].id);
/*     */     }
/*     */     
/* 235 */     localChunk.initLighting();
/*     */     
/* 237 */     return localChunk;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void a(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 248 */     this.g = this.b.a(this.g, paramInt1, paramInt3, 5, 5, this.r.e, this.r.f, this.r.g);
/*     */     
/* 250 */     float f1 = this.r.a;
/* 251 */     float f2 = this.r.b;
/* 252 */     this.d = this.k.a(this.d, paramInt1, paramInt2, paramInt3, 5, 33, 5, f1 / this.r.h, f2 / this.r.i, f1 / this.r.j);
/* 253 */     this.e = this.i.a(this.e, paramInt1, paramInt2, paramInt3, 5, 33, 5, f1, f2, f1);
/* 254 */     this.f = this.j.a(this.f, paramInt1, paramInt2, paramInt3, 5, 33, 5, f1, f2, f1);
/* 255 */     paramInt1 = paramInt3 = 0;
/*     */     
/* 257 */     int i1 = 0;
/* 258 */     int i2 = 0;
/*     */     
/* 260 */     for (int i3 = 0; i3 < 5; i3++) {
/* 261 */       for (int i4 = 0; i4 < 5; i4++) {
/* 262 */         float f3 = 0.0F;
/* 263 */         float f4 = 0.0F;
/* 264 */         float f5 = 0.0F;
/*     */         
/* 266 */         int i5 = 2;
/*     */         
/*     */ 
/*     */ 
/* 270 */         BiomeBase localBiomeBase1 = this.B[(i3 + 2 + (i4 + 2) * 10)];
/* 271 */         for (int i6 = -i5; i6 <= i5; i6++) {
/* 272 */           for (int i7 = -i5; i7 <= i5; i7++) {
/* 273 */             BiomeBase localBiomeBase2 = this.B[(i3 + i6 + 2 + (i4 + i7 + 2) * 10)];
/* 274 */             float f6 = this.r.n + localBiomeBase2.an * this.r.m;
/* 275 */             float f7 = this.r.p + localBiomeBase2.ao * this.r.o;
/* 276 */             if ((this.o == WorldType.AMPLIFIED) && (f6 > 0.0F)) {
/* 277 */               f6 = 1.0F + f6 * 2.0F;
/* 278 */               f7 = 1.0F + f7 * 4.0F;
/*     */             }
/* 280 */             float f8 = this.q[(i6 + 2 + (i7 + 2) * 5)] / (f6 + 2.0F);
/* 281 */             if (localBiomeBase2.an > localBiomeBase1.an) {
/* 282 */               f8 /= 2.0F;
/*     */             }
/* 284 */             f3 += f7 * f8;
/* 285 */             f4 += f6 * f8;
/* 286 */             f5 += f8;
/*     */           }
/*     */         }
/* 289 */         f3 /= f5;
/* 290 */         f4 /= f5;
/*     */         
/* 292 */         f3 = f3 * 0.9F + 0.1F;
/* 293 */         f4 = (f4 * 4.0F - 1.0F) / 8.0F;
/*     */         
/* 295 */         double d1 = this.g[i2] / 8000.0D;
/* 296 */         if (d1 < 0.0D) {
/* 297 */           d1 = -d1 * 0.3D;
/*     */         }
/* 299 */         d1 = d1 * 3.0D - 2.0D;
/*     */         
/* 301 */         if (d1 < 0.0D) {
/* 302 */           d1 /= 2.0D;
/* 303 */           if (d1 < -1.0D) {
/* 304 */             d1 = -1.0D;
/*     */           }
/* 306 */           d1 /= 1.4D;
/* 307 */           d1 /= 2.0D;
/*     */         } else {
/* 309 */           if (d1 > 1.0D) {
/* 310 */             d1 = 1.0D;
/*     */           }
/* 312 */           d1 /= 8.0D;
/*     */         }
/*     */         
/* 315 */         i2++;
/*     */         
/* 317 */         double d2 = f4;
/* 318 */         double d3 = f3;
/* 319 */         d2 += d1 * 0.2D;
/* 320 */         d2 = d2 * this.r.k / 8.0D;
/*     */         
/* 322 */         double d4 = this.r.k + d2 * 4.0D;
/*     */         
/* 324 */         for (int i8 = 0; i8 < 33; i8++) {
/* 325 */           double d5 = (i8 - d4) * this.r.l * 128.0D / 256.0D / d3;
/*     */           
/* 327 */           if (d5 < 0.0D) {
/* 328 */             d5 *= 4.0D;
/*     */           }
/*     */           
/* 331 */           double d6 = this.e[i1] / this.r.d;
/* 332 */           double d7 = this.f[i1] / this.r.c;
/*     */           
/* 334 */           double d8 = (this.d[i1] / 10.0D + 1.0D) / 2.0D;
/* 335 */           double d9 = MathHelper.b(d6, d7, d8) - d5;
/*     */           
/*     */ 
/*     */ 
/* 339 */           if (i8 > 29) {
/* 340 */             double d10 = (i8 - 29) / 3.0F;
/* 341 */             d9 = d9 * (1.0D - d10) + -10.0D * d10;
/*     */           }
/*     */           
/* 344 */           this.p[i1] = d9;
/* 345 */           i1++;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int paramInt1, int paramInt2)
/*     */   {
/* 353 */     return true;
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
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
/*     */   {
/* 411 */     BlockFalling.instaFall = true;
/* 412 */     int i1 = paramInt1 * 16;
/* 413 */     int i2 = paramInt2 * 16;
/* 414 */     BlockPosition localBlockPosition1 = new BlockPosition(i1, 0, i2);
/* 415 */     BiomeBase localBiomeBase = this.m.getBiome(localBlockPosition1.a(16, 0, 16));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 421 */     this.h.setSeed(this.m.getSeed());
/* 422 */     long l1 = this.h.nextLong() / 2L * 2L + 1L;
/* 423 */     long l2 = this.h.nextLong() / 2L * 2L + 1L;
/* 424 */     this.h.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.m.getSeed());
/*     */     
/* 426 */     boolean bool = false;
/*     */     
/* 428 */     ChunkCoordIntPair localChunkCoordIntPair = new ChunkCoordIntPair(paramInt1, paramInt2);
/*     */     
/* 430 */     if ((this.r.w) && (this.n)) {
/* 431 */       this.x.a(this.m, this.h, localChunkCoordIntPair);
/*     */     }
/* 433 */     if ((this.r.v) && (this.n)) {
/* 434 */       bool = this.w.a(this.m, this.h, localChunkCoordIntPair);
/*     */     }
/* 436 */     if ((this.r.u) && (this.n)) {
/* 437 */       this.v.a(this.m, this.h, localChunkCoordIntPair);
/*     */     }
/* 439 */     if ((this.r.x) && (this.n)) {
/* 440 */       this.y.a(this.m, this.h, localChunkCoordIntPair);
/*     */     }
/* 442 */     if ((this.r.y) && (this.n))
/* 443 */       this.A.a(this.m, this.h, localChunkCoordIntPair);
/*     */     int i4;
/*     */     int i5;
/* 446 */     if ((localBiomeBase != BiomeBase.DESERT) && (localBiomeBase != BiomeBase.DESERT_HILLS) && (this.r.A) && 
/* 447 */       (!bool) && (this.h.nextInt(this.r.B) == 0)) {
/* 448 */       i3 = this.h.nextInt(16) + 8;
/* 449 */       i4 = this.h.nextInt(256);
/* 450 */       i5 = this.h.nextInt(16) + 8;
/* 451 */       new WorldGenLakes(Blocks.WATER).generate(this.m, this.h, localBlockPosition1.a(i3, i4, i5));
/*     */     }
/*     */     
/*     */ 
/* 455 */     if ((!bool) && (this.h.nextInt(this.r.D / 10) == 0) && (this.r.C)) {
/* 456 */       i3 = this.h.nextInt(16) + 8;
/* 457 */       i4 = this.h.nextInt(this.h.nextInt(248) + 8);
/* 458 */       i5 = this.h.nextInt(16) + 8;
/* 459 */       if ((i4 < this.m.F()) || (this.h.nextInt(this.r.D / 8) == 0)) {
/* 460 */         new WorldGenLakes(Blocks.LAVA).generate(this.m, this.h, localBlockPosition1.a(i3, i4, i5));
/*     */       }
/*     */     }
/*     */     
/* 464 */     if (this.r.s) {
/* 465 */       for (i3 = 0; i3 < this.r.t; i3++) {
/* 466 */         i4 = this.h.nextInt(16) + 8;
/* 467 */         i5 = this.h.nextInt(256);
/* 468 */         int i6 = this.h.nextInt(16) + 8;
/* 469 */         new WorldGenDungeons().generate(this.m, this.h, localBlockPosition1.a(i4, i5, i6));
/*     */       }
/*     */     }
/*     */     
/* 473 */     localBiomeBase.a(this.m, this.h, new BlockPosition(i1, 0, i2));
/*     */     
/* 475 */     SpawnerCreature.a(this.m, localBiomeBase, i1 + 8, i2 + 8, 16, 16, this.h);
/*     */     
/* 477 */     localBlockPosition1 = localBlockPosition1.a(8, 0, 8);
/* 478 */     for (int i3 = 0; i3 < 16; i3++) {
/* 479 */       for (i4 = 0; i4 < 16; i4++) {
/* 480 */         BlockPosition localBlockPosition2 = this.m.q(localBlockPosition1.a(i3, 0, i4));
/* 481 */         BlockPosition localBlockPosition3 = localBlockPosition2.down();
/*     */         
/* 483 */         if (this.m.v(localBlockPosition3)) {
/* 484 */           this.m.setTypeAndData(localBlockPosition3, Blocks.ICE.getBlockData(), 2);
/*     */         }
/* 486 */         if (this.m.f(localBlockPosition2, true)) {
/* 487 */           this.m.setTypeAndData(localBlockPosition2, Blocks.SNOW_LAYER.getBlockData(), 2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 492 */     BlockFalling.instaFall = false;
/*     */   }
/*     */   
/*     */   public boolean a(IChunkProvider paramIChunkProvider, Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 497 */     boolean bool = false;
/* 498 */     if ((this.r.y) && (this.n))
/*     */     {
/* 500 */       if (paramChunk.w() < 3600L)
/*     */       {
/* 502 */         bool |= this.A.a(this.m, this.h, new ChunkCoordIntPair(paramInt1, paramInt2));
/*     */       }
/*     */     }
/*     */     
/* 506 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 511 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c() {}
/*     */   
/*     */ 
/*     */   public boolean unloadChunks()
/*     */   {
/* 520 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave()
/*     */   {
/* 525 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 530 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType paramEnumCreatureType, BlockPosition paramBlockPosition)
/*     */   {
/* 535 */     BiomeBase localBiomeBase = this.m.getBiome(paramBlockPosition);
/* 536 */     if (this.n) {
/* 537 */       if ((paramEnumCreatureType == EnumCreatureType.MONSTER) && (this.y.a(paramBlockPosition)))
/* 538 */         return this.y.b();
/* 539 */       if ((paramEnumCreatureType == EnumCreatureType.MONSTER) && (this.r.y) && (this.A.a(this.m, paramBlockPosition))) {
/* 540 */         return this.A.b();
/*     */       }
/*     */     }
/* 543 */     return localBiomeBase.getMobs(paramEnumCreatureType);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(World paramWorld, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 548 */     if (("Stronghold".equals(paramString)) && (this.v != null)) {
/* 549 */       return this.v.getNearestGeneratedFeature(paramWorld, paramBlockPosition);
/*     */     }
/* 551 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks()
/*     */   {
/* 556 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 561 */     if ((this.r.w) && (this.n)) {
/* 562 */       this.x.a(this, this.m, paramInt1, paramInt2, null);
/*     */     }
/* 564 */     if ((this.r.v) && (this.n)) {
/* 565 */       this.w.a(this, this.m, paramInt1, paramInt2, null);
/*     */     }
/* 567 */     if ((this.r.u) && (this.n)) {
/* 568 */       this.v.a(this, this.m, paramInt1, paramInt2, null);
/*     */     }
/* 570 */     if ((this.r.x) && (this.n)) {
/* 571 */       this.y.a(this, this.m, paramInt1, paramInt2, null);
/*     */     }
/* 573 */     if ((this.r.y) && (this.n)) {
/* 574 */       this.A.a(this, this.m, paramInt1, paramInt2, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(BlockPosition paramBlockPosition)
/*     */   {
/* 580 */     return getOrCreateChunk(paramBlockPosition.getX() >> 4, paramBlockPosition.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkProviderGenerate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */