/*    */ package org.bukkit.craftbukkit.v1_8_R3.generator;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.Chunk;
/*    */ import net.minecraft.server.v1_8_R3.IChunkProvider;
/*    */ import net.minecraft.server.v1_8_R3.IProgressUpdate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ 
/*    */ public class NormalChunkGenerator extends InternalChunkGenerator
/*    */ {
/*    */   private final IChunkProvider provider;
/*    */   
/*    */   public NormalChunkGenerator(net.minecraft.server.v1_8_R3.World world, long seed)
/*    */   {
/* 16 */     this.provider = world.worldProvider.getChunkProvider();
/*    */   }
/*    */   
/*    */   public byte[] generate(org.bukkit.World world, Random random, int x, int z)
/*    */   {
/* 21 */     throw new UnsupportedOperationException("Not supported.");
/*    */   }
/*    */   
/*    */   public boolean canSpawn(org.bukkit.World world, int x, int z)
/*    */   {
/* 26 */     return ((CraftWorld)world).getHandle().worldProvider.canSpawn(x, z);
/*    */   }
/*    */   
/*    */   public java.util.List<org.bukkit.generator.BlockPopulator> getDefaultPopulators(org.bukkit.World world)
/*    */   {
/* 31 */     return new java.util.ArrayList();
/*    */   }
/*    */   
/*    */   public boolean isChunkLoaded(int i, int i1)
/*    */   {
/* 36 */     return this.provider.isChunkLoaded(i, i1);
/*    */   }
/*    */   
/*    */   public Chunk getOrCreateChunk(int i, int i1)
/*    */   {
/* 41 */     return this.provider.getOrCreateChunk(i, i1);
/*    */   }
/*    */   
/*    */   public Chunk getChunkAt(BlockPosition blockPosition)
/*    */   {
/* 46 */     return this.provider.getChunkAt(blockPosition);
/*    */   }
/*    */   
/*    */   public void getChunkAt(IChunkProvider icp, int i, int i1)
/*    */   {
/* 51 */     this.provider.getChunkAt(icp, i, i1);
/*    */   }
/*    */   
/*    */   public boolean a(IChunkProvider iChunkProvider, Chunk chunk, int i, int i1)
/*    */   {
/* 56 */     return this.provider.a(this.provider, chunk, i, i1);
/*    */   }
/*    */   
/*    */   public boolean saveChunks(boolean bln, IProgressUpdate ipu)
/*    */   {
/* 61 */     return this.provider.saveChunks(bln, ipu);
/*    */   }
/*    */   
/*    */   public boolean unloadChunks()
/*    */   {
/* 66 */     return this.provider.unloadChunks();
/*    */   }
/*    */   
/*    */   public boolean canSave()
/*    */   {
/* 71 */     return this.provider.canSave();
/*    */   }
/*    */   
/*    */   public java.util.List<net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta> getMobsFor(net.minecraft.server.v1_8_R3.EnumCreatureType ect, BlockPosition position)
/*    */   {
/* 76 */     return this.provider.getMobsFor(ect, position);
/*    */   }
/*    */   
/*    */   public BlockPosition findNearestMapFeature(net.minecraft.server.v1_8_R3.World world, String string, BlockPosition position)
/*    */   {
/* 81 */     return this.provider.findNearestMapFeature(world, string, position);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getLoadedChunks()
/*    */   {
/* 87 */     return 0;
/*    */   }
/*    */   
/*    */   public void recreateStructures(Chunk chunk, int i, int i1)
/*    */   {
/* 92 */     this.provider.recreateStructures(chunk, i, i1);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 97 */     return "NormalWorldGenerator";
/*    */   }
/*    */   
/*    */   public void c() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\generator\NormalChunkGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */