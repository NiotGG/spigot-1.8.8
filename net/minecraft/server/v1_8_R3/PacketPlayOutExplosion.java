/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutExplosion
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   private double c;
/*    */   private float d;
/*    */   private List<BlockPosition> e;
/*    */   private float f;
/*    */   private float g;
/*    */   private float h;
/*    */   
/*    */   public PacketPlayOutExplosion() {}
/*    */   
/*    */   public PacketPlayOutExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, List<BlockPosition> paramList, Vec3D paramVec3D)
/*    */   {
/* 28 */     this.a = paramDouble1;
/* 29 */     this.b = paramDouble2;
/* 30 */     this.c = paramDouble3;
/* 31 */     this.d = paramFloat;
/* 32 */     this.e = Lists.newArrayList(paramList);
/*    */     
/* 34 */     if (paramVec3D != null) {
/* 35 */       this.f = ((float)paramVec3D.a);
/* 36 */       this.g = ((float)paramVec3D.b);
/* 37 */       this.h = ((float)paramVec3D.c);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 43 */     this.a = paramPacketDataSerializer.readFloat();
/* 44 */     this.b = paramPacketDataSerializer.readFloat();
/* 45 */     this.c = paramPacketDataSerializer.readFloat();
/* 46 */     this.d = paramPacketDataSerializer.readFloat();
/* 47 */     int i = paramPacketDataSerializer.readInt();
/*    */     
/* 49 */     this.e = Lists.newArrayListWithCapacity(i);
/*    */     
/* 51 */     int j = (int)this.a;
/* 52 */     int k = (int)this.b;
/* 53 */     int m = (int)this.c;
/* 54 */     for (int n = 0; n < i; n++) {
/* 55 */       int i1 = paramPacketDataSerializer.readByte() + j;
/* 56 */       int i2 = paramPacketDataSerializer.readByte() + k;
/* 57 */       int i3 = paramPacketDataSerializer.readByte() + m;
/* 58 */       this.e.add(new BlockPosition(i1, i2, i3));
/*    */     }
/*    */     
/* 61 */     this.f = paramPacketDataSerializer.readFloat();
/* 62 */     this.g = paramPacketDataSerializer.readFloat();
/* 63 */     this.h = paramPacketDataSerializer.readFloat();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 68 */     paramPacketDataSerializer.writeFloat((float)this.a);
/* 69 */     paramPacketDataSerializer.writeFloat((float)this.b);
/* 70 */     paramPacketDataSerializer.writeFloat((float)this.c);
/* 71 */     paramPacketDataSerializer.writeFloat(this.d);
/* 72 */     paramPacketDataSerializer.writeInt(this.e.size());
/*    */     
/* 74 */     int i = (int)this.a;
/* 75 */     int j = (int)this.b;
/* 76 */     int k = (int)this.c;
/* 77 */     for (BlockPosition localBlockPosition : this.e) {
/* 78 */       int m = localBlockPosition.getX() - i;
/* 79 */       int n = localBlockPosition.getY() - j;
/* 80 */       int i1 = localBlockPosition.getZ() - k;
/* 81 */       paramPacketDataSerializer.writeByte(m);
/* 82 */       paramPacketDataSerializer.writeByte(n);
/* 83 */       paramPacketDataSerializer.writeByte(i1);
/*    */     }
/*    */     
/* 86 */     paramPacketDataSerializer.writeFloat(this.f);
/* 87 */     paramPacketDataSerializer.writeFloat(this.g);
/* 88 */     paramPacketDataSerializer.writeFloat(this.h);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 93 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutExplosion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */