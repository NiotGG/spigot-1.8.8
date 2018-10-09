/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayInResourcePackStatus
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private String a;
/*    */   public EnumResourcePackStatus b;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {
/* 13 */     this.a = packetdataserializer.c(40);
/* 14 */     this.b = ((EnumResourcePackStatus)packetdataserializer.a(EnumResourcePackStatus.class));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 18 */     packetdataserializer.a(this.a);
/* 19 */     packetdataserializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn packetlistenerplayin) {
/* 23 */     packetlistenerplayin.a(this);
/*    */   }
/*    */   
/*    */   public static enum EnumResourcePackStatus
/*    */   {
/* 28 */     SUCCESSFULLY_LOADED,  DECLINED,  FAILED_DOWNLOAD,  ACCEPTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInResourcePackStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */