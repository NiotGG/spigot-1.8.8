package org.bukkit.entity;

import org.bukkit.inventory.meta.FireworkMeta;

public abstract interface Firework
  extends Entity
{
  public abstract FireworkMeta getFireworkMeta();
  
  public abstract void setFireworkMeta(FireworkMeta paramFireworkMeta);
  
  public abstract void detonate();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Firework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */