package org.apache.logging.log4j.core;

public abstract interface ErrorHandler
{
  public abstract void error(String paramString);
  
  public abstract void error(String paramString, Throwable paramThrowable);
  
  public abstract void error(String paramString, LogEvent paramLogEvent, Throwable paramThrowable);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\ErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */