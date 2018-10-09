package org.bukkit.entity;

public abstract interface Ageable
  extends Creature
{
  public abstract int getAge();
  
  public abstract void setAge(int paramInt);
  
  public abstract void setAgeLock(boolean paramBoolean);
  
  public abstract boolean getAgeLock();
  
  public abstract void setBaby();
  
  public abstract void setAdult();
  
  public abstract boolean isAdult();
  
  public abstract boolean canBreed();
  
  public abstract void setBreed(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Ageable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */