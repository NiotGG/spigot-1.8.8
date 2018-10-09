package org.bukkit.entity;

public abstract interface Tameable
{
  public abstract boolean isTamed();
  
  public abstract void setTamed(boolean paramBoolean);
  
  public abstract AnimalTamer getOwner();
  
  public abstract void setOwner(AnimalTamer paramAnimalTamer);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Tameable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */