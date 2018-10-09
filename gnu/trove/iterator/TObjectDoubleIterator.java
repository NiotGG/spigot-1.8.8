package gnu.trove.iterator;

public abstract interface TObjectDoubleIterator<K>
  extends TAdvancingIterator
{
  public abstract K key();
  
  public abstract double value();
  
  public abstract double setValue(double paramDouble);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TObjectDoubleIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */