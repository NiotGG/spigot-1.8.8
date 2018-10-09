package org.bukkit.inventory.meta;

import org.bukkit.Color;

public abstract interface LeatherArmorMeta
  extends ItemMeta
{
  public abstract Color getColor();
  
  public abstract void setColor(Color paramColor);
  
  public abstract LeatherArmorMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\LeatherArmorMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */