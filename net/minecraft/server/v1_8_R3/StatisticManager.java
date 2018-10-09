/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.event.Cancellable;
/*    */ 
/*    */ public class StatisticManager
/*    */ {
/*  8 */   protected final Map<Statistic, StatisticWrapper> a = com.google.common.collect.Maps.newConcurrentMap();
/*    */   
/*    */ 
/*    */   public boolean hasAchievement(Achievement achievement)
/*    */   {
/* 13 */     return getStatisticValue(achievement) > 0;
/*    */   }
/*    */   
/*    */   public boolean b(Achievement achievement) {
/* 17 */     return (achievement.c == null) || (hasAchievement(achievement.c));
/*    */   }
/*    */   
/*    */   public void b(EntityHuman entityhuman, Statistic statistic, int i) {
/* 21 */     if ((!statistic.d()) || (b((Achievement)statistic)))
/*    */     {
/* 23 */       Cancellable cancellable = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.handleStatisticsIncrease(entityhuman, statistic, getStatisticValue(statistic), i);
/* 24 */       if ((cancellable != null) && (cancellable.isCancelled())) {
/* 25 */         return;
/*    */       }
/*    */       
/* 28 */       setStatistic(entityhuman, statistic, getStatisticValue(statistic) + i);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setStatistic(EntityHuman entityhuman, Statistic statistic, int i) {
/* 33 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 35 */     if (statisticwrapper == null) {
/* 36 */       statisticwrapper = new StatisticWrapper();
/* 37 */       this.a.put(statistic, statisticwrapper);
/*    */     }
/*    */     
/* 40 */     statisticwrapper.a(i);
/*    */   }
/*    */   
/*    */   public int getStatisticValue(Statistic statistic) {
/* 44 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 46 */     return statisticwrapper == null ? 0 : statisticwrapper.a();
/*    */   }
/*    */   
/*    */   public <T extends IJsonStatistic> T b(Statistic statistic) {
/* 50 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 52 */     return statisticwrapper != null ? statisticwrapper.b() : null;
/*    */   }
/*    */   
/*    */   public <T extends IJsonStatistic> T a(Statistic statistic, T t0) {
/* 56 */     StatisticWrapper statisticwrapper = (StatisticWrapper)this.a.get(statistic);
/*    */     
/* 58 */     if (statisticwrapper == null) {
/* 59 */       statisticwrapper = new StatisticWrapper();
/* 60 */       this.a.put(statistic, statisticwrapper);
/*    */     }
/*    */     
/* 63 */     statisticwrapper.a(t0);
/* 64 */     return t0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StatisticManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */