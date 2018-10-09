package io.netty.handler.stream;

import io.netty.channel.ChannelHandlerContext;

public abstract interface ChunkedInput<B>
{
  public abstract boolean isEndOfInput()
    throws Exception;
  
  public abstract void close()
    throws Exception;
  
  public abstract B readChunk(ChannelHandlerContext paramChannelHandlerContext)
    throws Exception;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */