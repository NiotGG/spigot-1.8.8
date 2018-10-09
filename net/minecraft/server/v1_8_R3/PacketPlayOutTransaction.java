/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutTransaction
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private short b;
/*    */   private boolean c;
/*    */   
/*    */   public PacketPlayOutTransaction() {}
/*    */   
/*    */   public PacketPlayOutTransaction(int paramInt, short paramShort, boolean paramBoolean)
/*    */   {
/* 19 */     this.a = paramInt;
/* 20 */     this.b = paramShort;
/* 21 */     this.c = paramBoolean;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 26 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 31 */     this.a = paramPacketDataSerializer.readUnsignedByte();
/* 32 */     this.b = paramPacketDataSerializer.readShort();
/* 33 */     this.c = paramPacketDataSerializer.readBoolean();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 38 */     paramPacketDataSerializer.writeByte(this.a);
/* 39 */     paramPacketDataSerializer.writeShort(this.b);
/* 40 */     paramPacketDataSerializer.writeBoolean(this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */