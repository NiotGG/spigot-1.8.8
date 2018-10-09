package org.bukkit.help;

import org.bukkit.command.Command;

public abstract interface HelpTopicFactory<TCommand extends Command>
{
  public abstract HelpTopic createTopic(TCommand paramTCommand);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpTopicFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */