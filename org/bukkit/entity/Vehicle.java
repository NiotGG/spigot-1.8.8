package org.bukkit.entity;

import org.bukkit.util.Vector;

public abstract interface Vehicle
  extends Entity
{
  public abstract Vector getVelocity();
  
  public abstract void setVelocity(Vector paramVector);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Vehicle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */