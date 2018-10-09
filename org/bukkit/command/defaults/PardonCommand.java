/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class PardonCommand extends VanillaCommand
/*    */ {
/*    */   public PardonCommand()
/*    */   {
/* 20 */     super("pardon");
/* 21 */     this.description = "Allows the specified player to use this server";
/* 22 */     this.usageMessage = "/pardon <player>";
/* 23 */     setPermission("bukkit.command.unban.player");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 28 */     if (!testPermission(sender)) return true;
/* 29 */     if (args.length != 1) {
/* 30 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(args[0]);
/* 35 */     Command.broadcastCommandMessage(sender, "Pardoned " + args[0]);
/* 36 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 41 */     Validate.notNull(sender, "Sender cannot be null");
/* 42 */     Validate.notNull(args, "Arguments cannot be null");
/* 43 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 45 */     if (args.length == 1) {
/* 46 */       List<String> completions = new ArrayList();
/* 47 */       for (OfflinePlayer player : Bukkit.getBannedPlayers()) {
/* 48 */         String name = player.getName();
/* 49 */         if (StringUtil.startsWithIgnoreCase(name, args[0])) {
/* 50 */           completions.add(name);
/*    */         }
/*    */       }
/* 53 */       return completions;
/*    */     }
/* 55 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PardonCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */