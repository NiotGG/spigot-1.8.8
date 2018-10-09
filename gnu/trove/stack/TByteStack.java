package gnu.trove.stack;

public abstract interface TByteStack
{
  public abstract byte getNoEntryValue();
  
  public abstract void push(byte paramByte);
  
  public abstract byte pop();
  
  public abstract byte peek();
  
  public abstract int size();
  
  public abstract void clear();
  
  public abstract byte[] toArray();
  
  public abstract void toArray(byte[] paramArrayOfByte);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\TByteStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */