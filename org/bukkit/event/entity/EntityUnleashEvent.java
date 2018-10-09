/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntityUnleashEvent
/*    */   extends EntityEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final UnleashReason reason;
/*    */   
/*    */   public EntityUnleashEvent(Entity entity, UnleashReason reason) {
/* 14 */     super(entity);
/* 15 */     this.reason = reason;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UnleashReason getReason()
/*    */   {
/* 24 */     return this.reason;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 29 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 33 */     return handlers;
/*    */   }
/*    */   
/*    */   public static enum UnleashReason {
/* 37 */     HOLDER_GONE, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 42 */     PLAYER_UNLEASH, 
/*    */     
/*    */ 
/*    */ 
/* 46 */     DISTANCE, 
/*    */     
/*    */ 
/*    */ 
/* 50 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityUnleashEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */