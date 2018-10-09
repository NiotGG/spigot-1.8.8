package org.bukkit.inventory;

public abstract interface EnchantingInventory
  extends Inventory
{
  public abstract void setItem(ItemStack paramItemStack);
  
  public abstract ItemStack getItem();
  
  public abstract void setSecondary(ItemStack paramItemStack);
  
  public abstract ItemStack getSecondary();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\EnchantingInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */