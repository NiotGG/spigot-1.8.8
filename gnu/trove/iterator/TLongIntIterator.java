package gnu.trove.iterator;

public abstract interface TLongIntIterator
  extends TAdvancingIterator
{
  public abstract long key();
  
  public abstract int value();
  
  public abstract int setValue(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TLongIntIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */