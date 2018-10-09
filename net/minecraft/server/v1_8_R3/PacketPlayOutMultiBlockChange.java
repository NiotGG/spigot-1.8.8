/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutMultiBlockChange
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private ChunkCoordIntPair a;
/*    */   private MultiBlockChangeInfo[] b;
/*    */   
/*    */   public PacketPlayOutMultiBlockChange() {}
/*    */   
/*    */   public PacketPlayOutMultiBlockChange(int paramInt, short[] paramArrayOfShort, Chunk paramChunk)
/*    */   {
/* 22 */     this.a = new ChunkCoordIntPair(paramChunk.locX, paramChunk.locZ);
/*    */     
/* 24 */     this.b = new MultiBlockChangeInfo[paramInt];
/* 25 */     for (int i = 0; i < this.b.length; i++) {
/* 26 */       this.b[i] = new MultiBlockChangeInfo(paramArrayOfShort[i], paramChunk);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     this.a = new ChunkCoordIntPair(paramPacketDataSerializer.readInt(), paramPacketDataSerializer.readInt());
/* 33 */     this.b = new MultiBlockChangeInfo[paramPacketDataSerializer.e()];
/*    */     
/* 35 */     for (int i = 0; i < this.b.length; i++) {
/* 36 */       this.b[i] = new MultiBlockChangeInfo(paramPacketDataSerializer.readShort(), (IBlockData)Block.d.a(paramPacketDataSerializer.e()));
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 42 */     paramPacketDataSerializer.writeInt(this.a.x);
/* 43 */     paramPacketDataSerializer.writeInt(this.a.z);
/* 44 */     paramPacketDataSerializer.b(this.b.length);
/* 45 */     for (MultiBlockChangeInfo localMultiBlockChangeInfo : this.b) {
/* 46 */       paramPacketDataSerializer.writeShort(localMultiBlockChangeInfo.b());
/* 47 */       paramPacketDataSerializer.b(Block.d.b(localMultiBlockChangeInfo.c()));
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 53 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */ 
/*    */   public class MultiBlockChangeInfo
/*    */   {
/*    */     private final short b;
/*    */     
/*    */     private final IBlockData c;
/*    */     
/*    */     public MultiBlockChangeInfo(short paramShort, IBlockData paramIBlockData)
/*    */     {
/* 65 */       this.b = paramShort;
/* 66 */       this.c = paramIBlockData;
/*    */     }
/*    */     
/*    */     public MultiBlockChangeInfo(short paramShort, Chunk paramChunk) {
/* 70 */       this.b = paramShort;
/* 71 */       this.c = paramChunk.getBlockData(a());
/*    */     }
/*    */     
/*    */     public BlockPosition a() {
/* 75 */       return new BlockPosition(PacketPlayOutMultiBlockChange.a(PacketPlayOutMultiBlockChange.this).a(this.b >> 12 & 0xF, this.b & 0xFF, this.b >> 8 & 0xF));
/*    */     }
/*    */     
/*    */     public short b() {
/* 79 */       return this.b;
/*    */     }
/*    */     
/*    */     public IBlockData c() {
/* 83 */       return this.c;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutMultiBlockChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */