package org.bukkit.inventory;

import org.bukkit.block.Furnace;

public abstract interface FurnaceInventory
  extends Inventory
{
  public abstract ItemStack getResult();
  
  public abstract ItemStack getFuel();
  
  public abstract ItemStack getSmelting();
  
  public abstract void setFuel(ItemStack paramItemStack);
  
  public abstract void setResult(ItemStack paramItemStack);
  
  public abstract void setSmelting(ItemStack paramItemStack);
  
  public abstract Furnace getHolder();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\FurnaceInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */