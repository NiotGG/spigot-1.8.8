package gnu.trove.queue;

import gnu.trove.TDoubleCollection;

public abstract interface TDoubleQueue
  extends TDoubleCollection
{
  public abstract double element();
  
  public abstract boolean offer(double paramDouble);
  
  public abstract double peek();
  
  public abstract double poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TDoubleQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */