package gnu.trove.queue;

import gnu.trove.TLongCollection;

public abstract interface TLongQueue
  extends TLongCollection
{
  public abstract long element();
  
  public abstract boolean offer(long paramLong);
  
  public abstract long peek();
  
  public abstract long poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TLongQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */