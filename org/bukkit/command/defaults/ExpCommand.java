/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class ExpCommand extends VanillaCommand
/*    */ {
/*    */   public ExpCommand()
/*    */   {
/* 17 */     super("xp");
/* 18 */     this.description = "Gives the specified player a certain amount of experience. Specify <amount>L to give levels instead, with a negative amount resulting in taking levels.";
/* 19 */     this.usageMessage = "/xp <amount> [player] OR /xp <amount>L [player]";
/* 20 */     setPermission("bukkit.command.xp");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 25 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 27 */     if (args.length > 0) {
/* 28 */       String inputAmount = args[0];
/* 29 */       Player player = null;
/*    */       
/* 31 */       boolean isLevel = (inputAmount.endsWith("l")) || (inputAmount.endsWith("L"));
/* 32 */       if ((isLevel) && (inputAmount.length() > 1)) {
/* 33 */         inputAmount = inputAmount.substring(0, inputAmount.length() - 1);
/*    */       }
/*    */       
/* 36 */       int amount = getInteger(sender, inputAmount, Integer.MIN_VALUE, Integer.MAX_VALUE);
/* 37 */       boolean isTaking = amount < 0;
/*    */       
/* 39 */       if (isTaking) {
/* 40 */         amount *= -1;
/*    */       }
/*    */       
/* 43 */       if (args.length > 1) {
/* 44 */         player = Bukkit.getPlayer(args[1]);
/* 45 */       } else if ((sender instanceof Player)) {
/* 46 */         player = (Player)sender;
/*    */       }
/*    */       
/* 49 */       if (player != null) {
/* 50 */         if (isLevel) {
/* 51 */           if (isTaking) {
/* 52 */             player.giveExpLevels(-amount);
/* 53 */             Command.broadcastCommandMessage(sender, "Taken " + amount + " level(s) from " + player.getName());
/*    */           } else {
/* 55 */             player.giveExpLevels(amount);
/* 56 */             Command.broadcastCommandMessage(sender, "Given " + amount + " level(s) to " + player.getName());
/*    */           }
/*    */         } else {
/* 59 */           if (isTaking) {
/* 60 */             sender.sendMessage(ChatColor.RED + "Taking experience can only be done by levels, cannot give players negative experience points");
/* 61 */             return false;
/*    */           }
/* 63 */           player.giveExp(amount);
/* 64 */           Command.broadcastCommandMessage(sender, "Given " + amount + " experience to " + player.getName());
/*    */         }
/*    */       }
/*    */       else {
/* 68 */         sender.sendMessage("Can't find player, was one provided?\n" + ChatColor.RED + "Usage: " + this.usageMessage);
/* 69 */         return false;
/*    */       }
/*    */       
/* 72 */       return true;
/*    */     }
/*    */     
/* 75 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 81 */     Validate.notNull(sender, "Sender cannot be null");
/* 82 */     Validate.notNull(args, "Arguments cannot be null");
/* 83 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 85 */     if (args.length == 2) {
/* 86 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 88 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ExpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */