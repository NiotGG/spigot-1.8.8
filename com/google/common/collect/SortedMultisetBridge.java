package com.google.common.collect;

import java.util.SortedSet;

abstract interface SortedMultisetBridge<E>
  extends Multiset<E>
{
  public abstract SortedSet<E> elementSet();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedMultisetBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */