package org.bukkit.entity;

import org.bukkit.block.BlockFace;
import org.bukkit.material.Attachable;

public abstract interface Hanging
  extends Entity, Attachable
{
  public abstract boolean setFacingDirection(BlockFace paramBlockFace, boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Hanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */