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
/*     */ public class BiomeTaiga
/*     */   extends BiomeBase
/*     */ {
/*  16 */   private static final WorldGenTaiga1 aD = new WorldGenTaiga1();
/*  17 */   private static final WorldGenTaiga2 aE = new WorldGenTaiga2(false);
/*  18 */   private static final WorldGenMegaTree aF = new WorldGenMegaTree(false, false);
/*  19 */   private static final WorldGenMegaTree aG = new WorldGenMegaTree(false, true);
/*  20 */   private static final WorldGenTaigaStructure aH = new WorldGenTaigaStructure(Blocks.MOSSY_COBBLESTONE, 0);
/*     */   
/*     */ 
/*     */   private int aI;
/*     */   
/*     */ 
/*     */ 
/*     */   public BiomeTaiga(int paramInt1, int paramInt2)
/*     */   {
/*  29 */     super(paramInt1);
/*  30 */     this.aI = paramInt2;
/*     */     
/*  32 */     this.au.add(new BiomeBase.BiomeMeta(EntityWolf.class, 8, 4, 4));
/*     */     
/*  34 */     this.as.A = 10;
/*  35 */     if ((paramInt2 == 1) || (paramInt2 == 2)) {
/*  36 */       this.as.C = 7;
/*  37 */       this.as.D = 1;
/*  38 */       this.as.E = 3;
/*     */     } else {
/*  40 */       this.as.C = 1;
/*  41 */       this.as.E = 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenTreeAbstract a(Random paramRandom)
/*     */   {
/*  47 */     if (((this.aI == 1) || (this.aI == 2)) && (paramRandom.nextInt(3) == 0)) {
/*  48 */       if ((this.aI == 2) || (paramRandom.nextInt(13) == 0)) {
/*  49 */         return aG;
/*     */       }
/*  51 */       return aF;
/*     */     }
/*  53 */     if (paramRandom.nextInt(3) == 0) {
/*  54 */       return aD;
/*     */     }
/*  56 */     return aE;
/*     */   }
/*     */   
/*     */   public WorldGenerator b(Random paramRandom)
/*     */   {
/*  61 */     if (paramRandom.nextInt(5) > 0) {
/*  62 */       return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.FERN);
/*     */     }
/*  64 */     return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS); }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition) { int j;
/*     */     int k;
/*     */     int m;
/*  69 */     if ((this.aI == 1) || (this.aI == 2)) {
/*  70 */       i = paramRandom.nextInt(3);
/*  71 */       for (j = 0; j < i; j++) {
/*  72 */         k = paramRandom.nextInt(16) + 8;
/*  73 */         m = paramRandom.nextInt(16) + 8;
/*  74 */         BlockPosition localBlockPosition = paramWorld.getHighestBlockYAt(paramBlockPosition.a(k, 0, m));
/*  75 */         aH.generate(paramWorld, paramRandom, localBlockPosition);
/*     */       }
/*     */     }
/*     */     
/*  79 */     ag.a(BlockTallPlant.EnumTallFlowerVariants.FERN);
/*  80 */     for (int i = 0; i < 7; i++) {
/*  81 */       j = paramRandom.nextInt(16) + 8;
/*  82 */       k = paramRandom.nextInt(16) + 8;
/*  83 */       m = paramRandom.nextInt(paramWorld.getHighestBlockYAt(paramBlockPosition.a(j, 0, k)).getY() + 32);
/*  84 */       ag.generate(paramWorld, paramRandom, paramBlockPosition.a(j, m, k));
/*     */     }
/*  86 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*     */   {
/*  91 */     if ((this.aI == 1) || (this.aI == 2)) {
/*  92 */       this.ak = Blocks.GRASS.getBlockData();
/*  93 */       this.al = Blocks.DIRT.getBlockData();
/*  94 */       if (paramDouble > 1.75D) {
/*  95 */         this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT);
/*  96 */       } else if (paramDouble > -0.95D) {
/*  97 */         this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.PODZOL);
/*     */       }
/*     */     }
/* 100 */     b(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*     */   }
/*     */   
/*     */   protected BiomeBase d(int paramInt)
/*     */   {
/* 105 */     if (this.id == BiomeBase.MEGA_TAIGA.id) {
/* 106 */       return new BiomeTaiga(paramInt, 2).a(5858897, true).a("Mega Spruce Taiga").a(5159473).a(0.25F, 0.8F).a(new BiomeBase.BiomeTemperature(this.an, this.ao));
/*     */     }
/* 108 */     return super.d(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeTaiga.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */