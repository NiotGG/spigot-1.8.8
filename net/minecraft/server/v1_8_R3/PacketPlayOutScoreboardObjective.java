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
/*    */ public class PacketPlayOutScoreboardObjective
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private String a;
/*    */   private String b;
/*    */   private IScoreboardCriteria.EnumScoreboardHealthDisplay c;
/*    */   private int d;
/*    */   
/*    */   public PacketPlayOutScoreboardObjective() {}
/*    */   
/*    */   public PacketPlayOutScoreboardObjective(ScoreboardObjective paramScoreboardObjective, int paramInt)
/*    */   {
/* 24 */     this.a = paramScoreboardObjective.getName();
/* 25 */     this.b = paramScoreboardObjective.getDisplayName();
/* 26 */     this.c = paramScoreboardObjective.getCriteria().c();
/* 27 */     this.d = paramInt;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.c(16);
/* 33 */     this.d = paramPacketDataSerializer.readByte();
/*    */     
/* 35 */     if ((this.d == 0) || (this.d == 2)) {
/* 36 */       this.b = paramPacketDataSerializer.c(32);
/* 37 */       this.c = IScoreboardCriteria.EnumScoreboardHealthDisplay.a(paramPacketDataSerializer.c(16));
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 43 */     paramPacketDataSerializer.a(this.a);
/* 44 */     paramPacketDataSerializer.writeByte(this.d);
/*    */     
/* 46 */     if ((this.d == 0) || (this.d == 2)) {
/* 47 */       paramPacketDataSerializer.a(this.b);
/* 48 */       paramPacketDataSerializer.a(this.c.a());
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 54 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutScoreboardObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */