/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public abstract class PlayerNamePrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   private Plugin plugin;
/*    */   
/*    */   public PlayerNamePrompt(Plugin plugin)
/*    */   {
/* 15 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   protected boolean isInputValid(ConversationContext context, String input)
/*    */   {
/* 20 */     return this.plugin.getServer().getPlayer(input) != null;
/*    */   }
/*    */   
/*    */ 
/*    */   protected Prompt acceptValidatedInput(ConversationContext context, String input)
/*    */   {
/* 26 */     return acceptValidatedInput(context, this.plugin.getServer().getPlayer(input));
/*    */   }
/*    */   
/*    */   protected abstract Prompt acceptValidatedInput(ConversationContext paramConversationContext, Player paramPlayer);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\PlayerNamePrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */