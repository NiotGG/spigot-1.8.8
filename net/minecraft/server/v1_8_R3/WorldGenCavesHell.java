/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenCavesHell
/*     */   extends WorldGenBase
/*     */ {
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  12 */     a(paramLong, paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */   
/*     */   protected void a(long paramLong, int paramInt1, int paramInt2, ChunkSnapshot paramChunkSnapshot, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  16 */     double d1 = paramInt1 * 16 + 8;
/*  17 */     double d2 = paramInt2 * 16 + 8;
/*     */     
/*  19 */     float f1 = 0.0F;
/*  20 */     float f2 = 0.0F;
/*  21 */     Random localRandom = new Random(paramLong);
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
/*  34 */     int j = localRandom.nextInt(paramInt4 / 2) + paramInt4 / 4;
/*  35 */     int k = localRandom.nextInt(6) == 0 ? 1 : 0;
/*  37 */     for (; 
/*  37 */         paramInt3 < paramInt4; paramInt3++) {
/*  38 */       double d3 = 1.5D + MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F;
/*  39 */       double d4 = d3 * paramDouble4;
/*     */       
/*  41 */       float f3 = MathHelper.cos(paramFloat3);
/*  42 */       float f4 = MathHelper.sin(paramFloat3);
/*  43 */       paramDouble1 += MathHelper.cos(paramFloat2) * f3;
/*  44 */       paramDouble2 += f4;
/*  45 */       paramDouble3 += MathHelper.sin(paramFloat2) * f3;
/*     */       
/*  47 */       if (k != 0) {
/*  48 */         paramFloat3 *= 0.92F;
/*     */       } else {
/*  50 */         paramFloat3 *= 0.7F;
/*     */       }
/*  52 */       paramFloat3 += f2 * 0.1F;
/*  53 */       paramFloat2 += f1 * 0.1F;
/*     */       
/*  55 */       f2 *= 0.9F;
/*  56 */       f1 *= 0.75F;
/*  57 */       f2 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 2.0F;
/*  58 */       f1 += (localRandom.nextFloat() - localRandom.nextFloat()) * localRandom.nextFloat() * 4.0F;
/*     */       
/*  60 */       if ((i == 0) && (paramInt3 == j) && (paramFloat1 > 1.0F)) {
/*  61 */         a(localRandom.nextLong(), paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  62 */         a(localRandom.nextLong(), paramInt1, paramInt2, paramChunkSnapshot, paramDouble1, paramDouble2, paramDouble3, localRandom.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  63 */         return;
/*     */       }
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
/*  83 */           int m = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  84 */           int n = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  86 */           int i1 = MathHelper.floor(paramDouble2 - d4) - 1;
/*  87 */           int i2 = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  89 */           IBlockData localIBlockData1 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  90 */           IBlockData localIBlockData2 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  92 */           if (m < 0) {
/*  93 */             m = 0;
/*     */           }
/*  95 */           if (n > 16) {
/*  96 */             n = 16;
/*     */           }
/*     */           
/*  99 */           if (i1 < 1) {
/* 100 */             i1 = 1;
/*     */           }
/* 102 */           if (i2 > 120) {
/* 103 */             i2 = 120;
/*     */           }
/*     */           
/* 106 */           if (localIBlockData1 < 0) {
/* 107 */             localIBlockData1 = 0;
/*     */           }
/* 109 */           if (localIBlockData2 > 16) {
/* 110 */             localIBlockData2 = 16;
/*     */           }
/*     */           
/* 113 */           int i3 = 0;
/* 114 */           IBlockData localIBlockData3; for (int i4 = m; (i3 == 0) && (i4 < n); i4++) {
/* 115 */             for (int i5 = localIBlockData1; (i3 == 0) && (i5 < localIBlockData2); i5++) {
/* 116 */               for (int i6 = i2 + 1; (i3 == 0) && (i6 >= i1 - 1); i6--) {
/* 117 */                 if ((i6 >= 0) && (i6 < 128))
/*     */                 {
/*     */ 
/*     */ 
/* 121 */                   localIBlockData3 = paramChunkSnapshot.a(i4, i6, i5);
/* 122 */                   if ((localIBlockData3.getBlock() == Blocks.FLOWING_LAVA) || (localIBlockData3.getBlock() == Blocks.LAVA)) {
/* 123 */                     i3 = 1;
/*     */                   }
/* 125 */                   if ((i6 != i1 - 1) && (i4 != m) && (i4 != n - 1) && (i5 != localIBlockData1) && (i5 != localIBlockData2 - 1))
/* 126 */                     i6 = i1;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 131 */           if (i3 == 0)
/*     */           {
/*     */ 
/*     */ 
/* 135 */             for (i4 = m; i4 < n; i4++) {
/* 136 */               double d9 = (i4 + paramInt1 * 16 + 0.5D - paramDouble1) / d3;
/* 137 */               for (localIBlockData3 = localIBlockData1; localIBlockData3 < localIBlockData2; localIBlockData3++) {
/* 138 */                 double d10 = (localIBlockData3 + paramInt2 * 16 + 0.5D - paramDouble3) / d3;
/* 139 */                 for (int i7 = i2; i7 > i1; i7--) {
/* 140 */                   double d11 = (i7 - 1 + 0.5D - paramDouble2) / d4;
/* 141 */                   if ((d11 > -0.7D) && (d9 * d9 + d11 * d11 + d10 * d10 < 1.0D)) {
/* 142 */                     IBlockData localIBlockData4 = paramChunkSnapshot.a(i4, i7, localIBlockData3);
/* 143 */                     if ((localIBlockData4.getBlock() == Blocks.NETHERRACK) || (localIBlockData4.getBlock() == Blocks.DIRT) || (localIBlockData4.getBlock() == Blocks.GRASS)) {
/* 144 */                       paramChunkSnapshot.a(i4, i7, localIBlockData3, Blocks.AIR.getBlockData());
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 150 */             if (i != 0)
/*     */               break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 158 */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ChunkSnapshot paramChunkSnapshot) { int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(10) + 1) + 1);
/* 159 */     if (this.b.nextInt(5) != 0) {
/* 160 */       i = 0;
/*     */     }
/*     */     
/* 163 */     for (int j = 0; j < i; j++) {
/* 164 */       double d1 = paramInt1 * 16 + this.b.nextInt(16);
/* 165 */       double d2 = this.b.nextInt(128);
/* 166 */       double d3 = paramInt2 * 16 + this.b.nextInt(16);
/*     */       
/* 168 */       int k = 1;
/* 169 */       if (this.b.nextInt(4) == 0) {
/* 170 */         a(this.b.nextLong(), paramInt3, paramInt4, paramChunkSnapshot, d1, d2, d3);
/* 171 */         k += this.b.nextInt(4);
/*     */       }
/*     */       
/* 174 */       for (int m = 0; m < k; m++) {
/* 175 */         float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 176 */         float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 177 */         float f3 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
/*     */         
/* 179 */         a(this.b.nextLong(), paramInt3, paramInt4, paramChunkSnapshot, d1, d2, d3, f3 * 2.0F, f1, f2, 0, 0, 0.5D);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenCavesHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */