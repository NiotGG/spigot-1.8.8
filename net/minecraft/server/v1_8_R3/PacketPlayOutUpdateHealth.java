/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutUpdateHealth
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private float a;
/*    */   private int b;
/*    */   private float c;
/*    */   
/*    */   public PacketPlayOutUpdateHealth() {}
/*    */   
/*    */   public PacketPlayOutUpdateHealth(float paramFloat1, int paramInt, float paramFloat2)
/*    */   {
/* 17 */     this.a = paramFloat1;
/* 18 */     this.b = paramInt;
/* 19 */     this.c = paramFloat2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     this.a = paramPacketDataSerializer.readFloat();
/* 25 */     this.b = paramPacketDataSerializer.e();
/* 26 */     this.c = paramPacketDataSerializer.readFloat();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 31 */     paramPacketDataSerializer.writeFloat(this.a);
/* 32 */     paramPacketDataSerializer.b(this.b);
/* 33 */     paramPacketDataSerializer.writeFloat(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 38 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutUpdateHealth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */