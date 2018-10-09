/*    */ package org.bukkit.craftbukkit.v1_8_R3.chunkio;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import net.minecraft.server.v1_8_R3.Chunk;
/*    */ import net.minecraft.server.v1_8_R3.ChunkProviderServer;
/*    */ import net.minecraft.server.v1_8_R3.ChunkRegionLoader;
/*    */ import net.minecraft.server.v1_8_R3.IChunkProvider;
/*    */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.LongObjectHashMap;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.spigotmc.CustomTimingsHandler;
/*    */ 
/*    */ class ChunkIOProvider implements org.bukkit.craftbukkit.v1_8_R3.util.AsynchronousExecutor.CallBackProvider<QueuedChunk, Chunk, Runnable, RuntimeException>
/*    */ {
/* 19 */   private final AtomicInteger threadNumber = new AtomicInteger(1);
/*    */   
/*    */   public Chunk callStage1(QueuedChunk queuedChunk) throws RuntimeException
/*    */   {
/*    */     try {
/* 24 */       ChunkRegionLoader loader = queuedChunk.loader;
/* 25 */       Object[] data = loader.loadChunk(queuedChunk.world, queuedChunk.x, queuedChunk.z);
/*    */       
/* 27 */       if (data != null) {
/* 28 */         queuedChunk.compound = ((NBTTagCompound)data[1]);
/* 29 */         return (Chunk)data[0];
/*    */       }
/*    */       
/* 32 */       return null;
/*    */     } catch (IOException ex) {
/* 34 */       throw new RuntimeException(ex);
/*    */     }
/*    */   }
/*    */   
/*    */   public void callStage2(QueuedChunk queuedChunk, Chunk chunk) throws RuntimeException
/*    */   {
/* 40 */     if (chunk == null)
/*    */     {
/* 42 */       queuedChunk.provider.originalGetChunkAt(queuedChunk.x, queuedChunk.z);
/* 43 */       return;
/*    */     }
/*    */     
/* 46 */     queuedChunk.loader.loadEntities(chunk, queuedChunk.compound.getCompound("Level"), queuedChunk.world);
/* 47 */     chunk.setLastSaved(queuedChunk.provider.world.getTime());
/* 48 */     queuedChunk.provider.chunks.put(org.bukkit.craftbukkit.v1_8_R3.util.LongHash.toLong(queuedChunk.x, queuedChunk.z), chunk);
/* 49 */     chunk.addEntities();
/*    */     
/* 51 */     if (queuedChunk.provider.chunkProvider != null) {
/* 52 */       queuedChunk.provider.world.timings.syncChunkLoadStructuresTimer.startTiming();
/* 53 */       queuedChunk.provider.chunkProvider.recreateStructures(chunk, queuedChunk.x, queuedChunk.z);
/* 54 */       queuedChunk.provider.world.timings.syncChunkLoadStructuresTimer.stopTiming();
/*    */     }
/*    */     
/* 57 */     Server server = queuedChunk.provider.world.getServer();
/* 58 */     if (server != null) {
/* 59 */       server.getPluginManager().callEvent(new org.bukkit.event.world.ChunkLoadEvent(chunk.bukkitChunk, false));
/*    */     }
/*    */     
/*    */ 
/* 63 */     for (int x = -2; x < 3; x++) {
/* 64 */       for (int z = -2; z < 3; z++) {
/* 65 */         if ((x != 0) || (z != 0))
/*    */         {
/*    */ 
/*    */ 
/* 69 */           Chunk neighbor = queuedChunk.provider.getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
/* 70 */           if (neighbor != null) {
/* 71 */             neighbor.setNeighborLoaded(-x, -z);
/* 72 */             chunk.setNeighborLoaded(x, z);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 77 */     chunk.loadNearby(queuedChunk.provider, queuedChunk.provider, queuedChunk.x, queuedChunk.z);
/*    */   }
/*    */   
/*    */   public void callStage3(QueuedChunk queuedChunk, Chunk chunk, Runnable runnable) throws RuntimeException {
/* 81 */     runnable.run();
/*    */   }
/*    */   
/*    */   public Thread newThread(Runnable runnable) {
/* 85 */     Thread thread = new Thread(runnable, "Chunk I/O Executor Thread-" + this.threadNumber.getAndIncrement());
/* 86 */     thread.setDaemon(true);
/* 87 */     return thread;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\chunkio\ChunkIOProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */