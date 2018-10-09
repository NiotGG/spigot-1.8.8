/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutRemoveEntityEffect
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutRemoveEntityEffect() {}
/*    */   
/*    */   public PacketPlayOutRemoveEntityEffect(int paramInt, MobEffect paramMobEffect)
/*    */   {
/* 18 */     this.a = paramInt;
/* 19 */     this.b = paramMobEffect.getEffectId();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     this.a = paramPacketDataSerializer.e();
/* 25 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     paramPacketDataSerializer.b(this.a);
/* 31 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 36 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutRemoveEntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */