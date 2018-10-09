package org.apache.logging.log4j.message;

import java.io.Serializable;

public abstract interface Message
  extends Serializable
{
  public abstract String getFormattedMessage();
  
  public abstract String getFormat();
  
  public abstract Object[] getParameters();
  
  public abstract Throwable getThrowable();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */