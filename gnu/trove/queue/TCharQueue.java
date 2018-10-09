package gnu.trove.queue;

import gnu.trove.TCharCollection;

public abstract interface TCharQueue
  extends TCharCollection
{
  public abstract char element();
  
  public abstract boolean offer(char paramChar);
  
  public abstract char peek();
  
  public abstract char poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TCharQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */