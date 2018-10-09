/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutPosition
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   private double c;
/*     */   private float d;
/*     */   private float e;
/*     */   private Set<EnumPlayerTeleportFlags> f;
/*     */   
/*     */   public PacketPlayOutPosition() {}
/*     */   
/*     */   public PacketPlayOutPosition(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, Set<EnumPlayerTeleportFlags> paramSet)
/*     */   {
/*  23 */     this.a = paramDouble1;
/*  24 */     this.b = paramDouble2;
/*  25 */     this.c = paramDouble3;
/*  26 */     this.d = paramFloat1;
/*  27 */     this.e = paramFloat2;
/*  28 */     this.f = paramSet;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  33 */     this.a = paramPacketDataSerializer.readDouble();
/*  34 */     this.b = paramPacketDataSerializer.readDouble();
/*  35 */     this.c = paramPacketDataSerializer.readDouble();
/*  36 */     this.d = paramPacketDataSerializer.readFloat();
/*  37 */     this.e = paramPacketDataSerializer.readFloat();
/*  38 */     this.f = EnumPlayerTeleportFlags.a(paramPacketDataSerializer.readUnsignedByte());
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/*  43 */     paramPacketDataSerializer.writeDouble(this.a);
/*  44 */     paramPacketDataSerializer.writeDouble(this.b);
/*  45 */     paramPacketDataSerializer.writeDouble(this.c);
/*  46 */     paramPacketDataSerializer.writeFloat(this.d);
/*  47 */     paramPacketDataSerializer.writeFloat(this.e);
/*  48 */     paramPacketDataSerializer.writeByte(EnumPlayerTeleportFlags.a(this.f));
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/*  53 */     paramPacketListenerPlayOut.a(this);
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
/*     */   public static enum EnumPlayerTeleportFlags
/*     */   {
/*     */     private int f;
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
/*     */     private EnumPlayerTeleportFlags(int paramInt)
/*     */     {
/*  91 */       this.f = paramInt;
/*     */     }
/*     */     
/*     */     private int a() {
/*  95 */       return 1 << this.f;
/*     */     }
/*     */     
/*     */     private boolean b(int paramInt) {
/*  99 */       return (paramInt & a()) == a();
/*     */     }
/*     */     
/*     */     public static Set<EnumPlayerTeleportFlags> a(int paramInt) {
/* 103 */       EnumSet localEnumSet = EnumSet.noneOf(EnumPlayerTeleportFlags.class);
/*     */       
/* 105 */       for (EnumPlayerTeleportFlags localEnumPlayerTeleportFlags : values()) {
/* 106 */         if (localEnumPlayerTeleportFlags.b(paramInt)) {
/* 107 */           localEnumSet.add(localEnumPlayerTeleportFlags);
/*     */         }
/*     */       }
/*     */       
/* 111 */       return localEnumSet;
/*     */     }
/*     */     
/*     */     public static int a(Set<EnumPlayerTeleportFlags> paramSet) {
/* 115 */       int i = 0;
/*     */       
/* 117 */       for (EnumPlayerTeleportFlags localEnumPlayerTeleportFlags : paramSet) {
/* 118 */         i |= localEnumPlayerTeleportFlags.a();
/*     */       }
/*     */       
/* 121 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */