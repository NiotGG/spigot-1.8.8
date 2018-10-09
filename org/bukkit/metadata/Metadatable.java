package org.bukkit.metadata;

import java.util.List;
import org.bukkit.plugin.Plugin;

public abstract interface Metadatable
{
  public abstract void setMetadata(String paramString, MetadataValue paramMetadataValue);
  
  public abstract List<MetadataValue> getMetadata(String paramString);
  
  public abstract boolean hasMetadata(String paramString);
  
  public abstract void removeMetadata(String paramString, Plugin paramPlugin);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\Metadatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */