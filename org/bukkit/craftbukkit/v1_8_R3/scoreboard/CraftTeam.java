/*     */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSet.Builder;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_8_R3.Scoreboard;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardTeam;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardTeamBase.EnumNameTagVisibility;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.scoreboard.NameTagVisibility;
/*     */ 
/*     */ final class CraftTeam extends CraftScoreboardComponent implements org.bukkit.scoreboard.Team
/*     */ {
/*     */   private final ScoreboardTeam team;
/*     */   
/*     */   CraftTeam(CraftScoreboard scoreboard, ScoreboardTeam team)
/*     */   {
/*  20 */     super(scoreboard);
/*  21 */     this.team = team;
/*     */   }
/*     */   
/*     */   public String getName() throws IllegalStateException {
/*  25 */     checkState();
/*     */     
/*  27 */     return this.team.getName();
/*     */   }
/*     */   
/*     */   public String getDisplayName() throws IllegalStateException {
/*  31 */     checkState();
/*     */     
/*  33 */     return this.team.getDisplayName();
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) throws IllegalStateException {
/*  37 */     Validate.notNull(displayName, "Display name cannot be null");
/*  38 */     Validate.isTrue(displayName.length() <= 32, "Display name '" + displayName + "' is longer than the limit of 32 characters");
/*  39 */     checkState();
/*     */     
/*  41 */     this.team.setDisplayName(displayName);
/*     */   }
/*     */   
/*     */   public String getPrefix() throws IllegalStateException {
/*  45 */     checkState();
/*     */     
/*  47 */     return this.team.getPrefix();
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) throws IllegalStateException, IllegalArgumentException {
/*  51 */     Validate.notNull(prefix, "Prefix cannot be null");
/*  52 */     Validate.isTrue(prefix.length() <= 32, "Prefix '" + prefix + "' is longer than the limit of 32 characters");
/*  53 */     checkState();
/*     */     
/*  55 */     this.team.setPrefix(prefix);
/*     */   }
/*     */   
/*     */   public String getSuffix() throws IllegalStateException {
/*  59 */     checkState();
/*     */     
/*  61 */     return this.team.getSuffix();
/*     */   }
/*     */   
/*     */   public void setSuffix(String suffix) throws IllegalStateException, IllegalArgumentException {
/*  65 */     Validate.notNull(suffix, "Suffix cannot be null");
/*  66 */     Validate.isTrue(suffix.length() <= 32, "Suffix '" + suffix + "' is longer than the limit of 32 characters");
/*  67 */     checkState();
/*     */     
/*  69 */     this.team.setSuffix(suffix);
/*     */   }
/*     */   
/*     */   public boolean allowFriendlyFire() throws IllegalStateException {
/*  73 */     checkState();
/*     */     
/*  75 */     return this.team.allowFriendlyFire();
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean enabled) throws IllegalStateException {
/*  79 */     checkState();
/*     */     
/*  81 */     this.team.setAllowFriendlyFire(enabled);
/*     */   }
/*     */   
/*     */   public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
/*  85 */     checkState();
/*     */     
/*  87 */     return this.team.canSeeFriendlyInvisibles();
/*     */   }
/*     */   
/*     */   public void setCanSeeFriendlyInvisibles(boolean enabled) throws IllegalStateException {
/*  91 */     checkState();
/*     */     
/*  93 */     this.team.setCanSeeFriendlyInvisibles(enabled);
/*     */   }
/*     */   
/*     */   public NameTagVisibility getNameTagVisibility() throws IllegalArgumentException {
/*  97 */     checkState();
/*     */     
/*  99 */     return notchToBukkit(this.team.getNameTagVisibility());
/*     */   }
/*     */   
/*     */   public void setNameTagVisibility(NameTagVisibility visibility) throws IllegalArgumentException {
/* 103 */     checkState();
/*     */     
/* 105 */     this.team.setNameTagVisibility(bukkitToNotch(visibility));
/*     */   }
/*     */   
/*     */   public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
/* 109 */     checkState();
/*     */     
/* 111 */     ImmutableSet.Builder<OfflinePlayer> players = ImmutableSet.builder();
/* 112 */     for (String playerName : this.team.getPlayerNameSet()) {
/* 113 */       players.add(org.bukkit.Bukkit.getOfflinePlayer(playerName));
/*     */     }
/* 115 */     return players.build();
/*     */   }
/*     */   
/*     */   public Set<String> getEntries() throws IllegalStateException
/*     */   {
/* 120 */     checkState();
/*     */     
/* 122 */     ImmutableSet.Builder<String> entries = ImmutableSet.builder();
/* 123 */     for (String playerName : this.team.getPlayerNameSet()) {
/* 124 */       entries.add(playerName);
/*     */     }
/* 126 */     return entries.build();
/*     */   }
/*     */   
/*     */   public int getSize() throws IllegalStateException {
/* 130 */     checkState();
/*     */     
/* 132 */     return this.team.getPlayerNameSet().size();
/*     */   }
/*     */   
/*     */   public void addPlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
/* 136 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/* 137 */     addEntry(player.getName());
/*     */   }
/*     */   
/*     */   public void addEntry(String entry) throws IllegalStateException, IllegalArgumentException {
/* 141 */     Validate.notNull(entry, "Entry cannot be null");
/* 142 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 144 */     scoreboard.board.addPlayerToTeam(entry, this.team.getName());
/*     */   }
/*     */   
/*     */   public boolean removePlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
/* 148 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/* 149 */     return removeEntry(player.getName());
/*     */   }
/*     */   
/*     */   public boolean removeEntry(String entry) throws IllegalStateException, IllegalArgumentException {
/* 153 */     Validate.notNull(entry, "Entry cannot be null");
/* 154 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 156 */     if (!this.team.getPlayerNameSet().contains(entry)) {
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     scoreboard.board.removePlayerFromTeam(entry, this.team);
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasPlayer(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
/* 165 */     Validate.notNull(player, "OfflinePlayer cannot be null");
/* 166 */     return hasEntry(player.getName());
/*     */   }
/*     */   
/*     */   public boolean hasEntry(String entry) throws IllegalArgumentException, IllegalStateException {
/* 170 */     Validate.notNull("Entry cannot be null");
/*     */     
/* 172 */     checkState();
/*     */     
/* 174 */     return this.team.getPlayerNameSet().contains(entry);
/*     */   }
/*     */   
/*     */   public void unregister() throws IllegalStateException
/*     */   {
/* 179 */     CraftScoreboard scoreboard = checkState();
/*     */     
/* 181 */     scoreboard.board.removeTeam(this.team);
/*     */   }
/*     */   
/*     */   public static ScoreboardTeamBase.EnumNameTagVisibility bukkitToNotch(NameTagVisibility visibility) {
/* 185 */     switch (visibility) {
/*     */     case ALWAYS: 
/* 187 */       return ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
/*     */     case HIDE_FOR_OTHER_TEAMS: 
/* 189 */       return ScoreboardTeamBase.EnumNameTagVisibility.NEVER;
/*     */     case HIDE_FOR_OWN_TEAM: 
/* 191 */       return ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OTHER_TEAMS;
/*     */     case NEVER: 
/* 193 */       return ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OWN_TEAM;
/*     */     }
/* 195 */     throw new IllegalArgumentException("Unknown visibility level " + visibility);
/*     */   }
/*     */   
/*     */   public static NameTagVisibility notchToBukkit(ScoreboardTeamBase.EnumNameTagVisibility visibility)
/*     */   {
/* 200 */     switch (visibility) {
/*     */     case ALWAYS: 
/* 202 */       return NameTagVisibility.ALWAYS;
/*     */     case HIDE_FOR_OTHER_TEAMS: 
/* 204 */       return NameTagVisibility.NEVER;
/*     */     case HIDE_FOR_OWN_TEAM: 
/* 206 */       return NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
/*     */     case NEVER: 
/* 208 */       return NameTagVisibility.HIDE_FOR_OWN_TEAM;
/*     */     }
/* 210 */     throw new IllegalArgumentException("Unknown visibility level " + visibility);
/*     */   }
/*     */   
/*     */   CraftScoreboard checkState()
/*     */     throws IllegalStateException
/*     */   {
/* 216 */     if (getScoreboard().board.getTeam(this.team.getName()) == null) {
/* 217 */       throw new IllegalStateException("Unregistered scoreboard component");
/*     */     }
/*     */     
/* 220 */     return getScoreboard();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 225 */     int hash = 7;
/* 226 */     hash = 43 * hash + (this.team != null ? this.team.hashCode() : 0);
/* 227 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 232 */     if (obj == null) {
/* 233 */       return false;
/*     */     }
/* 235 */     if (getClass() != obj.getClass()) {
/* 236 */       return false;
/*     */     }
/* 238 */     CraftTeam other = (CraftTeam)obj;
/* 239 */     return (this.team == other.team) || ((this.team != null) && (this.team.equals(other.team)));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */