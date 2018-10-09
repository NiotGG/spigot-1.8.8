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
/*    */ public class PacketPlayOutBed
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private BlockPosition b;
/*    */   
/*    */   public PacketPlayOutBed() {}
/*    */   
/*    */   public PacketPlayOutBed(EntityHuman paramEntityHuman, BlockPosition paramBlockPosition)
/*    */   {
/* 21 */     this.a = paramEntityHuman.getId();
/* 22 */     this.b = paramBlockPosition;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     this.a = paramPacketDataSerializer.e();
/* 28 */     this.b = paramPacketDataSerializer.c();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.b(this.a);
/* 34 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 39 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */