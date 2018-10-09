package gnu.trove.iterator;

public abstract interface TIntCharIterator
  extends TAdvancingIterator
{
  public abstract int key();
  
  public abstract char value();
  
  public abstract char setValue(char paramChar);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TIntCharIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */