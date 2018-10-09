package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public abstract interface RecvByteBufAllocator
{
  public abstract Handle newHandle();
  
  public static abstract interface Handle
  {
    public abstract ByteBuf allocate(ByteBufAllocator paramByteBufAllocator);
    
    public abstract int guess();
    
    public abstract void record(int paramInt);
  }
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\RecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */