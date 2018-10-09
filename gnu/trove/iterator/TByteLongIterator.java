package gnu.trove.iterator;

public abstract interface TByteLongIterator
  extends TAdvancingIterator
{
  public abstract byte key();
  
  public abstract long value();
  
  public abstract long setValue(long paramLong);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TByteLongIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */