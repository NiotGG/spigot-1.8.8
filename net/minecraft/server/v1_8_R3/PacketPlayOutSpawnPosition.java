/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutSpawnPosition
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   public BlockPosition position;
/*    */   
/*    */   public PacketPlayOutSpawnPosition() {}
/*    */   
/*    */   public PacketPlayOutSpawnPosition(BlockPosition paramBlockPosition)
/*    */   {
/* 17 */     this.position = paramBlockPosition;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 22 */     this.position = paramPacketDataSerializer.c();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     paramPacketDataSerializer.a(this.position);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 32 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */