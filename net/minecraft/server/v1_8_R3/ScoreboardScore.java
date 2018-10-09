/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ScoreboardScore
/*    */ {
/*  9 */   public static final Comparator<ScoreboardScore> a = new Comparator()
/*    */   {
/*    */     public int a(ScoreboardScore paramAnonymousScoreboardScore1, ScoreboardScore paramAnonymousScoreboardScore2) {
/* 12 */       if (paramAnonymousScoreboardScore1.getScore() > paramAnonymousScoreboardScore2.getScore())
/* 13 */         return 1;
/* 14 */       if (paramAnonymousScoreboardScore1.getScore() < paramAnonymousScoreboardScore2.getScore()) {
/* 15 */         return -1;
/*    */       }
/* 17 */       return paramAnonymousScoreboardScore2.getPlayerName().compareToIgnoreCase(paramAnonymousScoreboardScore1.getPlayerName());
/*    */     }
/*    */   };
/*    */   
/*    */   private final Scoreboard b;
/*    */   private final ScoreboardObjective c;
/*    */   private final String playerName;
/*    */   private int score;
/*    */   private boolean f;
/*    */   private boolean g;
/*    */   
/*    */   public ScoreboardScore(Scoreboard paramScoreboard, ScoreboardObjective paramScoreboardObjective, String paramString)
/*    */   {
/* 30 */     this.b = paramScoreboard;
/* 31 */     this.c = paramScoreboardObjective;
/* 32 */     this.playerName = paramString;
/* 33 */     this.g = true;
/*    */   }
/*    */   
/*    */   public void addScore(int paramInt) {
/* 37 */     if (this.c.getCriteria().isReadOnly()) {
/* 38 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 40 */     setScore(getScore() + paramInt);
/*    */   }
/*    */   
/*    */   public void removeScore(int paramInt) {
/* 44 */     if (this.c.getCriteria().isReadOnly()) {
/* 45 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 47 */     setScore(getScore() - paramInt);
/*    */   }
/*    */   
/*    */   public void incrementScore() {
/* 51 */     if (this.c.getCriteria().isReadOnly()) {
/* 52 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 54 */     addScore(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getScore()
/*    */   {
/* 65 */     return this.score;
/*    */   }
/*    */   
/*    */   public void setScore(int paramInt) {
/* 69 */     int i = this.score;
/* 70 */     this.score = paramInt;
/* 71 */     if ((i != paramInt) || (this.g)) {
/* 72 */       this.g = false;
/* 73 */       f().handleScoreChanged(this);
/*    */     }
/*    */   }
/*    */   
/*    */   public ScoreboardObjective getObjective() {
/* 78 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getPlayerName() {
/* 82 */     return this.playerName;
/*    */   }
/*    */   
/*    */   public Scoreboard f() {
/* 86 */     return this.b;
/*    */   }
/*    */   
/*    */   public boolean g() {
/* 90 */     return this.f;
/*    */   }
/*    */   
/*    */   public void a(boolean paramBoolean) {
/* 94 */     this.f = paramBoolean;
/*    */   }
/*    */   
/*    */   public void updateForList(List<EntityHuman> paramList) {
/* 98 */     setScore(this.c.getCriteria().getScoreModifier(paramList));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */