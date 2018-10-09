/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderDebug
/*     */   implements IChunkProvider
/*     */ {
/*  23 */   private static final List<IBlockData> a = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  30 */     for (Block localBlock : Block.REGISTRY)
/*  31 */       a.addAll(localBlock.P().a());
/*     */   }
/*     */   
/*  34 */   private static final int b = MathHelper.f(MathHelper.c(a.size()));
/*  35 */   private static final int c = MathHelper.f(a.size() / b);
/*     */   private final World d;
/*     */   
/*     */   public ChunkProviderDebug(World paramWorld) {
/*  39 */     this.d = paramWorld;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Chunk getOrCreateChunk(int paramInt1, int paramInt2)
/*     */   {
/*  49 */     ChunkSnapshot localChunkSnapshot = new ChunkSnapshot();
/*     */     
/*  51 */     for (int i = 0; i < 16; i++) {
/*  52 */       for (int j = 0; j < 16; j++) {
/*  53 */         int k = paramInt1 * 16 + i;
/*  54 */         m = paramInt2 * 16 + j;
/*     */         
/*  56 */         localChunkSnapshot.a(i, 60, j, Blocks.BARRIER.getBlockData());
/*     */         
/*  58 */         IBlockData localIBlockData = b(k, m);
/*  59 */         if (localIBlockData != null) {
/*  60 */           localChunkSnapshot.a(i, 70, j, localIBlockData);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  65 */     Chunk localChunk = new Chunk(this.d, localChunkSnapshot, paramInt1, paramInt2);
/*  66 */     localChunk.initLighting();
/*     */     
/*  68 */     BiomeBase[] arrayOfBiomeBase = this.d.getWorldChunkManager().getBiomeBlock(null, paramInt1 * 16, paramInt2 * 16, 16, 16);
/*  69 */     byte[] arrayOfByte = localChunk.getBiomeIndex();
/*     */     
/*  71 */     for (int m = 0; m < arrayOfByte.length; m++) {
/*  72 */       arrayOfByte[m] = ((byte)arrayOfBiomeBase[m].id);
/*     */     }
/*     */     
/*  75 */     localChunk.initLighting();
/*     */     
/*  77 */     return localChunk;
/*     */   }
/*     */   
/*     */   public static IBlockData b(int paramInt1, int paramInt2)
/*     */   {
/*  82 */     IBlockData localIBlockData = null;
/*     */     
/*  84 */     if ((paramInt1 > 0) && (paramInt2 > 0) && (paramInt1 % 2 != 0) && (paramInt2 % 2 != 0)) {
/*  85 */       paramInt1 /= 2;
/*  86 */       paramInt2 /= 2;
/*     */       
/*  88 */       if ((paramInt1 <= b) && (paramInt2 <= c)) {
/*  89 */         int i = MathHelper.a(paramInt1 * b + paramInt2);
/*  90 */         if (i < a.size()) {
/*  91 */           localIBlockData = (IBlockData)a.get(i);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  96 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public boolean isChunkLoaded(int paramInt1, int paramInt2)
/*     */   {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(IChunkProvider paramIChunkProvider, Chunk paramChunk, int paramInt1, int paramInt2)
/*     */   {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramIProgressUpdate)
/*     */   {
/* 115 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean unloadChunks()
/*     */   {
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canSave()
/*     */   {
/* 129 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 134 */     return "DebugLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType paramEnumCreatureType, BlockPosition paramBlockPosition)
/*     */   {
/* 139 */     BiomeBase localBiomeBase = this.d.getBiome(paramBlockPosition);
/* 140 */     return localBiomeBase.getMobs(paramEnumCreatureType);
/*     */   }
/*     */   
/*     */   public BlockPosition findNearestMapFeature(World paramWorld, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunks()
/*     */   {
/* 150 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Chunk getChunkAt(BlockPosition paramBlockPosition)
/*     */   {
/* 159 */     return getOrCreateChunk(paramBlockPosition.getX() >> 4, paramBlockPosition.getZ() >> 4);
/*     */   }
/*     */   
/*     */   public void getChunkAt(IChunkProvider paramIChunkProvider, int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void c() {}
/*     */   
/*     */   public void recreateStructures(Chunk paramChunk, int paramInt1, int paramInt2) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkProviderDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */