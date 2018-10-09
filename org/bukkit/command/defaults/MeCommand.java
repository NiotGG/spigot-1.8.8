/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Deprecated
/*    */ public class MeCommand extends VanillaCommand
/*    */ {
/*    */   public MeCommand()
/*    */   {
/* 10 */     super("me");
/* 11 */     this.description = "Performs the specified action in chat";
/* 12 */     this.usageMessage = "/me <action>";
/* 13 */     setPermission("bukkit.command.me");
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
/* 24 */     StringBuilder message = new StringBuilder();
/* 25 */     message.append(sender.getName());
/*    */     String[] arrayOfString;
/* 27 */     int i = (arrayOfString = args).length; for (int j = 0; j < i; j++) { String arg = arrayOfString[j];
/* 28 */       message.append(" ");
/* 29 */       message.append(arg);
/*    */     }
/*    */     
/* 32 */     org.bukkit.Bukkit.broadcastMessage("* " + message.toString());
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\MeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */