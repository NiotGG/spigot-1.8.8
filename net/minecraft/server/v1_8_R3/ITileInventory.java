package net.minecraft.server.v1_8_R3;

public abstract interface ITileInventory
  extends IInventory, ITileEntityContainer
{
  public abstract boolean r_();
  
  public abstract void a(ChestLock paramChestLock);
  
  public abstract ChestLock i();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ITileInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */