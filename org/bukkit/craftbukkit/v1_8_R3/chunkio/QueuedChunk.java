/*    */ package org.bukkit.craftbukkit.v1_8_R3.chunkio;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.ChunkProviderServer;
/*    */ import net.minecraft.server.v1_8_R3.ChunkRegionLoader;
/*    */ 
/*    */ class QueuedChunk
/*    */ {
/*    */   final int x;
/*    */   final int z;
/*    */   final ChunkRegionLoader loader;
/*    */   final net.minecraft.server.v1_8_R3.World world;
/*    */   final ChunkProviderServer provider;
/*    */   net.minecraft.server.v1_8_R3.NBTTagCompound compound;
/*    */   
/*    */   public QueuedChunk(int x, int z, ChunkRegionLoader loader, net.minecraft.server.v1_8_R3.World world, ChunkProviderServer provider)
/*    */   {
/* 17 */     this.x = x;
/* 18 */     this.z = z;
/* 19 */     this.loader = loader;
/* 20 */     this.world = world;
/* 21 */     this.provider = provider;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 26 */     return this.x * 31 + this.z * 29 ^ this.world.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object object)
/*    */   {
/* 31 */     if ((object instanceof QueuedChunk)) {
/* 32 */       QueuedChunk other = (QueuedChunk)object;
/* 33 */       return (this.x == other.x) && (this.z == other.z) && (this.world == other.world);
/*    */     }
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\chunkio\QueuedChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */