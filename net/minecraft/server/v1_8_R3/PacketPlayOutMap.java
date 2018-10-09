/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutMap
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private byte b;
/*    */   private MapIcon[] c;
/*    */   private int d;
/*    */   private int e;
/*    */   private int f;
/*    */   private int g;
/*    */   private byte[] h;
/*    */   
/*    */   public PacketPlayOutMap() {}
/*    */   
/*    */   public PacketPlayOutMap(int paramInt1, byte paramByte, Collection<MapIcon> paramCollection, byte[] paramArrayOfByte, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*    */   {
/* 29 */     this.a = paramInt1;
/* 30 */     this.b = paramByte;
/* 31 */     this.c = ((MapIcon[])paramCollection.toArray(new MapIcon[paramCollection.size()]));
/* 32 */     this.d = paramInt2;
/* 33 */     this.e = paramInt3;
/* 34 */     this.f = paramInt4;
/* 35 */     this.g = paramInt5;
/*    */     
/* 37 */     this.h = new byte[paramInt4 * paramInt5];
/* 38 */     for (int i = 0; i < paramInt4; i++) {
/* 39 */       for (int j = 0; j < paramInt5; j++) {
/* 40 */         this.h[(i + j * paramInt4)] = paramArrayOfByte[(paramInt2 + i + (paramInt3 + j) * 128)];
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 47 */     this.a = paramPacketDataSerializer.e();
/* 48 */     this.b = paramPacketDataSerializer.readByte();
/* 49 */     this.c = new MapIcon[paramPacketDataSerializer.e()];
/* 50 */     for (int i = 0; i < this.c.length; i++) {
/* 51 */       int j = (short)paramPacketDataSerializer.readByte();
/* 52 */       this.c[i] = new MapIcon((byte)(j >> 4 & 0xF), paramPacketDataSerializer.readByte(), paramPacketDataSerializer.readByte(), (byte)(j & 0xF));
/*    */     }
/* 54 */     this.f = paramPacketDataSerializer.readUnsignedByte();
/* 55 */     if (this.f > 0) {
/* 56 */       this.g = paramPacketDataSerializer.readUnsignedByte();
/* 57 */       this.d = paramPacketDataSerializer.readUnsignedByte();
/* 58 */       this.e = paramPacketDataSerializer.readUnsignedByte();
/* 59 */       this.h = paramPacketDataSerializer.a();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 65 */     paramPacketDataSerializer.b(this.a);
/* 66 */     paramPacketDataSerializer.writeByte(this.b);
/* 67 */     paramPacketDataSerializer.b(this.c.length);
/* 68 */     for (MapIcon localMapIcon : this.c) {
/* 69 */       paramPacketDataSerializer.writeByte((localMapIcon.getType() & 0xF) << 4 | localMapIcon.getRotation() & 0xF);
/* 70 */       paramPacketDataSerializer.writeByte(localMapIcon.getX());
/* 71 */       paramPacketDataSerializer.writeByte(localMapIcon.getY());
/*    */     }
/* 73 */     paramPacketDataSerializer.writeByte(this.f);
/* 74 */     if (this.f > 0) {
/* 75 */       paramPacketDataSerializer.writeByte(this.g);
/* 76 */       paramPacketDataSerializer.writeByte(this.d);
/* 77 */       paramPacketDataSerializer.writeByte(this.e);
/* 78 */       paramPacketDataSerializer.a(this.h);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 84 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */