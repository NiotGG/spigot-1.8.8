/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ScoreboardHealthCriteria
/*    */   extends ScoreboardBaseCriteria
/*    */ {
/*    */   public ScoreboardHealthCriteria(String paramString)
/*    */   {
/* 10 */     super(paramString);
/*    */   }
/*    */   
/*    */   public int getScoreModifier(List<EntityHuman> paramList)
/*    */   {
/* 15 */     float f = 0.0F;
/*    */     
/* 17 */     for (EntityHuman localEntityHuman : paramList) {
/* 18 */       f += localEntityHuman.getHealth() + localEntityHuman.getAbsorptionHearts();
/*    */     }
/*    */     
/* 21 */     if (paramList.size() > 0) {
/* 22 */       f /= paramList.size();
/*    */     }
/*    */     
/* 25 */     return MathHelper.f(f);
/*    */   }
/*    */   
/*    */   public boolean isReadOnly()
/*    */   {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public IScoreboardCriteria.EnumScoreboardHealthDisplay c()
/*    */   {
/* 35 */     return IScoreboardCriteria.EnumScoreboardHealthDisplay.HEARTS;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ScoreboardHealthCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */