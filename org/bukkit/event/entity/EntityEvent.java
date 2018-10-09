/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public abstract class EntityEvent
/*    */   extends Event
/*    */ {
/*    */   protected Entity entity;
/*    */   
/*    */   public EntityEvent(Entity what)
/*    */   {
/* 14 */     this.entity = what;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getEntity()
/*    */   {
/* 23 */     return this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EntityType getEntityType()
/*    */   {
/* 32 */     return this.entity.getType();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */