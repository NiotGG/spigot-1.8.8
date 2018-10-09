/*    */ package org.bukkit.block;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum PistonMoveReaction
/*    */ {
/*  8 */   MOVE(
/*    */   
/*    */ 
/* 11 */     0), 
/* 12 */   BREAK(
/*    */   
/*    */ 
/* 15 */     1), 
/* 16 */   BLOCK(
/*    */   
/*    */ 
/* 19 */     2);
/*    */   
/*    */   static {
/* 22 */     byId = new HashMap();
/*    */     PistonMoveReaction[] arrayOfPistonMoveReaction;
/* 24 */     int i = (arrayOfPistonMoveReaction = values()).length; for (int j = 0; j < i; j++) { PistonMoveReaction reaction = arrayOfPistonMoveReaction[j];
/* 25 */       byId.put(Integer.valueOf(reaction.id), reaction);
/*    */     }
/*    */   }
/*    */   
/*    */   private PistonMoveReaction(int id) {
/* 30 */     this.id = id;
/*    */   }
/*    */   
/*    */ 
/*    */   private int id;
/*    */   
/*    */   @Deprecated
/*    */   public int getId()
/*    */   {
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private static Map<Integer, PistonMoveReaction> byId;
/*    */   
/*    */   @Deprecated
/*    */   public static PistonMoveReaction getById(int id)
/*    */   {
/* 49 */     return (PistonMoveReaction)byId.get(Integer.valueOf(id));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\PistonMoveReaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */