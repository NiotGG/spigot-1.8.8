package org.apache.logging.log4j.core;

import java.io.Serializable;
import java.util.Map;

public abstract interface Layout<T extends Serializable>
{
  public abstract byte[] getFooter();
  
  public abstract byte[] getHeader();
  
  public abstract byte[] toByteArray(LogEvent paramLogEvent);
  
  public abstract T toSerializable(LogEvent paramLogEvent);
  
  public abstract String getContentType();
  
  public abstract Map<String, String> getContentFormat();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\Layout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */