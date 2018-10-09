package org.apache.logging.log4j.message;

public abstract interface MessageFactory
{
  public abstract Message newMessage(Object paramObject);
  
  public abstract Message newMessage(String paramString);
  
  public abstract Message newMessage(String paramString, Object... paramVarArgs);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\MessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */