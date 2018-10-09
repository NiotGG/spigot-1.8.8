/*    */ package org.bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Rotation
/*    */ {
/* 10 */   NONE, 
/*    */   
/*    */ 
/*    */ 
/* 14 */   CLOCKWISE_45, 
/*    */   
/*    */ 
/*    */ 
/* 18 */   CLOCKWISE, 
/*    */   
/*    */ 
/*    */ 
/* 22 */   CLOCKWISE_135, 
/*    */   
/*    */ 
/*    */ 
/* 26 */   FLIPPED, 
/*    */   
/*    */ 
/*    */ 
/* 30 */   FLIPPED_45, 
/*    */   
/*    */ 
/*    */ 
/* 34 */   COUNTER_CLOCKWISE, 
/*    */   
/*    */ 
/*    */ 
/* 38 */   COUNTER_CLOCKWISE_45;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 44 */   private static final Rotation[] rotations = values();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Rotation rotateClockwise()
/*    */   {
/* 52 */     return rotations[(ordinal() + 1 & 0x7)];
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Rotation rotateCounterClockwise()
/*    */   {
/* 61 */     return rotations[(ordinal() - 1 & 0x7)];
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Rotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */