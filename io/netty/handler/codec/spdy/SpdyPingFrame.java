package io.netty.handler.codec.spdy;

public abstract interface SpdyPingFrame
  extends SpdyFrame
{
  public abstract int id();
  
  public abstract SpdyPingFrame setId(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyPingFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */