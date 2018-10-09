/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ScoreboardBaseCriteria implements IScoreboardCriteria
/*    */ {
/*    */   private final String j;
/*    */   
/*    */   public ScoreboardBaseCriteria(String paramString)
/*    */   {
/* 11 */     this.j = paramString;
/* 12 */     IScoreboardCriteria.criteria.put(paramString, this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 17 */     return this.j;
/*    */   }
/*    */   
/*    */   public int getScoreModifier(List<EntityHuman> paramList)
/*    */   {
/* 22 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isReadOnly()
/*    */   {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria.EnumScoreboardHealthDisplay c()
/*    */   {
/* 32 */     return IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardBaseCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */