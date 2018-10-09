package gnu.trove.stack;

public abstract interface TCharStack
{
  public abstract char getNoEntryValue();
  
  public abstract void push(char paramChar);
  
  public abstract char pop();
  
  public abstract char peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract char[] toArray();
  
  public abstract void toArray(char[] paramArrayOfChar);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TCharStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */