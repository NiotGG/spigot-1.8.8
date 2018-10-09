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
/*    */ public class PacketPlayInWindowClick
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private int a;
/*    */   private int slot;
/*    */   private int button;
/*    */   private short d;
/*    */   private ItemStack item;
/*    */   private int shift;
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 31 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     this.a = paramPacketDataSerializer.readByte();
/* 37 */     this.slot = paramPacketDataSerializer.readShort();
/* 38 */     this.button = paramPacketDataSerializer.readByte();
/* 39 */     this.d = paramPacketDataSerializer.readShort();
/* 40 */     this.shift = paramPacketDataSerializer.readByte();
/*    */     
/* 42 */     this.item = paramPacketDataSerializer.i();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 47 */     paramPacketDataSerializer.writeByte(this.a);
/* 48 */     paramPacketDataSerializer.writeShort(this.slot);
/* 49 */     paramPacketDataSerializer.writeByte(this.button);
/* 50 */     paramPacketDataSerializer.writeShort(this.d);
/* 51 */     paramPacketDataSerializer.writeByte(this.shift);
/*    */     
/* 53 */     paramPacketDataSerializer.a(this.item);
/*    */   }
/*    */   
/*    */   public int a() {
/* 57 */     return this.a;
/*    */   }
/*    */   
/*    */   public int b() {
/* 61 */     return this.slot;
/*    */   }
/*    */   
/*    */   public int c() {
/* 65 */     return this.button;
/*    */   }
/*    */   
/*    */   public short d() {
/* 69 */     return this.d;
/*    */   }
/*    */   
/*    */   public ItemStack e() {
/* 73 */     return this.item;
/*    */   }
/*    */   
/*    */   public int f() {
/* 77 */     return this.shift;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInWindowClick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */