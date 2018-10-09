/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenCanyon
/*     */   extends WorldGenBase
/*     */ {
/*  12 */   private float[] d = new float['Ѐ'];
/*     */   
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  15 */     Random localRandom = new Random(paramLong);
/*     */     
/*  17 */     double d1 = paramInt1 * 16 + 8;
/*  18 */     double d2 = paramInt2 * 16 + 8;
/*     */     
/*  20 */     float f1 = 0.0F;
/*  21 */     float f2 = 0.0F;
/*     */     
/*  23 */     if (paramInt4 <= 0) {
/*  24 */       i = this.a * 16 - 16;
/*  25 */       paramInt4 = i - localRandom.nextInt(i / 4);
/*     */     }
/*  27 */     int i = 0;
/*     */     
/*  29 */     if (paramInt3 == -1) {
/*  30 */       paramInt3 = paramInt4 / 2;
/*  31 */       i = 1;
/*     */     }
/*     */     
/*  34 */     float f3 = 1.0F;
/*  35 */     for (int j = 0; j < 256; j++) {
/*  36 */       if ((j == 0) || (localRandom.nextInt(3) == 0)) {
/*  37 */         f3 = 1.0F + localRandom.nextFloat() * localRandom.nextFloat() * 1.0F;
/*     */       }
/*  39 */       this.d[j] = (f3 * f3);
/*     */     }
/*  42 */     for (; 
/*  42 */         paramInt3 < paramInt4; paramInt3++) {
/*  43 */       double d3 = 1.5D + MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F;
/*  44 */       double d4 = d3 * paramDouble4;
/*     */       
/*  46 */       d3 *= (localRandom.nextFloat() * 0.25D + 0.75D);
/*  47 */       d4 *= (localRandom.nextFloat() * 0.25D + 0.75D);
/*     */       
/*  49 */       float f4 = MathHelper.cos(paramFloat3);
/*  50 */       float f5 = MathHelper.sin(paramFloat3);
/*  51 */       paramDouble1 += MathHelper.cos(paramFloat2) * f4;
/*  52 */       paramDouble2 += f5;
/*  53 */       paramDouble3 += MathHelper.sin(paramFloat2) * f4;
/*     */       
/*  55 */       paramFloat3 *= 0.7F;
/*     */       
/*  57 */       paramFloat3 += f2 * 0.05F;
/*  58 */       paramFloat2 += f1 * 0.05F;
/*     */       
/*  60 */       f2 *= 0.8F;
/*  61 */       f1 *= 0.5F;
/*  62 */       f2 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 2.0F;
/*  63 */       f1 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 4.0F;
/*     */       
/*  65 */       if ((i != 0) || (localRandom.nextInt(4) != 0))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  70 */         double d5 = paramDouble1 - d1;
/*  71 */         double d6 = paramDouble3 - d2;
/*  72 */         double d7 = paramInt4 - paramInt3;
/*  73 */         double d8 = paramFloat1 + 2.0F + 16.0F;
/*  74 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*  75 */           return;
/*     */         }
/*     */         
/*     */ 
/*  79 */         if ((paramDouble1 >= d1 - 16.0D - d3 * 2.0D) && (paramDouble3 >= d2 - 16.0D - d3 * 2.0D) && (paramDouble1 <= d1 + 16.0D + d3 * 2.0D) && (paramDouble3 <= d2 + 16.0D + d3 * 2.0D))
/*     */         {
/*     */ 
/*     */ 
/*  83 */           int k = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  84 */           int m = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  86 */           int n = MathHelper.floor(paramDouble2 - d4) - 1;
/*  87 */           int i1 = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  89 */           int i2 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  90 */           int i3 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  92 */           if (k < 0) {
/*  93 */             k = 0;
/*     */           }
/*  95 */           if (m > 16) {
/*  96 */             m = 16;
/*     */           }
/*     */           
/*  99 */           if (n < 1) {
/* 100 */             n = 1;
/*     */           }
/* 102 */           if (i1 > 248) {
/* 103 */             i1 = 248;
/*     */           }
/*     */           
/* 106 */           if (i2 < 0) {
/* 107 */             i2 = 0;
/*     */           }
/* 109 */           if (i3 > 16) {
/* 110 */             i3 = 16;
/*     */           }
/*     */           
/* 113 */           int i4 = 0;
/* 114 */           int i6; for (int i5 = k; (i4 == 0) && (i5 < m); i5++) {
/* 115 */             for (i6 = i2; (i4 == 0) && (i6 < i3); i6++) {
/* 116 */               for (int i7 = i1 + 1; (i4 == 0) && (i7 >= n - 1); i7--) {
/* 117 */                 if ((i7 >= 0) && (i7 < 256))
/*     */                 {
/*     */ 
/*     */ 
/* 121 */                   IBlockData localIBlockData1 = paramChunkSnapshot.a(i5, i7, i6);
/* 122 */                   if ((localIBlockData1.getBlock() == Blocks.FLOWING_WATER) || (localIBlockData1.getBlock() == Blocks.WATER)) {
/* 123 */                     i4 = 1;
/*     */                   }
/* 125 */                   if ((i7 != n - 1) && (i5 != k) && (i5 != m - 1) && (i6 != i2) && (i6 != i3 - 1))
/* 126 */                     i7 = n;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 131 */           if (i4 == 0)
/*     */           {
/*     */ 
/*     */ 
/* 135 */             BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 136 */             for (i6 = k; i6 < m; i6++) {
/* 137 */               double d9 = (i6 + paramInt1 * 16 + 0.5D - paramDouble1) / d3;
/* 138 */               for (int i8 = i2; i8 < i3; i8++) {
/* 139 */                 double d10 = (i8 + paramInt2 * 16 + 0.5D - paramDouble3) / d3;
/* 140 */                 int i9 = 0;
/* 141 */                 if (d9 * d9 + d10 * d10 < 1.0D) {
/* 142 */                   for (int i10 = i1; i10 > n; i10--) {
/* 143 */                     double d11 = (i10 - 1 + 0.5D - paramDouble2) / d4;
/* 144 */                     if ((d9 * d9 + d10 * d10) * this.d[(i10 - 1)] + d11 * d11 / 6.0D < 1.0D) {
/* 145 */                       IBlockData localIBlockData2 = paramChunkSnapshot.a(i6, i10, i8);
/* 146 */                       if (localIBlockData2.getBlock() == Blocks.GRASS) {
/* 147 */                         i9 = 1;
/*     */                       }
/* 149 */                       if ((localIBlockData2.getBlock() == Blocks.STONE) || (localIBlockData2.getBlock() == Blocks.DIRT) || (localIBlockData2.getBlock() == Blocks.GRASS)) {
/* 150 */                         if (i10 - 1 < 10) {
/* 151 */                           paramChunkSnapshot.a(i6, i10, i8, Blocks.FLOWING_LAVA.getBlockData());
/*     */                         } else {
/* 153 */                           paramChunkSnapshot.a(i6, i10, i8, Blocks.AIR.getBlockData());
/* 154 */                           if ((i9 != 0) && (paramChunkSnapshot.a(i6, i10 - 1, i8).getBlock() == Blocks.DIRT)) {
/* 155 */                             localMutableBlockPosition.c(i6 + paramInt1 * 16, 0, i8 + paramInt2 * 16);
/* 156 */                             paramChunkSnapshot.a(i6, i10 - 1, i8, this.c.getBiome(localMutableBlockPosition).ak);
/*     */                           }
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 165 */             if (i != 0)
/*     */               break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 173 */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ChunkSnapshot paramChunkSnapshot) { if (this.b.nextInt(50) != 0) {
/* 174 */       return;
/*     */     }
/* 176 */     double d1 = paramInt1 * 16 + this.b.nextInt(16);
/* 177 */     double d2 = this.b.nextInt(this.b.nextInt(40) + 8) + 20;
/* 178 */     double d3 = paramInt2 * 16 + this.b.nextInt(16);
/*     */     
/* 180 */     int i = 1;
/*     */     
/* 182 */     for (int j = 0; j < i; j++) {
/* 183 */       float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 184 */       float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 185 */       float f3 = (this.b.nextFloat() * 2.0F + this.b.nextFloat()) * 2.0F;
/*     */       
/* 187 */       a(this.b.nextLong(), paramInt3, paramInt4, paramChunkSnapshot, d1, d2, d3, f3, f1, f2, 0, 0, 3.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenCanyon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */