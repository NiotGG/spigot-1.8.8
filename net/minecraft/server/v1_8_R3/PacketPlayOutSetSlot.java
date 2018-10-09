/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutSetSlot
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private ItemStack c;
/*    */   
/*    */   public PacketPlayOutSetSlot() {}
/*    */   
/*    */   public PacketPlayOutSetSlot(int paramInt1, int paramInt2, ItemStack paramItemStack)
/*    */   {
/* 20 */     this.a = paramInt1;
/* 21 */     this.b = paramInt2;
/* 22 */     this.c = (paramItemStack == null ? null : paramItemStack.cloneItemStack());
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 27 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.readByte();
/* 33 */     this.b = paramPacketDataSerializer.readShort();
/* 34 */     this.c = paramPacketDataSerializer.i();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 39 */     paramPacketDataSerializer.writeByte(this.a);
/* 40 */     paramPacketDataSerializer.writeShort(this.b);
/* 41 */     paramPacketDataSerializer.a(this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */