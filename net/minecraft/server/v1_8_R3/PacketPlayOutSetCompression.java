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
/*    */ public class PacketPlayOutSetCompression
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 21 */     this.a = paramPacketDataSerializer.e();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     paramPacketDataSerializer.b(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 31 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSetCompression.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */