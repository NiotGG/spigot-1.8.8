/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutEntityEffect
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private byte b;
/*    */   private byte c;
/*    */   private int d;
/*    */   private byte e;
/*    */   
/*    */   public PacketPlayOutEntityEffect() {}
/*    */   
/*    */   public PacketPlayOutEntityEffect(int paramInt, MobEffect paramMobEffect)
/*    */   {
/* 23 */     this.a = paramInt;
/* 24 */     this.b = ((byte)(paramMobEffect.getEffectId() & 0xFF));
/* 25 */     this.c = ((byte)(paramMobEffect.getAmplifier() & 0xFF));
/* 26 */     if (paramMobEffect.getDuration() > 32767) {
/* 27 */       this.d = 32767;
/*    */     }
/*    */     else {
/* 30 */       this.d = paramMobEffect.getDuration();
/*    */     }
/* 32 */     this.e = ((byte)(paramMobEffect.isShowParticles() ? 1 : 0));
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 37 */     this.a = paramPacketDataSerializer.e();
/* 38 */     this.b = paramPacketDataSerializer.readByte();
/* 39 */     this.c = paramPacketDataSerializer.readByte();
/* 40 */     this.d = paramPacketDataSerializer.e();
/* 41 */     this.e = paramPacketDataSerializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 46 */     paramPacketDataSerializer.b(this.a);
/* 47 */     paramPacketDataSerializer.writeByte(this.b);
/* 48 */     paramPacketDataSerializer.writeByte(this.c);
/* 49 */     paramPacketDataSerializer.b(this.d);
/* 50 */     paramPacketDataSerializer.writeByte(this.e);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 59 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutEntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */