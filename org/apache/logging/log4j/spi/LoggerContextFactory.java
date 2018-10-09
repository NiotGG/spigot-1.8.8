package org.apache.logging.log4j.spi;

import java.net.URI;

public abstract interface LoggerContextFactory
{
  public abstract LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean);
  
  public abstract LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI);
  
  public abstract void removeContext(LoggerContext paramLoggerContext);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\LoggerContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */