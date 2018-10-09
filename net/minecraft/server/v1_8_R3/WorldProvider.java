/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public abstract class WorldProvider
/*     */ {
/*  23 */   public static final float[] a = { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*     */   
/*     */   protected World b;
/*     */   
/*     */   private WorldType type;
/*     */   private String i;
/*     */   protected WorldChunkManager c;
/*     */   protected boolean d;
/*     */   protected boolean e;
/*  32 */   protected final float[] f = new float[16];
/*     */   
/*     */   protected int dimension;
/*     */   
/*  36 */   private final float[] j = new float[4];
/*     */   
/*     */   public final void a(World paramWorld) {
/*  39 */     this.b = paramWorld;
/*  40 */     this.type = paramWorld.getWorldData().getType();
/*  41 */     this.i = paramWorld.getWorldData().getGeneratorOptions();
/*  42 */     b();
/*  43 */     a();
/*     */   }
/*     */   
/*     */   protected void a() {
/*  47 */     float f1 = 0.0F;
/*  48 */     for (int k = 0; k <= 15; k++) {
/*  49 */       float f2 = 1.0F - k / 15.0F;
/*  50 */       this.f[k] = ((1.0F - f2) / (f2 * 3.0F + 1.0F) * (1.0F - f1) + f1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b() {
/*  55 */     WorldType localWorldType = this.b.getWorldData().getType();
/*  56 */     if (localWorldType == WorldType.FLAT) {
/*  57 */       WorldGenFlatInfo localWorldGenFlatInfo = WorldGenFlatInfo.a(this.b.getWorldData().getGeneratorOptions());
/*  58 */       this.c = new WorldChunkManagerHell(BiomeBase.getBiome(localWorldGenFlatInfo.a(), BiomeBase.ad), 0.5F);
/*  59 */     } else if (localWorldType == WorldType.DEBUG_ALL_BLOCK_STATES) {
/*  60 */       this.c = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.0F);
/*     */     } else {
/*  62 */       this.c = new WorldChunkManager(this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public IChunkProvider getChunkProvider() {
/*  67 */     if (this.type == WorldType.FLAT)
/*  68 */       return new ChunkProviderFlat(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i);
/*  69 */     if (this.type == WorldType.DEBUG_ALL_BLOCK_STATES)
/*  70 */       return new ChunkProviderDebug(this.b);
/*  71 */     if (this.type == WorldType.CUSTOMIZED) {
/*  72 */       return new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i);
/*     */     }
/*  74 */     return new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i);
/*     */   }
/*     */   
/*     */   public boolean canSpawn(int paramInt1, int paramInt2)
/*     */   {
/*  79 */     return this.b.c(new BlockPosition(paramInt1, 0, paramInt2)) == Blocks.GRASS;
/*     */   }
/*     */   
/*     */   public float a(long paramLong, float paramFloat) {
/*  83 */     int k = (int)(paramLong % 24000L);
/*  84 */     float f1 = (k + paramFloat) / 24000.0F - 0.25F;
/*  85 */     if (f1 < 0.0F) {
/*  86 */       f1 += 1.0F;
/*     */     }
/*  88 */     if (f1 > 1.0F) {
/*  89 */       f1 -= 1.0F;
/*     */     }
/*  91 */     float f2 = f1;
/*  92 */     f1 = 1.0F - (float)((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
/*  93 */     f1 = f2 + (f1 - f2) / 3.0F;
/*  94 */     return f1;
/*     */   }
/*     */   
/*     */   public int a(long paramLong)
/*     */   {
/*  99 */     return (int)(paramLong / 24000L % 8L + 8L) % 8;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 103 */     return true;
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
/*     */   public boolean e()
/*     */   {
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   public static WorldProvider byDimension(int paramInt)
/*     */   {
/* 145 */     if (paramInt == -1) {
/* 146 */       return new WorldProviderHell();
/*     */     }
/* 148 */     if (paramInt == 0) {
/* 149 */       return new WorldProviderNormal();
/*     */     }
/* 151 */     if (paramInt == 1) {
/* 152 */       return new WorldProviderTheEnd();
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockPosition h()
/*     */   {
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   public int getSeaLevel() {
/* 170 */     if (this.type == WorldType.FLAT) {
/* 171 */       return 4;
/*     */     }
/* 173 */     return this.b.F() + 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getName();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getSuffix();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldChunkManager m()
/*     */   {
/* 192 */     return this.c;
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 196 */     return this.d;
/*     */   }
/*     */   
/*     */   public boolean o() {
/* 200 */     return this.e;
/*     */   }
/*     */   
/*     */   public float[] p() {
/* 204 */     return this.f;
/*     */   }
/*     */   
/*     */   public int getDimension() {
/* 208 */     return this.dimension;
/*     */   }
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/* 212 */     return new WorldBorder();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */