package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.Event.ID;

public abstract interface Parser
{
  public abstract boolean checkEvent(Event.ID paramID);
  
  public abstract Event peekEvent();
  
  public abstract Event getEvent();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\parser\Parser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */