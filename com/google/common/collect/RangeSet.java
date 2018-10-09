package com.google.common.collect;

import com.google.common.annotations.Beta;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract interface RangeSet<C extends Comparable>
{
  public abstract boolean contains(C paramC);
  
  public abstract Range<C> rangeContaining(C paramC);
  
  public abstract boolean encloses(Range<C> paramRange);
  
  public abstract boolean enclosesAll(RangeSet<C> paramRangeSet);
  
  public abstract boolean isEmpty();
  
  public abstract Range<C> span();
  
  public abstract Set<Range<C>> asRanges();
  
  public abstract RangeSet<C> complement();
  
  public abstract RangeSet<C> subRangeSet(Range<C> paramRange);
  
  public abstract void add(Range<C> paramRange);
  
  public abstract void remove(Range<C> paramRange);
  
  public abstract void clear();
  
  public abstract void addAll(RangeSet<C> paramRangeSet);
  
  public abstract void removeAll(RangeSet<C> paramRangeSet);
  
  public abstract boolean equals(@Nullable Object paramObject);
  
  public abstract int hashCode();
  
  public abstract String toString();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RangeSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */