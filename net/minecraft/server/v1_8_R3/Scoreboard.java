/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*  24 */   private final Map<String, ScoreboardObjective> objectivesByName = Maps.newHashMap();
/*  25 */   private final Map<IScoreboardCriteria, List<ScoreboardObjective>> objectivesByCriteria = Maps.newHashMap();
/*  26 */   private final Map<String, Map<ScoreboardObjective, ScoreboardScore>> playerScores = Maps.newHashMap();
/*  27 */   private final ScoreboardObjective[] displaySlots = new ScoreboardObjective[19];
/*  28 */   private final Map<String, ScoreboardTeam> teamsByName = Maps.newHashMap();
/*  29 */   private final Map<String, ScoreboardTeam> teamsByPlayer = Maps.newHashMap();
/*     */   
/*     */   public ScoreboardObjective getObjective(String paramString) {
/*  32 */     return (ScoreboardObjective)this.objectivesByName.get(paramString);
/*     */   }
/*     */   
/*     */   public ScoreboardObjective registerObjective(String paramString, IScoreboardCriteria paramIScoreboardCriteria) {
/*  36 */     if (paramString.length() > 16) {
/*  37 */       throw new IllegalArgumentException("The objective name '" + paramString + "' is too long!");
/*     */     }
/*  39 */     ScoreboardObjective localScoreboardObjective = getObjective(paramString);
/*  40 */     if (localScoreboardObjective != null) {
/*  41 */       throw new IllegalArgumentException("An objective with the name '" + paramString + "' already exists!");
/*     */     }
/*     */     
/*  44 */     localScoreboardObjective = new ScoreboardObjective(this, paramString, paramIScoreboardCriteria);
/*     */     
/*  46 */     Object localObject = (List)this.objectivesByCriteria.get(paramIScoreboardCriteria);
/*     */     
/*  48 */     if (localObject == null) {
/*  49 */       localObject = Lists.newArrayList();
/*  50 */       this.objectivesByCriteria.put(paramIScoreboardCriteria, localObject);
/*     */     }
/*     */     
/*  53 */     ((List)localObject).add(localScoreboardObjective);
/*  54 */     this.objectivesByName.put(paramString, localScoreboardObjective);
/*  55 */     handleObjectiveAdded(localScoreboardObjective);
/*     */     
/*  57 */     return localScoreboardObjective;
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardObjective> getObjectivesForCriteria(IScoreboardCriteria paramIScoreboardCriteria) {
/*  61 */     Collection localCollection = (Collection)this.objectivesByCriteria.get(paramIScoreboardCriteria);
/*     */     
/*     */ 
/*  64 */     return localCollection == null ? Lists.newArrayList() : Lists.newArrayList(localCollection);
/*     */   }
/*     */   
/*     */   public boolean b(String paramString, ScoreboardObjective paramScoreboardObjective) {
/*  68 */     Map localMap = (Map)this.playerScores.get(paramString);
/*  69 */     if (localMap == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     ScoreboardScore localScoreboardScore = (ScoreboardScore)localMap.get(paramScoreboardObjective);
/*  73 */     if (localScoreboardScore == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   public ScoreboardScore getPlayerScoreForObjective(String paramString, ScoreboardObjective paramScoreboardObjective) {
/*  80 */     if (paramString.length() > 40) {
/*  81 */       throw new IllegalArgumentException("The player name '" + paramString + "' is too long!");
/*     */     }
/*  83 */     Object localObject = (Map)this.playerScores.get(paramString);
/*     */     
/*  85 */     if (localObject == null) {
/*  86 */       localObject = Maps.newHashMap();
/*  87 */       this.playerScores.put(paramString, localObject);
/*     */     }
/*     */     
/*  90 */     ScoreboardScore localScoreboardScore = (ScoreboardScore)((Map)localObject).get(paramScoreboardObjective);
/*     */     
/*  92 */     if (localScoreboardScore == null) {
/*  93 */       localScoreboardScore = new ScoreboardScore(this, paramScoreboardObjective, paramString);
/*  94 */       ((Map)localObject).put(paramScoreboardObjective, localScoreboardScore);
/*     */     }
/*     */     
/*  97 */     return localScoreboardScore;
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardScore> getScoresForObjective(ScoreboardObjective paramScoreboardObjective) {
/* 101 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 103 */     for (Map localMap : this.playerScores.values()) {
/* 104 */       ScoreboardScore localScoreboardScore = (ScoreboardScore)localMap.get(paramScoreboardObjective);
/* 105 */       if (localScoreboardScore != null) {
/* 106 */         localArrayList.add(localScoreboardScore);
/*     */       }
/*     */     }
/*     */     
/* 110 */     Collections.sort(localArrayList, ScoreboardScore.a);
/*     */     
/* 112 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardObjective> getObjectives() {
/* 116 */     return this.objectivesByName.values();
/*     */   }
/*     */   
/*     */ 
/* 120 */   public Collection<String> getPlayers() { return this.playerScores.keySet(); }
/*     */   
/*     */   public void resetPlayerScores(String paramString, ScoreboardObjective paramScoreboardObjective) {
/*     */     Map localMap1;
/* 124 */     if (paramScoreboardObjective == null) {
/* 125 */       localMap1 = (Map)this.playerScores.remove(paramString);
/* 126 */       if (localMap1 != null) {
/* 127 */         handlePlayerRemoved(paramString);
/*     */       }
/*     */     } else {
/* 130 */       localMap1 = (Map)this.playerScores.get(paramString);
/* 131 */       if (localMap1 != null) {
/* 132 */         ScoreboardScore localScoreboardScore = (ScoreboardScore)localMap1.remove(paramScoreboardObjective);
/* 133 */         if (localMap1.size() < 1) {
/* 134 */           Map localMap2 = (Map)this.playerScores.remove(paramString);
/* 135 */           if (localMap2 != null) {
/* 136 */             handlePlayerRemoved(paramString);
/*     */           }
/* 138 */         } else if (localScoreboardScore != null) {
/* 139 */           a(paramString, paramScoreboardObjective);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardScore> getScores() {
/* 146 */     Collection localCollection = this.playerScores.values();
/* 147 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 149 */     for (Map localMap : localCollection) {
/* 150 */       localArrayList.addAll(localMap.values());
/*     */     }
/*     */     
/* 153 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<ScoreboardObjective, ScoreboardScore> getPlayerObjectives(String paramString)
/*     */   {
/* 171 */     Object localObject = (Map)this.playerScores.get(paramString);
/* 172 */     if (localObject == null) {
/* 173 */       localObject = Maps.newHashMap();
/*     */     }
/* 175 */     return (Map<ScoreboardObjective, ScoreboardScore>)localObject;
/*     */   }
/*     */   
/*     */   public void unregisterObjective(ScoreboardObjective paramScoreboardObjective) {
/* 179 */     this.objectivesByName.remove(paramScoreboardObjective.getName());
/*     */     
/* 181 */     for (int i = 0; i < 19; i++) {
/* 182 */       if (getObjectiveForSlot(i) == paramScoreboardObjective) {
/* 183 */         setDisplaySlot(i, null);
/*     */       }
/*     */     }
/*     */     
/* 187 */     List localList = (List)this.objectivesByCriteria.get(paramScoreboardObjective.getCriteria());
/* 188 */     if (localList != null) {
/* 189 */       localList.remove(paramScoreboardObjective);
/*     */     }
/*     */     
/* 192 */     for (Map localMap : this.playerScores.values()) {
/* 193 */       localMap.remove(paramScoreboardObjective);
/*     */     }
/*     */     
/* 196 */     handleObjectiveRemoved(paramScoreboardObjective);
/*     */   }
/*     */   
/*     */   public void setDisplaySlot(int paramInt, ScoreboardObjective paramScoreboardObjective) {
/* 200 */     this.displaySlots[paramInt] = paramScoreboardObjective;
/*     */   }
/*     */   
/*     */   public ScoreboardObjective getObjectiveForSlot(int paramInt)
/*     */   {
/* 205 */     return this.displaySlots[paramInt];
/*     */   }
/*     */   
/*     */   public ScoreboardTeam getTeam(String paramString) {
/* 209 */     return (ScoreboardTeam)this.teamsByName.get(paramString);
/*     */   }
/*     */   
/*     */   public ScoreboardTeam createTeam(String paramString) {
/* 213 */     if (paramString.length() > 16) {
/* 214 */       throw new IllegalArgumentException("The team name '" + paramString + "' is too long!");
/*     */     }
/* 216 */     ScoreboardTeam localScoreboardTeam = getTeam(paramString);
/* 217 */     if (localScoreboardTeam != null) {
/* 218 */       throw new IllegalArgumentException("A team with the name '" + paramString + "' already exists!");
/*     */     }
/*     */     
/* 221 */     localScoreboardTeam = new ScoreboardTeam(this, paramString);
/* 222 */     this.teamsByName.put(paramString, localScoreboardTeam);
/* 223 */     handleTeamAdded(localScoreboardTeam);
/*     */     
/* 225 */     return localScoreboardTeam;
/*     */   }
/*     */   
/*     */   public void removeTeam(ScoreboardTeam paramScoreboardTeam) {
/* 229 */     this.teamsByName.remove(paramScoreboardTeam.getName());
/*     */     
/*     */ 
/*     */ 
/* 233 */     for (String str : paramScoreboardTeam.getPlayerNameSet()) {
/* 234 */       this.teamsByPlayer.remove(str);
/*     */     }
/*     */     
/* 237 */     handleTeamRemoved(paramScoreboardTeam);
/*     */   }
/*     */   
/*     */   public boolean addPlayerToTeam(String paramString1, String paramString2) {
/* 241 */     if (paramString1.length() > 40) {
/* 242 */       throw new IllegalArgumentException("The player name '" + paramString1 + "' is too long!");
/*     */     }
/* 244 */     if (!this.teamsByName.containsKey(paramString2)) {
/* 245 */       return false;
/*     */     }
/* 247 */     ScoreboardTeam localScoreboardTeam = getTeam(paramString2);
/*     */     
/* 249 */     if (getPlayerTeam(paramString1) != null) {
/* 250 */       removePlayerFromTeam(paramString1);
/*     */     }
/*     */     
/* 253 */     this.teamsByPlayer.put(paramString1, localScoreboardTeam);
/* 254 */     localScoreboardTeam.getPlayerNameSet().add(paramString1);
/* 255 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removePlayerFromTeam(String paramString) {
/* 259 */     ScoreboardTeam localScoreboardTeam = getPlayerTeam(paramString);
/*     */     
/* 261 */     if (localScoreboardTeam != null) {
/* 262 */       removePlayerFromTeam(paramString, localScoreboardTeam);
/* 263 */       return true;
/*     */     }
/* 265 */     return false;
/*     */   }
/*     */   
/*     */   public void removePlayerFromTeam(String paramString, ScoreboardTeam paramScoreboardTeam)
/*     */   {
/* 270 */     if (getPlayerTeam(paramString) != paramScoreboardTeam) {
/* 271 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + paramScoreboardTeam.getName() + "'.");
/*     */     }
/*     */     
/* 274 */     this.teamsByPlayer.remove(paramString);
/* 275 */     paramScoreboardTeam.getPlayerNameSet().remove(paramString);
/*     */   }
/*     */   
/*     */   public Collection<String> getTeamNames() {
/* 279 */     return this.teamsByName.keySet();
/*     */   }
/*     */   
/*     */   public Collection<ScoreboardTeam> getTeams() {
/* 283 */     return this.teamsByName.values();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScoreboardTeam getPlayerTeam(String paramString)
/*     */   {
/* 291 */     return (ScoreboardTeam)this.teamsByPlayer.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void handleObjectiveAdded(ScoreboardObjective paramScoreboardObjective) {}
/*     */   
/*     */ 
/*     */   public void handleObjectiveChanged(ScoreboardObjective paramScoreboardObjective) {}
/*     */   
/*     */ 
/*     */   public void handleObjectiveRemoved(ScoreboardObjective paramScoreboardObjective) {}
/*     */   
/*     */ 
/*     */   public void handleScoreChanged(ScoreboardScore paramScoreboardScore) {}
/*     */   
/*     */ 
/*     */   public void handlePlayerRemoved(String paramString) {}
/*     */   
/*     */ 
/*     */   public void a(String paramString, ScoreboardObjective paramScoreboardObjective) {}
/*     */   
/*     */ 
/*     */   public void handleTeamAdded(ScoreboardTeam paramScoreboardTeam) {}
/*     */   
/*     */ 
/*     */   public void handleTeamChanged(ScoreboardTeam paramScoreboardTeam) {}
/*     */   
/*     */   public void handleTeamRemoved(ScoreboardTeam paramScoreboardTeam) {}
/*     */   
/*     */   public static String getSlotName(int paramInt)
/*     */   {
/* 322 */     switch (paramInt) {
/*     */     case 0: 
/* 324 */       return "list";
/*     */     case 1: 
/* 326 */       return "sidebar";
/*     */     case 2: 
/* 328 */       return "belowName";
/*     */     }
/* 330 */     if ((paramInt >= 3) && (paramInt <= 18)) {
/* 331 */       EnumChatFormat localEnumChatFormat = EnumChatFormat.a(paramInt - 3);
/* 332 */       if ((localEnumChatFormat != null) && (localEnumChatFormat != EnumChatFormat.RESET)) {
/* 333 */         return "sidebar.team." + localEnumChatFormat.e();
/*     */       }
/*     */     }
/* 336 */     return null;
/*     */   }
/*     */   
/*     */   public static int getSlotForName(String paramString)
/*     */   {
/* 341 */     if (paramString.equalsIgnoreCase("list"))
/* 342 */       return 0;
/* 343 */     if (paramString.equalsIgnoreCase("sidebar"))
/* 344 */       return 1;
/* 345 */     if (paramString.equalsIgnoreCase("belowName")) {
/* 346 */       return 2;
/*     */     }
/* 348 */     if (paramString.startsWith("sidebar.team.")) {
/* 349 */       String str = paramString.substring("sidebar.team.".length());
/* 350 */       EnumChatFormat localEnumChatFormat = EnumChatFormat.b(str);
/* 351 */       if ((localEnumChatFormat != null) && (localEnumChatFormat.b() >= 0)) {
/* 352 */         return localEnumChatFormat.b() + 3;
/*     */       }
/*     */     }
/* 355 */     return -1;
/*     */   }
/*     */   
/*     */ 
/* 359 */   private static String[] g = null;
/*     */   
/* 361 */   public static String[] h() { if (g == null) {
/* 362 */       g = new String[19];
/* 363 */       for (int i = 0; i < 19; i++) {
/* 364 */         g[i] = getSlotName(i);
/*     */       }
/*     */     }
/* 367 */     return g;
/*     */   }
/*     */   
/*     */   public void a(Entity paramEntity) {
/* 371 */     if ((paramEntity == null) || ((paramEntity instanceof EntityHuman)) || (paramEntity.isAlive())) {
/* 372 */       return;
/*     */     }
/* 374 */     String str = paramEntity.getUniqueID().toString();
/* 375 */     resetPlayerScores(str, null);
/* 376 */     removePlayerFromTeam(str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Scoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */