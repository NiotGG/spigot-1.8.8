package org.bukkit.map;

import java.awt.Image;

public abstract interface MapCanvas
{
  public abstract MapView getMapView();
  
  public abstract MapCursorCollection getCursors();
  
  public abstract void setCursors(MapCursorCollection paramMapCursorCollection);
  
  public abstract void setPixel(int paramInt1, int paramInt2, byte paramByte);
  
  public abstract byte getPixel(int paramInt1, int paramInt2);
  
  public abstract byte getBasePixel(int paramInt1, int paramInt2);
  
  public abstract void drawImage(int paramInt1, int paramInt2, Image paramImage);
  
  public abstract void drawText(int paramInt1, int paramInt2, MapFont paramMapFont, String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapCanvas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */