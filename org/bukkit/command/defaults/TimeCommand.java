/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class TimeCommand
/*    */   extends VanillaCommand
/*    */ {
/* 18 */   private static final List<String> TABCOMPLETE_ADD_SET = ImmutableList.of("add", "set");
/* 19 */   private static final List<String> TABCOMPLETE_DAY_NIGHT = ImmutableList.of("day", "night");
/*    */   
/*    */   public TimeCommand() {
/* 22 */     super("time");
/* 23 */     this.description = "Changes the time on each world";
/* 24 */     this.usageMessage = "/time set <value>\n/time add <value>";
/* 25 */     setPermission("bukkit.command.time.add;bukkit.command.time.set");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 30 */     if (args.length < 2) {
/* 31 */       sender.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage:\n" + this.usageMessage);
/* 32 */       return false;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 37 */     if (args[0].equals("set")) {
/* 38 */       if (!sender.hasPermission("bukkit.command.time.set")) {
/* 39 */         sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
/* 40 */         return true; }
/*    */       int value;
/*    */       int value;
/* 43 */       if (args[1].equals("day")) {
/* 44 */         value = 0; } else { int value;
/* 45 */         if (args[1].equals("night")) {
/* 46 */           value = 12500;
/*    */         } else {
/* 48 */           value = getInteger(sender, args[1], 0);
/*    */         }
/*    */       }
/* 51 */       for (World world : Bukkit.getWorlds()) {
/* 52 */         world.setTime(value);
/*    */       }
/*    */       
/* 55 */       Command.broadcastCommandMessage(sender, "Set time to " + value);
/* 56 */     } else if (args[0].equals("add")) {
/* 57 */       if (!sender.hasPermission("bukkit.command.time.add")) {
/* 58 */         sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
/* 59 */         return true;
/*    */       }
/*    */       
/* 62 */       int value = getInteger(sender, args[1], 0);
/*    */       
/* 64 */       for (World world : Bukkit.getWorlds()) {
/* 65 */         world.setFullTime(world.getFullTime() + value);
/*    */       }
/*    */       
/* 68 */       Command.broadcastCommandMessage(sender, "Added " + value + " to time");
/*    */     } else {
/* 70 */       sender.sendMessage("Unknown method. Usage: " + this.usageMessage);
/*    */     }
/*    */     
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 78 */     Validate.notNull(sender, "Sender cannot be null");
/* 79 */     Validate.notNull(args, "Arguments cannot be null");
/* 80 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 82 */     if (args.length == 1)
/* 83 */       return (List)StringUtil.copyPartialMatches(args[0], TABCOMPLETE_ADD_SET, new ArrayList(TABCOMPLETE_ADD_SET.size()));
/* 84 */     if ((args.length == 2) && (args[0].equalsIgnoreCase("set"))) {
/* 85 */       return (List)StringUtil.copyPartialMatches(args[1], TABCOMPLETE_DAY_NIGHT, new ArrayList(TABCOMPLETE_DAY_NIGHT.size()));
/*    */     }
/* 87 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TimeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */