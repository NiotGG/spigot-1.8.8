/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutWorldBorder
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private EnumWorldBorderAction a;
/*     */   private int b;
/*     */   private double c;
/*     */   private double d;
/*     */   private double e;
/*     */   private double f;
/*     */   private long g;
/*     */   private int h;
/*     */   private int i;
/*     */   
/*     */   public PacketPlayOutWorldBorder() {}
/*     */   
/*     */   public PacketPlayOutWorldBorder(WorldBorder paramWorldBorder, EnumWorldBorderAction paramEnumWorldBorderAction)
/*     */   {
/*  25 */     this.a = paramEnumWorldBorderAction;
/*  26 */     this.c = paramWorldBorder.getCenterX();
/*  27 */     this.d = paramWorldBorder.getCenterZ();
/*  28 */     this.f = paramWorldBorder.getSize();
/*  29 */     this.e = paramWorldBorder.j();
/*  30 */     this.g = paramWorldBorder.i();
/*  31 */     this.b = paramWorldBorder.l();
/*  32 */     this.i = paramWorldBorder.getWarningDistance();
/*  33 */     this.h = paramWorldBorder.getWarningTime();
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  38 */     this.a = ((EnumWorldBorderAction)paramPacketDataSerializer.a(EnumWorldBorderAction.class));
/*     */     
/*  40 */     switch (1.a[this.a.ordinal()]) {
/*     */     case 1: 
/*  42 */       this.e = paramPacketDataSerializer.readDouble();
/*  43 */       break;
/*     */     case 2: 
/*  45 */       this.f = paramPacketDataSerializer.readDouble();
/*  46 */       this.e = paramPacketDataSerializer.readDouble();
/*  47 */       this.g = paramPacketDataSerializer.f();
/*  48 */       break;
/*     */     case 3: 
/*  50 */       this.c = paramPacketDataSerializer.readDouble();
/*  51 */       this.d = paramPacketDataSerializer.readDouble();
/*  52 */       break;
/*     */     case 4: 
/*  54 */       this.i = paramPacketDataSerializer.e();
/*  55 */       break;
/*     */     case 5: 
/*  57 */       this.h = paramPacketDataSerializer.e();
/*  58 */       break;
/*     */     case 6: 
/*  60 */       this.c = paramPacketDataSerializer.readDouble();
/*  61 */       this.d = paramPacketDataSerializer.readDouble();
/*  62 */       this.f = paramPacketDataSerializer.readDouble();
/*  63 */       this.e = paramPacketDataSerializer.readDouble();
/*  64 */       this.g = paramPacketDataSerializer.f();
/*  65 */       this.b = paramPacketDataSerializer.e();
/*  66 */       this.i = paramPacketDataSerializer.e();
/*  67 */       this.h = paramPacketDataSerializer.e();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer)
/*     */     throws IOException
/*     */   {
/*  74 */     paramPacketDataSerializer.a(this.a);
/*     */     
/*  76 */     switch (1.a[this.a.ordinal()]) {
/*     */     case 1: 
/*  78 */       paramPacketDataSerializer.writeDouble(this.e);
/*  79 */       break;
/*     */     case 2: 
/*  81 */       paramPacketDataSerializer.writeDouble(this.f);
/*  82 */       paramPacketDataSerializer.writeDouble(this.e);
/*  83 */       paramPacketDataSerializer.b(this.g);
/*  84 */       break;
/*     */     case 3: 
/*  86 */       paramPacketDataSerializer.writeDouble(this.c);
/*  87 */       paramPacketDataSerializer.writeDouble(this.d);
/*  88 */       break;
/*     */     case 5: 
/*  90 */       paramPacketDataSerializer.b(this.h);
/*  91 */       break;
/*     */     case 4: 
/*  93 */       paramPacketDataSerializer.b(this.i);
/*  94 */       break;
/*     */     case 6: 
/*  96 */       paramPacketDataSerializer.writeDouble(this.c);
/*  97 */       paramPacketDataSerializer.writeDouble(this.d);
/*  98 */       paramPacketDataSerializer.writeDouble(this.f);
/*  99 */       paramPacketDataSerializer.writeDouble(this.e);
/* 100 */       paramPacketDataSerializer.b(this.g);
/* 101 */       paramPacketDataSerializer.b(this.b);
/* 102 */       paramPacketDataSerializer.b(this.i);
/* 103 */       paramPacketDataSerializer.b(this.h);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 110 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */   
/*     */   public static enum EnumWorldBorderAction
/*     */   {
/*     */     private EnumWorldBorderAction() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutWorldBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */