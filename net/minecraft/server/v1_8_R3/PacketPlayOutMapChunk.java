/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PacketPlayOutMapChunk implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private ChunkMap c;
/*     */   private boolean d;
/*     */   
/*     */   public PacketPlayOutMapChunk() {}
/*     */   
/*     */   public PacketPlayOutMapChunk(Chunk chunk, boolean flag, int i)
/*     */   {
/*  18 */     this.a = chunk.locX;
/*  19 */     this.b = chunk.locZ;
/*  20 */     this.d = flag;
/*  21 */     this.c = a(chunk, flag, !chunk.getWorld().worldProvider.o(), i);
/*  22 */     chunk.world.spigotConfig.antiXrayInstance.obfuscateSync(chunk.locX, chunk.locZ, this.c.b, this.c.a, chunk.world);
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  26 */     this.a = packetdataserializer.readInt();
/*  27 */     this.b = packetdataserializer.readInt();
/*  28 */     this.d = packetdataserializer.readBoolean();
/*  29 */     this.c = new ChunkMap();
/*  30 */     this.c.b = packetdataserializer.readShort();
/*  31 */     this.c.a = packetdataserializer.a();
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  35 */     packetdataserializer.writeInt(this.a);
/*  36 */     packetdataserializer.writeInt(this.b);
/*  37 */     packetdataserializer.writeBoolean(this.d);
/*  38 */     packetdataserializer.writeShort((short)(this.c.b & 0xFFFF));
/*  39 */     packetdataserializer.a(this.c.a);
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut packetlistenerplayout) {
/*  43 */     packetlistenerplayout.a(this);
/*     */   }
/*     */   
/*     */   protected static int a(int i, boolean flag, boolean flag1) {
/*  47 */     int j = i * 2 * 16 * 16 * 16;
/*  48 */     int k = i * 16 * 16 * 16 / 2;
/*  49 */     int l = flag ? i * 16 * 16 * 16 / 2 : 0;
/*  50 */     int i1 = flag1 ? 256 : 0;
/*     */     
/*  52 */     return j + k + l + i1;
/*     */   }
/*     */   
/*     */   public static ChunkMap a(Chunk chunk, boolean flag, boolean flag1, int i) {
/*  56 */     ChunkSection[] achunksection = chunk.getSections();
/*  57 */     ChunkMap packetplayoutmapchunk_chunkmap = new ChunkMap();
/*  58 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/*     */ 
/*     */ 
/*  62 */     for (int j = 0; j < achunksection.length; j++) {
/*  63 */       ChunkSection chunksection = achunksection[j];
/*     */       
/*  65 */       if ((chunksection != null) && ((!flag) || (!chunksection.a())) && ((i & 1 << j) != 0)) {
/*  66 */         packetplayoutmapchunk_chunkmap.b |= 1 << j;
/*  67 */         arraylist.add(chunksection);
/*     */       }
/*     */     }
/*     */     
/*  71 */     packetplayoutmapchunk_chunkmap.a = new byte[a(Integer.bitCount(packetplayoutmapchunk_chunkmap.b), flag1, flag)];
/*  72 */     j = 0;
/*  73 */     Iterator iterator = arraylist.iterator();
/*     */     int k;
/*     */     int l;
/*  77 */     for (; 
/*  77 */         iterator.hasNext(); 
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  83 */         l < k)
/*     */     {
/*  78 */       ChunkSection chunksection1 = (ChunkSection)iterator.next();
/*  79 */       char[] achar = chunksection1.getIdArray();
/*  80 */       char[] achar1 = achar;
/*  81 */       k = achar.length;
/*     */       
/*  83 */       l = 0; continue;
/*  84 */       char c0 = achar1[l];
/*     */       
/*  86 */       packetplayoutmapchunk_chunkmap.a[(j++)] = ((byte)(c0 & 0xFF));
/*  87 */       packetplayoutmapchunk_chunkmap.a[(j++)] = ((byte)(c0 >> '\b' & 0xFF));l++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     ChunkSection chunksection1;
/*     */     
/*     */ 
/*  91 */     for (iterator = arraylist.iterator(); iterator.hasNext(); j = a(chunksection1.getEmittedLightArray().a(), packetplayoutmapchunk_chunkmap.a, j)) {
/*  92 */       chunksection1 = (ChunkSection)iterator.next();
/*     */     }
/*     */     
/*  95 */     if (flag1) { ChunkSection chunksection1;
/*  96 */       for (iterator = arraylist.iterator(); iterator.hasNext(); j = a(chunksection1.getSkyLightArray().a(), packetplayoutmapchunk_chunkmap.a, j)) {
/*  97 */         chunksection1 = (ChunkSection)iterator.next();
/*     */       }
/*     */     }
/*     */     
/* 101 */     if (flag) {
/* 102 */       a(chunk.getBiomeIndex(), packetplayoutmapchunk_chunkmap.a, j);
/*     */     }
/*     */     
/* 105 */     return packetplayoutmapchunk_chunkmap;
/*     */   }
/*     */   
/*     */   private static int a(byte[] abyte, byte[] abyte1, int i) {
/* 109 */     System.arraycopy(abyte, 0, abyte1, i, abyte.length);
/* 110 */     return i + abyte.length;
/*     */   }
/*     */   
/*     */   public static class ChunkMap
/*     */   {
/*     */     public byte[] a;
/*     */     public int b;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutMapChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */