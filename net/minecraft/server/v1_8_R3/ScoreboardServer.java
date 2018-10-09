/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
/*     */ 
/*     */ public class ScoreboardServer extends Scoreboard
/*     */ {
/*     */   private final MinecraftServer a;
/*  14 */   private final Set<ScoreboardObjective> b = com.google.common.collect.Sets.newHashSet();
/*     */   private PersistentScoreboard c;
/*     */   
/*     */   public ScoreboardServer(MinecraftServer minecraftserver) {
/*  18 */     this.a = minecraftserver;
/*     */   }
/*     */   
/*     */   public void handleScoreChanged(ScoreboardScore scoreboardscore) {
/*  22 */     super.handleScoreChanged(scoreboardscore);
/*  23 */     if (this.b.contains(scoreboardscore.getObjective())) {
/*  24 */       sendAll(new PacketPlayOutScoreboardScore(scoreboardscore));
/*     */     }
/*     */     
/*  27 */     b();
/*     */   }
/*     */   
/*     */   public void handlePlayerRemoved(String s) {
/*  31 */     super.handlePlayerRemoved(s);
/*  32 */     sendAll(new PacketPlayOutScoreboardScore(s));
/*  33 */     b();
/*     */   }
/*     */   
/*     */   public void a(String s, ScoreboardObjective scoreboardobjective) {
/*  37 */     super.a(s, scoreboardobjective);
/*  38 */     sendAll(new PacketPlayOutScoreboardScore(s, scoreboardobjective));
/*  39 */     b();
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(int i, ScoreboardObjective scoreboardobjective) {
/*  43 */     ScoreboardObjective scoreboardobjective1 = getObjectiveForSlot(i);
/*     */     
/*  45 */     super.setDisplaySlot(i, scoreboardobjective);
/*  46 */     if ((scoreboardobjective1 != scoreboardobjective) && (scoreboardobjective1 != null)) {
/*  47 */       if (h(scoreboardobjective1) > 0) {
/*  48 */         sendAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       } else {
/*  50 */         g(scoreboardobjective1);
/*     */       }
/*     */     }
/*     */     
/*  54 */     if (scoreboardobjective != null) {
/*  55 */       if (this.b.contains(scoreboardobjective)) {
/*  56 */         sendAll(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       } else {
/*  58 */         e(scoreboardobjective);
/*     */       }
/*     */     }
/*     */     
/*  62 */     b();
/*     */   }
/*     */   
/*     */   public boolean addPlayerToTeam(String s, String s1) {
/*  66 */     if (super.addPlayerToTeam(s, s1)) {
/*  67 */       ScoreboardTeam scoreboardteam = getTeam(s1);
/*     */       
/*  69 */       sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s }), 3));
/*  70 */       b();
/*  71 */       return true;
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   public void removePlayerFromTeam(String s, ScoreboardTeam scoreboardteam)
/*     */   {
/*  78 */     super.removePlayerFromTeam(s, scoreboardteam);
/*  79 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s }), 4));
/*  80 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveAdded(ScoreboardObjective scoreboardobjective) {
/*  84 */     super.handleObjectiveAdded(scoreboardobjective);
/*  85 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveChanged(ScoreboardObjective scoreboardobjective) {
/*  89 */     super.handleObjectiveChanged(scoreboardobjective);
/*  90 */     if (this.b.contains(scoreboardobjective)) {
/*  91 */       sendAll(new PacketPlayOutScoreboardObjective(scoreboardobjective, 2));
/*     */     }
/*     */     
/*  94 */     b();
/*     */   }
/*     */   
/*     */   public void handleObjectiveRemoved(ScoreboardObjective scoreboardobjective) {
/*  98 */     super.handleObjectiveRemoved(scoreboardobjective);
/*  99 */     if (this.b.contains(scoreboardobjective)) {
/* 100 */       g(scoreboardobjective);
/*     */     }
/*     */     
/* 103 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamAdded(ScoreboardTeam scoreboardteam) {
/* 107 */     super.handleTeamAdded(scoreboardteam);
/* 108 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
/* 109 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamChanged(ScoreboardTeam scoreboardteam) {
/* 113 */     super.handleTeamChanged(scoreboardteam);
/* 114 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 2));
/* 115 */     b();
/*     */   }
/*     */   
/*     */   public void handleTeamRemoved(ScoreboardTeam scoreboardteam) {
/* 119 */     super.handleTeamRemoved(scoreboardteam);
/* 120 */     sendAll(new PacketPlayOutScoreboardTeam(scoreboardteam, 1));
/* 121 */     b();
/*     */   }
/*     */   
/*     */   public void a(PersistentScoreboard persistentscoreboard) {
/* 125 */     this.c = persistentscoreboard;
/*     */   }
/*     */   
/*     */   protected void b() {
/* 129 */     if (this.c != null) {
/* 130 */       this.c.c();
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Packet> getScoreboardScorePacketsForObjective(ScoreboardObjective scoreboardobjective)
/*     */   {
/* 136 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/* 138 */     arraylist.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 0));
/*     */     
/* 140 */     for (int i = 0; i < 19; i++) {
/* 141 */       if (getObjectiveForSlot(i) == scoreboardobjective) {
/* 142 */         arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       }
/*     */     }
/*     */     
/* 146 */     Iterator iterator = getScoresForObjective(scoreboardobjective).iterator();
/*     */     
/* 148 */     while (iterator.hasNext()) {
/* 149 */       ScoreboardScore scoreboardscore = (ScoreboardScore)iterator.next();
/*     */       
/* 151 */       arraylist.add(new PacketPlayOutScoreboardScore(scoreboardscore));
/*     */     }
/*     */     
/* 154 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void e(ScoreboardObjective scoreboardobjective) {
/* 158 */     List list = getScoreboardScorePacketsForObjective(scoreboardobjective);
/* 159 */     Iterator iterator = this.a.getPlayerList().v().iterator();
/*     */     
/* 161 */     while (iterator.hasNext()) {
/* 162 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/* 163 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
/* 164 */         Iterator iterator1 = list.iterator();
/*     */         
/* 166 */         while (iterator1.hasNext()) {
/* 167 */           Packet packet = (Packet)iterator1.next();
/*     */           
/* 169 */           entityplayer.playerConnection.sendPacket(packet);
/*     */         }
/*     */       }
/*     */     }
/* 173 */     this.b.add(scoreboardobjective);
/*     */   }
/*     */   
/*     */   public List<Packet> f(ScoreboardObjective scoreboardobjective) {
/* 177 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/* 179 */     arraylist.add(new PacketPlayOutScoreboardObjective(scoreboardobjective, 1));
/*     */     
/* 181 */     for (int i = 0; i < 19; i++) {
/* 182 */       if (getObjectiveForSlot(i) == scoreboardobjective) {
/* 183 */         arraylist.add(new PacketPlayOutScoreboardDisplayObjective(i, scoreboardobjective));
/*     */       }
/*     */     }
/*     */     
/* 187 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void g(ScoreboardObjective scoreboardobjective) {
/* 191 */     List list = f(scoreboardobjective);
/* 192 */     Iterator iterator = this.a.getPlayerList().v().iterator();
/*     */     
/* 194 */     while (iterator.hasNext()) {
/* 195 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/* 196 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
/* 197 */         Iterator iterator1 = list.iterator();
/*     */         
/* 199 */         while (iterator1.hasNext()) {
/* 200 */           Packet packet = (Packet)iterator1.next();
/*     */           
/* 202 */           entityplayer.playerConnection.sendPacket(packet);
/*     */         }
/*     */       }
/*     */     }
/* 206 */     this.b.remove(scoreboardobjective);
/*     */   }
/*     */   
/*     */   public int h(ScoreboardObjective scoreboardobjective) {
/* 210 */     int i = 0;
/*     */     
/* 212 */     for (int j = 0; j < 19; j++) {
/* 213 */       if (getObjectiveForSlot(j) == scoreboardobjective) {
/* 214 */         i++;
/*     */       }
/*     */     }
/*     */     
/* 218 */     return i;
/*     */   }
/*     */   
/*     */   private void sendAll(Packet packet)
/*     */   {
/* 223 */     for (EntityPlayer entityplayer : this.a.getPlayerList().players) {
/* 224 */       if (entityplayer.getBukkitEntity().getScoreboard().getHandle() == this) {
/* 225 */         entityplayer.playerConnection.sendPacket(packet);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */