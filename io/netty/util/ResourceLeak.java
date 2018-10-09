package io.netty.util;

public abstract interface ResourceLeak
{
  public abstract void record();
  
  public abstract boolean close();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\ResourceLeak.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */