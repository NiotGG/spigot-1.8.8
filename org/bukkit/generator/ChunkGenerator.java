/*     */ package org.bukkit.generator;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.material.MaterialData;
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
/*     */ public abstract class ChunkGenerator
/*     */ {
/*     */   @Deprecated
/*     */   public byte[] generate(World world, Random random, int x, int z)
/*     */   {
/*  78 */     throw new UnsupportedOperationException("Custom generator is missing required methods: generate(), generateBlockSections() and generateExtBlockSections()");
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
/*     */   @Deprecated
/*     */   public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
/*     */   {
/* 163 */     return null;
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
/*     */   @Deprecated
/*     */   public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
/*     */   {
/* 221 */     return null;
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
/*     */   public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
/*     */   {
/* 249 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ChunkData createChunkData(World world)
/*     */   {
/* 258 */     return Bukkit.getServer().createChunkData(world);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canSpawn(World world, int x, int z)
/*     */   {
/* 270 */     Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);
/*     */     
/* 272 */     switch (world.getEnvironment()) {
/*     */     case NORMAL: 
/* 274 */       return true;
/*     */     case THE_END: 
/* 276 */       return (highest.getType() != Material.AIR) && (highest.getType() != Material.WATER) && (highest.getType() != Material.LAVA);
/*     */     }
/*     */     
/* 279 */     return (highest.getType() == Material.SAND) || (highest.getType() == Material.GRAVEL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<BlockPopulator> getDefaultPopulators(World world)
/*     */   {
/* 291 */     return new ArrayList();
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
/*     */   public Location getFixedSpawnLocation(World world, Random random)
/*     */   {
/* 305 */     return null;
/*     */   }
/*     */   
/*     */   public static abstract interface BiomeGrid
/*     */   {
/*     */     public abstract Biome getBiome(int paramInt1, int paramInt2);
/*     */     
/*     */     public abstract void setBiome(int paramInt1, int paramInt2, Biome paramBiome);
/*     */   }
/*     */   
/*     */   public static abstract interface ChunkData
/*     */   {
/*     */     public abstract int getMaxHeight();
/*     */     
/*     */     public abstract void setBlock(int paramInt1, int paramInt2, int paramInt3, Material paramMaterial);
/*     */     
/*     */     public abstract void setBlock(int paramInt1, int paramInt2, int paramInt3, MaterialData paramMaterialData);
/*     */     
/*     */     public abstract void setRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Material paramMaterial);
/*     */     
/*     */     public abstract void setRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, MaterialData paramMaterialData);
/*     */     
/*     */     public abstract Material getType(int paramInt1, int paramInt2, int paramInt3);
/*     */     
/*     */     public abstract MaterialData getTypeAndData(int paramInt1, int paramInt2, int paramInt3);
/*     */     
/*     */     @Deprecated
/*     */     public abstract void setRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */     
/*     */     @Deprecated
/*     */     public abstract void setRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */     
/*     */     @Deprecated
/*     */     public abstract void setBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */     
/*     */     @Deprecated
/*     */     public abstract void setBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte paramByte);
/*     */     
/*     */     @Deprecated
/*     */     public abstract int getTypeId(int paramInt1, int paramInt2, int paramInt3);
/*     */     
/*     */     @Deprecated
/*     */     public abstract byte getData(int paramInt1, int paramInt2, int paramInt3);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\generator\ChunkGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */