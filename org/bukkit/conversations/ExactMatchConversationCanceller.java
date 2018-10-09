/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExactMatchConversationCanceller
/*    */   implements ConversationCanceller
/*    */ {
/*    */   private String escapeSequence;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ExactMatchConversationCanceller(String escapeSequence)
/*    */   {
/* 17 */     this.escapeSequence = escapeSequence;
/*    */   }
/*    */   
/*    */   public void setConversation(Conversation conversation) {}
/*    */   
/*    */   public boolean cancelBasedOnInput(ConversationContext context, String input) {
/* 23 */     return input.equals(this.escapeSequence);
/*    */   }
/*    */   
/*    */   public ConversationCanceller clone() {
/* 27 */     return new ExactMatchConversationCanceller(this.escapeSequence);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ExactMatchConversationCanceller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */