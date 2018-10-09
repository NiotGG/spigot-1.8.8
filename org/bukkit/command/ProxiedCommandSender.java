package org.bukkit.command;

public abstract interface ProxiedCommandSender
  extends CommandSender
{
  public abstract CommandSender getCaller();
  
  public abstract CommandSender getCallee();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\ProxiedCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */