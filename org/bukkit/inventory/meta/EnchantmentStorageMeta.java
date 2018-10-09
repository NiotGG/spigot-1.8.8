package org.bukkit.inventory.meta;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public abstract interface EnchantmentStorageMeta
  extends ItemMeta
{
  public abstract boolean hasStoredEnchants();
  
  public abstract boolean hasStoredEnchant(Enchantment paramEnchantment);
  
  public abstract int getStoredEnchantLevel(Enchantment paramEnchantment);
  
  public abstract Map<Enchantment, Integer> getStoredEnchants();
  
  public abstract boolean addStoredEnchant(Enchantment paramEnchantment, int paramInt, boolean paramBoolean);
  
  public abstract boolean removeStoredEnchant(Enchantment paramEnchantment)
    throws IllegalArgumentException;
  
  public abstract boolean hasConflictingStoredEnchant(Enchantment paramEnchantment);
  
  public abstract EnchantmentStorageMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\EnchantmentStorageMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */