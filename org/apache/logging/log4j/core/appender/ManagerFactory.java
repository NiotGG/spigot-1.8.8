package org.apache.logging.log4j.core.appender;

public abstract interface ManagerFactory<M, T>
{
  public abstract M createManager(String paramString, T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\ManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */