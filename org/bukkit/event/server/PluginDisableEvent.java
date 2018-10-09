/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class PluginDisableEvent
/*    */   extends PluginEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public PluginDisableEvent(Plugin plugin) {
/* 13 */     super(plugin);
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


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\PluginDisableEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */