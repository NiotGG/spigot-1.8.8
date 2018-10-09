/*     */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSet.Builder;
/*     */ import com.google.common.collect.Iterables;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardObjective;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardTeam;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public final class CraftScoreboard
/*     */   implements org.bukkit.scoreboard.Scoreboard
/*     */ {
/*     */   final net.minecraft.server.v1_8_R3.Scoreboard board;
/*     */   
/*     */   CraftScoreboard(net.minecraft.server.v1_8_R3.Scoreboard board)
/*     */   {
/*  24 */     this.board = board;
/*     */   }
/*     */   
/*     */   public CraftObjective registerNewObjective(String name, String criteria) throws IllegalArgumentException {
/*  28 */     Validate.notNull(name, "Objective name cannot be null");
/*  29 */     Validate.notNull(criteria, "Criteria cannot be null");
/*  30 */     Validate.isTrue(name.length() <= 16, "The name '" + name + "' is longer than the limit of 16 characters");
/*  31 */     Validate.isTrue(this.board.getObjective(name) == null, "An objective of name '" + name + "' already exists");
/*     */     
/*  33 */     CraftCriteria craftCriteria = CraftCriteria.getFromBukkit(criteria);
/*  34 */     ScoreboardObjective objective = this.board.registerObjective(name, craftCriteria.criteria);
/*  35 */     return new CraftObjective(this, objective);
/*     */   }
/*     */   
/*     */   public Objective getObjective(String name) throws IllegalArgumentException {
/*  39 */     Validate.notNull(name, "Name cannot be null");
/*  40 */     ScoreboardObjective nms = this.board.getObjective(name);
/*  41 */     return nms == null ? null : new CraftObjective(this, nms);
/*     */   }
/*     */   
/*     */   public ImmutableSet<Objective> getObjectivesByCriteria(String criteria) throws IllegalArgumentException {
/*  45 */     Validate.notNull(criteria, "Criteria cannot be null");
/*     */     
/*  47 */     ImmutableSet.Builder<Objective> objectives = ImmutableSet.builder();
/*  48 */     for (ScoreboardObjective netObjective : this.board.getObjectives()) {
/*  49 */       CraftObjective objective = new CraftObjective(this, netObjective);
/*  50 */       if (objective.getCriteria().equals(criteria)) {
/*  51 */         objectives.add(objective);
/*     */       }
/*     */     }
/*  54 */     return objectives.build();
/*     */   }
/*     */   
/*     */   public ImmutableSet<Objective> getObjectives() {
/*  58 */     ImmutableSet.copyOf(Iterables.transform(this.board.getObjectives(), new Function()
/*     */     {
/*     */       public Objective apply(ScoreboardObjective input)
/*     */       {
/*  62 */         return new CraftObjective(CraftScoreboard.this, input);
/*     */       }
/*     */     }));
/*     */   }
/*     */   
/*     */   public Objective getObjective(DisplaySlot slot) throws IllegalArgumentException {
/*  68 */     Validate.notNull(slot, "Display slot cannot be null");
/*  69 */     ScoreboardObjective objective = this.board.getObjectiveForSlot(CraftScoreboardTranslations.fromBukkitSlot(slot));
/*  70 */     if (objective == null) {
/*  71 */       return null;
/*     */     }
/*  73 */     return new CraftObjective(this, objective);
/*     */   }
/*     */   
/*     */   public ImmutableSet<Score> getScores(OfflinePlayer player) throws IllegalArgumentException {
/*  77 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/*  79 */     return getScores(player.getName());
/*     */   }
/*     */   
/*     */   public ImmutableSet<Score> getScores(String entry) throws IllegalArgumentException {
/*  83 */     Validate.notNull(entry, "Entry cannot be null");
/*     */     
/*  85 */     ImmutableSet.Builder<Score> scores = ImmutableSet.builder();
/*  86 */     for (ScoreboardObjective objective : this.board.getObjectives()) {
/*  87 */       scores.add(new CraftScore(new CraftObjective(this, objective), entry));
/*     */     }
/*  89 */     return scores.build();
/*     */   }
/*     */   
/*     */   public void resetScores(OfflinePlayer player) throws IllegalArgumentException {
/*  93 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/*  95 */     resetScores(player.getName());
/*     */   }
/*     */   
/*     */   public void resetScores(String entry) throws IllegalArgumentException {
/*  99 */     Validate.notNull(entry, "Entry cannot be null");
/*     */     
/* 101 */     for (ScoreboardObjective objective : this.board.getObjectives()) {
/* 102 */       this.board.resetPlayerScores(entry, objective);
/*     */     }
/*     */   }
/*     */   
/*     */   public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException {
/* 107 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/*     */     
/* 109 */     ScoreboardTeam team = this.board.getPlayerTeam(player.getName());
/* 110 */     return team == null ? null : new CraftTeam(this, team);
/*     */   }
/*     */   
/*     */   public Team getEntryTeam(String entry) throws IllegalArgumentException {
/* 114 */     Validate.notNull(entry, "Entry cannot be null");
/*     */     
/* 116 */     ScoreboardTeam team = this.board.getPlayerTeam(entry);
/* 117 */     return team == null ? null : new CraftTeam(this, team);
/*     */   }
/*     */   
/*     */   public Team getTeam(String teamName) throws IllegalArgumentException {
/* 121 */     Validate.notNull(teamName, "Team name cannot be null");
/*     */     
/* 123 */     ScoreboardTeam team = this.board.getTeam(teamName);
/* 124 */     return team == null ? null : new CraftTeam(this, team);
/*     */   }
/*     */   
/*     */   public ImmutableSet<Team> getTeams() {
/* 128 */     ImmutableSet.copyOf(Iterables.transform(this.board.getTeams(), new Function()
/*     */     {
/*     */       public Team apply(ScoreboardTeam input)
/*     */       {
/* 132 */         return new CraftTeam(CraftScoreboard.this, input);
/*     */       }
/*     */     }));
/*     */   }
/*     */   
/*     */   public Team registerNewTeam(String name) throws IllegalArgumentException {
/* 138 */     Validate.notNull(name, "Team name cannot be null");
/* 139 */     Validate.isTrue(name.length() <= 16, "Team name '" + name + "' is longer than the limit of 16 characters");
/* 140 */     Validate.isTrue(this.board.getTeam(name) == null, "Team name '" + name + "' is already in use");
/*     */     
/* 142 */     return new CraftTeam(this, this.board.createTeam(name));
/*     */   }
/*     */   
/*     */   public ImmutableSet<OfflinePlayer> getPlayers() {
/* 146 */     ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
/* 147 */     for (Object playerName : this.board.getPlayers()) {
/* 148 */       players.add(Bukkit.getOfflinePlayer(playerName.toString()));
/*     */     }
/* 150 */     return players.build();
/*     */   }
/*     */   
/*     */   public ImmutableSet<String> getEntries() {
/* 154 */     ImmutableSet.Builder<String> entries = ImmutableSet.builder();
/* 155 */     for (Object entry : this.board.getPlayers()) {
/* 156 */       entries.add(entry.toString());
/*     */     }
/* 158 */     return entries.build();
/*     */   }
/*     */   
/*     */   public void clearSlot(DisplaySlot slot) throws IllegalArgumentException {
/* 162 */     Validate.notNull(slot, "Slot cannot be null");
/* 163 */     this.board.setDisplaySlot(CraftScoreboardTranslations.fromBukkitSlot(slot), null);
/*     */   }
/*     */   
/*     */   public net.minecraft.server.v1_8_R3.Scoreboard getHandle()
/*     */   {
/* 168 */     return this.board;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */