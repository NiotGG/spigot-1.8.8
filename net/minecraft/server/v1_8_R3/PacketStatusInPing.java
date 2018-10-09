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
/*    */ public class PacketStatusInPing
/*    */   implements Packet<PacketStatusInListener>
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 21 */     this.a = paramPacketDataSerializer.readLong();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     paramPacketDataSerializer.writeLong(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketStatusInListener paramPacketStatusInListener)
/*    */   {
/* 31 */     paramPacketStatusInListener.a(this);
/*    */   }
/*    */   
/*    */   public long a() {
/* 35 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketStatusInPing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */