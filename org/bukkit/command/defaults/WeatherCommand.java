/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class WeatherCommand extends VanillaCommand
/*    */ {
/* 18 */   private static final List<String> WEATHER_TYPES = ImmutableList.of("clear", "rain", "thunder");
/*    */   
/*    */   public WeatherCommand() {
/* 21 */     super("weather");
/* 22 */     this.description = "Changes the weather";
/* 23 */     this.usageMessage = "/weather <clear/rain/thunder> [duration in seconds]";
/* 24 */     setPermission("bukkit.command.weather");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 29 */     if (!testPermission(sender)) return true;
/* 30 */     if (args.length == 0) {
/* 31 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     int duration = (300 + new Random().nextInt(600)) * 20;
/* 36 */     if (args.length >= 2) {
/* 37 */       duration = getInteger(sender, args[1], 1, 1000000) * 20;
/*    */     }
/*    */     
/* 40 */     World world = (World)Bukkit.getWorlds().get(0);
/*    */     
/* 42 */     world.setWeatherDuration(duration);
/* 43 */     world.setThunderDuration(duration);
/*    */     
/* 45 */     if ("clear".equalsIgnoreCase(args[0])) {
/* 46 */       world.setStorm(false);
/* 47 */       world.setThundering(false);
/* 48 */       Command.broadcastCommandMessage(sender, "Changed weather to clear for " + duration / 20 + " seconds.");
/* 49 */     } else if ("rain".equalsIgnoreCase(args[0])) {
/* 50 */       world.setStorm(true);
/* 51 */       world.setThundering(false);
/* 52 */       Command.broadcastCommandMessage(sender, "Changed weather to rainy for " + duration / 20 + " seconds.");
/* 53 */     } else if ("thunder".equalsIgnoreCase(args[0])) {
/* 54 */       world.setStorm(true);
/* 55 */       world.setThundering(true);
/* 56 */       Command.broadcastCommandMessage(sender, "Changed weather to thundering " + duration / 20 + " seconds.");
/*    */     }
/*    */     
/* 59 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 64 */     Validate.notNull(sender, "Sender cannot be null");
/* 65 */     Validate.notNull(args, "Arguments cannot be null");
/* 66 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 68 */     if (args.length == 1) {
/* 69 */       return (List)StringUtil.copyPartialMatches(args[0], WEATHER_TYPES, new ArrayList(WEATHER_TYPES.size()));
/*    */     }
/*    */     
/* 72 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\WeatherCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */