/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Rabbit
/*    */   extends Animals
/*    */ {
/*    */   public abstract Type getRabbitType();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setRabbitType(Type paramType);
/*    */   
/*    */ 
/*    */ 
/*    */   public static enum Type
/*    */   {
/* 20 */     BROWN, 
/*    */     
/*    */ 
/*    */ 
/* 24 */     WHITE, 
/*    */     
/*    */ 
/*    */ 
/* 28 */     BLACK, 
/*    */     
/*    */ 
/*    */ 
/* 32 */     BLACK_AND_WHITE, 
/*    */     
/*    */ 
/*    */ 
/* 36 */     GOLD, 
/*    */     
/*    */ 
/*    */ 
/* 40 */     SALT_AND_PEPPER, 
/*    */     
/*    */ 
/*    */ 
/* 44 */     THE_KILLER_BUNNY;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Rabbit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */