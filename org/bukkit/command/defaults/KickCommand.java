/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class KickCommand extends VanillaCommand
/*    */ {
/*    */   public KickCommand()
/*    */   {
/* 17 */     super("kick");
/* 18 */     this.description = "Removes the specified player from the server";
/* 19 */     this.usageMessage = "/kick <player> [reason ...]";
/* 20 */     setPermission("bukkit.command.kick");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 25 */     if (!testPermission(sender)) return true;
/* 26 */     if ((args.length < 1) || (args[0].length() == 0)) {
/* 27 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */     
/* 33 */     if (player != null) {
/* 34 */       String reason = "Kicked by an operator.";
/*    */       
/* 36 */       if (args.length > 1) {
/* 37 */         reason = createString(args, 1);
/*    */       }
/*    */       
/* 40 */       player.kickPlayer(reason);
/* 41 */       Command.broadcastCommandMessage(sender, "Kicked player " + player.getName() + ". With reason:\n" + reason);
/*    */     } else {
/* 43 */       sender.sendMessage(args[0] + " not found.");
/*    */     }
/*    */     
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 51 */     Validate.notNull(sender, "Sender cannot be null");
/* 52 */     Validate.notNull(args, "Arguments cannot be null");
/* 53 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 55 */     if (args.length >= 1) {
/* 56 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 58 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\KickCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */