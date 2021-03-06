package org.bukkit.inventory.meta;

import java.util.List;
import org.bukkit.FireworkEffect;

public abstract interface FireworkMeta
  extends ItemMeta
{
  public abstract void addEffect(FireworkEffect paramFireworkEffect)
    throws IllegalArgumentException;
  
  public abstract void addEffects(FireworkEffect... paramVarArgs)
    throws IllegalArgumentException;
  
  public abstract void addEffects(Iterable<FireworkEffect> paramIterable)
    throws IllegalArgumentException;
  
  public abstract List<FireworkEffect> getEffects();
  
  public abstract int getEffectsSize();
  
  public abstract void removeEffect(int paramInt)
    throws IndexOutOfBoundsException;
  
  public abstract void clearEffects();
  
  public abstract boolean hasEffects();
  
  public abstract int getPower();
  
  public abstract void setPower(int paramInt)
    throws IllegalArgumentException;
  
  public abstract FireworkMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\FireworkMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */