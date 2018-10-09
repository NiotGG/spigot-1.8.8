/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutAttachEntity
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutAttachEntity() {}
/*    */   
/*    */   public PacketPlayOutAttachEntity(int paramInt, Entity paramEntity1, Entity paramEntity2)
/*    */   {
/* 21 */     this.a = paramInt;
/* 22 */     this.b = paramEntity1.getId();
/* 23 */     this.c = (paramEntity2 != null ? paramEntity2.getId() : -1);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     this.b = paramPacketDataSerializer.readInt();
/* 29 */     this.c = paramPacketDataSerializer.readInt();
/* 30 */     this.a = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     paramPacketDataSerializer.writeInt(this.b);
/* 36 */     paramPacketDataSerializer.writeInt(this.c);
/* 37 */     paramPacketDataSerializer.writeByte(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 42 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutAttachEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */