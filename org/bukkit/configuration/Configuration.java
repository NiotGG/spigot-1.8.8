package org.bukkit.configuration;

import java.util.Map;

public abstract interface Configuration
  extends ConfigurationSection
{
  public abstract void addDefault(String paramString, Object paramObject);
  
  public abstract void addDefaults(Map<String, Object> paramMap);
  
  public abstract void addDefaults(Configuration paramConfiguration);
  
  public abstract void setDefaults(Configuration paramConfiguration);
  
  public abstract Configuration getDefaults();
  
  public abstract ConfigurationOptions options();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */