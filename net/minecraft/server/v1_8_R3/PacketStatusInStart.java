/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketStatusInStart
/*    */   implements Packet<PacketStatusInListener>
/*    */ {
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public void a(PacketStatusInListener paramPacketStatusInListener)
/*    */   {
/* 23 */     paramPacketStatusInListener.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketStatusInStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */