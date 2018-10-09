/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutWindowItems
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private ItemStack[] b;
/*    */   
/*    */   public PacketPlayOutWindowItems() {}
/*    */   
/*    */   public PacketPlayOutWindowItems(int paramInt, List<ItemStack> paramList)
/*    */   {
/* 20 */     this.a = paramInt;
/* 21 */     this.b = new ItemStack[paramList.size()];
/* 22 */     for (int i = 0; i < this.b.length; i++) {
/* 23 */       ItemStack localItemStack = (ItemStack)paramList.get(i);
/* 24 */       this.b[i] = (localItemStack == null ? null : localItemStack.cloneItemStack());
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     this.a = paramPacketDataSerializer.readUnsignedByte();
/* 31 */     int i = paramPacketDataSerializer.readShort();
/* 32 */     this.b = new ItemStack[i];
/* 33 */     for (int j = 0; j < i; j++) {
/* 34 */       this.b[j] = paramPacketDataSerializer.i();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 40 */     paramPacketDataSerializer.writeByte(this.a);
/* 41 */     paramPacketDataSerializer.writeShort(this.b.length);
/* 42 */     for (ItemStack localItemStack : this.b) {
/* 43 */       paramPacketDataSerializer.a(localItemStack);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 49 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutWindowItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */