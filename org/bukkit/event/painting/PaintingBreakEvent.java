/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.Warning;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Warning(reason="This event has been replaced by HangingBreakEvent")
/*    */ public class PaintingBreakEvent
/*    */   extends PaintingEvent
/*    */   implements Cancellable
/*    */ {
/* 16 */   private static final HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private final RemoveCause cause;
/*    */   
/*    */   public PaintingBreakEvent(Painting painting, RemoveCause cause) {
/* 21 */     super(painting);
/* 22 */     this.cause = cause;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public RemoveCause getCause()
/*    */   {
/* 31 */     return this.cause;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 35 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 39 */     this.cancelled = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static enum RemoveCause
/*    */   {
/* 46 */     ENTITY, 
/*    */     
/*    */ 
/*    */ 
/* 50 */     FIRE, 
/*    */     
/*    */ 
/*    */ 
/* 54 */     OBSTRUCTION, 
/*    */     
/*    */ 
/*    */ 
/* 58 */     WATER, 
/*    */     
/*    */ 
/*    */ 
/* 62 */     PHYSICS;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public HandlerList getHandlers()
/*    */   {
/* 70 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 74 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\painting\PaintingBreakEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */