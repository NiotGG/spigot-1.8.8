package gnu.trove.list;

import java.io.Serializable;

public abstract interface TLinkable<T extends TLinkable>
  extends Serializable
{
  public static final long serialVersionUID = 997545054865482562L;
  
  public abstract T getNext();
  
  public abstract T getPrevious();
  
  public abstract void setNext(T paramT);
  
  public abstract void setPrevious(T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\list\TLinkable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */