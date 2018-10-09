/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutWindowData
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutWindowData() {}
/*    */   
/*    */   public PacketPlayOutWindowData(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 19 */     this.a = paramInt1;
/* 20 */     this.b = paramInt2;
/* 21 */     this.c = paramInt3;
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
/* 33 */     this.c = paramPacketDataSerializer.readShort();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 39 */     paramPacketDataSerializer.writeByte(this.a);
/* 40 */     paramPacketDataSerializer.writeShort(this.b);
/* 41 */     paramPacketDataSerializer.writeShort(this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutWindowData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */