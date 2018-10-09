package org.bukkit.inventory;

public abstract interface HorseInventory
  extends Inventory
{
  public abstract ItemStack getSaddle();
  
  public abstract ItemStack getArmor();
  
  public abstract void setSaddle(ItemStack paramItemStack);
  
  public abstract void setArmor(ItemStack paramItemStack);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\HorseInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */