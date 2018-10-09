/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutWorldParticles
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private EnumParticle a;
/*     */   private float b;
/*     */   private float c;
/*     */   private float d;
/*     */   private float e;
/*     */   private float f;
/*     */   private float g;
/*     */   private float h;
/*     */   private int i;
/*     */   private boolean j;
/*     */   private int[] k;
/*     */   
/*     */   public PacketPlayOutWorldParticles() {}
/*     */   
/*     */   public PacketPlayOutWorldParticles(EnumParticle paramEnumParticle, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, int paramInt, int... paramVarArgs)
/*     */   {
/*  28 */     this.a = paramEnumParticle;
/*  29 */     this.j = paramBoolean;
/*  30 */     this.b = paramFloat1;
/*  31 */     this.c = paramFloat2;
/*  32 */     this.d = paramFloat3;
/*  33 */     this.e = paramFloat4;
/*  34 */     this.f = paramFloat5;
/*  35 */     this.g = paramFloat6;
/*  36 */     this.h = paramFloat7;
/*  37 */     this.i = paramInt;
/*  38 */     this.k = paramVarArgs;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  43 */     this.a = EnumParticle.a(paramPacketDataSerializer.readInt());
/*  44 */     if (this.a == null) {
/*  45 */       this.a = EnumParticle.BARRIER;
/*     */     }
/*  47 */     this.j = paramPacketDataSerializer.readBoolean();
/*  48 */     this.b = paramPacketDataSerializer.readFloat();
/*  49 */     this.c = paramPacketDataSerializer.readFloat();
/*  50 */     this.d = paramPacketDataSerializer.readFloat();
/*  51 */     this.e = paramPacketDataSerializer.readFloat();
/*  52 */     this.f = paramPacketDataSerializer.readFloat();
/*  53 */     this.g = paramPacketDataSerializer.readFloat();
/*  54 */     this.h = paramPacketDataSerializer.readFloat();
/*  55 */     this.i = paramPacketDataSerializer.readInt();
/*  56 */     int m = this.a.d();
/*  57 */     this.k = new int[m];
/*  58 */     for (int n = 0; n < m; n++) {
/*  59 */       this.k[n] = paramPacketDataSerializer.e();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  65 */     paramPacketDataSerializer.writeInt(this.a.c());
/*  66 */     paramPacketDataSerializer.writeBoolean(this.j);
/*  67 */     paramPacketDataSerializer.writeFloat(this.b);
/*  68 */     paramPacketDataSerializer.writeFloat(this.c);
/*  69 */     paramPacketDataSerializer.writeFloat(this.d);
/*  70 */     paramPacketDataSerializer.writeFloat(this.e);
/*  71 */     paramPacketDataSerializer.writeFloat(this.f);
/*  72 */     paramPacketDataSerializer.writeFloat(this.g);
/*  73 */     paramPacketDataSerializer.writeFloat(this.h);
/*  74 */     paramPacketDataSerializer.writeInt(this.i);
/*  75 */     int m = this.a.d();
/*  76 */     for (int n = 0; n < m; n++) {
/*  77 */       paramPacketDataSerializer.b(this.k[n]);
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
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 128 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutWorldParticles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */