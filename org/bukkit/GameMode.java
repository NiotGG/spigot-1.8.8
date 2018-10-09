/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum GameMode
/*    */ {
/* 14 */   CREATIVE(
/*    */   
/*    */ 
/*    */ 
/* 18 */     1), 
/*    */   
/* 20 */   SURVIVAL(
/*    */   
/*    */ 
/* 23 */     0), 
/*    */   
/* 25 */   ADVENTURE(
/*    */   
/*    */ 
/* 28 */     2), 
/*    */   
/* 30 */   SPECTATOR(
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 35 */     3);
/*    */   
/*    */   private final int value;
/*    */   private static final Map<Integer, GameMode> BY_ID;
/*    */   
/*    */   private GameMode(int value) {
/* 41 */     this.value = value;
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
/* 52 */     return this.value;
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
/*    */   public static GameMode getByValue(int value)
/*    */   {
/* 65 */     return (GameMode)BY_ID.get(Integer.valueOf(value));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 38 */     BY_ID = Maps.newHashMap();
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
/*    */     GameMode[] arrayOfGameMode;
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
/* 69 */     int i = (arrayOfGameMode = values()).length; for (int j = 0; j < i; j++) { GameMode mode = arrayOfGameMode[j];
/* 70 */       BY_ID.put(Integer.valueOf(mode.getValue()), mode);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\GameMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */