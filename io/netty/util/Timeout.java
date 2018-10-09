package io.netty.util;

public abstract interface Timeout
{
  public abstract Timer timer();
  
  public abstract TimerTask task();
  
  public abstract boolean isExpired();
  
  public abstract boolean isCancelled();
  
  public abstract boolean cancel();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\Timeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */