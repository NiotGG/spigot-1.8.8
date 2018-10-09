package org.bukkit.entity;

import org.bukkit.inventory.ItemStack;

public abstract interface Item
  extends Entity
{
  public abstract ItemStack getItemStack();
  
  public abstract void setItemStack(ItemStack paramItemStack);
  
  public abstract int getPickupDelay();
  
  public abstract void setPickupDelay(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */