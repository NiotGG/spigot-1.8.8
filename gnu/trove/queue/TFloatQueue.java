package gnu.trove.queue;

import gnu.trove.TFloatCollection;

public abstract interface TFloatQueue
  extends TFloatCollection
{
  public abstract float element();
  
  public abstract boolean offer(float paramFloat);
  
  public abstract float peek();
  
  public abstract float poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TFloatQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */