/*    */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ 
/*    */ public class CustomHelpTopic
/*    */   extends HelpTopic
/*    */ {
/*    */   private final String permissionNode;
/*    */   
/*    */   public CustomHelpTopic(String name, String shortText, String fullText, String permissionNode)
/*    */   {
/* 14 */     this.permissionNode = permissionNode;
/* 15 */     this.name = name;
/* 16 */     this.shortText = shortText;
/* 17 */     this.fullText = (shortText + "\n" + fullText);
/*    */   }
/*    */   
/*    */   public boolean canSee(CommandSender sender) {
/* 21 */     if ((sender instanceof ConsoleCommandSender)) {
/* 22 */       return true;
/*    */     }
/*    */     
/* 25 */     if (!this.permissionNode.equals("")) {
/* 26 */       return sender.hasPermission(this.permissionNode);
/*    */     }
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\CustomHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */