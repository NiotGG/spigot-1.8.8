package org.bukkit.metadata;

import java.util.List;
import org.bukkit.plugin.Plugin;

public abstract interface MetadataStore<T>
{
  public abstract void setMetadata(T paramT, String paramString, MetadataValue paramMetadataValue);
  
  public abstract List<MetadataValue> getMetadata(T paramT, String paramString);
  
  public abstract boolean hasMetadata(T paramT, String paramString);
  
  public abstract void removeMetadata(T paramT, String paramString, Plugin paramPlugin);
  
  public abstract void invalidateAll(Plugin paramPlugin);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\MetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */