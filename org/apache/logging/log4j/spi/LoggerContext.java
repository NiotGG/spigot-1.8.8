package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;

public abstract interface LoggerContext
{
  public abstract Object getExternalContext();
  
  public abstract Logger getLogger(String paramString);
  
  public abstract Logger getLogger(String paramString, MessageFactory paramMessageFactory);
  
  public abstract boolean hasLogger(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\LoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */