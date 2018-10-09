package gnu.trove.iterator;

public abstract interface TObjectIntIterator<K>
  extends TAdvancingIterator
{
  public abstract K key();
  
  public abstract int value();
  
  public abstract int setValue(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TObjectIntIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */