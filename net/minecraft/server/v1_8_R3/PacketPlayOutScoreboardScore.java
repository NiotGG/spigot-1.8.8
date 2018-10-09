/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutScoreboardScore
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/* 13 */   private String a = "";
/* 14 */   private String b = "";
/*    */   private int c;
/*    */   private EnumScoreboardAction d;
/*    */   
/*    */   public PacketPlayOutScoreboardScore() {}
/*    */   
/*    */   public PacketPlayOutScoreboardScore(ScoreboardScore paramScoreboardScore)
/*    */   {
/* 22 */     this.a = paramScoreboardScore.getPlayerName();
/* 23 */     this.b = paramScoreboardScore.getObjective().getName();
/* 24 */     this.c = paramScoreboardScore.getScore();
/* 25 */     this.d = EnumScoreboardAction.CHANGE;
/*    */   }
/*    */   
/*    */   public PacketPlayOutScoreboardScore(String paramString) {
/* 29 */     this.a = paramString;
/* 30 */     this.b = "";
/* 31 */     this.c = 0;
/* 32 */     this.d = EnumScoreboardAction.REMOVE;
/*    */   }
/*    */   
/*    */   public PacketPlayOutScoreboardScore(String paramString, ScoreboardObjective paramScoreboardObjective) {
/* 36 */     this.a = paramString;
/* 37 */     this.b = paramScoreboardObjective.getName();
/* 38 */     this.c = 0;
/* 39 */     this.d = EnumScoreboardAction.REMOVE;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 44 */     this.a = paramPacketDataSerializer.c(40);
/* 45 */     this.d = ((EnumScoreboardAction)paramPacketDataSerializer.a(EnumScoreboardAction.class));
/* 46 */     this.b = paramPacketDataSerializer.c(16);
/*    */     
/* 48 */     if (this.d != EnumScoreboardAction.REMOVE) {
/* 49 */       this.c = paramPacketDataSerializer.e();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 55 */     paramPacketDataSerializer.a(this.a);
/* 56 */     paramPacketDataSerializer.a(this.d);
/* 57 */     paramPacketDataSerializer.a(this.b);
/*    */     
/* 59 */     if (this.d != EnumScoreboardAction.REMOVE) {
/* 60 */       paramPacketDataSerializer.b(this.c);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 66 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public static enum EnumScoreboardAction
/*    */   {
/*    */     private EnumScoreboardAction() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutScoreboardScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */