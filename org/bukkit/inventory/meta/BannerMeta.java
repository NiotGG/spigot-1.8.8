package org.bukkit.inventory.meta;

import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;

public abstract interface BannerMeta
  extends ItemMeta
{
  public abstract DyeColor getBaseColor();
  
  public abstract void setBaseColor(DyeColor paramDyeColor);
  
  public abstract List<Pattern> getPatterns();
  
  public abstract void setPatterns(List<Pattern> paramList);
  
  public abstract void addPattern(Pattern paramPattern);
  
  public abstract Pattern getPattern(int paramInt);
  
  public abstract Pattern removePattern(int paramInt);
  
  public abstract void setPattern(int paramInt, Pattern paramPattern);
  
  public abstract int numberOfPatterns();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\BannerMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */