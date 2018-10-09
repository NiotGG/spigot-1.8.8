package org.apache.logging.log4j.core.appender.rolling;

public abstract interface RolloverStrategy
{
  public abstract RolloverDescription rollover(RollingFileManager paramRollingFileManager)
    throws SecurityException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */