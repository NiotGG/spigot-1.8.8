/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class BanCommand extends VanillaCommand
/*    */ {
/*    */   public BanCommand()
/*    */   {
/* 19 */     super("ban");
/* 20 */     this.description = "Prevents the specified player from using this server";
/* 21 */     this.usageMessage = "/ban <player> [reason ...]";
/* 22 */     setPermission("bukkit.command.ban.player");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 27 */     if (!testPermission(sender)) return true;
/* 28 */     if (args.length == 0) {
/* 29 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     String reason = args.length > 0 ? StringUtils.join(args, ' ', 1, args.length) : null;
/* 34 */     Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
/*    */     
/* 36 */     Player player = Bukkit.getPlayer(args[0]);
/* 37 */     if (player != null) {
/* 38 */       player.kickPlayer("Banned by admin.");
/*    */     }
/*    */     
/* 41 */     Command.broadcastCommandMessage(sender, "Banned player " + args[0]);
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 47 */     Validate.notNull(sender, "Sender cannot be null");
/* 48 */     Validate.notNull(args, "Arguments cannot be null");
/* 49 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 51 */     if (args.length >= 1) {
/* 52 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 54 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */