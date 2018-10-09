/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface LightningStrike
/*    */   extends Weather
/*    */ {
/*    */   public abstract boolean isEffect();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract Spigot spigot();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static class Spigot
/*    */     extends Entity.Spigot
/*    */   {
/*    */     public boolean isSilent()
/*    */     {
/* 26 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\LightningStrike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */