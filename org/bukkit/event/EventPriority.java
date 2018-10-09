/*    */ package org.bukkit.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EventPriority
/*    */ {
/*  8 */   LOWEST(
/*    */   
/*    */ 
/*    */ 
/* 12 */     0), 
/* 13 */   LOW(
/*    */   
/*    */ 
/* 16 */     1), 
/* 17 */   NORMAL(
/*    */   
/*    */ 
/*    */ 
/* 21 */     2), 
/* 22 */   HIGH(
/*    */   
/*    */ 
/* 25 */     3), 
/* 26 */   HIGHEST(
/*    */   
/*    */ 
/*    */ 
/* 30 */     4), 
/* 31 */   MONITOR(
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 36 */     5);
/*    */   
/*    */   private final int slot;
/*    */   
/*    */   private EventPriority(int slot) {
/* 41 */     this.slot = slot;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 45 */     return this.slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\EventPriority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */