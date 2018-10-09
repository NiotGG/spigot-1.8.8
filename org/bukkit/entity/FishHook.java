package org.bukkit.entity;

public abstract interface FishHook
  extends Projectile
{
  public abstract double getBiteChance();
  
  public abstract void setBiteChance(double paramDouble)
    throws IllegalArgumentException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\FishHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */