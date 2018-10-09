package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public abstract interface ElComparator<T>
  extends Comparator<T>
{
  public abstract int compare(T paramT1, T paramT2);
  
  public abstract int compareValue(Object paramObject, T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */