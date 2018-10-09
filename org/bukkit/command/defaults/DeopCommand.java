/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class DeopCommand extends VanillaCommand
/*    */ {
/*    */   public DeopCommand()
/*    */   {
/* 20 */     super("deop");
/* 21 */     this.description = "Takes the specified player's operator status";
/* 22 */     this.usageMessage = "/deop <player>";
/* 23 */     setPermission("bukkit.command.op.take");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 28 */     if (!testPermission(sender)) return true;
/* 29 */     if ((args.length != 1) || (args[0].length() == 0)) {
/* 30 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 35 */     player.setOp(false);
/*    */     
/* 37 */     if ((player instanceof Player)) {
/* 38 */       ((Player)player).sendMessage(ChatColor.YELLOW + "You are no longer op!");
/*    */     }
/*    */     
/* 41 */     Command.broadcastCommandMessage(sender, "De-opped " + args[0]);
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 47 */     Validate.notNull(sender, "Sender cannot be null");
/* 48 */     Validate.notNull(args, "Arguments cannot be null");
/* 49 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 51 */     if (args.length == 1) {
/* 52 */       List<String> completions = new ArrayList();
/* 53 */       for (OfflinePlayer player : Bukkit.getOperators()) {
/* 54 */         String playerName = player.getName();
/* 55 */         if (StringUtil.startsWithIgnoreCase(playerName, args[0])) {
/* 56 */           completions.add(playerName);
/*    */         }
/*    */       }
/* 59 */       return completions;
/*    */     }
/* 61 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DeopCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */