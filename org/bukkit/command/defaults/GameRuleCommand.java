/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.command.BlockCommandSender;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ @Deprecated
/*    */ public class GameRuleCommand extends VanillaCommand
/*    */ {
/* 20 */   private static final List<String> GAMERULE_STATES = ImmutableList.of("true", "false");
/*    */   
/*    */   public GameRuleCommand() {
/* 23 */     super("gamerule");
/* 24 */     this.description = "Sets a server's game rules";
/* 25 */     this.usageMessage = "/gamerule <rule name> <value> OR /gamerule <rule name>";
/* 26 */     setPermission("bukkit.command.gamerule");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 31 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 33 */     if (args.length > 0) {
/* 34 */       String rule = args[0];
/* 35 */       World world = getGameWorld(sender);
/*    */       
/* 37 */       if (world.isGameRule(rule)) {
/* 38 */         if (args.length > 1) {
/* 39 */           String value = args[1];
/*    */           
/* 41 */           world.setGameRuleValue(rule, value);
/* 42 */           org.bukkit.command.Command.broadcastCommandMessage(sender, "Game rule " + rule + " has been set to: " + value);
/*    */         } else {
/* 44 */           String value = world.getGameRuleValue(rule);
/* 45 */           sender.sendMessage(rule + " = " + value);
/*    */         }
/*    */       } else {
/* 48 */         sender.sendMessage(ChatColor.RED + "No game rule called " + rule + " is available");
/*    */       }
/*    */       
/* 51 */       return true;
/*    */     }
/* 53 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 54 */     sender.sendMessage("Rules: " + createString(getGameWorld(sender).getGameRules(), 0, ", "));
/*    */     
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   private World getGameWorld(CommandSender sender)
/*    */   {
/* 61 */     if ((sender instanceof HumanEntity)) {
/* 62 */       World world = ((HumanEntity)sender).getWorld();
/* 63 */       if (world != null) {
/* 64 */         return world;
/*    */       }
/* 66 */     } else if ((sender instanceof BlockCommandSender)) {
/* 67 */       return ((BlockCommandSender)sender).getBlock().getWorld();
/*    */     }
/*    */     
/* 70 */     return (World)Bukkit.getWorlds().get(0);
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 75 */     Validate.notNull(sender, "Sender cannot be null");
/* 76 */     Validate.notNull(args, "Arguments cannot be null");
/* 77 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 79 */     if (args.length == 1) {
/* 80 */       return (List)StringUtil.copyPartialMatches(args[0], Arrays.asList(getGameWorld(sender).getGameRules()), new ArrayList());
/*    */     }
/*    */     
/* 83 */     if (args.length == 2) {
/* 84 */       return (List)StringUtil.copyPartialMatches(args[1], GAMERULE_STATES, new ArrayList(GAMERULE_STATES.size()));
/*    */     }
/*    */     
/* 87 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GameRuleCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */