package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;

public abstract interface PluginMessageListener
{
  public abstract void onPluginMessageReceived(String paramString, Player paramPlayer, byte[] paramArrayOfByte);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\PluginMessageListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */