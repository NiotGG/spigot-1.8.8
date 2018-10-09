/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CounterStatistic extends Statistic {
/*    */   public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter) {
/*  7 */     super(paramString, paramIChatBaseComponent, paramCounter);
/*    */   }
/*    */   
/*    */   public CounterStatistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
/* 11 */     super(paramString, paramIChatBaseComponent);
/*    */   }
/*    */   
/*    */   public Statistic h()
/*    */   {
/* 16 */     super.h();
/*    */     
/* 18 */     StatisticList.c.add(this);
/*    */     
/* 20 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CounterStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */