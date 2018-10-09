package org.bukkit.entity;

import org.bukkit.Rotation;
import org.bukkit.inventory.ItemStack;

public abstract interface ItemFrame
  extends Hanging
{
  public abstract ItemStack getItem();
  
  public abstract void setItem(ItemStack paramItemStack);
  
  public abstract Rotation getRotation();
  
  public abstract void setRotation(Rotation paramRotation)
    throws IllegalArgumentException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\ItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */