/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ 
/*    */ public class EntityTargetLivingEntityEvent
/*    */   extends EntityTargetEvent
/*    */ {
/*    */   public EntityTargetLivingEntityEvent(Entity entity, LivingEntity target, EntityTargetEvent.TargetReason reason)
/*    */   {
/* 12 */     super(entity, target, reason);
/*    */   }
/*    */   
/*    */   public LivingEntity getTarget() {
/* 16 */     return (LivingEntity)super.getTarget();
/*    */   }
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
/*    */   public void setTarget(Entity target)
/*    */   {
/* 30 */     if ((target == null) || ((target instanceof LivingEntity))) {
/* 31 */       super.setTarget(target);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityTargetLivingEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */