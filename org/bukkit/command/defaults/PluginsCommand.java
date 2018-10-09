/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class PluginsCommand extends BukkitCommand
/*    */ {
/*    */   public PluginsCommand(String name)
/*    */   {
/* 12 */     super(name);
/* 13 */     this.description = "Gets a list of plugins running on the server";
/* 14 */     this.usageMessage = "/plugins";
/* 15 */     setPermission("bukkit.command.plugins");
/* 16 */     setAliases(java.util.Arrays.asList(new String[] { "pl" }));
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 21 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 23 */     sender.sendMessage("Plugins " + getPluginList());
/* 24 */     return true;
/*    */   }
/*    */   
/*    */   private String getPluginList() {
/* 28 */     StringBuilder pluginList = new StringBuilder();
/* 29 */     Plugin[] plugins = org.bukkit.Bukkit.getPluginManager().getPlugins();
/*    */     Plugin[] arrayOfPlugin1;
/* 31 */     int i = (arrayOfPlugin1 = plugins).length; for (int j = 0; j < i; j++) { Plugin plugin = arrayOfPlugin1[j];
/* 32 */       if (pluginList.length() > 0) {
/* 33 */         pluginList.append(ChatColor.WHITE);
/* 34 */         pluginList.append(", ");
/*    */       }
/*    */       
/* 37 */       pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
/* 38 */       pluginList.append(plugin.getDescription().getName());
/*    */     }
/*    */     
/* 41 */     return "(" + plugins.length + "): " + pluginList.toString();
/*    */   }
/*    */   
/*    */ 
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */     throws IllegalArgumentException
/*    */   {
/* 48 */     return java.util.Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PluginsCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */