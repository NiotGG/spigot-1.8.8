/*     */ package org.bukkit.craftbukkit.v1_8_R3;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.BiomeBase;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.ChunkSection;
/*     */ import net.minecraft.server.v1_8_R3.IBlockData;
/*     */ import net.minecraft.server.v1_8_R3.NibbleArray;
/*     */ import net.minecraft.server.v1_8_R3.WorldChunkManager;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.ChunkSnapshot;
/*     */ import org.bukkit.block.BlockState;
/*     */ 
/*     */ public class CraftChunk implements org.bukkit.Chunk
/*     */ {
/*     */   private WeakReference<net.minecraft.server.v1_8_R3.Chunk> weakChunk;
/*     */   private final WorldServer worldServer;
/*     */   private final int x;
/*     */   private final int z;
/*  21 */   private static final byte[] emptyData = new byte['ࠀ'];
/*  22 */   private static final short[] emptyBlockIDs = new short['က'];
/*  23 */   private static final byte[] emptySkyLight = new byte['ࠀ'];
/*     */   
/*     */   public CraftChunk(net.minecraft.server.v1_8_R3.Chunk chunk) {
/*  26 */     if (!(chunk instanceof net.minecraft.server.v1_8_R3.EmptyChunk)) {
/*  27 */       this.weakChunk = new WeakReference(chunk);
/*     */     }
/*     */     
/*  30 */     this.worldServer = ((WorldServer)getHandle().world);
/*  31 */     this.x = getHandle().locX;
/*  32 */     this.z = getHandle().locZ;
/*     */   }
/*     */   
/*     */   public org.bukkit.World getWorld() {
/*  36 */     return this.worldServer.getWorld();
/*     */   }
/*     */   
/*     */   public CraftWorld getCraftWorld() {
/*  40 */     return (CraftWorld)getWorld();
/*     */   }
/*     */   
/*     */   public net.minecraft.server.v1_8_R3.Chunk getHandle() {
/*  44 */     net.minecraft.server.v1_8_R3.Chunk c = (net.minecraft.server.v1_8_R3.Chunk)this.weakChunk.get();
/*     */     
/*  46 */     if (c == null) {
/*  47 */       c = this.worldServer.getChunkAt(this.x, this.z);
/*     */       
/*  49 */       if (!(c instanceof net.minecraft.server.v1_8_R3.EmptyChunk)) {
/*  50 */         this.weakChunk = new WeakReference(c);
/*     */       }
/*     */     }
/*     */     
/*  54 */     return c;
/*     */   }
/*     */   
/*     */   void breakLink() {
/*  58 */     this.weakChunk.clear();
/*     */   }
/*     */   
/*     */   public int getX() {
/*  62 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  66 */     return this.z;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  71 */     return "CraftChunk{x=" + getX() + "z=" + getZ() + '}';
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getBlock(int x, int y, int z) {
/*  75 */     return new org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock(this, getX() << 4 | x & 0xF, y, getZ() << 4 | z & 0xF);
/*     */   }
/*     */   
/*     */   public org.bukkit.entity.Entity[] getEntities() {
/*  79 */     int count = 0;int index = 0;
/*  80 */     net.minecraft.server.v1_8_R3.Chunk chunk = getHandle();
/*     */     
/*  82 */     for (int i = 0; i < 16; i++) {
/*  83 */       count += chunk.entitySlices[i].size();
/*     */     }
/*     */     
/*  86 */     org.bukkit.entity.Entity[] entities = new org.bukkit.entity.Entity[count];
/*     */     
/*  88 */     for (int i = 0; i < 16; i++) {
/*     */       Object[] arrayOfObject;
/*  90 */       int i = (arrayOfObject = chunk.entitySlices[i].toArray()).length; for (int j = 0; j < i; j++) { Object obj = arrayOfObject[j];
/*  91 */         if ((obj instanceof net.minecraft.server.v1_8_R3.Entity))
/*     */         {
/*     */ 
/*     */ 
/*  95 */           entities[(index++)] = ((net.minecraft.server.v1_8_R3.Entity)obj).getBukkitEntity();
/*     */         }
/*     */       }
/*     */     }
/*  99 */     return entities;
/*     */   }
/*     */   
/*     */   public BlockState[] getTileEntities() {
/* 103 */     int index = 0;
/* 104 */     net.minecraft.server.v1_8_R3.Chunk chunk = getHandle();
/*     */     
/* 106 */     BlockState[] entities = new BlockState[chunk.tileEntities.size()];
/*     */     Object[] arrayOfObject;
/* 108 */     int i = (arrayOfObject = chunk.tileEntities.keySet().toArray()).length; for (int j = 0; j < i; j++) { Object obj = arrayOfObject[j];
/* 109 */       if ((obj instanceof BlockPosition))
/*     */       {
/*     */ 
/*     */ 
/* 113 */         BlockPosition position = (BlockPosition)obj;
/* 114 */         entities[(index++)] = this.worldServer.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()).getState();
/*     */       }
/*     */     }
/* 117 */     return entities;
/*     */   }
/*     */   
/*     */   public boolean isLoaded() {
/* 121 */     return getWorld().isChunkLoaded(this);
/*     */   }
/*     */   
/*     */   public boolean load() {
/* 125 */     return getWorld().loadChunk(getX(), getZ(), true);
/*     */   }
/*     */   
/*     */   public boolean load(boolean generate) {
/* 129 */     return getWorld().loadChunk(getX(), getZ(), generate);
/*     */   }
/*     */   
/*     */   public boolean unload() {
/* 133 */     return getWorld().unloadChunk(getX(), getZ());
/*     */   }
/*     */   
/*     */   public boolean unload(boolean save) {
/* 137 */     return getWorld().unloadChunk(getX(), getZ(), save);
/*     */   }
/*     */   
/*     */   public boolean unload(boolean save, boolean safe) {
/* 141 */     return getWorld().unloadChunk(getX(), getZ(), save, safe);
/*     */   }
/*     */   
/*     */   public ChunkSnapshot getChunkSnapshot() {
/* 145 */     return getChunkSnapshot(true, false, false);
/*     */   }
/*     */   
/*     */   public ChunkSnapshot getChunkSnapshot(boolean includeMaxBlockY, boolean includeBiome, boolean includeBiomeTempRain) {
/* 149 */     net.minecraft.server.v1_8_R3.Chunk chunk = getHandle();
/*     */     
/* 151 */     ChunkSection[] cs = chunk.getSections();
/* 152 */     short[][] sectionBlockIDs = new short[cs.length][];
/* 153 */     byte[][] sectionBlockData = new byte[cs.length][];
/* 154 */     byte[][] sectionSkyLights = new byte[cs.length][];
/* 155 */     byte[][] sectionEmitLights = new byte[cs.length][];
/* 156 */     boolean[] sectionEmpty = new boolean[cs.length];
/*     */     
/* 158 */     for (int i = 0; i < cs.length; i++) {
/* 159 */       if (cs[i] == null) {
/* 160 */         sectionBlockIDs[i] = emptyBlockIDs;
/* 161 */         sectionBlockData[i] = emptyData;
/* 162 */         sectionSkyLights[i] = emptySkyLight;
/* 163 */         sectionEmitLights[i] = emptyData;
/* 164 */         sectionEmpty[i] = true;
/*     */       } else {
/* 166 */         short[] blockids = new short['က'];
/* 167 */         char[] baseids = cs[i].getIdArray();
/* 168 */         byte[] dataValues = sectionBlockData[i] = new byte['ࠀ'];
/*     */         
/*     */ 
/* 171 */         for (int j = 0; j < 4096; j++) {
/* 172 */           if (baseids[j] != 0) {
/* 173 */             IBlockData blockData = (IBlockData)net.minecraft.server.v1_8_R3.Block.d.a(baseids[j]);
/* 174 */             if (blockData != null) {
/* 175 */               blockids[j] = ((short)net.minecraft.server.v1_8_R3.Block.getId(blockData.getBlock()));
/* 176 */               int data = blockData.getBlock().toLegacyData(blockData);
/* 177 */               int jj = j >> 1;
/* 178 */               if ((j & 0x1) == 0) {
/* 179 */                 dataValues[jj] = ((byte)(dataValues[jj] & 0xF0 | data & 0xF));
/*     */               } else
/* 181 */                 dataValues[jj] = ((byte)(dataValues[jj] & 0xF | (data & 0xF) << 4));
/*     */             }
/*     */           }
/*     */         }
/* 185 */         sectionBlockIDs[i] = blockids;
/*     */         
/* 187 */         if (cs[i].getSkyLightArray() == null) {
/* 188 */           sectionSkyLights[i] = emptyData;
/*     */         } else {
/* 190 */           sectionSkyLights[i] = new byte['ࠀ'];
/* 191 */           System.arraycopy(cs[i].getSkyLightArray().a(), 0, sectionSkyLights[i], 0, 2048);
/*     */         }
/* 193 */         sectionEmitLights[i] = new byte['ࠀ'];
/* 194 */         System.arraycopy(cs[i].getEmittedLightArray().a(), 0, sectionEmitLights[i], 0, 2048);
/*     */       }
/*     */     }
/*     */     
/* 198 */     int[] hmap = null;
/*     */     
/* 200 */     if (includeMaxBlockY) {
/* 201 */       hmap = new int['Ā'];
/* 202 */       System.arraycopy(chunk.heightMap, 0, hmap, 0, 256);
/*     */     }
/*     */     
/* 205 */     BiomeBase[] biome = null;
/* 206 */     double[] biomeTemp = null;
/* 207 */     double[] biomeRain = null;
/*     */     
/* 209 */     if ((includeBiome) || (includeBiomeTempRain)) {
/* 210 */       WorldChunkManager wcm = chunk.world.getWorldChunkManager();
/*     */       
/* 212 */       if (includeBiome) {
/* 213 */         biome = new BiomeBase['Ā'];
/* 214 */         for (int i = 0; i < 256; i++) {
/* 215 */           biome[i] = chunk.getBiome(new BlockPosition(i & 0xF, 0, i >> 4), wcm);
/*     */         }
/*     */       }
/*     */       
/* 219 */       if (includeBiomeTempRain) {
/* 220 */         biomeTemp = new double['Ā'];
/* 221 */         biomeRain = new double['Ā'];
/* 222 */         float[] dat = getTemperatures(wcm, getX() << 4, getZ() << 4);
/*     */         
/* 224 */         for (int i = 0; i < 256; i++) {
/* 225 */           biomeTemp[i] = dat[i];
/*     */         }
/*     */         
/* 228 */         dat = wcm.getWetness(null, getX() << 4, getZ() << 4, 16, 16);
/*     */         
/* 230 */         for (int i = 0; i < 256; i++) {
/* 231 */           biomeRain[i] = dat[i];
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 236 */     org.bukkit.World world = getWorld();
/* 237 */     return new CraftChunkSnapshot(getX(), getZ(), world.getName(), world.getFullTime(), sectionBlockIDs, sectionBlockData, sectionSkyLights, sectionEmitLights, sectionEmpty, hmap, biome, biomeTemp, biomeRain);
/*     */   }
/*     */   
/*     */   public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
/* 241 */     BiomeBase[] biome = null;
/* 242 */     double[] biomeTemp = null;
/* 243 */     double[] biomeRain = null;
/*     */     
/* 245 */     if ((includeBiome) || (includeBiomeTempRain)) {
/* 246 */       WorldChunkManager wcm = world.getHandle().getWorldChunkManager();
/*     */       
/* 248 */       if (includeBiome) {
/* 249 */         biome = new BiomeBase['Ā'];
/* 250 */         for (int i = 0; i < 256; i++) {
/* 251 */           biome[i] = world.getHandle().getBiome(new BlockPosition((x << 4) + (i & 0xF), 0, (z << 4) + (i >> 4)));
/*     */         }
/*     */       }
/*     */       
/* 255 */       if (includeBiomeTempRain) {
/* 256 */         biomeTemp = new double['Ā'];
/* 257 */         biomeRain = new double['Ā'];
/* 258 */         float[] dat = getTemperatures(wcm, x << 4, z << 4);
/*     */         
/* 260 */         for (int i = 0; i < 256; i++) {
/* 261 */           biomeTemp[i] = dat[i];
/*     */         }
/*     */         
/* 264 */         dat = wcm.getWetness(null, x << 4, z << 4, 16, 16);
/*     */         
/* 266 */         for (int i = 0; i < 256; i++) {
/* 267 */           biomeRain[i] = dat[i];
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 273 */     int hSection = world.getMaxHeight() >> 4;
/* 274 */     short[][] blockIDs = new short[hSection][];
/* 275 */     byte[][] skyLight = new byte[hSection][];
/* 276 */     byte[][] emitLight = new byte[hSection][];
/* 277 */     byte[][] blockData = new byte[hSection][];
/* 278 */     boolean[] empty = new boolean[hSection];
/*     */     
/* 280 */     for (int i = 0; i < hSection; i++) {
/* 281 */       blockIDs[i] = emptyBlockIDs;
/* 282 */       skyLight[i] = emptySkyLight;
/* 283 */       emitLight[i] = emptyData;
/* 284 */       blockData[i] = emptyData;
/* 285 */       empty[i] = true;
/*     */     }
/*     */     
/* 288 */     return new CraftChunkSnapshot(x, z, world.getName(), world.getFullTime(), blockIDs, blockData, skyLight, emitLight, empty, new int['Ā'], biome, biomeTemp, biomeRain);
/*     */   }
/*     */   
/*     */   private static float[] getTemperatures(WorldChunkManager chunkmanager, int chunkX, int chunkZ) {
/* 292 */     BiomeBase[] biomes = chunkmanager.getBiomes(null, chunkX, chunkZ, 16, 16);
/* 293 */     float[] temps = new float[biomes.length];
/*     */     
/* 295 */     for (int i = 0; i < biomes.length; i++) {
/* 296 */       float temp = biomes[i].temperature;
/*     */       
/* 298 */       if (temp > 1.0F) {
/* 299 */         temp = 1.0F;
/*     */       }
/*     */       
/* 302 */       temps[i] = temp;
/*     */     }
/*     */     
/* 305 */     return temps;
/*     */   }
/*     */   
/*     */   static {
/* 309 */     java.util.Arrays.fill(emptySkyLight, (byte)-1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */