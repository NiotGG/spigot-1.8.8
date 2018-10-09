/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketHandshakingInSetProtocol
/*    */   implements Packet<PacketHandshakingInListener>
/*    */ {
/*    */   private int a;
/*    */   public String hostname;
/*    */   public int port;
/*    */   private EnumProtocol d;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {
/* 15 */     this.a = packetdataserializer.e();
/* 16 */     this.hostname = packetdataserializer.c(32767);
/* 17 */     this.port = packetdataserializer.readUnsignedShort();
/* 18 */     this.d = EnumProtocol.a(packetdataserializer.e());
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 22 */     packetdataserializer.b(this.a);
/* 23 */     packetdataserializer.a(this.hostname);
/* 24 */     packetdataserializer.writeShort(this.port);
/* 25 */     packetdataserializer.b(this.d.a());
/*    */   }
/*    */   
/*    */   public void a(PacketHandshakingInListener packethandshakinginlistener) {
/* 29 */     packethandshakinginlistener.a(this);
/*    */   }
/*    */   
/*    */   public EnumProtocol a() {
/* 33 */     return this.d;
/*    */   }
/*    */   
/*    */   public int b() {
/* 37 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketHandshakingInSetProtocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */