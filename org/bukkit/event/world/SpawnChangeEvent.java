/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnChangeEvent
/*    */   extends WorldEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final Location previousLocation;
/*    */   
/*    */   public SpawnChangeEvent(World world, Location previousLocation) {
/* 16 */     super(world);
/* 17 */     this.previousLocation = previousLocation;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Location getPreviousLocation()
/*    */   {
/* 26 */     return this.previousLocation;
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
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\SpawnChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */