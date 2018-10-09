/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
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
/*     */ public class ScoreboardTeam
/*     */   extends ScoreboardTeamBase
/*     */ {
/*     */   private final Scoreboard a;
/*     */   private final String b;
/*  21 */   private final Set<String> c = Sets.newHashSet();
/*     */   private String d;
/*  23 */   private String e = "";
/*  24 */   private String f = "";
/*  25 */   private boolean g = true;
/*  26 */   private boolean h = true;
/*  27 */   private ScoreboardTeamBase.EnumNameTagVisibility i = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
/*  28 */   private ScoreboardTeamBase.EnumNameTagVisibility j = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
/*  29 */   private EnumChatFormat k = EnumChatFormat.RESET;
/*     */   
/*     */   public ScoreboardTeam(Scoreboard paramScoreboard, String paramString) {
/*  32 */     this.a = paramScoreboard;
/*  33 */     this.b = paramString;
/*  34 */     this.d = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  43 */     return this.b;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/*  47 */     return this.d;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String paramString) {
/*  51 */     if (paramString == null) {
/*  52 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/*  54 */     this.d = paramString;
/*  55 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public Collection<String> getPlayerNameSet()
/*     */   {
/*  60 */     return this.c;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/*  64 */     return this.e;
/*     */   }
/*     */   
/*     */   public void setPrefix(String paramString) {
/*  68 */     if (paramString == null) {
/*  69 */       throw new IllegalArgumentException("Prefix cannot be null");
/*     */     }
/*  71 */     this.e = paramString;
/*  72 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public String getSuffix() {
/*  76 */     return this.f;
/*     */   }
/*     */   
/*     */   public void setSuffix(String paramString) {
/*  80 */     this.f = paramString;
/*  81 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public String getFormattedName(String paramString)
/*     */   {
/*  86 */     return getPrefix() + paramString + getSuffix();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getPlayerDisplayName(ScoreboardTeamBase paramScoreboardTeamBase, String paramString)
/*     */   {
/*  94 */     if (paramScoreboardTeamBase == null) {
/*  95 */       return paramString;
/*     */     }
/*  97 */     return paramScoreboardTeamBase.getFormattedName(paramString);
/*     */   }
/*     */   
/*     */   public boolean allowFriendlyFire()
/*     */   {
/* 102 */     return this.g;
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean paramBoolean) {
/* 106 */     this.g = paramBoolean;
/* 107 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public boolean canSeeFriendlyInvisibles()
/*     */   {
/* 112 */     return this.h;
/*     */   }
/*     */   
/*     */   public void setCanSeeFriendlyInvisibles(boolean paramBoolean) {
/* 116 */     this.h = paramBoolean;
/* 117 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility()
/*     */   {
/* 122 */     return this.i;
/*     */   }
/*     */   
/*     */   public ScoreboardTeamBase.EnumNameTagVisibility j()
/*     */   {
/* 127 */     return this.j;
/*     */   }
/*     */   
/*     */   public void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility paramEnumNameTagVisibility) {
/* 131 */     this.i = paramEnumNameTagVisibility;
/* 132 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public void b(ScoreboardTeamBase.EnumNameTagVisibility paramEnumNameTagVisibility) {
/* 136 */     this.j = paramEnumNameTagVisibility;
/* 137 */     this.a.handleTeamChanged(this);
/*     */   }
/*     */   
/*     */   public int packOptionData() {
/* 141 */     int m = 0;
/*     */     
/* 143 */     if (allowFriendlyFire()) {
/* 144 */       m |= 0x1;
/*     */     }
/* 146 */     if (canSeeFriendlyInvisibles()) {
/* 147 */       m |= 0x2;
/*     */     }
/*     */     
/* 150 */     return m;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(EnumChatFormat paramEnumChatFormat)
/*     */   {
/* 159 */     this.k = paramEnumChatFormat;
/*     */   }
/*     */   
/*     */   public EnumChatFormat l() {
/* 163 */     return this.k;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */