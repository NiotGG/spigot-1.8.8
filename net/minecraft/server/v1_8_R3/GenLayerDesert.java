/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class GenLayerDesert
/*     */   extends GenLayer
/*     */ {
/*     */   public GenLayerDesert(long paramLong, GenLayer paramGenLayer)
/*     */   {
/*   8 */     super(paramLong);
/*   9 */     this.a = paramGenLayer;
/*     */   }
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  14 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*     */     
/*  16 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*  17 */     for (int i = 0; i < paramInt4; i++) {
/*  18 */       for (int j = 0; j < paramInt3; j++) {
/*  19 */         a(j + paramInt1, i + paramInt2);
/*  20 */         int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
/*  21 */         if ((!a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.EXTREME_HILLS.id, BiomeBase.SMALL_MOUNTAINS.id)) && 
/*  22 */           (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MESA_PLATEAU_F.id, BiomeBase.MESA.id)) && 
/*  23 */           (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MESA_PLATEAU.id, BiomeBase.MESA.id)) && 
/*  24 */           (!b(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.MEGA_TAIGA.id, BiomeBase.TAIGA.id))) { int m;
/*  25 */           int n; int i1; int i2; if (k == BiomeBase.DESERT.id) {
/*  26 */             m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  27 */             n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  28 */             i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  29 */             i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  30 */             if ((m == BiomeBase.ICE_PLAINS.id) || (n == BiomeBase.ICE_PLAINS.id) || (i1 == BiomeBase.ICE_PLAINS.id) || (i2 == BiomeBase.ICE_PLAINS.id)) {
/*  31 */               arrayOfInt2[(j + i * paramInt3)] = BiomeBase.EXTREME_HILLS_PLUS.id;
/*     */             } else {
/*  33 */               arrayOfInt2[(j + i * paramInt3)] = k;
/*     */             }
/*  35 */           } else if (k == BiomeBase.SWAMPLAND.id)
/*     */           {
/*  37 */             m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  38 */             n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  39 */             i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  40 */             i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  41 */             if ((m == BiomeBase.DESERT.id) || (n == BiomeBase.DESERT.id) || (i1 == BiomeBase.DESERT.id) || (i2 == BiomeBase.DESERT.id) || (m == BiomeBase.COLD_TAIGA.id) || (n == BiomeBase.COLD_TAIGA.id) || (i1 == BiomeBase.COLD_TAIGA.id) || (i2 == BiomeBase.COLD_TAIGA.id) || (m == BiomeBase.ICE_PLAINS.id) || (n == BiomeBase.ICE_PLAINS.id) || (i1 == BiomeBase.ICE_PLAINS.id) || (i2 == BiomeBase.ICE_PLAINS.id)) {
/*  42 */               arrayOfInt2[(j + i * paramInt3)] = BiomeBase.PLAINS.id;
/*  43 */             } else if ((m == BiomeBase.JUNGLE.id) || (i2 == BiomeBase.JUNGLE.id) || (n == BiomeBase.JUNGLE.id) || (i1 == BiomeBase.JUNGLE.id)) {
/*  44 */               arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE_EDGE.id;
/*     */             } else {
/*  46 */               arrayOfInt2[(j + i * paramInt3)] = k;
/*     */             }
/*     */           } else {
/*  49 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  54 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private boolean a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  58 */     if (a(paramInt4, paramInt5)) {
/*  59 */       int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
/*  60 */       int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  61 */       int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  62 */       int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
/*  63 */       if ((!b(i, paramInt5)) || (!b(j, paramInt5)) || (!b(k, paramInt5)) || (!b(m, paramInt5))) {
/*  64 */         paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt6;
/*     */       } else {
/*  66 */         paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
/*     */       }
/*  68 */       return true;
/*     */     }
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   private boolean b(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  74 */     if (paramInt4 == paramInt5) {
/*  75 */       int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
/*  76 */       int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  77 */       int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  78 */       int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
/*  79 */       if ((!a(i, paramInt5)) || (!a(j, paramInt5)) || (!a(k, paramInt5)) || (!a(m, paramInt5))) {
/*  80 */         paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt6;
/*     */       } else {
/*  82 */         paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
/*     */       }
/*  84 */       return true;
/*     */     }
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   private boolean b(int paramInt1, int paramInt2) {
/*  90 */     if (a(paramInt1, paramInt2)) {
/*  91 */       return true;
/*     */     }
/*  93 */     BiomeBase localBiomeBase1 = BiomeBase.getBiome(paramInt1);
/*  94 */     BiomeBase localBiomeBase2 = BiomeBase.getBiome(paramInt2);
/*  95 */     if ((localBiomeBase1 != null) && (localBiomeBase2 != null)) {
/*  96 */       BiomeBase.EnumTemperature localEnumTemperature1 = localBiomeBase1.m();
/*  97 */       BiomeBase.EnumTemperature localEnumTemperature2 = localBiomeBase2.m();
/*  98 */       return (localEnumTemperature1 == localEnumTemperature2) || (localEnumTemperature1 == BiomeBase.EnumTemperature.MEDIUM) || (localEnumTemperature2 == BiomeBase.EnumTemperature.MEDIUM);
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */