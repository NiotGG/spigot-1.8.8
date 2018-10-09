/*    */ package org.bukkit.event.weather;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public abstract class WeatherEvent
/*    */   extends Event
/*    */ {
/*    */   protected World world;
/*    */   
/*    */   public WeatherEvent(World where)
/*    */   {
/* 13 */     this.world = where;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final World getWorld()
/*    */   {
/* 22 */     return this.world;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\weather\WeatherEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */