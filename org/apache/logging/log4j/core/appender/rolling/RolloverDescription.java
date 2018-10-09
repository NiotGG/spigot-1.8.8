package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.appender.rolling.helper.Action;

public abstract interface RolloverDescription
{
  public abstract String getActiveFileName();
  
  public abstract boolean getAppend();
  
  public abstract Action getSynchronous();
  
  public abstract Action getAsynchronous();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */