/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutBlockBreakAnimation
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private BlockPosition b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutBlockBreakAnimation() {}
/*    */   
/*    */   public PacketPlayOutBlockBreakAnimation(int paramInt1, BlockPosition paramBlockPosition, int paramInt2)
/*    */   {
/* 19 */     this.a = paramInt1;
/* 20 */     this.b = paramBlockPosition;
/* 21 */     this.c = paramInt2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     this.a = paramPacketDataSerializer.e();
/* 27 */     this.b = paramPacketDataSerializer.c();
/* 28 */     this.c = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.b(this.a);
/* 34 */     paramPacketDataSerializer.a(this.b);
/* 35 */     paramPacketDataSerializer.writeByte(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 40 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutBlockBreakAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */