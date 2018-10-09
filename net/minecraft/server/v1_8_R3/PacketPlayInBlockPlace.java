/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayInBlockPlace implements Packet<PacketListenerPlayIn>
/*    */ {
/*  7 */   private static final BlockPosition a = new BlockPosition(-1, -1, -1);
/*    */   private BlockPosition b;
/*    */   private int c;
/*    */   private ItemStack d;
/*    */   private float e;
/*    */   private float f;
/*    */   private float g;
/*    */   public long timestamp;
/*    */   
/*    */   public PacketPlayInBlockPlace() {}
/*    */   
/*    */   public PacketPlayInBlockPlace(ItemStack itemstack)
/*    */   {
/* 20 */     this(a, 255, itemstack, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public PacketPlayInBlockPlace(BlockPosition blockposition, int i, ItemStack itemstack, float f, float f1, float f2) {
/* 24 */     this.b = blockposition;
/* 25 */     this.c = i;
/* 26 */     this.d = (itemstack != null ? itemstack.cloneItemStack() : null);
/* 27 */     this.e = f;
/* 28 */     this.f = f1;
/* 29 */     this.g = f2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 33 */     this.timestamp = System.currentTimeMillis();
/* 34 */     this.b = packetdataserializer.c();
/* 35 */     this.c = packetdataserializer.readUnsignedByte();
/* 36 */     this.d = packetdataserializer.i();
/* 37 */     this.e = (packetdataserializer.readUnsignedByte() / 16.0F);
/* 38 */     this.f = (packetdataserializer.readUnsignedByte() / 16.0F);
/* 39 */     this.g = (packetdataserializer.readUnsignedByte() / 16.0F);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 43 */     packetdataserializer.a(this.b);
/* 44 */     packetdataserializer.writeByte(this.c);
/* 45 */     packetdataserializer.a(this.d);
/* 46 */     packetdataserializer.writeByte((int)(this.e * 16.0F));
/* 47 */     packetdataserializer.writeByte((int)(this.f * 16.0F));
/* 48 */     packetdataserializer.writeByte((int)(this.g * 16.0F));
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn packetlistenerplayin) {
/* 52 */     packetlistenerplayin.a(this);
/*    */   }
/*    */   
/*    */   public BlockPosition a() {
/* 56 */     return this.b;
/*    */   }
/*    */   
/*    */   public int getFace() {
/* 60 */     return this.c;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 64 */     return this.d;
/*    */   }
/*    */   
/*    */   public float d() {
/* 68 */     return this.e;
/*    */   }
/*    */   
/*    */   public float e() {
/* 72 */     return this.f;
/*    */   }
/*    */   
/*    */   public float f() {
/* 76 */     return this.g;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInBlockPlace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */