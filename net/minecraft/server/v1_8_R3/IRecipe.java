package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.inventory.Recipe;

public abstract interface IRecipe
{
  public abstract boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  public abstract ItemStack craftItem(InventoryCrafting paramInventoryCrafting);
  
  public abstract int a();
  
  public abstract ItemStack b();
  
  public abstract ItemStack[] b(InventoryCrafting paramInventoryCrafting);
  
  public abstract Recipe toBukkitRecipe();
  
  public abstract List<ItemStack> getIngredients();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */