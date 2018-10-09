package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public abstract interface CommandSender
  extends Permissible
{
  public abstract void sendMessage(String paramString);
  
  public abstract void sendMessage(String[] paramArrayOfString);
  
  public abstract Server getServer();
  
  public abstract String getName();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\CommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */