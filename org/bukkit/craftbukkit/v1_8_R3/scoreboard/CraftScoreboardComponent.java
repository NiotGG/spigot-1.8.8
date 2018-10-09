/*    */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*    */ 
/*    */ abstract class CraftScoreboardComponent {
/*    */   private CraftScoreboard scoreboard;
/*    */   
/*    */   CraftScoreboardComponent(CraftScoreboard scoreboard) {
/*  7 */     this.scoreboard = scoreboard;
/*    */   }
/*    */   
/*    */   abstract CraftScoreboard checkState() throws IllegalStateException;
/*    */   
/*    */   public CraftScoreboard getScoreboard() {
/* 13 */     return this.scoreboard;
/*    */   }
/*    */   
/*    */   abstract void unregister()
/*    */     throws IllegalStateException;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftScoreboardComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */