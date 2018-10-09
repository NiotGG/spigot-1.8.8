/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenCaves
/*     */   extends WorldGenBase
/*     */ {
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  16 */     a(paramLong, paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */   
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  20 */     double d1 = paramInt1 * 16 + 8;
/*  21 */     double d2 = paramInt2 * 16 + 8;
/*     */     
/*  23 */     float f1 = 0.0F;
/*  24 */     float f2 = 0.0F;
/*  25 */     Random localRandom = new Random(paramLong);
/*     */     
/*  27 */     if (paramInt4 <= 0) {
/*  28 */       i = this.a * 16 - 16;
/*  29 */       paramInt4 = i - localRandom.nextInt(i / 4);
/*     */     }
/*  31 */     int i = 0;
/*     */     
/*  33 */     if (paramInt3 == -1) {
/*  34 */       paramInt3 = paramInt4 / 2;
/*  35 */       i = 1;
/*     */     }
/*     */     
/*  38 */     int j = localRandom.nextInt(paramInt4 / 2) + paramInt4 / 4;
/*  39 */     int k = localRandom.nextInt(6) == 0 ? 1 : 0;
/*  41 */     for (; 
/*  41 */         paramInt3 < paramInt4; paramInt3++) {
/*  42 */       double d3 = 1.5D + MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F;
/*  43 */       double d4 = d3 * paramDouble4;
/*     */       
/*  45 */       float f3 = MathHelper.cos(paramFloat3);
/*  46 */       float f4 = MathHelper.sin(paramFloat3);
/*  47 */       paramDouble1 += MathHelper.cos(paramFloat2) * f3;
/*  48 */       paramDouble2 += f4;
/*  49 */       paramDouble3 += MathHelper.sin(paramFloat2) * f3;
/*     */       
/*  51 */       if (k != 0) {
/*  52 */         paramFloat3 *= 0.92F;
/*     */       } else {
/*  54 */         paramFloat3 *= 0.7F;
/*     */       }
/*  56 */       paramFloat3 += f2 * 0.1F;
/*  57 */       paramFloat2 += f1 * 0.1F;
/*     */       
/*  59 */       f2 *= 0.9F;
/*  60 */       f1 *= 0.75F;
/*  61 */       f2 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 2.0F;
/*  62 */       f1 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 4.0F;
/*     */       
/*  64 */       if ((i == 0) && (paramInt3 == j) && (paramFloat1 > 1.0F) && (paramInt4 > 0)) {
/*  65 */         a(localRandom.nextLong(), paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  66 */         a(localRandom.nextLong(), paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  67 */         return;
/*     */       }
/*  69 */       if ((i != 0) || (localRandom.nextInt(4) != 0))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  74 */         double d5 = paramDouble1 - d1;
/*  75 */         double d6 = paramDouble3 - d2;
/*  76 */         double d7 = paramInt4 - paramInt3;
/*  77 */         double d8 = paramFloat1 + 2.0F + 16.0F;
/*  78 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*  79 */           return;
/*     */         }
/*     */         
/*     */ 
/*  83 */         if ((paramDouble1 >= d1 - 16.0D - d3 * 2.0D) && (paramDouble3 >= d2 - 16.0D - d3 * 2.0D) && (paramDouble1 <= d1 + 16.0D + d3 * 2.0D) && (paramDouble3 <= d2 + 16.0D + d3 * 2.0D))
/*     */         {
/*     */ 
/*     */ 
/*  87 */           int m = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  88 */           int n = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  90 */           int i1 = MathHelper.floor(paramDouble2 - d4) - 1;
/*  91 */           int i2 = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  93 */           int i3 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  94 */           int i4 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  96 */           if (m < 0) {
/*  97 */             m = 0;
/*     */           }
/*  99 */           if (n > 16) {
/* 100 */             n = 16;
/*     */           }
/*     */           
/* 103 */           if (i1 < 1) {
/* 104 */             i1 = 1;
/*     */           }
/* 106 */           if (i2 > 248) {
/* 107 */             i2 = 248;
/*     */           }
/*     */           
/* 110 */           if (i3 < 0) {
/* 111 */             i3 = 0;
/*     */           }
/* 113 */           if (i4 > 16) {
/* 114 */             i4 = 16;
/*     */           }
/*     */           
/* 117 */           int i5 = 0;
/* 118 */           int i7; for (int i6 = m; (i5 == 0) && (i6 < n); i6++) {
/* 119 */             for (i7 = i3; (i5 == 0) && (i7 < i4); i7++) {
/* 120 */               for (int i8 = i2 + 1; (i5 == 0) && (i8 >= i1 - 1); i8--) {
/* 121 */                 if ((i8 >= 0) && (i8 < 256))
/*     */                 {
/*     */ 
/* 124 */                   IBlockData localIBlockData1 = paramChunkSnapshot.a(i6, i8, i7);
/* 125 */                   if ((localIBlockData1.getBlock() == Blocks.FLOWING_WATER) || (localIBlockData1.getBlock() == Blocks.WATER)) {
/* 126 */                     i5 = 1;
/*     */                   }
/* 128 */                   if ((i8 != i1 - 1) && (i6 != m) && (i6 != n - 1) && (i7 != i3) && (i7 != i4 - 1))
/* 129 */                     i8 = i1;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 134 */           if (i5 == 0)
/*     */           {
/*     */ 
/*     */ 
/* 138 */             BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 139 */             for (i7 = m; i7 < n; i7++) {
/* 140 */               double d9 = (i7 + paramInt1 * 16 + 0.5D - paramDouble1) / d3;
/* 141 */               for (int i9 = i3; i9 < i4; i9++) {
/* 142 */                 double d10 = (i9 + paramInt2 * 16 + 0.5D - paramDouble3) / d3;
/* 143 */                 int i10 = 0;
/* 144 */                 if (d9 * d9 + d10 * d10 < 1.0D) {
/* 145 */                   for (int i11 = i2; i11 > i1; i11--) {
/* 146 */                     double d11 = (i11 - 1 + 0.5D - paramDouble2) / d4;
/* 147 */                     if ((d11 > -0.7D) && (d9 * d9 + d11 * d11 + d10 * d10 < 1.0D)) {
/* 148 */                       IBlockData localIBlockData2 = paramChunkSnapshot.a(i7, i11, i9);
/* 149 */                       IBlockData localIBlockData3 = (IBlockData)Objects.firstNonNull(paramChunkSnapshot.a(i7, i11 + 1, i9), Blocks.AIR.getBlockData());
/* 150 */                       if ((localIBlockData2.getBlock() == Blocks.GRASS) || (localIBlockData2.getBlock() == Blocks.MYCELIUM)) {
/* 151 */                         i10 = 1;
/*     */                       }
/* 153 */                       if (a(localIBlockData2, localIBlockData3)) {
/* 154 */                         if (i11 - 1 < 10) {
/* 155 */                           paramChunkSnapshot.a(i7, i11, i9, Blocks.LAVA.getBlockData());
/*     */                         } else {
/* 157 */                           paramChunkSnapshot.a(i7, i11, i9, Blocks.AIR.getBlockData());
/* 158 */                           if (localIBlockData3.getBlock() == Blocks.SAND) {
/* 159 */                             paramChunkSnapshot.a(i7, i11 + 1, i9, localIBlockData3.get(BlockSand.VARIANT) == BlockSand.EnumSandVariant.RED_SAND ? Blocks.RED_SANDSTONE.getBlockData() : Blocks.SANDSTONE.getBlockData());
/*     */                           }
/* 161 */                           if ((i10 != 0) && (paramChunkSnapshot.a(i7, i11 - 1, i9).getBlock() == Blocks.DIRT)) {
/* 162 */                             localMutableBlockPosition.c(i7 + paramInt1 * 16, 0, i9 + paramInt2 * 16);
/* 163 */                             paramChunkSnapshot.a(i7, i11 - 1, i9, this.c.getBiome(localMutableBlockPosition).ak.getBlock().getBlockData());
/*     */                           }
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 172 */             if (i != 0) break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 179 */   protected boolean a(IBlockData paramIBlockData1, IBlockData paramIBlockData2) { if (paramIBlockData1.getBlock() == Blocks.STONE) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (paramIBlockData1.getBlock() == Blocks.DIRT) {
/* 183 */       return true;
/*     */     }
/* 185 */     if (paramIBlockData1.getBlock() == Blocks.GRASS) {
/* 186 */       return true;
/*     */     }
/* 188 */     if (paramIBlockData1.getBlock() == Blocks.HARDENED_CLAY) {
/* 189 */       return true;
/*     */     }
/* 191 */     if (paramIBlockData1.getBlock() == Blocks.STAINED_HARDENED_CLAY) {
/* 192 */       return true;
/*     */     }
/* 194 */     if (paramIBlockData1.getBlock() == Blocks.SANDSTONE) {
/* 195 */       return true;
/*     */     }
/* 197 */     if (paramIBlockData1.getBlock() == Blocks.RED_SANDSTONE) {
/* 198 */       return true;
/*     */     }
/* 200 */     if (paramIBlockData1.getBlock() == Blocks.MYCELIUM) {
/* 201 */       return true;
/*     */     }
/* 203 */     if (paramIBlockData1.getBlock() == Blocks.SNOW_LAYER) {
/* 204 */       return true;
/*     */     }
/* 206 */     if (((paramIBlockData1.getBlock() == Blocks.SAND) || (paramIBlockData1.getBlock() == Blocks.GRAVEL)) && (paramIBlockData2.getBlock().getMaterial() != Material.WATER)) {
/* 207 */       return true;
/*     */     }
/*     */     
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ChunkSnapshot paramChunkSnapshot)
/*     */   {
/* 215 */     int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(15) + 1) + 1);
/* 216 */     if (this.b.nextInt(7) != 0) {
/* 217 */       i = 0;
/*     */     }
/*     */     
/* 220 */     for (int j = 0; j < i; j++) {
/* 221 */       double d1 = paramInt1 * 16 + this.b.nextInt(16);
/* 222 */       double d2 = this.b.nextInt(this.b.nextInt(120) + 8);
/* 223 */       double d3 = paramInt2 * 16 + this.b.nextInt(16);
/*     */       
/* 225 */       int k = 1;
/* 226 */       if (this.b.nextInt(4) == 0) {
/* 227 */         a(this.b.nextLong(), paramInt3, paramInt4, paramChunkSnapshot, d1, d2, d3);
/* 228 */         k += this.b.nextInt(4);
/*     */       }
/*     */       
/* 231 */       for (int m = 0; m < k; m++) {
/* 232 */         float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 233 */         float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 234 */         float f3 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
/* 235 */         if (this.b.nextInt(10) == 0) {
/* 236 */           f3 *= (this.b.nextFloat() * this.b.nextFloat() * 3.0F + 1.0F);
/*     */         }
/*     */         
/* 239 */         a(this.b.nextLong(), paramInt3, paramInt4, paramChunkSnapshot, d1, d2, d3, f3, f1, f2, 0, 0, 1.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenCaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */