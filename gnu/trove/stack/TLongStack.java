package gnu.trove.stack;

public abstract interface TLongStack
{
  public abstract long getNoEntryValue();
  
  public abstract void push(long paramLong);
  
  public abstract long pop();
  
  public abstract long peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract long[] toArray();
  
  public abstract void toArray(long[] paramArrayOfLong);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TLongStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */