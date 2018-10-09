package org.bukkit.entity;

import org.bukkit.util.Vector;

public abstract interface Fireball
  extends Projectile, Explosive
{
  public abstract void setDirection(Vector paramVector);
  
  public abstract Vector getDirection();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Fireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */