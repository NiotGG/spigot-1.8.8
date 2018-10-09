/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerInteractAtEntityEvent
/*    */   extends PlayerInteractEntityEvent
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Vector position;
/*    */   
/*    */   public PlayerInteractAtEntityEvent(Player who, Entity clickedEntity, Vector position) {
/* 17 */     super(who, clickedEntity);
/* 18 */     this.position = position;
/*    */   }
/*    */   
/*    */   public Vector getClickedPosition() {
/* 22 */     return this.position.clone();
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 27 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 31 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerInteractAtEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */