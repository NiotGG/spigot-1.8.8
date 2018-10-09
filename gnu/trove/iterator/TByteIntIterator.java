package gnu.trove.iterator;

public abstract interface TByteIntIterator
  extends TAdvancingIterator
{
  public abstract byte key();
  
  public abstract int value();
  
  public abstract int setValue(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TByteIntIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */