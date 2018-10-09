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
/*    */ public class PacketPlayInSetCreativeSlot
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int slot;
/*    */   private ItemStack b;
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 23 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     this.slot = paramPacketDataSerializer.readShort();
/* 29 */     this.b = paramPacketDataSerializer.i();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 34 */     paramPacketDataSerializer.writeShort(this.slot);
/* 35 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public int a() {
/* 39 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 43 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInSetCreativeSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */