package net.minecraft.server.v1_8_R3;

public abstract interface ISourceBlock
  extends ILocationSource
{
  public abstract double getX();
  
  public abstract double getY();
  
  public abstract double getZ();
  
  public abstract BlockPosition getBlockPosition();
  
  public abstract int f();
  
  public abstract <T extends TileEntity> T getTileEntity();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ISourceBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */