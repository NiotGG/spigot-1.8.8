/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*     */ 
/*     */ @Deprecated
/*     */ public class TeleportCommand
/*     */   extends VanillaCommand
/*     */ {
/*     */   public TeleportCommand()
/*     */   {
/*  20 */     super("tp");
/*  21 */     this.description = "Teleports the given player (or yourself) to another player or coordinates";
/*  22 */     this.usageMessage = "/tp [player] <target> and/or <x> <y> <z>";
/*  23 */     setPermission("bukkit.command.teleport");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  28 */     if (!testPermission(sender)) return true;
/*  29 */     if ((args.length < 1) || (args.length > 4)) {
/*  30 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  31 */       return false;
/*     */     }
/*     */     
/*     */     Player player;
/*     */     
/*  36 */     if ((args.length == 1) || (args.length == 3)) { Player player;
/*  37 */       if ((sender instanceof Player)) {
/*  38 */         player = (Player)sender;
/*     */       } else {
/*  40 */         sender.sendMessage("Please provide a player!");
/*  41 */         return true;
/*     */       }
/*     */     } else {
/*  44 */       player = Bukkit.getPlayerExact(args[0]);
/*     */     }
/*     */     
/*  47 */     if (player == null) {
/*  48 */       sender.sendMessage("Player not found: " + args[0]);
/*  49 */       return true;
/*     */     }
/*     */     
/*  52 */     if (args.length < 3) {
/*  53 */       Player target = Bukkit.getPlayerExact(args[(args.length - 1)]);
/*  54 */       if (target == null) {
/*  55 */         sender.sendMessage("Can't find player " + args[(args.length - 1)] + ". No tp.");
/*  56 */         return true;
/*     */       }
/*  58 */       player.teleport(target, PlayerTeleportEvent.TeleportCause.COMMAND);
/*  59 */       Command.broadcastCommandMessage(sender, "Teleported " + player.getDisplayName() + " to " + target.getDisplayName());
/*  60 */     } else if (player.getWorld() != null) {
/*  61 */       Location playerLocation = player.getLocation();
/*  62 */       double x = getCoordinate(sender, playerLocation.getX(), args[(args.length - 3)]);
/*  63 */       double y = getCoordinate(sender, playerLocation.getY(), args[(args.length - 2)], 0, 0);
/*  64 */       double z = getCoordinate(sender, playerLocation.getZ(), args[(args.length - 1)]);
/*     */       
/*  66 */       if ((x == -3.0000001E7D) || (y == -3.0000001E7D) || (z == -3.0000001E7D)) {
/*  67 */         sender.sendMessage("Please provide a valid location!");
/*  68 */         return true;
/*     */       }
/*     */       
/*  71 */       playerLocation.setX(x);
/*  72 */       playerLocation.setY(y);
/*  73 */       playerLocation.setZ(z);
/*     */       
/*  75 */       player.teleport(playerLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
/*  76 */       Command.broadcastCommandMessage(sender, String.format("Teleported %s to %.2f, %.2f, %.2f", new Object[] { player.getDisplayName(), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) }));
/*     */     }
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   private double getCoordinate(CommandSender sender, double current, String input) {
/*  82 */     return getCoordinate(sender, current, input, -30000000, 30000000);
/*     */   }
/*     */   
/*     */   private double getCoordinate(CommandSender sender, double current, String input, int min, int max) {
/*  86 */     boolean relative = input.startsWith("~");
/*  87 */     double result = relative ? current : 0.0D;
/*     */     
/*  89 */     if ((!relative) || (input.length() > 1)) {
/*  90 */       boolean exact = input.contains(".");
/*  91 */       if (relative) { input = input.substring(1);
/*     */       }
/*  93 */       double testResult = getDouble(sender, input);
/*  94 */       if (testResult == -3.0000001E7D) {
/*  95 */         return -3.0000001E7D;
/*     */       }
/*  97 */       result += testResult;
/*     */       
/*  99 */       if ((!exact) && (!relative)) result += 0.5D;
/*     */     }
/* 101 */     if ((min != 0) || (max != 0)) {
/* 102 */       if (result < min) {
/* 103 */         result = -3.0000001E7D;
/*     */       }
/*     */       
/* 106 */       if (result > max) {
/* 107 */         result = -3.0000001E7D;
/*     */       }
/*     */     }
/*     */     
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/* 116 */     Validate.notNull(sender, "Sender cannot be null");
/* 117 */     Validate.notNull(args, "Arguments cannot be null");
/* 118 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 120 */     if ((args.length == 1) || (args.length == 2)) {
/* 121 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 123 */     return ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TeleportCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */