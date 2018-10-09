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
/*    */ public class SpawnpointCommand extends VanillaCommand
/*    */ {
/*    */   public SpawnpointCommand()
/*    */   {
/* 19 */     super("spawnpoint");
/* 20 */     this.description = "Sets a player's spawn point";
/* 21 */     this.usageMessage = "/spawnpoint OR /spawnpoint <player> OR /spawnpoint <player> <x> <y> <z>";
/* 22 */     setPermission("bukkit.command.spawnpoint");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 27 */     if (!testPermission(sender)) { return true;
/*    */     }
/*    */     
/*    */     Player player;
/* 31 */     if (args.length == 0) { Player player;
/* 32 */       if ((sender instanceof Player)) {
/* 33 */         player = (Player)sender;
/*    */       } else {
/* 35 */         sender.sendMessage("Please provide a player!");
/* 36 */         return true;
/*    */       }
/*    */     } else {
/* 39 */       player = Bukkit.getPlayerExact(args[0]);
/* 40 */       if (player == null) {
/* 41 */         sender.sendMessage("Can't find player " + args[0]);
/* 42 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 46 */     World world = player.getWorld();
/*    */     
/* 48 */     if (args.length == 4) {
/* 49 */       if (world != null) {
/* 50 */         int pos = 1;
/*    */         try
/*    */         {
/* 53 */           int x = getInteger(sender, args[(pos++)], -30000000, 30000000, true);
/* 54 */           int y = getInteger(sender, args[(pos++)], 0, world.getMaxHeight());
/* 55 */           z = getInteger(sender, args[pos], -30000000, 30000000, true);
/*    */         } catch (NumberFormatException ex) { int z;
/* 57 */           sender.sendMessage(ex.getMessage());
/* 58 */           return true; }
/*    */         int z;
/*    */         int y;
/* 61 */         int x; player.setBedSpawnLocation(new Location(world, x, y, z), true);
/* 62 */         Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + x + ", " + y + ", " + z);
/*    */       }
/* 64 */     } else if (args.length <= 1) {
/* 65 */       Location location = player.getLocation();
/* 66 */       player.setBedSpawnLocation(location, true);
/* 67 */       Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + location.getX() + ", " + location.getY() + ", " + location.getZ());
/*    */     } else {
/* 69 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 70 */       return false;
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
/* 82 */     if (args.length == 1) {
/* 83 */       return super.tabComplete(sender, alias, args);
/*    */     }
/*    */     
/* 86 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SpawnpointCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */