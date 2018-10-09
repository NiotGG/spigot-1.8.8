package io.netty.handler.codec.spdy;

public abstract interface SpdySynStreamFrame
  extends SpdyHeadersFrame
{
  public abstract int associatedStreamId();
  
  public abstract SpdySynStreamFrame setAssociatedStreamId(int paramInt);
  
  public abstract byte priority();
  
  public abstract SpdySynStreamFrame setPriority(byte paramByte);
  
  public abstract boolean isUnidirectional();
  
  public abstract SpdySynStreamFrame setUnidirectional(boolean paramBoolean);
  
  public abstract SpdySynStreamFrame setStreamId(int paramInt);
  
  public abstract SpdySynStreamFrame setLast(boolean paramBoolean);
  
  public abstract SpdySynStreamFrame setInvalid();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdySynStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */