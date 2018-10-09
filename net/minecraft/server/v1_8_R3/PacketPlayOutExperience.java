/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutExperience
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private float a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutExperience() {}
/*    */   
/*    */   public PacketPlayOutExperience(float paramFloat, int paramInt1, int paramInt2)
/*    */   {
/* 17 */     this.a = paramFloat;
/* 18 */     this.b = paramInt1;
/* 19 */     this.c = paramInt2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     this.a = paramPacketDataSerializer.readFloat();
/* 25 */     this.c = paramPacketDataSerializer.e();
/* 26 */     this.b = paramPacketDataSerializer.e();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 31 */     paramPacketDataSerializer.writeFloat(this.a);
/* 32 */     paramPacketDataSerializer.b(this.c);
/* 33 */     paramPacketDataSerializer.b(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 38 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */