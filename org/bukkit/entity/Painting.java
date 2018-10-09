package org.bukkit.entity;

import org.bukkit.Art;

public abstract interface Painting
  extends Hanging
{
  public abstract Art getArt();
  
  public abstract boolean setArt(Art paramArt);
  
  public abstract boolean setArt(Art paramArt, boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Painting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */