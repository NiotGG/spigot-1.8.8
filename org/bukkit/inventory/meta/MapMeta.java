package org.bukkit.inventory.meta;

public abstract interface MapMeta
  extends ItemMeta
{
  public abstract boolean isScaling();
  
  public abstract void setScaling(boolean paramBoolean);
  
  public abstract MapMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\MapMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */