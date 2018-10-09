package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map.Entry;

@GwtCompatible
abstract interface FilteredMultimap<K, V>
  extends Multimap<K, V>
{
  public abstract Multimap<K, V> unfiltered();
  
  public abstract Predicate<? super Map.Entry<K, V>> entryPredicate();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\FilteredMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */