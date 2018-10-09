package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public abstract interface Cache<K, V>
{
  @Nullable
  public abstract V getIfPresent(Object paramObject);
  
  public abstract V get(K paramK, Callable<? extends V> paramCallable)
    throws ExecutionException;
  
  public abstract ImmutableMap<K, V> getAllPresent(Iterable<?> paramIterable);
  
  public abstract void put(K paramK, V paramV);
  
  public abstract void putAll(Map<? extends K, ? extends V> paramMap);
  
  public abstract void invalidate(Object paramObject);
  
  public abstract void invalidateAll(Iterable<?> paramIterable);
  
  public abstract void invalidateAll();
  
  public abstract long size();
  
  public abstract CacheStats stats();
  
  public abstract ConcurrentMap<K, V> asMap();
  
  public abstract void cleanUp();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */