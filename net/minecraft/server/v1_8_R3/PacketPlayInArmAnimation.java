/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayInArmAnimation
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   public long timestamp;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {
/* 12 */     this.timestamp = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {}
/*    */   
/* 18 */   public void a(PacketListenerPlayIn packetlistenerplayin) { packetlistenerplayin.a(this); }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInArmAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */