package org.bukkit.potion;

import java.util.Collection;

public abstract interface PotionBrewer
{
  public abstract PotionEffect createEffect(PotionEffectType paramPotionEffectType, int paramInt1, int paramInt2);
  
  @Deprecated
  public abstract Collection<PotionEffect> getEffectsFromDamage(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */