/*     */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
/*     */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*     */ import net.minecraft.server.v1_8_R3.PlayerList;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardObjective;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardScore;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardServer;
/*     */ import net.minecraft.server.v1_8_R3.ScoreboardTeam;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.WeakCollection;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.ScoreboardManager;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ 
/*     */ public final class CraftScoreboardManager implements ScoreboardManager
/*     */ {
/*     */   private final CraftScoreboard mainScoreboard;
/*     */   private final MinecraftServer server;
/*  31 */   private final Collection<CraftScoreboard> scoreboards = new WeakCollection();
/*  32 */   private final Map<CraftPlayer, CraftScoreboard> playerBoards = new HashMap();
/*     */   
/*     */   public CraftScoreboardManager(MinecraftServer minecraftserver, net.minecraft.server.v1_8_R3.Scoreboard scoreboardServer) {
/*  35 */     this.mainScoreboard = new CraftScoreboard(scoreboardServer);
/*  36 */     this.server = minecraftserver;
/*  37 */     this.scoreboards.add(this.mainScoreboard);
/*     */   }
/*     */   
/*     */   public CraftScoreboard getMainScoreboard() {
/*  41 */     return this.mainScoreboard;
/*     */   }
/*     */   
/*     */   public CraftScoreboard getNewScoreboard() {
/*  45 */     AsyncCatcher.catchOp("scoreboard creation");
/*  46 */     CraftScoreboard scoreboard = new CraftScoreboard(new ScoreboardServer(this.server));
/*  47 */     this.scoreboards.add(scoreboard);
/*  48 */     return scoreboard;
/*     */   }
/*     */   
/*     */   public CraftScoreboard getPlayerBoard(CraftPlayer player)
/*     */   {
/*  53 */     CraftScoreboard board = (CraftScoreboard)this.playerBoards.get(player);
/*  54 */     return board == null ? getMainScoreboard() : board;
/*     */   }
/*     */   
/*     */   public void setPlayerBoard(CraftPlayer player, org.bukkit.scoreboard.Scoreboard bukkitScoreboard) throws IllegalArgumentException
/*     */   {
/*  59 */     Validate.isTrue(bukkitScoreboard instanceof CraftScoreboard, "Cannot set player scoreboard to an unregistered Scoreboard");
/*     */     
/*  61 */     CraftScoreboard scoreboard = (CraftScoreboard)bukkitScoreboard;
/*  62 */     net.minecraft.server.v1_8_R3.Scoreboard oldboard = getPlayerBoard(player).getHandle();
/*  63 */     net.minecraft.server.v1_8_R3.Scoreboard newboard = scoreboard.getHandle();
/*  64 */     EntityPlayer entityplayer = player.getHandle();
/*     */     
/*  66 */     if (oldboard == newboard) {
/*  67 */       return;
/*     */     }
/*     */     
/*  70 */     if (scoreboard == this.mainScoreboard) {
/*  71 */       this.playerBoards.remove(player);
/*     */     } else {
/*  73 */       this.playerBoards.put(player, scoreboard);
/*     */     }
/*     */     
/*     */ 
/*  77 */     HashSet<ScoreboardObjective> removed = new HashSet();
/*  78 */     for (int i = 0; i < 3; i++) {
/*  79 */       ScoreboardObjective scoreboardobjective = oldboard.getObjectiveForSlot(i);
/*  80 */       if ((scoreboardobjective != null) && (!removed.contains(scoreboardobjective))) {
/*  81 */         entityplayer.playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective(scoreboardobjective, 1));
/*  82 */         removed.add(scoreboardobjective);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  87 */     Iterator<?> iterator = oldboard.getTeams().iterator();
/*  88 */     while (iterator.hasNext()) {
/*  89 */       ScoreboardTeam scoreboardteam = (ScoreboardTeam)iterator.next();
/*  90 */       entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 1));
/*     */     }
/*     */     
/*     */ 
/*  94 */     this.server.getPlayerList().sendScoreboard((ScoreboardServer)newboard, player.getHandle());
/*     */   }
/*     */   
/*     */   public void removePlayer(Player player)
/*     */   {
/*  99 */     this.playerBoards.remove(player);
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardScore> getScoreboardScores(IScoreboardCriteria criteria, String name, Collection<ScoreboardScore> collection) {
/*     */     Iterator localIterator2;
/* 104 */     for (Iterator localIterator1 = this.scoreboards.iterator(); localIterator1.hasNext(); 
/*     */         
/* 106 */         localIterator2.hasNext())
/*     */     {
/* 104 */       CraftScoreboard scoreboard = (CraftScoreboard)localIterator1.next();
/* 105 */       net.minecraft.server.v1_8_R3.Scoreboard board = scoreboard.board;
/* 106 */       localIterator2 = board.getObjectivesForCriteria(criteria).iterator(); continue;ScoreboardObjective objective = (ScoreboardObjective)localIterator2.next();
/* 107 */       collection.add(board.getPlayerScoreForObjective(name, objective));
/*     */     }
/*     */     
/* 110 */     return collection;
/*     */   }
/*     */   
/*     */   public void updateAllScoresForList(IScoreboardCriteria criteria, String name, List<EntityPlayer> of)
/*     */   {
/* 115 */     for (ScoreboardScore score : getScoreboardScores(criteria, name, new ArrayList())) {
/* 116 */       score.updateForList(of);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftScoreboardManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */