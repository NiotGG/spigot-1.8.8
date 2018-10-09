/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class ToggleDownfallCommand extends VanillaCommand
/*    */ {
/*    */   public ToggleDownfallCommand()
/*    */   {
/* 18 */     super("toggledownfall");
/* 19 */     this.description = "Toggles rain on/off on a given world";
/* 20 */     this.usageMessage = "/toggledownfall";
/* 21 */     setPermission("bukkit.command.toggledownfall");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 26 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 28 */     World world = null;
/*    */     
/* 30 */     if (args.length == 1) {
/* 31 */       world = Bukkit.getWorld(args[0]);
/*    */       
/* 33 */       if (world == null) {
/* 34 */         sender.sendMessage(ChatColor.RED + "No world exists with the name '" + args[0] + "'");
/* 35 */         return true;
/*    */       }
/* 37 */     } else if ((sender instanceof Player)) {
/* 38 */       world = ((Player)sender).getWorld();
/*    */     } else {
/* 40 */       world = (World)Bukkit.getWorlds().get(0);
/*    */     }
/*    */     
/* 43 */     Command.broadcastCommandMessage(sender, "Toggling downfall " + (world.hasStorm() ? "off" : "on") + " for world '" + world.getName() + "'");
/* 44 */     world.setStorm(!world.hasStorm());
/*    */     
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 51 */     Validate.notNull(sender, "Sender cannot be null");
/* 52 */     Validate.notNull(args, "Arguments cannot be null");
/* 53 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 55 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ToggleDownfallCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */