/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ @Deprecated
/*    */ public class KillCommand extends VanillaCommand
/*    */ {
/*    */   public KillCommand()
/*    */   {
/* 16 */     super("kill");
/* 17 */     this.description = "Commits suicide, only usable as a player";
/* 18 */     this.usageMessage = "/kill";
/* 19 */     setPermission("bukkit.command.kill");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 24 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 26 */     if ((sender instanceof Player)) {
/* 27 */       Player player = (Player)sender;
/*    */       
/* 29 */       EntityDamageEvent ede = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 1000);
/* 30 */       org.bukkit.Bukkit.getPluginManager().callEvent(ede);
/* 31 */       if (ede.isCancelled()) { return true;
/*    */       }
/* 33 */       ede.getEntity().setLastDamageCause(ede);
/* 34 */       player.setHealth(0.0D);
/* 35 */       sender.sendMessage("Ouch. That look like it hurt.");
/*    */     } else {
/* 37 */       sender.sendMessage("You can only perform this command as a player");
/*    */     }
/*    */     
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 45 */     Validate.notNull(sender, "Sender cannot be null");
/* 46 */     Validate.notNull(args, "Arguments cannot be null");
/* 47 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 49 */     return com.google.common.collect.ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\KillCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */