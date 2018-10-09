/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityEquipment
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private ItemStack c;
/*    */   
/*    */   public PacketPlayOutEntityEquipment() {}
/*    */   
/*    */   public PacketPlayOutEntityEquipment(int paramInt1, int paramInt2, ItemStack paramItemStack)
/*    */   {
/* 18 */     this.a = paramInt1;
/* 19 */     this.b = paramInt2;
/* 20 */     this.c = (paramItemStack == null ? null : paramItemStack.cloneItemStack());
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 25 */     this.a = paramPacketDataSerializer.e();
/* 26 */     this.b = paramPacketDataSerializer.readShort();
/* 27 */     this.c = paramPacketDataSerializer.i();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     paramPacketDataSerializer.b(this.a);
/* 33 */     paramPacketDataSerializer.writeShort(this.b);
/* 34 */     paramPacketDataSerializer.a(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 39 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityEquipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */