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
/*    */ public class PacketPlayOutCustomPayload
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private String a;
/*    */   private PacketDataSerializer b;
/*    */   
/*    */   public PacketPlayOutCustomPayload() {}
/*    */   
/*    */   public PacketPlayOutCustomPayload(String paramString, PacketDataSerializer paramPacketDataSerializer)
/*    */   {
/* 26 */     this.a = paramString;
/* 27 */     this.b = paramPacketDataSerializer;
/*    */     
/* 29 */     if (paramPacketDataSerializer.writerIndex() > 1048576) {
/* 30 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     this.a = paramPacketDataSerializer.c(20);
/* 37 */     int i = paramPacketDataSerializer.readableBytes();
/* 38 */     if ((i < 0) || (i > 1048576)) {
/* 39 */       throw new IOException("Payload may not be larger than 1048576 bytes");
/*    */     }
/* 41 */     this.b = new PacketDataSerializer(paramPacketDataSerializer.readBytes(i));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 46 */     paramPacketDataSerializer.a(this.a);
/* 47 */     paramPacketDataSerializer.writeBytes(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 52 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutCustomPayload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */