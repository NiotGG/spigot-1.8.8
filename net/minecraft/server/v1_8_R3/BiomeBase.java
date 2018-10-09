/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public abstract class BiomeBase
/*     */ {
/*  33 */   private static final Logger aD = ;
/*     */   
/*     */ 
/*     */   public static enum EnumTemperature
/*     */   {
/*     */     private EnumTemperature() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public static class BiomeTemperature
/*     */   {
/*     */     public float a;
/*     */     public float b;
/*     */     
/*     */     public BiomeTemperature(float paramFloat1, float paramFloat2)
/*     */     {
/*  49 */       this.a = paramFloat1;
/*  50 */       this.b = paramFloat2;
/*     */     }
/*     */     
/*     */     public BiomeTemperature a() {
/*  54 */       return new BiomeTemperature(this.a * 0.8F, this.b * 0.6F);
/*     */     }
/*     */   }
/*     */   
/*  58 */   protected static final BiomeTemperature a = new BiomeTemperature(0.1F, 0.2F);
/*  59 */   protected static final BiomeTemperature b = new BiomeTemperature(-0.5F, 0.0F);
/*  60 */   protected static final BiomeTemperature c = new BiomeTemperature(-1.0F, 0.1F);
/*  61 */   protected static final BiomeTemperature d = new BiomeTemperature(-1.8F, 0.1F);
/*  62 */   protected static final BiomeTemperature e = new BiomeTemperature(0.125F, 0.05F);
/*  63 */   protected static final BiomeTemperature f = new BiomeTemperature(0.2F, 0.2F);
/*  64 */   protected static final BiomeTemperature g = new BiomeTemperature(0.45F, 0.3F);
/*  65 */   protected static final BiomeTemperature h = new BiomeTemperature(1.5F, 0.025F);
/*  66 */   protected static final BiomeTemperature i = new BiomeTemperature(1.0F, 0.5F);
/*  67 */   protected static final BiomeTemperature j = new BiomeTemperature(0.0F, 0.025F);
/*  68 */   protected static final BiomeTemperature k = new BiomeTemperature(0.1F, 0.8F);
/*  69 */   protected static final BiomeTemperature l = new BiomeTemperature(0.2F, 0.3F);
/*  70 */   protected static final BiomeTemperature m = new BiomeTemperature(-0.2F, 0.1F);
/*     */   
/*  72 */   private static final BiomeBase[] biomes = new BiomeBase['Ā'];
/*  73 */   public static final Set<BiomeBase> n = Sets.newHashSet();
/*  74 */   public static final Map<String, BiomeBase> o = Maps.newHashMap();
/*     */   
/*  76 */   public static final BiomeBase OCEAN = new BiomeOcean(0).b(112).a("Ocean").a(c);
/*  77 */   public static final BiomeBase PLAINS = new BiomePlains(1).b(9286496).a("Plains");
/*  78 */   public static final BiomeBase DESERT = new BiomeDesert(2).b(16421912).a("Desert").b().a(2.0F, 0.0F).a(e);
/*     */   
/*  80 */   public static final BiomeBase EXTREME_HILLS = new BiomeBigHills(3, false).b(6316128).a("Extreme Hills").a(i).a(0.2F, 0.3F);
/*  81 */   public static final BiomeBase FOREST = new BiomeForest(4, 0).b(353825).a("Forest");
/*  82 */   public static final BiomeBase TAIGA = new BiomeTaiga(5, 0).b(747097).a("Taiga").a(5159473).a(0.25F, 0.8F).a(f);
/*     */   
/*  84 */   public static final BiomeBase SWAMPLAND = new BiomeSwamp(6).b(522674).a("Swampland").a(9154376).a(m).a(0.8F, 0.9F);
/*  85 */   public static final BiomeBase RIVER = new BiomeRiver(7).b(255).a("River").a(b);
/*     */   
/*  87 */   public static final BiomeBase HELL = new BiomeHell(8).b(16711680).a("Hell").b().a(2.0F, 0.0F);
/*  88 */   public static final BiomeBase SKY = new BiomeTheEnd(9).b(8421631).a("The End").b();
/*     */   
/*  90 */   public static final BiomeBase FROZEN_OCEAN = new BiomeOcean(10).b(9474208).a("FrozenOcean").c().a(c).a(0.0F, 0.5F);
/*  91 */   public static final BiomeBase FROZEN_RIVER = new BiomeRiver(11).b(10526975).a("FrozenRiver").c().a(b).a(0.0F, 0.5F);
/*  92 */   public static final BiomeBase ICE_PLAINS = new BiomeIcePlains(12, false).b(16777215).a("Ice Plains").c().a(0.0F, 0.5F).a(e);
/*  93 */   public static final BiomeBase ICE_MOUNTAINS = new BiomeIcePlains(13, false).b(10526880).a("Ice Mountains").c().a(g).a(0.0F, 0.5F);
/*     */   
/*  95 */   public static final BiomeBase MUSHROOM_ISLAND = new BiomeMushrooms(14).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).a(l);
/*  96 */   public static final BiomeBase MUSHROOM_SHORE = new BiomeMushrooms(15).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).a(j);
/*     */   
/*  98 */   public static final BiomeBase BEACH = new BiomeBeach(16).b(16440917).a("Beach").a(0.8F, 0.4F).a(j);
/*  99 */   public static final BiomeBase DESERT_HILLS = new BiomeDesert(17).b(13786898).a("DesertHills").b().a(2.0F, 0.0F).a(g);
/* 100 */   public static final BiomeBase FOREST_HILLS = new BiomeForest(18, 0).b(2250012).a("ForestHills").a(g);
/* 101 */   public static final BiomeBase TAIGA_HILLS = new BiomeTaiga(19, 0).b(1456435).a("TaigaHills").a(5159473).a(0.25F, 0.8F).a(g);
/* 102 */   public static final BiomeBase SMALL_MOUNTAINS = new BiomeBigHills(20, true).b(7501978).a("Extreme Hills Edge").a(i.a()).a(0.2F, 0.3F);
/*     */   
/* 104 */   public static final BiomeBase JUNGLE = new BiomeJungle(21, false).b(5470985).a("Jungle").a(5470985).a(0.95F, 0.9F);
/* 105 */   public static final BiomeBase JUNGLE_HILLS = new BiomeJungle(22, false).b(2900485).a("JungleHills").a(5470985).a(0.95F, 0.9F).a(g);
/* 106 */   public static final BiomeBase JUNGLE_EDGE = new BiomeJungle(23, true).b(6458135).a("JungleEdge").a(5470985).a(0.95F, 0.8F);
/*     */   
/* 108 */   public static final BiomeBase DEEP_OCEAN = new BiomeOcean(24).b(48).a("Deep Ocean").a(d);
/* 109 */   public static final BiomeBase STONE_BEACH = new BiomeStoneBeach(25).b(10658436).a("Stone Beach").a(0.2F, 0.3F).a(k);
/* 110 */   public static final BiomeBase COLD_BEACH = new BiomeBeach(26).b(16445632).a("Cold Beach").a(0.05F, 0.3F).a(j).c();
/*     */   
/* 112 */   public static final BiomeBase BIRCH_FOREST = new BiomeForest(27, 2).a("Birch Forest").b(3175492);
/* 113 */   public static final BiomeBase BIRCH_FOREST_HILLS = new BiomeForest(28, 2).a("Birch Forest Hills").b(2055986).a(g);
/* 114 */   public static final BiomeBase ROOFED_FOREST = new BiomeForest(29, 3).b(4215066).a("Roofed Forest");
/*     */   
/* 116 */   public static final BiomeBase COLD_TAIGA = new BiomeTaiga(30, 0).b(3233098).a("Cold Taiga").a(5159473).c().a(-0.5F, 0.4F).a(f).c(16777215);
/* 117 */   public static final BiomeBase COLD_TAIGA_HILLS = new BiomeTaiga(31, 0).b(2375478).a("Cold Taiga Hills").a(5159473).c().a(-0.5F, 0.4F).a(g).c(16777215);
/* 118 */   public static final BiomeBase MEGA_TAIGA = new BiomeTaiga(32, 1).b(5858897).a("Mega Taiga").a(5159473).a(0.3F, 0.8F).a(f);
/* 119 */   public static final BiomeBase MEGA_TAIGA_HILLS = new BiomeTaiga(33, 1).b(4542270).a("Mega Taiga Hills").a(5159473).a(0.3F, 0.8F).a(g);
/*     */   
/* 121 */   public static final BiomeBase EXTREME_HILLS_PLUS = new BiomeBigHills(34, true).b(5271632).a("Extreme Hills+").a(i).a(0.2F, 0.3F);
/*     */   
/* 123 */   public static final BiomeBase SAVANNA = new BiomeSavanna(35).b(12431967).a("Savanna").a(1.2F, 0.0F).b().a(e);
/* 124 */   public static final BiomeBase SAVANNA_PLATEAU = new BiomeSavanna(36).b(10984804).a("Savanna Plateau").a(1.0F, 0.0F).b().a(h);
/*     */   
/* 126 */   public static final BiomeBase MESA = new BiomeMesa(37, false, false).b(14238997).a("Mesa");
/* 127 */   public static final BiomeBase MESA_PLATEAU_F = new BiomeMesa(38, false, true).b(11573093).a("Mesa Plateau F").a(h);
/* 128 */   public static final BiomeBase MESA_PLATEAU = new BiomeMesa(39, false, false).b(13274213).a("Mesa Plateau").a(h);
/*     */   
/* 130 */   public static final BiomeBase ad = OCEAN;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 136 */     PLAINS.k();
/* 137 */     DESERT.k();
/* 138 */     FOREST.k();
/* 139 */     TAIGA.k();
/* 140 */     SWAMPLAND.k();
/* 141 */     ICE_PLAINS.k();
/* 142 */     JUNGLE.k();
/* 143 */     JUNGLE_EDGE.k();
/* 144 */     COLD_TAIGA.k();
/* 145 */     SAVANNA.k();
/* 146 */     SAVANNA_PLATEAU.k();
/* 147 */     MESA.k();
/* 148 */     MESA_PLATEAU_F.k();
/* 149 */     MESA_PLATEAU.k();
/* 150 */     BIRCH_FOREST.k();
/* 151 */     BIRCH_FOREST_HILLS.k();
/* 152 */     ROOFED_FOREST.k();
/* 153 */     MEGA_TAIGA.k();
/* 154 */     EXTREME_HILLS.k();
/* 155 */     EXTREME_HILLS_PLUS.k();
/*     */     
/*     */ 
/* 158 */     MEGA_TAIGA.d(MEGA_TAIGA_HILLS.id + 128).a("Redwood Taiga Hills M");
/*     */     
/* 160 */     for (BiomeBase localBiomeBase : biomes) {
/* 161 */       if (localBiomeBase != null) {
/* 162 */         if (o.containsKey(localBiomeBase.ah)) {
/* 163 */           throw new Error("Biome \"" + localBiomeBase.ah + "\" is defined as both ID " + ((BiomeBase)o.get(localBiomeBase.ah)).id + " and " + localBiomeBase.id);
/*     */         }
/*     */         
/* 166 */         o.put(localBiomeBase.ah, localBiomeBase);
/*     */         
/* 168 */         if (localBiomeBase.id < 128) {
/* 169 */           n.add(localBiomeBase);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 174 */     n.remove(HELL);
/* 175 */     n.remove(SKY);
/* 176 */     n.remove(FROZEN_OCEAN);
/* 177 */     n.remove(SMALL_MOUNTAINS);
/*     */   }
/*     */   
/* 180 */   protected static final NoiseGenerator3 ae = new NoiseGenerator3(new Random(1234L), 1);
/* 181 */   protected static final NoiseGenerator3 af = new NoiseGenerator3(new Random(2345L), 1);
/* 182 */   protected static final WorldGenTallPlant ag = new WorldGenTallPlant();
/*     */   public String ah;
/*     */   public int ai;
/*     */   public int aj;
/* 186 */   public IBlockData ak = Blocks.GRASS.getBlockData();
/* 187 */   public IBlockData al = Blocks.DIRT.getBlockData();
/* 188 */   public int am = 5169201;
/* 189 */   public float an = a.a;
/* 190 */   public float ao = a.b;
/* 191 */   public float temperature = 0.5F;
/* 192 */   public float humidity = 0.5F;
/* 193 */   public int ar = 16777215;
/*     */   
/*     */   public BiomeDecorator as;
/*     */   
/* 197 */   protected List<BiomeMeta> at = Lists.newArrayList();
/* 198 */   protected List<BiomeMeta> au = Lists.newArrayList();
/* 199 */   protected List<BiomeMeta> av = Lists.newArrayList();
/* 200 */   protected List<BiomeMeta> aw = Lists.newArrayList();
/*     */   protected boolean ax;
/* 202 */   protected boolean ay = true;
/*     */   public final int id;
/*     */   
/*     */   protected BiomeBase(int paramInt)
/*     */   {
/* 207 */     this.id = paramInt;
/* 208 */     biomes[paramInt] = this;
/* 209 */     this.as = a();
/*     */     
/* 211 */     this.au.add(new BiomeMeta(EntitySheep.class, 12, 4, 4));
/* 212 */     this.au.add(new BiomeMeta(EntityRabbit.class, 10, 3, 3));
/* 213 */     this.au.add(new BiomeMeta(EntityPig.class, 10, 4, 4));
/* 214 */     this.au.add(new BiomeMeta(EntityChicken.class, 10, 4, 4));
/* 215 */     this.au.add(new BiomeMeta(EntityCow.class, 8, 4, 4));
/*     */     
/* 217 */     this.at.add(new BiomeMeta(EntitySpider.class, 100, 4, 4));
/* 218 */     this.at.add(new BiomeMeta(EntityZombie.class, 100, 4, 4));
/* 219 */     this.at.add(new BiomeMeta(EntitySkeleton.class, 100, 4, 4));
/* 220 */     this.at.add(new BiomeMeta(EntityCreeper.class, 100, 4, 4));
/* 221 */     this.at.add(new BiomeMeta(EntitySlime.class, 100, 4, 4));
/* 222 */     this.at.add(new BiomeMeta(EntityEnderman.class, 10, 1, 4));
/* 223 */     this.at.add(new BiomeMeta(EntityWitch.class, 5, 1, 1));
/*     */     
/*     */ 
/*     */ 
/* 227 */     this.av.add(new BiomeMeta(EntitySquid.class, 10, 4, 4));
/*     */     
/* 229 */     this.aw.add(new BiomeMeta(EntityBat.class, 10, 8, 8));
/*     */   }
/*     */   
/*     */   protected BiomeDecorator a() {
/* 233 */     return new BiomeDecorator();
/*     */   }
/*     */   
/*     */   protected BiomeBase a(float paramFloat1, float paramFloat2) {
/* 237 */     if ((paramFloat1 > 0.1F) && (paramFloat1 < 0.2F)) {
/* 238 */       throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/*     */     }
/*     */     
/* 241 */     this.temperature = paramFloat1;
/* 242 */     this.humidity = paramFloat2;
/* 243 */     return this;
/*     */   }
/*     */   
/*     */   protected final BiomeBase a(BiomeTemperature paramBiomeTemperature) {
/* 247 */     this.an = paramBiomeTemperature.a;
/* 248 */     this.ao = paramBiomeTemperature.b;
/* 249 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase b() {
/* 253 */     this.ay = false;
/* 254 */     return this;
/*     */   }
/*     */   
/* 257 */   protected WorldGenTrees aA = new WorldGenTrees(false);
/* 258 */   protected WorldGenBigTree aB = new WorldGenBigTree(false);
/* 259 */   protected WorldGenSwampTree aC = new WorldGenSwampTree();
/*     */   
/*     */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 262 */     if (paramRandom.nextInt(10) == 0) {
/* 263 */       return this.aB;
/*     */     }
/* 265 */     return this.aA;
/*     */   }
/*     */   
/*     */   public WorldGenerator b(Random paramRandom) {
/* 269 */     return new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS);
/*     */   }
/*     */   
/*     */   public BlockFlowers.EnumFlowerVarient a(Random paramRandom, BlockPosition paramBlockPosition) {
/* 273 */     if (paramRandom.nextInt(3) > 0) {
/* 274 */       return BlockFlowers.EnumFlowerVarient.DANDELION;
/*     */     }
/* 276 */     return BlockFlowers.EnumFlowerVarient.POPPY;
/*     */   }
/*     */   
/*     */   protected BiomeBase c() {
/* 280 */     this.ax = true;
/* 281 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(String paramString) {
/* 285 */     this.ah = paramString;
/* 286 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int paramInt) {
/* 290 */     this.am = paramInt;
/* 291 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase b(int paramInt) {
/* 295 */     a(paramInt, false);
/* 296 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase c(int paramInt) {
/* 300 */     this.aj = paramInt;
/* 301 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeBase a(int paramInt, boolean paramBoolean) {
/* 305 */     this.ai = paramInt;
/* 306 */     if (paramBoolean) {
/* 307 */       this.aj = ((paramInt & 0xFEFEFE) >> 1);
/*     */     } else {
/* 309 */       this.aj = paramInt;
/*     */     }
/* 311 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<BiomeMeta> getMobs(EnumCreatureType paramEnumCreatureType)
/*     */   {
/* 321 */     switch (1.switchMap[paramEnumCreatureType.ordinal()]) {
/*     */     case 1: 
/* 323 */       return this.at;
/*     */     case 2: 
/* 325 */       return this.au;
/*     */     case 3: 
/* 327 */       return this.av;
/*     */     case 4: 
/* 329 */       return this.aw;
/*     */     }
/* 331 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public static class BiomeMeta extends WeightedRandom.WeightedRandomChoice {
/*     */     public Class<? extends EntityInsentient> b;
/*     */     public int c;
/*     */     public int d;
/*     */     
/*     */     public BiomeMeta(Class<? extends EntityInsentient> paramClass, int paramInt1, int paramInt2, int paramInt3) {
/* 340 */       super();
/* 341 */       this.b = paramClass;
/* 342 */       this.c = paramInt2;
/* 343 */       this.d = paramInt3;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 348 */       return this.b.getSimpleName() + "*(" + this.c + "-" + this.d + "):" + this.a;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 353 */     return j();
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 357 */     if (j()) {
/* 358 */       return false;
/*     */     }
/* 360 */     return this.ay;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 364 */     return this.humidity > 0.85F;
/*     */   }
/*     */   
/*     */   public float g() {
/* 368 */     return 0.1F;
/*     */   }
/*     */   
/*     */   public final int h() {
/* 372 */     return (int)(this.humidity * 65536.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final float a(BlockPosition paramBlockPosition)
/*     */   {
/* 380 */     if (paramBlockPosition.getY() > 64) {
/* 381 */       float f1 = (float)(ae.a(paramBlockPosition.getX() * 1.0D / 8.0D, paramBlockPosition.getZ() * 1.0D / 8.0D) * 4.0D);
/* 382 */       return this.temperature - (f1 + paramBlockPosition.getY() - 64.0F) * 0.05F / 30.0F;
/*     */     }
/* 384 */     return this.temperature;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition) {
/* 388 */     this.as.a(paramWorld, paramRandom, this, paramBlockPosition);
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
/*     */   public boolean j()
/*     */   {
/* 406 */     return this.ax;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble) {
/* 410 */     b(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*     */   }
/*     */   
/*     */   public final void b(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble) {
/* 414 */     int i1 = paramWorld.F();
/* 415 */     IBlockData localIBlockData1 = this.ak;
/* 416 */     IBlockData localIBlockData2 = this.al;
/* 417 */     int i2 = -1;
/* 418 */     int i3 = (int)(paramDouble / 3.0D + 3.0D + paramRandom.nextDouble() * 0.25D);
/*     */     
/* 420 */     int i4 = paramInt1 & 0xF;
/* 421 */     int i5 = paramInt2 & 0xF;
/* 422 */     BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 423 */     for (int i6 = 255; i6 >= 0; i6--) {
/* 424 */       if (i6 <= paramRandom.nextInt(5)) {
/* 425 */         paramChunkSnapshot.a(i5, i6, i4, Blocks.BEDROCK.getBlockData());
/*     */       } else {
/* 427 */         IBlockData localIBlockData3 = paramChunkSnapshot.a(i5, i6, i4);
/*     */         
/* 429 */         if (localIBlockData3.getBlock().getMaterial() == Material.AIR) {
/* 430 */           i2 = -1;
/* 431 */         } else if (localIBlockData3.getBlock() == Blocks.STONE) {
/* 432 */           if (i2 == -1) {
/* 433 */             if (i3 <= 0) {
/* 434 */               localIBlockData1 = null;
/* 435 */               localIBlockData2 = Blocks.STONE.getBlockData();
/* 436 */             } else if ((i6 >= i1 - 4) && (i6 <= i1 + 1)) {
/* 437 */               localIBlockData1 = this.ak;
/* 438 */               localIBlockData2 = this.al;
/*     */             }
/*     */             
/* 441 */             if ((i6 < i1) && ((localIBlockData1 == null) || (localIBlockData1.getBlock().getMaterial() == Material.AIR))) {
/* 442 */               if (a(localMutableBlockPosition.c(paramInt1, i6, paramInt2)) < 0.15F) {
/* 443 */                 localIBlockData1 = Blocks.ICE.getBlockData();
/*     */               } else {
/* 445 */                 localIBlockData1 = Blocks.WATER.getBlockData();
/*     */               }
/*     */             }
/*     */             
/* 449 */             i2 = i3;
/* 450 */             if (i6 >= i1 - 1) {
/* 451 */               paramChunkSnapshot.a(i5, i6, i4, localIBlockData1);
/* 452 */             } else if (i6 < i1 - 7 - i3) {
/* 453 */               localIBlockData1 = null;
/* 454 */               localIBlockData2 = Blocks.STONE.getBlockData();
/* 455 */               paramChunkSnapshot.a(i5, i6, i4, Blocks.GRAVEL.getBlockData());
/*     */             } else {
/* 457 */               paramChunkSnapshot.a(i5, i6, i4, localIBlockData2);
/*     */             }
/* 459 */           } else if (i2 > 0) {
/* 460 */             i2--;
/* 461 */             paramChunkSnapshot.a(i5, i6, i4, localIBlockData2);
/*     */             
/*     */ 
/* 464 */             if ((i2 == 0) && (localIBlockData2.getBlock() == Blocks.SAND)) {
/* 465 */               i2 = paramRandom.nextInt(4) + Math.max(0, i6 - 63);
/* 466 */               localIBlockData2 = localIBlockData2.get(BlockSand.VARIANT) == BlockSand.EnumSandVariant.RED_SAND ? Blocks.RED_SANDSTONE.getBlockData() : Blocks.SANDSTONE.getBlockData();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected BiomeBase k() {
/* 475 */     return d(this.id + 128);
/*     */   }
/*     */   
/*     */   protected BiomeBase d(int paramInt) {
/* 479 */     return new BiomeBaseSub(paramInt, this);
/*     */   }
/*     */   
/*     */   public Class<? extends BiomeBase> l() {
/* 483 */     return getClass();
/*     */   }
/*     */   
/*     */   public boolean a(BiomeBase paramBiomeBase) {
/* 487 */     if (paramBiomeBase == this) {
/* 488 */       return true;
/*     */     }
/* 490 */     if (paramBiomeBase == null) {
/* 491 */       return false;
/*     */     }
/* 493 */     return l() == paramBiomeBase.l();
/*     */   }
/*     */   
/*     */   public EnumTemperature m() {
/* 497 */     if (this.temperature < 0.2D) {
/* 498 */       return EnumTemperature.COLD;
/*     */     }
/* 500 */     if (this.temperature < 1.0D) {
/* 501 */       return EnumTemperature.MEDIUM;
/*     */     }
/* 503 */     return EnumTemperature.WARM;
/*     */   }
/*     */   
/*     */   public static BiomeBase[] getBiomes() {
/* 507 */     return biomes;
/*     */   }
/*     */   
/*     */   public static BiomeBase getBiome(int paramInt) {
/* 511 */     return getBiome(paramInt, null);
/*     */   }
/*     */   
/*     */   public static BiomeBase getBiome(int paramInt, BiomeBase paramBiomeBase) {
/* 515 */     if ((paramInt < 0) || (paramInt > biomes.length)) {
/* 516 */       aD.warn("Biome ID is out of bounds: " + paramInt + ", defaulting to 0 (Ocean)");
/* 517 */       return OCEAN;
/*     */     }
/* 519 */     BiomeBase localBiomeBase = biomes[paramInt];
/* 520 */     if (localBiomeBase == null) {
/* 521 */       return paramBiomeBase;
/*     */     }
/* 523 */     return localBiomeBase;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */