/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityTeleport
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private byte e;
/*    */   private byte f;
/*    */   private boolean g;
/*    */   
/*    */   public PacketPlayOutEntityTeleport() {}
/*    */   
/*    */   public PacketPlayOutEntityTeleport(Entity paramEntity)
/*    */   {
/* 23 */     this.a = paramEntity.getId();
/* 24 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 25 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 26 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 27 */     this.e = ((byte)(int)(paramEntity.yaw * 256.0F / 360.0F));
/* 28 */     this.f = ((byte)(int)(paramEntity.pitch * 256.0F / 360.0F));
/* 29 */     this.g = paramEntity.onGround;
/*    */   }
/*    */   
/*    */   public PacketPlayOutEntityTeleport(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte paramByte1, byte paramByte2, boolean paramBoolean) {
/* 33 */     this.a = paramInt1;
/* 34 */     this.b = paramInt2;
/* 35 */     this.c = paramInt3;
/* 36 */     this.d = paramInt4;
/* 37 */     this.e = paramByte1;
/* 38 */     this.f = paramByte2;
/* 39 */     this.g = paramBoolean;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 44 */     this.a = paramPacketDataSerializer.e();
/* 45 */     this.b = paramPacketDataSerializer.readInt();
/* 46 */     this.c = paramPacketDataSerializer.readInt();
/* 47 */     this.d = paramPacketDataSerializer.readInt();
/* 48 */     this.e = paramPacketDataSerializer.readByte();
/* 49 */     this.f = paramPacketDataSerializer.readByte();
/* 50 */     this.g = paramPacketDataSerializer.readBoolean();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 55 */     paramPacketDataSerializer.b(this.a);
/* 56 */     paramPacketDataSerializer.writeInt(this.b);
/* 57 */     paramPacketDataSerializer.writeInt(this.c);
/* 58 */     paramPacketDataSerializer.writeInt(this.d);
/* 59 */     paramPacketDataSerializer.writeByte(this.e);
/* 60 */     paramPacketDataSerializer.writeByte(this.f);
/* 61 */     paramPacketDataSerializer.writeBoolean(this.g);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 66 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityTeleport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */