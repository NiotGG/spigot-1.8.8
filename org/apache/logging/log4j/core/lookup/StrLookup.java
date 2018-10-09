package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.LogEvent;

public abstract interface StrLookup
{
  public abstract String lookup(String paramString);
  
  public abstract String lookup(LogEvent paramLogEvent, String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\StrLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */