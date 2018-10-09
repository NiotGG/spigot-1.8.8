package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public abstract interface PluginMessageRecipient
{
  public abstract void sendPluginMessage(Plugin paramPlugin, String paramString, byte[] paramArrayOfByte);
  
  public abstract Set<String> getListeningPluginChannels();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\PluginMessageRecipient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */