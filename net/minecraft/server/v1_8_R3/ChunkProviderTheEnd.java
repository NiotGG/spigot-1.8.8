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
/*     */ public class ChunkProviderTheEnd
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random h;
/*     */   private NoiseGeneratorOctaves i;
/*     */   private NoiseGeneratorOctaves j;
/*     */   private NoiseGeneratorOctaves k;
/*     */   public NoiseGeneratorOctaves a;
/*     */   public NoiseGeneratorOctaves b;
/*     */   private World l;
/*     */   private double[] m;
/*     */   private BiomeBase[] n;
/*     */   double[] c;
/*     */   double[] d;
/*     */   double[] e;
/*     */   double[] f;
/*     */   double[] g;
/*     */   
/*     */   public ChunkProviderTheEnd(World paramWorld, long paramLong)
/*     */   {
/*  35 */     this.l = paramWorld;
/*     */     
/*  37 */     this.h = new Random(paramLong);
/*  38 */     this.i = new NoiseGeneratorOctaves(this.h, 16);
/*  39 */     this.j = new NoiseGeneratorOctaves(this.h, 16);
/*  40 */     this.k = new NoiseGeneratorOctaves(this.h, 8);
/*     */     
/*  42 */     this.a = new NoiseGeneratorOctaves(this.h, 10);
/*  43 */     this.b = new NoiseGeneratorOctaves(this.h, 16);
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot)
/*     */   {
/*  49 */     int i1 = 2;
/*     */     
/*  51 */     int i2 = i1 + 1;
/*  52 */     int i3 = 33;
/*  53 */     int i4 = i1 + 1;
/*  54 */     this.m = a(this.m, paramInt1 * i1, 0, paramInt2 * i1, i2, i3, i4);
/*     */     
/*  56 */     for (int i5 = 0; i5 < i1; i5++) {
/*  57 */       for (int i6 = 0; i6 < i1; i6++) {
/*  58 */         for (int i7 = 0; i7 < 32; i7++) {
/*  59 */           double d1 = 0.25D;
/*  60 */           double d2 = this.m[(((i5 + 0) * i4 + i6 + 0) * i3 + i7 + 0)];
/*  61 */           double d3 = this.m[(((i5 + 0) * i4 + i6 + 1) * i3 + i7 + 0)];
/*  62 */           double d4 = this.m[(((i5 + 1) * i4 + i6 + 0) * i3 + i7 + 0)];
/*  63 */           double d5 = this.m[(((i5 + 1) * i4 + i6 + 1) * i3 + i7 + 0)];
/*     */           
/*  65 */           double d6 = (this.m[(((i5 + 0) * i4 + i6 + 0) * i3 + i7 + 1)] - d2) * d1;
/*  66 */           double d7 = (this.m[(((i5 + 0) * i4 + i6 + 1) * i3 + i7 + 1)] - d3) * d1;
/*  67 */           double d8 = (this.m[(((i5 + 1) * i4 + i6 + 0) * i3 + i7 + 1)] - d4) * d1;
/*  68 */           double d9 = (this.m[(((i5 + 1) * i4 + i6 + 1) * i3 + i7 + 1)] - d5) * d1;
/*     */           
/*  70 */           for (int i8 = 0; i8 < 4; i8++) {
/*  71 */             double d10 = 0.125D;
/*     */             
/*  73 */             double d11 = d2;
/*  74 */             double d12 = d3;
/*  75 */             double d13 = (d4 - d2) * d10;
/*  76 */             double d14 = (d5 - d3) * d10;
/*     */             
/*  78 */             for (int i9 = 0; i9 < 8; i9++) {
/*  79 */               double d15 = 0.125D;
/*     */               
/*  81 */               double d16 = d11;
/*  82 */               double d17 = (d12 - d11) * d15;
/*  83 */               for (int i10 = 0; i10 < 8; i10++) {
/*  84 */                 IBlockData localIBlockData = null;
/*  85 */                 if (d16 > 0.0D) {
/*  86 */                   localIBlockData = Blocks.END_STONE.getBlockData();
/*     */                 }
/*     */                 
/*  89 */                 int i11 = i9 + i5 * 8;
/*  90 */                 int i12 = i8 + i7 * 4;
/*  91 */                 int i13 = i10 + i6 * 8;
/*  92 */                 paramChunkSnapshot.a(i11, i12, i13, localIBlockData);
/*  93 */                 d16 += d17;
/*     */               }
/*  95 */               d11 += d13;
/*  96 */               d12 += d14;
/*     */             }
/*     */             
/*  99 */             d2 += d6;
/* 100 */             d3 += d7;
/* 101 */             d4 += d8;
/* 102 */             d5 += d9;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(ChunkSnapshot paramChunkSnapshot) {
/* 110 */     for (int i1 = 0; i1 < 16; i1++) {
/* 111 */       for (int i2 = 0; i2 < 16; i2++) {
/* 112 */         int i3 = 1;
/* 113 */         int i4 = -1;
/*     */         
/* 115 */         IBlockData localIBlockData1 = Blocks.END_STONE.getBlockData();
/* 116 */         IBlockData localIBlockData2 = Blocks.END_STONE.getBlockData();
/*     */         
/* 118 */         for (int i5 = 127; i5 >= 0; i5--) {
/* 119 */           IBlockData localIBlockData3 = paramChunkSnapshot.a(i1, i5, i2);
/* 120 */           if (localIBlockData3.getBlock().getMaterial() == Material.AIR) {
/* 121 */             i4 = -1;
/* 122 */           } else if (localIBlockData3.getBlock() == Blocks.STONE) {
/* 123 */             if (i4 == -1) {
/* 124 */               if (i3 <= 0) {
/* 125 */                 localIBlockData1 = Blocks.AIR.getBlockData();
/* 126 */                 localIBlockData2 = Blocks.END_STONE.getBlockData();
/*     */               }
/*     */               
/* 129 */               i4 = i3;
/* 130 */               if (i5 >= 0) {
/* 131 */                 paramChunkSnapshot.a(i1, i5, i2, localIBlockData1);
/*     */               } else {
/* 133 */                 paramChunkSnapshot.a(i1, i5, i2, localIBlockData2);
/*     */               }
/* 135 */             } else if (i4 > 0) {
/* 136 */               i4--;
/* 137 */               paramChunkSnapshot.a(i1, i5, i2, localIBlockData2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/* 154 */     this.h.setSeed(paramInt1 * 341873128712L + paramInt2 * 132897987541L);
/*     */     
/* 156 */     ChunkSnapshot localChunkSnapshot = new ChunkSnapshot();
/* 157 */     this.n = this.l.getWorldChunkManager().getBiomeBlock(this.n, paramInt1 * 16, paramInt2 * 16, 16, 16);
/*     */     
/* 159 */     a(paramInt1, paramInt2, localChunkSnapshot);
/* 160 */     a(localChunkSnapshot);
/*     */     
/* 162 */     Chunk localChunk = new Chunk(this.l, localChunkSnapshot, paramInt1, paramInt2);
/* 163 */     byte[] arrayOfByte = localChunk.getBiomeIndex();
/*     */     
/* 165 */     for (int i1 = 0; i1 < arrayOfByte.length; i1++) {
/* 166 */       arrayOfByte[i1] = ((byte)this.n[i1].id);
/*     */     }
/*     */     
/* 169 */     localChunk.initLighting();
/*     */     
/* 171 */     return localChunk;
/*     */   }
/*     */   
/*     */ 
/*     */   private double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 177 */     if (paramArrayOfDouble == null) {
/* 178 */       paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
/*     */     }
/*     */     
/* 181 */     double d1 = 684.412D;
/* 182 */     double d2 = 684.412D;
/*     */     
/* 184 */     this.f = this.a.a(this.f, paramInt1, paramInt3, paramInt4, paramInt6, 1.121D, 1.121D, 0.5D);
/* 185 */     this.g = this.b.a(this.g, paramInt1, paramInt3, paramInt4, paramInt6, 200.0D, 200.0D, 0.5D);
/*     */     
/* 187 */     d1 *= 2.0D;
/*     */     
/* 189 */     this.c = this.k.a(this.c, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1 / 80.0D, d2 / 160.0D, d1 / 80.0D);
/* 190 */     this.d = this.i.a(this.d, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/* 191 */     this.e = this.j.a(this.e, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, d1, d2, d1);
/*     */     
/* 193 */     int i1 = 0;
/*     */     
/* 195 */     for (int i2 = 0; i2 < paramInt4; i2++) {
/* 196 */       for (int i3 = 0; i3 < paramInt6; i3++) {
/* 197 */         float f1 = (i2 + paramInt1) / 1.0F;
/* 198 */         float f2 = (i3 + paramInt3) / 1.0F;
/* 199 */         float f3 = 100.0F - MathHelper.c(f1 * f1 + f2 * f2) * 8.0F;
/* 200 */         if (f3 > 80.0F) {
/* 201 */           f3 = 80.0F;
/*     */         }
/* 203 */         if (f3 < -100.0F) {
/* 204 */           f3 = -100.0F;
/*     */         }
/*     */         
/* 207 */         for (int i4 = 0; i4 < paramInt5; i4++) {
/* 208 */           double d3 = 0.0D;
/* 209 */           double d4 = this.d[i1] / 512.0D;
/* 210 */           double d5 = this.e[i1] / 512.0D;
/*     */           
/* 212 */           double d6 = (this.c[i1] / 10.0D + 1.0D) / 2.0D;
/* 213 */           if (d6 < 0.0D) {
/* 214 */             d3 = d4;
/* 215 */           } else if (d6 > 1.0D) {
/* 216 */             d3 = d5;
/*     */           } else {
/* 218 */             d3 = d4 + (d5 - d4) * d6;
/*     */           }
/* 220 */           d3 -= 8.0D;
/*     */           
/* 222 */           d3 += f3;
/*     */           
/* 224 */           int i5 = 2;
/* 225 */           double d7; if (i4 > paramInt5 / 2 - i5) {
/* 226 */             d7 = (i4 - (paramInt5 / 2 - i5)) / 64.0F;
/* 227 */             d7 = MathHelper.a(d7, 0.0D, 1.0D);
/* 228 */             d3 = d3 * (1.0D - d7) + -3000.0D * d7;
/*     */           }
/* 230 */           i5 = 8;
/* 231 */           if (i4 < i5) {
/* 232 */             d7 = (i5 - i4) / (i5 - 1.0F);
/* 233 */             d3 = d3 * (1.0D - d7) + -30.0D * d7;
/*     */           }
/*     */           
/* 236 */           paramArrayOfDouble[i1] = d3;
/* 237 */           i1++;
/*     */         }
/*     */       }
/*     */     }
/* 241 */     return paramArrayOfDouble;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int paramInt1, int paramInt2)
/*     */   {
/* 246 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
/*     */   {
/* 251 */     BlockFalling.instaFall = true;
/*     */     
/* 253 */     BlockPosition localBlockPosition = new BlockPosition(paramInt1 * 16, 0, paramInt2 * 16);
/* 254 */     this.l.getBiome(localBlockPosition.a(16, 0, 16)).a(this.l, this.l.random, localBlockPosition);
/*     */     
/* 256 */     BlockFalling.instaFall = false;
/*     */   }
/*     */   
/*     */   public boolean a(IChunkProvider paramIChunkProvider, Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 261 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 266 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c() {}
/*     */   
/*     */ 
/*     */   public boolean unloadChunks()
/*     */   {
/* 275 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave()
/*     */   {
/* 280 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 285 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType paramEnumCreatureType, BlockPosition paramBlockPosition)
/*     */   {
/* 290 */     return this.l.getBiome(paramBlockPosition).getMobs(paramEnumCreatureType);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(World paramWorld, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 295 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks()
/*     */   {
/* 300 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2) {}
/*     */   
/*     */ 
/*     */   public Chunk getChunkAt(BlockPosition paramBlockPosition)
/*     */   {
/* 309 */     return getOrCreateChunk(paramBlockPosition.getX() >> 4, paramBlockPosition.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkProviderTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */