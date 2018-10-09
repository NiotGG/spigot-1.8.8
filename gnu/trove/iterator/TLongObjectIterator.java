package gnu.trove.iterator;

public abstract interface TLongObjectIterator<V>
  extends TAdvancingIterator
{
  public abstract long key();
  
  public abstract V value();
  
  public abstract V setValue(V paramV);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TLongObjectIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */