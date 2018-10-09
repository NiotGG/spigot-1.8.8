/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class WorldInitEvent
/*    */   extends WorldEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public WorldInitEvent(World world) {
/* 13 */     super(world);
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 18 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 22 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\WorldInitEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */