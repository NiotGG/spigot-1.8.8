/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class GameModeCommand
/*    */   extends VanillaCommand
/*    */ {
/* 19 */   private static final List<String> GAMEMODE_NAMES = ImmutableList.of("adventure", "creative", "survival", "spectator");
/*    */   
/*    */   public GameModeCommand() {
/* 22 */     super("gamemode");
/* 23 */     this.description = "Changes the player to a specific game mode";
/* 24 */     this.usageMessage = "/gamemode <mode> [player]";
/* 25 */     setPermission("bukkit.command.gamemode");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 30 */     if (!testPermission(sender)) return true;
/* 31 */     if (args.length == 0) {
/* 32 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     String modeArg = args[0];
/* 37 */     String playerArg = sender.getName();
/*    */     
/* 39 */     if (args.length == 2) {
/* 40 */       playerArg = args[1];
/*    */     }
/*    */     
/* 43 */     Player player = Bukkit.getPlayerExact(playerArg);
/*    */     
/* 45 */     if (player != null) {
/* 46 */       int value = -1;
/*    */       try
/*    */       {
/* 49 */         value = Integer.parseInt(modeArg);
/*    */       }
/*    */       catch (NumberFormatException localNumberFormatException) {}
/* 52 */       GameMode mode = GameMode.getByValue(value);
/*    */       
/* 54 */       if (mode == null) {
/* 55 */         if ((modeArg.equalsIgnoreCase("creative")) || (modeArg.equalsIgnoreCase("c"))) {
/* 56 */           mode = GameMode.CREATIVE;
/* 57 */         } else if ((modeArg.equalsIgnoreCase("adventure")) || (modeArg.equalsIgnoreCase("a"))) {
/* 58 */           mode = GameMode.ADVENTURE;
/* 59 */         } else if ((modeArg.equalsIgnoreCase("spectator")) || (modeArg.equalsIgnoreCase("sp"))) {
/* 60 */           mode = GameMode.SPECTATOR;
/*    */         } else {
/* 62 */           mode = GameMode.SURVIVAL;
/*    */         }
/*    */       }
/*    */       
/* 66 */       if (mode != player.getGameMode()) {
/* 67 */         player.setGameMode(mode);
/*    */         
/* 69 */         if (mode != player.getGameMode()) {
/* 70 */           sender.sendMessage("Game mode change for " + player.getName() + " failed!");
/*    */         }
/* 72 */         else if (player == sender) {
/* 73 */           Command.broadcastCommandMessage(sender, "Set own game mode to " + mode.toString() + " mode");
/*    */         } else {
/* 75 */           Command.broadcastCommandMessage(sender, "Set " + player.getName() + "'s game mode to " + mode.toString() + " mode");
/*    */         }
/*    */       }
/*    */       else {
/* 79 */         sender.sendMessage(player.getName() + " already has game mode " + mode.getValue());
/*    */       }
/*    */     } else {
/* 82 */       sender.sendMessage("Can't find player " + playerArg);
/*    */     }
/*    */     
/* 85 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 90 */     Validate.notNull(sender, "Sender cannot be null");
/* 91 */     Validate.notNull(args, "Arguments cannot be null");
/* 92 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 94 */     if (args.length == 1)
/* 95 */       return (List)StringUtil.copyPartialMatches(args[0], GAMEMODE_NAMES, new ArrayList(GAMEMODE_NAMES.size()));
/* 96 */     if (args.length == 2) {
/* 97 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 99 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GameModeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */