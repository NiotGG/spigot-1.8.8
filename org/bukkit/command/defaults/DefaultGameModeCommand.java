/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class DefaultGameModeCommand extends VanillaCommand
/*    */ {
/* 17 */   private static final List<String> GAMEMODE_NAMES = ImmutableList.of("adventure", "creative", "survival");
/*    */   
/*    */   public DefaultGameModeCommand() {
/* 20 */     super("defaultgamemode");
/* 21 */     this.description = "Set the default gamemode";
/* 22 */     this.usageMessage = "/defaultgamemode <mode>";
/* 23 */     setPermission("bukkit.command.defaultgamemode");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*    */   {
/* 28 */     if (!testPermission(sender)) return true;
/* 29 */     if (args.length == 0) {
/* 30 */       sender.sendMessage("Usage: " + this.usageMessage);
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     String modeArg = args[0];
/* 35 */     int value = -1;
/*    */     try
/*    */     {
/* 38 */       value = Integer.parseInt(modeArg);
/*    */     }
/*    */     catch (NumberFormatException localNumberFormatException) {}
/* 41 */     GameMode mode = GameMode.getByValue(value);
/*    */     
/* 43 */     if (mode == null) {
/* 44 */       if ((modeArg.equalsIgnoreCase("creative")) || (modeArg.equalsIgnoreCase("c"))) {
/* 45 */         mode = GameMode.CREATIVE;
/* 46 */       } else if ((modeArg.equalsIgnoreCase("adventure")) || (modeArg.equalsIgnoreCase("a"))) {
/* 47 */         mode = GameMode.ADVENTURE;
/*    */       } else {
/* 49 */         mode = GameMode.SURVIVAL;
/*    */       }
/*    */     }
/*    */     
/* 53 */     Bukkit.getServer().setDefaultGameMode(mode);
/* 54 */     Command.broadcastCommandMessage(sender, "Default game mode set to " + mode.toString().toLowerCase());
/*    */     
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 61 */     Validate.notNull(sender, "Sender cannot be null");
/* 62 */     Validate.notNull(args, "Arguments cannot be null");
/* 63 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 65 */     if (args.length == 1) {
/* 66 */       return (List)StringUtil.copyPartialMatches(args[0], GAMEMODE_NAMES, new ArrayList(GAMEMODE_NAMES.size()));
/*    */     }
/*    */     
/* 69 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DefaultGameModeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */