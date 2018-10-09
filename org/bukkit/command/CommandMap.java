package org.bukkit.command;

import java.util.List;

public abstract interface CommandMap
{
  public abstract void registerAll(String paramString, List<Command> paramList);
  
  public abstract boolean register(String paramString1, String paramString2, Command paramCommand);
  
  public abstract boolean register(String paramString, Command paramCommand);
  
  public abstract boolean dispatch(CommandSender paramCommandSender, String paramString)
    throws CommandException;
  
  public abstract void clearCommands();
  
  public abstract Command getCommand(String paramString);
  
  public abstract List<String> tabComplete(CommandSender paramCommandSender, String paramString)
    throws IllegalArgumentException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\CommandMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */