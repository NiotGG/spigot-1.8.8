package org.bukkit.block;

public abstract interface CommandBlock
  extends BlockState
{
  public abstract String getCommand();
  
  public abstract void setCommand(String paramString);
  
  public abstract String getName();
  
  public abstract void setName(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\CommandBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */