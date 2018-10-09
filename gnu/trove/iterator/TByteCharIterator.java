package gnu.trove.iterator;

public abstract interface TByteCharIterator
  extends TAdvancingIterator
{
  public abstract byte key();
  
  public abstract char value();
  
  public abstract char setValue(char paramChar);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\iterator\TByteCharIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */