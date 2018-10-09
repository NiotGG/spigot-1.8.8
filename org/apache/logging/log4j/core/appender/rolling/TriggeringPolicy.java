package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.LogEvent;

public abstract interface TriggeringPolicy
{
  public abstract void initialize(RollingFileManager paramRollingFileManager);
  
  public abstract boolean isTriggeringEvent(LogEvent paramLogEvent);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\TriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */