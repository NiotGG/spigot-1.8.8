/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface IScoreboardCriteria
/*    */ {
/* 11 */   public static final Map<String, IScoreboardCriteria> criteria = ;
/*    */   
/* 13 */   public static final IScoreboardCriteria b = new ScoreboardBaseCriteria("dummy");
/* 14 */   public static final IScoreboardCriteria c = new ScoreboardBaseCriteria("trigger");
/* 15 */   public static final IScoreboardCriteria d = new ScoreboardBaseCriteria("deathCount");
/* 16 */   public static final IScoreboardCriteria e = new ScoreboardBaseCriteria("playerKillCount");
/* 17 */   public static final IScoreboardCriteria f = new ScoreboardBaseCriteria("totalKillCount");
/* 18 */   public static final IScoreboardCriteria g = new ScoreboardHealthCriteria("health");
/* 19 */   public static final IScoreboardCriteria[] h = { new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.BLACK), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_BLUE), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_GREEN), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_AQUA), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_RED), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_PURPLE), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.GOLD), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.GRAY), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.DARK_GRAY), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.BLUE), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.GREEN), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.AQUA), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.RED), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.LIGHT_PURPLE), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.YELLOW), new ScoreboardCriteriaInteger("teamkill.", EnumChatFormat.WHITE) };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 29 */   public static final IScoreboardCriteria[] i = { new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.BLACK), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_BLUE), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_GREEN), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_AQUA), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_RED), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_PURPLE), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.GOLD), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.GRAY), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.DARK_GRAY), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.BLUE), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.GREEN), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.AQUA), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.RED), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.LIGHT_PURPLE), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.YELLOW), new ScoreboardCriteriaInteger("killedByTeam.", EnumChatFormat.WHITE) };
/*    */   
/*    */ 
/*    */   public abstract String getName();
/*    */   
/*    */ 
/*    */   public abstract int getScoreModifier(List<EntityHuman> paramList);
/*    */   
/*    */ 
/*    */   public abstract boolean isReadOnly();
/*    */   
/*    */ 
/*    */   public abstract EnumScoreboardHealthDisplay c();
/*    */   
/*    */   public static enum EnumScoreboardHealthDisplay
/*    */   {
/*    */     private static final Map<String, EnumScoreboardHealthDisplay> c;
/*    */     private final String d;
/*    */     
/*    */     static
/*    */     {
/* 50 */       c = Maps.newHashMap();
/*    */       
/*    */ 
/*    */ 
/* 54 */       for (EnumScoreboardHealthDisplay localEnumScoreboardHealthDisplay : values()) {
/* 55 */         c.put(localEnumScoreboardHealthDisplay.a(), localEnumScoreboardHealthDisplay);
/*    */       }
/*    */     }
/*    */     
/*    */     private EnumScoreboardHealthDisplay(String paramString) {
/* 60 */       this.d = paramString;
/*    */     }
/*    */     
/*    */     public String a() {
/* 64 */       return this.d;
/*    */     }
/*    */     
/*    */     public static EnumScoreboardHealthDisplay a(String paramString) {
/* 68 */       EnumScoreboardHealthDisplay localEnumScoreboardHealthDisplay = (EnumScoreboardHealthDisplay)c.get(paramString);
/* 69 */       return localEnumScoreboardHealthDisplay == null ? INTEGER : localEnumScoreboardHealthDisplay;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IScoreboardCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */