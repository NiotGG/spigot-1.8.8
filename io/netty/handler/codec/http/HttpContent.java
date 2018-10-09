package io.netty.handler.codec.http;

import io.netty.buffer.ByteBufHolder;

public abstract interface HttpContent
  extends HttpObject, ByteBufHolder
{
  public abstract HttpContent copy();
  
  public abstract HttpContent duplicate();
  
  public abstract HttpContent retain();
  
  public abstract HttpContent retain(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */