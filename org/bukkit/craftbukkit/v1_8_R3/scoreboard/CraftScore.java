/*    */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_8_R3.Scoreboard;
/*    */ import net.minecraft.server.v1_8_R3.ScoreboardObjective;
/*    */ import net.minecraft.server.v1_8_R3.ScoreboardScore;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.scoreboard.Objective;
/*    */ import org.bukkit.scoreboard.Score;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CraftScore
/*    */   implements Score
/*    */ {
/*    */   private final String entry;
/*    */   private final CraftObjective objective;
/*    */   
/*    */   CraftScore(CraftObjective objective, String entry)
/*    */   {
/* 25 */     this.objective = objective;
/* 26 */     this.entry = entry;
/*    */   }
/*    */   
/*    */   public OfflinePlayer getPlayer() {
/* 30 */     return Bukkit.getOfflinePlayer(this.entry);
/*    */   }
/*    */   
/*    */   public String getEntry() {
/* 34 */     return this.entry;
/*    */   }
/*    */   
/*    */   public Objective getObjective() {
/* 38 */     return this.objective;
/*    */   }
/*    */   
/*    */   public int getScore() throws IllegalStateException {
/* 42 */     Scoreboard board = this.objective.checkState().board;
/*    */     
/* 44 */     if (board.getPlayers().contains(this.entry)) {
/* 45 */       Map<ScoreboardObjective, ScoreboardScore> scores = board.getPlayerObjectives(this.entry);
/* 46 */       ScoreboardScore score = (ScoreboardScore)scores.get(this.objective.getHandle());
/* 47 */       if (score != null) {
/* 48 */         return score.getScore();
/*    */       }
/*    */     }
/*    */     
/* 52 */     return 0;
/*    */   }
/*    */   
/*    */   public void setScore(int score) throws IllegalStateException {
/* 56 */     this.objective.checkState().board.getPlayerScoreForObjective(this.entry, this.objective.getHandle()).setScore(score);
/*    */   }
/*    */   
/*    */   public CraftScoreboard getScoreboard() {
/* 60 */     return this.objective.getScoreboard();
/*    */   }
/*    */   
/*    */   public boolean isScoreSet()
/*    */     throws IllegalStateException
/*    */   {
/* 66 */     Scoreboard board = this.objective.checkState().board;
/*    */     
/* 68 */     return (board.getPlayers().contains(this.entry)) && (board.getPlayerObjectives(this.entry).containsKey(this.objective.getHandle()));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */