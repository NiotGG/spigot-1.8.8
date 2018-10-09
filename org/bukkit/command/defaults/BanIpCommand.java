/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class BanIpCommand extends VanillaCommand
/*    */ {
/* 19 */   public static final Pattern ipValidity = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*    */   
/*    */   public BanIpCommand() {
/* 22 */     super("ban-ip");
/* 23 */     this.description = "Prevents the specified IP address from using this server";
/* 24 */     this.usageMessage = "/ban-ip <address|player> [reason ...]";
/* 25 */     setPermission("bukkit.command.ban.ip");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 30 */     if (!testPermission(sender)) return true;
/* 31 */     if (args.length < 1) {
/* 32 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     String reason = args.length > 0 ? StringUtils.join(args, ' ', 1, args.length) : null;
/*    */     
/* 38 */     if (ipValidity.matcher(args[0]).matches()) {
/* 39 */       processIPBan(args[0], sender, reason);
/*    */     } else {
/* 41 */       Player player = Bukkit.getPlayer(args[0]);
/*    */       
/* 43 */       if (player == null) {
/* 44 */         sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 45 */         return false;
/*    */       }
/*    */       
/* 48 */       processIPBan(player.getAddress().getAddress().getHostAddress(), sender, reason);
/*    */     }
/*    */     
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   private void processIPBan(String ip, CommandSender sender, String reason) {
/* 55 */     Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(ip, reason, null, sender.getName());
/*    */     
/*    */ 
/* 58 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 59 */       if (player.getAddress().getAddress().getHostAddress().equals(ip)) {
/* 60 */         player.kickPlayer("You have been IP banned.");
/*    */       }
/*    */     }
/*    */     
/* 64 */     org.bukkit.command.Command.broadcastCommandMessage(sender, "Banned IP Address " + ip);
/*    */   }
/*    */   
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 69 */     Validate.notNull(sender, "Sender cannot be null");
/* 70 */     Validate.notNull(args, "Arguments cannot be null");
/* 71 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 73 */     if (args.length == 1) {
/* 74 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 76 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanIpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */