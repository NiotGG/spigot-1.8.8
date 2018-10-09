package org.bukkit.entity;

import org.bukkit.Material;

public abstract interface FallingBlock
  extends Entity
{
  public abstract Material getMaterial();
  
  @Deprecated
  public abstract int getBlockId();
  
  @Deprecated
  public abstract byte getBlockData();
  
  public abstract boolean getDropItem();
  
  public abstract void setDropItem(boolean paramBoolean);
  
  public abstract boolean canHurtEntities();
  
  public abstract void setHurtEntities(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\FallingBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */