package gnu.trove.iterator;

public abstract interface TCharObjectIterator<V>
  extends TAdvancingIterator
{
  public abstract char key();
  
  public abstract V value();
  
  public abstract V setValue(V paramV);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TCharObjectIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */