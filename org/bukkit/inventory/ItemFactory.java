package org.bukkit.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public abstract interface ItemFactory
{
  public abstract ItemMeta getItemMeta(Material paramMaterial);
  
  public abstract boolean isApplicable(ItemMeta paramItemMeta, ItemStack paramItemStack)
    throws IllegalArgumentException;
  
  public abstract boolean isApplicable(ItemMeta paramItemMeta, Material paramMaterial)
    throws IllegalArgumentException;
  
  public abstract boolean equals(ItemMeta paramItemMeta1, ItemMeta paramItemMeta2)
    throws IllegalArgumentException;
  
  public abstract ItemMeta asMetaFor(ItemMeta paramItemMeta, ItemStack paramItemStack)
    throws IllegalArgumentException;
  
  public abstract ItemMeta asMetaFor(ItemMeta paramItemMeta, Material paramMaterial)
    throws IllegalArgumentException;
  
  public abstract Color getDefaultLeatherColor();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ItemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */