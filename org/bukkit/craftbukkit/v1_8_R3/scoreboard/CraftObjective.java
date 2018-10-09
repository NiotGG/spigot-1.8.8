/*     */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.Scoreboard;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardObjective;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ 
/*     */ final class CraftObjective extends CraftScoreboardComponent implements org.bukkit.scoreboard.Objective
/*     */ {
/*     */   private final ScoreboardObjective objective;
/*     */   private final CraftCriteria criteria;
/*     */   
/*     */   CraftObjective(CraftScoreboard scoreboard, ScoreboardObjective objective)
/*     */   {
/*  17 */     super(scoreboard);
/*  18 */     this.objective = objective;
/*  19 */     this.criteria = CraftCriteria.getFromNMS(objective);
/*     */   }
/*     */   
/*     */   ScoreboardObjective getHandle() {
/*  23 */     return this.objective;
/*     */   }
/*     */   
/*     */   public String getName() throws IllegalStateException {
/*  27 */     checkState();
/*     */     
/*  29 */     return this.objective.getName();
/*     */   }
/*     */   
/*     */   public String getDisplayName() throws IllegalStateException {
/*  33 */     checkState();
/*     */     
/*  35 */     return this.objective.getDisplayName();
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException {
/*  39 */     Validate.notNull(displayName, "Display name cannot be null");
/*  40 */     Validate.isTrue(displayName.length() <= 32, "Display name '" + displayName + "' is longer than the limit of 32 characters");
/*  41 */     checkState();
/*     */     
/*  43 */     this.objective.setDisplayName(displayName);
/*     */   }
/*     */   
/*     */   public String getCriteria() throws IllegalStateException {
/*  47 */     checkState();
/*     */     
/*  49 */     return this.criteria.bukkitName;
/*     */   }
/*     */   
/*     */   public boolean isModifiable() throws IllegalStateException {
/*  53 */     checkState();
/*     */     
/*  55 */     return !this.criteria.criteria.isReadOnly();
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException {
/*  59 */     CraftScoreboard scoreboard = checkState();
/*  60 */     Scoreboard board = scoreboard.board;
/*  61 */     ScoreboardObjective objective = this.objective;
/*     */     
/*  63 */     for (int i = 0; i < 3; i++) {
/*  64 */       if (board.getObjectiveForSlot(i) == objective) {
/*  65 */         board.setDisplaySlot(i, null);
/*     */       }
/*     */     }
/*  68 */     if (slot != null) {
/*  69 */       int slotNumber = CraftScoreboardTranslations.fromBukkitSlot(slot);
/*  70 */       board.setDisplaySlot(slotNumber, getHandle());
/*     */     }
/*     */   }
/*     */   
/*     */   public DisplaySlot getDisplaySlot() throws IllegalStateException {
/*  75 */     CraftScoreboard scoreboard = checkState();
/*  76 */     Scoreboard board = scoreboard.board;
/*  77 */     ScoreboardObjective objective = this.objective;
/*     */     
/*  79 */     for (int i = 0; i < 3; i++) {
/*  80 */       if (board.getObjectiveForSlot(i) == objective) {
/*  81 */         return CraftScoreboardTranslations.toBukkitSlot(i);
/*     */       }
/*     */     }
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   public Score getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
/*  88 */     Validate.notNull(player, "Player cannot be null");
/*  89 */     checkState();
/*     */     
/*  91 */     return new CraftScore(this, player.getName());
/*     */   }
/*     */   
/*     */   public Score getScore(String entry) throws IllegalArgumentException, IllegalStateException {
/*  95 */     Validate.notNull(entry, "Entry cannot be null");
/*  96 */     if (entry.length() > 40) throw new IllegalArgumentException("Entry cannot be longer than 40 characters!");
/*  97 */     checkState();
/*     */     
/*  99 */     return new CraftScore(this, entry);
/*     */   }
/*     */   
/*     */   public void unregister() throws IllegalStateException
/*     */   {
/* 104 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 106 */     scoreboard.board.unregisterObjective(this.objective);
/*     */   }
/*     */   
/*     */   CraftScoreboard checkState() throws IllegalStateException
/*     */   {
/* 111 */     if (getScoreboard().board.getObjective(this.objective.getName()) == null) {
/* 112 */       throw new IllegalStateException("Unregistered scoreboard component");
/*     */     }
/*     */     
/* 115 */     return getScoreboard();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 120 */     int hash = 7;
/* 121 */     hash = 31 * hash + (this.objective != null ? this.objective.hashCode() : 0);
/* 122 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 127 */     if (obj == null) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (getClass() != obj.getClass()) {
/* 131 */       return false;
/*     */     }
/* 133 */     CraftObjective other = (CraftObjective)obj;
/* 134 */     return (this.objective == other.objective) || ((this.objective != null) && (this.objective.equals(other.objective)));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */