package org.apache.logging.log4j.message;

public abstract interface MultiformatMessage
  extends Message
{
  public abstract String getFormattedMessage(String[] paramArrayOfString);
  
  public abstract String[] getFormats();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\MultiformatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */