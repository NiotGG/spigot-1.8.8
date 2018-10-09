package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract interface Constraint<E>
{
  public abstract E checkElement(E paramE);
  
  public abstract String toString();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Constraint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */