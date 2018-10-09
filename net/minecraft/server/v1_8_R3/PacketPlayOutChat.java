/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutChat implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   public net.md_5.bungee.api.chat.BaseComponent[] components;
/*    */   private byte b;
/*    */   
/*    */   public PacketPlayOutChat() {}
/*    */   
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent) {
/* 14 */     this(ichatbasecomponent, (byte)1);
/*    */   }
/*    */   
/*    */   public PacketPlayOutChat(IChatBaseComponent ichatbasecomponent, byte b0) {
/* 18 */     this.a = ichatbasecomponent;
/* 19 */     this.b = b0;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 23 */     this.a = packetdataserializer.d();
/* 24 */     this.b = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {
/* 29 */     if (this.components != null) {
/* 30 */       packetdataserializer.a(net.md_5.bungee.chat.ComponentSerializer.toString(this.components));
/*    */     } else {
/* 32 */       packetdataserializer.a(this.a);
/*    */     }
/*    */     
/* 35 */     packetdataserializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut packetlistenerplayout) {
/* 39 */     packetlistenerplayout.a(this);
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 43 */     return (this.b == 1) || (this.b == 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */