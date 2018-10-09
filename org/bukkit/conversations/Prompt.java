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
/*    */ public abstract interface Prompt
/*    */   extends Cloneable
/*    */ {
/* 15 */   public static final Prompt END_OF_CONVERSATION = null;
/*    */   
/*    */   public abstract String getPromptText(ConversationContext paramConversationContext);
/*    */   
/*    */   public abstract boolean blocksForInput(ConversationContext paramConversationContext);
/*    */   
/*    */   public abstract Prompt acceptInput(ConversationContext paramConversationContext, String paramString);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\Prompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */