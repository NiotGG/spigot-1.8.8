package org.bukkit.block;

import org.bukkit.Material;

public abstract interface Jukebox
  extends BlockState
{
  public abstract Material getPlaying();
  
  public abstract void setPlaying(Material paramMaterial);
  
  public abstract boolean isPlaying();
  
  public abstract boolean eject();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Jukebox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */