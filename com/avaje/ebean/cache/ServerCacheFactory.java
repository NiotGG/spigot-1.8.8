package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public abstract interface ServerCacheFactory
{
  public abstract void init(EbeanServer paramEbeanServer);
  
  public abstract ServerCache createCache(String paramString, ServerCacheOptions paramServerCacheOptions);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */