package gnu.trove.queue;

import gnu.trove.TShortCollection;

public abstract interface TShortQueue
  extends TShortCollection
{
  public abstract short element();
  
  public abstract boolean offer(short paramShort);
  
  public abstract short peek();
  
  public abstract short poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TShortQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */