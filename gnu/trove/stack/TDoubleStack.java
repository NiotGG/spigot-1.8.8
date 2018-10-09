package gnu.trove.stack;

public abstract interface TDoubleStack
{
  public abstract double getNoEntryValue();
  
  public abstract void push(double paramDouble);
  
  public abstract double pop();
  
  public abstract double peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract double[] toArray();
  
  public abstract void toArray(double[] paramArrayOfDouble);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TDoubleStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */