/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MessagePrompt
/*    */   implements Prompt
/*    */ {
/*    */   public boolean blocksForInput(ConversationContext context)
/*    */   {
/* 20 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Prompt acceptInput(ConversationContext context, String input)
/*    */   {
/* 32 */     return getNextPrompt(context);
/*    */   }
/*    */   
/*    */   protected abstract Prompt getNextPrompt(ConversationContext paramConversationContext);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\MessagePrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */