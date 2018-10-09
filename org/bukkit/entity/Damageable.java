package org.bukkit.entity;

public abstract interface Damageable
  extends Entity
{
  public abstract void damage(double paramDouble);
  
  public abstract void damage(double paramDouble, Entity paramEntity);
  
  public abstract double getHealth();
  
  public abstract void setHealth(double paramDouble);
  
  public abstract double getMaxHealth();
  
  public abstract void setMaxHealth(double paramDouble);
  
  public abstract void resetMaxHealth();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Damageable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */