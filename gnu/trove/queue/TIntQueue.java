package gnu.trove.queue;

import gnu.trove.TIntCollection;

public abstract interface TIntQueue
  extends TIntCollection
{
  public abstract int element();
  
  public abstract boolean offer(int paramInt);
  
  public abstract int peek();
  
  public abstract int poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TIntQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */