/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutUpdateSign
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private World a;
/*    */   private BlockPosition b;
/*    */   private IChatBaseComponent[] c;
/*    */   
/*    */   public PacketPlayOutUpdateSign() {}
/*    */   
/*    */   public PacketPlayOutUpdateSign(World paramWorld, BlockPosition paramBlockPosition, IChatBaseComponent[] paramArrayOfIChatBaseComponent)
/*    */   {
/* 20 */     this.a = paramWorld;
/* 21 */     this.b = paramBlockPosition;
/* 22 */     this.c = new IChatBaseComponent[] { paramArrayOfIChatBaseComponent[0], paramArrayOfIChatBaseComponent[1], paramArrayOfIChatBaseComponent[2], paramArrayOfIChatBaseComponent[3] };
/*    */   }
/*    */   
/*    */ 
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 29 */     this.b = paramPacketDataSerializer.c();
/* 30 */     this.c = new IChatBaseComponent[4];
/* 31 */     for (int i = 0; i < 4; i++) {
/* 32 */       this.c[i] = paramPacketDataSerializer.d();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 38 */     paramPacketDataSerializer.a(this.b);
/* 39 */     for (int i = 0; i < 4; i++) {
/* 40 */       paramPacketDataSerializer.a(this.c[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 46 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutUpdateSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */