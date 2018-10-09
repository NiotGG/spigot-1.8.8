/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.Statistic.Type;
/*     */ import org.bukkit.UnsafeValues;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerAchievementAwardedEvent;
/*     */ import org.bukkit.event.player.PlayerStatisticIncrementEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ @Deprecated
/*     */ public class AchievementCommand extends VanillaCommand
/*     */ {
/*     */   public AchievementCommand()
/*     */   {
/*  26 */     super("achievement");
/*  27 */     this.description = "Gives the specified player an achievement or changes a statistic value. Use '*' to give all achievements.";
/*  28 */     this.usageMessage = "/achievement give <stat_name> [player]";
/*  29 */     setPermission("bukkit.command.achievement");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  34 */     if (!testPermission(sender)) { return true;
/*     */     }
/*  36 */     if (args.length < 2) {
/*  37 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  38 */       return false;
/*     */     }
/*     */     
/*  41 */     if (!args[0].equalsIgnoreCase("give")) {
/*  42 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     String statisticString = args[1];
/*  47 */     Player player = null;
/*     */     
/*  49 */     if (args.length > 2) {
/*  50 */       player = Bukkit.getPlayer(args[1]);
/*  51 */     } else if ((sender instanceof Player)) {
/*  52 */       player = (Player)sender;
/*     */     }
/*     */     
/*  55 */     if (player == null) {
/*  56 */       sender.sendMessage("You must specify which player you wish to perform this action on.");
/*  57 */       return true;
/*     */     }
/*     */     
/*  60 */     if (statisticString.equals("*")) { Achievement[] arrayOfAchievement;
/*  61 */       int i = (arrayOfAchievement = Achievement.values()).length; for (int j = 0; j < i; j++) { Achievement achievement = arrayOfAchievement[j];
/*  62 */         if (!player.hasAchievement(achievement))
/*     */         {
/*     */ 
/*  65 */           PlayerAchievementAwardedEvent event = new PlayerAchievementAwardedEvent(player, achievement);
/*  66 */           Bukkit.getServer().getPluginManager().callEvent(event);
/*  67 */           if (!event.isCancelled())
/*  68 */             player.awardAchievement(achievement);
/*     */         }
/*     */       }
/*  71 */       Command.broadcastCommandMessage(sender, String.format("Successfully given all achievements to %s", new Object[] { player.getName() }));
/*  72 */       return true;
/*     */     }
/*     */     
/*  75 */     Achievement achievement = Bukkit.getUnsafe().getAchievementFromInternalName(statisticString);
/*  76 */     Statistic statistic = Bukkit.getUnsafe().getStatisticFromInternalName(statisticString);
/*     */     
/*  78 */     if (achievement != null) {
/*  79 */       if (player.hasAchievement(achievement)) {
/*  80 */         sender.sendMessage(String.format("%s already has achievement %s", new Object[] { player.getName(), statisticString }));
/*  81 */         return true;
/*     */       }
/*     */       
/*  84 */       PlayerAchievementAwardedEvent event = new PlayerAchievementAwardedEvent(player, achievement);
/*  85 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*  86 */       if (event.isCancelled()) {
/*  87 */         sender.sendMessage(String.format("Unable to award %s the achievement %s", new Object[] { player.getName(), statisticString }));
/*  88 */         return true;
/*     */       }
/*  90 */       player.awardAchievement(achievement);
/*     */       
/*  92 */       Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     if (statistic == null) {
/*  97 */       sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     if (statistic.getType() == Statistic.Type.UNTYPED) {
/* 102 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1);
/* 103 */       Bukkit.getServer().getPluginManager().callEvent(event);
/* 104 */       if (event.isCancelled()) {
/* 105 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 106 */         return true;
/*     */       }
/* 108 */       player.incrementStatistic(statistic);
/* 109 */       Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/* 110 */       return true;
/*     */     }
/*     */     
/* 113 */     if (statistic.getType() == Statistic.Type.ENTITY) {
/* 114 */       EntityType entityType = EntityType.fromName(statisticString.substring(statisticString.lastIndexOf(".") + 1));
/*     */       
/* 116 */       if (entityType == null) {
/* 117 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 118 */         return true;
/*     */       }
/*     */       
/* 121 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, entityType);
/* 122 */       Bukkit.getServer().getPluginManager().callEvent(event);
/* 123 */       if (event.isCancelled()) {
/* 124 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 125 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 129 */         player.incrementStatistic(statistic, entityType);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 131 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 132 */         return true;
/*     */       }
/*     */     }
/*     */     else {
/*     */       try {
/* 137 */         id = getInteger(sender, statisticString.substring(statisticString.lastIndexOf(".") + 1), 0, Integer.MAX_VALUE, true);
/*     */       } catch (NumberFormatException e) { int id;
/* 139 */         sender.sendMessage(e.getMessage());
/* 140 */         return true;
/*     */       }
/*     */       int id;
/* 143 */       Material material = Material.getMaterial(id);
/*     */       
/* 145 */       if (material == null) {
/* 146 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 147 */         return true;
/*     */       }
/*     */       
/* 150 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, material);
/* 151 */       Bukkit.getServer().getPluginManager().callEvent(event);
/* 152 */       if (event.isCancelled()) {
/* 153 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 154 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 158 */         player.incrementStatistic(statistic, material);
/*     */       } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 160 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 161 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 165 */     Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/* 166 */     return true;
/*     */   }
/*     */   
/*     */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/* 171 */     Validate.notNull(sender, "Sender cannot be null");
/* 172 */     Validate.notNull(args, "Arguments cannot be null");
/* 173 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 175 */     if (args.length == 1) {
/* 176 */       return java.util.Arrays.asList(new String[] { "give" });
/*     */     }
/*     */     
/* 179 */     if (args.length == 2) {
/* 180 */       return Bukkit.getUnsafe().tabCompleteInternalStatisticOrAchievementName(args[1], new ArrayList());
/*     */     }
/*     */     
/* 183 */     if (args.length == 3) {
/* 184 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 186 */     return com.google.common.collect.ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\AchievementCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */