package net.minecraft.server.v1_8_R3;

import java.util.Random;

public abstract interface IBlockFragilePlantElement
{
  public abstract boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, boolean paramBoolean);
  
  public abstract boolean a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData);
  
  public abstract void b(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IBlockFragilePlantElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */