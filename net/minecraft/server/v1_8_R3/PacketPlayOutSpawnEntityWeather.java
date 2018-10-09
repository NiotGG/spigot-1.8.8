/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutSpawnEntityWeather
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutSpawnEntityWeather() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityWeather(Entity paramEntity)
/*    */   {
/* 26 */     this.a = paramEntity.getId();
/* 27 */     this.b = MathHelper.floor(paramEntity.locX * 32.0D);
/* 28 */     this.c = MathHelper.floor(paramEntity.locY * 32.0D);
/* 29 */     this.d = MathHelper.floor(paramEntity.locZ * 32.0D);
/* 30 */     if ((paramEntity instanceof EntityLightning)) {
/* 31 */       this.e = 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 37 */     this.a = paramPacketDataSerializer.e();
/* 38 */     this.e = paramPacketDataSerializer.readByte();
/* 39 */     this.b = paramPacketDataSerializer.readInt();
/* 40 */     this.c = paramPacketDataSerializer.readInt();
/* 41 */     this.d = paramPacketDataSerializer.readInt();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 46 */     paramPacketDataSerializer.b(this.a);
/* 47 */     paramPacketDataSerializer.writeByte(this.e);
/* 48 */     paramPacketDataSerializer.writeInt(this.b);
/* 49 */     paramPacketDataSerializer.writeInt(this.c);
/* 50 */     paramPacketDataSerializer.writeInt(this.d);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 55 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnEntityWeather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */