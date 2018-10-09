package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;

public abstract interface BrewingStand
  extends BlockState, ContainerBlock
{
  public abstract int getBrewingTime();
  
  public abstract void setBrewingTime(int paramInt);
  
  public abstract BrewerInventory getInventory();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\BrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */