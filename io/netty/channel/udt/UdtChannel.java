package io.netty.channel.udt;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;

public abstract interface UdtChannel
  extends Channel
{
  public abstract UdtChannelConfig config();
  
  public abstract InetSocketAddress localAddress();
  
  public abstract InetSocketAddress remoteAddress();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\UdtChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */