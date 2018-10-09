package org.bukkit.entity.minecart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;

public abstract interface CommandMinecart
  extends Minecart, CommandSender
{
  public abstract String getCommand();
  
  public abstract void setCommand(String paramString);
  
  public abstract void setName(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\minecart\CommandMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */