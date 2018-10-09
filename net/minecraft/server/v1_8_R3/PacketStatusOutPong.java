/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketStatusOutPong
/*    */   implements Packet<PacketStatusOutListener>
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public PacketStatusOutPong() {}
/*    */   
/*    */   public PacketStatusOutPong(long paramLong)
/*    */   {
/* 16 */     this.a = paramLong;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 21 */     this.a = paramPacketDataSerializer.readLong();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     paramPacketDataSerializer.writeLong(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketStatusOutListener paramPacketStatusOutListener)
/*    */   {
/* 31 */     paramPacketStatusOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketStatusOutPong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */