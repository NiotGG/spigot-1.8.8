/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginLogger
/*    */   extends Logger
/*    */ {
/*    */   private String pluginName;
/*    */   
/*    */   public PluginLogger(Plugin context)
/*    */   {
/* 23 */     super(context.getClass().getCanonicalName(), null);
/* 24 */     String prefix = context.getDescription().getPrefix();
/* 25 */     this.pluginName = ("[" + context.getDescription().getName() + "] ");
/* 26 */     setParent(context.getServer().getLogger());
/* 27 */     setLevel(Level.ALL);
/*    */   }
/*    */   
/*    */   public void log(LogRecord logRecord)
/*    */   {
/* 32 */     logRecord.setMessage(this.pluginName + logRecord.getMessage());
/* 33 */     super.log(logRecord);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */