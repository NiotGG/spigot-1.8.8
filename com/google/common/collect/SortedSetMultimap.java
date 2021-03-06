package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public abstract interface SortedSetMultimap<K, V>
  extends SetMultimap<K, V>
{
  public abstract SortedSet<V> get(@Nullable K paramK);
  
  public abstract SortedSet<V> removeAll(@Nullable Object paramObject);
  
  public abstract SortedSet<V> replaceValues(K paramK, Iterable<? extends V> paramIterable);
  
  public abstract Map<K, Collection<V>> asMap();
  
  public abstract Comparator<? super V> valueComparator();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedSetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */