package org.bukkit.inventory;

import org.bukkit.block.BrewingStand;

public abstract interface BrewerInventory
  extends Inventory
{
  public abstract ItemStack getIngredient();
  
  public abstract void setIngredient(ItemStack paramItemStack);
  
  public abstract BrewingStand getHolder();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\BrewerInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */