/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public abstract class PluginEvent
/*    */   extends ServerEvent
/*    */ {
/*    */   private final Plugin plugin;
/*    */   
/*    */   public PluginEvent(Plugin plugin)
/*    */   {
/* 12 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Plugin getPlugin()
/*    */   {
/* 21 */     return this.plugin;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\PluginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */