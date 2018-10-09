/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PacketPlayInFlying implements Packet<PacketListenerPlayIn>
/*     */ {
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected float yaw;
/*     */   protected float pitch;
/*     */   protected boolean f;
/*     */   protected boolean hasPos;
/*     */   protected boolean hasLook;
/*     */   
/*     */   public static class PacketPlayInPositionLook
/*     */     extends PacketPlayInFlying
/*     */   {
/*     */     public PacketPlayInPositionLook()
/*     */     {
/*  21 */       this.hasPos = true;
/*  22 */       this.hasLook = true;
/*     */     }
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
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer)
/*     */       throws IOException
/*     */     {
/*  38 */       this.x = paramPacketDataSerializer.readDouble();
/*  39 */       this.y = paramPacketDataSerializer.readDouble();
/*  40 */       this.z = paramPacketDataSerializer.readDouble();
/*  41 */       this.yaw = paramPacketDataSerializer.readFloat();
/*  42 */       this.pitch = paramPacketDataSerializer.readFloat();
/*  43 */       super.a(paramPacketDataSerializer);
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  48 */       paramPacketDataSerializer.writeDouble(this.x);
/*  49 */       paramPacketDataSerializer.writeDouble(this.y);
/*  50 */       paramPacketDataSerializer.writeDouble(this.z);
/*  51 */       paramPacketDataSerializer.writeFloat(this.yaw);
/*  52 */       paramPacketDataSerializer.writeFloat(this.pitch);
/*  53 */       super.b(paramPacketDataSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PacketPlayInPosition extends PacketPlayInFlying
/*     */   {
/*     */     public PacketPlayInPosition() {
/*  60 */       this.hasPos = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer)
/*     */       throws IOException
/*     */     {
/*  73 */       this.x = paramPacketDataSerializer.readDouble();
/*  74 */       this.y = paramPacketDataSerializer.readDouble();
/*  75 */       this.z = paramPacketDataSerializer.readDouble();
/*  76 */       super.a(paramPacketDataSerializer);
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  81 */       paramPacketDataSerializer.writeDouble(this.x);
/*  82 */       paramPacketDataSerializer.writeDouble(this.y);
/*  83 */       paramPacketDataSerializer.writeDouble(this.z);
/*  84 */       super.b(paramPacketDataSerializer);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PacketPlayInLook extends PacketPlayInFlying
/*     */   {
/*     */     public PacketPlayInLook() {
/*  91 */       this.hasLook = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer)
/*     */       throws IOException
/*     */     {
/* 103 */       this.yaw = paramPacketDataSerializer.readFloat();
/* 104 */       this.pitch = paramPacketDataSerializer.readFloat();
/* 105 */       super.a(paramPacketDataSerializer);
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/* 110 */       paramPacketDataSerializer.writeFloat(this.yaw);
/* 111 */       paramPacketDataSerializer.writeFloat(this.pitch);
/* 112 */       super.b(paramPacketDataSerializer);
/*     */     }
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
/*     */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*     */   {
/* 126 */     paramPacketListenerPlayIn.a(this);
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/* 131 */     this.f = (paramPacketDataSerializer.readUnsignedByte() != 0);
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/* 136 */     paramPacketDataSerializer.writeByte(this.f ? 1 : 0);
/*     */   }
/*     */   
/*     */   public double a() {
/* 140 */     return this.x;
/*     */   }
/*     */   
/*     */   public double b() {
/* 144 */     return this.y;
/*     */   }
/*     */   
/*     */   public double c() {
/* 148 */     return this.z;
/*     */   }
/*     */   
/*     */   public float d() {
/* 152 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float e() {
/* 156 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 160 */     return this.f;
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 164 */     return this.hasPos;
/*     */   }
/*     */   
/*     */   public boolean h() {
/* 168 */     return this.hasLook;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 172 */     this.hasPos = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInFlying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */