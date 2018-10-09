/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ public class EntityCombustByEntityEvent
/*    */   extends EntityCombustEvent
/*    */ {
/*    */   private final Entity combuster;
/*    */   
/*    */   public EntityCombustByEntityEvent(Entity combuster, Entity combustee, int duration)
/*    */   {
/* 12 */     super(combustee, duration);
/* 13 */     this.combuster = combuster;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getCombuster()
/*    */   {
/* 22 */     return this.combuster;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityCombustByEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */