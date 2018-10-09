package gnu.trove.queue;

import gnu.trove.TByteCollection;

public abstract interface TByteQueue
  extends TByteCollection
{
  public abstract byte element();
  
  public abstract boolean offer(byte paramByte);
  
  public abstract byte peek();
  
  public abstract byte poll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\queue\TByteQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */