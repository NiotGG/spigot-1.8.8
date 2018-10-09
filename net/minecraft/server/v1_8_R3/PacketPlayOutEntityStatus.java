/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityStatus
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private byte b;
/*    */   
/*    */   public PacketPlayOutEntityStatus() {}
/*    */   
/*    */   public PacketPlayOutEntityStatus(Entity paramEntity, byte paramByte)
/*    */   {
/* 20 */     this.a = paramEntity.getId();
/* 21 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     this.a = paramPacketDataSerializer.readInt();
/* 27 */     this.b = paramPacketDataSerializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     paramPacketDataSerializer.writeInt(this.a);
/* 33 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 38 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */