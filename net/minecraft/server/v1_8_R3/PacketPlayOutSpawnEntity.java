/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutSpawnEntity
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
/*     */   private int i;
/*     */   private int j;
/*     */   private int k;
/*     */   
/*     */   public PacketPlayOutSpawnEntity() {}
/*     */   
/*     */   public PacketPlayOutSpawnEntity(Entity paramEntity, int paramInt)
/*     */   {
/*  51 */     this(paramEntity, paramInt, 0);
/*     */   }
/*     */   
/*     */   public PacketPlayOutSpawnEntity(Entity paramEntity, int paramInt1, int paramInt2) {
/*  55 */     this.a = paramEntity.getId();
/*  56 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/*  57 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/*  58 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/*  59 */     this.h = MathHelper.d(paramEntity.pitch * 256.0F / 360.0F);
/*  60 */     this.i = MathHelper.d(paramEntity.yaw * 256.0F / 360.0F);
/*  61 */     this.j = paramInt1;
/*  62 */     this.k = paramInt2;
/*  63 */     if (paramInt2 > 0) {
/*  64 */       double d1 = paramEntity.motX;
/*  65 */       double d2 = paramEntity.motY;
/*  66 */       double d3 = paramEntity.motZ;
/*  67 */       double d4 = 3.9D;
/*  68 */       if (d1 < -d4) {
/*  69 */         d1 = -d4;
/*     */       }
/*  71 */       if (d2 < -d4) {
/*  72 */         d2 = -d4;
/*     */       }
/*  74 */       if (d3 < -d4) {
/*  75 */         d3 = -d4;
/*     */       }
/*  77 */       if (d1 > d4) {
/*  78 */         d1 = d4;
/*     */       }
/*  80 */       if (d2 > d4) {
/*  81 */         d2 = d4;
/*     */       }
/*  83 */       if (d3 > d4) {
/*  84 */         d3 = d4;
/*     */       }
/*  86 */       this.e = ((int)(d1 * 8000.0D));
/*  87 */       this.f = ((int)(d2 * 8000.0D));
/*  88 */       this.g = ((int)(d3 * 8000.0D));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  94 */     this.a = paramPacketDataSerializer.e();
/*  95 */     this.j = paramPacketDataSerializer.readByte();
/*  96 */     this.b = paramPacketDataSerializer.readInt();
/*  97 */     this.c = paramPacketDataSerializer.readInt();
/*  98 */     this.d = paramPacketDataSerializer.readInt();
/*  99 */     this.h = paramPacketDataSerializer.readByte();
/* 100 */     this.i = paramPacketDataSerializer.readByte();
/* 101 */     this.k = paramPacketDataSerializer.readInt();
/* 102 */     if (this.k > 0) {
/* 103 */       this.e = paramPacketDataSerializer.readShort();
/* 104 */       this.f = paramPacketDataSerializer.readShort();
/* 105 */       this.g = paramPacketDataSerializer.readShort();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/* 111 */     paramPacketDataSerializer.b(this.a);
/* 112 */     paramPacketDataSerializer.writeByte(this.j);
/* 113 */     paramPacketDataSerializer.writeInt(this.b);
/* 114 */     paramPacketDataSerializer.writeInt(this.c);
/* 115 */     paramPacketDataSerializer.writeInt(this.d);
/* 116 */     paramPacketDataSerializer.writeByte(this.h);
/* 117 */     paramPacketDataSerializer.writeByte(this.i);
/* 118 */     paramPacketDataSerializer.writeInt(this.k);
/* 119 */     if (this.k > 0) {
/* 120 */       paramPacketDataSerializer.writeShort(this.e);
/* 121 */       paramPacketDataSerializer.writeShort(this.f);
/* 122 */       paramPacketDataSerializer.writeShort(this.g);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 128 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(int paramInt)
/*     */   {
/* 176 */     this.b = paramInt;
/*     */   }
/*     */   
/*     */   public void b(int paramInt) {
/* 180 */     this.c = paramInt;
/*     */   }
/*     */   
/*     */   public void c(int paramInt) {
/* 184 */     this.d = paramInt;
/*     */   }
/*     */   
/*     */   public void d(int paramInt) {
/* 188 */     this.e = paramInt;
/*     */   }
/*     */   
/*     */   public void e(int paramInt) {
/* 192 */     this.f = paramInt;
/*     */   }
/*     */   
/*     */   public void f(int paramInt) {
/* 196 */     this.g = paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */