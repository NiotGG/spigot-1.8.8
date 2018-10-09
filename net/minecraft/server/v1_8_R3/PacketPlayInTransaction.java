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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInTransaction
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int a;
/*    */   private short b;
/*    */   private boolean c;
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 24 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.readByte();
/* 30 */     this.b = paramPacketDataSerializer.readShort();
/* 31 */     this.c = (paramPacketDataSerializer.readByte() != 0);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     paramPacketDataSerializer.writeByte(this.a);
/* 37 */     paramPacketDataSerializer.writeShort(this.b);
/* 38 */     paramPacketDataSerializer.writeByte(this.c ? 1 : 0);
/*    */   }
/*    */   
/*    */   public int a() {
/* 42 */     return this.a;
/*    */   }
/*    */   
/*    */   public short b() {
/* 46 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */