package gnu.trove.iterator;

public abstract interface TObjectCharIterator<K>
  extends TAdvancingIterator
{
  public abstract K key();
  
  public abstract char value();
  
  public abstract char setValue(char paramChar);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TObjectCharIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */