package io.netty.handler.codec.spdy;

public abstract interface SpdyStreamFrame
  extends SpdyFrame
{
  public abstract int streamId();
  
  public abstract SpdyStreamFrame setStreamId(int paramInt);
  
  public abstract boolean isLast();
  
  public abstract SpdyStreamFrame setLast(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */