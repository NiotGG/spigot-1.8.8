package org.apache.logging.log4j.core.selector;

import java.net.URI;
import org.apache.logging.log4j.core.LoggerContext;

public abstract interface NamedContextSelector
  extends ContextSelector
{
  public abstract LoggerContext locateContext(String paramString, Object paramObject, URI paramURI);
  
  public abstract LoggerContext removeContext(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\NamedContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */