/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public abstract class WorldEvent
/*    */   extends Event
/*    */ {
/*    */   private final World world;
/*    */   
/*    */   public WorldEvent(World world)
/*    */   {
/* 13 */     this.world = world;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public World getWorld()
/*    */   {
/* 22 */     return this.world;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\WorldEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */