/*    */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*    */ 
/*    */ import org.bukkit.command.MultipleCommandAlias;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ import org.bukkit.help.HelpTopicFactory;
/*    */ 
/*    */ 
/*    */ public class MultipleCommandAliasHelpTopicFactory
/*    */   implements HelpTopicFactory<MultipleCommandAlias>
/*    */ {
/*    */   public HelpTopic createTopic(MultipleCommandAlias multipleCommandAlias)
/*    */   {
/* 13 */     return new MultipleCommandAliasHelpTopic(multipleCommandAlias);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\MultipleCommandAliasHelpTopicFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */