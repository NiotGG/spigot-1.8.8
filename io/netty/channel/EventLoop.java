package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

public abstract interface EventLoop
  extends EventExecutor, EventLoopGroup
{
  public abstract EventLoopGroup parent();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\EventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */