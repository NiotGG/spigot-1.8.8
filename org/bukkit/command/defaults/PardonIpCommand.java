/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Deprecated
/*    */ public class PardonIpCommand extends VanillaCommand
/*    */ {
/*    */   public PardonIpCommand()
/*    */   {
/* 18 */     super("pardon-ip");
/* 19 */     this.description = "Allows the specified IP address to use this server";
/* 20 */     this.usageMessage = "/pardon-ip <address>";
/* 21 */     setPermission("bukkit.command.unban.ip");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 26 */     if (!testPermission(sender)) return true;
/* 27 */     if (args.length != 1) {
/* 28 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     if (BanIpCommand.ipValidity.matcher(args[0]).matches()) {
/* 33 */       Bukkit.unbanIP(args[0]);
/* 34 */       org.bukkit.command.Command.broadcastCommandMessage(sender, "Pardoned ip " + args[0]);
/*    */     } else {
/* 36 */       sender.sendMessage("Invalid ip");
/*    */     }
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 44 */     Validate.notNull(sender, "Sender cannot be null");
/* 45 */     Validate.notNull(args, "Arguments cannot be null");
/* 46 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 48 */     if (args.length == 1) {
/* 49 */       return (List)org.bukkit.util.StringUtil.copyPartialMatches(args[0], Bukkit.getIPBans(), new ArrayList());
/*    */     }
/* 51 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PardonIpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */