/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutOpenWindow implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private String b;
/*    */   private IChatBaseComponent c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutOpenWindow() {}
/*    */   
/*    */   public PacketPlayOutOpenWindow(int i, String s, IChatBaseComponent ichatbasecomponent) {
/* 16 */     this(i, s, ichatbasecomponent, 0);
/*    */   }
/*    */   
/*    */   public PacketPlayOutOpenWindow(int i, String s, IChatBaseComponent ichatbasecomponent, int j) {
/* 20 */     this.a = i;
/* 21 */     this.b = s;
/* 22 */     this.c = ichatbasecomponent;
/* 23 */     this.d = j;
/*    */   }
/*    */   
/*    */   public PacketPlayOutOpenWindow(int i, String s, IChatBaseComponent ichatbasecomponent, int j, int k) {
/* 27 */     this(i, s, ichatbasecomponent, j);
/* 28 */     this.e = k;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut packetlistenerplayout) {
/* 32 */     packetlistenerplayout.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 36 */     this.a = packetdataserializer.readUnsignedByte();
/* 37 */     this.b = packetdataserializer.c(32);
/* 38 */     this.c = packetdataserializer.d();
/* 39 */     this.d = packetdataserializer.readUnsignedByte();
/* 40 */     if (this.b.equals("EntityHorse")) {
/* 41 */       this.e = packetdataserializer.readInt();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException
/*    */   {
/* 47 */     packetdataserializer.writeByte(this.a);
/* 48 */     packetdataserializer.a(this.b);
/* 49 */     packetdataserializer.a(this.c);
/* 50 */     packetdataserializer.writeByte(this.d);
/* 51 */     if (this.b.equals("EntityHorse")) {
/* 52 */       packetdataserializer.writeInt(this.e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutOpenWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */