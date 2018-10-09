/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPortalExitEvent
/*    */   extends EntityTeleportEvent
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/*    */   private Vector before;
/*    */   private Vector after;
/*    */   
/*    */   public EntityPortalExitEvent(Entity entity, Location from, Location to, Vector before, Vector after) {
/* 20 */     super(entity, from, to);
/* 21 */     this.before = before;
/* 22 */     this.after = after;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Vector getBefore()
/*    */   {
/* 32 */     return this.before.clone();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Vector getAfter()
/*    */   {
/* 42 */     return this.after.clone();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAfter(Vector after)
/*    */   {
/* 51 */     this.after = after.clone();
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 56 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 60 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityPortalExitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */