/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ @Deprecated
/*     */ public class WhitelistCommand
/*     */   extends VanillaCommand
/*     */ {
/*  18 */   private static final List<String> WHITELIST_SUBCOMMANDS = ImmutableList.of("add", "remove", "on", "off", "list", "reload");
/*     */   
/*     */   public WhitelistCommand() {
/*  21 */     super("whitelist");
/*  22 */     this.description = "Manages the list of players allowed to use this server";
/*  23 */     this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
/*  24 */     setPermission("bukkit.command.whitelist.reload;bukkit.command.whitelist.enable;bukkit.command.whitelist.disable;bukkit.command.whitelist.list;bukkit.command.whitelist.add;bukkit.command.whitelist.remove");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  29 */     if (!testPermission(sender)) { return true;
/*     */     }
/*  31 */     if (args.length == 1) {
/*  32 */       if (args[0].equalsIgnoreCase("reload")) {
/*  33 */         if (badPerm(sender, "reload")) { return true;
/*     */         }
/*  35 */         Bukkit.reloadWhitelist();
/*  36 */         Command.broadcastCommandMessage(sender, "Reloaded white-list from file");
/*  37 */         return true; }
/*  38 */       if (args[0].equalsIgnoreCase("on")) {
/*  39 */         if (badPerm(sender, "enable")) { return true;
/*     */         }
/*  41 */         Bukkit.setWhitelist(true);
/*  42 */         Command.broadcastCommandMessage(sender, "Turned on white-listing");
/*  43 */         return true; }
/*  44 */       if (args[0].equalsIgnoreCase("off")) {
/*  45 */         if (badPerm(sender, "disable")) { return true;
/*     */         }
/*  47 */         Bukkit.setWhitelist(false);
/*  48 */         Command.broadcastCommandMessage(sender, "Turned off white-listing");
/*  49 */         return true; }
/*  50 */       if (args[0].equalsIgnoreCase("list")) {
/*  51 */         if (badPerm(sender, "list")) { return true;
/*     */         }
/*  53 */         StringBuilder result = new StringBuilder();
/*     */         
/*  55 */         for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
/*  56 */           if (result.length() > 0) {
/*  57 */             result.append(", ");
/*     */           }
/*     */           
/*  60 */           result.append(player.getName());
/*     */         }
/*     */         
/*  63 */         sender.sendMessage("White-listed players: " + result.toString());
/*  64 */         return true;
/*     */       }
/*  66 */     } else if (args.length == 2) {
/*  67 */       if (args[0].equalsIgnoreCase("add")) {
/*  68 */         if (badPerm(sender, "add")) { return true;
/*     */         }
/*  70 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
/*     */         
/*  72 */         Command.broadcastCommandMessage(sender, "Added " + args[1] + " to white-list");
/*  73 */         return true; }
/*  74 */       if (args[0].equalsIgnoreCase("remove")) {
/*  75 */         if (badPerm(sender, "remove")) { return true;
/*     */         }
/*  77 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);
/*     */         
/*  79 */         Command.broadcastCommandMessage(sender, "Removed " + args[1] + " from white-list");
/*  80 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  84 */     sender.sendMessage(ChatColor.RED + "Correct command usage:\n" + this.usageMessage);
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   private boolean badPerm(CommandSender sender, String perm) {
/*  89 */     if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
/*  90 */       sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action.");
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*     */   {
/*  99 */     Validate.notNull(sender, "Sender cannot be null");
/* 100 */     Validate.notNull(args, "Arguments cannot be null");
/* 101 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 103 */     if (args.length == 1)
/* 104 */       return (List)StringUtil.copyPartialMatches(args[0], WHITELIST_SUBCOMMANDS, new ArrayList(WHITELIST_SUBCOMMANDS.size()));
/* 105 */     if (args.length == 2) {
/* 106 */       if (args[0].equalsIgnoreCase("add")) {
/* 107 */         List<String> completions = new ArrayList();
/* 108 */         for (OfflinePlayer player : Bukkit.getOnlinePlayers()) {
/* 109 */           String name = player.getName();
/* 110 */           if ((StringUtil.startsWithIgnoreCase(name, args[1])) && (!player.isWhitelisted())) {
/* 111 */             completions.add(name);
/*     */           }
/*     */         }
/* 114 */         return completions; }
/* 115 */       if (args[0].equalsIgnoreCase("remove")) {
/* 116 */         List<String> completions = new ArrayList();
/* 117 */         for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
/* 118 */           String name = player.getName();
/* 119 */           if (StringUtil.startsWithIgnoreCase(name, args[1])) {
/* 120 */             completions.add(name);
/*     */           }
/*     */         }
/* 123 */         return completions;
/*     */       }
/*     */     }
/* 126 */     return ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\WhitelistCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */