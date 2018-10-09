package org.bukkit.block;

public abstract interface Sign
  extends BlockState
{
  public abstract String[] getLines();
  
  public abstract String getLine(int paramInt)
    throws IndexOutOfBoundsException;
  
  public abstract void setLine(int paramInt, String paramString)
    throws IndexOutOfBoundsException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Sign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */