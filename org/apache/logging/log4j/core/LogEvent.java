package org.apache.logging.log4j.core;

import java.io.Serializable;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.message.Message;

public abstract interface LogEvent
  extends Serializable
{
  public abstract Level getLevel();
  
  public abstract String getLoggerName();
  
  public abstract StackTraceElement getSource();
  
  public abstract Message getMessage();
  
  public abstract Marker getMarker();
  
  public abstract String getThreadName();
  
  public abstract long getMillis();
  
  public abstract Throwable getThrown();
  
  public abstract Map<String, String> getContextMap();
  
  public abstract ThreadContext.ContextStack getContextStack();
  
  public abstract String getFQCN();
  
  public abstract boolean isIncludeLocation();
  
  public abstract void setIncludeLocation(boolean paramBoolean);
  
  public abstract boolean isEndOfBatch();
  
  public abstract void setEndOfBatch(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\LogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */