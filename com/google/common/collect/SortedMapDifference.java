package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.SortedMap;

@GwtCompatible
public abstract interface SortedMapDifference<K, V>
  extends MapDifference<K, V>
{
  public abstract SortedMap<K, V> entriesOnlyOnLeft();
  
  public abstract SortedMap<K, V> entriesOnlyOnRight();
  
  public abstract SortedMap<K, V> entriesInCommon();
  
  public abstract SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedMapDifference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */