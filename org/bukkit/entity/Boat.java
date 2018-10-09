package org.bukkit.entity;

public abstract interface Boat
  extends Vehicle
{
  public abstract double getMaxSpeed();
  
  public abstract void setMaxSpeed(double paramDouble);
  
  public abstract double getOccupiedDeceleration();
  
  public abstract void setOccupiedDeceleration(double paramDouble);
  
  public abstract double getUnoccupiedDeceleration();
  
  public abstract void setUnoccupiedDeceleration(double paramDouble);
  
  public abstract boolean getWorkOnLand();
  
  public abstract void setWorkOnLand(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Boat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */