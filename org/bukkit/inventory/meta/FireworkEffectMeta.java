package org.bukkit.inventory.meta;

import org.bukkit.FireworkEffect;

public abstract interface FireworkEffectMeta
  extends ItemMeta
{
  public abstract void setEffect(FireworkEffect paramFireworkEffect);
  
  public abstract boolean hasEffect();
  
  public abstract FireworkEffect getEffect();
  
  public abstract FireworkEffectMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\FireworkEffectMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */