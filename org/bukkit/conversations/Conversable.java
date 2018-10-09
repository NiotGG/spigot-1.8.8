package org.bukkit.conversations;

public abstract interface Conversable
{
  public abstract boolean isConversing();
  
  public abstract void acceptConversationInput(String paramString);
  
  public abstract boolean beginConversation(Conversation paramConversation);
  
  public abstract void abandonConversation(Conversation paramConversation);
  
  public abstract void abandonConversation(Conversation paramConversation, ConversationAbandonedEvent paramConversationAbandonedEvent);
  
  public abstract void sendRawMessage(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\Conversable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */