/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutCamera
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   public int a;
/*    */   
/*    */   public PacketPlayOutCamera() {}
/*    */   
/*    */   public PacketPlayOutCamera(Entity paramEntity)
/*    */   {
/* 19 */     this.a = paramEntity.getId();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     this.a = paramPacketDataSerializer.e();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     paramPacketDataSerializer.b(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 34 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutCamera.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */