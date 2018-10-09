/*    */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.help.HelpMap;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ 
/*    */ public class CommandAliasHelpTopic extends HelpTopic
/*    */ {
/*    */   private final String aliasFor;
/*    */   private final HelpMap helpMap;
/*    */   
/*    */   public CommandAliasHelpTopic(String alias, String aliasFor, HelpMap helpMap)
/*    */   {
/* 15 */     this.aliasFor = ("/" + aliasFor);
/* 16 */     this.helpMap = helpMap;
/* 17 */     this.name = ("/" + alias);
/* 18 */     org.apache.commons.lang.Validate.isTrue(!this.name.equals(this.aliasFor), "Command " + this.name + " cannot be alias for itself");
/* 19 */     this.shortText = (ChatColor.YELLOW + "Alias for " + ChatColor.WHITE + this.aliasFor);
/*    */   }
/*    */   
/*    */   public String getFullText(CommandSender forWho)
/*    */   {
/* 24 */     StringBuilder sb = new StringBuilder(this.shortText);
/* 25 */     HelpTopic aliasForTopic = this.helpMap.getHelpTopic(this.aliasFor);
/* 26 */     if (aliasForTopic != null) {
/* 27 */       sb.append("\n");
/* 28 */       sb.append(aliasForTopic.getFullText(forWho));
/*    */     }
/* 30 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public boolean canSee(CommandSender commandSender)
/*    */   {
/* 35 */     if (this.amendedPermission == null) {
/* 36 */       HelpTopic aliasForTopic = this.helpMap.getHelpTopic(this.aliasFor);
/* 37 */       if (aliasForTopic != null) {
/* 38 */         return aliasForTopic.canSee(commandSender);
/*    */       }
/* 40 */       return false;
/*    */     }
/*    */     
/* 43 */     return commandSender.hasPermission(this.amendedPermission);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\CommandAliasHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */