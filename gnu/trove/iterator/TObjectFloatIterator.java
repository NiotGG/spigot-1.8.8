package gnu.trove.iterator;

public abstract interface TObjectFloatIterator<K>
  extends TAdvancingIterator
{
  public abstract K key();
  
  public abstract float value();
  
  public abstract float setValue(float paramFloat);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TObjectFloatIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */