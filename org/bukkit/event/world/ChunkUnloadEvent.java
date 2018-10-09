/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ChunkUnloadEvent
/*    */   extends ChunkEvent
/*    */   implements Cancellable
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/* 12 */   private boolean cancel = false;
/*    */   
/*    */   public ChunkUnloadEvent(Chunk chunk) {
/* 15 */     super(chunk);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 19 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 23 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 28 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 32 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\ChunkUnloadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */