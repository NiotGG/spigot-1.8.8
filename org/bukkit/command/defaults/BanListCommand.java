/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.BanList.Type;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class BanListCommand
/*    */   extends VanillaCommand
/*    */ {
/* 20 */   private static final List<String> BANLIST_TYPES = ImmutableList.of("ips", "players");
/*    */   
/*    */   public BanListCommand() {
/* 23 */     super("banlist");
/* 24 */     this.description = "View all players banned from this server";
/* 25 */     this.usageMessage = "/banlist [ips|players]";
/* 26 */     setPermission("bukkit.command.ban.list");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 31 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 33 */     BanList.Type banType = BanList.Type.NAME;
/* 34 */     if (args.length > 0) {
/* 35 */       if (args[0].equalsIgnoreCase("ips")) {
/* 36 */         banType = BanList.Type.IP;
/* 37 */       } else if (!args[0].equalsIgnoreCase("players")) {
/* 38 */         sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 39 */         return false;
/*    */       }
/*    */     }
/*    */     
/* 43 */     StringBuilder message = new StringBuilder();
/* 44 */     BanEntry[] banlist = (BanEntry[])Bukkit.getBanList(banType).getBanEntries().toArray(new BanEntry[0]);
/*    */     
/* 46 */     for (int x = 0; x < banlist.length; x++) {
/* 47 */       if (x != 0) {
/* 48 */         if (x == banlist.length - 1) {
/* 49 */           message.append(" and ");
/*    */         } else {
/* 51 */           message.append(", ");
/*    */         }
/*    */       }
/*    */       
/* 55 */       message.append(banlist[x].getTarget());
/*    */     }
/*    */     
/* 58 */     sender.sendMessage("There are " + banlist.length + " total banned players:");
/* 59 */     sender.sendMessage(message.toString());
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 65 */     Validate.notNull(sender, "Sender cannot be null");
/* 66 */     Validate.notNull(args, "Arguments cannot be null");
/* 67 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 69 */     if (args.length == 1) {
/* 70 */       return (List)StringUtil.copyPartialMatches(args[0], BANLIST_TYPES, new ArrayList(BANLIST_TYPES.size()));
/*    */     }
/* 72 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanListCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */