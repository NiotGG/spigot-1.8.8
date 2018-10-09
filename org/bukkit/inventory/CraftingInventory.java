package org.bukkit.inventory;

public abstract interface CraftingInventory
  extends Inventory
{
  public abstract ItemStack getResult();
  
  public abstract ItemStack[] getMatrix();
  
  public abstract void setResult(ItemStack paramItemStack);
  
  public abstract void setMatrix(ItemStack[] paramArrayOfItemStack);
  
  public abstract Recipe getRecipe();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\CraftingInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */