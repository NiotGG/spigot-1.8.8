/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Arrow
/*    */   extends Projectile
/*    */ {
/*    */   public abstract int getKnockbackStrength();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setKnockbackStrength(int paramInt);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract boolean isCritical();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setCritical(boolean paramBoolean);
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
/*    */ 
/*    */   public static class Spigot
/*    */     extends Entity.Spigot
/*    */   {
/*    */     public double getDamage()
/*    */     {
/* 48 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */     
/*    */     public void setDamage(double damage)
/*    */     {
/* 53 */       throw new UnsupportedOperationException("Not supported yet.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Arrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */