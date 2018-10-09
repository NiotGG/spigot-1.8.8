/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class TellCommand extends VanillaCommand
/*    */ {
/*    */   public TellCommand()
/*    */   {
/* 13 */     super("tell");
/* 14 */     this.description = "Sends a private message to the given player";
/* 15 */     this.usageMessage = "/tell <player> <message>";
/* 16 */     setAliases(Arrays.asList(new String[] { "w", "msg" }));
/* 17 */     setPermission("bukkit.command.tell");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 22 */     if (!testPermission(sender)) return true;
/* 23 */     if (args.length < 2) {
/* 24 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 25 */       return false;
/*    */     }
/*    */     
/* 28 */     Player player = org.bukkit.Bukkit.getPlayerExact(args[0]);
/*    */     
/*    */ 
/* 31 */     if ((player == null) || (((sender instanceof Player)) && (!((Player)sender).canSee(player)))) {
/* 32 */       sender.sendMessage("There's no player by that name online.");
/*    */     } else {
/* 34 */       StringBuilder message = new StringBuilder();
/*    */       
/* 36 */       for (int i = 1; i < args.length; i++) {
/* 37 */         if (i > 1) message.append(" ");
/* 38 */         message.append(args[i]);
/*    */       }
/*    */       
/* 41 */       String result = ChatColor.GRAY + sender.getName() + " whispers " + message;
/*    */       
/* 43 */       sender.sendMessage("[" + sender.getName() + "->" + player.getName() + "] " + message);
/* 44 */       player.sendMessage(result);
/*    */     }
/*    */     
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */     throws IllegalArgumentException
/*    */   {
/* 54 */     if (args.length == 0)
/*    */     {
/* 56 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 58 */     return java.util.Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TellCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */