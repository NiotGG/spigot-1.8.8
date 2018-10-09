/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
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
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private GenLayer b;
/*     */   private GenLayer c;
/*  21 */   private BiomeCache d = new BiomeCache(this);
/*     */   
/*     */   private List<BiomeBase> e;
/*  24 */   private String f = "";
/*     */   
/*     */   protected WorldChunkManager() {
/*  27 */     this.e = Lists.newArrayList();
/*  28 */     this.e.add(BiomeBase.FOREST);
/*  29 */     this.e.add(BiomeBase.PLAINS);
/*  30 */     this.e.add(BiomeBase.TAIGA);
/*  31 */     this.e.add(BiomeBase.TAIGA_HILLS);
/*  32 */     this.e.add(BiomeBase.FOREST_HILLS);
/*  33 */     this.e.add(BiomeBase.JUNGLE);
/*  34 */     this.e.add(BiomeBase.JUNGLE_HILLS);
/*     */   }
/*     */   
/*     */   public WorldChunkManager(long paramLong, WorldType paramWorldType, String paramString) {
/*  38 */     this();
/*  39 */     this.f = paramString;
/*     */     
/*  41 */     GenLayer[] arrayOfGenLayer = GenLayer.a(paramLong, paramWorldType, paramString);
/*  42 */     this.b = arrayOfGenLayer[0];
/*  43 */     this.c = arrayOfGenLayer[1];
/*     */   }
/*     */   
/*     */   public WorldChunkManager(World paramWorld) {
/*  47 */     this(paramWorld.getSeed(), paramWorld.getWorldData().getType(), paramWorld.getWorldData().getGeneratorOptions());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<BiomeBase> a()
/*     */   {
/*  56 */     return this.e;
/*     */   }
/*     */   
/*     */   public BiomeBase getBiome(BlockPosition paramBlockPosition) {
/*  60 */     return getBiome(paramBlockPosition, null);
/*     */   }
/*     */   
/*     */   public BiomeBase getBiome(BlockPosition paramBlockPosition, BiomeBase paramBiomeBase) {
/*  64 */     return this.d.a(paramBlockPosition.getX(), paramBlockPosition.getZ(), paramBiomeBase);
/*     */   }
/*     */   
/*     */   public float[] getWetness(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     
/*  69 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt3 * paramInt4)) {
/*  70 */       paramArrayOfFloat = new float[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  73 */     int[] arrayOfInt = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*  74 */     for (int i = 0; i < paramInt3 * paramInt4; i++) {
/*     */       try {
/*  76 */         float f1 = BiomeBase.getBiome(arrayOfInt[i], BiomeBase.ad).h() / 65536.0F;
/*  77 */         if (f1 > 1.0F) {
/*  78 */           f1 = 1.0F;
/*     */         }
/*  80 */         paramArrayOfFloat[i] = f1;
/*     */       } catch (Throwable localThrowable) {
/*  82 */         CrashReport localCrashReport = CrashReport.a(localThrowable, "Invalid Biome id");
/*  83 */         CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("DownfallBlock");
/*  84 */         localCrashReportSystemDetails.a("biome id", Integer.valueOf(i));
/*  85 */         localCrashReportSystemDetails.a("downfalls[] size", Integer.valueOf(paramArrayOfFloat.length));
/*  86 */         localCrashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/*  87 */         localCrashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/*  88 */         localCrashReportSystemDetails.a("w", Integer.valueOf(paramInt3));
/*  89 */         localCrashReportSystemDetails.a("h", Integer.valueOf(paramInt4));
/*     */         
/*  91 */         throw new ReportedException(localCrashReport);
/*     */       }
/*     */     }
/*     */     
/*  95 */     return paramArrayOfFloat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BiomeBase[] getBiomes(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 108 */     if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
/* 109 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/* 112 */     int[] arrayOfInt = this.b.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     try {
/* 114 */       for (int i = 0; i < paramInt3 * paramInt4; i++) {
/* 115 */         paramArrayOfBiomeBase[i] = BiomeBase.getBiome(arrayOfInt[i], BiomeBase.ad);
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 118 */       CrashReport localCrashReport = CrashReport.a(localThrowable, "Invalid Biome id");
/* 119 */       CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("RawBiomeBlock");
/* 120 */       localCrashReportSystemDetails.a("biomes[] size", Integer.valueOf(paramArrayOfBiomeBase.length));
/* 121 */       localCrashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/* 122 */       localCrashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/* 123 */       localCrashReportSystemDetails.a("w", Integer.valueOf(paramInt3));
/* 124 */       localCrashReportSystemDetails.a("h", Integer.valueOf(paramInt4));
/*     */       
/* 126 */       throw new ReportedException(localCrashReport);
/*     */     }
/*     */     
/* 129 */     return paramArrayOfBiomeBase;
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
/*     */   public BiomeBase[] getBiomeBlock(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 142 */     return a(paramArrayOfBiomeBase, paramInt1, paramInt2, paramInt3, paramInt4, true);
/*     */   }
/*     */   
/*     */   public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*     */     
/* 147 */     if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
/* 148 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/* 151 */     if ((paramBoolean) && (paramInt3 == 16) && (paramInt4 == 16) && ((paramInt1 & 0xF) == 0) && ((paramInt2 & 0xF) == 0)) {
/* 152 */       localObject = this.d.c(paramInt1, paramInt2);
/* 153 */       System.arraycopy(localObject, 0, paramArrayOfBiomeBase, 0, paramInt3 * paramInt4);
/* 154 */       return paramArrayOfBiomeBase;
/*     */     }
/*     */     
/* 157 */     Object localObject = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 158 */     for (int i = 0; i < paramInt3 * paramInt4; i++) {
/* 159 */       paramArrayOfBiomeBase[i] = BiomeBase.getBiome(localObject[i], BiomeBase.ad);
/*     */     }
/*     */     
/* 162 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, List<BiomeBase> paramList)
/*     */   {
/* 173 */     IntCache.a();
/* 174 */     int i = paramInt1 - paramInt3 >> 2;
/* 175 */     int j = paramInt2 - paramInt3 >> 2;
/* 176 */     int k = paramInt1 + paramInt3 >> 2;
/* 177 */     int m = paramInt2 + paramInt3 >> 2;
/*     */     
/* 179 */     int n = k - i + 1;
/* 180 */     int i1 = m - j + 1;
/*     */     
/* 182 */     int[] arrayOfInt = this.b.a(i, j, n, i1);
/*     */     try {
/* 184 */       for (int i2 = 0; i2 < n * i1; i2++) {
/* 185 */         localObject = BiomeBase.getBiome(arrayOfInt[i2]);
/* 186 */         if (!paramList.contains(localObject)) {
/* 187 */           return false;
/*     */         }
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 191 */       Object localObject = CrashReport.a(localThrowable, "Invalid Biome id");
/* 192 */       CrashReportSystemDetails localCrashReportSystemDetails = ((CrashReport)localObject).a("Layer");
/* 193 */       localCrashReportSystemDetails.a("Layer", this.b.toString());
/* 194 */       localCrashReportSystemDetails.a("x", Integer.valueOf(paramInt1));
/* 195 */       localCrashReportSystemDetails.a("z", Integer.valueOf(paramInt2));
/* 196 */       localCrashReportSystemDetails.a("radius", Integer.valueOf(paramInt3));
/* 197 */       localCrashReportSystemDetails.a("allowed", paramList);
/*     */       
/* 199 */       throw new ReportedException((CrashReport)localObject);
/*     */     }
/*     */     
/* 202 */     return true;
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
/*     */   public BlockPosition a(int paramInt1, int paramInt2, int paramInt3, List<BiomeBase> paramList, Random paramRandom)
/*     */   {
/* 270 */     IntCache.a();
/* 271 */     int i = paramInt1 - paramInt3 >> 2;
/* 272 */     int j = paramInt2 - paramInt3 >> 2;
/* 273 */     int k = paramInt1 + paramInt3 >> 2;
/* 274 */     int m = paramInt2 + paramInt3 >> 2;
/*     */     
/* 276 */     int n = k - i + 1;
/* 277 */     int i1 = m - j + 1;
/* 278 */     int[] arrayOfInt = this.b.a(i, j, n, i1);
/* 279 */     BlockPosition localBlockPosition = null;
/* 280 */     int i2 = 0;
/* 281 */     for (int i3 = 0; i3 < n * i1; i3++) {
/* 282 */       int i4 = i + i3 % n << 2;
/* 283 */       int i5 = j + i3 / n << 2;
/* 284 */       BiomeBase localBiomeBase = BiomeBase.getBiome(arrayOfInt[i3]);
/* 285 */       if ((paramList.contains(localBiomeBase)) && (
/* 286 */         (localBlockPosition == null) || (paramRandom.nextInt(i2 + 1) == 0))) {
/* 287 */         localBlockPosition = new BlockPosition(i4, 0, i5);
/* 288 */         i2++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 293 */     return localBlockPosition;
/*     */   }
/*     */   
/*     */   public void b() {
/* 297 */     this.d.a();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldChunkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */