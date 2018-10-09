/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutSpawnEntityLiving
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private byte i;
/*     */   private byte j;
/*     */   private byte k;
/*     */   private DataWatcher l;
/*     */   private List<DataWatcher.WatchableObject> m;
/*     */   
/*     */   public PacketPlayOutSpawnEntityLiving() {}
/*     */   
/*     */   public PacketPlayOutSpawnEntityLiving(EntityLiving paramEntityLiving)
/*     */   {
/*  35 */     this.a = paramEntityLiving.getId();
/*     */     
/*  37 */     this.b = ((byte)EntityTypes.a(paramEntityLiving));
/*  38 */     this.c = MathHelper.floor(paramEntityLiving.locX * 32.0D);
/*  39 */     this.d = MathHelper.floor(paramEntityLiving.locY * 32.0D);
/*  40 */     this.e = MathHelper.floor(paramEntityLiving.locZ * 32.0D);
/*  41 */     this.i = ((byte)(int)(paramEntityLiving.yaw * 256.0F / 360.0F));
/*  42 */     this.j = ((byte)(int)(paramEntityLiving.pitch * 256.0F / 360.0F));
/*  43 */     this.k = ((byte)(int)(paramEntityLiving.aK * 256.0F / 360.0F));
/*     */     
/*     */ 
/*  46 */     double d1 = 3.9D;
/*  47 */     double d2 = paramEntityLiving.motX;
/*  48 */     double d3 = paramEntityLiving.motY;
/*  49 */     double d4 = paramEntityLiving.motZ;
/*  50 */     if (d2 < -d1) {
/*  51 */       d2 = -d1;
/*     */     }
/*  53 */     if (d3 < -d1) {
/*  54 */       d3 = -d1;
/*     */     }
/*  56 */     if (d4 < -d1) {
/*  57 */       d4 = -d1;
/*     */     }
/*  59 */     if (d2 > d1) {
/*  60 */       d2 = d1;
/*     */     }
/*  62 */     if (d3 > d1) {
/*  63 */       d3 = d1;
/*     */     }
/*  65 */     if (d4 > d1) {
/*  66 */       d4 = d1;
/*     */     }
/*  68 */     this.f = ((int)(d2 * 8000.0D));
/*  69 */     this.g = ((int)(d3 * 8000.0D));
/*  70 */     this.h = ((int)(d4 * 8000.0D));
/*     */     
/*  72 */     this.l = paramEntityLiving.getDataWatcher();
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  77 */     this.a = paramPacketDataSerializer.e();
/*  78 */     this.b = (paramPacketDataSerializer.readByte() & 0xFF);
/*  79 */     this.c = paramPacketDataSerializer.readInt();
/*  80 */     this.d = paramPacketDataSerializer.readInt();
/*  81 */     this.e = paramPacketDataSerializer.readInt();
/*  82 */     this.i = paramPacketDataSerializer.readByte();
/*  83 */     this.j = paramPacketDataSerializer.readByte();
/*  84 */     this.k = paramPacketDataSerializer.readByte();
/*  85 */     this.f = paramPacketDataSerializer.readShort();
/*  86 */     this.g = paramPacketDataSerializer.readShort();
/*  87 */     this.h = paramPacketDataSerializer.readShort();
/*  88 */     this.m = DataWatcher.b(paramPacketDataSerializer);
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  93 */     paramPacketDataSerializer.b(this.a);
/*  94 */     paramPacketDataSerializer.writeByte(this.b & 0xFF);
/*  95 */     paramPacketDataSerializer.writeInt(this.c);
/*  96 */     paramPacketDataSerializer.writeInt(this.d);
/*  97 */     paramPacketDataSerializer.writeInt(this.e);
/*  98 */     paramPacketDataSerializer.writeByte(this.i);
/*  99 */     paramPacketDataSerializer.writeByte(this.j);
/* 100 */     paramPacketDataSerializer.writeByte(this.k);
/* 101 */     paramPacketDataSerializer.writeShort(this.f);
/* 102 */     paramPacketDataSerializer.writeShort(this.g);
/* 103 */     paramPacketDataSerializer.writeShort(this.h);
/* 104 */     this.l.a(paramPacketDataSerializer);
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 109 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnEntityLiving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */