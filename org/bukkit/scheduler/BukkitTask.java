package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public abstract interface BukkitTask
{
  public abstract int getTaskId();
  
  public abstract Plugin getOwner();
  
  public abstract boolean isSync();
  
  public abstract void cancel();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */