/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutScoreboardDisplayObjective
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private String b;
/*    */   
/*    */   public PacketPlayOutScoreboardDisplayObjective() {}
/*    */   
/*    */   public PacketPlayOutScoreboardDisplayObjective(int paramInt, ScoreboardObjective paramScoreboardObjective)
/*    */   {
/* 17 */     this.a = paramInt;
/*    */     
/* 19 */     if (paramScoreboardObjective == null) {
/* 20 */       this.b = "";
/*    */     } else {
/* 22 */       this.b = paramScoreboardObjective.getName();
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     this.a = paramPacketDataSerializer.readByte();
/* 29 */     this.b = paramPacketDataSerializer.c(16);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 34 */     paramPacketDataSerializer.writeByte(this.a);
/* 35 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 40 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutScoreboardDisplayObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */