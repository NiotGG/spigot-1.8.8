/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class GenLayerMushroomShore
/*     */   extends GenLayer
/*     */ {
/*     */   public GenLayerMushroomShore(long paramLong, GenLayer paramGenLayer)
/*     */   {
/*   9 */     super(paramLong);
/*  10 */     this.a = paramGenLayer;
/*     */   }
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  15 */     int[] arrayOfInt1 = this.a.a(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2);
/*     */     
/*  17 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*  18 */     for (int i = 0; i < paramInt4; i++) {
/*  19 */       for (int j = 0; j < paramInt3; j++) {
/*  20 */         a(j + paramInt1, i + paramInt2);
/*  21 */         int k = arrayOfInt1[(j + 1 + (i + 1) * (paramInt3 + 2))];
/*  22 */         BiomeBase localBiomeBase = BiomeBase.getBiome(k);
/*  23 */         int m; int n; int i1; int i2; if (k == BiomeBase.MUSHROOM_ISLAND.id) {
/*  24 */           m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  25 */           n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  26 */           i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  27 */           i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  28 */           if ((m == BiomeBase.OCEAN.id) || (n == BiomeBase.OCEAN.id) || (i1 == BiomeBase.OCEAN.id) || (i2 == BiomeBase.OCEAN.id)) {
/*  29 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.MUSHROOM_SHORE.id;
/*     */           } else {
/*  31 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*     */           }
/*  33 */         } else if ((localBiomeBase != null) && (localBiomeBase.l() == BiomeJungle.class)) {
/*  34 */           m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  35 */           n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  36 */           i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  37 */           i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  38 */           if ((!c(m)) || (!c(n)) || (!c(i1)) || (!c(i2))) {
/*  39 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.JUNGLE_EDGE.id;
/*  40 */           } else if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
/*  41 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.BEACH.id;
/*     */           } else {
/*  43 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*     */           }
/*  45 */         } else if ((k == BiomeBase.EXTREME_HILLS.id) || (k == BiomeBase.EXTREME_HILLS_PLUS.id) || (k == BiomeBase.SMALL_MOUNTAINS.id)) {
/*  46 */           a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.STONE_BEACH.id);
/*  47 */         } else if ((localBiomeBase != null) && (localBiomeBase.j())) {
/*  48 */           a(arrayOfInt1, arrayOfInt2, j, i, paramInt3, k, BiomeBase.COLD_BEACH.id);
/*  49 */         } else if ((k == BiomeBase.MESA.id) || (k == BiomeBase.MESA_PLATEAU_F.id)) {
/*  50 */           m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  51 */           n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  52 */           i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  53 */           i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  54 */           if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
/*  55 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*  56 */           } else if ((!d(m)) || (!d(n)) || (!d(i1)) || (!d(i2))) {
/*  57 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.DESERT.id;
/*     */           } else {
/*  59 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*     */           }
/*  61 */         } else if ((k != BiomeBase.OCEAN.id) && (k != BiomeBase.DEEP_OCEAN.id) && (k != BiomeBase.RIVER.id) && (k != BiomeBase.SWAMPLAND.id)) {
/*  62 */           m = arrayOfInt1[(j + 1 + (i + 1 - 1) * (paramInt3 + 2))];
/*  63 */           n = arrayOfInt1[(j + 1 + 1 + (i + 1) * (paramInt3 + 2))];
/*  64 */           i1 = arrayOfInt1[(j + 1 - 1 + (i + 1) * (paramInt3 + 2))];
/*  65 */           i2 = arrayOfInt1[(j + 1 + (i + 1 + 1) * (paramInt3 + 2))];
/*  66 */           if ((b(m)) || (b(n)) || (b(i1)) || (b(i2))) {
/*  67 */             arrayOfInt2[(j + i * paramInt3)] = BiomeBase.BEACH.id;
/*     */           } else {
/*  69 */             arrayOfInt2[(j + i * paramInt3)] = k;
/*     */           }
/*     */         } else {
/*  72 */           arrayOfInt2[(j + i * paramInt3)] = k;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  77 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private void a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  81 */     if (b(paramInt4)) {
/*  82 */       paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
/*  83 */       return;
/*     */     }
/*  85 */     int i = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 - 1) * (paramInt3 + 2))];
/*  86 */     int j = paramArrayOfInt1[(paramInt1 + 1 + 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  87 */     int k = paramArrayOfInt1[(paramInt1 + 1 - 1 + (paramInt2 + 1) * (paramInt3 + 2))];
/*  88 */     int m = paramArrayOfInt1[(paramInt1 + 1 + (paramInt2 + 1 + 1) * (paramInt3 + 2))];
/*  89 */     if ((b(i)) || (b(j)) || (b(k)) || (b(m))) {
/*  90 */       paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt5;
/*     */     } else {
/*  92 */       paramArrayOfInt2[(paramInt1 + paramInt2 * paramInt3)] = paramInt4;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean c(int paramInt) {
/*  97 */     if ((BiomeBase.getBiome(paramInt) != null) && (BiomeBase.getBiome(paramInt).l() == BiomeJungle.class)) {
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     return (paramInt == BiomeBase.JUNGLE_EDGE.id) || (paramInt == BiomeBase.JUNGLE.id) || (paramInt == BiomeBase.JUNGLE_HILLS.id) || (paramInt == BiomeBase.FOREST.id) || (paramInt == BiomeBase.TAIGA.id) || (b(paramInt));
/*     */   }
/*     */   
/*     */   private boolean d(int paramInt) {
/* 105 */     return BiomeBase.getBiome(paramInt) instanceof BiomeMesa;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerMushroomShore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */