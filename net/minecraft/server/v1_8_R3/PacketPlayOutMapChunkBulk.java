/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PacketPlayOutMapChunkBulk implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int[] a;
/*    */   private int[] b;
/*    */   private PacketPlayOutMapChunk.ChunkMap[] c;
/*    */   private boolean d;
/*    */   private World world;
/*    */   
/*    */   public PacketPlayOutMapChunkBulk() {}
/*    */   
/*    */   public PacketPlayOutMapChunkBulk(List<Chunk> list)
/*    */   {
/* 17 */     int i = list.size();
/*    */     
/* 19 */     this.a = new int[i];
/* 20 */     this.b = new int[i];
/* 21 */     this.c = new PacketPlayOutMapChunk.ChunkMap[i];
/* 22 */     this.d = (!((Chunk)list.get(0)).getWorld().worldProvider.o());
/*    */     
/* 24 */     for (int j = 0; j < i; j++) {
/* 25 */       Chunk chunk = (Chunk)list.get(j);
/* 26 */       PacketPlayOutMapChunk.ChunkMap packetplayoutmapchunk_chunkmap = PacketPlayOutMapChunk.a(chunk, true, this.d, 65535);
/*    */       
/* 28 */       this.a[j] = chunk.locX;
/* 29 */       this.b[j] = chunk.locZ;
/* 30 */       this.c[j] = packetplayoutmapchunk_chunkmap;
/*    */     }
/*    */     
/* 33 */     this.world = ((Chunk)list.get(0)).getWorld();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws java.io.IOException {
/* 37 */     this.d = packetdataserializer.readBoolean();
/* 38 */     int i = packetdataserializer.e();
/*    */     
/* 40 */     this.a = new int[i];
/* 41 */     this.b = new int[i];
/* 42 */     this.c = new PacketPlayOutMapChunk.ChunkMap[i];
/*    */     
/*    */ 
/*    */ 
/* 46 */     for (int j = 0; j < i; j++) {
/* 47 */       this.a[j] = packetdataserializer.readInt();
/* 48 */       this.b[j] = packetdataserializer.readInt();
/* 49 */       this.c[j] = new PacketPlayOutMapChunk.ChunkMap();
/* 50 */       this.c[j].b = (packetdataserializer.readShort() & 0xFFFF);
/* 51 */       this.c[j].a = new byte[PacketPlayOutMapChunk.a(Integer.bitCount(this.c[j].b), this.d, true)];
/*    */     }
/*    */     
/* 54 */     for (j = 0; j < i; j++) {
/* 55 */       packetdataserializer.readBytes(this.c[j].a);
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws java.io.IOException
/*    */   {
/* 61 */     packetdataserializer.writeBoolean(this.d);
/* 62 */     packetdataserializer.b(this.c.length);
/*    */     
/*    */ 
/*    */ 
/* 66 */     for (int i = 0; i < this.a.length; i++) {
/* 67 */       packetdataserializer.writeInt(this.a[i]);
/* 68 */       packetdataserializer.writeInt(this.b[i]);
/* 69 */       packetdataserializer.writeShort((short)(this.c[i].b & 0xFFFF));
/*    */     }
/*    */     
/* 72 */     for (i = 0; i < this.a.length; i++) {
/* 73 */       this.world.spigotConfig.antiXrayInstance.obfuscate(this.a[i], this.b[i], this.c[i].b, this.c[i].a, this.world);
/* 74 */       packetdataserializer.writeBytes(this.c[i].a);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut packetlistenerplayout)
/*    */   {
/* 80 */     packetlistenerplayout.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutMapChunkBulk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */