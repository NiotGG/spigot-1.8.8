/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderFlat
/*     */   implements IChunkProvider
/*     */ {
/*     */   private World a;
/*     */   private Random b;
/*  31 */   private final IBlockData[] c = new IBlockData['Ā'];
/*     */   private final WorldGenFlatInfo d;
/*  33 */   private final List<StructureGenerator> e = Lists.newArrayList();
/*     */   private final boolean f;
/*     */   private final boolean g;
/*     */   private WorldGenLakes h;
/*     */   private WorldGenLakes i;
/*     */   
/*     */   public ChunkProviderFlat(World paramWorld, long paramLong, boolean paramBoolean, String paramString) {
/*  40 */     this.a = paramWorld;
/*  41 */     this.b = new Random(paramLong);
/*  42 */     this.d = WorldGenFlatInfo.a(paramString);
/*     */     
/*  44 */     if (paramBoolean) {
/*  45 */       Map localMap1 = this.d.b();
/*     */       
/*  47 */       if (localMap1.containsKey("village")) {
/*  48 */         Map localMap2 = (Map)localMap1.get("village");
/*  49 */         if (!localMap2.containsKey("size")) {
/*  50 */           localMap2.put("size", "1");
/*     */         }
/*  52 */         this.e.add(new WorldGenVillage(localMap2));
/*     */       }
/*     */       
/*  55 */       if (localMap1.containsKey("biome_1")) {
/*  56 */         this.e.add(new WorldGenLargeFeature((Map)localMap1.get("biome_1")));
/*     */       }
/*  58 */       if (localMap1.containsKey("mineshaft")) {
/*  59 */         this.e.add(new WorldGenMineshaft((Map)localMap1.get("mineshaft")));
/*     */       }
/*  61 */       if (localMap1.containsKey("stronghold")) {
/*  62 */         this.e.add(new WorldGenStronghold((Map)localMap1.get("stronghold")));
/*     */       }
/*  64 */       if (localMap1.containsKey("oceanmonument")) {
/*  65 */         this.e.add(new WorldGenMonument((Map)localMap1.get("oceanmonument")));
/*     */       }
/*     */     }
/*     */     
/*  69 */     if (this.d.b().containsKey("lake")) {
/*  70 */       this.h = new WorldGenLakes(Blocks.WATER);
/*     */     }
/*  72 */     if (this.d.b().containsKey("lava_lake")) {
/*  73 */       this.i = new WorldGenLakes(Blocks.LAVA);
/*     */     }
/*  75 */     this.g = this.d.b().containsKey("dungeon");
/*     */     
/*  77 */     int j = 0;
/*  78 */     int k = 0;
/*  79 */     int m = 1;
/*  80 */     for (WorldGenFlatLayerInfo localWorldGenFlatLayerInfo : this.d.c()) {
/*  81 */       for (int n = localWorldGenFlatLayerInfo.d(); n < localWorldGenFlatLayerInfo.d() + localWorldGenFlatLayerInfo.b(); n++) {
/*  82 */         IBlockData localIBlockData = localWorldGenFlatLayerInfo.c();
/*  83 */         if (localIBlockData.getBlock() != Blocks.AIR)
/*     */         {
/*     */ 
/*  86 */           m = 0;
/*  87 */           this.c[n] = localIBlockData;
/*     */         } }
/*  89 */       if (localWorldGenFlatLayerInfo.c().getBlock() == Blocks.AIR) {
/*  90 */         k += localWorldGenFlatLayerInfo.b();
/*     */       } else {
/*  92 */         j += localWorldGenFlatLayerInfo.b() + k;
/*  93 */         k = 0;
/*     */       }
/*     */     }
/*  96 */     paramWorld.b(j);
/*     */     
/*  98 */     this.f = (m != 0 ? false : this.d.b().containsKey("decoration"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/* 108 */     ChunkSnapshot localChunkSnapshot = new ChunkSnapshot();
/*     */     
/* 110 */     for (int j = 0; j < this.c.length; j++) {
/* 111 */       localObject2 = this.c[j];
/* 112 */       if (localObject2 != null)
/*     */       {
/*     */ 
/*     */ 
/* 116 */         for (int k = 0; k < 16; k++) {
/* 117 */           for (m = 0; m < 16; m++) {
/* 118 */             localChunkSnapshot.a(k, j, m, (IBlockData)localObject2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 123 */     for (Object localObject1 = this.e.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (WorldGenBase)((Iterator)localObject1).next();
/* 124 */       ((WorldGenBase)localObject2).a(this, this.a, paramInt1, paramInt2, localChunkSnapshot);
/*     */     }
/*     */     
/* 127 */     localObject1 = new Chunk(this.a, localChunkSnapshot, paramInt1, paramInt2);
/* 128 */     Object localObject2 = this.a.getWorldChunkManager().getBiomeBlock(null, paramInt1 * 16, paramInt2 * 16, 16, 16);
/* 129 */     byte[] arrayOfByte = ((Chunk)localObject1).getBiomeIndex();
/*     */     
/* 131 */     for (int m = 0; m < arrayOfByte.length; m++) {
/* 132 */       arrayOfByte[m] = ((byte)localObject2[m].id);
/*     */     }
/*     */     
/* 135 */     ((Chunk)localObject1).initLighting();
/*     */     
/* 137 */     return (Chunk)localObject1;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int paramInt1, int paramInt2)
/*     */   {
/* 142 */     return true;
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2)
/*     */   {
/* 147 */     int j = paramInt1 * 16;
/* 148 */     int k = paramInt2 * 16;
/* 149 */     BlockPosition localBlockPosition = new BlockPosition(j, 0, k);
/* 150 */     BiomeBase localBiomeBase = this.a.getBiome(new BlockPosition(j + 16, 0, k + 16));
/* 151 */     boolean bool1 = false;
/*     */     
/* 153 */     this.b.setSeed(this.a.getSeed());
/* 154 */     long l1 = this.b.nextLong() / 2L * 2L + 1L;
/* 155 */     long l2 = this.b.nextLong() / 2L * 2L + 1L;
/* 156 */     this.b.setSeed(paramInt1 * l1 + paramInt2 * l2 ^ this.a.getSeed());
/*     */     
/* 158 */     ChunkCoordIntPair localChunkCoordIntPair = new ChunkCoordIntPair(paramInt1, paramInt2);
/*     */     
/* 160 */     for (Object localObject = this.e.iterator(); ((Iterator)localObject).hasNext();) { StructureGenerator localStructureGenerator = (StructureGenerator)((Iterator)localObject).next();
/* 161 */       boolean bool2 = localStructureGenerator.a(this.a, this.b, localChunkCoordIntPair);
/* 162 */       if ((localStructureGenerator instanceof WorldGenVillage)) {
/* 163 */         bool1 |= bool2;
/*     */       }
/*     */     }
/*     */     
/* 167 */     if ((this.h != null) && (!bool1) && (this.b.nextInt(4) == 0)) {
/* 168 */       this.h.generate(this.a, this.b, localBlockPosition.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
/*     */     }
/*     */     
/* 171 */     if ((this.i != null) && (!bool1) && (this.b.nextInt(8) == 0)) {
/* 172 */       localObject = localBlockPosition.a(this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(248) + 8), this.b.nextInt(16) + 8);
/* 173 */       if ((((BlockPosition)localObject).getY() < this.a.F()) || (this.b.nextInt(10) == 0)) {
/* 174 */         this.i.generate(this.a, this.b, (BlockPosition)localObject);
/*     */       }
/*     */     }
/*     */     
/* 178 */     if (this.g) {
/* 179 */       for (int m = 0; m < 8; m++) {
/* 180 */         new WorldGenDungeons().generate(this.a, this.b, localBlockPosition.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
/*     */       }
/*     */     }
/*     */     
/* 184 */     if (this.f) {
/* 185 */       localBiomeBase.a(this.a, this.b, localBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(IChunkProvider paramIChunkProvider, Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 196 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c() {}
/*     */   
/*     */ 
/*     */   public boolean unloadChunks()
/*     */   {
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave()
/*     */   {
/* 210 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 215 */     return "FlatLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType paramEnumCreatureType, BlockPosition paramBlockPosition)
/*     */   {
/* 220 */     BiomeBase localBiomeBase = this.a.getBiome(paramBlockPosition);
/* 221 */     return localBiomeBase.getMobs(paramEnumCreatureType);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(World paramWorld, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 226 */     if ("Stronghold".equals(paramString)) {
/* 227 */       for (StructureGenerator localStructureGenerator : this.e) {
/* 228 */         if ((localStructureGenerator instanceof WorldGenStronghold)) {
/* 229 */           return localStructureGenerator.getNearestGeneratedFeature(paramWorld, paramBlockPosition);
/*     */         }
/*     */       }
/*     */     }
/* 233 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks()
/*     */   {
/* 238 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 243 */     for (StructureGenerator localStructureGenerator : this.e) {
/* 244 */       localStructureGenerator.a(this, this.a, paramInt1, paramInt2, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(BlockPosition paramBlockPosition)
/*     */   {
/* 250 */     return getOrCreateChunk(paramBlockPosition.getX() >> 4, paramBlockPosition.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkProviderFlat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */