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
/*    */ public class PacketPlayInHeldItemSlot
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int itemInHandIndex;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 20 */     this.itemInHandIndex = paramPacketDataSerializer.readShort();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 25 */     paramPacketDataSerializer.writeShort(this.itemInHandIndex);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 30 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public int a() {
/* 34 */     return this.itemInHandIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInHeldItemSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */