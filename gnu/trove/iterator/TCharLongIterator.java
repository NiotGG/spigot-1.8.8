package gnu.trove.iterator;

public abstract interface TCharLongIterator
  extends TAdvancingIterator
{
  public abstract char key();
  
  public abstract long value();
  
  public abstract long setValue(long paramLong);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TCharLongIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */