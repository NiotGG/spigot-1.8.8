package org.bukkit;

public abstract interface BlockChangeDelegate
{
  @Deprecated
  public abstract boolean setRawTypeId(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @Deprecated
  public abstract boolean setRawTypeIdAndData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  @Deprecated
  public abstract boolean setTypeId(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @Deprecated
  public abstract boolean setTypeIdAndData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  @Deprecated
  public abstract int getTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getHeight();
  
  public abstract boolean isEmpty(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\BlockChangeDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */