/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerResourcePackStatusEvent
/*    */   extends PlayerEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Status status;
/*    */   
/*    */   public PlayerResourcePackStatusEvent(Player who, Status resourcePackStatus) {
/* 16 */     super(who);
/* 17 */     this.status = resourcePackStatus;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Status getStatus()
/*    */   {
/* 26 */     return this.status;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 31 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 35 */     return handlers;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum Status
/*    */   {
/* 43 */     SUCCESSFULLY_LOADED, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 48 */     DECLINED, 
/*    */     
/*    */ 
/*    */ 
/* 52 */     FAILED_DOWNLOAD, 
/*    */     
/*    */ 
/*    */ 
/* 56 */     ACCEPTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerResourcePackStatusEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */