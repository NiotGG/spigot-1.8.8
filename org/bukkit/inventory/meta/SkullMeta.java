package org.bukkit.inventory.meta;

public abstract interface SkullMeta
  extends ItemMeta
{
  public abstract String getOwner();
  
  public abstract boolean hasOwner();
  
  public abstract boolean setOwner(String paramString);
  
  public abstract SkullMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\SkullMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */