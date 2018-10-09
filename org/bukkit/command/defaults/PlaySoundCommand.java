/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class PlaySoundCommand extends VanillaCommand
/*    */ {
/*    */   public PlaySoundCommand()
/*    */   {
/* 12 */     super("playsound");
/* 13 */     this.description = "Plays a sound to a given player";
/* 14 */     this.usageMessage = "/playsound <sound> <player> [x] [y] [z] [volume] [pitch] [minimumVolume]";
/* 15 */     setPermission("bukkit.command.playsound");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 20 */     if (!testPermission(sender)) {
/* 21 */       return true;
/*    */     }
/*    */     
/* 24 */     if (args.length < 2) {
/* 25 */       sender.sendMessage(org.bukkit.ChatColor.RED + "Usage: " + this.usageMessage);
/* 26 */       return false;
/*    */     }
/* 28 */     String soundArg = args[0];
/* 29 */     String playerArg = args[1];
/*    */     
/* 31 */     Player player = org.bukkit.Bukkit.getPlayerExact(playerArg);
/* 32 */     if (player == null) {
/* 33 */       sender.sendMessage(org.bukkit.ChatColor.RED + "Can't find player " + playerArg);
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     Location location = player.getLocation();
/*    */     
/* 39 */     double x = Math.floor(location.getX());
/* 40 */     double y = Math.floor(location.getY() + 0.5D);
/* 41 */     double z = Math.floor(location.getZ());
/* 42 */     double volume = 1.0D;
/* 43 */     double pitch = 1.0D;
/* 44 */     double minimumVolume = 0.0D;
/*    */     
/* 46 */     switch (args.length) {
/*    */     case 8: 
/*    */     default: 
/* 49 */       minimumVolume = getDouble(sender, args[7], 0.0D, 1.0D);
/*    */     case 7: 
/* 51 */       pitch = getDouble(sender, args[6], 0.0D, 2.0D);
/*    */     case 6: 
/* 53 */       volume = getDouble(sender, args[5], 0.0D, 3.4028234663852886E38D);
/*    */     case 5: 
/* 55 */       z = getRelativeDouble(z, sender, args[4]);
/*    */     case 4: 
/* 57 */       y = getRelativeDouble(y, sender, args[3]);
/*    */     case 3: 
/* 59 */       x = getRelativeDouble(x, sender, args[2]);
/*    */     }
/*    */     
/*    */     
/*    */ 
/* 64 */     double fixedVolume = volume > 1.0D ? volume * 16.0D : 16.0D;
/* 65 */     Location soundLocation = new Location(player.getWorld(), x, y, z);
/* 66 */     if (location.distanceSquared(soundLocation) > fixedVolume * fixedVolume) {
/* 67 */       if (minimumVolume <= 0.0D) {
/* 68 */         sender.sendMessage(org.bukkit.ChatColor.RED + playerArg + " is too far away to hear the sound");
/* 69 */         return false;
/*    */       }
/*    */       
/* 72 */       double deltaX = x - location.getX();
/* 73 */       double deltaY = y - location.getY();
/* 74 */       double deltaZ = z - location.getZ();
/* 75 */       double delta = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / 2.0D;
/*    */       
/* 77 */       if (delta > 0.0D) {
/* 78 */         location.add(deltaX / delta, deltaY / delta, deltaZ / delta);
/*    */       }
/*    */       
/* 81 */       player.playSound(location, soundArg, (float)minimumVolume, (float)pitch);
/*    */     } else {
/* 83 */       player.playSound(soundLocation, soundArg, (float)volume, (float)pitch);
/*    */     }
/* 85 */     sender.sendMessage(String.format("Played '%s' to %s", new Object[] { soundArg, playerArg }));
/* 86 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PlaySoundCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */