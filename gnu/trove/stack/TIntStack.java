package gnu.trove.stack;

public abstract interface TIntStack
{
  public abstract int getNoEntryValue();
  
  public abstract void push(int paramInt);
  
  public abstract int pop();
  
  public abstract int peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract int[] toArray();
  
  public abstract void toArray(int[] paramArrayOfInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TIntStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */