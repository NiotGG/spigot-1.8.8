package io.netty.handler.codec.spdy;

public abstract interface SpdyGoAwayFrame
  extends SpdyFrame
{
  public abstract int lastGoodStreamId();
  
  public abstract SpdyGoAwayFrame setLastGoodStreamId(int paramInt);
  
  public abstract SpdySessionStatus status();
  
  public abstract SpdyGoAwayFrame setStatus(SpdySessionStatus paramSpdySessionStatus);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyGoAwayFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */