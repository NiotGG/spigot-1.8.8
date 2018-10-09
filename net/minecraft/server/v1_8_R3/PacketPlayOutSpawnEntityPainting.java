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
/*    */ public class PacketPlayOutSpawnEntityPainting
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private BlockPosition b;
/*    */   private EnumDirection c;
/*    */   private String d;
/*    */   
/*    */   public PacketPlayOutSpawnEntityPainting() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityPainting(EntityPainting paramEntityPainting)
/*    */   {
/* 23 */     this.a = paramEntityPainting.getId();
/* 24 */     this.b = paramEntityPainting.getBlockPosition();
/* 25 */     this.c = paramEntityPainting.direction;
/* 26 */     this.d = paramEntityPainting.art.B;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 31 */     this.a = paramPacketDataSerializer.e();
/* 32 */     this.d = paramPacketDataSerializer.c(EntityPainting.EnumArt.A);
/* 33 */     this.b = paramPacketDataSerializer.c();
/* 34 */     this.c = EnumDirection.fromType2(paramPacketDataSerializer.readUnsignedByte());
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 39 */     paramPacketDataSerializer.b(this.a);
/* 40 */     paramPacketDataSerializer.a(this.d);
/* 41 */     paramPacketDataSerializer.a(this.b);
/* 42 */     paramPacketDataSerializer.writeByte(this.c.b());
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 47 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnEntityPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */