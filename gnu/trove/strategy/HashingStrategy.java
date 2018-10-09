package gnu.trove.strategy;

import java.io.Serializable;

public abstract interface HashingStrategy<T>
  extends Serializable
{
  public static final long serialVersionUID = 5674097166776615540L;
  
  public abstract int computeHashCode(T paramT);
  
  public abstract boolean equals(T paramT1, T paramT2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\strategy\HashingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */