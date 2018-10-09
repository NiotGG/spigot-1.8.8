package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public abstract interface ServerCache
{
  public abstract void init(EbeanServer paramEbeanServer);
  
  public abstract ServerCacheOptions getOptions();
  
  public abstract void setOptions(ServerCacheOptions paramServerCacheOptions);
  
  public abstract Object get(Object paramObject);
  
  public abstract Object put(Object paramObject1, Object paramObject2);
  
  public abstract Object putIfAbsent(Object paramObject1, Object paramObject2);
  
  public abstract Object remove(Object paramObject);
  
  public abstract void clear();
  
  public abstract int size();
  
  public abstract int getHitRatio();
  
  public abstract ServerCacheStatistics getStatistics(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */