package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

public abstract interface Furnace
  extends BlockState, ContainerBlock
{
  public abstract short getBurnTime();
  
  public abstract void setBurnTime(short paramShort);
  
  public abstract short getCookTime();
  
  public abstract void setCookTime(short paramShort);
  
  public abstract FurnaceInventory getInventory();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Furnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */