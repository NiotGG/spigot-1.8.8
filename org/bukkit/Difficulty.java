/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Difficulty
/*    */ {
/* 11 */   PEACEFUL(
/*    */   
/*    */ 
/*    */ 
/* 15 */     0), 
/*    */   
/* 17 */   EASY(
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 22 */     1), 
/*    */   
/* 24 */   NORMAL(
/*    */   
/*    */ 
/*    */ 
/* 28 */     2), 
/*    */   
/* 30 */   HARD(
/*    */   
/*    */ 
/*    */ 
/* 34 */     3);
/*    */   
/*    */   private final int value;
/*    */   private static final Map<Integer, Difficulty> BY_ID;
/*    */   
/*    */   private Difficulty(int value) {
/* 40 */     this.value = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public int getValue()
/*    */   {
/* 51 */     return this.value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static Difficulty getByValue(int value)
/*    */   {
/* 64 */     return (Difficulty)BY_ID.get(Integer.valueOf(value));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 37 */     BY_ID = Maps.newHashMap();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     Difficulty[] arrayOfDifficulty;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 68 */     int i = (arrayOfDifficulty = values()).length; for (int j = 0; j < i; j++) { Difficulty diff = arrayOfDifficulty[j];
/* 69 */       BY_ID.put(Integer.valueOf(diff.value), diff);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Difficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */