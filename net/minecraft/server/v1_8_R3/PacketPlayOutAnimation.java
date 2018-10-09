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
/*    */ 
/*    */ public class PacketPlayOutAnimation
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutAnimation() {}
/*    */   
/*    */   public PacketPlayOutAnimation(Entity paramEntity, int paramInt)
/*    */   {
/* 26 */     this.a = paramEntity.getId();
/* 27 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.e();
/* 33 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 38 */     paramPacketDataSerializer.b(this.a);
/* 39 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 44 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */