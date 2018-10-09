/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityVelocity
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public PacketPlayOutEntityVelocity() {}
/*    */   
/*    */   public PacketPlayOutEntityVelocity(Entity paramEntity)
/*    */   {
/* 19 */     this(paramEntity.getId(), paramEntity.motX, paramEntity.motY, paramEntity.motZ);
/*    */   }
/*    */   
/*    */   public PacketPlayOutEntityVelocity(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 23 */     this.a = paramInt;
/* 24 */     double d1 = 3.9D;
/* 25 */     if (paramDouble1 < -d1) {
/* 26 */       paramDouble1 = -d1;
/*    */     }
/* 28 */     if (paramDouble2 < -d1) {
/* 29 */       paramDouble2 = -d1;
/*    */     }
/* 31 */     if (paramDouble3 < -d1) {
/* 32 */       paramDouble3 = -d1;
/*    */     }
/* 34 */     if (paramDouble1 > d1) {
/* 35 */       paramDouble1 = d1;
/*    */     }
/* 37 */     if (paramDouble2 > d1) {
/* 38 */       paramDouble2 = d1;
/*    */     }
/* 40 */     if (paramDouble3 > d1) {
/* 41 */       paramDouble3 = d1;
/*    */     }
/* 43 */     this.b = ((int)(paramDouble1 * 8000.0D));
/* 44 */     this.c = ((int)(paramDouble2 * 8000.0D));
/* 45 */     this.d = ((int)(paramDouble3 * 8000.0D));
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 50 */     this.a = paramPacketDataSerializer.e();
/* 51 */     this.b = paramPacketDataSerializer.readShort();
/* 52 */     this.c = paramPacketDataSerializer.readShort();
/* 53 */     this.d = paramPacketDataSerializer.readShort();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 58 */     paramPacketDataSerializer.b(this.a);
/* 59 */     paramPacketDataSerializer.writeShort(this.b);
/* 60 */     paramPacketDataSerializer.writeShort(this.c);
/* 61 */     paramPacketDataSerializer.writeShort(this.d);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 66 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityVelocity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */