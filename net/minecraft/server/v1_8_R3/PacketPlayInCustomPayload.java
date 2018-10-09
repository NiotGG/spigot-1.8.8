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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInCustomPayload
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private String a;
/*    */   private PacketDataSerializer b;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 39 */     this.a = paramPacketDataSerializer.c(20);
/* 40 */     int i = paramPacketDataSerializer.readableBytes();
/* 41 */     if ((i < 0) || (i > 32767)) {
/* 42 */       throw new IOException("Payload may not be larger than 32767 bytes");
/*    */     }
/* 44 */     this.b = new PacketDataSerializer(paramPacketDataSerializer.readBytes(i));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 49 */     paramPacketDataSerializer.a(this.a);
/* 50 */     paramPacketDataSerializer.writeBytes(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 55 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public String a() {
/* 59 */     return this.a;
/*    */   }
/*    */   
/*    */   public PacketDataSerializer b() {
/* 63 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInCustomPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */