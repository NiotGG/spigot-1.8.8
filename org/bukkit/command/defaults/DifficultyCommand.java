/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Difficulty;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class DifficultyCommand extends VanillaCommand
/*    */ {
/* 17 */   private static final List<String> DIFFICULTY_NAMES = ImmutableList.of("peaceful", "easy", "normal", "hard");
/*    */   
/*    */   public DifficultyCommand() {
/* 20 */     super("difficulty");
/* 21 */     this.description = "Sets the game difficulty";
/* 22 */     this.usageMessage = "/difficulty <new difficulty> ";
/* 23 */     setPermission("bukkit.command.difficulty");
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
/* 34 */     Difficulty difficulty = Difficulty.getByValue(getDifficultyForString(sender, args[0]));
/*    */     
/* 36 */     if (Bukkit.isHardcore()) {
/* 37 */       difficulty = Difficulty.HARD;
/*    */     }
/*    */     
/* 40 */     ((World)Bukkit.getWorlds().get(0)).setDifficulty(difficulty);
/*    */     
/* 42 */     int levelCount = 1;
/* 43 */     if (Bukkit.getAllowNether()) {
/* 44 */       ((World)Bukkit.getWorlds().get(levelCount)).setDifficulty(difficulty);
/* 45 */       levelCount++;
/*    */     }
/*    */     
/* 48 */     if (Bukkit.getAllowEnd()) {
/* 49 */       ((World)Bukkit.getWorlds().get(levelCount)).setDifficulty(difficulty);
/*    */     }
/*    */     
/* 52 */     org.bukkit.command.Command.broadcastCommandMessage(sender, "Set difficulty to " + difficulty.toString());
/* 53 */     return true;
/*    */   }
/*    */   
/*    */   protected int getDifficultyForString(CommandSender sender, String name) {
/* 57 */     if ((name.equalsIgnoreCase("peaceful")) || (name.equalsIgnoreCase("p")))
/* 58 */       return 0;
/* 59 */     if ((name.equalsIgnoreCase("easy")) || (name.equalsIgnoreCase("e")))
/* 60 */       return 1;
/* 61 */     if ((name.equalsIgnoreCase("normal")) || (name.equalsIgnoreCase("n")))
/* 62 */       return 2;
/* 63 */     if ((name.equalsIgnoreCase("hard")) || (name.equalsIgnoreCase("h"))) {
/* 64 */       return 3;
/*    */     }
/* 66 */     return getInteger(sender, name, 0, 3);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 72 */     Validate.notNull(sender, "Sender cannot be null");
/* 73 */     Validate.notNull(args, "Arguments cannot be null");
/* 74 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 76 */     if (args.length == 1) {
/* 77 */       return (List)StringUtil.copyPartialMatches(args[0], DIFFICULTY_NAMES, new ArrayList(DIFFICULTY_NAMES.size()));
/*    */     }
/*    */     
/* 80 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DifficultyCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */