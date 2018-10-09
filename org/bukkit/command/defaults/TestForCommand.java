/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Deprecated
/*    */ public class TestForCommand extends VanillaCommand
/*    */ {
/*    */   public TestForCommand()
/*    */   {
/* 10 */     super("testfor");
/* 11 */     this.description = "Tests whether a specifed player is online";
/* 12 */     this.usageMessage = "/testfor <player>";
/* 13 */     setPermission("bukkit.command.testfor");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 18 */     if (!testPermission(sender)) return true;
/* 19 */     if (args.length < 1) {
/* 20 */       sender.sendMessage(org.bukkit.ChatColor.RED + "Usage: " + this.usageMessage);
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     sender.sendMessage(org.bukkit.ChatColor.RED + "/testfor is only usable by commandblocks with analog output.");
/* 25 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */     throws IllegalArgumentException
/*    */   {
/* 32 */     if (args.length == 0)
/*    */     {
/* 34 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 36 */     return java.util.Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TestForCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */