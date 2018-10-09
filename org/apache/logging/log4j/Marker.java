package org.apache.logging.log4j;

import java.io.Serializable;

public abstract interface Marker
  extends Serializable
{
  public abstract String getName();
  
  public abstract Marker getParent();
  
  public abstract boolean isInstanceOf(Marker paramMarker);
  
  public abstract boolean isInstanceOf(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\Marker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */