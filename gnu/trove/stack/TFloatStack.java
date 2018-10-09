package gnu.trove.stack;

public abstract interface TFloatStack
{
  public abstract float getNoEntryValue();
  
  public abstract void push(float paramFloat);
  
  public abstract float pop();
  
  public abstract float peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract float[] toArray();
  
  public abstract void toArray(float[] paramArrayOfFloat);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TFloatStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */