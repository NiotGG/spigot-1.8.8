/*    */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.command.MultipleCommandAlias;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ 
/*    */ 
/*    */ public class MultipleCommandAliasHelpTopic
/*    */   extends HelpTopic
/*    */ {
/*    */   private final MultipleCommandAlias alias;
/*    */   
/*    */   public MultipleCommandAliasHelpTopic(MultipleCommandAlias alias)
/*    */   {
/* 18 */     this.alias = alias;
/*    */     
/* 20 */     this.name = ("/" + alias.getLabel());
/*    */     
/*    */ 
/* 23 */     StringBuilder sb = new StringBuilder();
/* 24 */     for (int i = 0; i < alias.getCommands().length; i++) {
/* 25 */       if (i != 0) {
/* 26 */         sb.append(ChatColor.GOLD + " > " + ChatColor.WHITE);
/*    */       }
/* 28 */       sb.append("/");
/* 29 */       sb.append(alias.getCommands()[i].getLabel());
/*    */     }
/* 31 */     this.shortText = sb.toString();
/*    */     
/*    */ 
/* 34 */     this.fullText = (ChatColor.GOLD + "Alias for: " + ChatColor.WHITE + getShortText());
/*    */   }
/*    */   
/*    */   public boolean canSee(CommandSender sender) {
/* 38 */     if (this.amendedPermission == null) {
/* 39 */       if ((sender instanceof ConsoleCommandSender)) {
/* 40 */         return true;
/*    */       }
/*    */       Command[] arrayOfCommand;
/* 43 */       int i = (arrayOfCommand = this.alias.getCommands()).length; for (int j = 0; j < i; j++) { Command command = arrayOfCommand[j];
/* 44 */         if (!command.testPermissionSilent(sender)) {
/* 45 */           return false;
/*    */         }
/*    */       }
/*    */       
/* 49 */       return true;
/*    */     }
/* 51 */     return sender.hasPermission(this.amendedPermission);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\MultipleCommandAliasHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */