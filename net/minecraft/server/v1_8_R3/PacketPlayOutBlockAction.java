/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutBlockAction
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private BlockPosition a;
/*    */   private int b;
/*    */   private int c;
/*    */   private Block d;
/*    */   
/*    */   public PacketPlayOutBlockAction() {}
/*    */   
/*    */   public PacketPlayOutBlockAction(BlockPosition paramBlockPosition, Block paramBlock, int paramInt1, int paramInt2)
/*    */   {
/* 21 */     this.a = paramBlockPosition;
/* 22 */     this.b = paramInt1;
/* 23 */     this.c = paramInt2;
/* 24 */     this.d = paramBlock;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.c();
/* 30 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/* 31 */     this.c = paramPacketDataSerializer.readUnsignedByte();
/* 32 */     this.d = Block.getById(paramPacketDataSerializer.e() & 0xFFF);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 37 */     paramPacketDataSerializer.a(this.a);
/* 38 */     paramPacketDataSerializer.writeByte(this.b);
/* 39 */     paramPacketDataSerializer.writeByte(this.c);
/* 40 */     paramPacketDataSerializer.b(Block.getId(this.d) & 0xFFF);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 45 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutBlockAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */