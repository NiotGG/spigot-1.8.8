/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class SetWorldSpawnCommand extends VanillaCommand
/*    */ {
/*    */   public SetWorldSpawnCommand()
/*    */   {
/* 19 */     super("setworldspawn");
/* 20 */     this.description = "Sets a worlds's spawn point. If no coordinates are specified, the player's coordinates will be used.";
/* 21 */     this.usageMessage = "/setworldspawn OR /setworldspawn <x> <y> <z>";
/* 22 */     setPermission("bukkit.command.setworldspawn");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 27 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 29 */     Player player = null;
/*    */     World world;
/* 31 */     World world; if ((sender instanceof Player)) {
/* 32 */       player = (Player)sender;
/* 33 */       world = player.getWorld();
/*    */     } else {
/* 35 */       world = (World)Bukkit.getWorlds().get(0);
/*    */     }
/*    */     
/*    */     int z;
/*    */     
/* 40 */     if (args.length == 0) {
/* 41 */       if (player == null) {
/* 42 */         sender.sendMessage("You can only perform this command as a player");
/* 43 */         return true;
/*    */       }
/*    */       
/* 46 */       Location location = player.getLocation();
/*    */       
/* 48 */       int x = location.getBlockX();
/* 49 */       int y = location.getBlockY();
/* 50 */       z = location.getBlockZ();
/* 51 */     } else if (args.length == 3) {
/*    */       try {
/* 53 */         int x = getInteger(sender, args[0], -30000000, 30000000, true);
/* 54 */         int y = getInteger(sender, args[1], 0, world.getMaxHeight(), true);
/* 55 */         z = getInteger(sender, args[2], -30000000, 30000000, true);
/*    */       } catch (NumberFormatException ex) { int z;
/* 57 */         sender.sendMessage(ex.getMessage());
/* 58 */         return true;
/*    */       }
/*    */     } else {
/* 61 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 62 */       return false; }
/*    */     int z;
/*    */     int y;
/* 65 */     int x; world.setSpawnLocation(x, y, z);
/*    */     
/* 67 */     Command.broadcastCommandMessage(sender, "Set world " + world.getName() + "'s spawnpoint to (" + x + ", " + y + ", " + z + ")");
/* 68 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 74 */     Validate.notNull(sender, "Sender cannot be null");
/* 75 */     Validate.notNull(args, "Arguments cannot be null");
/* 76 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 78 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SetWorldSpawnCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */