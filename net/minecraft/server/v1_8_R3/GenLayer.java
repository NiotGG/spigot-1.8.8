/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.concurrent.Callable;
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
/*     */ public abstract class GenLayer
/*     */ {
/*     */   private long c;
/*     */   protected GenLayer a;
/*     */   private long d;
/*     */   protected long b;
/*     */   
/*     */   public static GenLayer[] a(long paramLong, WorldType paramWorldType, String paramString)
/*     */   {
/*  28 */     Object localObject1 = new LayerIsland(1L);
/*  29 */     localObject1 = new GenLayerZoomFuzzy(2000L, (GenLayer)localObject1);
/*  30 */     localObject1 = new GenLayerIsland(1L, (GenLayer)localObject1);
/*  31 */     localObject1 = new GenLayerZoom(2001L, (GenLayer)localObject1);
/*  32 */     localObject1 = new GenLayerIsland(2L, (GenLayer)localObject1);
/*  33 */     localObject1 = new GenLayerIsland(50L, (GenLayer)localObject1);
/*  34 */     localObject1 = new GenLayerIsland(70L, (GenLayer)localObject1);
/*  35 */     localObject1 = new GenLayerIcePlains(2L, (GenLayer)localObject1);
/*  36 */     localObject1 = new GenLayerTopSoil(2L, (GenLayer)localObject1);
/*  37 */     localObject1 = new GenLayerIsland(3L, (GenLayer)localObject1);
/*  38 */     localObject1 = new GenLayerSpecial(2L, (GenLayer)localObject1, GenLayerSpecial.EnumGenLayerSpecial.COOL_WARM);
/*  39 */     localObject1 = new GenLayerSpecial(2L, (GenLayer)localObject1, GenLayerSpecial.EnumGenLayerSpecial.HEAT_ICE);
/*  40 */     localObject1 = new GenLayerSpecial(3L, (GenLayer)localObject1, GenLayerSpecial.EnumGenLayerSpecial.SPECIAL);
/*  41 */     localObject1 = new GenLayerZoom(2002L, (GenLayer)localObject1);
/*  42 */     localObject1 = new GenLayerZoom(2003L, (GenLayer)localObject1);
/*  43 */     localObject1 = new GenLayerIsland(4L, (GenLayer)localObject1);
/*  44 */     localObject1 = new GenLayerMushroomIsland(5L, (GenLayer)localObject1);
/*  45 */     localObject1 = new GenLayerDeepOcean(4L, (GenLayer)localObject1);
/*  46 */     localObject1 = GenLayerZoom.b(1000L, (GenLayer)localObject1, 0);
/*     */     
/*  48 */     CustomWorldSettingsFinal localCustomWorldSettingsFinal = null;
/*  49 */     int i = 4;
/*  50 */     int j = i;
/*  51 */     if ((paramWorldType == WorldType.CUSTOMIZED) && (paramString.length() > 0)) {
/*  52 */       localCustomWorldSettingsFinal = CustomWorldSettingsFinal.CustomWorldSettings.a(paramString).b();
/*  53 */       i = localCustomWorldSettingsFinal.G;
/*  54 */       j = localCustomWorldSettingsFinal.H;
/*     */     }
/*  56 */     if (paramWorldType == WorldType.LARGE_BIOMES) {
/*  57 */       i = 6;
/*     */     }
/*     */     
/*  60 */     Object localObject2 = localObject1;
/*  61 */     localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, 0);
/*  62 */     localObject2 = new GenLayerCleaner(100L, (GenLayer)localObject2);
/*     */     
/*  64 */     Object localObject3 = localObject1;
/*  65 */     localObject3 = new GenLayerBiome(200L, (GenLayer)localObject3, paramWorldType, paramString);
/*  66 */     localObject3 = GenLayerZoom.b(1000L, (GenLayer)localObject3, 2);
/*  67 */     localObject3 = new GenLayerDesert(1000L, (GenLayer)localObject3);
/*  68 */     Object localObject4 = localObject2;
/*  69 */     localObject4 = GenLayerZoom.b(1000L, (GenLayer)localObject4, 2);
/*  70 */     localObject3 = new GenLayerRegionHills(1000L, (GenLayer)localObject3, (GenLayer)localObject4);
/*     */     
/*  72 */     localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, 2);
/*  73 */     localObject2 = GenLayerZoom.b(1000L, (GenLayer)localObject2, j);
/*  74 */     localObject2 = new GenLayerRiver(1L, (GenLayer)localObject2);
/*  75 */     localObject2 = new GenLayerSmooth(1000L, (GenLayer)localObject2);
/*     */     
/*  77 */     localObject3 = new GenLayerPlains(1001L, (GenLayer)localObject3);
/*  78 */     for (int k = 0; k < i; k++) {
/*  79 */       localObject3 = new GenLayerZoom(1000 + k, (GenLayer)localObject3);
/*  80 */       if (k == 0) {
/*  81 */         localObject3 = new GenLayerIsland(3L, (GenLayer)localObject3);
/*     */       }
/*     */       
/*  84 */       if ((k == 1) || (i == 1)) {
/*  85 */         localObject3 = new GenLayerMushroomShore(1000L, (GenLayer)localObject3);
/*     */       }
/*     */     }
/*     */     
/*  89 */     localObject3 = new GenLayerSmooth(1000L, (GenLayer)localObject3);
/*     */     
/*  91 */     localObject3 = new GenLayerRiverMix(100L, (GenLayer)localObject3, (GenLayer)localObject2);
/*     */     
/*  93 */     Object localObject5 = localObject3;
/*  94 */     GenLayerZoomVoronoi localGenLayerZoomVoronoi = new GenLayerZoomVoronoi(10L, (GenLayer)localObject3);
/*     */     
/*  96 */     ((GenLayer)localObject3).a(paramLong);
/*  97 */     localGenLayerZoomVoronoi.a(paramLong);
/*     */     
/*  99 */     return new GenLayer[] { localObject3, localGenLayerZoomVoronoi, localObject5 };
/*     */   }
/*     */   
/*     */ 
/*     */   public GenLayer(long paramLong)
/*     */   {
/* 105 */     this.b = paramLong;
/* 106 */     this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
/* 107 */     this.b += paramLong;
/* 108 */     this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
/* 109 */     this.b += paramLong;
/* 110 */     this.b *= (this.b * 6364136223846793005L + 1442695040888963407L);
/* 111 */     this.b += paramLong;
/*     */   }
/*     */   
/*     */   public void a(long paramLong) {
/* 115 */     this.c = paramLong;
/* 116 */     if (this.a != null) {
/* 117 */       this.a.a(paramLong);
/*     */     }
/* 119 */     this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
/* 120 */     this.c += this.b;
/* 121 */     this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
/* 122 */     this.c += this.b;
/* 123 */     this.c *= (this.c * 6364136223846793005L + 1442695040888963407L);
/* 124 */     this.c += this.b;
/*     */   }
/*     */   
/*     */   public void a(long paramLong1, long paramLong2) {
/* 128 */     this.d = this.c;
/* 129 */     this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
/* 130 */     this.d += paramLong1;
/* 131 */     this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
/* 132 */     this.d += paramLong2;
/* 133 */     this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
/* 134 */     this.d += paramLong1;
/* 135 */     this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
/* 136 */     this.d += paramLong2;
/*     */   }
/*     */   
/*     */   protected int a(int paramInt) {
/* 140 */     int i = (int)((this.d >> 24) % paramInt);
/* 141 */     if (i < 0) {
/* 142 */       i += paramInt;
/*     */     }
/* 144 */     this.d *= (this.d * 6364136223846793005L + 1442695040888963407L);
/* 145 */     this.d += this.c;
/* 146 */     return i;
/*     */   }
/*     */   
/*     */   public abstract int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   protected static boolean a(int paramInt1, int paramInt2) {
/* 152 */     if (paramInt1 == paramInt2) {
/* 153 */       return true;
/*     */     }
/* 155 */     if ((paramInt1 == BiomeBase.MESA_PLATEAU_F.id) || (paramInt1 == BiomeBase.MESA_PLATEAU.id)) {
/* 156 */       return (paramInt2 == BiomeBase.MESA_PLATEAU_F.id) || (paramInt2 == BiomeBase.MESA_PLATEAU.id);
/*     */     }
/*     */     
/* 159 */     BiomeBase localBiomeBase1 = BiomeBase.getBiome(paramInt1);
/* 160 */     BiomeBase localBiomeBase2 = BiomeBase.getBiome(paramInt2);
/*     */     try {
/* 162 */       if ((localBiomeBase1 != null) && (localBiomeBase2 != null)) {
/* 163 */         return localBiomeBase1.a(localBiomeBase2);
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 166 */       CrashReport localCrashReport = CrashReport.a(localThrowable, "Comparing biomes");
/* 167 */       CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("Biomes being compared");
/*     */       
/* 169 */       localCrashReportSystemDetails.a("Biome A ID", Integer.valueOf(paramInt1));
/* 170 */       localCrashReportSystemDetails.a("Biome B ID", Integer.valueOf(paramInt2));
/*     */       
/* 172 */       localCrashReportSystemDetails.a("Biome A", new Callable()
/*     */       {
/*     */         public String a() throws Exception {
/* 175 */           return String.valueOf(this.a);
/*     */         }
/* 177 */       });
/* 178 */       localCrashReportSystemDetails.a("Biome B", new Callable()
/*     */       {
/*     */         public String a() throws Exception {
/* 181 */           return String.valueOf(this.a);
/*     */         }
/*     */         
/* 184 */       });
/* 185 */       throw new ReportedException(localCrashReport);
/*     */     }
/*     */     
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean b(int paramInt) {
/* 192 */     return (paramInt == BiomeBase.OCEAN.id) || (paramInt == BiomeBase.DEEP_OCEAN.id) || (paramInt == BiomeBase.FROZEN_OCEAN.id);
/*     */   }
/*     */   
/*     */   protected int a(int... paramVarArgs) {
/* 196 */     return paramVarArgs[a(paramVarArgs.length)];
/*     */   }
/*     */   
/*     */   protected int b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 200 */     if ((paramInt2 == paramInt3) && (paramInt3 == paramInt4)) {
/* 201 */       return paramInt2;
/*     */     }
/* 203 */     if ((paramInt1 == paramInt2) && (paramInt1 == paramInt3)) {
/* 204 */       return paramInt1;
/*     */     }
/* 206 */     if ((paramInt1 == paramInt2) && (paramInt1 == paramInt4)) {
/* 207 */       return paramInt1;
/*     */     }
/* 209 */     if ((paramInt1 == paramInt3) && (paramInt1 == paramInt4)) {
/* 210 */       return paramInt1;
/*     */     }
/*     */     
/* 213 */     if ((paramInt1 == paramInt2) && (paramInt3 != paramInt4)) {
/* 214 */       return paramInt1;
/*     */     }
/* 216 */     if ((paramInt1 == paramInt3) && (paramInt2 != paramInt4)) {
/* 217 */       return paramInt1;
/*     */     }
/* 219 */     if ((paramInt1 == paramInt4) && (paramInt2 != paramInt3)) {
/* 220 */       return paramInt1;
/*     */     }
/*     */     
/*     */ 
/* 224 */     if ((paramInt2 == paramInt3) && (paramInt1 != paramInt4)) {
/* 225 */       return paramInt2;
/*     */     }
/* 227 */     if ((paramInt2 == paramInt4) && (paramInt1 != paramInt3)) {
/* 228 */       return paramInt2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 233 */     if ((paramInt3 == paramInt4) && (paramInt1 != paramInt2)) {
/* 234 */       return paramInt3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 241 */     return a(new int[] { paramInt1, paramInt2, paramInt3, paramInt4 });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */