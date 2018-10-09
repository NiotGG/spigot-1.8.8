/*    */ package org.spigotmc;
/*    */ 
/*    */ public class TickLimiter
/*    */ {
/*    */   private final int maxTime;
/*    */   private long startTime;
/*    */   
/*    */   public TickLimiter(int maxtime) {
/*  9 */     this.maxTime = maxtime;
/*    */   }
/*    */   
/*    */   public void initTick() {
/* 13 */     this.startTime = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean shouldContinue() {
/* 17 */     long remaining = System.currentTimeMillis() - this.startTime;
/* 18 */     return remaining < this.maxTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\TickLimiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */