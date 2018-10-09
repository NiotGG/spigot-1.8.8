package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;

public abstract interface Score
{
  @Deprecated
  public abstract OfflinePlayer getPlayer();
  
  public abstract String getEntry();
  
  public abstract Objective getObjective();
  
  public abstract int getScore()
    throws IllegalStateException;
  
  public abstract void setScore(int paramInt)
    throws IllegalStateException;
  
  public abstract boolean isScoreSet()
    throws IllegalStateException;
  
  public abstract Scoreboard getScoreboard();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scoreboard\Score.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */