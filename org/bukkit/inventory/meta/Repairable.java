package org.bukkit.inventory.meta;

public abstract interface Repairable
{
  public abstract boolean hasRepairCost();
  
  public abstract int getRepairCost();
  
  public abstract void setRepairCost(int paramInt);
  
  public abstract Repairable clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\Repairable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */