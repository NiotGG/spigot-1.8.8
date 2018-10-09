/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GenLayerRegionHills extends GenLayer
/*     */ {
/*   8 */   private static final Logger c = ;
/*     */   private GenLayer d;
/*     */   
/*     */   public GenLayerRegionHills(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2) {
/*  12 */     super(paramLong);
/*  13 */     this.a = paramGenLayer1;
/*  14 */     this.d = paramGenLayer2;
/*     */   }
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  19 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*  20 */     int[] arrayOfInt2 = this.d.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*     */     
/*  22 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/*  23 */     for (int i = 0; i < paramInt4; i++) {
/*  24 */       for (int j = 0; j < paramInt3; j++) {
/*  25 */         a(j + paramInt1, i + paramInt2);
/*  26 */         int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
/*  27 */         int m = arrayOfInt2[(j + 1 + (i + 1) * (paramInt3 + 2))];
/*  28 */         int n = (m - 2) % 29 == 0 ? 1 : 0;
/*  29 */         if (k > 255) {
/*  30 */           c.debug("old! " + k);
/*     */         }
/*  32 */         if ((k != 0) && (m >= 2) && ((m - 2) % 29 == 1) && (k < 128)) {
/*  33 */           if (BiomeBase.getBiome(k + 128) != null) {
/*  34 */             arrayOfInt3[(j + i * paramInt3)] = (k + 128);
/*     */           } else {
/*  36 */             arrayOfInt3[(j + i * paramInt3)] = k;
/*     */           }
/*  38 */         } else if ((a(3) == 0) || (n != 0)) {
/*  39 */           int i1 = k;
/*  40 */           int i2; if (k == BiomeBase.DESERT.id) {
/*  41 */             i1 = BiomeBase.DESERT_HILLS.id;
/*  42 */           } else if (k == BiomeBase.FOREST.id) {
/*  43 */             i1 = BiomeBase.FOREST_HILLS.id;
/*  44 */           } else if (k == BiomeBase.BIRCH_FOREST.id) {
/*  45 */             i1 = BiomeBase.BIRCH_FOREST_HILLS.id;
/*  46 */           } else if (k == BiomeBase.ROOFED_FOREST.id) {
/*  47 */             i1 = BiomeBase.PLAINS.id;
/*  48 */           } else if (k == BiomeBase.TAIGA.id) {
/*  49 */             i1 = BiomeBase.TAIGA_HILLS.id;
/*  50 */           } else if (k == BiomeBase.MEGA_TAIGA.id) {
/*  51 */             i1 = BiomeBase.MEGA_TAIGA_HILLS.id;
/*  52 */           } else if (k == BiomeBase.COLD_TAIGA.id) {
/*  53 */             i1 = BiomeBase.COLD_TAIGA_HILLS.id;
/*  54 */           } else if (k == BiomeBase.PLAINS.id) {
/*  55 */             if (a(3) == 0) {
/*  56 */               i1 = BiomeBase.FOREST_HILLS.id;
/*     */             } else {
/*  58 */               i1 = BiomeBase.FOREST.id;
/*     */             }
/*  60 */           } else if (k == BiomeBase.ICE_PLAINS.id) {
/*  61 */             i1 = BiomeBase.ICE_MOUNTAINS.id;
/*  62 */           } else if (k == BiomeBase.JUNGLE.id) {
/*  63 */             i1 = BiomeBase.JUNGLE_HILLS.id;
/*  64 */           } else if (k == BiomeBase.OCEAN.id) {
/*  65 */             i1 = BiomeBase.DEEP_OCEAN.id;
/*  66 */           } else if (k == BiomeBase.EXTREME_HILLS.id) {
/*  67 */             i1 = BiomeBase.EXTREME_HILLS_PLUS.id;
/*  68 */           } else if (k == BiomeBase.SAVANNA.id) {
/*  69 */             i1 = BiomeBase.SAVANNA_PLATEAU.id;
/*  70 */           } else if (a(k, BiomeBase.MESA_PLATEAU_F.id)) {
/*  71 */             i1 = BiomeBase.MESA.id;
/*  72 */           } else if ((k == BiomeBase.DEEP_OCEAN.id) && 
/*  73 */             (a(3) == 0)) {
/*  74 */             i2 = a(2);
/*  75 */             if (i2 == 0) {
/*  76 */               i1 = BiomeBase.PLAINS.id;
/*     */             } else {
/*  78 */               i1 = BiomeBase.FOREST.id;
/*     */             }
/*     */           }
/*     */           
/*  82 */           if ((n != 0) && (i1 != k)) {
/*  83 */             if (BiomeBase.getBiome(i1 + 128) != null) {
/*  84 */               i1 += 128;
/*     */             } else {
/*  86 */               i1 = k;
/*     */             }
/*     */           }
/*  89 */           if (i1 == k) {
/*  90 */             arrayOfInt3[(j + i * paramInt3)] = k;
/*     */           } else {
/*  92 */             i2 = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  93 */             int i3 = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  94 */             int i4 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  95 */             int i5 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  96 */             int i6 = 0;
/*  97 */             if (a(i2, k)) {
/*  98 */               i6++;
/*     */             }
/* 100 */             if (a(i3, k)) {
/* 101 */               i6++;
/*     */             }
/* 103 */             if (a(i4, k)) {
/* 104 */               i6++;
/*     */             }
/* 106 */             if (a(i5, k)) {
/* 107 */               i6++;
/*     */             }
/* 109 */             if (i6 >= 3) {
/* 110 */               arrayOfInt3[(j + i * paramInt3)] = i1;
/*     */             } else {
/* 112 */               arrayOfInt3[(j + i * paramInt3)] = k;
/*     */             }
/*     */           }
/*     */         } else {
/* 116 */           arrayOfInt3[(j + i * paramInt3)] = k;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 121 */     return arrayOfInt3;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerRegionHills.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */