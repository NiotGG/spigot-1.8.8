package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public abstract interface FileRegion
  extends ReferenceCounted
{
  public abstract long position();
  
  public abstract long transfered();
  
  public abstract long count();
  
  public abstract long transferTo(WritableByteChannel paramWritableByteChannel, long paramLong)
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\FileRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */