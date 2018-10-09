package net.minecraft.server.v1_8_R3;

public abstract interface IBlockAccess
{
  public abstract TileEntity getTileEntity(BlockPosition paramBlockPosition);
  
  public abstract IBlockData getType(BlockPosition paramBlockPosition);
  
  public abstract boolean isEmpty(BlockPosition paramBlockPosition);
  
  public abstract int getBlockPower(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IBlockAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */