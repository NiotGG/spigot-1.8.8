package org.bukkit.entity;

import java.util.Collection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public abstract interface ThrownPotion
  extends Projectile
{
  public abstract Collection<PotionEffect> getEffects();
  
  public abstract ItemStack getItem();
  
  public abstract void setItem(ItemStack paramItemStack);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\ThrownPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */