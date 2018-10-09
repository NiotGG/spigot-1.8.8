/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutResourcePackSend
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private String a;
/*    */   private String b;
/*    */   
/*    */   public PacketPlayOutResourcePackSend() {}
/*    */   
/*    */   public PacketPlayOutResourcePackSend(String paramString1, String paramString2)
/*    */   {
/* 20 */     this.a = paramString1;
/* 21 */     this.b = paramString2;
/*    */     
/* 23 */     if (paramString2.length() > 40) {
/* 24 */       throw new IllegalArgumentException("Hash is too long (max 40, was " + paramString2.length() + ")");
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     this.a = paramPacketDataSerializer.c(32767);
/* 31 */     this.b = paramPacketDataSerializer.c(40);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     paramPacketDataSerializer.a(this.a);
/* 37 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 42 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutResourcePackSend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */