/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ScoreboardObjective
/*    */ {
/*    */   private final Scoreboard a;
/*    */   
/*    */   private final String b;
/*    */   
/*    */   private final IScoreboardCriteria c;
/*    */   private IScoreboardCriteria.EnumScoreboardHealthDisplay d;
/*    */   private String e;
/*    */   
/*    */   public ScoreboardObjective(Scoreboard paramScoreboard, String paramString, IScoreboardCriteria paramIScoreboardCriteria)
/*    */   {
/* 16 */     this.a = paramScoreboard;
/* 17 */     this.b = paramString;
/* 18 */     this.c = paramIScoreboardCriteria;
/* 19 */     this.e = paramString;
/* 20 */     this.d = paramIScoreboardCriteria.c();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 28 */     return this.b;
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria getCriteria() {
/* 32 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 36 */     return this.e;
/*    */   }
/*    */   
/*    */   public void setDisplayName(String paramString) {
/* 40 */     this.e = paramString;
/* 41 */     this.a.handleObjectiveChanged(this);
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria.EnumScoreboardHealthDisplay e() {
/* 45 */     return this.d;
/*    */   }
/*    */   
/*    */   public void a(IScoreboardCriteria.EnumScoreboardHealthDisplay paramEnumScoreboardHealthDisplay) {
/* 49 */     this.d = paramEnumScoreboardHealthDisplay;
/* 50 */     this.a.handleObjectiveChanged(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */