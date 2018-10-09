/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class OpCommand extends VanillaCommand
/*    */ {
/*    */   public OpCommand()
/*    */   {
/* 21 */     super("op");
/* 22 */     this.description = "Gives the specified player operator status";
/* 23 */     this.usageMessage = "/op <player>";
/* 24 */     setPermission("bukkit.command.op.give");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 29 */     if (!testPermission(sender)) return true;
/* 30 */     if ((args.length != 1) || (args[0].length() == 0)) {
/* 31 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 36 */     player.setOp(true);
/*    */     
/* 38 */     Command.broadcastCommandMessage(sender, "Opped " + args[0]);
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 44 */     Validate.notNull(sender, "Sender cannot be null");
/* 45 */     Validate.notNull(args, "Arguments cannot be null");
/* 46 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 48 */     if (args.length == 1) {
/* 49 */       if (!(sender instanceof Player)) {
/* 50 */         return ImmutableList.of();
/*    */       }
/*    */       
/* 53 */       String lastWord = args[0];
/* 54 */       if (lastWord.length() == 0) {
/* 55 */         return ImmutableList.of();
/*    */       }
/*    */       
/* 58 */       Player senderPlayer = (Player)sender;
/*    */       
/* 60 */       ArrayList<String> matchedPlayers = new ArrayList();
/* 61 */       for (Player player : sender.getServer().getOnlinePlayers()) {
/* 62 */         String name = player.getName();
/* 63 */         if ((senderPlayer.canSee(player)) && (!player.isOp()))
/*    */         {
/*    */ 
/* 66 */           if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
/* 67 */             matchedPlayers.add(name);
/*    */           }
/*    */         }
/*    */       }
/* 71 */       Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
/* 72 */       return matchedPlayers;
/*    */     }
/* 74 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\OpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */