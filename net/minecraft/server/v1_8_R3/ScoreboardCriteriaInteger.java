/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ScoreboardCriteriaInteger implements IScoreboardCriteria
/*    */ {
/*    */   private final String j;
/*    */   
/*    */   public ScoreboardCriteriaInteger(String paramString, EnumChatFormat paramEnumChatFormat)
/*    */   {
/* 12 */     this.j = (paramString + paramEnumChatFormat.e());
/* 13 */     IScoreboardCriteria.criteria.put(this.j, this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 18 */     return this.j;
/*    */   }
/*    */   
/*    */   public int getScoreModifier(List<EntityHuman> paramList)
/*    */   {
/* 23 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isReadOnly()
/*    */   {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria.EnumScoreboardHealthDisplay c()
/*    */   {
/* 33 */     return IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardCriteriaInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */