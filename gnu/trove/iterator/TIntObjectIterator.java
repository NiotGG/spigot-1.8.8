package gnu.trove.iterator;

public abstract interface TIntObjectIterator<V>
  extends TAdvancingIterator
{
  public abstract int key();
  
  public abstract V value();
  
  public abstract V setValue(V paramV);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TIntObjectIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */