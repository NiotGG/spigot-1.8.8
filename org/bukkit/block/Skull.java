package org.bukkit.block;

import org.bukkit.SkullType;

public abstract interface Skull
  extends BlockState
{
  public abstract boolean hasOwner();
  
  public abstract String getOwner();
  
  public abstract boolean setOwner(String paramString);
  
  public abstract BlockFace getRotation();
  
  public abstract void setRotation(BlockFace paramBlockFace);
  
  public abstract SkullType getSkullType();
  
  public abstract void setSkullType(SkullType paramSkullType);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Skull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */