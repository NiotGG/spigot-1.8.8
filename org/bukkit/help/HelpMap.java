package org.bukkit.help;

import java.util.Collection;
import java.util.List;

public abstract interface HelpMap
{
  public abstract HelpTopic getHelpTopic(String paramString);
  
  public abstract Collection<HelpTopic> getHelpTopics();
  
  public abstract void addTopic(HelpTopic paramHelpTopic);
  
  public abstract void clear();
  
  public abstract void registerHelpTopicFactory(Class<?> paramClass, HelpTopicFactory<?> paramHelpTopicFactory);
  
  public abstract List<String> getIgnoredPlugins();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */