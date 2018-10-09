/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutUpdateTime
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private long a;
/*    */   private long b;
/*    */   
/*    */   public PacketPlayOutUpdateTime() {}
/*    */   
/*    */   public PacketPlayOutUpdateTime(long paramLong1, long paramLong2, boolean paramBoolean)
/*    */   {
/* 16 */     this.a = paramLong1;
/* 17 */     this.b = paramLong2;
/*    */     
/* 19 */     if (!paramBoolean) {
/* 20 */       this.b = (-this.b);
/* 21 */       if (this.b == 0L) {
/* 22 */         this.b = -1L;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.readLong();
/* 30 */     this.b = paramPacketDataSerializer.readLong();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     paramPacketDataSerializer.writeLong(this.a);
/* 36 */     paramPacketDataSerializer.writeLong(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 41 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutUpdateTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */