/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class SeedCommand extends VanillaCommand
/*    */ {
/*    */   public SeedCommand()
/*    */   {
/* 15 */     super("seed");
/* 16 */     this.description = "Shows the world seed";
/* 17 */     this.usageMessage = "/seed";
/* 18 */     setPermission("bukkit.command.seed");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*    */   {
/* 23 */     if (!testPermission(sender)) return true;
/*    */     long seed;
/* 25 */     long seed; if ((sender instanceof Player)) {
/* 26 */       seed = ((Player)sender).getWorld().getSeed();
/*    */     } else {
/* 28 */       seed = ((World)org.bukkit.Bukkit.getWorlds().get(0)).getSeed();
/*    */     }
/* 30 */     sender.sendMessage("Seed: " + seed);
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 36 */     Validate.notNull(sender, "Sender cannot be null");
/* 37 */     Validate.notNull(args, "Arguments cannot be null");
/* 38 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 40 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SeedCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */