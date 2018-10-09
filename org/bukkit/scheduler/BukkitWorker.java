package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public abstract interface BukkitWorker
{
  public abstract int getTaskId();
  
  public abstract Plugin getOwner();
  
  public abstract Thread getThread();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */