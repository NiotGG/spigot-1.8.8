/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ReloadCommand extends BukkitCommand
/*    */ {
/*    */   public ReloadCommand(String name)
/*    */   {
/* 12 */     super(name);
/* 13 */     this.description = "Reloads the server configuration and plugins";
/* 14 */     this.usageMessage = "/reload";
/* 15 */     setPermission("bukkit.command.reload");
/* 16 */     setAliases(Arrays.asList(new String[] { "rl" }));
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 21 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 23 */     Command.broadcastCommandMessage(sender, ChatColor.RED + "Please note that this command is not supported and may cause issues when using some plugins.");
/* 24 */     Command.broadcastCommandMessage(sender, ChatColor.RED + "If you encounter any issues please use the /stop command to restart your server.");
/* 25 */     org.bukkit.Bukkit.reload();
/* 26 */     Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Reload complete.");
/*    */     
/* 28 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */     throws IllegalArgumentException
/*    */   {
/* 35 */     return java.util.Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ReloadCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */