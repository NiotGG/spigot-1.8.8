package org.bukkit;

import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

public abstract interface OfflinePlayer
  extends ServerOperator, AnimalTamer, ConfigurationSerializable
{
  public abstract boolean isOnline();
  
  public abstract String getName();
  
  public abstract UUID getUniqueId();
  
  public abstract boolean isBanned();
  
  @Deprecated
  public abstract void setBanned(boolean paramBoolean);
  
  public abstract boolean isWhitelisted();
  
  public abstract void setWhitelisted(boolean paramBoolean);
  
  public abstract Player getPlayer();
  
  public abstract long getFirstPlayed();
  
  public abstract long getLastPlayed();
  
  public abstract boolean hasPlayedBefore();
  
  public abstract Location getBedSpawnLocation();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\OfflinePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */