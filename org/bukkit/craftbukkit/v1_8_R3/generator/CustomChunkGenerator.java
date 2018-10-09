/*     */ package org.bukkit.craftbukkit.v1_8_R3.generator;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.v1_8_R3.BiomeBase;
/*     */ import net.minecraft.server.v1_8_R3.Block;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.Chunk;
/*     */ import net.minecraft.server.v1_8_R3.ChunkSection;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ 
/*     */ public class CustomChunkGenerator extends InternalChunkGenerator
/*     */ {
/*     */   private final ChunkGenerator generator;
/*     */   private final WorldServer world;
/*     */   private final Random random;
/*  17 */   private final net.minecraft.server.v1_8_R3.WorldGenStronghold strongholdGen = new net.minecraft.server.v1_8_R3.WorldGenStronghold();
/*     */   
/*     */   private static class CustomBiomeGrid implements org.bukkit.generator.ChunkGenerator.BiomeGrid {
/*     */     BiomeBase[] biome;
/*     */     
/*     */     public org.bukkit.block.Biome getBiome(int x, int z) {
/*  23 */       return org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.biomeBaseToBiome(this.biome[(z << 4 | x)]);
/*     */     }
/*     */     
/*     */     public void setBiome(int x, int z, org.bukkit.block.Biome bio) {
/*  27 */       this.biome[(z << 4 | x)] = org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.biomeToBiomeBase(bio);
/*     */     }
/*     */   }
/*     */   
/*     */   public CustomChunkGenerator(net.minecraft.server.v1_8_R3.World world, long seed, ChunkGenerator generator) {
/*  32 */     this.world = ((WorldServer)world);
/*  33 */     this.generator = generator;
/*     */     
/*  35 */     this.random = new Random(seed);
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int x, int z) {
/*  39 */     return true;
/*     */   }
/*     */   
/*     */   public Chunk getOrCreateChunk(int x, int z) {
/*  43 */     this.random.setSeed(x * 341873128712L + z * 132897987541L);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  48 */     CustomBiomeGrid biomegrid = new CustomBiomeGrid(null);
/*  49 */     biomegrid.biome = new BiomeBase['Ā'];
/*  50 */     this.world.getWorldChunkManager().getBiomeBlock(biomegrid.biome, x << 4, z << 4, 16, 16);
/*     */     
/*     */ 
/*  53 */     CraftChunkData data = (CraftChunkData)this.generator.generateChunkData(this.world.getWorld(), this.random, x, z, biomegrid);
/*  54 */     Chunk chunk; if (data != null) {
/*  55 */       char[][] sections = data.getRawChunkData();
/*  56 */       Chunk chunk = new Chunk(this.world, x, z);
/*     */       
/*  58 */       ChunkSection[] csect = chunk.getSections();
/*  59 */       int scnt = Math.min(csect.length, sections.length);
/*     */       
/*     */ 
/*  62 */       for (int sec = 0; sec < scnt; sec++) {
/*  63 */         if (sections[sec] != null)
/*     */         {
/*     */ 
/*  66 */           char[] section = sections[sec];
/*  67 */           char emptyTest = '\000';
/*  68 */           for (int i = 0; i < 4096; i++)
/*     */           {
/*  70 */             if (Block.d.a(section[i]) == null) {
/*  71 */               section[i] = '\000';
/*     */             }
/*  73 */             emptyTest = (char)(emptyTest | section[i]);
/*     */           }
/*     */           
/*  76 */           if (emptyTest != 0) {
/*  77 */             csect[sec] = new ChunkSection(sec << 4, true, section);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*  83 */       short[][] xbtypes = this.generator.generateExtBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
/*  84 */       if (xbtypes != null) {
/*  85 */         Chunk chunk = new Chunk(this.world, x, z);
/*     */         
/*  87 */         ChunkSection[] csect = chunk.getSections();
/*  88 */         int scnt = Math.min(csect.length, xbtypes.length);
/*     */         
/*     */ 
/*  91 */         for (int sec = 0; sec < scnt; sec++) {
/*  92 */           if (xbtypes[sec] != null)
/*     */           {
/*     */ 
/*  95 */             char[] secBlkID = new char['က'];
/*  96 */             short[] bdata = xbtypes[sec];
/*  97 */             for (int i = 0; i < bdata.length; i++) {
/*  98 */               Block b = Block.getById(bdata[i]);
/*  99 */               secBlkID[i] = ((char)Block.d.b(b.getBlockData()));
/*     */             }
/*     */             
/* 102 */             csect[sec] = new ChunkSection(sec << 4, true, secBlkID);
/*     */           }
/*     */         }
/*     */       } else {
/* 106 */         byte[][] btypes = this.generator.generateBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
/*     */         
/* 108 */         if (btypes != null) {
/* 109 */           Chunk chunk = new Chunk(this.world, x, z);
/*     */           
/* 111 */           ChunkSection[] csect = chunk.getSections();
/* 112 */           int scnt = Math.min(csect.length, btypes.length);
/*     */           
/* 114 */           for (int sec = 0; sec < scnt; sec++) {
/* 115 */             if (btypes[sec] != null)
/*     */             {
/*     */ 
/*     */ 
/* 119 */               char[] secBlkID = new char['က'];
/* 120 */               for (int i = 0; i < secBlkID.length; i++) {
/* 121 */                 Block b = Block.getById(btypes[sec][i] & 0xFF);
/* 122 */                 secBlkID[i] = ((char)Block.d.b(b.getBlockData()));
/*     */               }
/* 124 */               csect[sec] = new ChunkSection(sec << 4, true, secBlkID);
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 129 */           byte[] types = this.generator.generate(this.world.getWorld(), this.random, x, z);
/* 130 */           int ydim = types.length / 256;
/* 131 */           int scnt = ydim / 16;
/*     */           
/* 133 */           chunk = new Chunk(this.world, x, z);
/*     */           
/* 135 */           ChunkSection[] csect = chunk.getSections();
/*     */           
/* 137 */           scnt = Math.min(scnt, csect.length);
/*     */           
/* 139 */           for (int sec = 0; sec < scnt; sec++) {
/* 140 */             ChunkSection cs = null;
/* 141 */             char[] csbytes = null;
/*     */             
/* 143 */             for (int cy = 0; cy < 16; cy++) {
/* 144 */               int cyoff = cy | sec << 4;
/*     */               
/* 146 */               for (int cx = 0; cx < 16; cx++) {
/* 147 */                 int cxyoff = cx * ydim * 16 + cyoff;
/*     */                 
/* 149 */                 for (int cz = 0; cz < 16; cz++) {
/* 150 */                   byte blk = types[(cxyoff + cz * ydim)];
/*     */                   
/* 152 */                   if (blk != 0) {
/* 153 */                     if (cs == null) {
/* 154 */                       cs = csect[sec] = new ChunkSection(sec << 4, true);
/* 155 */                       csbytes = cs.getIdArray();
/*     */                     }
/*     */                     
/* 158 */                     Block b = Block.getById(blk & 0xFF);
/* 159 */                     csbytes[(cy << 8 | cz << 4 | cx)] = ((char)Block.d.b(b.getBlockData()));
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 165 */             if (cs != null) {
/* 166 */               cs.recalcBlockCounts();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 173 */     byte[] biomeIndex = chunk.getBiomeIndex();
/* 174 */     for (int i = 0; i < biomeIndex.length; i++) {
/* 175 */       biomeIndex[i] = ((byte)(biomegrid.biome[i].id & 0xFF));
/*     */     }
/*     */     
/* 178 */     chunk.initLighting();
/*     */     
/* 180 */     return chunk;
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(BlockPosition blockPosition)
/*     */   {
/* 185 */     return getChunkAt(blockPosition.getX() >> 4, blockPosition.getZ() >> 4);
/*     */   }
/*     */   
/*     */ 
/*     */   public void getChunkAt(net.minecraft.server.v1_8_R3.IChunkProvider icp, int i, int i1) {}
/*     */   
/*     */ 
/*     */   public boolean a(net.minecraft.server.v1_8_R3.IChunkProvider iChunkProvider, Chunk chunk, int i, int i1)
/*     */   {
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean bln, net.minecraft.server.v1_8_R3.IProgressUpdate ipu) {
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   public boolean unloadChunks() {
/* 202 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave() {
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   public byte[] generate(org.bukkit.World world, Random random, int x, int z)
/*     */   {
/* 211 */     return this.generator.generate(world, random, x, z);
/*     */   }
/*     */   
/*     */   public byte[][] generateBlockSections(org.bukkit.World world, Random random, int x, int z, org.bukkit.generator.ChunkGenerator.BiomeGrid biomes) {
/* 215 */     return this.generator.generateBlockSections(world, random, x, z, biomes);
/*     */   }
/*     */   
/*     */   public short[][] generateExtBlockSections(org.bukkit.World world, Random random, int x, int z, org.bukkit.generator.ChunkGenerator.BiomeGrid biomes) {
/* 219 */     return this.generator.generateExtBlockSections(world, random, x, z, biomes);
/*     */   }
/*     */   
/*     */   public Chunk getChunkAt(int x, int z) {
/* 223 */     return getOrCreateChunk(x, z);
/*     */   }
/*     */   
/*     */   public boolean canSpawn(org.bukkit.World world, int x, int z)
/*     */   {
/* 228 */     return this.generator.canSpawn(world, x, z);
/*     */   }
/*     */   
/*     */   public java.util.List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(org.bukkit.World world)
/*     */   {
/* 233 */     return this.generator.getDefaultPopulators(world);
/*     */   }
/*     */   
/*     */   public java.util.List<net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta> getMobsFor(net.minecraft.server.v1_8_R3.EnumCreatureType type, BlockPosition position)
/*     */   {
/* 238 */     BiomeBase biomebase = this.world.getBiome(position);
/*     */     
/* 240 */     return biomebase == null ? null : biomebase.getMobs(type);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(net.minecraft.server.v1_8_R3.World world, String type, BlockPosition position)
/*     */   {
/* 245 */     return ("Stronghold".equals(type)) && (this.strongholdGen != null) ? this.strongholdGen.getNearestGeneratedFeature(world, position) : null;
/*     */   }
/*     */   
/*     */   public void recreateStructures(int i, int j) {}
/*     */   
/*     */   public int getLoadedChunks() {
/* 251 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void recreateStructures(Chunk chunk, int i, int i1) {}
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 260 */     return "CustomChunkGenerator";
/*     */   }
/*     */   
/*     */   public void c() {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\generator\CustomChunkGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */