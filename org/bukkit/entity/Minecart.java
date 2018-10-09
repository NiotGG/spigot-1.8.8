package org.bukkit.entity;

import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public abstract interface Minecart
  extends Vehicle
{
  public abstract void setDamage(double paramDouble);
  
  public abstract double getDamage();
  
  public abstract double getMaxSpeed();
  
  public abstract void setMaxSpeed(double paramDouble);
  
  public abstract boolean isSlowWhenEmpty();
  
  public abstract void setSlowWhenEmpty(boolean paramBoolean);
  
  public abstract Vector getFlyingVelocityMod();
  
  public abstract void setFlyingVelocityMod(Vector paramVector);
  
  public abstract Vector getDerailedVelocityMod();
  
  public abstract void setDerailedVelocityMod(Vector paramVector);
  
  public abstract void setDisplayBlock(MaterialData paramMaterialData);
  
  public abstract MaterialData getDisplayBlock();
  
  public abstract void setDisplayBlockOffset(int paramInt);
  
  public abstract int getDisplayBlockOffset();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Minecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */