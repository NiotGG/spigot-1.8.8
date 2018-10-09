package org.apache.logging.log4j.spi;

import java.util.Map;

public abstract interface ThreadContextMap
{
  public abstract void put(String paramString1, String paramString2);
  
  public abstract String get(String paramString);
  
  public abstract void remove(String paramString);
  
  public abstract void clear();
  
  public abstract boolean containsKey(String paramString);
  
  public abstract Map<String, String> getCopy();
  
  public abstract Map<String, String> getImmutableMapOrNull();
  
  public abstract boolean isEmpty();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\ThreadContextMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */