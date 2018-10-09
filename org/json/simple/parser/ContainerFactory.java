package org.json.simple.parser;

import java.util.List;
import java.util.Map;

public abstract interface ContainerFactory
{
  public abstract Map createObjectContainer();
  
  public abstract List creatArrayContainer();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\json\simple\parser\ContainerFactory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */