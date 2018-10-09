package org.apache.logging.log4j.status;

import org.apache.logging.log4j.Level;

public abstract interface StatusListener
{
  public abstract void log(StatusData paramStatusData);
  
  public abstract Level getStatusLevel();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */