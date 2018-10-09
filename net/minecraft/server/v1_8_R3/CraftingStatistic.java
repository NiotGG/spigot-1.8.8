/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CraftingStatistic extends Statistic
/*    */ {
/*    */   private final Item a;
/*    */   
/*    */   public CraftingStatistic(String paramString1, String paramString2, IChatBaseComponent paramIChatBaseComponent, Item paramItem)
/*    */   {
/* 11 */     super(paramString1 + paramString2, paramIChatBaseComponent);
/* 12 */     this.a = paramItem;
/*    */     
/* 14 */     int i = Item.getId(paramItem);
/* 15 */     if (i != 0) {
/* 16 */       IScoreboardCriteria.criteria.put(paramString1 + i, k());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CraftingStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */