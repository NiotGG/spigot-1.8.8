package gnu.trove.iterator;

public abstract interface TObjectByteIterator<K>
  extends TAdvancingIterator
{
  public abstract K key();
  
  public abstract byte value();
  
  public abstract byte setValue(byte paramByte);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TObjectByteIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */