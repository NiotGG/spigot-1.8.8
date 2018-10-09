package org.bukkit.entity;

import org.bukkit.DyeColor;

public abstract interface Wolf
  extends Animals, Tameable
{
  public abstract boolean isAngry();
  
  public abstract void setAngry(boolean paramBoolean);
  
  public abstract boolean isSitting();
  
  public abstract void setSitting(boolean paramBoolean);
  
  public abstract DyeColor getCollarColor();
  
  public abstract void setCollarColor(DyeColor paramDyeColor);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Wolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */