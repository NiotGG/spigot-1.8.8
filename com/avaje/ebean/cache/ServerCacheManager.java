package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public abstract interface ServerCacheManager
{
  public abstract void init(EbeanServer paramEbeanServer);
  
  public abstract void setCaching(Class<?> paramClass, boolean paramBoolean);
  
  public abstract boolean isBeanCaching(Class<?> paramClass);
  
  public abstract ServerCache getNaturalKeyCache(Class<?> paramClass);
  
  public abstract ServerCache getBeanCache(Class<?> paramClass);
  
  public abstract ServerCache getCollectionIdsCache(Class<?> paramClass, String paramString);
  
  public abstract ServerCache getQueryCache(Class<?> paramClass);
  
  public abstract void clear(Class<?> paramClass);
  
  public abstract void clearAll();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */