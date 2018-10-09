package io.netty.bootstrap;

import io.netty.channel.Channel;

public abstract interface ChannelFactory<T extends Channel>
{
  public abstract T newChannel();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\bootstrap\ChannelFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */