package io.netty.channel;

public abstract interface MessageSizeEstimator
{
  public abstract Handle newHandle();
  
  public static abstract interface Handle
  {
    public abstract int size(Object paramObject);
  }
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\MessageSizeEstimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */