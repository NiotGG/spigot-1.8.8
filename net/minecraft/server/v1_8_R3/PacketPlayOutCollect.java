/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutCollect
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutCollect() {}
/*    */   
/*    */   public PacketPlayOutCollect(int paramInt1, int paramInt2)
/*    */   {
/* 16 */     this.a = paramInt1;
/* 17 */     this.b = paramInt2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 22 */     this.a = paramPacketDataSerializer.e();
/* 23 */     this.b = paramPacketDataSerializer.e();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     paramPacketDataSerializer.b(this.a);
/* 29 */     paramPacketDataSerializer.b(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 34 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutCollect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */