/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.entity.Sheep;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class SheepRegrowWoolEvent
/*    */   extends EntityEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancel;
/*    */   
/*    */   public SheepRegrowWoolEvent(Sheep sheep) {
/* 15 */     super(sheep);
/* 16 */     this.cancel = false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 20 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 24 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public Sheep getEntity()
/*    */   {
/* 29 */     return (Sheep)this.entity;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 34 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 38 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\SheepRegrowWoolEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */