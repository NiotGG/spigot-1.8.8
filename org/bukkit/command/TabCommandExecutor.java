package org.bukkit.command;

import java.util.List;

@Deprecated
public abstract interface TabCommandExecutor
  extends CommandExecutor
{
  public abstract List<String> onTabComplete();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\TabCommandExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */