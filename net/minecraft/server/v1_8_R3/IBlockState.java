package net.minecraft.server.v1_8_R3;

import java.util.Collection;

public abstract interface IBlockState<T extends Comparable<T>>
{
  public abstract String a();
  
  public abstract Collection<T> c();
  
  public abstract Class<T> b();
  
  public abstract String a(T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IBlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */