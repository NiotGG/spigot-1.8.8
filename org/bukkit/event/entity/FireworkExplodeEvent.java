/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Firework;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class FireworkExplodeEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   
/*    */   public FireworkExplodeEvent(Firework what) {
/* 16 */     super(what);
/*    */   }
/*    */   
/*    */   public boolean isCancelled()
/*    */   {
/* 21 */     return this.cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCancelled(boolean cancel)
/*    */   {
/* 33 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public Firework getEntity()
/*    */   {
/* 38 */     return (Firework)super.getEntity();
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 43 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 47 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\FireworkExplodeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */