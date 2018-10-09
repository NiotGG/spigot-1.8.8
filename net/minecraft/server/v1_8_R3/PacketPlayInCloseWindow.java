/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayInCloseWindow implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public PacketPlayInCloseWindow() {}
/*    */   
/*    */   public PacketPlayInCloseWindow(int id)
/*    */   {
/* 13 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn packetlistenerplayin)
/*    */   {
/* 18 */     packetlistenerplayin.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 22 */     this.id = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 26 */     packetdataserializer.writeByte(this.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInCloseWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */