/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutEntity
/*     */   implements Packet<PacketListenerPlayOut>
/*     */ {
/*     */   protected int a;
/*     */   protected byte b;
/*     */   protected byte c;
/*     */   protected byte d;
/*     */   protected byte e;
/*     */   protected byte f;
/*     */   protected boolean g;
/*     */   protected boolean h;
/*     */   public PacketPlayOutEntity() {}
/*     */   
/*     */   public static class PacketPlayOutRelEntityMoveLook
/*     */     extends PacketPlayOutEntity
/*     */   {
/*     */     public PacketPlayOutRelEntityMoveLook()
/*     */     {
/*  24 */       this.h = true;
/*     */     }
/*     */     
/*     */     public PacketPlayOutRelEntityMoveLook(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, boolean paramBoolean) {
/*  28 */       super();
/*  29 */       this.b = paramByte1;
/*  30 */       this.c = paramByte2;
/*  31 */       this.d = paramByte3;
/*  32 */       this.e = paramByte4;
/*  33 */       this.f = paramByte5;
/*  34 */       this.g = paramBoolean;
/*  35 */       this.h = true;
/*     */     }
/*     */     
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  40 */       super.a(paramPacketDataSerializer);
/*  41 */       this.b = paramPacketDataSerializer.readByte();
/*  42 */       this.c = paramPacketDataSerializer.readByte();
/*  43 */       this.d = paramPacketDataSerializer.readByte();
/*  44 */       this.e = paramPacketDataSerializer.readByte();
/*  45 */       this.f = paramPacketDataSerializer.readByte();
/*  46 */       this.g = paramPacketDataSerializer.readBoolean();
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  51 */       super.b(paramPacketDataSerializer);
/*  52 */       paramPacketDataSerializer.writeByte(this.b);
/*  53 */       paramPacketDataSerializer.writeByte(this.c);
/*  54 */       paramPacketDataSerializer.writeByte(this.d);
/*  55 */       paramPacketDataSerializer.writeByte(this.e);
/*  56 */       paramPacketDataSerializer.writeByte(this.f);
/*  57 */       paramPacketDataSerializer.writeBoolean(this.g);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PacketPlayOutRelEntityMove extends PacketPlayOutEntity
/*     */   {
/*     */     public PacketPlayOutRelEntityMove() {}
/*     */     
/*     */     public PacketPlayOutRelEntityMove(int paramInt, byte paramByte1, byte paramByte2, byte paramByte3, boolean paramBoolean)
/*     */     {
/*  67 */       super();
/*  68 */       this.b = paramByte1;
/*  69 */       this.c = paramByte2;
/*  70 */       this.d = paramByte3;
/*  71 */       this.g = paramBoolean;
/*     */     }
/*     */     
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  76 */       super.a(paramPacketDataSerializer);
/*  77 */       this.b = paramPacketDataSerializer.readByte();
/*  78 */       this.c = paramPacketDataSerializer.readByte();
/*  79 */       this.d = paramPacketDataSerializer.readByte();
/*  80 */       this.g = paramPacketDataSerializer.readBoolean();
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/*  85 */       super.b(paramPacketDataSerializer);
/*  86 */       paramPacketDataSerializer.writeByte(this.b);
/*  87 */       paramPacketDataSerializer.writeByte(this.c);
/*  88 */       paramPacketDataSerializer.writeByte(this.d);
/*  89 */       paramPacketDataSerializer.writeBoolean(this.g);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PacketPlayOutEntityLook extends PacketPlayOutEntity
/*     */   {
/*     */     public PacketPlayOutEntityLook() {
/*  96 */       this.h = true;
/*     */     }
/*     */     
/*     */     public PacketPlayOutEntityLook(int paramInt, byte paramByte1, byte paramByte2, boolean paramBoolean) {
/* 100 */       super();
/* 101 */       this.e = paramByte1;
/* 102 */       this.f = paramByte2;
/* 103 */       this.h = true;
/* 104 */       this.g = paramBoolean;
/*     */     }
/*     */     
/*     */     public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/* 109 */       super.a(paramPacketDataSerializer);
/* 110 */       this.e = paramPacketDataSerializer.readByte();
/* 111 */       this.f = paramPacketDataSerializer.readByte();
/* 112 */       this.g = paramPacketDataSerializer.readBoolean();
/*     */     }
/*     */     
/*     */     public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */     {
/* 117 */       super.b(paramPacketDataSerializer);
/* 118 */       paramPacketDataSerializer.writeByte(this.e);
/* 119 */       paramPacketDataSerializer.writeByte(this.f);
/* 120 */       paramPacketDataSerializer.writeBoolean(this.g);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PacketPlayOutEntity(int paramInt)
/*     */   {
/* 129 */     this.a = paramInt;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/* 134 */     this.a = paramPacketDataSerializer.e();
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*     */   {
/* 139 */     paramPacketDataSerializer.b(this.a);
/*     */   }
/*     */   
/*     */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*     */   {
/* 144 */     paramPacketListenerPlayOut.a(this);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 149 */     return "Entity_" + super.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */