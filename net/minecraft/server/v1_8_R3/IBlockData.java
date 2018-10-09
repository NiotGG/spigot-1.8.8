package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;

public abstract interface IBlockData
{
  public abstract Collection<IBlockState> a();
  
  public abstract <T extends Comparable<T>> T get(IBlockState<T> paramIBlockState);
  
  public abstract <T extends Comparable<T>, V extends T> IBlockData set(IBlockState<T> paramIBlockState, V paramV);
  
  public abstract <T extends Comparable<T>> IBlockData a(IBlockState<T> paramIBlockState);
  
  public abstract ImmutableMap<IBlockState, Comparable> b();
  
  public abstract Block getBlock();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IBlockData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */