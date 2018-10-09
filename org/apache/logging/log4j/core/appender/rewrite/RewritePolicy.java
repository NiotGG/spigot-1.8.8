package org.apache.logging.log4j.core.appender.rewrite;

import org.apache.logging.log4j.core.LogEvent;

public abstract interface RewritePolicy
{
  public abstract LogEvent rewrite(LogEvent paramLogEvent);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rewrite\RewritePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */