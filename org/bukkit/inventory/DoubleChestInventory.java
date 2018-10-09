package org.bukkit.inventory;

import org.bukkit.block.DoubleChest;

public abstract interface DoubleChestInventory
  extends Inventory
{
  public abstract Inventory getLeftSide();
  
  public abstract Inventory getRightSide();
  
  public abstract DoubleChest getHolder();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\DoubleChestInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */