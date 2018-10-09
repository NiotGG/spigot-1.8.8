package org.apache.logging.log4j.core;

import java.io.Serializable;

public abstract interface Appender
  extends LifeCycle
{
  public abstract void append(LogEvent paramLogEvent);
  
  public abstract String getName();
  
  public abstract Layout<? extends Serializable> getLayout();
  
  public abstract boolean ignoreExceptions();
  
  public abstract ErrorHandler getHandler();
  
  public abstract void setHandler(ErrorHandler paramErrorHandler);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\Appender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */