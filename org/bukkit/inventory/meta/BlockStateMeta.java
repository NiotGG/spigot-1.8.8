package org.bukkit.inventory.meta;

import org.bukkit.block.BlockState;

public abstract interface BlockStateMeta
  extends ItemMeta
{
  public abstract boolean hasBlockState();
  
  public abstract BlockState getBlockState();
  
  public abstract void setBlockState(BlockState paramBlockState);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\BlockStateMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */