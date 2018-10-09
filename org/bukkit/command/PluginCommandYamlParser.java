/*    */ package org.bukkit.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class PluginCommandYamlParser
/*    */ {
/*    */   public static List<Command> parse(Plugin plugin)
/*    */   {
/* 14 */     List<Command> pluginCmds = new java.util.ArrayList();
/*    */     
/* 16 */     Map<String, Map<String, Object>> map = plugin.getDescription().getCommands();
/*    */     
/* 18 */     if (map == null) {
/* 19 */       return pluginCmds;
/*    */     }
/*    */     
/* 22 */     for (Map.Entry<String, Map<String, Object>> entry : map.entrySet())
/* 23 */       if (((String)entry.getKey()).contains(":")) {
/* 24 */         org.bukkit.Bukkit.getServer().getLogger().severe("Could not load command " + (String)entry.getKey() + " for plugin " + plugin.getName() + ": Illegal Characters");
/*    */       }
/*    */       else {
/* 27 */         Command newCmd = new PluginCommand((String)entry.getKey(), plugin);
/* 28 */         Object description = ((Map)entry.getValue()).get("description");
/* 29 */         Object usage = ((Map)entry.getValue()).get("usage");
/* 30 */         Object aliases = ((Map)entry.getValue()).get("aliases");
/* 31 */         Object permission = ((Map)entry.getValue()).get("permission");
/* 32 */         Object permissionMessage = ((Map)entry.getValue()).get("permission-message");
/*    */         
/* 34 */         if (description != null) {
/* 35 */           newCmd.setDescription(description.toString());
/*    */         }
/*    */         
/* 38 */         if (usage != null) {
/* 39 */           newCmd.setUsage(usage.toString());
/*    */         }
/*    */         
/* 42 */         if (aliases != null) {
/* 43 */           List<String> aliasList = new java.util.ArrayList();
/*    */           
/* 45 */           if ((aliases instanceof List)) {
/* 46 */             for (Object o : (List)aliases) {
/* 47 */               if (o.toString().contains(":")) {
/* 48 */                 org.bukkit.Bukkit.getServer().getLogger().severe("Could not load alias " + o.toString() + " for plugin " + plugin.getName() + ": Illegal Characters");
/*    */               }
/*    */               else {
/* 51 */                 aliasList.add(o.toString());
/*    */               }
/*    */             }
/* 54 */           } else if (aliases.toString().contains(":")) {
/* 55 */             org.bukkit.Bukkit.getServer().getLogger().severe("Could not load alias " + aliases.toString() + " for plugin " + plugin.getName() + ": Illegal Characters");
/*    */           } else {
/* 57 */             aliasList.add(aliases.toString());
/*    */           }
/*    */           
/*    */ 
/* 61 */           newCmd.setAliases(aliasList);
/*    */         }
/*    */         
/* 64 */         if (permission != null) {
/* 65 */           newCmd.setPermission(permission.toString());
/*    */         }
/*    */         
/* 68 */         if (permissionMessage != null) {
/* 69 */           newCmd.setPermissionMessage(permissionMessage.toString());
/*    */         }
/*    */         
/* 72 */         pluginCmds.add(newCmd);
/*    */       }
/* 74 */     return pluginCmds;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\PluginCommandYamlParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */