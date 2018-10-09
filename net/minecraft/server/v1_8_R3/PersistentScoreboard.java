/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersistentScoreboard
/*     */   extends PersistentBase
/*     */ {
/*  16 */   private static final Logger b = ;
/*     */   
/*     */   private Scoreboard c;
/*     */   private NBTTagCompound d;
/*     */   
/*     */   public PersistentScoreboard()
/*     */   {
/*  23 */     this("scoreboard");
/*     */   }
/*     */   
/*     */   public PersistentScoreboard(String paramString) {
/*  27 */     super(paramString);
/*     */   }
/*     */   
/*     */   public void a(Scoreboard paramScoreboard) {
/*  31 */     this.c = paramScoreboard;
/*     */     
/*  33 */     if (this.d != null) {
/*  34 */       a(this.d);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  40 */     if (this.c == null) {
/*  41 */       this.d = paramNBTTagCompound;
/*  42 */       return;
/*     */     }
/*     */     
/*  45 */     b(paramNBTTagCompound.getList("Objectives", 10));
/*  46 */     c(paramNBTTagCompound.getList("PlayerScores", 10));
/*     */     
/*  48 */     if (paramNBTTagCompound.hasKeyOfType("DisplaySlots", 10)) {
/*  49 */       c(paramNBTTagCompound.getCompound("DisplaySlots"));
/*     */     }
/*     */     
/*  52 */     if (paramNBTTagCompound.hasKeyOfType("Teams", 9)) {
/*  53 */       a(paramNBTTagCompound.getList("Teams", 10));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(NBTTagList paramNBTTagList) {
/*  58 */     for (int i = 0; i < paramNBTTagList.size(); i++) {
/*  59 */       NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
/*     */       
/*  61 */       String str1 = localNBTTagCompound.getString("Name");
/*  62 */       if (str1.length() > 16)
/*     */       {
/*  64 */         str1 = str1.substring(0, 16);
/*     */       }
/*  66 */       ScoreboardTeam localScoreboardTeam = this.c.createTeam(str1);
/*  67 */       String str2 = localNBTTagCompound.getString("DisplayName");
/*  68 */       if (str2.length() > 32)
/*     */       {
/*  70 */         str2 = str2.substring(0, 32);
/*     */       }
/*  72 */       localScoreboardTeam.setDisplayName(str2);
/*  73 */       if (localNBTTagCompound.hasKeyOfType("TeamColor", 8)) {
/*  74 */         localScoreboardTeam.a(EnumChatFormat.b(localNBTTagCompound.getString("TeamColor")));
/*     */       }
/*  76 */       localScoreboardTeam.setPrefix(localNBTTagCompound.getString("Prefix"));
/*  77 */       localScoreboardTeam.setSuffix(localNBTTagCompound.getString("Suffix"));
/*  78 */       if (localNBTTagCompound.hasKeyOfType("AllowFriendlyFire", 99)) {
/*  79 */         localScoreboardTeam.setAllowFriendlyFire(localNBTTagCompound.getBoolean("AllowFriendlyFire"));
/*     */       }
/*  81 */       if (localNBTTagCompound.hasKeyOfType("SeeFriendlyInvisibles", 99))
/*  82 */         localScoreboardTeam.setCanSeeFriendlyInvisibles(localNBTTagCompound.getBoolean("SeeFriendlyInvisibles"));
/*     */       ScoreboardTeamBase.EnumNameTagVisibility localEnumNameTagVisibility;
/*  84 */       if (localNBTTagCompound.hasKeyOfType("NameTagVisibility", 8)) {
/*  85 */         localEnumNameTagVisibility = ScoreboardTeamBase.EnumNameTagVisibility.a(localNBTTagCompound.getString("NameTagVisibility"));
/*  86 */         if (localEnumNameTagVisibility != null) {
/*  87 */           localScoreboardTeam.setNameTagVisibility(localEnumNameTagVisibility);
/*     */         }
/*     */       }
/*  90 */       if (localNBTTagCompound.hasKeyOfType("DeathMessageVisibility", 8)) {
/*  91 */         localEnumNameTagVisibility = ScoreboardTeamBase.EnumNameTagVisibility.a(localNBTTagCompound.getString("DeathMessageVisibility"));
/*  92 */         if (localEnumNameTagVisibility != null) {
/*  93 */           localScoreboardTeam.b(localEnumNameTagVisibility);
/*     */         }
/*     */       }
/*     */       
/*  97 */       a(localScoreboardTeam, localNBTTagCompound.getList("Players", 8));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(ScoreboardTeam paramScoreboardTeam, NBTTagList paramNBTTagList) {
/* 102 */     for (int i = 0; i < paramNBTTagList.size(); i++) {
/* 103 */       this.c.addPlayerToTeam(paramNBTTagList.getString(i), paramScoreboardTeam.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c(NBTTagCompound paramNBTTagCompound) {
/* 108 */     for (int i = 0; i < 19; i++) {
/* 109 */       if (paramNBTTagCompound.hasKeyOfType("slot_" + i, 8)) {
/* 110 */         String str = paramNBTTagCompound.getString("slot_" + i);
/* 111 */         ScoreboardObjective localScoreboardObjective = this.c.getObjective(str);
/* 112 */         this.c.setDisplaySlot(i, localScoreboardObjective);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagList paramNBTTagList) {
/* 118 */     for (int i = 0; i < paramNBTTagList.size(); i++) {
/* 119 */       NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
/*     */       
/* 121 */       IScoreboardCriteria localIScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(localNBTTagCompound.getString("CriteriaName"));
/* 122 */       if (localIScoreboardCriteria != null)
/*     */       {
/*     */ 
/* 125 */         String str = localNBTTagCompound.getString("Name");
/* 126 */         if (str.length() > 16)
/*     */         {
/* 128 */           str = str.substring(0, 16);
/*     */         }
/* 130 */         ScoreboardObjective localScoreboardObjective = this.c.registerObjective(str, localIScoreboardCriteria);
/* 131 */         localScoreboardObjective.setDisplayName(localNBTTagCompound.getString("DisplayName"));
/* 132 */         localScoreboardObjective.a(IScoreboardCriteria.EnumScoreboardHealthDisplay.a(localNBTTagCompound.getString("RenderType")));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 137 */   protected void c(NBTTagList paramNBTTagList) { for (int i = 0; i < paramNBTTagList.size(); i++) {
/* 138 */       NBTTagCompound localNBTTagCompound = paramNBTTagList.get(i);
/*     */       
/* 140 */       ScoreboardObjective localScoreboardObjective = this.c.getObjective(localNBTTagCompound.getString("Objective"));
/* 141 */       String str = localNBTTagCompound.getString("Name");
/* 142 */       if (str.length() > 40)
/*     */       {
/* 144 */         str = str.substring(0, 40);
/*     */       }
/* 146 */       ScoreboardScore localScoreboardScore = this.c.getPlayerScoreForObjective(str, localScoreboardObjective);
/* 147 */       localScoreboardScore.setScore(localNBTTagCompound.getInt("Score"));
/* 148 */       if (localNBTTagCompound.hasKey("Locked")) {
/* 149 */         localScoreboardScore.a(localNBTTagCompound.getBoolean("Locked"));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 156 */     if (this.c == null) {
/* 157 */       b.warn("Tried to save scoreboard without having a scoreboard...");
/* 158 */       return;
/*     */     }
/*     */     
/* 161 */     paramNBTTagCompound.set("Objectives", b());
/* 162 */     paramNBTTagCompound.set("PlayerScores", e());
/* 163 */     paramNBTTagCompound.set("Teams", a());
/*     */     
/* 165 */     d(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   protected NBTTagList a() {
/* 169 */     NBTTagList localNBTTagList1 = new NBTTagList();
/* 170 */     Collection localCollection = this.c.getTeams();
/*     */     
/* 172 */     for (ScoreboardTeam localScoreboardTeam : localCollection) {
/* 173 */       NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */       
/* 175 */       localNBTTagCompound.setString("Name", localScoreboardTeam.getName());
/* 176 */       localNBTTagCompound.setString("DisplayName", localScoreboardTeam.getDisplayName());
/* 177 */       if (localScoreboardTeam.l().b() >= 0) {
/* 178 */         localNBTTagCompound.setString("TeamColor", localScoreboardTeam.l().e());
/*     */       }
/* 180 */       localNBTTagCompound.setString("Prefix", localScoreboardTeam.getPrefix());
/* 181 */       localNBTTagCompound.setString("Suffix", localScoreboardTeam.getSuffix());
/* 182 */       localNBTTagCompound.setBoolean("AllowFriendlyFire", localScoreboardTeam.allowFriendlyFire());
/* 183 */       localNBTTagCompound.setBoolean("SeeFriendlyInvisibles", localScoreboardTeam.canSeeFriendlyInvisibles());
/* 184 */       localNBTTagCompound.setString("NameTagVisibility", localScoreboardTeam.getNameTagVisibility().e);
/* 185 */       localNBTTagCompound.setString("DeathMessageVisibility", localScoreboardTeam.j().e);
/*     */       
/* 187 */       NBTTagList localNBTTagList2 = new NBTTagList();
/*     */       
/* 189 */       for (String str : localScoreboardTeam.getPlayerNameSet()) {
/* 190 */         localNBTTagList2.add(new NBTTagString(str));
/*     */       }
/*     */       
/* 193 */       localNBTTagCompound.set("Players", localNBTTagList2);
/*     */       
/* 195 */       localNBTTagList1.add(localNBTTagCompound);
/*     */     }
/*     */     
/* 198 */     return localNBTTagList1;
/*     */   }
/*     */   
/*     */   protected void d(NBTTagCompound paramNBTTagCompound) {
/* 202 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 203 */     int i = 0;
/*     */     
/* 205 */     for (int j = 0; j < 19; j++) {
/* 206 */       ScoreboardObjective localScoreboardObjective = this.c.getObjectiveForSlot(j);
/*     */       
/* 208 */       if (localScoreboardObjective != null) {
/* 209 */         localNBTTagCompound.setString("slot_" + j, localScoreboardObjective.getName());
/* 210 */         i = 1;
/*     */       }
/*     */     }
/*     */     
/* 214 */     if (i != 0) {
/* 215 */       paramNBTTagCompound.set("DisplaySlots", localNBTTagCompound);
/*     */     }
/*     */   }
/*     */   
/*     */   protected NBTTagList b() {
/* 220 */     NBTTagList localNBTTagList = new NBTTagList();
/* 221 */     Collection localCollection = this.c.getObjectives();
/*     */     
/* 223 */     for (ScoreboardObjective localScoreboardObjective : localCollection) {
/* 224 */       if (localScoreboardObjective.getCriteria() != null)
/*     */       {
/*     */ 
/*     */ 
/* 228 */         NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 229 */         localNBTTagCompound.setString("Name", localScoreboardObjective.getName());
/* 230 */         localNBTTagCompound.setString("CriteriaName", localScoreboardObjective.getCriteria().getName());
/* 231 */         localNBTTagCompound.setString("DisplayName", localScoreboardObjective.getDisplayName());
/* 232 */         localNBTTagCompound.setString("RenderType", localScoreboardObjective.e().a());
/*     */         
/* 234 */         localNBTTagList.add(localNBTTagCompound);
/*     */       }
/*     */     }
/* 237 */     return localNBTTagList;
/*     */   }
/*     */   
/*     */   protected NBTTagList e() {
/* 241 */     NBTTagList localNBTTagList = new NBTTagList();
/* 242 */     Collection localCollection = this.c.getScores();
/*     */     
/* 244 */     for (ScoreboardScore localScoreboardScore : localCollection) {
/* 245 */       if (localScoreboardScore.getObjective() != null)
/*     */       {
/*     */ 
/*     */ 
/* 249 */         NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 250 */         localNBTTagCompound.setString("Name", localScoreboardScore.getPlayerName());
/* 251 */         localNBTTagCompound.setString("Objective", localScoreboardScore.getObjective().getName());
/* 252 */         localNBTTagCompound.setInt("Score", localScoreboardScore.getScore());
/* 253 */         localNBTTagCompound.setBoolean("Locked", localScoreboardScore.g());
/*     */         
/* 255 */         localNBTTagList.add(localNBTTagCompound);
/*     */       }
/*     */     }
/* 258 */     return localNBTTagList;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PersistentScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */