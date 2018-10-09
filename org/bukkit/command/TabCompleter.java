package org.bukkit.command;

import java.util.List;

public abstract interface TabCompleter
{
  public abstract List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\TabCompleter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */