package gnu.trove.stack;

public abstract interface TShortStack
{
  public abstract short getNoEntryValue();
  
  public abstract void push(short paramShort);
  
  public abstract short pop();
  
  public abstract short peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract short[] toArray();
  
  public abstract void toArray(short[] paramArrayOfShort);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TShortStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */