package gnu.trove.iterator;

public abstract interface TLongShortIterator
  extends TAdvancingIterator
{
  public abstract long key();
  
  public abstract short value();
  
  public abstract short setValue(short paramShort);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TLongShortIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */