package org.bukkit.plugin;

import com.avaje.ebean.EbeanServer;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;

public abstract interface Plugin
  extends TabExecutor
{
  public abstract File getDataFolder();
  
  public abstract PluginDescriptionFile getDescription();
  
  public abstract FileConfiguration getConfig();
  
  public abstract InputStream getResource(String paramString);
  
  public abstract void saveConfig();
  
  public abstract void saveDefaultConfig();
  
  public abstract void saveResource(String paramString, boolean paramBoolean);
  
  public abstract void reloadConfig();
  
  public abstract PluginLoader getPluginLoader();
  
  public abstract Server getServer();
  
  public abstract boolean isEnabled();
  
  public abstract void onDisable();
  
  public abstract void onLoad();
  
  public abstract void onEnable();
  
  public abstract boolean isNaggable();
  
  public abstract void setNaggable(boolean paramBoolean);
  
  public abstract EbeanServer getDatabase();
  
  public abstract ChunkGenerator getDefaultWorldGenerator(String paramString1, String paramString2);
  
  public abstract Logger getLogger();
  
  public abstract String getName();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\Plugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */