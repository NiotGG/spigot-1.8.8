package org.apache.logging.log4j.core;

public abstract interface LifeCycle
{
  public abstract void start();
  
  public abstract void stop();
  
  public abstract boolean isStarted();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\LifeCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */