package org.apache.logging.log4j.core.appender.rolling.helper;

import java.io.IOException;

public abstract interface Action
  extends Runnable
{
  public abstract boolean execute()
    throws IOException;
  
  public abstract void close();
  
  public abstract boolean isComplete();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */