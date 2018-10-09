package org.bukkit.plugin;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract interface PluginLoader
{
  public abstract Plugin loadPlugin(File paramFile)
    throws InvalidPluginException, UnknownDependencyException;
  
  public abstract PluginDescriptionFile getPluginDescription(File paramFile)
    throws InvalidDescriptionException;
  
  public abstract Pattern[] getPluginFileFilters();
  
  public abstract Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener paramListener, Plugin paramPlugin);
  
  public abstract void enablePlugin(Plugin paramPlugin);
  
  public abstract void disablePlugin(Plugin paramPlugin);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */