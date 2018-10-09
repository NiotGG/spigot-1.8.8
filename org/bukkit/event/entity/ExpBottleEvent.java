/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.ThrownExpBottle;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class ExpBottleEvent
/*    */   extends ProjectileHitEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private int exp;
/* 12 */   private boolean showEffect = true;
/*    */   
/*    */   public ExpBottleEvent(ThrownExpBottle bottle, int exp) {
/* 15 */     super(bottle);
/* 16 */     this.exp = exp;
/*    */   }
/*    */   
/*    */   public ThrownExpBottle getEntity()
/*    */   {
/* 21 */     return (ThrownExpBottle)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean getShowEffect()
/*    */   {
/* 30 */     return this.showEffect;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setShowEffect(boolean showEffect)
/*    */   {
/* 42 */     this.showEffect = showEffect;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getExperience()
/*    */   {
/* 53 */     return this.exp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setExperience(int exp)
/*    */   {
/* 64 */     this.exp = exp;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 69 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 73 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\ExpBottleEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */