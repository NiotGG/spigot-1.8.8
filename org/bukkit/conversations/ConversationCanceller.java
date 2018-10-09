package org.bukkit.conversations;

public abstract interface ConversationCanceller
  extends Cloneable
{
  public abstract void setConversation(Conversation paramConversation);
  
  public abstract boolean cancelBasedOnInput(ConversationContext paramConversationContext, String paramString);
  
  public abstract ConversationCanceller clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ConversationCanceller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */