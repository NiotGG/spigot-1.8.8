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
/*    */ public class PacketPlayInEnchantItem
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 22 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     this.a = paramPacketDataSerializer.readByte();
/* 28 */     this.b = paramPacketDataSerializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.writeByte(this.a);
/* 34 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public int a() {
/* 38 */     return this.a;
/*    */   }
/*    */   
/*    */   public int b() {
/* 42 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInEnchantItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */